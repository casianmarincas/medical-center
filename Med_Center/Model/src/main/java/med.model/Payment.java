package med.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="payments")
public class Payment implements Serializable {

    @Id
    @GeneratedValue(generator="inc-gen")
    @GenericGenerator(name="inc-gen", strategy = "increment")
    private int id;
    @ManyToOne
    @JoinColumn(name="location_id")
    private Location location;
    @ManyToOne
    @JoinColumn(name="treatment_id")
    private Treatment treatment;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name="person_id")
    private Person person;
    private double sum;

    public Payment() {
    }

    public Payment(LocalDateTime date, Location location, Treatment treatment, Person person, double sum) {
        this.date = date;
        this.treatment = treatment;
        this.location = location;
        this.person = person;
        this.sum = sum;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id == payment.id && Double.compare(payment.sum, sum) == 0 && location.equals(payment.location) && treatment.equals(payment.treatment) && date.equals(payment.date) && person.equals(payment.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location, treatment, date, person, sum);
    }
}
