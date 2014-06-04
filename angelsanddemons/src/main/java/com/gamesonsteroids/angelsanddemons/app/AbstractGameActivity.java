package com.gamesonsteroids.angelsanddemons.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.gamesonsteroids.angelsanddemons.game.GameSession;


public abstract class AbstractGameActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    protected void initHeader() {
        TextView round = (TextView)findViewById(R.id.header_round);
        round.setText(getString(R.string.header_round, GameSession.getCurrent().getRounds().size()));

        ((TextView)findViewById(R.id.header_score)).setText(Html.fromHtml(getString(R.string.score,  GameSession.getCurrent().getMajorityScore(), GameSession.getCurrent().getMinorityScore(), GameSession.getCurrent().getConsecutiveDraws())));
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getString(android.R.string.dialog_alert_title))
                .setMessage(getString(R.string.are_you_sure))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(AbstractGameActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        AbstractGameActivity.this.startActivity(intent);
                    }

                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

}
