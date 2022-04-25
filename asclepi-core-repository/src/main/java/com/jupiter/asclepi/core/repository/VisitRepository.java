package com.jupiter.asclepi.core.repository;

import com.jupiter.asclepi.core.model.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.VisitId;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("unused")
public interface VisitRepository extends JpaRepository<Visit, VisitId> {
}
