package com.example.users.users.repository;

import com.example.users.users.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class UserRepository {
    private final List<User> myUsers = new ArrayList<>();
    UserRepository () {
        myUsers.add(new User(1, "Tom","manager"));
        myUsers.add(new User(2, "Natasza","developer"));
        myUsers.add(new User(3, "Rich","programmer"));
    }
    //findAllUsers
    public List<User> findAll() {
        return myUsers.stream()
                .map(User::new)
                .toList();
    }

    //findUserById
    public Optional<User> findUserById(int searchId) {
        return myUsers.stream()
                .filter(user -> user.id() == searchId)
                .findFirst();
    }
    //findUserByName
    public List<User> findUserByName(String searchName) {
        return myUsers.stream()
                .filter(user -> Objects.equals(user.name(), searchName))
                .toList();
    }

    //deleteAll
    public void deleteAll () {
        myUsers.clear();
    }
    //deleteUser
    public boolean deleteUserById (int searchId) {
        boolean existed = myUsers.stream().anyMatch(user -> user.id().equals(searchId));
        myUsers.removeIf(user -> Integer.valueOf(searchId).equals(user.id()));
        return existed;
    }
    //addUser
    public boolean addUser(User newUser) {
        boolean exists = myUsers.contains(newUser);
        if (exists) {
            return false;
        } else {
            myUsers.add(newUser);
            return true;
        }
    }
    //addAllUsers
    public int addAllUsers(List<User> newUsers) {
        int initialSize = myUsers.size();
        for (User u : newUsers) {
            if (myUsers.stream().noneMatch(o -> o.id().equals(u.id()))) {
                myUsers.add(u);
            }
        }
        return myUsers.size() - initialSize;

    }
    //updateUser
    public boolean updateUser(int targetId, String newName, String newOccupation) {
//        AtomicBoolean changed = new AtomicBoolean(false);
//        myUsers.replaceAll(user -> {
//                    if (user.id().equals(id)) {
//                        changed.set(true);
//                        return new User(user.id(), user.name(), occupation);
//                    }
//                    return user;
//                });
//        return changed.get();
        for (int i = 0; i < myUsers.size(); i++) {
            User user = myUsers.get(i);
            if (user.id().equals(targetId) && !user.occupation().equals(newOccupation)) {
                myUsers.set(i, new User(user.id(),
                        newName != null ? newName : user.name(),
                        newOccupation != null ? newOccupation : user.occupation()));
                return true;
            }
        }
        return false;
    }
}
