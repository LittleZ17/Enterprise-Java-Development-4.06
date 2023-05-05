package Labs.EnterpriseJavaDevelopment42.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer patientId;
    private String name;
    @Temporal(TemporalType.DATE) //aplicarlo sin hora
    private Date dateOfBirth;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "admitted_by")
    private Doctor admittedBy;

    public Patient( String name, Date dateOfBirth, Doctor admittedBy) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.admittedBy = admittedBy;
    }

    public Patient(String name, Date dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public Patient(Integer patientId, String name, Date dateOfBirth) {
        this.patientId = patientId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public Patient(int i, String karlaVega, Date date) {
    }

    public void setPatient(Integer id) {
    }
}
