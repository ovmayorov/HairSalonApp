package org.top.hairsalonapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.hairsalonapp.entity.HairService;
import org.top.hairsalonapp.entity.Order;
import org.top.hairsalonapp.entity.OrderedServices;
import org.top.hairsalonapp.service.hairService.HairServiceService;
import org.top.hairsalonapp.service.order.OrderService;
import org.top.hairsalonapp.service.orderedServices.OrderedServicesService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("order-service")
public class OrderedServicesController {

    public final OrderedServicesService orderedServicesService;
    public final HairServiceService hairServiceService;
    public final OrderService orderService;

    @GetMapping("")
    public String addServiceToOrder(Model model){

        OrderedServices orderedServices = new OrderedServices();
        Iterable<HairService> hairServices = hairServiceService.findAll();
        model.addAttribute("orderedService", orderedServices);
        model.addAttribute("hairServices", hairServices);

        return "orderedServices/add-service-to-order";
    }

    //Обработчик добавляет услугу в конкретный заказ
    //прокидываем order id чтобы добавить услугу в конкретный заказ
    @GetMapping("{orderId}")
    public String addServiceToOrderNumber(@PathVariable Integer orderId,  Model model, RedirectAttributes ra){
        //проверить, есть ли такой заказ
        Optional<Order> order = orderService.findById(orderId);
        if(order.isEmpty()){
            ra.addFlashAttribute("errorMessage", "Заказ не нейден. Создайте заказ. ");
            return "redirect: /order";
        }
        //подготовить форму для добавления услуги
        OrderedServices oneOrderedService = new OrderedServices();
        model.addAttribute("orderedService", oneOrderedService);

        Iterable<HairService> hairServices = hairServiceService.findAll();
        model.addAttribute("hairServices", hairServices);

        Iterable<Order> newOrder = List.of(order.get());
        model.addAttribute("orders", newOrder);
        return "orderedServices/add-service-to-order";
    }


    @PostMapping("")
    public String addServiceToOrder(OrderedServices orderedServices, RedirectAttributes ra){
        Optional<OrderedServices> newOrderedServices = orderedServicesService.save(orderedServices);
        if(newOrderedServices.isPresent()){
            ra.addFlashAttribute("successMessage",
                    "Услуга добавлена.");
        } else{
            ra.addFlashAttribute("errorMessage", "Услуга не добавлена.");
        }
        return "redirect:/order/" + orderedServices.getOrder().getId();
    }

    //Обработчкик удаления услуг из заказа
    // /order-service/delete/id
    @GetMapping("delete/{orderId}")
    public String deleteServicesInOrder(@PathVariable Integer orderId){
        Optional<Order> order = orderService.findById(orderId);
        orderedServicesService.deleteServicesInOrder(order.get().getId());
        return "redirect:/order/" + orderId;
    }

}
