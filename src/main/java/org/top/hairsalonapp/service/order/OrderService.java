package org.top.hairsalonapp.service.order;

import org.springframework.stereotype.Service;
import org.top.hairsalonapp.entity.Order;
import org.top.hairsalonapp.entity.OrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public interface OrderService {

    //Создать заказ
    Optional<Order> save(Order order);

    //Найти все заказы
    Iterable<Order> findAll();

    Iterable<Order> findByClientId(Integer clientId);

    //Найти заказ по id
    Optional<Order> findById(Integer id);

    //Найти заказ по номеру телефона клиента
    Iterable<Order> findByClientPhoneNumber(String phoneNumber);

    Iterable<Order> findByTodayDate();

    Iterable<Order> findLateOrders();

    Iterable<Order> findFutureOrders();

    Iterable<Order> findByStatus(OrderStatus status);

    //Найти заказ по id клиента и по id мастера

    //Редактировать заказ по id (+ пометить как отмененный)
    Optional<Order> updateById(Integer id, Order order, LocalDateTime serviceDateTimeValue);



    //Удалить заказ по id (не удалять, а пометить как отмененный ??)
//    Optional<Order> deleteById(Integer id);


}
