package ma.payment.ws;

import ma.payment.bean.Payment;
import ma.payment.exceptions.EntityNotFoundException;
import ma.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable int id) {
        Payment payment = paymentService.getPaymentById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with ID: " + id));
        return ResponseEntity.ok(payment);
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment createdPayment = paymentService.savePayment(payment);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable int id, @RequestBody Payment payment) {
        Payment existingPayment = paymentService.getPaymentById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with ID: " + id));
        payment.setId(id);
        Payment updatedPayment = paymentService.savePayment(payment);
        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable int id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/findPayment/{id}")
    public List<Payment> getPaymentsForEleve(@PathVariable int id) {
        return paymentService.getPaymentsForEleve(id);
    }
}

