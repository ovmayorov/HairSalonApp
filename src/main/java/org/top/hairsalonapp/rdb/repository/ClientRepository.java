package org.top.hairsalonapp.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.hairsalonapp.entity.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client,Integer> {
    Optional<Client> findByPhoneNumber(String phoneNumber);
}
