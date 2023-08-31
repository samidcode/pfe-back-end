package ma.payment.service;

import ma.payment.bean.Eleve;
import ma.payment.dao.EleveDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EleveService {
    private final EleveDao eleveDao;

    @Autowired
    public EleveService(EleveDao eleveDao) {
        this.eleveDao = eleveDao;
    }

    public List<Eleve> getAllEleves() {
        return eleveDao.findAll();
    }

    public Optional<Eleve> getEleveById(int id) {
        return eleveDao.findById(id);
    }

    public Eleve saveEleve(Eleve eleve) {
        return eleveDao.save(eleve);
    }

    public void deleteEleve(int id) {
        eleveDao.deleteById(id);
    }


    public List<Eleve> searchEleve(String keyword) {
        return eleveDao.searchByNameLastNameAndCode(keyword);
    }

    public Page<Eleve> elevePagination(int page , int size) {

        Pageable pageable = PageRequest.of(page, size);
        return eleveDao.findAll(pageable);

    }




}

