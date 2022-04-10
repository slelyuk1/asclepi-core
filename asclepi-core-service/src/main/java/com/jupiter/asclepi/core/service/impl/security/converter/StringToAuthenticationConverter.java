package com.jupiter.asclepi.core.service.impl.security.converter;

import com.jupiter.asclepi.core.service.exception.AsclepiRuntimeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.Authentication;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;

@Slf4j
@AllArgsConstructor
public class StringToAuthenticationConverter implements Converter<String, Authentication> {

    private final Base64.Decoder decoder;

    @Override
    public Authentication convert(String source) {
        try (
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decoder.decode(source));
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        ) {
            return (Authentication) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("An error occurred during String {} to Authentication conversion:", source, e);
            throw new AsclepiRuntimeException(e);
        }
    }
}
