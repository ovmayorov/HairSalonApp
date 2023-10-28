package org.top.hairsalonapp.service.hairService;

import org.springframework.stereotype.Service;
import org.top.hairsalonapp.entity.HairService;

import java.util.Optional;

//Контракт для работы с каталогом услуг
@Service
public interface HairServiceService {
    //CRUD Create - Read - Update - Delete

    Optional<HairService> save(HairService hairService);                              //Создать новую услугу в каталоге
    Iterable<HairService> findAll();                                        //Получить все услуги
    Optional<HairService> findById(Integer id);                             //Получить услугу по id
    Optional<HairService> deleteById(Integer id);                           //Удалить услугу по id, возвращает удаляемую услугу
    Optional<HairService> updateById(Integer id, HairService hairService);  //редактировать услугу в каталоге
}
