package com.italodea.pressureMonitor.PressureMonitor.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.italodea.pressureMonitor.PressureMonitor.entities.Pressure;
import com.italodea.pressureMonitor.PressureMonitor.entities.User;

@Repository
public interface PressureRepository extends JpaRepository<Pressure, Integer> {

    @Query("SELECT p FROM Pressure p WHERE p.userId = :id ORDER BY p.createdAt")
    List<Pressure> findAllByUserId(@Param("id") User id);

    @Query("SELECT p FROM Pressure p WHERE p.userId = :id ORDER BY p.createdAt")
    List<Pressure> findAllByUserIdOrderByUserId(@Param("id") User id);

    @Query("SELECT p FROM Pressure p WHERE p.id = :id AND p.userId = :userId")
    Optional<Pressure> findByIdAndUserId(@Param("id") int id, @Param("userId") User userId);
}