
package com.colruyt.repository;

import com.colruyt.entity.DemandTechnology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandTechnologyRepository extends JpaRepository<DemandTechnology,Integer> {
}
