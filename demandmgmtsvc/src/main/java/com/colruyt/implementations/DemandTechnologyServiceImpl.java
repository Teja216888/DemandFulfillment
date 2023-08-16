package com.colruyt.implementations;

import com.colruyt.converter.DemandConverter;
import com.colruyt.dto.DemandTechnologyDto;
import com.colruyt.entity.DemandTechnology;
import com.colruyt.repository.DemandTechnologyRepository;
import com.colruyt.services.DemandTechnologyService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class DemandTechnologyServiceImpl implements DemandTechnologyService {
    private DemandTechnologyRepository demandTechnologyRepository;
    private DemandConverter demandConverter;

    public DemandTechnologyServiceImpl(DemandConverter demandConverter,DemandTechnologyRepository demandTechnologyRepository){
        this.demandConverter=demandConverter;
        this.demandTechnologyRepository=demandTechnologyRepository;
    }


    @Override
    public String createTechnology(DemandTechnologyDto demandTechnologyDto) {
        DemandTechnology demandTechnology=demandTechnologyRepository.save(demandConverter.convertTechnologyDtoToEntity(demandTechnologyDto));
        return demandTechnology.getTechnology();
    }


    public List<String> getAllTechnologies() {
        List<String> technologyList=new ArrayList<>();
        demandTechnologyRepository.findAll().forEach(tech->technologyList.add(tech.getTechnology()));
        return technologyList;
    }
}
