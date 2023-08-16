
package com.colruyt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name = "DEMAND_REQUEST_TECHNOLOGY_UNQ")
public class DemandTechnology implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TECHNOLOGY_ID")
    private Integer technologyId;

    @Column(name = "TECHNOLOGY_NAME")
    private String technology;

    @OneToMany(mappedBy = "demandTechnology")
    private List<DemandRequest> demandRequest;

    public DemandTechnology() {

    }

    public DemandTechnology(int i, String s) {
        technology=s;
        technologyId=i;
    }
}

