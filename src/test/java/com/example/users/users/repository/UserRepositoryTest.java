package com.example.users.users.repository;

import com.example.users.users.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {
    private static UserRepository userRepository;

    @BeforeEach
    public void initialize () {
        userRepository = new UserRepository();
    }
    //Remember the repository is preloaded with three users ...
    // @BeforeEach makes sure that each test has the same environment.

    @Test
    public void testAddingAndDeletingUsers() {
        User u4 = new User(1,"Steve", "director");
        assertTrue(userRepository.addUser(u4));
        assertEquals(4, userRepository.findAll().size());
        userRepository.deleteUserById(2);
        assertEquals(3, userRepository.findAll().size());
        userRepository.deleteAll();
        assertEquals(0, userRepository.findAll().size());
    }

    @Test
    public void testFindingAUser () {
        User user3 = userRepository.findUserById(3).orElse(null);
        assertNotNull(user3);
        assertEquals("programmer", user3.occupation());
    }

    @Test
    public void testLists () {
        List<User> listOfUsers = new ArrayList<>();
        listOfUsers.add(new User(5,"Lupa","queen"));
        listOfUsers.add(new User(6, "Izzy","judge"));
        listOfUsers.add(new User(7, "Cowboy","jester"));
        assertEquals(3, userRepository.addAllUsers(listOfUsers));
        listOfUsers.add(new User(8, "Savvy","CEO"));
        assertEquals(1, userRepository.addAllUsers(listOfUsers));
    }

    @Test
    public void testUpdatingAUser () {
        User user3 = new User(3, "Richard", "manager");
        userRepository.updateUser(user3.id(), user3.name(), user3.occupation());
        var promotedUser3 = userRepository.findUserById(3);
        assertEquals(user3.name(), promotedUser3.map(User::name).orElse("Unknown"));
        assertEquals(user3.occupation(), promotedUser3.map(User::occupation).orElse("Unknown"));
    }
}
