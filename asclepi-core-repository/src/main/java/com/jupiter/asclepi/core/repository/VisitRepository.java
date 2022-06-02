package com.jupiter.asclepi.core.repository;

import com.jupiter.asclepi.core.repository.entity.Visit;
import com.jupiter.asclepi.core.repository.entity.id.VisitId;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("unused")
public interface VisitRepository extends JpaRepository<Visit, VisitId> {
}
