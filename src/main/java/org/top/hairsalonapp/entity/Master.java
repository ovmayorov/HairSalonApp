package org.top.hairsalonapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name="master_t")
public class Master {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="phoneNumber_f", nullable = false)
    private String phoneNumber;

    @Column(name="first_nmae_f", nullable = false)
    private String name;

    @Column(name="last_nmae_f", nullable = false)
    private String lastName;

    @Column(name="email_f", nullable = false)
    private String email;

    //связь с заказами, один мастер - много заказов
    @OneToMany(mappedBy = "master")
    private Set<Order> orders;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
