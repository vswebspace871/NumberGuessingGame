package com.example.numberguessinggame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.numberguessinggame.databinding.ActivityGameBinding;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {


    ActivityGameBinding binding;

    boolean twoDigits, threeDigits, fourDigits;

    Random r = new Random();
    int random;
    int remainingRight = 10;

    ArrayList<Integer> guessesList = new ArrayList<>();
    int userAttempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        twoDigits = getIntent().getBooleanExtra("two",false);
        threeDigits = getIntent().getBooleanExtra("three",false);
        fourDigits = getIntent().getBooleanExtra("four",false);

        if (twoDigits){
            random = r.nextInt(90)+10;
        }
        if (threeDigits){
            random = r.nextInt(900)+10;
        }
        if (fourDigits){
            random = r.nextInt(9000)+10;
        }

        binding.buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guess = binding.editTextGuess.getText().toString();

                if (guess.equals("")){
                    Toast.makeText(GameActivity.this, "Please Enter Number", Toast.LENGTH_SHORT).show();
                }else {
                    binding.textViewLast.setVisibility(View.VISIBLE);
                    binding.textViewRight.setVisibility(View.VISIBLE);
                    binding.textViewHint.setVisibility(View.VISIBLE);


                    userAttempts++;
                    remainingRight--;

                    int userGuess = Integer.parseInt(guess);
                    //insert user value into array
                    guessesList.add(userGuess);

                    binding.textViewLast.setText("Your Last Guess is : "+guess);

                    binding.textViewRight.setText("Your remaining rights are : "+ remainingRight);

                    if (random == userGuess)
                    {
                        //person win
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Number Guessing Game");
                        builder.setCancelable(false);
                        builder.setMessage("Congrats My Guess was "+random + "\n\n you know my number in "+userAttempts
                        +"attempts. \n\n Your guesses are: "+guessesList + "\n\n Would you like to play again?");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1); // forcefully kill application
                            }
                        });

                        builder.create().show();
                    }
                    if (random < userGuess){
                        binding.textViewHint.setText("Decrease your guess");
                    }
                    if (random > userGuess){
                        binding.textViewHint.setText("Increase your guess");
                    }

                    if (remainingRight == 0){
                        //game over

                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Number Guessing Game");
                        builder.setCancelable(false);
                        builder.setMessage("Sorry! GAME OVER  My Guess was "+random +" \n\n Your guesses are: "+guessesList + "\n\n Would you like to play again?");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1); // forcefully kill application
                            }
                        });

                        builder.create().show();
                    }

                    binding.editTextGuess.setText("");
                }
            }
        });


    }
}