package med.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="persons")
public class Person implements Serializable {

    @Id
    @GeneratedValue(generator="inc-gen")
    @GenericGenerator(name="inc-gen", strategy = "increment")
    private int id;
    private String name;
    private String cnp;

    public Person() {
    }

    public Person(String name, String cnp) {
        this.name = name;
        this.cnp = cnp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
