package ma.payment.service;

import ma.payment.bean.Eleve;
import ma.payment.dao.EleveDao;
import ma.payment.dto.EleveWithStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EleveService {
    private final EleveDao eleveDao;
    private final PaymentService paymentService;
    @Autowired
    public EleveService(EleveDao eleveDao, PaymentService paymentService) {
        this.eleveDao = eleveDao;
        this.paymentService = paymentService;
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

    public Page<EleveWithStatusDTO> elevePagination(int page , int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Eleve> elevePage = eleveDao.findAll(pageable);

        Page<EleveWithStatusDTO> eleveWithStatusPage = elevePage.map(eleve -> new EleveWithStatusDTO(
                eleve.getId(),
                eleve.getIdMassar(),
                eleve.getNom(),
                eleve.getPrenom(),
                eleve.getDateNaissance(),
                eleve.getImage(),
                eleve.getImageType(),
                eleve.getClasse(),
                eleve.getPayeur(),
                paymentService.hasPaidForLastMonth(eleve.getId()) ,// Assuming eleve has an ID field,
                eleve.getDateDeCreation()
        ));

        return eleveWithStatusPage;

    }


    public List<Eleve> getElevesByClasseId(Integer classeId) {
        return eleveDao.findByClasseId(classeId);
    }


}

