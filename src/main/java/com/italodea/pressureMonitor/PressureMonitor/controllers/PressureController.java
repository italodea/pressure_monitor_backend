package com.italodea.pressureMonitor.PressureMonitor.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.italodea.pressureMonitor.PressureMonitor.auth.JwtTokenProvider;
import com.italodea.pressureMonitor.PressureMonitor.entities.Pressure;
import com.italodea.pressureMonitor.PressureMonitor.entities.User;
import com.italodea.pressureMonitor.PressureMonitor.repositories.PressureRepository;

@RestController
@RequestMapping("/pressure")
class PressureController {

    @Autowired
    PressureRepository repository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestHeader("Authorization") String authorization) {
        try {
            User user = jwtTokenProvider.getUser(authorization.replace("Bearer ", ""));
            List<Pressure> items = new ArrayList<>();

            repository.findAllByUserId(user).forEach(items::add);

            return items.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Pressure> getById(@RequestHeader("Authorization") String authorization,
            @PathVariable("id") int id) {

        User user = jwtTokenProvider.getUser(authorization.replace("Bearer ", ""));
        Optional<Pressure> existingItemOptional = repository.findByIdAndUserId(id, user);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Pressure> create(@RequestHeader("Authorization") String authorization,
            @RequestBody Pressure item) {
        try {
            User user = jwtTokenProvider.getUser(authorization.replace("Bearer ", ""));
            item.setUserId(user);
            Pressure savedItem = repository.save(item);
            return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Pressure> update(@RequestHeader("Authorization") String authorization,
            @PathVariable("id") int id, @RequestBody Pressure item) {
        User user = jwtTokenProvider.getUser(authorization.replace("Bearer ", ""));
        Optional<Pressure> existingItemOptional = repository.findByIdAndUserId(id, user);
        if (existingItemOptional.isPresent()) {
            Pressure existingItem = existingItemOptional.get();
            existingItem.setUserId(user);
            if (item.getDiastole() != 0)
                existingItem.setDiastole(item.getDiastole());
            if (item.getSystole() != 0)
                existingItem.setSystole(item.getSystole());
            return new ResponseEntity<>(repository.save(existingItem), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@RequestHeader("Authorization") String authorization,
            @PathVariable("id") int id) {
        try {
            User user = jwtTokenProvider.getUser(authorization.replace("Bearer ", ""));
            Optional<Pressure> p = repository.findByIdAndUserId(id, user);
            if (p.isPresent()) {
                repository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}