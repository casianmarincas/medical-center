package med.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
@Entity
@Table(name="payments")
public class Payment implements Serializable {

    @Id
    @GeneratedValue(generator="inc-gen")
    @GenericGenerator(name="inc-gen", strategy = "increment")
    private int id;
    private LocalDateTime date;
    private String cnp;
    private double sum;

    public Payment() {
    }

    public Payment(LocalDateTime date, String cnp, double sum) {
        this.date = date;
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
}
