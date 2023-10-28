package org.top.hairsalonapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.hairsalonapp.entity.Client;
import org.top.hairsalonapp.entity.Master;
import org.top.hairsalonapp.entity.Order;
import org.top.hairsalonapp.entity.OrderStatus;
import org.top.hairsalonapp.entity.User;
import org.top.hairsalonapp.rdb.repository.UserRepository;
import org.top.hairsalonapp.service.client.ClientService;
import org.top.hairsalonapp.service.master.MasterService;
import org.top.hairsalonapp.service.order.OrderService;
import org.top.hairsalonapp.service.order.OrderStatusService;
import org.top.hairsalonapp.service.user.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("order")
public class OrderController {

    //Необходимые сервисы
    private final OrderService orderService;
    private final OrderStatusService orderStatusService;
    private final ClientService clientService;
    private final MasterService masterService;
    private final UserService userService;
    private final UserRepository userRepository;


    //Поиск и вывод всех заказов
    @GetMapping("")
    public String findAll(Model model){
        Iterable<Order> orders = orderService.findAll();

        if(orders.iterator().hasNext()){
            model.addAttribute("orders", orders);
        } else {
            model.addAttribute("orders", null);
        }
        return "order/order-list";
    }
///////////////////////////////////////////////////////////////////////////
    //Поиск и вывод заказов на сегодняшний день
    @GetMapping("today")
    public String findByTodayDate(Model model){
        Iterable<Order> orders = orderService.findByTodayDate();
        if(orders.iterator().hasNext()){
            model.addAttribute("orders", orders);
        } else {
            model.addAttribute("orders", null);
        }
        return "order/order-list-today";
    }
///////////////////////////////////////////////////////////////////////////

    //Поиск и вывод выполненных заказов
    @GetMapping("done")
    public String findByStatusDone(Model model){
        Iterable<Order> orders = orderService.findByStatus(OrderStatus.DONE);
        if(orders.iterator().hasNext()){
            model.addAttribute("orders", orders);
        } else {
            model.addAttribute("orders", null);
        }
        return "order/order-list-done";
    }

    //Поиск и вывод просроченных заказов
    @GetMapping("late")
    public String findLateOrders(Model model){
        Iterable<Order> orders = orderService.findLateOrders();

        if(orders.iterator().hasNext()){
            model.addAttribute("orders", orders);
        } else {
            model.addAttribute("orders", null);
        }
        return "order/order-list-late";
    }

    //Поиск и вывод отмененных заказов
    @GetMapping("canceled")
    public String findByStatusCanceled(Model model){
        Iterable<Order> orders = orderService.findByStatus(OrderStatus.CANCELED);
        if(orders.iterator().hasNext()){
            model.addAttribute("orders", orders);
        } else {
            model.addAttribute("orders", null);
        }
        return "order/order-list-canceled";
    }

    //Поиск и вывод будущих заказов
    @GetMapping("future")
    public String findFutureOrders(Model model){
        Iterable<Order> orders = orderService.findFutureOrders();

        if(orders.iterator().hasNext()){
            model.addAttribute("orders", orders);
        } else {
            model.addAttribute("orders", null);
        }
        return "order/order-list-future";
    }

///////////////////////////////////////////////////////////////////////////
    @GetMapping("client")
    public String findByClient(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = ((UserDetails)principal).getUsername();
        Optional<User> user = userRepository.findUserByLogin(login);

//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String clientPhoneNumber = user.get().getClient().getPhoneNumber();
        Iterable<Order> clientOrders = orderService.findByClientPhoneNumber(clientPhoneNumber);
        if(clientOrders.iterator().hasNext()){
                model.addAttribute("clientOrders", clientOrders);
            }else{
                model.addAttribute("clientOrders", null);
            }

        return "order/order-list-by-client";
    }

    //////////////////////////////////////////////////////////////

    //Обработчики добавления нового заказа
    @GetMapping("new-order")
    public String addNewOrder(Model model){
        Order order = new Order();          //обьект для заполнения в форме
        Iterable<Client> clients = clientService.findAll();
        Iterable<Master> masters = masterService.findAll();
        model.addAttribute("newOrder", order);
        model.addAttribute("clients", clients);
        model.addAttribute("masters", masters);
        return "order/add-new-order-form";
    }

    @PostMapping("new-order")
    public String addNewOrder(Order order, RedirectAttributes ra){
        Optional<Order> newOrder = orderService.save(order);

        //выводим служебные сообщения
        if(newOrder.isPresent()){
            ra.addFlashAttribute("successMessage",
                    "Заказ № " + order.getId() + " успешно создан.");
        } else{
            ra.addFlashAttribute("errorMessage", "Заказ не создан. ");
        }
        return "redirect:/order/" +newOrder.get().getId();
//        return "redirect:/order";
    }

    //////////////////////////////////////////////////////////////
    ////////////////////////////
    //Обработчики добавления нового заказа для текущего пользователя
    @GetMapping("client/new-order")
    public String addNewOrderByClient(Model model){
        Order order = new Order();          //обьект для заполнения в форме

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = ((UserDetails)principal).getUsername();
        Optional<User> user = userRepository.findUserByLogin(login);
        Optional<Client> client = clientService.findById(user.get().getClient().getId());

        Iterable<Client> clients = List.of(client.get());
        Iterable<Master> masters = masterService.findAll();
        model.addAttribute("newOrder", order);
        model.addAttribute("clients", clients);
        model.addAttribute("masters", masters);
        return "order/add-new-order-by-client";
    }

    @PostMapping("client/new-order")
    public String addNewOrderByClient(Order order, RedirectAttributes ra){
        Optional<Order> newOrder = orderService.save(order);

        //выводим служебные сообщения
        if(newOrder.isPresent()){
            ra.addFlashAttribute("successMessage",
                    "Заказ № " + order.getId() + " успешно создан.");
        } else{
            ra.addFlashAttribute("errorMessage", "Заказ не создан. ");
        }
        return "redirect:/order/client";
//        return "redirect:/order";
    }
    ///////////////////////////
    //////////////////////////

    //Детальная информация о записи
    @GetMapping("{id}")
    public String orderDetails(@PathVariable Integer id, Model model){
        //находим запись/заказ
        Optional<Order> order = orderService.findById(id);
        if(order.isPresent()){
            model.addAttribute("orderDetails", order.get());
        }else {
            model.addAttribute("orderDetails", null);
        }
        return "order/order-details";
    }




    //    Обработчики обновления/редактирования заказа
    @GetMapping("update/{id}")
    public String orderUpdate(@PathVariable Integer id, Model model){
        Optional<Order> order = orderService.findById(id);
//        order.get().getMaster()
        Iterable<Master> masters = masterService.findAll();
        model.addAttribute("masters", masters);
        if(order.isPresent()){
            model.addAttribute("orderUpdate", order.get());
            model.addAttribute("serviceDateTimeValue", order.get().getServiceDateTime());
        }else{
            model.addAttribute("orderUpdate", null);
        }
        return "order/order-update-form";

    }

    @PostMapping("update/{id}")
    public String orderUpdate(@PathVariable Integer id, Order order, LocalDateTime serviceDateTimeValue, RedirectAttributes ra){
        Optional<Order> updated = orderService.updateById(id, order, serviceDateTimeValue);
        if(updated.isPresent()){
            ra.addFlashAttribute("successMessage",
                    "Заказ № " + order.getId() + " успешно обновлен.");
        }else{
            ra.addFlashAttribute("errorMessage",
                    "Заказ не был изменен.");
        }
//        String orderId = "/order/" + id
        return "redirect:/order/" + id ;

    }




    //Обработчик установки статуса заказа в DONE-Оплачено (1)
    @GetMapping("status-done/{id}")
    public String statusToDone(@PathVariable Integer id){
        orderStatusService.statusToDone(id);

        return "redirect:/order";
    }

    //Обработчик установки статуса заказа в CANCELED-Отменено (2)
    @GetMapping("status-canceled/{id}")
    public String statusToCanceled(@PathVariable Integer id){
        orderStatusService.statusToCanceled(id);

        return "redirect:/order";
    }

    //Обработчик установки статуса заказа в CANCELED-Отменено (2)
    @GetMapping("status-canceled-by-client/{id}")
    public String statusToCanceledByClient(@PathVariable Integer id){
        orderStatusService.statusToCanceled(id);

        return "redirect:/order/client";
    }

}
