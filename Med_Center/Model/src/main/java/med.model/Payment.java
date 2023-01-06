package med.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
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
    private String cnp;
    private double sum;

    public Payment() {
    }

    public Payment(LocalDateTime date, Location location, Treatment treatment, String cnp, double sum) {
        this.date = date;
        this.treatment = treatment;
        this.location = location;
        this.cnp = cnp;
        this.sum = sum;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
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
}
