package com.gamesonsteroids.angelsanddemons.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.gamesonsteroids.angelsanddemons.game.GameSession;
import com.gamesonsteroids.angelsanddemons.game.Player;
import com.gamesonsteroids.angelsanddemons.game.Role;
import com.gamesonsteroids.angelsanddemons.widgets.FlipAnimation;


public class AssignRolesActivity extends GameActivity {

    public int currentPlayerIndex;
    public boolean hidden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_roles);

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


    private void setPlayer() {

        Player player = GameSession.getCurrent().getPlayers().get(currentPlayerIndex);

        TextView textView = (TextView)findViewById(R.id.assign_roles_name);
        textView.setText(player.getName());

        Role playerRole = player.getRole();
        if (hidden) {

            ImageView imageView = (ImageView)findViewById(R.id.assign_roles_image);
            imageView.setImageResource(R.drawable.role_unknown);

            TextView role = (TextView)findViewById(R.id.assign_roles_text);
            role.setVisibility(View.INVISIBLE);

            final ViewAnimator viewAnimator = (ViewAnimator) this.findViewById(R.id.viewFlipper);

            viewAnimator.setInAnimation(null);
            viewAnimator.setOutAnimation(null);
            viewAnimator.setDisplayedChild(0);

        } else {
            final ImageView imageView = (ImageView) findViewById(R.id.assign_roles_image);
            final TextView role = (TextView) findViewById(R.id.assign_roles_text);

            if (playerRole == Role.Minority) {
                imageView.setImageResource(R.drawable.role_minority);
                role.setText(Html.fromHtml(getString(R.string.you_are_minority)));
            } else if (playerRole == Role.Majority) {
                imageView.setImageResource(R.drawable.role_majority);
                role.setText(Html.fromHtml(getString(R.string.you_are_majority)));
            }

            role.setVisibility(View.VISIBLE);

            final ViewAnimator viewAnimator = (ViewAnimator) this.findViewById(R.id.viewFlipper);

            viewAnimator.showNext();
        }
    }

    public void onRevealClick(View view) {

        hidden = false;

        Player player = GameSession.getCurrent().getPlayers().get(currentPlayerIndex);

        final ImageView imageView = (ImageView)findViewById(R.id.assign_roles_image);
        final View reveal = findViewById(R.id.assign_roles_reveal);
        final TextView role = (TextView)findViewById(R.id.assign_roles_text);

        if (player.getRole() == Role.Minority) {
            imageView.setImageResource(R.drawable.role_minority);
            role.setText(Html.fromHtml(getString(R.string.you_are_minority)));
        } else if (player.getRole() == Role.Majority) {
            imageView.setImageResource(R.drawable.role_majority);
            role.setText(Html.fromHtml(getString(R.string.you_are_majority)));
        }

        imageView.setEnabled(false);
        reveal.setEnabled(false);


        final ViewAnimator viewAnimator = (ViewAnimator)this.findViewById(R.id.viewFlipper);

        FlipAnimation.flipTransition(viewAnimator, FlipAnimation.FlipDirection.LEFT_RIGHT);

        viewAnimator.getInAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                role.setVisibility(View.VISIBLE);
                imageView.setEnabled(true);
                reveal.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void onNextClick(View view) {
        int index = currentPlayerIndex + 1;
        if (index != GameSession.getCurrent().getPlayers().size()) {
            currentPlayerIndex = index;
            hidden = true;
            setPlayer();
        } else {

            Intent intent = new Intent(this, CreateTeamActivity.class);
            this.startActivity(intent);
        }
    }
}
