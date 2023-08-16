package com.colruyt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@Table(name = "DEMAND_REQUEST_UNQ")
@IdClass(DemanRequestKey.class)
public class DemandRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REQ_ID")
    private Long reqId;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "TECHNOLOGY")
    private String technology;

    @Column(name="REQUESTED_BY")
    private String requestedBy;

    @Column(name="OPERATING_UNIT",length=40,columnDefinition = "OU IT")
    private String operatingUnit;

    @Column(name="REF_COMM_NO",length=15)
    private String refCommNo;

    @Column(name = "RESOURCE_TYPE")
    private String resourceType;

    @Column(name = "PRIORITY")
    private String priority;

    @Transient
    private Integer noOfResources;

    @Column(name="DEMAND_LEVEL")
    private String level;

    @Column(name="REQUESTING_TM",length=40)
    private String requestingTm;

    @Column(name="RESPONSIBLE_TM",length=40)
    private String responsibleTm;

    @Column(name="REQUESTED_DATE")
    private LocalDate requestedDate;

    @Column(name="TARGET_DATE")
    private LocalDate targetDate;

    @Column(name="COMMENTS",length=400)
    private String comments;

    @Column(name="STATUS")
    private String status;

    @Column(name="OFFERED_DATE")
    private LocalDate offeredDate;

    @Column(name="OFFER_SLA_MET")
    private String offerSlaMet;

    @Column(name="DOJ_OFFER_LETTER")
    private LocalDate dojOfferLetter;

    @Column(name="ACTUAL_JOINING_DATE")
    private LocalDate actualJoiningDate;

    @Column(name="REQUEST_TO_OFFER")
    private Integer requestToOffer;

    @Column(name="REQUEST_TO_JOINING")
    private Integer requestToJoining;

    @Column(name="RESOURCE_NAME",length=40)
    private String resourceName;

    @Column(name="REMARKS",length=400)
    private String remarks;

    @Column(name = "COMMUNICATION_TO_MANDO")
    private String communicationToMandO;

    @Column(name = "HIRE_LOSS")
    private Integer hireLoss;


    @JsonIgnore
    @JoinColumn(name = "ROLE",referencedColumnName = "ROLE_NAME",insertable = false,updatable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private DemandRole demandRole;

    @JsonIgnore
    @JoinColumn(name = "TECHNOLOGY",referencedColumnName = "TECHNOLOGY_NAME",insertable = false,updatable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private DemandTechnology demandTechnology;

    @JsonIgnore
    @JoinColumn(name = "REQUESTED_BY",referencedColumnName = "REQUESTED_BY_NAME",insertable = false,updatable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private DemandRequestedBy demandRequestedBy;


    public DemandRequest() {

    }

}

