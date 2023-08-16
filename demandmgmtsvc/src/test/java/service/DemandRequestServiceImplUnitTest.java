package service;

import com.colruyt.Validation;
import com.colruyt.converter.DemandConverter;
import com.colruyt.dto.DemandRequestDto;
import com.colruyt.entity.DemandRequest;
import com.colruyt.exception.IdNotFoundException;
import com.colruyt.exception.RefCommNoException;
import com.colruyt.repository.DemandRequestRepository;
import com.colruyt.implementations.DemandRequestServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class DemandRequestServiceImplUnitTest {
    @InjectMocks
    DemandRequestServiceImpl demandRequestServiceImpl;

    @Mock
    DemandRequestRepository demandRequestRepository;

    @Mock
    DemandConverter demandConverter;

    @Mock
    Validation validation;

    @Test
    @DisplayName("find all")
    void givenNone_whenFindAll_thenReturnList(){
       DemandRequest demandRequest=new DemandRequest();
       demandRequest.setRole("PM");

       DemandRequest demandRequest1=new DemandRequest();
       demandRequest1.setRole("SA");

        List<DemandRequest> demandRequestList=Arrays.asList(demandRequest,demandRequest1);

        DemandRequestDto demandRequestDto1=new DemandRequestDto();
        demandRequestDto1.setRole("PM");

        DemandRequestDto demandRequestDto2=new DemandRequestDto();
        demandRequestDto2.setRole("SA");

        List<DemandRequestDto> demandRequestDtoList=Arrays.asList(demandRequestDto1,demandRequestDto2);

        Mockito.when(demandConverter.convertEntityToDtoList(demandRequestList)).thenReturn(demandRequestDtoList);
        Mockito.when(demandRequestRepository.findAll()).thenReturn(demandRequestList);
        List<DemandRequestDto> result=demandRequestServiceImpl.findAll();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getRole()).isEqualTo("PM");

    }

    @Test
    @DisplayName("find by id")
    void givenId_whenFindById_thenReturnDemand(){
        DemandRequestDto demandRequestDto=new DemandRequestDto();
        demandRequestDto.setRole("SA");
        demandRequestDto.setResourceName("resource");
        demandRequestDto.setReqId("D00001");
        DemandRequest demandRequest=new DemandRequest();
        demandRequest.setRole("SA");
        demandRequest.setResourceName("resource");
        demandRequest.setReqId(1L);
        Mockito.when(demandConverter.convertEntityToDto(demandRequest)).thenReturn(demandRequestDto);
        Mockito.when(demandRequestRepository.findById(1L)).thenReturn(Optional.of(demandRequest));
        DemandRequestDto result=demandRequestServiceImpl.findById("D00001");
        assertThat(result.getRole()).isEqualTo("SA");
    }

    @Test
    @DisplayName("search by fields")
    void givenDemand_whenSearchByField_thenReturnDemand(){
        DemandRequestDto demandRequestDto=new DemandRequestDto();
        demandRequestDto.setRole("SA");

        DemandRequest demandRequest=new DemandRequest();
        demandRequest.setRole("SA");
        demandRequest.setLevel("JUNIOR");

        DemandRequest demandRequest1=new DemandRequest();
        demandRequest1.setRole("SA");
        demandRequest1.setLevel("PRO");

        DemandRequestDto demandRequestDto1=new DemandRequestDto();
        demandRequestDto1.setRole("SA");
        demandRequestDto1.setLevel("JUNIOR");

        DemandRequestDto demandRequestDto2=new DemandRequestDto();
        demandRequestDto2.setRole("SA");
        demandRequestDto2.setLevel("PRO");

        List<DemandRequest> demandRequestList=Arrays.asList(demandRequest,demandRequest1);

        List<DemandRequestDto> demandRequestDtoList=Arrays.asList(demandRequestDto1,demandRequestDto2);


        Mockito.when(demandRequestRepository.findByField(demandRequestDto.getRole(),demandRequestDto.getTechnology(),demandRequestDto.getRequestedBy(),demandRequestDto.getStatus())).thenReturn(demandRequestList);
        Mockito.when(demandConverter.convertEntityToDtoList(demandRequestList)).thenReturn(demandRequestDtoList);
        List<DemandRequestDto> result=demandRequestServiceImpl.findRequestsByField(demandRequestDto);
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(1).getLevel()).isEqualTo("PRO");

    }

    @Test
    @DisplayName("add demand")
    void givenDemand_whenCreateDemand_thenReturnString(){
        DemandRequestDto demandRequestDto=new DemandRequestDto();
        DemandRequest demandRequest=new DemandRequest();
        demandRequestDto.setRole("SA");
        demandRequest.setRole("SA");
        demandRequestDto.setNoOfResources(1);
        demandRequest.setNoOfResources(1);
        demandRequest.setReqId(1L);
        demandRequestDto.setReqId("D00001");

        //Mockito.doNothing().when(validation).validateCreateDemandRequest(demandRequestDto);
        Mockito.when(demandConverter.convertDtoToEntity(demandRequestDto)).thenReturn(demandRequest);
        Mockito.when(demandRequestRepository.save(demandRequest)).thenReturn(demandRequest);
        String result=demandRequestServiceImpl.createDemandRequest(demandRequestDto);
        System.out.println(result);
        assertThat(result).isEqualTo(demandRequestDto.getReqId()+"   ");
    }

    @Test
    @DisplayName("create ref comm no exception")
    void givenDemand_whenCreateDemand_thenReturnRefCommNoException(){
        DemandRequestDto demandRequestDto1=new DemandRequestDto();
        demandRequestDto1.setRefCommNo("com.123.456.789");
        demandRequestDto1.setStatus("NEW");

        DemandRequestDto demandRequestDto=new DemandRequestDto();
        demandRequestDto.setRefCommNo("com.123.456.789");

        DemandRequest demandRequest=new DemandRequest();
        demandRequest.setStatus("new");
        List<DemandRequest> demandRequestList=Arrays.asList(demandRequest);

        Mockito.when(demandRequestRepository.findByRefAndStatus(demandRequestDto.getRefCommNo(),"cancelled")).thenReturn(demandRequestList);
        //Mockito.doNothing().when(validation).checkRefCommNo(demandRequestList);
        assertThrows(RefCommNoException.class,()->demandRequestServiceImpl.createDemandRequest(demandRequestDto));
    }


    @Test
    @DisplayName("update")
    void givenDemand_whenUpdateDemand_thenReturnString(){
        DemandRequest demandRequest1=new DemandRequest();
        demandRequest1.setReqId(13L);
        demandRequest1.setHireLoss(1);

        DemandRequestDto demandRequestDto=new DemandRequestDto();
        demandRequestDto.setResourceName("resource");
        demandRequestDto.setCommunicationToMandO("com.123.456.789");
        demandRequestDto.setHireLoss(2);
        demandRequestDto.setReqId("D00013");

        DemandRequest demandRequest=new DemandRequest();
        demandRequest.setResourceName("resource");
        demandRequest.setCommunicationToMandO("com.123.456.789");
        demandRequest.setHireLoss(2);
        demandRequest.setReqId(13L);




        Mockito.when(demandConverter.convertDtoToEntity(demandRequestDto)).thenReturn(demandRequest);
       Mockito.when(demandRequestRepository.findById(demandRequest.getReqId())).thenReturn(Optional.of(demandRequest));
      //Mockito.doNothing().when(validation).checkHireLoss(demandRequest.getHireLoss(),demandRequest);
        Mockito.when(demandRequestRepository.save(demandRequest)).thenReturn(demandRequest);
        Mockito.when(demandConverter.convertEntityToDto(demandRequest)).thenReturn(demandRequestDto);
        DemandRequestDto result=demandRequestServiceImpl.updateDemand(demandRequestDto);
        assertThat(result.getReqId()).isEqualTo("D00013");
        assertThat(result.getCommunicationToMandO()).isEqualTo("com.123.456.789");
    }

    @Test
    @DisplayName("update id not found")
    void givenDemand_whenUpdate_thenReturnException(){
        DemandRequestDto demandRequestDto=new DemandRequestDto();
        demandRequestDto.setReqId("D00001");
        //Mockito.doThrow(new NullPointerException()).when(demandRequestRepository.findById(1L));
        assertThrows(NullPointerException.class,()->demandRequestServiceImpl.updateDemand(demandRequestDto));
    }

    @Test
    @DisplayName("id not found exception")
    void givenId_whenFindById_thenReturnException(){
        assertThrows(IdNotFoundException.class,()->demandRequestServiceImpl.findById("D00005"));
    }



}
