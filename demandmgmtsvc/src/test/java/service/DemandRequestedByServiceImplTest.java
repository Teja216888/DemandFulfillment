package service;

import com.colruyt.converter.DemandConverter;
import com.colruyt.dto.DemandRequestedByDto;
import com.colruyt.entity.DemandRequestedBy;
import com.colruyt.implementations.DemandRequestedByServiceImpl;
import com.colruyt.repository.DemandRequestedByRepository;
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
public class DemandRequestedByServiceImplTest {
    @InjectMocks
    DemandRequestedByServiceImpl demandRequestedByServiceImpl;

    @Mock
    DemandRequestedByRepository demandRequestedByRepository;

    @Mock
    DemandConverter demandConverter;

    @Test
    @DisplayName("find all requestedBy")
    void givenNone_whenFindAllRoles_thenReturnList(){
        DemandRequestedBy demandRequestedBy=new DemandRequestedBy(1,"Digital Factory");
        DemandRequestedBy demandRequestedBy1=new DemandRequestedBy(2,"IAAS");
        List<DemandRequestedBy> demandRequestedByList= Arrays.asList(demandRequestedBy,demandRequestedBy1);
        Mockito.when(demandRequestedByRepository.findAll()).thenReturn(demandRequestedByList);
        List<String> result=demandRequestedByServiceImpl.getAllRequestedBy();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualTo("Digital Factory");
    }

    @Test
    @DisplayName("create requestedBy")
    void givenDemandRole_whenCreateRole_thenReturnString(){
        DemandRequestedBy demandRequestedBy=new DemandRequestedBy(1,"Digital Factory");
        DemandRequestedByDto demandRequestedByDto=new DemandRequestedByDto(1,"Digital Factory");
        Mockito.when(demandConverter.convertRequestedByDtoToEntity(demandRequestedByDto)).thenReturn(demandRequestedBy);
        Mockito.when(demandRequestedByRepository.save(demandRequestedBy)).thenReturn(demandRequestedBy);
        String result=demandRequestedByServiceImpl.createRequestedBy(demandRequestedByDto);
        assertThat(result).isEqualTo(demandRequestedByDto.getRequestedBy());
    }
}
