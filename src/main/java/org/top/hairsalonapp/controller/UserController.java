package org.top.hairsalonapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.hairsalonapp.entity.Client;
import org.top.hairsalonapp.entity.User;
import org.top.hairsalonapp.service.client.ClientService;
import org.top.hairsalonapp.service.user.UserService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("register")
public class UserController {

    private final UserService userService;
    private final ClientService clientService;

    @PostMapping("")
    public String register(
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam String passwordConfirmation,
            RedirectAttributes ra)
    {
        Optional<User> user = userService.register(login, password, passwordConfirmation);

        if(user.isPresent()){
            ra.addFlashAttribute("successMessage", "Регистрация прошла успешно. Войдите.");
        }else{
            ra.addFlashAttribute("errorMessage", "Ошибка регистрации.");
        }

        return "redirect:register/new-client/" + user.get().getId();

    }


    // Обработчики добавления нового мастера
    @GetMapping("master")
    public String addNewMaster(Model model, RedirectAttributes ra){

        User user = new User();                   //обьект для заполнения в форме
        model.addAttribute("newuser", user);
        return "user/login-master";
//        return "master/add-new-master-form";
    }

    @PostMapping("master")
    public String registerMaster(
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam String passwordConfirmation,
            RedirectAttributes ra)
    {
        Optional<User> user = userService.registerMaster(login, password, passwordConfirmation);

        if(user.isPresent()){
            ra.addFlashAttribute("successMessage", "Регистрация прошла успешно. ");
        }else{
            ra.addFlashAttribute("errorMessage", "Ошибка регистрации.");
        }

        return "redirect:/master/new-master/" + user.get().getId();

    }


    //Представления для добавления клиента отдельное от представления client чтобы не было видно всех клиентов
    @GetMapping("new-client/{userId}")
    public String addNewClient(@PathVariable Integer userId, Model model, RedirectAttributes ra){

        Optional<User> user = userService.findById(userId);
        if(user.isEmpty()){
            ra.addFlashAttribute("errorMessage", "Не найден логин/пароль.");
            return "redirect:/services";
        }
        model.addAttribute("user", user);

        Client client = new Client();                   //обьект для заполнения в форме
        client.setUser(user.get());
        model.addAttribute("newClient", client);
        return "user/add-new-client-form";
    }

    @PostMapping("new-client")
    public String addNewClient(Client client,  RedirectAttributes ra){
        Optional<Client> newClient = clientService.save(client);     //добавляем клиента

        //выводим служебные сообщения
        if(newClient.isPresent()){
            ra.addFlashAttribute("successMessage",
                    "Клиент: " + client.getClientName() + " " + client.getClientLastName() + " , " +
                            "успешно добавлен. Вы можете войти используя свой Логин и Пароль");
        } else{
            ra.addFlashAttribute("errorMessage", "Клиент с данным номером телефона уже есть в базе. ");
        }
        return "redirect:/login";

    }

}
