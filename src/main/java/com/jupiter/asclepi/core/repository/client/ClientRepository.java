package com.jupiter.asclepi.core.repository.client;

import com.jupiter.asclepi.core.model.entity.people.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
