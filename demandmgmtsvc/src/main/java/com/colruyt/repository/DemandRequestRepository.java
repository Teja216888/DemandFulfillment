package com.colruyt.repository;

import com.colruyt.entity.DemandRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface DemandRequestRepository extends JpaRepository<DemandRequest,Long> {

    @Query("select u from DemandRequest u where u.refCommNo=?1 and u.status<>?2")
    List<DemandRequest> findByRefAndStatus(String ref, String st);

    //List<DemandRequest> findByRefCommNoAndNotStatus(String ref,String st);



    @Query("select u from DemandRequest u where (:role is null or u.role=:role) and " +
            "(:technology is null or u.technology=:technology) and " +
            "(:requestedBy is null or u.requestedBy=:requestedBy) and" +
            "(:status is null or u.status=:status)")
    List<DemandRequest> findByField(@Param("role") String role,@Param("technology") String technology,@Param("requestedBy") String requestedBy,@Param("status") String status);


}
