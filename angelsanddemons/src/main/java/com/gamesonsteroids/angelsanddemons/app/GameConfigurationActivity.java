package com.gamesonsteroids.angelsanddemons.app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gamesonsteroids.angelsanddemons.game.GameSession;
import com.gamesonsteroids.angelsanddemons.game.Player;
import com.gamesonsteroids.angelsanddemons.widgets.ListAdapter;


public class GameConfigurationActivity extends ListActivity {

    public void onStartGameClick(View v) {
        // validate player names

        extractPlayerNames();

        Intent intent = new Intent(GameConfigurationActivity.this, AssignRolesActivity.class);
        GameConfigurationActivity.this.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        extractPlayerNames();

        super.onSaveInstanceState(outState);
    }

    private void extractPlayerNames() {
        for (int i = 0; i < getListView().getChildCount(); i++) {
            CharSequence playerName = ((EditText)getListView().getChildAt(i).findViewById(R.id.playerName)).getText();
            GameSession.getCurrent().getPlayers().get(i).setName(playerName);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_configuration);


        setListAdapter(new ListAdapter<Player>(GameSession.getCurrent().getPlayers(), new ListAdapter.ListViewProvider<Player>() {
            @Override
            public View getView(final int position, final Player item, View convertView) {
                View view = convertView;
                if (view == null) {
                    view = View.inflate(GameConfigurationActivity.this, R.layout.fragment_player_configuration, null);
                }

                ((TextView)view.findViewById(R.id.playerIndex)).setText(Integer.toString(position + 1));

                EditText playerName = (EditText)view.findViewById(R.id.playerName);

                playerName.setText(item.getName());

                return view;
            }
        }));
    }
}
