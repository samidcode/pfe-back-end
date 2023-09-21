package ma.payment.ws;

import ma.payment.bean.Classe;
import ma.payment.bean.Eleve;
import ma.payment.exceptions.EntityNotFoundException;
import ma.payment.service.ClasseService;
import ma.payment.service.EleveService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClasseController {
    private final ClasseService classeService;
    private final EleveService eleveService;
    @Autowired
    public ClasseController(ClasseService classeService, EleveService eleveService) {
        this.classeService = classeService;
        this.eleveService = eleveService;
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
        classeService.getClasseById(id)
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


    @GetMapping("/classePagination")
    public ResponseEntity<Page<Classe>> getPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<Classe> classes = classeService.classePagination(page,size);
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/export/{classeId}")
    public void exportElevesToExcel(@PathVariable Integer classeId, HttpServletResponse response) throws IOException, ParseException {
        List<Eleve> eleves = eleveService.getElevesByClasseId(classeId);

        // Create an Excel workbook using Apache POI or a similar library
        Workbook workbook = createExcelWorkbook(eleves);

        // Set the content type and headers for the response
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=eleves.xlsx");

        // Write the Excel workbook to the response output stream
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
    }

    private Workbook createExcelWorkbook(List<Eleve> eleves) throws ParseException {
        // Create a new Excel workbook
        Workbook workbook = new XSSFWorkbook();

        // Create a sheet in the workbook with the name "Eleves"
        Sheet sheet = workbook.createSheet("Eleves");

        // Create a header row with column names
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID Massar");
        headerRow.createCell(1).setCellValue("Nom");
        headerRow.createCell(2).setCellValue("Prenom");
        headerRow.createCell(3).setCellValue("Date de Naissance");


        // Populate rows with data from the Eleve list
        int rowNum = 1; // Start from the second row (index 1) since the first row is the header
        for (Eleve eleve : eleves) {



            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(eleve.getIdMassar());
            row.createCell(1).setCellValue(eleve.getNom());
            row.createCell(2).setCellValue(eleve.getPrenom());
            row.createCell(3).setCellValue(classeService.formatDate(eleve.getDateNaissance()));
        }

        return workbook;
    }

    @GetMapping("/classeSearch")
    public List<Classe> searchClasses(@RequestParam String keyword) {
        // Call the service to perform the search
        return classeService.searchClasse(keyword);
    }

}

