package com.jupiter.asclepi.core.repository;

import com.jupiter.asclepi.core.repository.entity.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.id.DiseaseHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("unused")
public interface DiseaseHistoryRepository extends JpaRepository<DiseaseHistory, DiseaseHistoryId> {
}
