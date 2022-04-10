package com.jupiter.asclepi.core.service.impl.security.converter;

import com.jupiter.asclepi.core.service.exception.AsclepiRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.Authentication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationToStringConverter implements Converter<Authentication, String> {

    private final Base64.Encoder encoder;

    @Override
    public String convert(Authentication source) {

        try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        ) {
            objectOutputStream.writeObject(source);
            return new String(encoder.encode(byteArrayOutputStream.toByteArray()));
        } catch (IOException e) {
            log.error("An error occurred during Authentication {} to String conversion:", source, e);
            throw new AsclepiRuntimeException(e);
        }
    }
}
