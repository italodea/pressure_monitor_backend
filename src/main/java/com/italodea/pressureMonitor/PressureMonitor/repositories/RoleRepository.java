package com.italodea.pressureMonitor.PressureMonitor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.italodea.pressureMonitor.PressureMonitor.entities.User;

@Repository
public interface RoleRepository extends JpaRepository<User, Long> {

}