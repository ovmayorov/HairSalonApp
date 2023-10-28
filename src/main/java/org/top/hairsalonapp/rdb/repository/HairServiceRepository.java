package org.top.hairsalonapp.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.hairsalonapp.entity.HairService;

import java.util.Optional;

@Repository
public interface HairServiceRepository extends CrudRepository<HairService, Integer> {

    //Расширяем репозиторий , добавляем метод поиска услуги по имени.
    Optional<HairService> findByHairServiceName(String hairServiceName);
}
