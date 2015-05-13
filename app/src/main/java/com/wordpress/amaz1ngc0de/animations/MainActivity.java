package com.wordpress.amaz1ngc0de.animations;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends Activity {

    private Fragment gameOver, highScoreGameOver, game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        final SharedPreferences prefs = getSharedPreferences("hitColor", MODE_PRIVATE);
        int startHighscore = prefs.getInt("highScore", 0);

        gameOver = new GameOverFragment();
        highScoreGameOver = new HighScoreFragment();
        game = new GameFragment();

        Intent i = getIntent();
        int countSum = i.getIntExtra("CountSum", -1);

        if(countSum == -1) {
            Log.d("GSS", "Primeira entrada");
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layout_root_view, game)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            Log.d("GSS", "COUNT: " + String.valueOf(countSum));
            Log.d("GSS", "HIGH: " + String.valueOf(startHighscore));

            finishGame(countSum, startHighscore, prefs);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.go_back:
                getFragmentManager()
                        .beginTransaction() // setar aqui animacao para voltar
                        .replace(R.id.layout_root_view, game)
                        .commit();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void finishGame(int actualPoints, int lastHighScore, SharedPreferences prefs)
    {
        if(actualPoints > lastHighScore){
            prefs.edit().putInt("highScore", actualPoints).commit();
        }
        flipEndOfGame(actualPoints > lastHighScore);
    }

    private void flipEndOfGame(boolean highScore){
        if(highScore){
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                    .replace(R.id.layout_root_view, highScoreGameOver)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                    .replace(R.id.layout_root_view, gameOver)
                    .addToBackStack(null)
                    .commit();
        }
    }
}