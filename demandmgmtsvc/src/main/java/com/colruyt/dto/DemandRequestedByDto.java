package com.colruyt.dto;

import lombok.Data;

@Data
public class DemandRequestedByDto {
    private Integer requestedByid;
    private String requestedBy;

    public DemandRequestedByDto(int i, String s) {
        requestedBy=s;
        requestedByid=i;
    }

    public DemandRequestedByDto() {

    }
}
