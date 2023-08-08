package ma.payment.bean;

import javax.persistence.*;
import java.util.List;

@Entity
public class Payeur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cin;
    private String nom;
    private String prenom;
    private Integer tele;
    private String mail;
    private String adresse;

    @OneToMany
    private List<Payment> payments;

    public Payeur(Integer id, String cin, String nom, String prenom, Integer tele, String mail, String adresse, List<Payment> payments) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.tele = tele;
        this.mail = mail;
        this.adresse = adresse;
        this.payments = payments;
    }

    public Payeur() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Integer getTele() {
        return tele;
    }

    public void setTele(Integer tele) {
        this.tele = tele;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}