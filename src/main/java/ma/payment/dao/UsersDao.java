package ma.payment.dao;

import ma.payment.bean.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersDao extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
}
