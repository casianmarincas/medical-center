package med.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="treatments")
public class Treatment implements Serializable {

    @Id
    @GeneratedValue(generator="inc-gen")
    @GenericGenerator(name="inc-gen", strategy = "increment")
    private int id;
    private double cost;
    private double time;
    private int maxPatients;

    public Treatment() {
    }

    public Treatment(double cost, double time) {
        this.cost = cost;
        this.time = time;

    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
