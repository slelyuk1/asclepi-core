package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.repository.entity.client.Client;
import com.jupiter.asclepi.core.service.api.ClientService;
import com.jupiter.asclepi.core.service.helper.api.AbstractService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.math.BigInteger;

@Transactional
@Validated
@Service
public class ClientServiceImpl extends AbstractService<Client, BigInteger> implements ClientService {

    public ClientServiceImpl(ConversionService conversionService,
                             JpaRepository<Client, BigInteger> repository) {
        super(conversionService, repository);
    }

}
