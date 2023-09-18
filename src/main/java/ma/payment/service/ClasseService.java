package ma.payment.service;

import ma.payment.bean.Classe;
import ma.payment.bean.Eleve;
import ma.payment.bean.Payeur;
import ma.payment.dao.ClasseDao;
import org.apache.bcel.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ClasseService {
    private final ClasseDao classeDao;

    @Autowired
    public ClasseService(ClasseDao classeDao) {
        this.classeDao = classeDao;
    }

    public List<Classe> getAllClasses() {
        return classeDao.findAll();
    }

    public Optional<Classe> getClasseById(int id) {
        return classeDao.findById(id);
    }

    public Classe saveClasse(Classe classe) {


        if (Objects.isNull(classe.getId())){

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            String formattedDate = sdf.format(new Date());
            classe.setDateDeCreation(formattedDate);

        }
        return classeDao.save(classe);
    }

    public void deleteClasse(int id) {
        classeDao.deleteById(id);
    }
    public List<Classe> autocomplet(String name) {
        return classeDao.findByNomContainingIgnoreCase(name);
    }

    public Page<Classe> classePagination(int page , int size) {

        Pageable pageable = PageRequest.of(page, size);
        return classeDao.findAll(pageable);

    }

    public List<Eleve> getAllElevesForClasse(Integer classeId) {
        Optional<Classe> classeOptional = classeDao.findById(classeId);
        if (classeOptional.isPresent()) {
            Classe classe = classeOptional.get();
            return classe.getEleves();
        }
        return Collections.emptyList();
    }

    public String formatDate(String dateStr){



        // Define a DateTimeFormatter for the input format
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;

        // Parse the date string into an Instant
        Instant instant = Instant.from(formatter.parse(dateStr));

        // Convert Instant to LocalDateTime
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

        // Convert LocalDateTime to Date
        Date date = Date.from(instant);

        // Format the Date to "dd/MM/yyyy" format using SimpleDateFormat
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);



    }
}

