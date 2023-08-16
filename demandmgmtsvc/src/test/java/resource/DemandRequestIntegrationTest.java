package resource;

import com.colruyt.DemandmgmtsvcApplication;
import com.colruyt.controller.DemandRequestController;
import com.colruyt.converter.DemandConverter;
import com.colruyt.entity.DemandRequest;
import com.colruyt.repository.DemandRequestRepository;
import com.colruyt.services.DemandRequestService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ContextConfiguration(classes = DemandmgmtsvcApplication.class)
@AutoConfigureMockMvc
@Transactional
public class DemandRequestIntegrationTest {


    @Autowired
    private DemandRequestRepository demandRequestRepository ;

    @Autowired
    DemandConverter demandConverter;

   @Autowired
   DemandRequestService demandRequestService;

    @Autowired
    private MockMvc mockMvc;


//    @SneakyThrows
//    @Test
//    void givenNone_whenFindAll_thenReturnList(){
//        List<DemandRequest> demandRequestList=new ArrayList<>();
//        DemandRequest demandRequest=new DemandRequest();
//        demandRequest.setRole("SA");
//
//        DemandRequest demandRequest1=new DemandRequest();
//        demandRequest1.setRole("PM");
//
//        demandRequestList.add(demandRequest);
//        demandRequestList.add(demandRequest1);
//        for(DemandRequest demand:demandRequestList){
//            demandRequestService.createDemandRequest(demand);
//        }
//
//        mockMvc.perform(post("/demandmgmt/all").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").exists())
//                .andExpect(jsonPath("$[0].Role").value("SA"));
//    }


    @SneakyThrows
    @Test
    void givenId_whenFindById_thenReturnDemand(){
        DemandRequest demandRequest=new DemandRequest();
        demandRequest.setRole("SA");
        demandRequest.setResourceName("resource");
        DemandRequest demandRequest1=demandRequestRepository.save(demandRequest);
        String id=String.format("D%05d",demandRequest.getReqId());
        String url="/demandmgmt/get-by-id/"+id;

        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.role").value("SA"));
    }



}
