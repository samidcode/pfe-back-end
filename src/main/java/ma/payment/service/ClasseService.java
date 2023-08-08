package ma.payment.service;

import ma.payment.bean.Classe;
import ma.payment.dao.ClasseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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
        return classeDao.save(classe);
    }

    public void deleteClasse(int id) {
        classeDao.deleteById(id);
    }
    public List<Classe> autocomplet(String name) {
        return classeDao.findByNomContainingIgnoreCase(name);
    }

}

