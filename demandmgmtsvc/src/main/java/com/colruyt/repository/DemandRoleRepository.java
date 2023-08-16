package com.colruyt.repository;

import com.colruyt.entity.DemandRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface DemandRoleRepository extends JpaRepository<DemandRole,Integer> {

//    @Query("select a.roleName from DemandRole a where a.roleName like :name%")
//    public String getRole(@Param("name") String name);

}
