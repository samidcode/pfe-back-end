package ma.payment.enums;

public enum PaymentObject {

    MONTHLY("Monthly Payment"),
    REGISTRATION("Registration Fee"),
    TRANSPORTATION("Transportation Fee");

    private final String description;

    PaymentObject(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
