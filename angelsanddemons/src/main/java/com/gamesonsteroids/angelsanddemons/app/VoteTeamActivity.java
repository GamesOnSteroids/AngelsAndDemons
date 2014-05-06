package com.gamesonsteroids.angelsanddemons.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.gamesonsteroids.angelsanddemons.game.GameRules;
import com.gamesonsteroids.angelsanddemons.game.GameSession;
import com.gamesonsteroids.angelsanddemons.game.Player;
import com.gamesonsteroids.angelsanddemons.widgets.ListAdapter;


public class VoteTeamActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_team);

        initHeader();

        ((TextView)findViewById(R.id.vote_team_text)).setText(getString(R.string.approve_text, (int)Math.floor((float)GameSession.getCurrent().getPlayers().size() / 2) + 1));

        ListView list = (ListView)findViewById(R.id.vote_team_members);
        list.setAdapter(new ListAdapter<Player>(GameSession.getCurrent().getCurrentRound().getTeam(), new ListAdapter.ListViewProvider<Player>() {
            @Override
            public View getView(final int position, final Player item, View convertView) {
                View view = convertView;
                if (view == null) {
                    view = new TextView(VoteTeamActivity.this);
                }
                ((TextView)view).setText(item.getName());

                return view;
            }
        }));
    }

    public void onApproveClick(View view) {

        Intent intent = new Intent(this, VoteActionActivity.class);
        this.startActivity(intent);

    }

    public void onRejectClick(View view) {

        GameSession.getCurrent().setConsecutiveDraws(GameSession.getCurrent().getConsecutiveDraws() + 1);
        if (GameSession.getCurrent().getConsecutiveDraws() == GameRules.DrawsToWin) {
            Intent intent = new Intent(this, GameOverActivity.class);
            this.startActivity(intent);
        } else {

            GameSession.getCurrent().restartRound();

            Intent intent = new Intent(this, CreateTeamActivity.class);
            this.startActivity(intent);
        }
    }

}
