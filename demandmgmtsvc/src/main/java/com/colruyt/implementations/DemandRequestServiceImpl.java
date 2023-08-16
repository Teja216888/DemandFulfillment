package com.colruyt.implementations;
import com.colruyt.Validation;
import com.colruyt.converter.DemandConverter;
import com.colruyt.dto.DemandRequestDto;
import com.colruyt.entity.DemandRequest;
import com.colruyt.exception.IdNotFoundException;
import com.colruyt.exception.UpdateException;
import com.colruyt.repository.DemandRequestRepository;
import com.colruyt.services.DemandRequestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DemandRequestServiceImpl implements DemandRequestService {
    private DemandConverter demandConverter;

    private DemandRequestRepository demandRequestRepository;


    @Override
    public String createDemandRequest(DemandRequestDto demandRequestDto) {
        String id="";
        demandRequestDto.setHireLoss(0);
        List<DemandRequest> checkList=demandRequestRepository.findByRefAndStatus(demandRequestDto.getRefCommNo(),"cancelled");
        Validation.checkRefCommNo(checkList);
        for (int i = 0; i < demandRequestDto.getNoOfResources(); i++) {
            DemandRequest demandRequest1 = demandRequestRepository.save(demandConverter.convertDtoToEntity(demandRequestDto));
            id += "D"+String.format("%05d",demandRequest1.getReqId())+"   ";
        }
        return id;
    }

    @Override
    public List<DemandRequestDto> findAll() {
        List<DemandRequest> demandRequestList=new ArrayList<>();
        demandRequestRepository.findAll().forEach(i->demandRequestList.add(i));
        return demandConverter.convertEntityToDtoList(demandRequestList);
    }


    @Override
    public List<DemandRequestDto> findRequestsByField(DemandRequestDto demandRequestDto) {
        List<DemandRequest> demandRequestList = demandRequestRepository.findByField(demandRequestDto.getRole(), demandRequestDto.getTechnology(), demandRequestDto.getRequestedBy(), demandRequestDto.getStatus());
        if(demandRequestList.isEmpty()){
            throw new UpdateException("No results found for the provided data");
        }
        //return demandRequestList.stream().map(demandConverter::convertEntityToDto).collect(Collectors.toList());
        return demandConverter.convertEntityToDtoList(demandRequestList);
    }


    @Override
    public DemandRequestDto updateDemand(DemandRequestDto demandRequestDto) {
        DemandRequest demandRequest=demandConverter.convertDtoToEntity(demandRequestDto);
        DemandRequest result= demandRequestRepository.findById(demandRequest.getReqId()).get();
        if(result!=null) {
            Validation.checkHireLoss(result.getHireLoss(), demandRequest);
            demandRequestRepository.save(demandRequest);
            //return String.format("D%05d", demandRequest.getReqId());
            return demandConverter.convertEntityToDto(demandRequest);
        }
        else {
            throw new UpdateException("Id not present to update");
        }
    }


    @Override
    public DemandRequestDto findById(String id) {
        Optional<DemandRequest> demandRequest= Optional.ofNullable(demandRequestRepository.findById(Long.parseLong(id.substring(1))).orElseThrow(() -> new IdNotFoundException("demand request " + id + " not found")));
        return demandConverter.convertEntityToDto(demandRequest.get());
    }





}
