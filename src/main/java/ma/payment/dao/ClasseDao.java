package ma.payment.dao;

import ma.payment.bean.Classe;
import ma.payment.bean.Eleve;
import ma.payment.bean.Payeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClasseDao extends JpaRepository<Classe, Integer> {

    List<Classe> findByNomContainingIgnoreCase(String name);
    @Query("SELECT c FROM Classe c WHERE " +
            "LOWER(c.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.niveau) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
    List<Classe> search(@Param("keyword") String keyword);
}
