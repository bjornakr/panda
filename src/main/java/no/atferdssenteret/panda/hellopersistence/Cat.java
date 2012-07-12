package no.atferdssenteret.panda.hellopersistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Cat {
    public enum Colors {BLACK, BROWN, GREEN, WHITE}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private double weight;
    private Colors color;
    private Monster captor;
    
    public long getId() {
	return id;
    }
    
    public void setId(long id) {
	this.id = id;
    }
    
    public String getName() {
	return name;
    }
    
    public void setName(String name) {
	this.name = name;
    }
    
    public double getWeight() {
	return weight;
    }
    
    public void setWeight(double weight) {
	this.weight = weight;
    }

    public Colors getColor() {
	return color;
    }

    public void setColor(Colors color) {
	this.color = color;
    }
    
    public void setCaptor(Monster captor) {
	this.captor = captor;
    }
    
    public Monster getCaptor() {
	return captor;
    }

}
