package ma.payment.dao;

import ma.payment.bean.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentDao extends JpaRepository<Payment, Integer> {

    List<Payment> findByEleveId(Integer eleveId);

    boolean existsByEleveIdAndMoisP(Integer eleveId, String moisP);


}
