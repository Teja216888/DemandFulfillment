package com.colruyt;

import com.colruyt.dto.DemandRequestDto;
import com.colruyt.entity.DemandRequest;
import com.colruyt.exception.RefCommNoException;
import com.colruyt.exception.TechnoloyMandatoryRoleSE;
import com.colruyt.exception.UpdateException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.interceptor.CacheOperationSource;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

public class Validation {
    public  static void checkUpdateDto(DemandRequestDto demandRequestDto) {

        if(nonNull(demandRequestDto.getStatus())) {
                if (demandRequestDto.getOfferedDate()==null) {
                    throw new UpdateException("offered date is mandatory when status is offered");
                }
                if (demandRequestDto.getDojOfferLetter() == null) {
                    throw new UpdateException("Doj Offer letter is mandatory when status is offered");
                }
            }
            if(Constants.STATUS_RECRUITED.equals(demandRequestDto.getStatus()) && demandRequestDto.getActualJoiningDate()==null){
                    throw new UpdateException("Actual Joining Date is mandatory when status is recruited");
            }


        if(demandRequestDto.getOfferedDate()!=null && demandRequestDto.getRequestToOffer()==null){
                throw new UpdateException("Request to offer is mandatory when offered date is present");
        }
        if(demandRequestDto.getDojOfferLetter()!=null && demandRequestDto.getRequestToJoining()==null){
                throw new UpdateException("Request to Joining is mandatory when Actual Joining date is present");
        }
        if(demandRequestDto.getResourceName()==null){
            throw new UpdateException("Resource name is mandatory field");
        }
        if(demandRequestDto.getCommunicationToMandO()==null){
            throw new UpdateException("Communication to M and O is mandatory field");
        }
        if(demandRequestDto.getOfferedDate()!=null && demandRequestDto.getRequestedDate()!=null){
            long requestToOffer=ChronoUnit.DAYS.between(demandRequestDto.getRequestedDate(),demandRequestDto.getOfferedDate());
            demandRequestDto.setRequestToOffer(Math.toIntExact(requestToOffer));
        }

        if(demandRequestDto.getDojOfferLetter()!=null && demandRequestDto.getRequestedDate()!=null){
            long requestToJoining=ChronoUnit.DAYS.between(demandRequestDto.getRequestedDate(),demandRequestDto.getDojOfferLetter());
            demandRequestDto.setRequestToJoining(Math.toIntExact(requestToJoining));
        }
    }

    public static void checkHireLoss(Integer HireLossFromId, DemandRequest demandRequest) {
        if (demandRequest.getHireLoss() > HireLossFromId) {

            demandRequest.setActualJoiningDate(null);
            demandRequest.setDojOfferLetter(null);
            demandRequest.setOfferedDate(null);
            demandRequest.setRequestToJoining(null);
            demandRequest.setRequestToOffer(null);
            demandRequest.setResourceName(null);
            demandRequest.setCommunicationToMandO(null);
            demandRequest.setOfferSlaMet(null);
            demandRequest.setRemarks(null);
            demandRequest.setStatus("In-progress");

        }
    }

    public static void validateSearch(DemandRequestDto demandRequestDto){
        if(demandRequestDto.getRole()==null || demandRequestDto.getRole().trim().length()==0){
            throw new UpdateException("Role is mandatory field");
        }
        else{
            if(Constants.ROLE_SE.equals(demandRequestDto.getRole())&&(demandRequestDto.getTechnology()==null || demandRequestDto.getTechnology().trim().length()==0)){
                    throw new TechnoloyMandatoryRoleSE("Technology is mandatory field when role is SE");
            }
        }
    }



    public static void validateCreateDemandRequest(DemandRequestDto demandRequestDto){
        if(!Constants.ROLE_LIST.contains(demandRequestDto.getRole())){
            throw new UpdateException("Role value should be 'SE', 'SA', 'PM'");
        }
        if(!Constants.TECHNOLOGY_LIST.contains(demandRequestDto.getTechnology())){
            throw new UpdateException("Technology value should be 'Mainframe', 'Java', 'test3','Others'");
        }
        if(!Constants.REQUESTEDBY_LIST.contains(demandRequestDto.getRequestedBy())){
            throw new UpdateException("Requested By value should be 'IAAS', 'abcd', 'Digital Factory'");
        }

        if(demandRequestDto.getRequestedBy()!=null){
            if(Constants.REQ_BY_DIGITAL_FACTORY.equals(demandRequestDto.getRequestedBy())){
                demandRequestDto.setOperatingUnit(Constants.REQ_BY_DIGITAL_FACTORY);
            }
            else{
                demandRequestDto.setOperatingUnit(Constants.REQ_BY_OTHERS);
            }
        }
        if(Constants.ROLE_SE.equals(demandRequestDto.getRole())&&(demandRequestDto.getTechnology()==null || demandRequestDto.getTechnology().trim().length()==0)){
                throw new TechnoloyMandatoryRoleSE("Technology is a mandatory field when role is SE");
        }
        if(demandRequestDto.getRequestedDate()!=null){
            demandRequestDto.setTargetDate(demandRequestDto.getRequestedDate().plusDays(56));
        }
    }



    public static void checkRefCommNo(List<DemandRequest> checkList){
        if(checkList.size()>0){
            String cancelId="";
            for(int i=0;i<checkList.size();i++){
                cancelId+="D"+String.format("%05d",checkList.get(i).getReqId())+" ";
            }
            throw new RefCommNoException("The Demand request already exists with ids: "+cancelId);
        }
    }


}
