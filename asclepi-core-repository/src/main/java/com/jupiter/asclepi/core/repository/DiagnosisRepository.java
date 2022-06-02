package com.jupiter.asclepi.core.repository;

import com.jupiter.asclepi.core.repository.entity.Diagnosis;
import com.jupiter.asclepi.core.repository.entity.id.DiagnosisId;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("unused")
public interface DiagnosisRepository extends JpaRepository<Diagnosis, DiagnosisId> {
}
