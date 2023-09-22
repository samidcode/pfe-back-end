package ma.payment.ws;
import ma.payment.bean.Eleve;
import ma.payment.bean.Payment;
import ma.payment.dto.CreatedEleve;
import ma.payment.dto.EleveWithStatusDTO;
import ma.payment.exceptions.EntityNotFoundException;
import ma.payment.service.EleveService;
import ma.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/eleves")
public class EleveController {
    private final EleveService eleveService;
    private final PaymentService paymentService;
    @Autowired
    public EleveController(EleveService eleveService, PaymentService paymentService) {
        this.eleveService = eleveService;
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<Eleve> getAllEleves() {

        return eleveService.getAllEleves();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Eleve> getEleveById(@PathVariable int id) {
        Eleve eleve = eleveService.getEleveById(id)
                .orElseThrow(() -> new EntityNotFoundException("Eleve not found with ID: " + id));
        return ResponseEntity.ok(eleve);
    }

    @PostMapping
    public ResponseEntity<CreatedEleve> createEleve(@RequestBody Eleve e, @RequestParam("inscriptionFrais") int inscriptionFrai, @RequestParam("inscriptionAnnee") String inscriptionAnnee) throws IOException {

        Payment p = new Payment();
        Payment payment = null;
        if (Objects.isNull(e.getId())){

            e.setDateDeCreation(new Date());
            p.setEleve(e);
            p.setObjet("Inscription");
            p.setMontant(inscriptionFrai);
            p.setPayeur(e.getPayeur());
            p.setYearP(inscriptionAnnee);
            p.setDateDeCreation(new Date());
        }

        Eleve createdEleve = eleveService.saveEleve(e);

if(Objects.nonNull(p)){
    payment = paymentService.savePayment(p);

}


        CreatedEleve createdObjects = new CreatedEleve(createdEleve, payment);

        return new ResponseEntity<>(createdObjects, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Eleve> updateEleve(@PathVariable int id, @RequestBody Eleve eleve) {
       eleveService.getEleveById(id)
                .orElseThrow(() -> new EntityNotFoundException("Eleve not found with ID: " + id));
        eleve.setId(id);
        Eleve updatedEleve = eleveService.saveEleve(eleve);
        return ResponseEntity.ok(updatedEleve);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEleve(@PathVariable int id) {
        eleveService.deleteEleve(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/eleveSearch")
    public ResponseEntity<List<EleveWithStatusDTO>> searchContacts(@RequestParam String keyword) {
        List<EleveWithStatusDTO> eleves = eleveService.searchEleve(keyword);
        return ResponseEntity.ok(eleves);
    }


    @GetMapping("/elevePagination")
    public ResponseEntity<Page<EleveWithStatusDTO>> getPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<EleveWithStatusDTO> students = eleveService.elevePagination(page,size);
        return ResponseEntity.ok(students);
    }
}
