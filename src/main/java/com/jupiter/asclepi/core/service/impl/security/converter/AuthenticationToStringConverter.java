package com.jupiter.asclepi.core.service.impl.security.converter;

import com.jupiter.asclepi.core.exception.AsclepiRuntimeException;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.Authentication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

@AllArgsConstructor
@Slf4j
public class AuthenticationToStringConverter implements Converter<Authentication, String> {
    @Override
    public String convert(Authentication source) {
        try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        ) {
            objectOutputStream.writeObject(source);
            return Base64.encode(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            log.error("An error occurred during Authentication {} to String conversion:", source, e);
            throw new AsclepiRuntimeException(e);
        }
    }
}
