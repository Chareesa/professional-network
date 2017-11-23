package com.chareesa.exam3.repositories;

import com.chareesa.exam3.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    boolean existsByEmail(final String email);
    List<User> findAll();
}
