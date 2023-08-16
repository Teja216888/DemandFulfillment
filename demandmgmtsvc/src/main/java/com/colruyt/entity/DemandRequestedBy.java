package com.colruyt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name = "DEMAND_REQUEST_REQUESTED_BY_UNQ")
public class DemandRequestedBy implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "REQUESTED_BY_ID")
    private Integer requestedById;

    @Column(name = "REQUESTED_BY_NAME")
    private String requestedBy;

    @OneToMany(mappedBy = "demandRequestedBy")
    private List<DemandRequest> demandRequest;


    public DemandRequestedBy() {

    }

    public DemandRequestedBy(int i, String s) {
        requestedBy=s;
        requestedById=i;
    }
}
