package com.italodea.pressureMonitor.PressureMonitor.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.italodea.pressureMonitor.PressureMonitor.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Query("SELECT u FROM User u WHERE u.id = ?1")
    Optional<User> findById(int id);

    @Query("DELETE FROM User u WHERE u.id = ?1")
    void deleteById(int id);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findOneByEmail(String email);

}