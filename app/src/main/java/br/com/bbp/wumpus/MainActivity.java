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
        //gridView.setBackgroundColor(Color.WHITE);

        final int [] array = new int [(int) Math.pow(size,2)];

        Random random = new Random();
        int wumpus = random.nextInt(array.length);
        int gold = random.nextInt(array.length);
        setCurrentHunterPosition(array.length - size);
        position = getCurrentHunterPosition();

        for (int i = 0; i < (array.length * 7)/100; i++){
            array[random.nextInt(array.length)] = R.drawable.hole;
        }
        for (int i = 0; i < array.length; i++){
            if (array[i] != R.drawable.hole){
                array[i] = R.drawable.blank_space;
            }
        }

        array[position] = R.drawable.hunter_boy;
        array[wumpus] = R.drawable.wumpus;
        while (wumpus == gold){
            gold = random.nextInt(array.length);
        }
        array[gold] = R.drawable.gold;

        final GridAdapter gridAdapter = new GridAdapter(this, array);
        gridView.setAdapter(gridAdapter);

        buttonUp = (ImageButton) findViewById(R.id.btn_up);
        buttonBotton = (ImageButton) findViewById(R.id.btn_botton);
        buttonLeft = (ImageButton) findViewById(R.id.btn_left);
        buttonRight = (ImageButton) findViewById(R.id.btn_right);

        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView iv = new ImageView(getApplicationContext());

            }
        });

        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "andou para direita", Toast.LENGTH_SHORT).show();

            }
        });

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "andou para esquerda", Toast.LENGTH_SHORT).show();

            }
        });

        buttonBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "andou para baixo", Toast.LENGTH_SHORT).show();

            }
        });

    }
    public int getCurrentHunterPosition(){
        return position;
    }

    public void setCurrentHunterPosition(int position){
        this.position = position;
    }

}
