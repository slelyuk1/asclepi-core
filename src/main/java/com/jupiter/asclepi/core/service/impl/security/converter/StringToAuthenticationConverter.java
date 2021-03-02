package com.jupiter.asclepi.core.service.impl.security.converter;

import com.jupiter.asclepi.core.exception.AsclepiRuntimeException;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.Authentication;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

@AllArgsConstructor
@Slf4j
public class StringToAuthenticationConverter implements Converter<String, Authentication> {

    @Override
    public Authentication convert(String source) {
        try (
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.decode(source));
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        ) {
            return (Authentication) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("An error occurred during String {} to Authentication conversion:", source, e);
            throw new AsclepiRuntimeException(e);
        }
    }
}
