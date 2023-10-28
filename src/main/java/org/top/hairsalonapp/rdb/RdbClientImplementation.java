package org.top.hairsalonapp.rdb;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.top.hairsalonapp.entity.Client;
import org.top.hairsalonapp.rdb.repository.ClientRepository;
import org.top.hairsalonapp.service.client.ClientService;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RdbClientImplementation implements ClientService {

    private final ClientRepository clientRepository;
    @Override
    public Optional<Client> save(Client client) {
        if(clientRepository.findByPhoneNumber(client.getPhoneNumber()).isPresent()){
            return Optional.empty();  //возвращаем пустой обьект null потому что клиент с таким номером телефона уже есть
        }
        return Optional.of(clientRepository.save(client));
    }

    @Override
    public Iterable<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findById(Integer id) {

        return clientRepository.findById(id);
    }

    @Override
    public Optional<Client> findByPhoneNumber(String phoneNumber) {
        return clientRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Optional<Client> updateById(Integer id, Client client) {
        Optional<Client> updatable = clientRepository.findById(id);       //находим услугу, которую нужно обновить
        Optional<Client> duplicatedByClientPhoneNumber = clientRepository.findByPhoneNumber(client.getPhoneNumber()); //если клиент с таким номером телефона уже есть
        if(updatable.isPresent() &&
                (duplicatedByClientPhoneNumber.isEmpty() ||
                        Objects.equals(duplicatedByClientPhoneNumber.get().getId(), id))){
            //если нашли и при этом нету дубликата (т.е. номер телефона клиента после редактирования поменялся) или
            //телефон дубликата  является телефоном нашего изменяемого клиента.
            client.setId(id);
            return Optional.of(clientRepository.save(client));
        }else {

            return Optional.empty();  //если услуга не найдена, возвращаем null
        }



    }

    @Override
    public Optional<Client> deleteById(Integer id) {
        Optional<Client> removable = clientRepository.findById(id); //Находим удаляемого клиента
        if(removable.isPresent()){
            clientRepository.deleteById(id);        //если нашли - удаляем
        }

        return removable;   // возвращаем обьект который удалили.
    }
}
