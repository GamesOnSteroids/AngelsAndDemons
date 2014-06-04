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


public class VoteTeamActivity extends AbstractGameActivity {

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
                    view = getLayoutInflater().inflate(R.layout.fragment_textview, null);
                }
                ((TextView)view).setText(item.getName());

                return view;
            }
        }));
    }

    public void onApproveClick(View view) {
        view.setEnabled(false);
        Intent intent = new Intent(this, VoteActionActivity.class);
        this.startActivity(intent);

    }

    public void onRejectClick(View view) {
        view.setEnabled(false);
        GameSession.getCurrent().rejectTeam();

        if (GameSession.getCurrent().getConsecutiveDraws() == GameRules.DrawsToWin) {
            Intent intent = new Intent(this, ActionResultActivity.class);
            this.startActivity(intent);
        } else {

            Intent intent = new Intent(this, CreateTeamActivity.class);
            this.startActivity(intent);
        }
    }

}
