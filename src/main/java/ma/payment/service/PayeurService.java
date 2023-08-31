package ma.payment.service;

import ma.payment.bean.Eleve;
import ma.payment.bean.Payeur;
import ma.payment.dao.EleveDao;
import ma.payment.dao.PayeurDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PayeurService {
    private final PayeurDao payeurDao;

    @Autowired
    public PayeurService(PayeurDao payeurDao ) {
        this.payeurDao =  payeurDao;
    }

    @Autowired
    private EleveDao eleveRepository;

    public List<Payeur> getAllPayeurs() {
        return payeurDao.findAll();
    }

    public Optional<Payeur> getPayeurById(int id) {
        return payeurDao.findById(id);
    }

    public Payeur savePayeur(Payeur payeur) {
        return payeurDao.save(payeur);
    }

    public void deletePayeur(int id) {
        payeurDao.deleteById(id);
    }

    public List<Payeur> autoComplet(String cin) {
      return  payeurDao.findByCinContainingIgnoreCase(cin);
    }
    public Page<Payeur> payeurPagination(int page , int size) {

        Pageable pageable = PageRequest.of(page, size);
        return payeurDao.findAll(pageable);

    }

   public List<Eleve> findElevesById(Integer payeurId){

       return eleveRepository.findByPayeurId(payeurId);

   }




}

