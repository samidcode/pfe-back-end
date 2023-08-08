package ma.payment.ws;

import io.jsonwebtoken.lang.Classes;
import ma.payment.bean.Classe;
import ma.payment.bean.Payeur;
import ma.payment.exceptions.EntityNotFoundException;
import ma.payment.service.ClasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClasseController {
    private final ClasseService classeService;

    @Autowired
    public ClasseController(ClasseService classeService) {
        this.classeService = classeService;
    }

    @GetMapping
    public ResponseEntity<List<Classe>> getAllClasses() {
        List<Classe> classes = classeService.getAllClasses();
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classe> getClasseById(@PathVariable int id) {
        Classe classe = classeService.getClasseById(id)
                .orElseThrow(() -> new EntityNotFoundException("Classe not found with ID: " + id));
        return ResponseEntity.ok(classe);
    }

    @PostMapping
    public ResponseEntity<Classe> createClasse(@RequestBody Classe classe) {
        Classe createdClasse = classeService.saveClasse(classe);
        return new ResponseEntity<>(createdClasse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Classe> updateClasse(@PathVariable int id, @RequestBody Classe classe) {
        Classe existingClasse = classeService.getClasseById(id)
                .orElseThrow(() -> new EntityNotFoundException("Classe not found with ID: " + id));
        classe.setId(id);
        Classe updatedClasse = classeService.saveClasse(classe);
        return ResponseEntity.ok(updatedClasse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClasse(@PathVariable int id) {
        classeService.deleteClasse(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/autocomplet")
    public ResponseEntity<List<Classe>> createPayeur(@RequestBody String nom) {
        List<Classe> classes = classeService.autocomplet(nom);
        return new ResponseEntity<>(classes, HttpStatus.ACCEPTED);
    }
}

