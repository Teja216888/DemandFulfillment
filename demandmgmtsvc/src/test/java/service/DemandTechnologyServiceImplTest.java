package service;

import com.colruyt.converter.DemandConverter;
import com.colruyt.dto.DemandTechnologyDto;
import com.colruyt.entity.DemandTechnology;
import com.colruyt.implementations.DemandTechnologyServiceImpl;
import com.colruyt.repository.DemandTechnologyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class DemandTechnologyServiceImplTest {
    @InjectMocks
    DemandTechnologyServiceImpl demandTechnologyServiceImpl;

    @Mock
    DemandTechnologyRepository demandTechnologyRepository;

    @Mock
    DemandConverter demandConverter;

    @Test
    @DisplayName("find all technologies")
    void givenNone_whenFindAllRoles_thenReturnList(){
        DemandTechnology demandTechnology=new DemandTechnology(1,"JAVA");
        DemandTechnology demandTechnology1=new DemandTechnology(2,"DM");
        List<DemandTechnology> demandTechnologyList= Arrays.asList(demandTechnology,demandTechnology1);
        Mockito.when(demandTechnologyRepository.findAll()).thenReturn(demandTechnologyList);
        List<String> result=demandTechnologyServiceImpl.getAllTechnologies();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualTo("JAVA");
    }

    @Test
    @DisplayName("create technology")
    void givenDemandRole_whenCreateRole_thenReturnString(){
        DemandTechnology demandTechnology=new DemandTechnology(3,"SQL");
        DemandTechnologyDto demandTechnologyDto=new DemandTechnologyDto(3,"SQL");
        Mockito.when(demandConverter.convertTechnologyDtoToEntity(demandTechnologyDto)).thenReturn(demandTechnology);
        Mockito.when(demandTechnologyRepository.save(demandTechnology)).thenReturn(demandTechnology);
        String result=demandTechnologyServiceImpl.createTechnology(demandTechnologyDto);
        assertThat(result).isEqualTo(demandTechnologyDto.getTechnology());
    }
}
