package med.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.cglib.core.Local;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="appointments")
public class Appointment implements Serializable {

    @Id
    @GeneratedValue(generator="inc-gen")
    @GenericGenerator(name="inc-gen", strategy = "increment")
    private int id;

    @ManyToOne
    @JoinColumn(name="person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name="treatment_id")
    private Treatment treatment;

    @ManyToOne
    @JoinColumn(name="location_id")
    private Location location;
    private LocalDateTime treatmentDateTime;
    private LocalDateTime appointmentDateTime;

    public Appointment() {

    }

    public Appointment(Person person, Treatment treatment, Location location, LocalDateTime treatmentDateTime) {
        this.person = person;
        this.treatment = treatment;
        this.location = location;
        this.treatmentDateTime = treatmentDateTime;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDateTime getTreatmentDateTime() {
        return treatmentDateTime;
    }

    public void setTreatmentDateTime(LocalDateTime treatmentDateTime) {
        this.treatmentDateTime = treatmentDateTime;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", person=" + person.getId() +
                ", treatment=" + treatment.getId() +
                ", location=" + location.getId() +
                ", treatmentDateTime=" + treatmentDateTime +
                ", appointmentDateTime=" + appointmentDateTime +
                '}';
    }
}
