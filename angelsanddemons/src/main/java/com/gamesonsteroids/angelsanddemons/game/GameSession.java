package com.gamesonsteroids.angelsanddemons.game;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameSession {

    private static GameSession current;

    private List<Player> players;
    private int round;
    private Team currentTeam;
    private Map<Player, Role> votes;
    private int seed;
    private Random random;
    private int majorityScore;
    private int minorityScore;
    private int consecutiveDraws;
    private int teamLeaderRotation;


    public static GameSession getCurrent() {
        if (current == null) {
            current = new GameSession();
        }

        return current;
    }

    public List<Player> getPlayers() {
        return players;
    }


    public void createGame(int playerCount, CharSequence[] defaultPlayerNames) {

        this.seed = (int) (new Date().getTime() % Integer.MAX_VALUE);
        this.teamLeaderRotation = 0;
        this.random = new Random(this.seed);
        this.consecutiveDraws = 0;
        this.majorityScore = 0;
        this.minorityScore = 0;
        this.players = new ArrayList<Player>();

        for (int i = 0; i < playerCount; i++) {
            Player player = new Player();
            player.setName(defaultPlayerNames.length > i ? defaultPlayerNames[i] :  GameRules.DefaultNames[i]);
            players.add(player);
        }

        for (Player player : players) {
            player.setRole(Role.Majority);
        }

        int demonCount = GameRules.getMinorityCount(players.size());

        while (demonCount > 0) {
            Player demonCandidate = players.get(random.nextInt(players.size()));
            if (demonCandidate.getRole() != Role.Minority) {
                demonCandidate.setRole(Role.Minority);
                demonCount--;
            }
        }

        startRound(0);
    }

    public void startRound(int round) {
        if (this.round != round) {
            this.round = round;
            this.consecutiveDraws = 0;
        }
        this.teamLeaderRotation++;

        this.currentTeam = new Team();
        this.currentTeam.setLeader(this.players.get((this.seed + this.teamLeaderRotation) % this.players.size()));
        this.votes = new HashMap<Player, Role>();

    }



    public void vote(Player player, Role voteAs) {
        this.votes.put(player, voteAs);
    }


    public Team getCurrentTeam() {
        return currentTeam;
    }

    public int getConsecutiveDraws() {
        return consecutiveDraws;
    }

    public void setConsecutiveDraws(int consecutiveDraws) {
        this.consecutiveDraws = consecutiveDraws;
    }

    public int getRound() {
        return round;
    }

    public Map<Player, Role> getVotes() {
        return votes;
    }

    public int getMajorityScore() {
        return majorityScore; }

    public int getMinorityScore() {
        return minorityScore; }

    public void setMinorityScore(int minorityScore) {
        this.minorityScore = minorityScore;
    }

    public void setMajorityScore(int majorityScore) {
        this.majorityScore = majorityScore;
    }

    public Random getRandom() {
        return random;
    }
}
