package com.colruyt.converter;

import com.colruyt.dto.DemandRequestDto;
import com.colruyt.dto.DemandRequestedByDto;
import com.colruyt.dto.DemandRoleDto;
import com.colruyt.dto.DemandTechnologyDto;
import com.colruyt.entity.DemandRequest;
import com.colruyt.entity.DemandRequestedBy;
import com.colruyt.entity.DemandRole;
import com.colruyt.entity.DemandTechnology;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DemandConverter {
    public DemandRequestDto convertEntityToDto(DemandRequest demandRequest){
        DemandRequestDto demandRequestDto=new DemandRequestDto();
        BeanUtils.copyProperties(demandRequest,demandRequestDto);
        demandRequestDto.setReqId(String.format("D%05d",demandRequest.getReqId()));
        return demandRequestDto;
    }

    public DemandRequest convertDtoToEntity(DemandRequestDto demandRequestDto){
        DemandRequest demandRequest=new DemandRequest();
        BeanUtils.copyProperties(demandRequestDto,demandRequest);
        setDemandId(demandRequestDto,demandRequest);
        return demandRequest;
    }

    public List<DemandRequest> convertDtoListToEntityList(List<DemandRequestDto> demandRequestDtoList)
    {
        List<DemandRequest> demandRequestList=new ArrayList<>();
        for(DemandRequestDto demandRequestDto:demandRequestDtoList){
            demandRequestList.add(convertDtoToEntity(demandRequestDto));
        }
        return demandRequestList;
    }

    public List<DemandRequestDto> convertEntityToDtoList(List<DemandRequest> demandRequestList)
    {
        List<DemandRequestDto> demandRequestDtoList=new ArrayList<>();
        for(DemandRequest demandRequest:demandRequestList){
            demandRequestDtoList.add(convertEntityToDto(demandRequest));
        }
        return demandRequestDtoList;
    }

    private void setDemandId(DemandRequestDto demandRequestDto,DemandRequest demandRequest){
        if(demandRequestDto.getReqId()!=null){
            demandRequest.setReqId(Long.parseLong(demandRequestDto.getReqId().substring(1)));
        }
    }

    public DemandRole convertRoleDtoToEntity(DemandRoleDto demandRoleDto){
        DemandRole demandRole=new DemandRole();
        BeanUtils.copyProperties(demandRoleDto,demandRole);
        return demandRole;
    }

    public DemandTechnology convertTechnologyDtoToEntity(DemandTechnologyDto demandTechnologyDto){
        DemandTechnology demandTechnology=new DemandTechnology();
        BeanUtils.copyProperties(demandTechnologyDto,demandTechnology);
        return demandTechnology;
    }

    public DemandRequestedBy convertRequestedByDtoToEntity(DemandRequestedByDto demandRequestedByDto){
        DemandRequestedBy demandRequestedBy=new DemandRequestedBy();
        BeanUtils.copyProperties(demandRequestedByDto,demandRequestedBy);
        return demandRequestedBy;
    }
}
