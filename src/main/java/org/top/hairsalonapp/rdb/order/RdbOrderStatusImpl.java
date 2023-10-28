package org.top.hairsalonapp.rdb.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.top.hairsalonapp.entity.Order;
import org.top.hairsalonapp.entity.OrderStatus;
import org.top.hairsalonapp.rdb.repository.OrderRepository;
import org.top.hairsalonapp.service.order.OrderStatusService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RdbOrderStatusImpl implements OrderStatusService {

    private final OrderRepository orderRepository;

    @Override
    public void statusToDone(Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        order.get().setStatus(OrderStatus.DONE);
        orderRepository.save(order.get());
    }


    @Override
    public void statusToCanceled(Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        order.get().setStatus(OrderStatus.CANCELED);
        orderRepository.save(order.get());
    }
}
