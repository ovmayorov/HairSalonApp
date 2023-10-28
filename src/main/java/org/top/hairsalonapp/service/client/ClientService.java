package org.top.hairsalonapp.service.client;

import org.springframework.stereotype.Service;
import org.top.hairsalonapp.entity.Client;

import java.util.Optional;

@Service
public interface ClientService {
    /*
    //CRUD Create - Read - Update - Delete

    Optional<HairService> save(HairService hairService);                              //Создать новую услугу в каталоге
    Iterable<HairService> findAll();                                        //Получить все услуги
    Optional<HairService> findById(Integer id);                             //Получить услугу по id
    Optional<HairService> deleteById(Integer id);                           //Удалить услугу по id, возвращает удаляемую услугу
    Optional<HairService> updateById(Integer id, HairService hairService);  //редактировать услугу в каталоге
     */

    //Создать клиента
    Optional<Client> save(Client client);

    //Найти всех клиентов
    Iterable<Client> findAll();

    //Получить клиента по id
    Optional<Client> findById(Integer id);

    //Найти клиента по номеру телефона
    Optional<Client> findByPhoneNumber(String phoneNumber);

    //Редактировать клиента по id
    Optional<Client> updateById(Integer id, Client client);

    //Удалить клиента по id
    Optional<Client> deleteById(Integer id);


}
