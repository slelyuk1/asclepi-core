package com.jupiter.asclepi.core.web.configuration;

import com.jupiter.asclepi.core.web.filter.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    public static final String AUTHENTICATION_ENDPOINT_PREFIX = "/api/v1/security";
    public static final String AUTHENTICATION_ENDPOINT_SUFFIX = "/authenticate";
    // todo why it is not used
    // public static final String AUTHENTICATION_COOKIE_NAME = "access_token";

    private final String serverSecret;
    private final int serverInteger;
    private final UserDetailsService userDetailsService;
    private final ConversionService conversionService;

    @Autowired
    public WebSecurityConfiguration(@Value("${security.secret}") String serverSecret,
                                    @Value("${security.serverInteger}") Integer serverInteger,
                                    @Qualifier("defaultUserDetailService") UserDetailsService userDetailsService,
                                    ConversionService conversionService) {
        this.serverSecret = serverSecret;
        this.serverInteger = serverInteger;
        this.userDetailsService = userDetailsService;
        this.conversionService = conversionService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // todo enable csrf and cors
        http
                .cors().and()
                .csrf().ignoringAntMatchers(AUTHENTICATION_ENDPOINT_PREFIX + AUTHENTICATION_ENDPOINT_SUFFIX).disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAt(new AuthorizationFilter(conversionService, tokenService()), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, AUTHENTICATION_ENDPOINT_PREFIX + AUTHENTICATION_ENDPOINT_SUFFIX)
                .permitAll()
                .anyRequest()
                .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Collections.singletonList("https://asclepi-web-client.herokuapp.com"));
        configuration.addAllowedOriginPattern("*");  // TODO: lock down before deploying
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        final String idForEncode = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }

    @Bean
    public TokenService tokenService() {
        KeyBasedPersistenceTokenService tokenService = new KeyBasedPersistenceTokenService();
        tokenService.setServerSecret(serverSecret);
        tokenService.setServerInteger(serverInteger);
        tokenService.setSecureRandom(new SecureRandom());
        return tokenService;
    }
}
