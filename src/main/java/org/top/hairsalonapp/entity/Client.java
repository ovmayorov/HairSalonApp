package org.top.hairsalonapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="client_t")
public class Client {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="phone_number_f", nullable = false)
    private String phoneNumber;

    @Column(name="client_name_f", nullable = false)
    private String clientName;

    @Column(name="client_last_name_f", nullable = false)
    private String clientLastName;

    @Column(name="client_email_f", nullable = false)
    private String clientEmail;

    @Column(name="marketing_f")
    private boolean marketing;

    //связь с таблицей Order : один клиент - много заказов
    @OneToMany(mappedBy = "client")
    private Set<Order> orders;

    //связь с таблицей User
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;


}
