package org.top.hairsalonapp.rdb;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.top.hairsalonapp.entity.HairService;
import org.top.hairsalonapp.rdb.repository.HairServiceRepository;
import org.top.hairsalonapp.service.hairService.HairServiceService;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RdbHairServiceImplementation implements HairServiceService {

    private final HairServiceRepository hairServiceRepository;

    @Override
    public Optional<HairService> save(HairService hairService) {
        if(hairServiceRepository.findByHairServiceName(hairService.getHairServiceName()).isPresent()){
            return Optional.empty();       //такая услуга уже есть, возвращается пустой объект, новая услуга не добавляется
        }
        return Optional.of(hairServiceRepository.save(hairService));
    }

    @Override
    public Iterable<HairService> findAll() {
        return hairServiceRepository.findAll();
    }

    @Override
    public Optional<HairService> findById(Integer id) {
        return hairServiceRepository.findById(id);
    }

    @Override
    public Optional<HairService> deleteById(Integer id) {
        Optional<HairService> removable = hairServiceRepository.findById(id); //находим услугу, которую хотим улалить
        if(removable.isPresent()){
            hairServiceRepository.deleteById(id);  //если нашли, то удаляем
        }
        return removable;   //возвращаем удаляемую услоугу, если ее нет, то вернется null
//        if(findById(id).isEmpty()){
//            return Optional.empty();   //удаляемый объект не найден, удалять нечего
//        }
//        return Optional.empty();
    }

    @Override
    public Optional<HairService> updateById(Integer id, HairService hairService) {
        Optional<HairService> updatable = hairServiceRepository.findById(id);       //находим услугу, которую нужно обновить
        Optional<HairService> duplicatedByNameService = hairServiceRepository.findByHairServiceName(hairService.getHairServiceName());
        if(updatable.isPresent() &&
                (duplicatedByNameService.isEmpty() ||
                        Objects.equals(duplicatedByNameService.get().getId(), id))){
            //если нашли и при этом нету дубликата (т.е. название услуги после редактирования поменялось) или
            //название услуги дубликата  является названием нашей изменяемой услуги.
            hairService.setId(id);
            return Optional.of(hairServiceRepository.save(hairService));
        }else {

            return Optional.empty();  //если услуга не найдена, возвращаем null
        }
    }
}
