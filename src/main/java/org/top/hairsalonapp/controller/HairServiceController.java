package org.top.hairsalonapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.hairsalonapp.entity.HairService;
import org.top.hairsalonapp.service.hairService.HairServiceService;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("services")
public class HairServiceController {

    private final HairServiceService hairServiceService;

    @GetMapping("")
    public String findAll(Model model){
        Iterable<HairService> services =  hairServiceService.findAll();    //получить список обьектов
        if(services.iterator().hasNext()) {
            model.addAttribute("services", services);       //добавить обьекты в контекст представления
        } else{
            model.addAttribute("services", null);
        }
        return "hairServices/hair-services-list";                   //вернуть предствление
    }

//    Обработчики добавления новой услуги
    @GetMapping("new-service")
    public String addNewService(Model model){
        HairService hairService = new HairService();    //обьект для заполнения в форме
        model.addAttribute("hairService", hairService);
        return "hairServices/add-new-service-form";
    }

    @PostMapping("new-service")
    public String addNewService(HairService hairService, RedirectAttributes ra){
        Optional<HairService> newHairService = hairServiceService.save(hairService);   //добавить услугу
        if(newHairService.isPresent()){
            ra.addFlashAttribute("successMessage",
                    "Услуга \"" + hairService.getHairServiceName() + "\" успешно добавлена.");
        } else{
            ra.addFlashAttribute("errorMessage",
                    "Услуга не добавлена. Услуга с названием \"" + hairService.getHairServiceName() + "\" уже существует.");
        }
        return "redirect:/services";            //перенаправиться на список улуг
    }

    //Обработчик удаления
    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id, RedirectAttributes ra){
        Optional<HairService> removed = hairServiceService.deleteById(id);
        if(removed.isPresent()){
            ra.addFlashAttribute("successMessage", "Услуга \"" + removed.get().getHairServiceName() + "\" удалена из каталога."  );
        }
        return "redirect:/services";
    }

    //Обработчик details
    @GetMapping("{id}")
    public String details(@PathVariable Integer id, Model model){
        Optional<HairService> hairService = hairServiceService.findById(id);
        if(hairService.isPresent()){
            model.addAttribute("hairServiceDetails", hairService.get());
        } else{
            model.addAttribute("hairServiceDetails", null);
        }
        return "hairServices/hair-service-details";
    }

    //    Обработчики обновления услуги  (редактирование услуги)
    @GetMapping("update/{id}")
    public String updateService(@PathVariable Integer id, Model model){
        Optional<HairService> hairService = hairServiceService.findById(id);    //обьект для заполнения в форме
        if(hairService.isPresent()){
            model.addAttribute("hairServiceUpdate", hairService.get());
        } else{
            model.addAttribute("hairServiceUpdate", null);
        }

        return "hairServices/update-service-form";
    }

    @PostMapping("update/{id}")
    public String updateService(@PathVariable Integer id, HairService hairService, RedirectAttributes ra){
        Optional<HairService> updated = hairServiceService.updateById(id, hairService);
        if(updated.isPresent()){
            ra.addFlashAttribute("successMessage",
                    "Услуга \"" + hairService.getHairServiceName() + "\" успешно обновлена.");
        } else{
            ra.addFlashAttribute("errorMessage",
                    "Услуга не была обновлена. Возможно дублирование названия услуги с существующей услугой.");
        }
        return "redirect:/services";            //перенаправиться на список улуг
    }

}

























