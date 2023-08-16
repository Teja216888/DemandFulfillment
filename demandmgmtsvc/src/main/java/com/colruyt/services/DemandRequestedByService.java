package com.colruyt.services;

import com.colruyt.dto.DemandRequestedByDto;

import java.util.List;

public interface DemandRequestedByService {
    String createRequestedBy(DemandRequestedByDto demandRequestedByDto);

    List<String> getAllRequestedBy();
}
