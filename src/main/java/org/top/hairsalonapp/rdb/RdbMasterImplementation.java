package org.top.hairsalonapp.rdb;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.top.hairsalonapp.entity.Master;
import org.top.hairsalonapp.rdb.repository.MasterRepository;
import org.top.hairsalonapp.service.master.MasterService;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RdbMasterImplementation implements MasterService {

    private final MasterRepository masterRepository;

    @Override
    public Optional<Master> save(Master master) {
        if(masterRepository.findByPhoneNumber(master.getPhoneNumber()).isPresent()){
            return Optional.empty();  //возвращаем пустой обьект null потому что клиент с таким номером телефона уже есть
        }
        return Optional.of(masterRepository.save(master));
    }

    @Override
    public Iterable<Master> findAll() {
        return masterRepository.findAll();
    }

    @Override
    public Optional<Master> findById(Integer id) {
        return masterRepository.findById(id);
    }

    @Override
    public Optional<Master> findByPhoneNumber(String phoneNumber) {
        return masterRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Optional<Master> updateById(Integer id, Master master) {
        Optional<Master> updatable = masterRepository.findById(id);       //находим услугу, которую нужно обновить
        Optional<Master> duplicatedByMasterPhoneNumber = masterRepository.findByPhoneNumber(master.getPhoneNumber()); //если клиент с таким номером телефона уже есть
        if(updatable.isPresent() &&
                (duplicatedByMasterPhoneNumber.isEmpty() ||
                        Objects.equals(duplicatedByMasterPhoneNumber.get().getId(), id))){
            //если нашли и при этом нету дубликата (т.е. номер телефона клиента после редактирования поменялся) или
            //телефон дубликата  является телефоном нашего изменяемого клиента.
            master.setId(id);
            return Optional.of(masterRepository.save(master));
        }else {

            return Optional.empty();  //если услуга не найдена, возвращаем null
        }
    }

    @Override
    public Optional<Master> deleteById(Integer id) {
        Optional<Master> removable = masterRepository.findById(id); //Находим удаляемого клиента
        if(removable.isPresent()){
            masterRepository.deleteById(id);        //если нашли - удаляем
        }

        return removable;   // возвращаем обьект который удалили.
    }
}
