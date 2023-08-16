package resource;

import com.colruyt.Validation;
import com.colruyt.dto.DemandRequestDto;
import com.colruyt.dto.DemandRequestedByDto;
import com.colruyt.dto.DemandRoleDto;
import com.colruyt.dto.DemandTechnologyDto;
import com.colruyt.controller.DemandRequestController;
import com.colruyt.exception.DemandExceptionHandler;
import com.colruyt.exception.IdNotFoundException;
import com.colruyt.exception.TechnoloyMandatoryRoleSE;
import com.colruyt.exception.UpdateException;
import com.colruyt.services.DemandRequestService;
import com.colruyt.services.DemandRequestedByService;
import com.colruyt.services.DemandRoleService;
import com.colruyt.services.DemandTechnologyService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class DemandRequestControllerUnitTest {
    @InjectMocks
    DemandRequestController demandRequestController;

    @Mock
    DemandRoleService demandRoleService;

    @Mock
    DemandTechnologyService demandTechnologyService;
    @Mock
    DemandRequestedByService demandRequestedByService;

    @Mock
    Validation validation;


    @Mock
    DemandRequestService demandRequestService;


//    @AfterEach
//    void afterEach(){
//        Mockito.verifyNoMoreInteractions(demandRequestService);
//    }

    @Test
    void givenNone_whenFindAll_thenReturnList(){
        DemandRequestDto demandRequestDto=new DemandRequestDto();
        demandRequestDto.setLevel("Junior");
        List<DemandRequestDto> demandRequestList=new ArrayList<>();
        demandRequestList.add(demandRequestDto);
        Mockito.when(demandRequestService.findAll()).thenReturn(demandRequestList);
        List<DemandRequestDto> demandList=demandRequestController.findAllDemandRequests();
        assertThat(demandList.size()).isEqualTo(1);
        assertThat(demandList.get(0).getLevel().equals("Junior"));
    }


    @Test
    void givenDemand_whenCreateDemand_thenReturnString(){
        DemandRequestDto demandRequestDto=new DemandRequestDto();
        demandRequestDto.setRole("SE");
        demandRequestDto.setTechnology("test");
        demandRequestDto.setRequestedBy("Digital Factory");
        demandRequestDto.setNoOfResources(2);
        //Mockito.doNothing().when(validation).validateCreateDemandRequest(demandRequestDto);
        Mockito.when(demandRequestService.createDemandRequest(demandRequestDto)).thenReturn(demandRequestDto.getReqId());
        String result=demandRequestController.addDemandRequest(demandRequestDto);
        assertThat(result).isEqualTo("Demand Request Created with id : null");
    }

    @SneakyThrows
    @Test
    void givenDemand_whenCreateDemand_thenReturnRoleException(){
        DemandRequestDto demandRequestDto=new DemandRequestDto();
        demandRequestDto.setRole(null);
        demandRequestDto.setRefCommNo("com.123.345.567");
        //MethodArgumentNotValidException methodArgumentNotValidException=mock(MethodArgumentNotValidException.class);
        //Mockito.doThrow(MethodArgumentNotValidException.class).when(demandExceptionHandler).CreateDemandRequestValidation(methodArgumentNotValidException);
        //Mockito.doThrow(MethodArgumentNotValidException.class).when(demandRequestController).addDemandRequest(demandRequestDto);
        assertThrows(MethodArgumentNotValidException.class,()-> demandRequestController.addDemandRequest(demandRequestDto));
    }

    @Test
    void givenDemand_whenCreateDemand_thenReturnTechnologyMandatoryException(){
        DemandRequestDto demandRequestDto=new DemandRequestDto();
        demandRequestDto.setRole("SE");
        demandRequestDto.setRequestedBy("IAAS");
        demandRequestDto.setNoOfResources(1);
        demandRequestDto.setRefCommNo("com.123.345.567");
        assertThrows(TechnoloyMandatoryRoleSE.class,()-> demandRequestController.addDemandRequest(demandRequestDto));
    }

    @Test
    void givenDemand_whenSearch_thenReturnList(){
        DemandRequestDto demandRequestDto1=new DemandRequestDto();
        demandRequestDto1.setRole("SA");
        demandRequestDto1.setRequestedBy("Digital Factory");

        DemandRequestDto demandRequestDto2=new DemandRequestDto();
        demandRequestDto2.setRole("SA");
        demandRequestDto2.setRequestedBy("Medical");

        List<DemandRequestDto> demandRequestDtoList=new ArrayList<>();
        demandRequestDtoList.add(demandRequestDto1);
        demandRequestDtoList.add(demandRequestDto2);

        DemandRequestDto demandRequestDto3=new DemandRequestDto();
        demandRequestDto3.setRole("SA");

        Mockito.when(demandRequestService.findRequestsByField(demandRequestDto3)).thenReturn(demandRequestDtoList);
        List<DemandRequestDto> demandRequestDtoList1=demandRequestController.findDemandRequestByFields(demandRequestDto3);
        assertThat(demandRequestDtoList1.size()).isEqualTo(2);
        assertThat(demandRequestDtoList1.get(0).getRequestedBy()).isEqualTo("Digital Factory");
        assertThat(demandRequestDtoList1.get(1).getRequestedBy()).isEqualTo("Medical");

    }


    @Test
    @DisplayName("technology mandatory Exception when role SE ")
    void givenDemand_whenSearch_thenReturnTechnologyMandatoryException(){
        DemandRequestDto demandRequestDto3=new DemandRequestDto();
        demandRequestDto3.setRole("SE");
        assertThrows(TechnoloyMandatoryRoleSE.class,()->demandRequestController.findDemandRequestByFields(demandRequestDto3));
    }

    @Test
    @DisplayName("find by id")
    void givenId_whenGetById_thenReturnDemandRequest(){
        DemandRequestDto demandRequestDto=new DemandRequestDto();
        demandRequestDto.setRole("SA");
        demandRequestDto.setRequestedBy("Digital Factory");
        demandRequestDto.setReqId("D00187");
        Mockito.when(demandRequestService.findById("D00187")).thenReturn(demandRequestDto);
        DemandRequestDto result=demandRequestController.findDemandById("D00187");
        assertThat(result.getRole()).isEqualTo("SA");
        assertThat(result.getRequestedBy()).isEqualTo("Digital Factory");
    }

    @Test
    @DisplayName("id not found Exception")
    void givenId_whenGetById_thenReturnException(){
        //Mockito.doThrow(new NullPointerException()).when(demandRequestService).findById("D00054");
        Mockito.doThrow(IdNotFoundException.class).when(demandRequestService).findById("D00054");
        assertThrows(IdNotFoundException.class,()->demandRequestController.findDemandById("D00054"));
        Mockito.verify(demandRequestService).findById("D00054");
    }


    @Test
    @DisplayName("find all")
    void givenNone_whenFindAllRoles_thenReturnList(){
        List<String> roleList= Arrays.asList("SA","SE","PM","SITA");
        Mockito.when(demandRoleService.getAllRoles()).thenReturn(roleList);
        List<String> result=demandRequestController.getDemandRequestRoles();
        assertThat(result.size()).isEqualTo(4);
        assertThat(result.contains("SA"));
        assertThat(result.get(3)).isEqualTo("SITA");
    }

    @Test
    @DisplayName("find all technologies")
    void givenNone_whenFindAllTechnologies_thenReturnList(){
        List<String> technologyList= Arrays.asList("Java","Mainframe","Others");
        Mockito.when(demandTechnologyService.getAllTechnologies()).thenReturn(technologyList);
        List<String> result=demandRequestController.getDemandTechnologies();
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.contains("Java"));
        assertThat(result.get(2)).isEqualTo("Others");
    }

    @Test
    @DisplayName("find all requested by")
    void givenNone_whenFindAllRequestedBy_thenReturnList(){
        List<String> requestedByList= Arrays.asList("SAP","IAAS");
        Mockito.when(demandRequestedByService.getAllRequestedBy()).thenReturn(requestedByList);
        List<String> result=demandRequestController.getRequestedBy();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.contains("SAP"));
        assertThat(result.get(1)).isEqualTo("IAAS");
    }

    @Test
    @DisplayName("add role")
    void givenRoleDto_whenAddRole_thenReturnString(){
        DemandRoleDto demandRoleDto=new DemandRoleDto();
        demandRoleDto.setRole("SA");
        demandRoleDto.setRoleId(1);
        Mockito.when(demandRoleService.createRole(demandRoleDto)).thenReturn(demandRoleDto.getRole());
        String result=demandRequestController.createDemandRole(demandRoleDto);
        assertThat(result).isEqualTo("role added successfully with id : SA");
    }

    @Test
    @DisplayName("add technology")
    void givenTechnologyDto_whenAddTechnology_thenReturnString(){
        DemandTechnologyDto demandTechnologyDto=new DemandTechnologyDto();
        demandTechnologyDto.setTechnology("Java");
        demandTechnologyDto.setTechnologyId(1);
        Mockito.when(demandTechnologyService.createTechnology(demandTechnologyDto)).thenReturn(demandTechnologyDto.getTechnology());
        String result=demandRequestController.createDemandTechnology(demandTechnologyDto);
        assertThat(result).isEqualTo("technology added successfully with id : Java");
    }

    @Test
    @DisplayName("add requestedBy")
    void givenRequestedByDto_whenAddRequestedBy_thenReturnString(){
        DemandRequestedByDto demandRequestedByDto=new DemandRequestedByDto();
        demandRequestedByDto.setRequestedBy("IAAS");
        demandRequestedByDto.setRequestedByid(2);
        Mockito.when(demandRequestedByService.createRequestedBy(demandRequestedByDto)).thenReturn(demandRequestedByDto.getRequestedBy());
        String result=demandRequestController.createDemandRequestedBy(demandRequestedByDto);
        assertThat(result).isEqualTo("requested by data added successfully with id : IAAS");
    }

    LocalDate offered= LocalDate.of(2023, 3, 1);

    @Test
    @DisplayName("update")
    void givenDemandRequestDto_whenUpdate_thenReturnString(){
        DemandRequestDto demandRequestDto1=new DemandRequestDto();
        demandRequestDto1.setRole("SA");
        demandRequestDto1.setReqId("D00001");
        demandRequestDto1.setResourceName("resource");
        demandRequestDto1.setStatus("Offered");
        demandRequestDto1.setRequestedDate(offered);
        demandRequestDto1.setCommunicationToMandO("com.123.456.778");
        demandRequestDto1.setOfferedDate(offered);
        demandRequestDto1.setDojOfferLetter(offered);
        demandRequestDto1.setRequestToOffer(10);
        demandRequestDto1.setRequestToJoining(15);

        Mockito.when(demandRequestService.updateDemand(demandRequestDto1)).thenReturn(demandRequestDto1);
        String result=demandRequestController.updateDemandRequest(demandRequestDto1);
        assertThat(result).isEqualTo("Demand Request D00001 updated successfully");
    }

    @Test
    @DisplayName("resource mandatory")
    void givenDemandRequestDto_whenUpdate_thenReturnResourceMandatoryException(){
        DemandRequestDto demandRequestDto=new DemandRequestDto();
        demandRequestDto.setResourceName(null);
        assertThrows(UpdateException.class,()->demandRequestController.updateDemandRequest(demandRequestDto));
    }

    @Test
    @DisplayName("Communication to Mando mandatory")
    void givenDemandRequestDto_whenUpdate_thenReturnCommunicationMandOMandatoryException(){
        DemandRequestDto demandRequestDto=new DemandRequestDto();
        demandRequestDto.setCommunicationToMandO(null);
        assertThrows(UpdateException.class,()->demandRequestController.updateDemandRequest(demandRequestDto));
    }

    @Test
    @DisplayName("Offered date mandatory Exception")
    void givenDemandRequestDto_whenUpdate_thenReturnOfferedDateMandatoryException(){
        DemandRequestDto demandRequestDto=new DemandRequestDto();
        demandRequestDto.setStatus("Offered");
        demandRequestDto.setOfferedDate(null);
        assertThrows(UpdateException.class,()->demandRequestController.updateDemandRequest(demandRequestDto));
    }

    @Test
    @DisplayName("Doj Offer Letter is mandatory Exception")
    void givenDemandRequestDto_whenUpdate_thenReturnDojOfferMandatoryException(){
        DemandRequestDto demandRequestDto=new DemandRequestDto();
        demandRequestDto.setStatus("Offered");
        demandRequestDto.setDojOfferLetter(null);
        assertThrows(UpdateException.class,()->demandRequestController.updateDemandRequest(demandRequestDto));
    }

    @Test
    @DisplayName("Doj Offer Letter is mandatory Exception")
    void givenDemandRequestDto_whenUpdate_thenReturnRDojMandatoryException(){
        DemandRequestDto demandRequestDto=new DemandRequestDto();
        demandRequestDto.setStatus("Offered");
        demandRequestDto.setOfferedDate(offered);
        demandRequestDto.setDojOfferLetter(null);
        assertThrows(UpdateException.class,()->demandRequestController.updateDemandRequest(demandRequestDto));
    }

    @Test
    @DisplayName("Actual Joining date mandatory Exception")
    void givenDemandRequestDto_whenUpdate_thenReturnActualJoinDateMandatoryException(){
        DemandRequestDto demandRequestDto=new DemandRequestDto();
        demandRequestDto.setStatus("Recruited");
        demandRequestDto.setActualJoiningDate(null);
        assertThrows(UpdateException.class,()->demandRequestController.updateDemandRequest(demandRequestDto));
    }

    @Test
    @DisplayName("Request To Offer mandatory Exception")
    void givenDemandRequestDto_whenUpdate_thenReturnRequestToOfferMandatoryException(){
        DemandRequestDto demandRequestDto=new DemandRequestDto();
        demandRequestDto.setRequestedDate(offered);
        demandRequestDto.setOfferedDate(offered);
        assertThrows(UpdateException.class,()->demandRequestController.updateDemandRequest(demandRequestDto));
    }

    @Test
    @DisplayName("Request To Joining mandatory Exception")
    void givenDemandRequestDto_whenUpdate_thenReturnRequestToJoiningMandatoryException(){
        DemandRequestDto demandRequestDto=new DemandRequestDto();
        demandRequestDto.setRequestedDate(offered);
        demandRequestDto.setDojOfferLetter(offered);
        //demandRequestDto.getRequestToJoining(null);
        assertThrows(UpdateException.class,()->demandRequestController.updateDemandRequest(demandRequestDto));
    }












}
