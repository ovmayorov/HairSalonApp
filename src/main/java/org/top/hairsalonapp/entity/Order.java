package org.top.hairsalonapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name="order_t")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_date_f")
    private LocalDateTime orderDate;

    @Column(name = "service_date_time_f")
    private LocalDateTime serviceDateTime;

    @Column(name = "total_price_f")
    private double totalPrice;

    @Column(name="status_f")
    private OrderStatus status;

    //связь многие к одному к таблице клиентов, столбец с id клиента
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    //связь многие к одному к таблице мастеров, столбец с id мастера
    @ManyToOne
    @JoinColumn(name = "master_id")
    private Master master;

    //OneToOne к заказанным услугам OrderedServices - один заказ- один набор услуг OrderedServices
    @OneToMany(mappedBy = "order")
    private Set<OrderedServices> orderedServices;

    //Форматированная дата
    public String getFormattedOrderDate(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return orderDate.format(formatter);
    }

    public String getFormattedServiceDateTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return serviceDateTime.format(formatter);
    }
}
