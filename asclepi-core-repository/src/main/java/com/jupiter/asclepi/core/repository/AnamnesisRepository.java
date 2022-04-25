package com.jupiter.asclepi.core.repository;

import com.jupiter.asclepi.core.model.model.entity.disease.Anamnesis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

@SuppressWarnings("unused")
public interface AnamnesisRepository extends JpaRepository<Anamnesis, BigInteger> {
}
