package ma.payment.dao;

import ma.payment.bean.Payeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayeurDao extends JpaRepository<Payeur, Integer> {
    // Add custom query methods if needed
    List<Payeur>  findByCinContainingIgnoreCase(String cin);

}
