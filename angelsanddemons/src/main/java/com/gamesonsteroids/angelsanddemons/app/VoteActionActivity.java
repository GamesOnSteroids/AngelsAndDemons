package com.gamesonsteroids.angelsanddemons.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamesonsteroids.angelsanddemons.game.GameRules;
import com.gamesonsteroids.angelsanddemons.game.GameSession;
import com.gamesonsteroids.angelsanddemons.game.Player;
import com.gamesonsteroids.angelsanddemons.game.Role;

import java.util.List;
import java.util.Map;
import java.util.Random;


public class VoteActionActivity extends GameActivity {

    private int currentPlayerIndex;
    private boolean hidden;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_action);

        initHeader();

        if (savedInstanceState != null) {
            currentPlayerIndex = savedInstanceState.getInt("currentPlayerIndex");
            hidden = savedInstanceState.getBoolean("hidden");
        } else {
            currentPlayerIndex = 0;
            hidden = true;
        }

        setPlayer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt("currentPlayerIndex", currentPlayerIndex);
        outState.putBoolean("hidden", hidden);

        super.onSaveInstanceState(outState);
    }

    public void onVoteClick(View view) {
        Role voteAs = (Role)view.getTag();

        List<Player> members = GameSession.getCurrent().getCurrentTeam().getMembers();

        Player player = members.get(this.currentPlayerIndex);

        GameSession.getCurrent().vote(player, voteAs);

        int index = this.currentPlayerIndex + 1;
        if (index < members.size()) {
            this.currentPlayerIndex = index;
            this.hidden = true;
            setPlayer();
        } else {

            Map<Player, Role> votes = GameSession.getCurrent().getVotes();

            int minorityVotes = 0;
            for (Role vote : votes.values()) {
                if (vote == Role.Minority) {
                    minorityVotes++;
                }
            }

            boolean minorityWon = minorityVotes >= GameRules.getNecessaryMinorityVotes(GameSession.getCurrent().getRound(), GameSession.getCurrent().getPlayers().size());

            if (minorityWon) {
                GameSession.getCurrent().setMinorityScore(GameSession.getCurrent().getMinorityScore() + 1);
            } else {
                GameSession.getCurrent().setMajorityScore(GameSession.getCurrent().getMajorityScore() + 1);
            }

            if (GameSession.getCurrent().getMajorityScore() == GameRules.PointsToWin || GameSession.getCurrent().getMinorityScore() == GameRules.PointsToWin) {
                Intent intent = new Intent(this, GameOverActivity.class);
                this.startActivity(intent);
            } else {
                Intent intent = new Intent(this, ActionResultActivity.class);
                this.startActivity(intent);
            }
        }

    }

    private void setPlayer() {

        List<Player> members = GameSession.getCurrent().getCurrentTeam().getMembers();

        Player player = members.get(this.currentPlayerIndex);

        TextView textView = (TextView)findViewById(R.id.activity_vote_player);
        textView.setText(getString(R.string.your_turn, player.getName()));

        View turnButton = findViewById(R.id.vote_action_turn);
        View choiceLayout = findViewById(R.id.vote_action_choice_layout);

        ImageView vote1 = (ImageView)findViewById(R.id.vote1);
        ImageView vote2 = (ImageView)findViewById(R.id.vote2);

        ImageView[] votes = new ImageView[] { vote1, vote2 };



        if (player.getRole() == Role.Majority) {
            for (int i = 0; i < votes.length; i++) {
                votes[i].setTag(Role.Majority);
                votes[i].setImageResource(R.drawable.role_majority);
            }
        } else {
            Random random = GameSession.getCurrent().getRandom();
            int minorityVoteIndex = random.nextInt(votes.length);

            for (int i = 0; i < votes.length; i++) {
                if (minorityVoteIndex == i) {
                    votes[i].setTag(Role.Minority);
                    votes[i].setImageResource(R.drawable.role_minority);
                } else {
                    votes[i].setTag(Role.Majority);
                    votes[i].setImageResource(R.drawable.role_majority);
                }
            }

        }

        if (!hidden) {
            turnButton.setVisibility(View.GONE);
            choiceLayout.setVisibility(View.VISIBLE);
        } else {
            turnButton.setVisibility(View.VISIBLE);
            choiceLayout.setVisibility(View.GONE);
        }
    }


    public void onTurnClick(View view) {
        hidden = false;

        View turnButton = findViewById(R.id.vote_action_turn);
        turnButton.setVisibility(View.GONE);

        View choiceLayout = findViewById(R.id.vote_action_choice_layout);
        choiceLayout.setVisibility(View.VISIBLE);
    }

}
