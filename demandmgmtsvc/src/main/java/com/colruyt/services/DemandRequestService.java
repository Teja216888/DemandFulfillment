package com.colruyt.services;

import com.colruyt.dto.DemandRequestDto;

import java.util.List;


public interface DemandRequestService {

    String createDemandRequest(DemandRequestDto demandRequestDto);

    List<DemandRequestDto> findAll();

    List<DemandRequestDto> findRequestsByField(DemandRequestDto demandRequestDto);

    DemandRequestDto updateDemand(DemandRequestDto demandRequestDto);

    DemandRequestDto findById(String id);
}
