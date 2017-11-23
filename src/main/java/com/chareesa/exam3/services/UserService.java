package com.chareesa.exam3.services;

import com.chareesa.exam3.models.User;
import com.chareesa.exam3.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    public Long saveUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            return -1L;
        }

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
        userRepository.save(user);
        return user.getId();
    }

    public Boolean login(User user, String password, HttpSession session) {
        if(user != null) {
            if(BCrypt.checkpw(password,  user.getPassword())) {//Check password by unhashing password
                session.setAttribute("user_id",  user.getId());
                return true;
            }
        }
        return false;

    }

    public User findUser(final String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


    public void addUser(User dbUser) {
        userRepository.save(dbUser);
    }

    public User findById(long id) {
        return userRepository.findOne(id);
    }
}