package ma.payment.dao;

import ma.payment.bean.Eleve;
import ma.payment.bean.Payeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayeurDao extends JpaRepository<Payeur, Integer> {
    // Add custom query methods if needed
    List<Payeur>  findByCinContainingIgnoreCase(String cin);

    List<Eleve> findEleveById(Integer payeurId);

    @Query("SELECT e FROM Payeur e WHERE " +
            "e.cin LIKE %:searchTerm% OR " +
            "e.nom LIKE %:searchTerm% OR " +
            "e.prenom LIKE %:searchTerm% OR " +
            "e.mail LIKE %:searchTerm%")
    List<Payeur> searchByMultipleAttributes(String searchTerm);
}
