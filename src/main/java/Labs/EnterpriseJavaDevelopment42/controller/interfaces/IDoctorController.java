package Labs.EnterpriseJavaDevelopment42.controller.interfaces;

import Labs.EnterpriseJavaDevelopment42.controller.dto.DoctorDepartmentDTO;
import Labs.EnterpriseJavaDevelopment42.controller.dto.DoctorStatusDTO;
import Labs.EnterpriseJavaDevelopment42.enums.Status;
import Labs.EnterpriseJavaDevelopment42.model.Doctor;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface IDoctorController {
    List<Doctor> getDoctors();
    List<Doctor> getDoctorsByStatus(Optional<Status> status);
    List<Doctor> getDoctorsByDepartment(Optional<String> department);
    Doctor getDoctor(Integer id);
    void saveDoctor(Doctor doctor);
    void updateDoctorStatus(DoctorStatusDTO doctorStatusDTO, Integer id);
    void updateDoctorDepartment( DoctorDepartmentDTO doctorDepartmentDTO, Integer id);

    }
