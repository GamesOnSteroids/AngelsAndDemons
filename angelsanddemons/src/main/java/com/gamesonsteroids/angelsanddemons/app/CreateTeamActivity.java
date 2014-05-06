package com.gamesonsteroids.angelsanddemons.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.gamesonsteroids.angelsanddemons.game.GameRules;
import com.gamesonsteroids.angelsanddemons.game.GameSession;
import com.gamesonsteroids.angelsanddemons.game.Player;
import com.gamesonsteroids.angelsanddemons.game.Team;
import com.gamesonsteroids.angelsanddemons.widgets.ListAdapter;


public class CreateTeamActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);

        initHeader();

        TextView teamLeader = (TextView)findViewById(R.id.create_team_leader);
        teamLeader.setText(getString(R.string.your_turn, GameSession.getCurrent().getCurrentTeam().getLeader().getName() ));

        final Button createTeam = (Button)findViewById(R.id.create_team_button);
        createTeam.setEnabled(false);

        final ListView list = (ListView)findViewById(R.id.create_team_list);
        list.setAdapter(new ListAdapter<Player>(GameSession.getCurrent().getPlayers(), new ListAdapter.ListViewProvider<Player>() {
            @Override
            public View getView(final int position, final Player item, View convertView) {
                View view = convertView;
                if (view == null) {
                    view = new CheckBox(CreateTeamActivity.this);
                }

                final CheckBox playerCheckBox = ((CheckBox)view);
                playerCheckBox.setOnCheckedChangeListener(null);
                playerCheckBox.setChecked(GameSession.getCurrent().getCurrentTeam().getMembers().contains(item));
                playerCheckBox.setText(item.getName());
                playerCheckBox.setTag(item);
                playerCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                        Player checkedPlayer = (Player)playerCheckBox.getTag();

                        Team currentTeam = GameSession.getCurrent().getCurrentTeam();
                        if (isChecked) {
                            currentTeam.getMembers().add(checkedPlayer);
                        } else {
                            currentTeam.getMembers().remove(checkedPlayer);
                        }

                        checkTeamSize();
                    }
                });
                int maxTeamSize = GameRules.getTeamSize(GameSession.getCurrent().getRound(), GameSession.getCurrent().getPlayers().size());

                Team currentTeam = GameSession.getCurrent().getCurrentTeam();
                if (maxTeamSize == currentTeam.getMembers().size()) {
                    if (!playerCheckBox.isChecked()) {
                        playerCheckBox.setEnabled(false);
                    }
                }

                return view;
            }
        }));


        checkTeamSize();
    }

    private void checkTeamSize() {

        final ListView list = (ListView)findViewById(R.id.create_team_list);

        final TextView remainingTeamMembers = (TextView)findViewById(R.id.create_team_members);


        int maxTeamSize = GameRules.getTeamSize(GameSession.getCurrent().getRound(), GameSession.getCurrent().getPlayers().size());

        Team currentTeam = GameSession.getCurrent().getCurrentTeam();

        remainingTeamMembers.setText(getString(R.string.remaining_team_members, maxTeamSize - currentTeam.getMembers().size()));


        final Button createTeam = (Button)findViewById(R.id.create_team_button);

        if (maxTeamSize == currentTeam.getMembers().size()) {
            for (int i=0; i< list.getChildCount(); ++i) {
                CheckBox childCheckBox = (CheckBox)list.getChildAt(i);

                if (!childCheckBox.isChecked()) {
                    childCheckBox.setEnabled(false);
                }
            }
            createTeam.setEnabled(true);

        } else {
            for (int i=0; i< list.getChildCount(); ++i) {
                CheckBox childCheckBox = (CheckBox)list.getChildAt(i);
                childCheckBox.setEnabled(true);
            }
            createTeam.setEnabled(false);
        }
    }

    public void onCreateTeamClick(View view) {
        Intent intent = new Intent(this, VoteTeamActivity.class);
        this.startActivity(intent);
    }
}