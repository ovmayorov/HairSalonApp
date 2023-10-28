package org.top.hairsalonapp.rdb;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.top.hairsalonapp.entity.Order;
import org.top.hairsalonapp.entity.OrderedServices;
import org.top.hairsalonapp.rdb.repository.OrderRepository;
import org.top.hairsalonapp.rdb.repository.OrderedServicesRepository;
import org.top.hairsalonapp.service.order.OrderService;
import org.top.hairsalonapp.service.orderedServices.OrderedServicesService;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RdbOrderedServicesImplementation implements OrderedServicesService {

    private final OrderedServicesRepository orderedServicesRepository;
    private final OrderService orderService;

    @Override
    public Optional<OrderedServices> save(OrderedServices orderedServices) {
        Order order = orderedServices.getOrder();
        Iterable<OrderedServices> currentServices = orderedServicesRepository.findOrderedServicesByOrder(order);
        for (OrderedServices currentService: currentServices) {
            if (currentService.getHairService().equals(orderedServices.getHairService())){
                return Optional.empty();
            }
        }
        double startOrderPrice = order.getTotalPrice();
        double hairServicePrice = orderedServices.getHairService().getPrice();

        order.setTotalPrice(startOrderPrice + hairServicePrice);

        return Optional.of(orderedServicesRepository.save(orderedServices));
    }

    @Override
    public Optional<OrderedServices> findById(Integer id) {
        return orderedServicesRepository.findById(id);
    }

    @Override
    public Iterable<OrderedServices> findAll() {
        return orderedServicesRepository.findAll();
    }

    @Override
    public Optional<Order> deleteServicesInOrder(Integer orderId) {
        Optional<Order> workingOrder = orderService.findById(orderId);
        LocalDateTime serviceDateTimeValue = workingOrder.get().getServiceDateTime();
        Iterable<OrderedServices> removableServices =  findOrderedServicesByOrder(workingOrder.get());
        for (OrderedServices removableService : removableServices) {
            deleteById(removableService.getId());
        }
        workingOrder.get().setOrderedServices(null);
        workingOrder.get().setTotalPrice(0.0);
        return orderService.updateById(orderId, workingOrder.get(), serviceDateTimeValue);
    }

    @Override
    public void deleteById(Integer id) {
        orderedServicesRepository.deleteById(id);
    }

    public Iterable<OrderedServices> findOrderedServicesByOrder(Order order){
        return orderedServicesRepository.findOrderedServicesByOrder(order);
    }

}
