package med.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="treatment_location")
public class TreatmentLocation implements Serializable {

    @Id
    @GeneratedValue(generator="inc-gen")
    @GenericGenerator(name="inc-gen", strategy = "increment")
    private int id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="treatment_id")

    private Treatment treatment;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="location_id")
    private Location location;
    private int nrMax;

    public TreatmentLocation() {

    }

    public TreatmentLocation(Treatment treatment, Location location, int nrMax) {
        this.treatment = treatment;
        this.location = location;
        this.nrMax = nrMax;
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

    public int getNrMax() {
        return nrMax;
    }

    public void setNrMax(int nrMax) {
        this.nrMax = nrMax;
    }
}
