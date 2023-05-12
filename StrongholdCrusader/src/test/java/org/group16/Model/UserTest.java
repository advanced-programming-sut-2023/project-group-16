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

    @Test
    public void testColorPrint() {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_BLACK = "\u001B[30m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_PURPLE = "\u001B[35m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_WHITE = "\u001B[37m";
        String ANSI_BLACK_BACKGROUND = "\u001B[40m";
        String ANSI_RED_BACKGROUND = "\u001B[41m";
        String ANSI_GREEN_BACKGROUND = "\u001B[42m";
        String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
        String ANSI_BLUE_BACKGROUND = "\u001B[44m";
        String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
        String ANSI_CYAN_BACKGROUND = "\u001B[46m";
        String ANSI_WHITE_BACKGROUND = "\u001B[47m";

        System.out.println(ANSI_RED + "This text is red!" + ANSI_RESET);
        System.out.println(ANSI_GREEN_BACKGROUND + "This text has a green background but default text!" + ANSI_RESET);
        System.out.println(ANSI_RED + "This text has red text but a default background!" + ANSI_RESET);
        System.out.println(ANSI_GREEN_BACKGROUND + ANSI_RED + "This text has a green background and red text!" + ANSI_RESET);
    }
}
