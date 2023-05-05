package Labs.EnterpriseJavaDevelopment42.controller.impl;

import Labs.EnterpriseJavaDevelopment42.enums.Status;
import Labs.EnterpriseJavaDevelopment42.model.Doctor;
import Labs.EnterpriseJavaDevelopment42.model.Patient;
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

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PatientControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getAllPatients_validRequest_allPatients() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());

        String responseContent = mvcResult.getResponse().getContentAsString();

        assertTrue(responseContent.contains("Marian Garcia"));
        assertTrue(responseContent.contains("Julia Dusterdieck"));
        assertFalse(responseContent.contains("RANDOM"));
        assertFalse(responseContent.contains("...."));
    }

    @Test
    void getPatientById_validId_correctPatient() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/patients/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());

        String responseContent = mvcResult.getResponse().getContentAsString();

        assertTrue(responseContent.contains("Mayerline"));
        assertTrue(responseContent.contains("1996-03-02"));
        assertTrue(responseContent.contains("564134"));
        assertFalse(responseContent.contains("HOLA"));
    }

    @Test
    void getPatientById_invalidId_BadRequest() throws Exception {

        mockMvc.perform(get("/patients/abcabc").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void getPatientsByBirthDay_validRequest_patientsByDate() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/patients/between-date-of-birth?date1=1990-01-01&date2=1992-12-31"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());

        String responseContent = mvcResult.getResponse().getContentAsString();
        assertTrue(responseContent.contains("Fernando Herrera"));
        assertTrue(responseContent.contains("1991-10-27"));
        assertTrue(responseContent.contains("564134"));
    }

    @Test
    void getPatientsByBirthDay_invalidRequest_BadRequest() throws Exception {

        mockMvc.perform(get("/patients/between-date-of-birth?date1=1991").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void getPatientsByDepartmentAdd_validRequest_patientsByDepartment() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/patients/doctor-department/cardiology"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());

        String responseContent = mvcResult.getResponse().getContentAsString();
        assertTrue(responseContent.contains("Julia Dusterdieck"));
        assertTrue(responseContent.contains("Steve McDuck"));
    }

    @Test
    void getPatientsDoctorOff_validRequest_patientsDoctorStatusOFF() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/patients/doctor-off"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());

        String responseContent = mvcResult.getResponse().getContentAsString();
        assertTrue(responseContent.contains("Marian Garcia"));
        assertTrue(responseContent.contains("Steve McDuck"));
    }

    @Test
    void savePatient_validPatient_successful() throws Exception {

        Doctor doctor = new Doctor (98761, "inmunology", "Marina Gala", Status.valueOf("OFF"));
        Patient patient = new Patient("Karla Vega", new Date(1991-12-01), doctor);

        String body = objectMapper.writeValueAsString(patient);

        mockMvc.perform(post("/patients").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

    }

    @Test
    void updatePatients_validPatient_patientUpdate() throws Exception {
        Patient patient = new Patient(1, "Zindy Martinez", new Date(1991-12-01));

        String body = objectMapper.writeValueAsString(patient);

        mockMvc.perform(put("/patients/1").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}