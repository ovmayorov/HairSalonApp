package org.top.hairsalonapp.service.master;

import org.springframework.stereotype.Service;

import org.top.hairsalonapp.entity.Master;

import java.util.Optional;

@Service
public interface MasterService {



    //Создать парикмахера
    Optional<Master> save(Master master);

    //Найти всех парикмахеров
    Iterable<Master> findAll();

    //Получить парикмахера по id
    Optional<Master> findById(Integer id);

    //Найти клиента по номеру телефона
    Optional<Master> findByPhoneNumber(String phoneNumber);

    //Редактировать мастера по id
    Optional<Master> updateById(Integer id, Master master);

    //Удалить мастера по id
    Optional<Master> deleteById(Integer id);

}
