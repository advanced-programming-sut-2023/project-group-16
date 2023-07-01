package org.group16.Model;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class UUIDResolver {
    private static final int RANDOM_VERSION = 4;
    private static final HashMap<UUID, Object> objects = new HashMap<>();

    public static UUID generateUUID(Random random) {
        long msb = 0;
        long lsb = 0;

        // (3) set all bit randomly
        if (random instanceof SecureRandom) {
            // Faster for instances of SecureRandom
            final byte[] bytes = new byte[16];
            random.nextBytes(bytes);
            msb = toNumber(bytes, 0, 8); // first 8 bytes for MSB
            lsb = toNumber(bytes, 8, 16); // last 8 bytes for LSB
        } else {
            msb = random.nextLong(); // first 8 bytes for MSB
            lsb = random.nextLong(); // last 8 bytes for LSB
        }

        // Apply version and variant bits (required for RFC-4122 compliance)
        msb = (msb & 0xffffffffffff0fffL) | (RANDOM_VERSION & 0x0f) << 12; // apply version bits
        lsb = (lsb & 0x3fffffffffffffffL) | 0x8000000000000000L; // apply variant bits

        // Return the UUID
        return new UUID(msb, lsb);
    }

    private static long toNumber(final byte[] bytes, final int start, final int length) {
        long result = 0;
        for (int i = start; i < length; i++) {
            result = (result << 8) | (bytes[i] & 0xff);
        }
        return result;
    }

    public static void subscribeObject(UUID uuid, Object obj) {
        objects.put(uuid, obj);
    }

    public static void unsubscribeObject(UUID uuid) {
        objects.remove(uuid);
    }

    public static Object getObject(UUID uuid) {
        return objects.get(uuid);
    }

    public static void subscribeObject(ReferenceSerializable obj) {
        subscribeObject(obj.getUuid(), obj);
    }

    public static void unsubscribeObject(ReferenceSerializable obj) {
        unsubscribeObject(obj.getUuid());
    }
}
