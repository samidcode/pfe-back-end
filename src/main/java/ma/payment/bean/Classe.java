package ma.payment.bean;

import javax.persistence.*;
import java.util.List;

@Entity
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    private String niveau;
    private String dateDeCreation;

    @OneToMany
    private List<Eleve> eleves;

    public Classe(Integer id, String nom, String niveau, String dateDeCreation, List<Eleve> eleves) {
        this.id = id;
        this.nom = nom;
        this.niveau = niveau;
        this.dateDeCreation = dateDeCreation;
        this.eleves = eleves;
    }

    public Classe() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getDateDeCreation() {
        return dateDeCreation;
    }

    public void setDateDeCreation(String dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }

    public List<Eleve> getEleves() {
        return eleves;
    }

    public void setEleves(List<Eleve> eleves) {
        this.eleves = eleves;
    }
}
