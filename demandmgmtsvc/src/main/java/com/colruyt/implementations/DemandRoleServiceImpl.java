package com.colruyt.implementations;

import com.colruyt.Constants;
import com.colruyt.converter.DemandConverter;
import com.colruyt.dto.DemandRoleDto;
import com.colruyt.entity.DemandRole;
import com.colruyt.repository.DemandRoleRepository;
import com.colruyt.services.DemandRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemandRoleServiceImpl implements DemandRoleService {
    private DemandRoleRepository demandRoleRepository;

    private DemandConverter demandConverter;

    public DemandRoleServiceImpl(DemandRoleRepository demandRoleRepository,DemandConverter demandConverter){
        this.demandConverter=demandConverter;
        this.demandRoleRepository=demandRoleRepository;
    }


    @Override
    public String createRole(DemandRoleDto demandRoleDto) {
        DemandRole demandRole=demandRoleRepository.save(demandConverter.convertRoleDtoToEntity(demandRoleDto));
        // apache

        Constants.ROLE_LIST.add(demandRoleDto.getRole());
        System.out.println(Constants.ROLE_LIST);
        return demandRole.getRole();


    }

    public List<String> getAllRoles() {
        List<String> roleList = new ArrayList<>();
        demandRoleRepository.findAll().forEach(role->roleList.add(role.getRole()));
        return roleList;
    }
}
