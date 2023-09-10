package ma.payment.ws;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.payment.bean.Eleve;
import ma.payment.dto.EleveWithStatusDTO;
import ma.payment.exceptions.EntityNotFoundException;
import ma.payment.service.EleveService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/eleves")
public class EleveController {
    private final EleveService eleveService;

    @Autowired
    public EleveController(EleveService eleveService) {
        this.eleveService = eleveService;
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
    public ResponseEntity<Eleve> createEleve(  @RequestBody Eleve e ) throws IOException {
        Eleve createdEleve = eleveService.saveEleve(e);
        return new ResponseEntity<>(createdEleve, HttpStatus.CREATED);
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
    public ResponseEntity<List<Eleve>> searchContacts(@RequestParam String keyword) {
        List<Eleve> eleves = eleveService.searchEleve(keyword);
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
