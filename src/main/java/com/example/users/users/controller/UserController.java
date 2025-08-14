package com.example.users.users.controller;

import com.example.users.users.model.User;
import com.example.users.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
public class UserController {
    Logger logger = Logger.getLogger(UserController.class.getName());
    @Autowired
    UserRepository userRepository;

    //findAllUsers
   @GetMapping("/all")
    public ResponseEntity<List<User>> findAllUsers () {
        List<User> allUsers = userRepository.findAll();
       logger.info("ALL USERS COUNT IS " + allUsers.size());
        return ResponseEntity.ok(allUsers);
    }

    //findUserById
    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable int id) {
       assert(id >= 0);
       Optional<User> user = userRepository.findUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //deleteAll
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAll () {
       userRepository.deleteAll();
       return ResponseEntity.noContent().build();
    }
    //deleteUser
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserById (@PathVariable int id) {
       boolean deleted = userRepository.deleteUserById(id);
        logger.info((deleted ? "Deleted" : "Failed to delete") + " User " + id + ")");
       return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();

    }
    //addUser
    @PostMapping
    public  ResponseEntity<User> addUser(@RequestBody User user) {
        boolean added = userRepository.addUser(user);
        if(added) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(user.id())
                    .toUri();
            return ResponseEntity.created(location).body(user);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();

    }
    //addAllUsers
    @PostMapping("/addAll")
    public ResponseEntity<Map<String, Integer>> addAllUsers(@RequestBody List<User> users) {
       int addedCount = userRepository.addAllUsers(users);
       Map<String, Integer> response = new HashMap<>();
       response.put("requested", users.size());
       response.put("added", addedCount);
       return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    //updateUser
    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
       boolean updated = userRepository.updateUser(user.id(), user.name(), user.occupation());
       if (updated)
           return ResponseEntity.ok().build();
       return ResponseEntity.notFound().build();
    }

    @GetMapping("/create")
    public ResponseEntity<List<User>> fancyStuff(@RequestParam(value = "name", required = false) String name) {
        List<User> sameNameUsers;
        if (name == null || name.isBlank()) {
            sameNameUsers = userRepository.findAll();
        } else {
            sameNameUsers = userRepository.findUserByName(name);
        }
        if(sameNameUsers.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(sameNameUsers);
    }

}
