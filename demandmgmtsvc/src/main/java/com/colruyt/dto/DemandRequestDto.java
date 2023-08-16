package com.colruyt.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class DemandRequestDto {

    private String reqId;

    @NotBlank(message = "ROLE should not be empty")
    private String role;

    @Pattern(regexp = "Java|Others",message = "not valid")
    private String technology;

    @NotBlank(message = "REQUESTED BY should not be empty")
    private String requestedBy;

    private String operatingUnit;


    @Pattern(regexp = "^com.[0-9]{3}.[0-9]{3}.[0-9]{3}$",message = "REF COMM NO should be in format of com.xxx.xxx.xxx")
    @NotBlank(message="REF COMM NO should not be empty")
    @Size(max = 15,message = "REF COMM NO field size should be less than 15 char")
    private String refCommNo;


    @Digits(integer = 2,fraction = 0,message = "NO OF RESOURCES should not be a fractional value")
    @Max(value = 20,message = "NO OF RESOURCES should be less than 20")
    @Min(value=1,message = "NO OF RESOURCES Should be greater than 1")
    private Integer noOfResources;


    private String level;

    @NotBlank(message="REQUESTING TM should not be empty")
    @Size(max = 40,message = "REQUESTING TM field size should be less than 40 char")
    private String requestingTm;

    @NotBlank(message="RESPONSIBLE TM should not be empty")
    @Size(max = 40,message = "RESPONSIBLE TM field size should be less than 40 char")
    private String responsibleTm;

    @NotNull(message = "REQUESTED DATE should not be empty")
    private LocalDate requestedDate;

    //@NotNull(message = "TARGET DATE should not be empty")
    private LocalDate targetDate;

    @NotBlank(message = "PRIORITY should not be empty")
    private String priority;

    @NotBlank(message = "RESOURCE TYPE should not be empty")
    private String resourceType;

    @Size(max = 400,message = "Comments field size should be less than 400 char")
    private String comments;


    private String status;

    private LocalDate offeredDate;

    private LocalDate dojOfferLetter;

    private String offerSlaMet;

    private LocalDate actualJoiningDate;

    private Integer requestToOffer;

    private Integer requestToJoining;

    @Size(max = 40,message = "RESOURCE NAME field size should be less than 40 char")
    private String resourceName;

    @Size(max = 400,message = "REMARKS field size should be less than 400 char")
    private String remarks;

    private String communicationToMandO;

    private Integer hireLoss;
}
