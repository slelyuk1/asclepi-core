package com.jupiter.asclepi.core.repository;

import com.jupiter.asclepi.core.model.entity.analysis.Analysis;
import com.jupiter.asclepi.core.model.entity.analysis.AnalysisId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisRepository extends JpaRepository<Analysis, AnalysisId> {
}