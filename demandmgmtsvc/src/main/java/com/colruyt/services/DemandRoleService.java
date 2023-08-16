package com.colruyt.services;

import com.colruyt.dto.DemandRoleDto;

import java.util.List;

public interface DemandRoleService {
     String createRole(DemandRoleDto demandRoleDto);
      List<String> getAllRoles();
}
