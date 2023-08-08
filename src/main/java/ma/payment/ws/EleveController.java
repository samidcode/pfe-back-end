package ma.payment.ws;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.payment.bean.Eleve;
import ma.payment.exceptions.EntityNotFoundException;
import ma.payment.service.EleveService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/eleves")
public class EleveController {
    private final EleveService eleveService;

    @Autowired
    public EleveController(EleveService eleveService) {
        this.eleveService = eleveService;
    }

    @GetMapping
    public ResponseEntity<List<Eleve>> getAllEleves() {
        List<Eleve> eleves = eleveService.getAllEleves();
        return ResponseEntity.ok(eleves);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Eleve> getEleveById(@PathVariable int id) {
        Eleve eleve = eleveService.getEleveById(id)
                .orElseThrow(() -> new EntityNotFoundException("Eleve not found with ID: " + id));
        return ResponseEntity.ok(eleve);
    }

    @PostMapping
    public ResponseEntity<Eleve> createEleve(  @RequestParam("eleve") String eleves, @RequestParam("image") MultipartFile image) throws IOException {


        System.out.println(eleves);

        Eleve e = new ObjectMapper().readValue(eleves,Eleve.class);
        Eleve eleve = new Eleve();
        if (e.getId()!=null){
            eleve.setId(e.getId());
        }
        eleve.setClasse(e.getClasse());
        eleve.setNom(e.getNom());
        eleve.setPrenom(e.getPrenom());
        eleve.setDateNaissance(e.getDateNaissance());
        eleve.setIdMassar(e.getIdMassar());
        eleve.setPayeur(e.getPayeur());
        eleve.setImage(image.getBytes());
        eleve.setImageType(image.getContentType());
        Eleve createdEleve = eleveService.saveEleve(eleve);
        System.out.println(eleve.toString());
        return new ResponseEntity<>(createdEleve, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Eleve> updateEleve(@PathVariable int id, @RequestBody Eleve eleve) {
        Eleve existingEleve = eleveService.getEleveById(id)
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
}