package org.group16.Model;

import java.io.Serializable;
import java.util.UUID;

public record GameInfo(UUID gameID, long randomSeed, String mapname, PlayerList playerList) implements Serializable {
}
