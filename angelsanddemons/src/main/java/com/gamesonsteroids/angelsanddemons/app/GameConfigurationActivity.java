package com.gamesonsteroids.angelsanddemons.app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.gamesonsteroids.angelsanddemons.game.GameSession;
import com.gamesonsteroids.angelsanddemons.game.Player;
import com.gamesonsteroids.angelsanddemons.widgets.ListAdapter;


public class GameConfigurationActivity extends ListActivity {

    public void onStartGameClick(View v) {
        // validate player names

        Intent intent = new Intent(GameConfigurationActivity.this, AssignRolesActivity.class);
        GameConfigurationActivity.this.startActivity(intent);
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
                playerName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {

                            item.setName(v.getText());
                            return  true;
                        }

                        return false;
                    }
                });

                return view;
            }
        }));
    }
}
