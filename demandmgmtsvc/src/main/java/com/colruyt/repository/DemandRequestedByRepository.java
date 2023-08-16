package com.colruyt.repository;

import com.colruyt.entity.DemandRequestedBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandRequestedByRepository extends JpaRepository<DemandRequestedBy,Integer> {
}
