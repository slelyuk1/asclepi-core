package com.jupiter.asclepi.core.repository;

import com.jupiter.asclepi.core.model.entity.disease.consultation.Consultation;
import com.jupiter.asclepi.core.model.entity.disease.consultation.ConsultationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, ConsultationId> {
}
