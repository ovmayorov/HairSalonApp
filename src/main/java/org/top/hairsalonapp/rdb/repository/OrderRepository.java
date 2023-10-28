package org.top.hairsalonapp.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.hairsalonapp.entity.Order;
import org.top.hairsalonapp.entity.OrderStatus;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

    Iterable<Order> findByClientPhoneNumber(String phoneNumber);

    Iterable<Order> findByStatus(OrderStatus status);
}
