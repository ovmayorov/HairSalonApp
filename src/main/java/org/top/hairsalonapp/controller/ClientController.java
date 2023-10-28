package org.top.hairsalonapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.hairsalonapp.entity.Client;
import org.top.hairsalonapp.entity.Order;
import org.top.hairsalonapp.entity.OrderedServices;
import org.top.hairsalonapp.service.client.ClientService;
import org.top.hairsalonapp.service.order.OrderService;
import org.top.hairsalonapp.service.order.OrderStatusService;
import org.top.hairsalonapp.service.orderedServices.OrderedServicesService;
import org.top.hairsalonapp.service.user.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Controller
@RequestMapping("client")
public class ClientController {

    //внедряем наш ClientService для реализации запросов
    private final ClientService clientService;
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("")
    public String findAll(Model model){

        Iterable<Client> clients = clientService.findAll();   //Находим всех клиентов

        if(clients.iterator().hasNext()){
            model.addAttribute("clients", clients);
        }else{
            model.addAttribute("clients", null);
        }
        return "client/clients-list";

    }


        // Обработчики добавления нового клиента
        @GetMapping("new-client")
        public String addNewClient(Model model){
            Client client = new Client();                   //обьект для заполнения в форме
            model.addAttribute("newClient", client);
            return "client/add-new-client-form";
        }

    @PostMapping("new-client")
    public String addNewClient(Client client, RedirectAttributes ra){
        Optional<Client> newClient = clientService.save(client);     //добавляем клиента
        //выводим служебные сообщения
        if(newClient.isPresent()){
            ra.addFlashAttribute("successMessage",
                    "Клиент: " + client.getClientName() + " " + client.getClientLastName() + " , успешно добавлен.");
        } else{
            ra.addFlashAttribute("errorMessage", "Клиент с данным номером телефона уже есть в базе. ");
        }
        return "redirect:/client";

    }



    //Обработчик удаления клиента по id
    @GetMapping("delete/{id}")
    public String deleteById(@PathVariable Integer id, RedirectAttributes ra){
        Optional<Client> removed = clientService.deleteById(id);
        if(removed.isPresent()){
            ra.addFlashAttribute("successMessage", "Клиент: " + removed.get().getClientName() + " " + removed.get().getClientLastName() + ", удален из базы.");
        }

        return "redirect:/client";
    }


    //Обработчик поиска клиента по id, (вывод детальной информации о клиенте)
    @GetMapping("{id}")
    public String clientDetails(@PathVariable Integer id, Model model){
        //Находим клиента
        Optional<Client> client = clientService.findById(id);
        Iterable<Order> allOrders = orderService.findAll();
        boolean isClientHaveOrder = false;
        for (Order order: allOrders) {
            if(order.getClient().getId() == client.get().getId()){
                isClientHaveOrder = true;
            }
        }

        if(client.isPresent()) {
            model.addAttribute("clientDetails", client.get());
            if (!isClientHaveOrder) {
                model.addAttribute("clientOrders", 0);
            }

        }else{
            model.addAttribute("clientDetails", null);
        }
        return "client/client-details";

    }



    //    Обработчики обновления/редактирования клиента
    @GetMapping("update/{id}")
    public String clientUpdate(@PathVariable Integer id, Model model){
        Optional<Client> client = clientService.findById(id);
        if(client.isPresent()){
            model.addAttribute("clientUpdate", client.get());
        }else{
            model.addAttribute("clientUpdate", null);
        }
        return "client/client-update-form";
    }

    @PostMapping("update/{id}")
    public String clientUpdate(@PathVariable Integer id, Client client, RedirectAttributes ra){
        Optional<Client> updated = clientService.updateById(id, client);
        if(updated.isPresent()){
            ra.addFlashAttribute("successMessage",
                    "Данные клиента: \"" + client.getClientLastName() + "\" успешно обновлены.");
        } else{
            ra.addFlashAttribute("errorMessage",
                    "Данные клиента не были обновлены. Введен номер телефона другого клиента. ");
        }
        return "redirect:/client";            //перенаправиться на список клиентов
    }
}
