package ma.payment.dto;

import ma.payment.bean.Classe;
import ma.payment.bean.Payeur;

public class EleveWithStatusDTO {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdMassar() {
        return idMassar;
    }

    public void setIdMassar(String idMassar) {
        this.idMassar = idMassar;
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

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Payeur getPayeur() {
        return payeur;
    }

    public void setPayeur(Payeur payeur) {
        this.payeur = payeur;
    }

    public boolean isStatue() {
        return statue;
    }

    public void setStatue(boolean statue) {
        this.statue = statue;
    }

    private String idMassar;
    private String nom;
    private String prenom;
    private String dateNaissance;

    public String getDateDeCreation() {
        return dateDeCreation;
    }

    public void setDateDeCreation(String dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }

    private String image;
    private String imageType;
    private Classe classe;
    private Payeur payeur;
    private String dateDeCreation;

    private boolean statue; // Assuming "statue" is a boolean attribute

    public EleveWithStatusDTO(Integer id, String idMassar, String nom, String prenom, String dateNaissance, String image, String imageType, Classe classe, Payeur payeur, boolean statue,String dateDeCreation) {
        this.id = id;
        this.idMassar = idMassar;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.image = image;
        this.imageType = imageType;
        this.classe = classe;
        this.payeur = payeur;
        this.statue = statue;
        this.dateDeCreation =dateDeCreation;
    }

}
