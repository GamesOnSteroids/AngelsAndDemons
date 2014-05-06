package com.gamesonsteroids.angelsanddemons.game;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class GameSession {

    private static GameSession current;

    private List<Player> players;
    private Stack<Round> rounds;

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

    public Round getCurrentRound() {
        return rounds.peek();
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
        this.rounds = new Stack<Round>();

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

        nextRound();
    }



    public void finishRound() {
        Round round = GameSession.getCurrent().getCurrentRound();
        round.setFinished(true);

        int necessaryMinorityVotes = GameRules.getNecessaryMinorityVotes(GameSession.getCurrent().getRounds().size(), GameSession.getCurrent().getPlayers().size());


        for (Role vote : round.getVotes().values()) {
            if (vote == Role.Majority) {
                round.setMajorityVotes(round.getMajorityVotes()+1);
            } else {
                round.setMinorityVotes(round.getMinorityVotes()+1);
            }
        }

        if (round.getMinorityVotes() >= necessaryMinorityVotes) {
            round.setWinner(Role.Minority);
        } else {
            round.setWinner(Role.Majority);
        }

        if (round.getWinner() == Role.Majority) {
            majorityScore++;
        } else {
            minorityScore++;
        }
    }

    public void nextRound() {
        Round round = new Round();

        this.consecutiveDraws = 0;
        this.teamLeaderRotation++;

        round.setLeader(this.players.get((this.seed + this.teamLeaderRotation) % this.players.size()));

        this.rounds.push(round);
    }




    public void restartRound() {
        Round currentRound = this.getCurrentRound();

        currentRound.getTeam().clear();
        currentRound.getVotes().clear();

        currentRound.setLeader(this.players.get((this.seed + this.teamLeaderRotation) % this.players.size()));
    }

    public int getConsecutiveDraws() {
        return consecutiveDraws;
    }

    public void setConsecutiveDraws(int consecutiveDraws) {
        this.consecutiveDraws = consecutiveDraws;
    }



    public int getMajorityScore() {
        return majorityScore;
    }

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

    public Stack<Round> getRounds() {
        return rounds;
    }

}
