package com.gamesonsteroids.angelsanddemons.app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gamesonsteroids.angelsanddemons.game.GameRules;
import com.gamesonsteroids.angelsanddemons.game.GameSession;
import com.gamesonsteroids.angelsanddemons.game.Player;
import com.gamesonsteroids.angelsanddemons.widgets.ListAdapter;


public class GameConfigurationActivity extends ListActivity {


    private Button addPlayerButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_configuration);

        nextButton = (Button)findViewById(R.id.game_configuration_button_next);

        addPlayerButton = new Button(this);
        addPlayerButton.setText(getString(R.string.add));
        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameConfigurationActivity.this.onAddPlayerClick(v);
            }
        });

        this.getListView().addFooterView(addPlayerButton);

        setListAdapter(new ListAdapter<Player>(GameSession.getCurrent().getPlayers(), new ListAdapter.ListViewProvider<Player>() {
            @Override
            public View getView(final int position, final Player item, View convertView) {
                View view = convertView;
                if (view == null) {
                    view = View.inflate(GameConfigurationActivity.this, R.layout.fragment_player_configuration, null);

                    View removePlayerButton = view.findViewById(R.id.game_configuration_button_remove_player);

                    removePlayerButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            GameConfigurationActivity.this.onRemovePlayerClick(v);
                        }
                    });
                }

                view.requestFocus();

                View removePlayerButton = view.findViewById(R.id.game_configuration_button_remove_player);
                TextView playerIndexText = (TextView) view.findViewById(R.id.player_configuration_index);
                EditText playerNameText = (EditText) view.findViewById(R.id.player_configuration_name);


                removePlayerButton.setTag(item);
                playerIndexText.setText(Integer.toString(position + 1));
                playerNameText.setText(item.getName());

                return view;
            }
        }));

        checkPlayerCount();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        extractPlayerNames();
        checkPlayerCount();

        super.onSaveInstanceState(outState);
    }


    public void onStartGameClick(View v) {
        // validate player names

        extractPlayerNames();

        GameSession.getCurrent().startGame();

        Intent intent = new Intent(GameConfigurationActivity.this, AssignRolesActivity.class);
        GameConfigurationActivity.this.startActivity(intent);
    }


    private void extractPlayerNames() {
        for (int i = 0; i < GameSession.getCurrent().getPlayers().size(); i++) {
            EditText playerNameEdit = (EditText) getListView().getChildAt(i).findViewById(R.id.player_configuration_name);
            if (playerNameEdit != null) {
                CharSequence playerName = playerNameEdit.getText();
                GameSession.getCurrent().getPlayers().get(i).setName(playerName);
            }
        }
    }



    private void onRemovePlayerClick(View v) {
        extractPlayerNames();

        GameSession.getCurrent().getPlayers().remove(v.getTag());
        ((ListAdapter)this.getListAdapter()).notifyDataSetChanged();

        checkPlayerCount();
    }

    private void checkPlayerCount() {
        nextButton.setEnabled(GameSession.getCurrent().getPlayers().size() >= GameRules.MinPlayers);
        addPlayerButton.setEnabled(GameSession.getCurrent().getPlayers().size() < GameRules.MaxPlayers);
    }

    private void onAddPlayerClick(View v) {

        GameSession.getCurrent().getPlayers().add(new Player());

        ((ListAdapter)this.getListAdapter()).notifyDataSetChanged();
        checkPlayerCount();
        extractPlayerNames();
    }


}
