package com.colruyt.controller;

import com.colruyt.Validation;
import com.colruyt.dto.DemandRequestDto;
import com.colruyt.dto.DemandRequestedByDto;
import com.colruyt.dto.DemandRoleDto;
import com.colruyt.dto.DemandTechnologyDto;
import com.colruyt.services.DemandRequestService;
import com.colruyt.services.DemandRequestedByService;
import com.colruyt.services.DemandRoleService;
import com.colruyt.services.DemandTechnologyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/demandmgmt")
public class DemandRequestController {
    private final DemandRequestService demandRequestService;
    private final DemandRoleService demandRoleService;
    private final DemandTechnologyService demandTechnologyService;
    private final DemandRequestedByService demandRequestedByService;


    @GetMapping("/roles")
    public List<String> getDemandRequestRoles(){
        return demandRoleService.getAllRoles();
    }

    @PostMapping("/addrole")
    public String createDemandRole(@RequestBody DemandRoleDto demandRoleDto){
        return "role added successfully with id : "+demandRoleService.createRole(demandRoleDto);
    }

    @PostMapping("/addtechnoloy")
    public String createDemandTechnology(@RequestBody DemandTechnologyDto demandTechnologyDto){
        return "technology added successfully with id : "+demandTechnologyService.createTechnology(demandTechnologyDto);
    }

    @PostMapping("/addrequestedby")
    public String createDemandRequestedBy(@RequestBody DemandRequestedByDto demandRequestedByDto){
        return "requested by data added successfully with id : "+demandRequestedByService.createRequestedBy(demandRequestedByDto);
    }

    @GetMapping("/technology")
    public List<String> getDemandTechnologies(){
        return demandTechnologyService.getAllTechnologies();
    }

    @GetMapping("/requestedby")
    public List<String> getRequestedBy(){
        return demandRequestedByService.getAllRequestedBy();
    }

    @PostMapping("/adddemand")
    public String addDemandRequest(@RequestBody @Valid DemandRequestDto demandRequestDto){
        Validation.validateCreateDemandRequest(demandRequestDto);
        return "Demand Request Created with id : "+demandRequestService.createDemandRequest(demandRequestDto);
    }

    @GetMapping("/all")
    public List<DemandRequestDto> findAllDemandRequests(){
        return demandRequestService.findAll();
    }

    @GetMapping("/get-by-id/{id}")
    public DemandRequestDto findDemandById(@PathVariable String id){
        return demandRequestService.findById(id);
    }

    @PostMapping("/search")
    public List<DemandRequestDto> findDemandRequestByFields(@RequestBody DemandRequestDto demandRequestDto){
        Validation.validateSearch(demandRequestDto);
        return demandRequestService.findRequestsByField(demandRequestDto);
    }

    @PutMapping("/update")
    public String updateDemandRequest(@RequestBody DemandRequestDto demandRequestDto){
        Validation.checkUpdateDto(demandRequestDto);
        DemandRequestDto demandRequestDto1=demandRequestService.updateDemand(demandRequestDto);
        String s=demandRequestDto1  .getReqId();
        return "Demand Request "+s+" updated successfully";
    }
}
