package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.model.request.client.CreateClientRequest;
import com.jupiter.asclepi.core.model.request.client.EditClientRequest;
import com.jupiter.asclepi.core.repository.ClientRepository;
import com.jupiter.asclepi.core.repository.entity.client.Client;
import com.jupiter.asclepi.core.service.api.ClientService;
import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.service.util.CustomBeanUtils;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.repository.JpaRepository;
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
    public ConversionService getConversionService() {
        return conversionService;
    }

    @Override
    public JpaRepository<Client, BigInteger> getRepository() {
        return repository;
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
    public Client edit(@Valid @NotNull EditClientRequest editRequest) {
        Client existing = repository.findById(editRequest.getId())
                .orElseThrow(() -> new NonExistentIdException("Client", editRequest.getId()));
        Client toCopyFrom = Objects.requireNonNull(conversionService.convert(editRequest, Client.class));
        CustomBeanUtils.copyPropertiesWithoutNull(toCopyFrom, existing);
        return repository.save(existing);
    }

}
