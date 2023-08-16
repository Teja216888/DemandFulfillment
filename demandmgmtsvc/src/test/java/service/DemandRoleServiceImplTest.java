package service;

import com.colruyt.converter.DemandConverter;
import com.colruyt.dto.DemandRoleDto;
import com.colruyt.entity.DemandRole;
import com.colruyt.implementations.DemandRoleServiceImpl;
import com.colruyt.repository.DemandRoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class DemandRoleServiceImplTest {
    @InjectMocks
    DemandRoleServiceImpl demandRoleServiceImpl;

    @Mock
    DemandRoleRepository demandRoleRepository;

    @Mock
    DemandConverter demandConverter;

    @Test
    @DisplayName("find all roles")
    void givenNone_whenFindAllRoles_thenReturnList(){
        DemandRole demandRole=new DemandRole(1,"SA");
        DemandRole demandRole1=new DemandRole(2,"PM");
        List<DemandRole> demandRoleList= Arrays.asList(demandRole,demandRole1);
        Mockito.when(demandRoleRepository.findAll()).thenReturn(demandRoleList);
        List<String> result=demandRoleServiceImpl.getAllRoles();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualTo("SA");
    }

    @Test
    void givenDemandRole_whenCreateRole_thenReturnString(){
        DemandRole demandRole=new DemandRole(3,"SITA");
        DemandRoleDto demandRoleDto=new DemandRoleDto(3,"SITA");
        Mockito.when(demandConverter.convertRoleDtoToEntity(demandRoleDto)).thenReturn(demandRole);
        Mockito.when(demandRoleRepository.save(demandRole)).thenReturn(demandRole);
        String result=demandRoleServiceImpl.createRole(demandRoleDto);
        assertThat(result).isEqualTo(demandRoleDto.getRole());
    }
}
