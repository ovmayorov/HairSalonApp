package org.top.hairsalonapp.rdb;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.top.hairsalonapp.entity.User;
import org.top.hairsalonapp.rdb.repository.UserRepository;
import org.top.hairsalonapp.service.user.UserService;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RdbUserImplementation implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    @Override
    public Optional<User> register(String login, String password, String passwordConfirmation) {
                if(!password.equals(passwordConfirmation) || userRepository.findUserByLogin(login).isPresent()){
            return Optional.empty();           //пользователь с таким логином уже существует или пароли не одинаковые
        }
        User user = new User();
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");           //по умолчанию, роль - USER

        return Optional.of(userRepository.save(user));
    }


    @Override
    public Optional<User> registerMaster(String login, String password, String passwordConfirmation) {
        if(!password.equals(passwordConfirmation) || userRepository.findUserByLogin(login).isPresent()){
            return Optional.empty();           //пользователь с таким логином уже существует или пароли не одинаковые
        }
        User user = new User();
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ADMIN");           //по умолчанию, роль - ADMIN

        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }
}
