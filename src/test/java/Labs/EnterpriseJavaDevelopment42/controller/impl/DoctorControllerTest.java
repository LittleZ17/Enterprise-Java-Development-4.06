package Labs.EnterpriseJavaDevelopment42.controller.impl;

import Labs.EnterpriseJavaDevelopment42.controller.dto.DoctorDepartmentDTO;
import Labs.EnterpriseJavaDevelopment42.controller.dto.DoctorStatusDTO;
import Labs.EnterpriseJavaDevelopment42.enums.Status;
import Labs.EnterpriseJavaDevelopment42.model.Doctor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class DoctorControllerTest {


    @Autowired
    private WebApplicationContext webApplicationContext;
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /* *********** TEST GET *********** */

    @Test
    void getAllDoctors_validRequest_allDoctors() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/doctors"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());

        String responseContent = mvcResult.getResponse().getContentAsString();

        assertTrue(responseContent.contains("name"));
        assertTrue(responseContent.contains("Fernando Herrera"));
        assertFalse(responseContent.contains("RANDOM"));
    }

    @Test
    void getDoctorById_validId_correctDoctor() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/doctors/156545"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());

        String responseContent = mvcResult.getResponse().getContentAsString();
        assertTrue(responseContent.contains("156545"));
        assertTrue(responseContent.contains("orthopaedic"));
        assertTrue(responseContent.contains("Paolo Rodriguez"));
        assertTrue(responseContent.contains("ON_CALL"));
    }

    @Test
    void getDoctorById_invalidId_BadRequest() throws Exception {

        mockMvc.perform(get("/doctors/abcabc").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void getDoctorsByStatus_validRequest_doctorsByStatus() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/doctors/status?status=ON"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());

        String responseContent = mvcResult.getResponse().getContentAsString();
        assertTrue(responseContent.contains("Bryan Rodriguez"));
        assertTrue(responseContent.contains("Maria Lin"));
        assertTrue(responseContent.contains("Sam Ortega"));
        assertTrue(responseContent.contains("ON"));
        assertFalse(responseContent.contains("OFF"));
    }

    @Test
    void getDoctorsByStatus_invalidRequest_BadRequest() throws Exception {

        mockMvc.perform(get("/doctors/status?status=1234").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void getDoctorsByDepartment_validRequest_doctorsByDepartment() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/doctors/department?department=cardiology"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());

        String responseContent = mvcResult.getResponse().getContentAsString();
        assertTrue(responseContent.contains("11111"));
        assertTrue(responseContent.contains("356712"));
        assertTrue(responseContent.contains("761527"));
        assertFalse(responseContent.contains("immunology"));
        assertFalse(responseContent.contains("pulmonary"));
    }

    @Test
    void getDoctorsByDepartment_invalidRequest_NoResultFounds() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/doctors/department?department=lololo").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        assertEquals("[]", responseContent);
    }

    /* *********** TEST POST *********** */

    @Test
    void saveDoctor_validDoctor_successful() throws Exception {

        Doctor doctor = new Doctor(12345, "cardiology", "Maria Ortiz", Status.valueOf("ON"));

        String body = objectMapper.writeValueAsString(doctor);

        mockMvc.perform(post("/doctors").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        /* CHECKING EXTRA

        MvcResult mvcResult =mockMvc.perform(get("/doctors/12345"))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
        String responseContent = mvcResult.getResponse().getContentAsString();

        assertTrue(responseContent.contains("Maria Ortiz"));

        mockMvc.perform(delete("/doctors/12345"))
                .andExpect(status().isOk())
                .andReturn();

         */
    }

    @Test
    void updateDoctorStatus_validDoctor_doctorUpdate() throws Exception {

        DoctorStatusDTO doctorStatusDTO = new DoctorStatusDTO(Status.ON_CALL);
        String body = objectMapper.writeValueAsString(doctorStatusDTO);

        mockMvc.perform(patch("/doctors/status/12345").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

            /* CHECKING EXTRA

            MvcResult mvcResult =mockMvc.perform(get("/doctors/12345"))
                .andExpect(status().isOk())
                .andReturn();

            System.out.println(mvcResult.getResponse().getContentAsString());
            String responseContent = mvcResult.getResponse().getContentAsString();

            assertTrue(responseContent.contains("Magdalena"));

            mockMvc.perform(delete("/doctors/12345"))
                .andExpect(status().isOk())
                .andReturn();*/
    }

    @Test
    void updateDoctorDepartment_validDoctor_doctorUpdateDepartment() throws Exception {
        DoctorDepartmentDTO doctorDepartmentDTO = new DoctorDepartmentDTO("immunology");
        String body = objectMapper.writeValueAsString(doctorDepartmentDTO);

        mockMvc.perform(patch("/doctors/department/12345").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        /* CHECKING EXTRA

            MvcResult mvcResult =mockMvc.perform(get("/doctors/12345"))
                .andExpect(status().isOk())
                .andReturn();

            System.out.println(mvcResult.getResponse().getContentAsString());
            String responseContent = mvcResult.getResponse().getContentAsString();

            assertTrue(responseContent.contains("immunology"));

            mockMvc.perform(delete("/doctors/12345"))
                .andExpect(status().isOk())
                .andReturn();*/
    }
}