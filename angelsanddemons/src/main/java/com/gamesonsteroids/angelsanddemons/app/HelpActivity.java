package com.gamesonsteroids.angelsanddemons.app;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;


public class HelpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        
        ((TextView)findViewById(R.id.help_rules)).setText(Html.fromHtml(getString(R.string.rules)));
        ((TextView)findViewById(R.id.help_rules)).setMovementMethod(ScrollingMovementMethod.getInstance());

    }
}
