package com.jupiter.asclepi.core.repository;

import com.jupiter.asclepi.core.repository.entity.Consultation;
import com.jupiter.asclepi.core.repository.entity.id.ConsultationId;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("unused")
public interface ConsultationRepository extends JpaRepository<Consultation, ConsultationId> {
}
