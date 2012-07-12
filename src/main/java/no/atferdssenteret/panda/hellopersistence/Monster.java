package no.atferdssenteret.panda.hellopersistence;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Monster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    
    @OneToMany(mappedBy = "captor", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<Cat> cats = new HashSet<Cat>();

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

    public Set<Cat> getCats() {
	return cats;
    }

    public void setCats(Set<Cat> cats) {
	this.cats = cats;
    }
    
    public void stealCat(Cat cat) {
	cats.add(cat);
	cat.setCaptor(this);
    }
    
    public void eat(Cat cat) {
	cats.remove(cat);
    }
}
