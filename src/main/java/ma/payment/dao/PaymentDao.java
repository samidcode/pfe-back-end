package ma.payment.dao;

import ma.payment.bean.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentDao extends JpaRepository<Payment, Integer> {

    List<Payment> findByEleveId(Integer eleveId);

    boolean existsByEleveIdAndMoisP(Integer eleveId, String moisP);

    Optional<Payment> findByMoisPAndYearPAndEleve_IdAndObjet(String moisP, String yearP, Integer eleveId, String objet);

    List<Payment> findByDateBetween(String startDate, String endDate);

    @Query("SELECT p.date, p.montant FROM Payment p WHERE p.date >= ?1 AND p.date <= ?2")
    List<Object[]> findPaymentDataByDateDeCreationRange(Date startDate, Date endDate);

}
