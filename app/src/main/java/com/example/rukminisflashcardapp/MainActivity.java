package com.example.rukminisflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.ImageView;
import android.content.Intent;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView Answer;
    private boolean showing = true;
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();
        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
        }

        findViewById(R.id.flashcard_question).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
            }
        });
        findViewById(R.id.flashcard_question).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
                Answer = findViewById(R.id.flashcard_answer);
                Answer.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                        findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        findViewById(R.id.answer1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer1).setBackgroundColor(getResources().getColor(R.color.wronganswer,null));
                findViewById(R.id.answer2).setBackgroundColor(getResources().getColor(R.color.rightanswer,null));
            }
        });

        findViewById(R.id.answer2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer2).setBackgroundColor(getResources().getColor(R.color.rightanswer,null));
            }
        });

        findViewById(R.id.answer3).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor(R.color.wronganswer,null));
                findViewById(R.id.answer2).setBackgroundColor(getResources().getColor(R.color.rightanswer,null));
            }
        });

        findViewById(R.id.toggle_choices_visibility).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showing = false;
                ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.ic_iconmonstr_eye_thin);
                if(showing == false) {
                    findViewById(R.id.answer1).setVisibility(View.INVISIBLE);
                    findViewById(R.id.answer2).setVisibility(View.INVISIBLE);
                    findViewById(R.id.answer3).setVisibility(View.INVISIBLE);
                    findViewById(R.id.toggle_choices_visibility).setVisibility(View.INVISIBLE);
                    findViewById(R.id.toggle_choices_visibilityoff).setVisibility(View.VISIBLE);
                }
            }
        });

        findViewById(R.id.toggle_choices_visibilityoff).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showing = true;
                ((ImageView) findViewById(R.id.toggle_choices_visibilityoff)).setImageResource(R.drawable.ic_iconmonstr_eye_off_thin);
                if(showing == true) {
                    findViewById(R.id.answer1).setVisibility(View.VISIBLE);
                    findViewById(R.id.answer2).setVisibility(View.VISIBLE);
                    findViewById(R.id.answer3).setVisibility(View.VISIBLE);
                    findViewById(R.id.toggle_choices_visibilityoff).setVisibility(View.INVISIBLE);
                    findViewById(R.id.toggle_choices_visibility).setVisibility(View.VISIBLE);

                }
            }
        });

        findViewById(R.id.add_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex++;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                // set the question and answer TextViews with data from the database
                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String question1 = data.getExtras().getString("question1"); // 'string1' needs to match the key we used when we put the string in the Intent
            String answer1 = data.getExtras().getString("answer1");

            ((TextView) findViewById(R.id.flashcard_question)).setText(question1);
            ((TextView) findViewById(R.id.flashcard_answer)).setText(answer1);
            flashcardDatabase.insertCard(new Flashcard(question1, answer1));
            allFlashcards = flashcardDatabase.getAllCards();
        }
    }
}
