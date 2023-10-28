package org.top.hairsalonapp.service.user;

import org.springframework.stereotype.Service;
import org.top.hairsalonapp.entity.User;

import java.util.Optional;

@Service
public interface UserService {

    //метод регистрации пользователя
    Optional<User> register(String login, String password, String passwordConfirmation);

    Optional<User> registerMaster(String login, String password, String passwordConfirmation);
    Optional<User> findById(Integer id);
}
