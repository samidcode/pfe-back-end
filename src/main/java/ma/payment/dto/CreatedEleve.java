package ma.payment.dto;

import ma.payment.bean.Eleve;
import ma.payment.bean.Payment;

public class CreatedEleve {

    private Eleve eleve;
    private Payment payment;



    public  CreatedEleve(Eleve eleve, Payment payment) {
        this.eleve = eleve;
        this.payment = payment;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public Payment getPayment() {
        return payment;
    }
}
