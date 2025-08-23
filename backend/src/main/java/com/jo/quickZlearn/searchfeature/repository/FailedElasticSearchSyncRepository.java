package com.jo.quickZlearn.searchfeature.repository;

import com.jo.quickZlearn.searchfeature.entity.FailedElasticSearchSync;
import com.jo.quickZlearn.searchfeature.entity.SyncStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FailedElasticSearchSyncRepository extends JpaRepository<FailedElasticSearchSync, Long> {
    List<FailedElasticSearchSync> findByStatus(SyncStatus status);
}
