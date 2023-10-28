package org.top.hairsalonapp.service.orderedServices;

import org.springframework.stereotype.Service;
import org.top.hairsalonapp.entity.Order;
import org.top.hairsalonapp.entity.OrderedServices;

import java.util.Optional;

@Service
public interface OrderedServicesService {

    //добавить услугу в заказанные услуги
    Optional<OrderedServices> save(OrderedServices orderedServices);

    Optional<OrderedServices> findById(Integer id);

    Iterable<OrderedServices> findAll();

    Optional<Order> deleteServicesInOrder(Integer orderId);

    void deleteById(Integer id);
}
