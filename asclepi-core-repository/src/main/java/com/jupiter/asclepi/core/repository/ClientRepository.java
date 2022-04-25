package com.jupiter.asclepi.core.repository;

import com.jupiter.asclepi.core.model.model.entity.people.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@SuppressWarnings("unused")
@Repository
public interface ClientRepository extends JpaRepository<Client, BigInteger> {
}
