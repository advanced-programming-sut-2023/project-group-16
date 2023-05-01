package org.group16.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    public void testAddUser() {
        User.addUser("ali", "xyz789#$", "ali@gmail.com",
                "???", "!!!", "aaaa", "slogan");
        User user = User.getUserByName("ali");
        assertEquals("ali", user.getUsername());
    }

    @Test
    public void testGetUserByName() {
        User.addUser("hasan", "abc123!@", "hasan@gmail.com",
                "...", "---", "bbbb", "slogan");
        User user = User.getUserByName("hasan");
        assertEquals("abc123!@", user.getPassword());
        assertEquals("hasan@gmail.com", user.getEmail());
        assertEquals("...", user.getPasswordRecoveryQuestion());
        assertEquals("---", user.getPasswordRecoveryAnswer());
        assertEquals(0, user.getHighScore());
    }

    @Test
    public void testGettersAndSetters() {
        User.addUser("ali", "xyz789#$", "ali@gmail.com",
                "???", "!!!", "aaaa", "slogan");
        User user = User.getUserByName("ali");
        user.setEmail("ali123@gmail.com");
        assertEquals("ali123@gmail.com", user.getEmail());
        user.setPasswordRecoveryAnswer("1");
        assertEquals("1", user.getPasswordRecoveryAnswer());
        user.setPassword("qwe192&%");
        assertEquals("qwe192&%", user.getPassword());
        user.setNickname("ALI");
        assertEquals("ALI", user.getNickname());
        user.setHighScore(1000);
        assertEquals(1000, user.getHighScore());
        user.setSlogan("idk");
        assertEquals("idk", user.getSlogan());
        user.setPasswordRecoveryQuestion("what?");
        assertEquals("what?", user.getPasswordRecoveryQuestion());
    }
}
