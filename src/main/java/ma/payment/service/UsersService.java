package ma.payment.service;

import ma.payment.bean.Payment;
import ma.payment.bean.Users;
import ma.payment.dao.UsersDao;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {

    private  final  UsersDao usersDao;

    public UsersService(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    public  Users findByEmail(String email){

        Users user = usersDao.findByEmail(email).get();

        return user;


    }


    public Users saveUser(Users user) {
        return usersDao.save(user);
    }



}
