package org.group16.Model;

public class GameManager {
    public static void createSoloTeam(Kingdom kingdom) {
        kingdom.getTeam().removeKingdom(kingdom);
        kingdom.setTeam(new Team(kingdom));
    }

    public static void addKingdomToTeam(Kingdom kingdom, Team team) {
        kingdom.getTeam().removeKingdom(kingdom);
        kingdom.setTeam(team);
        team.addKingdom(kingdom);
    }

    public static void createNewGameObject(Kingdom kingdom, GameObject gameObject) {

    }
}
