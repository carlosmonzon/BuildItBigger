package com.cmonzon.jokeviewermodule;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.cmonzon.jokedisplayermodule.R;

import org.w3c.dom.Text;

public class JokeViewerActivity extends AppCompatActivity {

    public static String JOKE_LABEL = "joke_label";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_viewer);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String joke = bundle.getString(JOKE_LABEL);
            TextView jokeLabel = (TextView) findViewById(R.id.tvJokeLabel);
            jokeLabel.setText(joke);
        }
    }

    public static void displayJokeActivity(Context context, String joke) {
        Intent intent = new Intent(context, JokeViewerActivity.class);
        intent.putExtra(JOKE_LABEL, joke);
        context.startActivity(intent);
    }
}
