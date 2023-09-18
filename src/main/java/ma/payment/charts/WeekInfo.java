package ma.payment.charts;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeekInfo {
    @JsonProperty("new-payment")
    private int newPayment;

    @JsonProperty("montant-totale")
    private int montantTotale;

    @JsonProperty("totale-payant-eleve")
    private int totalePayantEleve;

    public int getNewPayment() {
        return newPayment;
    }

    public void setNewPayment(int newPayment) {
        this.newPayment = newPayment;
    }

    public int getMontantTotale() {
        return montantTotale;
    }

    public void setMontantTotale(int montantTotale) {
        this.montantTotale = montantTotale;
    }

    public int getTotalePayantEleve() {
        return totalePayantEleve;
    }

    public void setTotalePayantEleve(int totalePayantEleve) {
        this.totalePayantEleve = totalePayantEleve;
    }
}

