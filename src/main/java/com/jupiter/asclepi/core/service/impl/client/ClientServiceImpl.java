package com.jupiter.asclepi.core.service.impl.client;

import com.jupiter.asclepi.core.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.request.people.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.people.EditClientRequest;
import com.jupiter.asclepi.core.repository.ClientRepository;
import com.jupiter.asclepi.core.service.ClientService;
import com.jupiter.asclepi.core.util.CustomBeanUtils;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Transactional
@Validated
@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ConversionService conversionService;
    private final ClientRepository repository;


    @Override
    public Try<Client> create(@Valid @NotNull CreateClientRequest createRequest) {
        Client toCreate = conversionService.convert(createRequest, Client.class);
        return Try.of(() -> repository.save(Objects.requireNonNull(toCreate)));
    }

    @Override
    public Boolean delete(@Valid @NotNull BigInteger toDeleteId) {
        return repository.findById(toDeleteId)
                .map(toDelete -> {
                    repository.delete(toDelete);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Try<Client> edit(@Valid @NotNull EditClientRequest editRequest) {
        return Try.of(() -> {
            Client existing = repository.findById(editRequest.getId())
                    .orElseThrow(() -> new NonExistentIdException("Client", editRequest.getId()));
            Client toCopyFrom = Objects.requireNonNull(conversionService.convert(editRequest, Client.class));
            CustomBeanUtils.copyPropertiesWithoutNull(toCopyFrom, existing);
            return repository.save(existing);
        });
    }

    @Override
    public List<Client> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Client> getOne(@Valid @NotNull BigInteger clientId) {
        return repository.findById(clientId);
    }
}
