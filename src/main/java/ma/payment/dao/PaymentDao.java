package ma.payment.dao;

import ma.payment.bean.Classe;
import ma.payment.bean.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentDao extends JpaRepository<Payment, Integer> {

    List<Payment> findByEleveId(Integer eleveId);

    boolean existsByEleveIdAndMoisP(Integer eleveId, String moisP);

    Optional<Payment> findByMoisPAndYearPAndEleve_IdAndObjet(String moisP, String yearP, Integer eleveId, String objet);


      List<Payment> findByDateDeCreationBetween(Date startDate, Date endDate);

    @Query("SELECT COUNT(p) FROM Payment p WHERE YEAR(p.dateDeCreation) = YEAR(:date) AND MONTH(p.dateDeCreation) = MONTH(:date) AND DAY(p.dateDeCreation) = DAY(:date)")
    Optional<Integer> countPaymentsByDate(@Param("date") Date date);

    @Query("SELECT SUM(p.montant) FROM Payment p WHERE YEAR(p.dateDeCreation) = YEAR(:date) AND MONTH(p.dateDeCreation) = MONTH(:date) AND DAY(p.dateDeCreation) = DAY(:date)")
    Optional<Integer> sumSizeOfPaymentsByDate(@Param("date") Date date);

    @Query("SELECT COUNT(p) FROM Payment p WHERE p.objet = 'Inscription' AND YEAR(p.dateDeCreation) = YEAR(:date) AND MONTH(p.dateDeCreation) = MONTH(:date) AND DAY(p.dateDeCreation) = DAY(:date)")
    Optional<Integer> sumInscription(@Param("date") Date date);

    @Query("SELECT p FROM Payment p WHERE " +
            "p.moisP LIKE %:keyword% OR " +
            "p.objet LIKE %:keyword% OR " +
            "p.payeur.cin LIKE %:keyword% OR " +
            "p.eleve.nom LIKE %:keyword% OR " +
            "p.eleve.prenom LIKE %:keyword%")
    List<Payment> searchPaymentsByKeyword(@Param("keyword") String keyword);
}
