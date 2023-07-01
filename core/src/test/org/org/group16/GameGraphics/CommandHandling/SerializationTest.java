package org.group16.GameGraphics.CommandHandling;

import org.group16.Model.*;
import org.group16.Model.Buildings.BuildingType;
import org.group16.Model.People.Soldier;
import org.group16.Model.People.SoldierDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class SerializationTest {
    User user;

    @BeforeEach
    void initialize() {
        User.addUser("player1", "pass", "email",
                "q1", "a1", "playerA", "slog");

        user = User.getUserByName("player1");
//        User user1 = User.getUserByName("player2");
    }

    @Test
    void testSerialize() {
        EndTurnCommand command = new EndTurnCommand(user);
        String data = command.serialize();
        EndTurnCommand deserialize = (EndTurnCommand) UserCommand.tryDeserialize(data);
        System.out.println(deserialize);
        System.out.println(deserialize == command);

    }

    @Test
    void testUUID() {
        Random random = new Random(0), random1 = new Random(0);
        System.out.println(UUIDResolver.generateUUID(random));
        System.out.println(UUIDResolver.generateUUID(random1));

        assertEquals(UUIDResolver.generateUUID(random),
                UUIDResolver.generateUUID(random1));

        assertEquals(UUIDResolver.generateUUID(random),
                UUIDResolver.generateUUID(random1));

        assertEquals(UUIDResolver.generateUUID(random),
                UUIDResolver.generateUUID(random1));
    }
}
