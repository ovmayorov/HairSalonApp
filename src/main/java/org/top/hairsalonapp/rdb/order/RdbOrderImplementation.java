package org.top.hairsalonapp.rdb.order;

import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Set;
import org.springframework.stereotype.Service;
import org.top.hairsalonapp.entity.Client;
import org.top.hairsalonapp.entity.Order;
import org.top.hairsalonapp.entity.OrderStatus;
import org.top.hairsalonapp.rdb.repository.OrderRepository;
import org.top.hairsalonapp.service.order.OrderService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RdbOrderImplementation implements OrderService {

    private final OrderRepository orderRepository;


    @Override
    public Optional<Order> save(Order order) {

        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(0.0);
        order.setOrderedServices(null);
        order.setStatus(OrderStatus.NEW);


        return Optional.of(orderRepository.save(order));
    }

    @Override
    public Iterable<Order> findAll() {

        return orderRepository.findAll();
    }

    @Override
    public Iterable<Order> findByClientId(Integer clientId) {
        return null;
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    public Iterable<Order> findByClientPhoneNumber(String phoneNumber) {

        return orderRepository.findByClientPhoneNumber(phoneNumber);
    }

    @Override
    public Iterable<Order> findByTodayDate(){
        Iterable<Order> orders = orderRepository.findAll();
        HashSet<Order> ordersForToday = new HashSet();
        for(Order order : orders){
            if(order.getServiceDateTime().toLocalDate().isEqual(LocalDate.now())){
                ordersForToday.add(order);
            }
        }
        return ordersForToday;
    }

    @Override
    public Iterable<Order> findLateOrders() {
        Iterable<Order> orders = orderRepository.findByStatus(OrderStatus.NEW);
        HashSet<Order> ordersLate = new HashSet();
        for(Order order : orders){
            if(order.getServiceDateTime().toLocalDate().isBefore(LocalDate.now())){
                ordersLate.add(order);
            }
        }
        return ordersLate;
    }

    @Override
    public Iterable<Order> findFutureOrders() {
        Iterable<Order> orders = orderRepository.findByStatus(OrderStatus.NEW);
        HashSet<Order> ordersFuture = new HashSet();
        for(Order order : orders){
            if(order.getServiceDateTime().toLocalDate().isAfter(LocalDate.now())){
                ordersFuture.add(order);
            }
        }
        return ordersFuture;
    }

    @Override
    public Iterable<Order> findByStatus(OrderStatus status) {
//        Iterable<Order> ordersByStatus = orderRepository.findByStatus(status);
        return orderRepository.findByStatus(status);
    }

    @Override
    public Optional<Order> updateById(Integer id, Order order, LocalDateTime serviceDateTimeValue) {
        Optional<Order> updatable = orderRepository.findById(id);  // наш зака для редактирования

        if(updatable.isPresent()){
            order.setId(id);
            order.setOrderDate(updatable.get().getOrderDate());
            order.setClient(updatable.get().getClient());
            order.setStatus(updatable.get().getStatus());
            order.setTotalPrice(updatable.get().getTotalPrice());
            order.setServiceDateTime(serviceDateTimeValue);
            if(order.getServiceDateTime().isAfter(updatable.get().getServiceDateTime())){
                order.setStatus(OrderStatus.NEW);
            }
            return Optional.of(orderRepository.save(order));
        }
        return Optional.empty();
    }



//    @Override
//    public Optional<Order> deleteById(Integer id) {
//        return Optional.empty();
//    }
}
