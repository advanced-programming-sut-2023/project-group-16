package org.group16.Model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    void addUser() {
        User.addUser("testUser", "pass", "email",
                "q1", "a1", "nick", "slog");

        User.addUser("testUser1", "pass", "email",
                "q1", "a1", "nick", "slog");

        User.addUser("testUser2", "pass", "email",
                "q1", "a1", "nick", "slog");

        User.addUser("testUser3", "pass", "email",
                "q1", "a1", "nick", "slog");
    }

    @Test
    void getUser() {
        addUser();

        User user = User.getUserByName("testUser");
        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
    }

    @Test
    void changeUserDetails() {
        addUser();

        User user = User.getUserByName("testUser");

        user.setPassword("cng");
        user = User.getUserByName("testUser");
        assertEquals("cng", user.getPassword());

        user.setEmail("cng");
        user = User.getUserByName("testUser");
        assertEquals("cng", user.getEmail());

        user.setSlogan("cng");
        user = User.getUserByName("testUser");
        assertEquals("cng", user.getSlogan());

        user.setNickname("cng");
        user = User.getUserByName("testUser");
        assertEquals("cng", user.getNickname());

        user.setPasswordRecoveryAnswer("cng");
        user = User.getUserByName("testUser");
        assertEquals("cng", user.getPasswordRecoveryAnswer());

        user.setPasswordRecoveryQuestion("cng");
        user = User.getUserByName("testUser");
        assertEquals("cng", user.getPasswordRecoveryQuestion());

        user.addScore(10 - user.getScore());
        user = User.getUserByName("testUser");
        assertEquals(10, user.getScore());
    }

    @Test
    void changeUserUsername() {
        addUser();

        User user = User.getUserByName("testUser");
        user.setUsername("newTestUser");
        user = User.getUserByName("testUser");
        assertNull(user);
        user = User.getUserByName("newTestUser");
        assertNotNull(user);
        assertEquals("newTestUser", user.getUsername());

        user.setUsername("testUser");
    }

    @Test
    void getUsers() {
        addUser();

        ArrayList<User> users = User.getAllUsers();
//        System.out.println(users);
        assertEquals(4, users.size());
    }

    @Test
    void userEqualsTest() {
        addUser();
        User user = User.getUserByName("testUser");
        User user1 = User.getUserByName("testUser1");
        assertEquals(user, User.getUserByName("testUser"));
        assertNotEquals(user, user1);
    }
}
