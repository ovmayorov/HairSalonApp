package org.top.hairsalonapp.rdb.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.top.hairsalonapp.entity.User;
import org.top.hairsalonapp.rdb.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RdbUserDetailsService implements UserDetailsService {

    //Используемый источник данных
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //1. Найти пльзователя в БД
        Optional<User> user = userRepository.findUserByLogin(username);

        if (user.isEmpty()) {
            return null;
        } else {
            //2. Если пользователь есть, создать UserDetails
            RdbUserDetails userDetails = new RdbUserDetails();
            userDetails.setUser(user.get());
            return userDetails;
        }
    }
}
