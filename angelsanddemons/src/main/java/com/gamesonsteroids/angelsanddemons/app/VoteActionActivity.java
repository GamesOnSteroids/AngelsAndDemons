package com.gamesonsteroids.angelsanddemons.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamesonsteroids.angelsanddemons.game.GameSession;
import com.gamesonsteroids.angelsanddemons.game.Player;
import com.gamesonsteroids.angelsanddemons.game.Role;

import java.util.List;
import java.util.Random;


public class VoteActionActivity extends AbstractGameActivity {

    private int currentPlayerIndex;
    private boolean revealed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_action);

        initHeader();

        if (savedInstanceState != null) {
            currentPlayerIndex = savedInstanceState.getInt("currentPlayerIndex");
            revealed = savedInstanceState.getBoolean("revealed");
        } else {
            currentPlayerIndex = 0;
            revealed = false;
        }

        setPlayer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt("currentPlayerIndex", currentPlayerIndex);
        outState.putBoolean("revealed", revealed);

        super.onSaveInstanceState(outState);
    }

    public void onVoteClick(View view) {
        view.setEnabled(false);

        Role voteAs = (Role)view.getTag();

        List<Player> members = GameSession.getCurrent().getCurrentRound().getTeam();

        Player player = members.get(this.currentPlayerIndex);

        GameSession.getCurrent().getCurrentRound().vote(player, voteAs);

        int index = this.currentPlayerIndex + 1;
        if (index < members.size()) {
            this.currentPlayerIndex = index;
            this.revealed = false;
            setPlayer();
        } else {


            GameSession.getCurrent().finishRound();

            Intent intent = new Intent(this, ActionResultActivity.class);
            this.startActivity(intent);
        }

    }

    private void setPlayer() {

        List<Player> members = GameSession.getCurrent().getCurrentRound().getTeam();

        Player player = members.get(this.currentPlayerIndex);

        TextView textView = (TextView)findViewById(R.id.activity_vote_player);
        textView.setText(getString(R.string.your_turn, player.getName()));

        View turnButton = findViewById(R.id.vote_action_turn);
        View choiceLayout = findViewById(R.id.vote_action_choice_layout);

        ImageView vote1 = (ImageView)findViewById(R.id.vote1);
        ImageView vote2 = (ImageView)findViewById(R.id.vote2);


        ImageView[] votes = new ImageView[] { vote1, vote2 };


        for (int i = 0; i < votes.length; i++) {
            votes[i].setEnabled(true);
        }

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

        if (revealed) {
            turnButton.setVisibility(View.GONE);
            choiceLayout.setVisibility(View.VISIBLE);
        } else {
            turnButton.setVisibility(View.VISIBLE);
            choiceLayout.setVisibility(View.GONE);
        }
    }


    public void onRevealClick(View view) {
        revealed = true;

        View turnButton = findViewById(R.id.vote_action_turn);
        turnButton.setVisibility(View.GONE);

        View choiceLayout = findViewById(R.id.vote_action_choice_layout);
        choiceLayout.setVisibility(View.VISIBLE);
    }

}
