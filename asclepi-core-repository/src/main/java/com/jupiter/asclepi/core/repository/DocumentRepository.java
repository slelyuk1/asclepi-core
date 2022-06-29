package com.jupiter.asclepi.core.repository;

import com.jupiter.asclepi.core.repository.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface DocumentRepository extends JpaRepository<Document, BigInteger> {
}
