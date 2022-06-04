package com.jupiter.asclepi.core.repository;

import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("unused")
public interface DiseaseHistoryRepository extends JpaRepository<DiseaseHistory, DiseaseHistoryId> {
}
