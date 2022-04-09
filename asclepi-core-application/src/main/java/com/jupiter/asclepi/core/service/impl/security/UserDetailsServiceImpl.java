package com.jupiter.asclepi.core.service.impl.security;

import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import com.jupiter.asclepi.core.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("defaultUserDetailService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final String NOT_FOUND_MESSAGE = "User with login %s doesn't exist!";

    private final EmployeeRepository employeeService;
    private final ConversionService conversionService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Employee toSearch = new Employee();
        toSearch.setLogin(login);
        return employeeService.findOne(Example.of(toSearch))
                .map(employee -> conversionService.convert(employee, User.class))
                .orElseThrow(() -> new UsernameNotFoundException(String.format(NOT_FOUND_MESSAGE, login)));
    }
}
