package org.top.hairsalonapp.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.hairsalonapp.entity.Master;

import java.util.Optional;

@Repository
public interface MasterRepository extends CrudRepository<Master, Integer> {

    Optional<Master> findByPhoneNumber(String phoneNumber);
}
