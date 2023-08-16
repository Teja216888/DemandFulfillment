package com.colruyt.implementations;

import com.colruyt.converter.DemandConverter;
import com.colruyt.dto.DemandRequestedByDto;
import com.colruyt.entity.DemandRequestedBy;
import com.colruyt.repository.DemandRequestedByRepository;
import com.colruyt.services.DemandRequestedByService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class DemandRequestedByServiceImpl implements DemandRequestedByService {
    private DemandRequestedByRepository demandRequestedByRepository;
    private DemandConverter demandConverter;

    public DemandRequestedByServiceImpl(DemandRequestedByRepository demandRequestedByRepository,DemandConverter demandConverter){
        this.demandRequestedByRepository=demandRequestedByRepository;
        this.demandConverter=demandConverter;
    }

    @Override
    public String createRequestedBy(DemandRequestedByDto demandRequestedByDto) {
        DemandRequestedBy demandRequestedBy=demandRequestedByRepository.save(demandConverter.convertRequestedByDtoToEntity(demandRequestedByDto));
        return demandRequestedBy.getRequestedBy();
    }

    public List<String> getAllRequestedBy() {
        List<String> requestedByList=new ArrayList<>();
        demandRequestedByRepository.findAll().forEach(reqBy->requestedByList.add(reqBy.getRequestedBy()));
        return requestedByList;
    }
}
