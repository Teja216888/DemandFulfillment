package com.colruyt.services;

import com.colruyt.dto.DemandTechnologyDto;

import java.util.List;

public interface DemandTechnologyService {
    String createTechnology(DemandTechnologyDto demandTechnologyDto);

    List<String> getAllTechnologies();
}
