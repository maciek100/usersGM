package com.example.users.users.repository;

import com.example.users.users.model.User;
import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UserRepositoryTest {
    private static UserRepository userRepo = new UserRepository();

    @Test
    public void testAddingUsers() {


        User u4 = new User(1,"Steve", "director");

        assertFalse(userRepo.addUser(u4));
        assertEquals(3, userRepo.findAll().size());
        userRepo.deleteUserById(2);
        assertEquals(2, userRepo.findAll().size());
        userRepo.deleteAll();
        assertEquals(0, userRepo.findAll().size());
    }

    @Test
    public void testLists () {
        List<User> listOfUsers = new ArrayList<>();
        listOfUsers.add(new User(5,"Lupa","queen"));
        listOfUsers.add(new User(6, "Izzy","judge"));
        listOfUsers.add(new User(7, "Cowboy","jester"));
        assertEquals(3,userRepo.addAllUsers(listOfUsers));
        listOfUsers.add(new User(8, "Savvy","CEO"));
        assertEquals(1,userRepo.addAllUsers(listOfUsers));
    }

    @BeforeTest
    public void jnitializeRepository() {
        User u1 = new User(1,"Steve","director");
        User u2 = new User(2, "Becky","manager");
        User u3 = new User(3, "Greta","trainer");
        userRepo.addUser(u1);
        userRepo.addUser(u2);
        userRepo.addUser(u3);
    }
}
