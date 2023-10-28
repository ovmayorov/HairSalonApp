package org.top.hairsalonapp.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.hairsalonapp.entity.Order;
import org.top.hairsalonapp.entity.OrderedServices;

@Repository
public interface OrderedServicesRepository extends CrudRepository<OrderedServices, Integer> {
    Iterable<OrderedServices> findOrderedServicesByOrder(Order order);
}
