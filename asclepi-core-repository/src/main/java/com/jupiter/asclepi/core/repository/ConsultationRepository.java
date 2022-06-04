package com.jupiter.asclepi.core.repository;

import com.jupiter.asclepi.core.repository.entity.consultation.Consultation;
import com.jupiter.asclepi.core.repository.entity.consultation.ConsultationId;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("unused")
public interface ConsultationRepository extends JpaRepository<Consultation, ConsultationId> {
}
