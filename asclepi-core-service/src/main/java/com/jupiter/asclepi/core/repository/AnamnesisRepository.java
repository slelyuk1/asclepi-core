package com.jupiter.asclepi.core.repository;

import com.jupiter.asclepi.core.model.entity.disease.Anamnesis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface AnamnesisRepository extends JpaRepository<Anamnesis, BigInteger> {
}
