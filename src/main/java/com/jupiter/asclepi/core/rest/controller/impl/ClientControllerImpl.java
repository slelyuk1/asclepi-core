package com.jupiter.asclepi.core.rest.controller.impl;

import com.jupiter.asclepi.core.exception.AsclepiRuntimeException;
import com.jupiter.asclepi.core.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.model.request.people.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.people.EditClientRequest;
import com.jupiter.asclepi.core.model.response.people.ClientInfo;
import com.jupiter.asclepi.core.rest.controller.ClientController;
import com.jupiter.asclepi.core.service.ClientService;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/client")
public class ClientControllerImpl implements ClientController {

    private final ClientService clientService;
    private final ConversionService conversionService;

    @Override
    public ResponseEntity<?> create(CreateClientRequest createRequest) {
        Try<ResponseEntity<?>> creationTry = clientService.create(createRequest).map(client -> {
            ClientInfo clientInfo = conversionService.convert(client, ClientInfo.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(clientInfo);
        });
        return creationTry
                .onFailure(e -> log.error("An error occurred during client creation: ", e))
                .getOrElseThrow(AsclepiRuntimeException::new);
    }

    @Override
    public ResponseEntity<?> edit(@NotNull EditClientRequest editRequest) {
        Try<ResponseEntity<?>> editionTry = clientService.edit(editRequest).map(edited -> {
            ClientInfo clientInfo = conversionService.convert(edited, ClientInfo.class);
            return ResponseEntity.ok().body(clientInfo);
        });
        return editionTry
                .recover(NonExistentIdException.class, e -> generateNotFoundResponse())
                .onFailure(e -> log.error("An error occurred during client creation: ", e))
                .getOrElseThrow(AsclepiRuntimeException::new);
    }

    @Override
    public List<ClientInfo> getAll() {
        return clientService.getAll().stream()
                .map(client -> conversionService.convert(client, ClientInfo.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<ClientInfo> getOne(@NotNull Integer getRequest) {
        // todo implement when security features will be implemented
        throw new UnsupportedOperationException("This functionality is not implemented yet!");
    }

    private static <T> ResponseEntity<T> generateNotFoundResponse(){
        return ResponseEntity.notFound().header(CONTENT_TYPE, APPLICATION_JSON_VALUE).build();
    }
}
