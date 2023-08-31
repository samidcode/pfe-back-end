package ma.payment.dao;

import ma.payment.bean.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EleveDao extends JpaRepository<Eleve, Integer> {

    @Query("SELECT c FROM Eleve c WHERE " +
            "LOWER(c.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.prenom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.idMassar) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Eleve> searchByNameLastNameAndCode(@Param("keyword") String keyword);
    List<Eleve> findByPayeurId(Integer payeurId);

}
