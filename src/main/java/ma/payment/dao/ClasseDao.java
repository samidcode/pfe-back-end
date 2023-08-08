package ma.payment.dao;

import ma.payment.bean.Classe;
import ma.payment.bean.Payeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClasseDao extends JpaRepository<Classe, Integer> {

    List<Classe> findByNomContainingIgnoreCase(String name);

}
