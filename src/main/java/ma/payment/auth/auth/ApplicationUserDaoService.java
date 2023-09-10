package ma.payment.auth.auth;

import lombok.RequiredArgsConstructor;
import ma.payment.bean.Users;
import ma.payment.dao.UsersDao;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static ma.payment.auth.Security.UserRoles.ADMIN;


@Service("fake")
@RequiredArgsConstructor
public class ApplicationUserDaoService implements  ApplicationUserDao{

    private final UsersDao usersDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return usersDao.findByEmail(username)
                .map(user -> new ApplicationUser(
                        ADMIN.getGrantedAuthorities(),
                        user.getPassword(),
                        user.getEmail(),
                        true,
                        true,
                        true,
                        true
                ));
    }

    public Optional<Users> saveApplicationUser(ApplicationUser appUser){
        var user = new Users();
        user.setEmail(appUser.getUsername());
        user.setPassword(passwordEncoder.encode(appUser.getPassword()));
       return Optional.of(usersDao.save(user));

    }
}
