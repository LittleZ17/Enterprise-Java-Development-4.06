package Labs.EnterpriseJavaDevelopment42.controller.dto;

public class DoctorDepartmentDTO {

    private String department;

    public DoctorDepartmentDTO() {
    }
    public DoctorDepartmentDTO(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }
}
