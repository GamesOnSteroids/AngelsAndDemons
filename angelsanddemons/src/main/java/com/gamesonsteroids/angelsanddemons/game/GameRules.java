package com.gamesonsteroids.angelsanddemons.game;

public class GameRules {
    public static int MaxPlayers = 10;
    public static int MinPlayers = 5;
    public static int DrawsToWin = 5;
    public static int PointsToWin = 3;


    public static int getTeamSize(int round, int playerCount) {

        switch (playerCount) {
            case 5:
                switch (round) {
                    case 1: return 2;
                    case 2: return 3;
                    case 3: return 2;
                    case 4: return 3;
                    case 5: return 3;
                }
            case 6:
                switch (round) {
                    case 1: return 2;
                    case 2: return 3;
                    case 3: return 4;
                    case 4: return 3;
                    case 5: return 4;
                }
            case 7:
                switch (round) {
                    case 1: return 2;
                    case 2: return 3;
                    case 3: return 3;
                    case 4: return 4;
                    case 5: return 4;
                }
            case 8:
                switch (round) {
                    case 1: return 3;
                    case 2: return 4;
                    case 3: return 4;
                    case 4: return 5;
                    case 5: return 5;
                }
            case 9:
                switch (round) {
                    case 1: return 3;
                    case 2: return 4;
                    case 3: return 4;
                    case 4: return 5;
                    case 5: return 5;
                }
            case 10:
                switch (round) {
                    case 1: return 3;
                    case 2: return 4;
                    case 3: return 4;
                    case 4: return 5;
                    case 5: return 5;
                }
        }
        throw new Error("No configuration for round: " + round + " playerCount: " + playerCount);
    }

    public static int getMinorityCount(int playerCount) {

        switch (playerCount) {
            case 5:
                return 2;
            case 6:
                return 2;
            case 7:
                return 3;
            case 8:
                return 3;
            case 9:
                return 3;
            case 10:
                return 4;
        }

        throw new Error("No configuration for playerCount: " + playerCount);
    }

    public static int getNecessaryMinorityVotes(int round, int playerCount) {
        if (playerCount >= 7 && round == 4) {
            return 2;
        }
        return 1;
    }
}
