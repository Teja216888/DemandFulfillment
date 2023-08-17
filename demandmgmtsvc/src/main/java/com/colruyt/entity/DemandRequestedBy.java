package com.colruyt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name = "DEMAND_REQUEST_REQUESTED_BY_UNQ")
@AllArgsConstructor
@NoArgsConstructor
public class DemandRequestedBy implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REQUESTED_BY_ID")
    private Integer requestedById;

    @Column(name = "REQUESTED_BY_NAME")
    private String requestedBy;
/*

    @OneToMany(mappedBy = "demandRequestedBy")
    private List<DemandRequest> demandRequest;

*/

}
