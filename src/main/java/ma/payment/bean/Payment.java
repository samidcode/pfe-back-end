package ma.payment.bean;

import javax.persistence.*;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String date;
    private Integer montant;
    private String moisP;

    private String yearP;
    private String objet;

    @ManyToOne
    private Payeur payeur;

    @ManyToOne
    private Eleve eleve;

    public Payment(Integer id, String date, Integer montant, String moisP, String objet, Payeur payeur, Eleve eleve, String yearP) {
        this.id = id;
        this.date = date;
        this.montant = montant;
        this.moisP = moisP;
        this.objet = objet;
        this.payeur = payeur;
        this.eleve = eleve;
       // this.yearP = yearP;
    }

    public Payment() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
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