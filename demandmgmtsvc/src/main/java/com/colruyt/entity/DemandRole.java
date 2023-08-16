package com.colruyt.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name = "DEMAND_REQUEST_ROLE_UNQ")
public class DemandRole implements Serializable {
    @Id
    @Column(name = "ROLE_NAME")
    private String role;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROLE_ID")
    private Integer roleId;

    @OneToMany(mappedBy = "demandRole")
    private List<DemandRequest> demandRequest;

    public DemandRole(int id, String roleName) {
        roleId=id;
        role=roleName;
    }

    public DemandRole() {

    }
}

