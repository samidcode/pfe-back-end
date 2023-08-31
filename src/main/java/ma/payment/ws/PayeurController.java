package ma.payment.ws;

import ma.payment.bean.Eleve;
import ma.payment.bean.Payeur;
import ma.payment.exceptions.EntityNotFoundException;
import ma.payment.service.PayeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payeurs")
public class PayeurController {
    private final PayeurService payeurService;

    @Autowired
    public PayeurController(PayeurService payeurService) {
        this.payeurService = payeurService;
    }

    @GetMapping
    public ResponseEntity<List<Payeur>> getAllPayeurs() {
        List<Payeur> payeurs = payeurService.getAllPayeurs();
        return ResponseEntity.ok(payeurs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payeur> getPayeurById(@PathVariable int id) {
        Payeur payeur = payeurService.getPayeurById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payeur not found with ID: " + id));
        return ResponseEntity.ok(payeur);
    }

    @PostMapping
    public ResponseEntity<Payeur> createPayeur(@RequestBody Payeur payeur) {
        Payeur createdPayeur = payeurService.savePayeur(payeur);
        return new ResponseEntity<>(createdPayeur, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payeur> updatePayeur(@PathVariable int id, @RequestBody Payeur payeur) {
        Payeur existingPayeur = payeurService.getPayeurById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payeur not found with ID: " + id));
        payeur.setId(id);
        Payeur updatedPayeur = payeurService.savePayeur(payeur);
        return ResponseEntity.ok(updatedPayeur);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayeur(@PathVariable int id) {
        payeurService.deletePayeur(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/autocomplet")
    public ResponseEntity<List<Payeur>> createPayeur(@RequestBody String cin) {
        List<Payeur> payeurs = payeurService.autoComplet(cin);
        return new ResponseEntity<>(payeurs, HttpStatus.ACCEPTED);
    }


    @GetMapping("/payeurpagination")
    public ResponseEntity<Page<Payeur>> getStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<Payeur> students = payeurService.payeurPagination(page,size);
        return ResponseEntity.ok(students);
    }


    @GetMapping("/findelevesbypayeur/{id}")
    public    ResponseEntity<List<Eleve>> findElevesById (@PathVariable int id) {
        List<Eleve> Eleves = payeurService.findElevesById(id);

        return ResponseEntity.ok(Eleves);
    }
}

