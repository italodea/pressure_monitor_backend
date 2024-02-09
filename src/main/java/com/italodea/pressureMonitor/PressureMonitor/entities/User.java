package com.italodea.pressureMonitor.PressureMonitor.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.italodea.pressureMonitor.PressureMonitor.entities.templates.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @JsonIgnore // Exclude password field from response
    @Column(nullable = false)
    private String password;
    @Column(name = "google_id", nullable = true, unique = true)
    private String googleId;

}