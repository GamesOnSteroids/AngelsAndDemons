package com.gamesonsteroids.angelsanddemons.game;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private Player leader;
    private List<Player> members = new ArrayList<Player>();

    public Player getLeader() {
        return leader;
    }

    public void setLeader(Player leader) {
        this.leader = leader;
    }

    public List<Player> getMembers() {
        return members;
    }

    public void setMembers(List<Player> members) {
        this.members = members;
    }
}
