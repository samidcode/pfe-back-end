package ma.payment.ws;

import ma.payment.bean.Classe;
import ma.payment.bean.Payment;
import ma.payment.exceptions.EntityNotFoundException;
import ma.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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


        if (Objects.isNull(payment.getId())){

           payment.setDateDeCreation(new Date());
        }

        Payment createdPayment = paymentService.savePayment(payment);

        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable int id, @RequestBody Payment payment) {
       paymentService.getPaymentById(id)
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

    @GetMapping("/{eleveId}/paymentStatus")
    public ResponseEntity<Boolean> checkPaymentStatus(@PathVariable Integer eleveId) {

        boolean paidLastMonth = paymentService.hasPaidForLastMonth(eleveId);
        return ResponseEntity.ok(paidLastMonth);
    }
    @GetMapping("/paymentPagination")
    public ResponseEntity<Page<Payment>> getStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<Payment> payments = paymentService.paymentPagination(page,size);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/find")
    public ResponseEntity<Payment> findPayment(
            @RequestParam String moisP,
            @RequestParam String yearP,
            @RequestParam Integer eleveId,
            @RequestParam String objet
    ) {
        Optional<Payment> payment = paymentService.getPaymentByMonth(moisP, yearP, eleveId, objet);
        System.out.println("===============================================> "+moisP);
        System.out.println("===============================================> "+yearP);
        System.out.println("===============================================> "+eleveId);

        System.out.println("===============================================> "+objet);
        System.out.println("===============================================> "+payment.toString());

        if (payment.isPresent()) {
            return new ResponseEntity<>(payment.get(), HttpStatus.OK);
        } else {
            return null;
        }
    }
    @GetMapping("/paymentSearch")
    public List<Payment> searchClasses(@RequestParam String keyword) {
        // Call the service to perform the search
        return paymentService.searchPayment(keyword);
    }
}

