package com.jupiter.asclepi.core.repository;

import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseHistoryRepository extends JpaRepository<DiseaseHistory, DiseaseHistoryId> {
}
