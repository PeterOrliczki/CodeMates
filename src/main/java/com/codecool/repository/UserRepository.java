package com.codecool.repository;

import com.codecool.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Users, Integer> {

    Optional<Users> findByName(String name);

    Optional<Users> findByEmail(String email);

    @Override
    <S extends Users> S save(S entity);
}
