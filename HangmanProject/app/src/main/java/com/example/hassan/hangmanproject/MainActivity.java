package com.example.hassan.hangmanproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends Activity {
    TextView word;
    String actualWord;
    String words[];
    int guesses;
    ImageView pic;
    ArrayList<String> guessList;
    TextView guessesLabel;
    boolean wonGame;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pic = (ImageView) findViewById(R.id.pic);
        words = getResources().getStringArray(R.array.words);
        word = (TextView) findViewById(R.id.word);
        guessList = new ArrayList<String>();
        guessesLabel = (TextView) findViewById(R.id.guesses);
        startGame();
    }

    private void startGame() {
        guesses = 0;
        wonGame = false;
        guessList = new ArrayList<String>();
        guessesLabel.setText("Guesses: ");
        Random r = new Random();
        int index = r.nextInt(words.length);
        actualWord = words[index];
        word.setText("");
        for(int i = 0; i < actualWord.length(); i++) {
            word.append("?");
        }
        pic.setImageResource(R.drawable.hangman6);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void guess(View view) {

        EditText guess = (EditText) findViewById(R.id.gu);
        String g = guess.getText().toString().toLowerCase();
        guess.setText("");
        if(guesses > 5 || wonGame) {
            Toast.makeText(this, "Please start a new game!", Toast.LENGTH_SHORT).show();
        } else if(g.isEmpty()) {
            Toast.makeText(this, "Please enter a letter.", Toast.LENGTH_SHORT).show();
        } else if(!Character.isLetter(g.charAt(0))) {
            Toast.makeText(this, "That's not a letter!", Toast.LENGTH_SHORT).show();
        } else if(guessList.contains(g)) {
            Toast.makeText(this, "You've already used this letter.", Toast.LENGTH_SHORT).show();
        } else {

            guessList.add(g + "");

            guessesLabel.append("" + g);

            String updatedWord = "";

            boolean guessRight = false;
            for (int i = 0; i < actualWord.length(); i++) {
                if (actualWord.charAt(i) == g.charAt(0)) {
                    updatedWord += g;
                    guessRight = true;
                } else {
                    updatedWord += word.getText().toString().charAt(i);
                }
            }
            if (guessRight) {
                word.setText(updatedWord);
                if(updatedWord.equals(actualWord)) {
                    wonGame = true;
                    Toast.makeText(this, "Congratulations! You won. Start again?", Toast.LENGTH_SHORT).show();
                }

            } else {
                guesses++;
                if (guesses == 1) {
                    pic.setImageResource(R.drawable.hangman5);
                } else if (guesses == 2) {
                    pic.setImageResource(R.drawable.hangman4);
                } else if (guesses == 3) {
                    pic.setImageResource(R.drawable.hangman3);
                } else if (guesses == 4) {
                    pic.setImageResource(R.drawable.hangman2);
                } else if (guesses == 5) {
                    pic.setImageResource(R.drawable.hangman1);
                } else if (guesses == 6) {
                    pic.setImageResource(R.drawable.hangman0);
                    word.setText(actualWord);
                    Toast.makeText(this, "Sorry... you lost. Start a new game!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void restartGame(View view) {
        startGame();
    }
}
