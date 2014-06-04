package com.gamesonsteroids.angelsanddemons.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.gamesonsteroids.angelsanddemons.game.GameSession;
import com.gamesonsteroids.angelsanddemons.game.Player;
import com.gamesonsteroids.angelsanddemons.game.Role;
import com.gamesonsteroids.angelsanddemons.widgets.FlipAnimation;
import com.gamesonsteroids.angelsanddemons.widgets.OnSwipeTouchListener;

import java.util.ArrayList;
import java.util.List;


public class AssignRolesActivity extends AbstractGameActivity {

    public int currentPlayerIndex;
    public boolean revealed;
    private TextView roleText;
    private ImageView roleRevealedImage;
    private ViewAnimator viewAnimator;
    private ImageView roleUnknownImage;
    private TextView minorityPlayersText;
    private TextView playerNameText;
    private Button nextPlayerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_roles);

        playerNameText = (TextView)findViewById((R.id.assign_roles_player_name));
        roleText = (TextView) findViewById(R.id.assign_roles_text_role);
        roleRevealedImage = (ImageView) findViewById(R.id.assign_roles_image_role_revealed);
        viewAnimator = (ViewAnimator) this.findViewById(R.id.viewFlipper);
        roleUnknownImage = (ImageView)findViewById(R.id.assign_roles_image_role_unknown);
        minorityPlayersText = (TextView)findViewById(R.id.assign_roles_text_minority_players);

        nextPlayerButton = (Button)findViewById(R.id.assign_roles_next_player);

        roleUnknownImage.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();

                AssignRolesActivity.this.onRevealClick(roleUnknownImage);
            }
        });

        if (savedInstanceState != null) {
            currentPlayerIndex = savedInstanceState.getInt("currentPlayerIndex");
            revealed = savedInstanceState.getBoolean("revealed");
        } else {
            currentPlayerIndex = 0;
            revealed = false;
        }

        setCurrentPlayer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentPlayerIndex", currentPlayerIndex);
        outState.putBoolean("revealed", revealed);

        super.onSaveInstanceState(outState);

    }


    private void setCurrentPlayer() {

        Player player = GameSession.getCurrent().getPlayers().get(currentPlayerIndex);

        playerNameText.setText(player.getName());
        nextPlayerButton.setEnabled(false);

        if (revealed) {
            revealPlayer(player);

            roleText.setVisibility(View.VISIBLE);
            minorityPlayersText.setVisibility(View.VISIBLE);

            nextPlayerButton.setEnabled(true);
            nextPlayerButton.setVisibility(View.VISIBLE);
            nextPlayerButton.setText(R.string.next);

            viewAnimator.showNext();
        } else {

            roleRevealedImage.setImageResource(R.drawable.role_unknown);
            roleText.setVisibility(View.INVISIBLE);
            minorityPlayersText.setVisibility(View.INVISIBLE);

            nextPlayerButton.setVisibility(View.INVISIBLE);

            viewAnimator.setInAnimation(null);
            viewAnimator.setOutAnimation(null);
            viewAnimator.setDisplayedChild(0);

        }
    }

    public void onRevealClick(View view) {

        revealed = true;

        Player player = GameSession.getCurrent().getPlayers().get(currentPlayerIndex);

        revealPlayer(player);

        roleRevealedImage.setEnabled(false);
        roleUnknownImage.setEnabled(false);


        FlipAnimation.flipTransition(viewAnimator, FlipAnimation.FlipDirection.LEFT_RIGHT);

        viewAnimator.getInAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                roleText.setVisibility(View.VISIBLE);
                minorityPlayersText.setVisibility(View.VISIBLE);
                nextPlayerButton.setVisibility(View.VISIBLE);
                roleRevealedImage.setEnabled(true);
                roleUnknownImage.setEnabled(true);

                final CountDownTimer timer = new CountDownTimer(2000, 100) {
                    public void onTick(long millisUntilFinished) {
                        nextPlayerButton.setText(getString(R.string.next) + " (" + ((int) Math.floor(millisUntilFinished / 1000f) + 1) + ")");
                    }

                    public void onFinish() {
                        nextPlayerButton.setText(R.string.next);
                        nextPlayerButton.setEnabled(true);
                    }
                };
                timer.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void revealPlayer(Player player) {
        if (player.getRole() == Role.Minority) {
            roleRevealedImage.setImageResource(R.drawable.role_minority);
            roleText.setText(Html.fromHtml(getString(R.string.you_are_minority)));

            List<CharSequence> otherMinorityMember = new ArrayList<CharSequence>();
            for (Player otherPlayer : GameSession.getCurrent().getPlayers()) {
                if (otherPlayer != player && otherPlayer.getRole().equals(Role.Minority)) {
                    otherMinorityMember.add(otherPlayer.getName());
                }
            }
            minorityPlayersText.setText(Html.fromHtml(getString(R.string.minority_players, TextUtils.join(", ", otherMinorityMember))));


            nextPlayerButton.setText(getString(R.string.next) + " (3)");

        } else if (player.getRole() == Role.Majority) {
            roleRevealedImage.setImageResource(R.drawable.role_majority);
            roleText.setText(Html.fromHtml(getString(R.string.you_are_majority)));
            minorityPlayersText.setText("");
        }
    }

    public void onNextClick(View view) {
        view.setEnabled(false);
        int index = currentPlayerIndex + 1;
        if (index != GameSession.getCurrent().getPlayers().size()) {
            currentPlayerIndex = index;
            revealed = false;
            setCurrentPlayer();
        } else {

            Intent intent = new Intent(this, CreateTeamActivity.class);
            this.startActivity(intent);
        }
    }
}
