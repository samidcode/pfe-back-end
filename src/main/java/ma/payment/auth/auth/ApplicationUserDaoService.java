package ma.payment.auth.auth;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static ma.payment.auth.Security.UserRoles.ADMIN;
import static ma.payment.auth.Security.UserRoles.STUDENT;


@Repository("fake")
public class ApplicationUserDaoService implements  ApplicationUserDao{

 private final PasswordEncoder passwordEncoder ;
    public ApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUser()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUser(){
        List<ApplicationUser>applicationUsers = Lists.newArrayList(
            new ApplicationUser(
                    STUDENT.getGrantedAuthorities(),
                    passwordEncoder.encode("123456"),
                    "samidi",
                    true,
                    true,
                    true,
                    true
            ),
                new ApplicationUser(
                        ADMIN.getGrantedAuthorities(),
                        passwordEncoder.encode("123456"),
                        "admin",
                        true,
                        true,
                        true,
                        true
                )

        );
    return applicationUsers;

    }
}
