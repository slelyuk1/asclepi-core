package com.jupiter.asclepi.core.repository;

import com.jupiter.asclepi.core.repository.entity.Analysis;
import com.jupiter.asclepi.core.repository.entity.id.AnalysisId;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("unused")
public interface AnalysisRepository extends JpaRepository<Analysis, AnalysisId> {
}