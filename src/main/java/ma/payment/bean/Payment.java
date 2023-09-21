package ma.payment.bean;


import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer montant;
    private String moisP;

    private String yearP;
    private String objet;
    private Date dateDeCreation;


    @ManyToOne
    private Payeur payeur;

    @ManyToOne
    private Eleve eleve;

    public Payment(Integer id, Integer montant, String moisP, String  objet, Payeur payeur, Eleve eleve, String yearP ,  Date dateDeCreation) {
        this.id = id;
        this.montant = montant;
        this.moisP = moisP;
        this.objet = objet;
        this.payeur = payeur;
        this.eleve = eleve;
        this.yearP = yearP;
        this.dateDeCreation = dateDeCreation;
    }

    public Payment() {

    }
    public Date getDateDeCreation() {
        return dateDeCreation;
    }

    public void setDateDeCreation(Date dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMontant() {
        return montant;
    }

    public void setMontant(Integer montant) {
        this.montant = montant;
    }

    public String getMoisP() {
        return moisP;
    }
    public String getYearP() {
        return yearP;
    }
    public void setMoisP(String moisP) {
        this.moisP = moisP;
    }
    public void setYearP(String yearP) {
        this.yearP = yearP;
    }
    public String  getObjet() {
        return objet;
    }

    public void setObjet(String  objet) {
        this.objet = objet;
    }

    public Payeur getPayeur() {
        return payeur;
    }

    public void setPayeur(Payeur payeur) {
        this.payeur = payeur;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }
}