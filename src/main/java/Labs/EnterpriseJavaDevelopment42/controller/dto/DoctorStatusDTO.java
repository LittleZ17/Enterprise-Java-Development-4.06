package Labs.EnterpriseJavaDevelopment42.controller.dto;

import Labs.EnterpriseJavaDevelopment42.enums.Status;

public class DoctorStatusDTO {
    private Status status;

    public DoctorStatusDTO() {
    }

    public DoctorStatusDTO(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
