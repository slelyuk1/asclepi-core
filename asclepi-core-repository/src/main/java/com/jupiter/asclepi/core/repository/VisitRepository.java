package com.jupiter.asclepi.core.repository;

import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.repository.entity.visit.VisitId;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("unused")
public interface VisitRepository extends JpaRepository<Visit, VisitId> {
}
