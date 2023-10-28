package org.top.hairsalonapp.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.top.hairsalonapp.rdb.repository.UserRepository;
import org.top.hairsalonapp.rdb.security.RdbUserDetailsService;

import javax.sql.DataSource;

//Класс конфигурации SpringSecurity
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    // Конфигурация управляющая доступом к нашим обработчикам (запросам)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Пример паттерна "Строитель"
        http
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/", "/register/**",
                                "/client/new-client/*",
                                "/services/**",
                                "webjars/**",
                                "css/**",
                                "images/**").permitAll()
                        .requestMatchers("/services/new-service",
                                "/services/delete/*",
                                "/services/update/*",
                                "/master/new-master",
                                "/master/delete/*",
                                "/master/update/*",
                                "/client",
                                "/register/master",
                                "/order",
                                "/order/new-order",
                                "/order/today").hasRole("ADMIN")

                        .anyRequest().authenticated())
                .formLogin((form)-> form
                        .loginPage("/login").permitAll())
                        .logout((customizer)-> customizer
                        .logoutSuccessUrl("/login"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public final UserRepository userRepository;    //обьявляется как финальное поле, т.к. это интерфейс, и new использовать нельзя.
                                                    //и добавить анотацию @RequiredArgsConstructor


    //Конфигурация UserDetailsService - провайдер работы с пользователями
    //Production version
    @Bean
    public UserDetailsService userDetailsService(){
        return new RdbUserDetailsService(userRepository);   //RdbUserDetailsService имеет поле UserRepository, по этому ждет в качестве параметра UserRepository
    }

    //Необходимо добавить зависимости, необходимые для работы SpringSecurity с нашими сервисами пользователя.
    //Сервис который задает нашему приложению информацию о том что у нас AuthenticationProvider бедет являться источником из базы данных.
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    private final DataSource dataSource;

    @Bean
    public UserDetailsManager users(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder())
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .build();
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setAuthenticationManager(authenticationManager);
        return jdbcUserDetailsManager;
    }



}
