package br.com.bbp.wumpus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {

    private ImageButton buttonUp;
    private ImageButton buttonBotton;
    private ImageButton buttonRight;
    private ImageButton buttonLeft;
    private GridView gridView;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String sizeFromStart = bundle.getString("size");
        final int size = Integer.parseInt(sizeFromStart);

        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setNumColumns(size);

        final int [] array = new int [(int) Math.pow(size,2)];

        Random random = new Random();
        int wumpus = random.nextInt(array.length);
        int gold = random.nextInt(array.length);
        setCurrentHunterPosition(array.length - size);

        for (int i = 0; i < (array.length * 7)/100; i++){
            array[random.nextInt(array.length)] = R.drawable.hole;
        }
        for (int i = 0; i < array.length; i++){
            if (array[i] != R.drawable.hole){
                array[i] = R.drawable.blank_space;
            }
        }

        array[position] = R.drawable.hunter_boy;
        while (wumpus == array.length-size){
            wumpus = random.nextInt(array.length);
        }
        array[wumpus] = R.drawable.wumpus;
        while (wumpus == gold || gold == array.length-size){
            gold = random.nextInt(array.length);
        }
        array[gold] = R.drawable.gold;

        final GridAdapter[] gridAdapter = {new GridAdapter(this, array)};
        gridView.setAdapter(gridAdapter[0]);

        buttonUp = (ImageButton) findViewById(R.id.btn_up);
        buttonBotton = (ImageButton) findViewById(R.id.btn_botton);
        buttonLeft = (ImageButton) findViewById(R.id.btn_left);
        buttonRight = (ImageButton) findViewById(R.id.btn_right);

        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getCurrentHunterPosition() >= size) {

                    if (verifyMovement(getCurrentHunterPosition()-size,array)) {

                        array[getCurrentHunterPosition()] = R.drawable.blank_space;
                        array[getCurrentHunterPosition() - size] = R.drawable.hunter_boy;
                        setCurrentHunterPosition(getCurrentHunterPosition() - size);
                        gridAdapter[0] = new GridAdapter(getApplicationContext(), array);
                        gridView.setAdapter(gridAdapter[0]);
                    }else {
                        finish();
                        /*Intent intentBack =  new Intent(MainActivity.this,SetSizeActivity.class);
                        intentBack.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intentBack);*/
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Movimento não permitido",Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int auth = 0;

                for (int i = (size - 1); i < array.length; i += (size)) {
                    if (getCurrentHunterPosition() == i) {
                        auth = 1;
                    }
                }
                if (auth == 0) {

                    if (verifyMovement(getCurrentHunterPosition() + 1, array)) {
                        array[getCurrentHunterPosition()] = R.drawable.blank_space;
                        array[getCurrentHunterPosition() + 1] = R.drawable.hunter_boy;
                        setCurrentHunterPosition(getCurrentHunterPosition() + 1);
                        gridAdapter[0] = new GridAdapter(getApplicationContext(), array);
                        gridView.setAdapter(gridAdapter[0]);
                    } else {
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Movimento não permitido", Toast.LENGTH_SHORT).show();
                }

            }
        });

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int auth = 0;
                for (int i = (size); i < array.length; i += (size)) {
                    if (getCurrentHunterPosition() == i) {
                        auth = 1;
                    }
                }
                if (auth == 0) {
                    if (verifyMovement(getCurrentHunterPosition()-1,array)) {
                        array[getCurrentHunterPosition()] = R.drawable.blank_space;
                        array[getCurrentHunterPosition() - 1] = R.drawable.hunter_boy;
                        setCurrentHunterPosition(getCurrentHunterPosition() - 1);
                        gridAdapter[0] = new GridAdapter(getApplicationContext(), array);
                        gridView.setAdapter(gridAdapter[0]);
                    } else {
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Movimento não permitido", Toast.LENGTH_SHORT).show();
                }

            }
        });

        buttonBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCurrentHunterPosition() < array.length - size) {
                    if (verifyMovement(getCurrentHunterPosition()+size,array)) {
                        array[getCurrentHunterPosition()] = R.drawable.blank_space;
                        array[getCurrentHunterPosition() + size] = R.drawable.hunter_boy;
                        setCurrentHunterPosition(getCurrentHunterPosition() + size);
                        gridAdapter[0] = new GridAdapter(getApplicationContext(), array);
                        gridView.setAdapter(gridAdapter[0]);
                    } else {
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Movimento não permitido", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public int getCurrentHunterPosition(){
        return position;
    }

    public void setCurrentHunterPosition(int position){
        this.position = position;
    }

    public boolean verifyMovement(int nextPosition, int [] array){
        if (array[nextPosition] == R.drawable.hole){
            Toast.makeText(getApplicationContext(),"Você caiu no buraco e morreu",Toast.LENGTH_LONG).show();
            return false;
        } else if (array[nextPosition] == R.drawable.wumpus){
            Toast.makeText(getApplicationContext(),"Você foi atacado pelo Wumpus e morreu",Toast.LENGTH_LONG).show();
            return false;
        } else if (array[nextPosition] == R.drawable.gold){
            Toast.makeText(getApplicationContext(),"Parabéns você achou o ouro",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
