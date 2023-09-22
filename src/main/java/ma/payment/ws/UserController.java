package ma.payment.ws;

import ma.payment.bean.Users;
import ma.payment.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private final  UsersService usersService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UsersService usersService, PasswordEncoder passwordEncoder) {
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<Users> getUser(@RequestParam String email) {
        Users user = usersService.findByEmail(email);
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }


    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        Users createdUser = usersService.saveUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    @PostMapping("/password")
    public ResponseEntity<String> changePassword(
            @RequestParam String email,
            @RequestParam String currentpassword,
            @RequestParam String newpassword
    ) {
        Users user = usersService.findByEmail(email);

        if ((passwordEncoder.matches( currentpassword,user.getPassword()))) {
            user.setPassword(passwordEncoder.encode(newpassword));
            usersService.saveUser(user);
            return ResponseEntity.ok("Password changed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Current password is incorrect");
        }
    }

}
