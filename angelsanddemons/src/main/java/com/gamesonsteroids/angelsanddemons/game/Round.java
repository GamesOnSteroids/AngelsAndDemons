package com.gamesonsteroids.angelsanddemons.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Round {

    private Player leader;
    private List<Player> team;
    private Map<Player, Role> votes;

    private boolean isFinished;
    private Role winner;
    private int majorityVotes;
    private int minorityVotes;

    public void vote(Player player, Role voteAs) {
        this.votes.put(player, voteAs);
    }

    public Round() {
        leader = null;
        team = new ArrayList<Player>();
        votes = new HashMap<Player, Role>();
    }


    public void setLeader(Player leader) {
        this.leader = leader;
    }

    public Player getLeader() {
        return leader;
    }

    public int getMinorityVotes() {
        return minorityVotes;
    }

    public int getMajorityVotes() {
        return majorityVotes;
    }



    public List<Player> getTeam() {
        return team;
    }

    public void setTeam(List<Player> team) {
        this.team = team;
    }

    public Role getWinner() {
        return winner;
    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public void setWinner(Role winner) {
        this.winner = winner;
    }

    public void setMajorityVotes(int majorityVotes) {
        this.majorityVotes = majorityVotes;
    }

    public void setMinorityVotes(int minorityVotes) {
        this.minorityVotes = minorityVotes;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public Map<Player, Role> getVotes() {
        return votes;
    }
}
