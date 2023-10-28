package org.top.hairsalonapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.hairsalonapp.entity.Master;
import org.top.hairsalonapp.entity.Order;
import org.top.hairsalonapp.entity.User;
import org.top.hairsalonapp.service.master.MasterService;
import org.top.hairsalonapp.service.order.OrderService;
import org.top.hairsalonapp.service.user.UserService;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("master")
public class MasterController {

    //внедряем наш MasterService для реализации запросов
    private final MasterService masterService;
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("")
    public String findAll(Model model){

        Iterable<Master> masters = masterService.findAll();   //Находим всех мастеров
        if(masters.iterator().hasNext()){
            model.addAttribute("masters", masters);
        }else{
            model.addAttribute("masters", null);
        }
        return "master/masters-list";

    }



    // Обработчики добавления нового мастера
    @GetMapping("new-master/{masterId}")
    public String addNewClient(@PathVariable Integer masterId, Model model, RedirectAttributes ra){
        Optional<User> user = userService.findById(masterId);
        if(user.isEmpty()){
            ra.addFlashAttribute("errorMessage", "Не найден логин/пароль.");
            return "redirect:/master";
        }
        model.addAttribute("user", user);


        Master master = new Master();                   //обьект для заполнения в форме
        master.setUser(user.get());
        model.addAttribute("newMaster", master);
        //return "user/login-master";
        return "master/add-new-master-form";
    }

    @PostMapping("new-master")
    public String addNewClient(Master master, RedirectAttributes ra){
        Optional<Master> newMaster = masterService.save(master);     //добавляем мастера
        //выводим служебные сообщения
        if(newMaster.isPresent()){
            ra.addFlashAttribute("successMessage",
                    "Мастер: " + master.getName() + " " + master.getLastName() + " , успешно добавлен.");
        } else{
            ra.addFlashAttribute("errorMessage", "Мастер с данным номером телефона уже есть в базе. ");
        }
        return "redirect:/master";

    }



    //Обработчик удаления мастера по id
    @GetMapping("delete/{id}")
    public String deleteById(@PathVariable Integer id, RedirectAttributes ra){
        Optional<Master> removed = masterService.deleteById(id);
        if(removed.isPresent()){
            ra.addFlashAttribute("successMessage", "Мастер: " + removed.get().getName() + " " + removed.get().getLastName() + ", удален из базы.");
        }
        return "redirect:/master";
    }


    //Обработчик поиска клиента по id, (вывод детальной информации о парикмахере)
    @GetMapping("{id}")
    public String masterDetails(@PathVariable Integer id, Model model){
        //Находим мастера
        Optional<Master> master = masterService.findById(id);
        Iterable<Order> orders = orderService.findAll();
        boolean isMasterHasOrder = false;
        for (Order order : orders) {
            if(order.getMaster().getId() == master.get().getId()){
                isMasterHasOrder = true;
            }
        }

        if(master.isPresent()){
            model.addAttribute("masterDetails", master.get());
            if(!isMasterHasOrder){
                model.addAttribute("masterHasOrder", 0);
            }
        }else{
            model.addAttribute("masterDetails", null);
        }
        return "master/master-details";

    }



    //    Обработчики обновления/редактирования клиента
    @GetMapping("update/{id}")
    public String masterUpdate(@PathVariable Integer id, Model model){
        Optional<Master> master = masterService.findById(id);
        if(master.isPresent()){
            model.addAttribute("masterUpdate", master.get());
        }else{
            model.addAttribute("masterUpdate", null);
        }
        return "master/master-update-form";
    }

    @PostMapping("update/{id}")
    public String masterUpdate(@PathVariable Integer id, Master master, RedirectAttributes ra){
        Optional<Master> updated = masterService.updateById(id, master);
        if(updated.isPresent()){
            ra.addFlashAttribute("successMessage",
                    "Данные мастера: \"" + master.getLastName() + "\" успешно обновлены.");
        } else{
            ra.addFlashAttribute("errorMessage",
                    "Данные мастера не были обновлены. Введен номер телефона другого мастера. ");
        }
        return "redirect:/master";            //перенаправиться на список клиентов
    }
}
