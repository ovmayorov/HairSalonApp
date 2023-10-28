package org.top.hairsalonapp.entity;

//Сущность "Пользователь" соответсвующая таблице в БД
//Нужно соеденить с сущностью Client  (у меня 2 сущности Client и Master)

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.Objects;

@Data
@Entity
@Table(name="user_t")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="login_f", nullable = false)
    private String login;       //Логин, или userName

    @Column(name="password_f", nullable = false)
    private String password;    //пароль пользователя. ПАРОЛЬ В ЗАХЕШИРОВАННОМ ВИДЕ

    @Column(name="role_f", nullable = false)
    private String role;        //роль пользователя, может быть либо Клиент, либо Мастер. Один логин с несколькими ролями
                                //не допускается.

    @OneToOne(mappedBy = "user")
    private Client client;

    @OneToOne(mappedBy = "user")
    private Master master;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, role);
    }
}
