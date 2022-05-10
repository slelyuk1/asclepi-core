package com.jupiter.asclepi.core.repository;

import com.jupiter.asclepi.core.model.model.entity.analysis.Analysis;
import com.jupiter.asclepi.core.model.model.entity.analysis.AnalysisId;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("unused")
public interface AnalysisRepository extends JpaRepository<Analysis, AnalysisId> {
}