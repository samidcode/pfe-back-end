package ma.payment.dao;

import ma.payment.bean.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EleveDao extends JpaRepository<Eleve, Integer> {

}
