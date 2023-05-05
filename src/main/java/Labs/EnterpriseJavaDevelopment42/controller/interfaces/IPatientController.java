package Labs.EnterpriseJavaDevelopment42.controller.interfaces;

import Labs.EnterpriseJavaDevelopment42.model.Patient;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IPatientController {

    List<Patient> getPatients();
    Optional<Patient> getPatient(Integer id);
    List<Patient> getPatientsByBirthDay(Date date1, Date date2);
    List<Patient> getPatientsByDepartmentAdd(String department);
    List<Patient> getPatientsDoctorOff();
    void savePatient(Patient patient);
    void updatePatients( Patient patient,  Integer id);


    }
