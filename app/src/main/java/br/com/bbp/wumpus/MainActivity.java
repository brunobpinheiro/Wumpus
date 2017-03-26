package br.com.bbp.wumpus;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener {

    private GridView gridView;
    private int position;

    private int[] array;
    private int[] arrayAux;
    private int size;
    private GridAdapter[] gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        size = Integer.parseInt(getIntent().getStringExtra("size"));

        SharedPreferences.Editor editor = getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit();
        editor.putInt("size", size);
        editor.apply();

        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setNumColumns(size);

        initSetup();

        gridAdapter = new GridAdapter[]{new GridAdapter(this, array)};
        gridView.setAdapter(gridAdapter[0]);
    }

    private void initSetup() {
        array = new int[(int) Math.pow(size, 2)];
        arrayAux = new int[(int) Math.pow(size, 2)];

        Random random = new Random();
        int wumpus = random.nextInt(array.length);
        int gold = random.nextInt(array.length / 2);
        setCurrentHunterPosition(array.length - size);

        for (int i = 0; i < (array.length * 7) / 100; i++) {
            array[random.nextInt(array.length)] = R.drawable.hole;
        }

        while (wumpus == array.length - size || wumpus < (array.length / 2)) {
            wumpus = random.nextInt(array.length);
        }
        array[wumpus] = R.drawable.wumpus;
        while (wumpus == gold || gold == array.length - size) {
            gold = random.nextInt(array.length / 2);
        }
        array[gold] = R.drawable.gold;

        for (int i = 0; i < arrayAux.length; i++) {
            arrayAux[i] = i == arrayAux.length - size ? R.drawable.hunter_boy : R.drawable.terreno;

            if (array[i] != R.drawable.hole && array[i] != R.drawable.wumpus && array[i] != R.drawable.gold) {
                array[i] = R.drawable.terreno_pisado;
            }
        }
    }

    public int getCurrentHunterPosition() {
        return position;
    }

    public void setCurrentHunterPosition(int position) {
        this.position = position;
    }

    public boolean verifyMovement(int nextPosition) {
        /* VERIFICA SE O MOVIMENTO FEITO FEZ VOCÊ MORRER*/

        if (array[nextPosition] == R.drawable.hole) {
            Toast.makeText(getApplicationContext(), "Você caiu no buraco e morreu", Toast.LENGTH_LONG).show();
            return false;
        } else if (array[nextPosition] == R.drawable.wumpus) {
            Toast.makeText(getApplicationContext(), "Você foi atacado pelo Wumpus e morreu", Toast.LENGTH_LONG).show();
            return false;
        } else if (array[nextPosition] == R.drawable.gold) {
            Toast.makeText(getApplicationContext(), "Parabéns você achou o ouro", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void verifySounds(int nextPosition, int size) {
        MediaPlayer somBrisa = MediaPlayer.create(this, R.raw.brisa);
        if (nextPosition - 1 >= 0) {
            if (array[nextPosition - 1] == R.drawable.hole) {
                somBrisa.start();
            }
        }

        if (nextPosition + 1 < array.length) {
            if (array[nextPosition + 1] == R.drawable.hole) {
                somBrisa.start();
            }
        }

        if (nextPosition + size < array.length) {
            if (array[nextPosition + size] == R.drawable.hole) {
                somBrisa.start();
            }
        }

        if (nextPosition - size >= 0) {
            if (array[nextPosition - size] == R.drawable.hole) {
                somBrisa.start();
            }
        }

        MediaPlayer somMonstro = MediaPlayer.create(this, R.raw.monstro);
        if (nextPosition - 1 >= 0) {
            if (array[nextPosition - 1] == R.drawable.wumpus) {
                somMonstro.start();
            }
        }

        if (nextPosition + 1 < array.length) {
            if (array[nextPosition + 1] == R.drawable.wumpus) {
                somMonstro.start();
            }
        }

        if (nextPosition + size < array.length) {
            if (array[nextPosition + size] == R.drawable.wumpus) {
                somMonstro.start();
            }
        }

        if (nextPosition - size >= 0) {
            if (array[nextPosition - size] == R.drawable.wumpus) {
                somMonstro.start();
            }
        }
    }

    @Override
    public void onClick(View v) {
        int auth = 0;

        switch (v.getId()) {
            case R.id.btn_up:
                if (getCurrentHunterPosition() >= size) {
                    if (verifyMovement(getCurrentHunterPosition() - size)) {

                        arrayAux[getCurrentHunterPosition()] = R.drawable.terreno_pisado;
                        arrayAux[getCurrentHunterPosition() - size] = R.drawable.hunter_boy;
                        setCurrentHunterPosition(getCurrentHunterPosition() - size);
                        gridAdapter[0] = new GridAdapter(getApplicationContext(), arrayAux);
                        gridView.setAdapter(gridAdapter[0]);
                        verifySounds(getCurrentHunterPosition(), size);
                    } else {
                        finish();
                        /*Intent intentBack =  new Intent(MainActivity.this,SetSizeActivity.class);
                        intentBack.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intentBack);*/
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Movimento não permitido", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_botton:
                if (getCurrentHunterPosition() < (array.length - size)) {
                    if (verifyMovement(getCurrentHunterPosition() + size)) {

                        arrayAux[getCurrentHunterPosition()] = R.drawable.terreno_pisado;
                        arrayAux[getCurrentHunterPosition() + size] = R.drawable.hunter_boy;
                        setCurrentHunterPosition(getCurrentHunterPosition() + size);
                        gridAdapter[0] = new GridAdapter(getApplicationContext(), arrayAux);
                        gridView.setAdapter(gridAdapter[0]);
                        verifySounds(getCurrentHunterPosition(), size);
                    } else {
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Movimento não permitido", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_left:
                for (int i = (size); i < array.length; i += (size)) {
                    if (getCurrentHunterPosition() == i) {
                        auth = 1;
                    }
                }
                if (auth == 0) {
                    if (verifyMovement(getCurrentHunterPosition() - 1)) {

                        arrayAux[getCurrentHunterPosition()] = R.drawable.terreno_pisado;
                        arrayAux[getCurrentHunterPosition() - 1] = R.drawable.hunter_boy;
                        setCurrentHunterPosition(getCurrentHunterPosition() - 1);
                        gridAdapter[0] = new GridAdapter(getApplicationContext(), arrayAux);
                        gridView.setAdapter(gridAdapter[0]);
                        verifySounds(getCurrentHunterPosition(), size);
                    } else {
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Movimento não permitido", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_right:
                for (int i = (size - 1); i < array.length; i += (size)) {
                    if (getCurrentHunterPosition() == i) {
                        auth = 1;
                    }
                }
                if (auth == 0) {

                    if (verifyMovement(getCurrentHunterPosition() + 1)) {

                        arrayAux[getCurrentHunterPosition()] = R.drawable.terreno_pisado;
                        arrayAux[getCurrentHunterPosition() + 1] = R.drawable.hunter_boy;
                        setCurrentHunterPosition(getCurrentHunterPosition() + 1);
                        gridAdapter[0] = new GridAdapter(getApplicationContext(), arrayAux);
                        gridView.setAdapter(gridAdapter[0]);

                        Log.i("hunter information", "current position: "
                                + String.valueOf(getCurrentHunterPosition() + 1));

                        Log.i("hunter information", "size: " + String.valueOf(size));

                        verifySounds(getCurrentHunterPosition(), size);
                    } else {
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Movimento não permitido", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_arrow:
                break;
        }
    }
}
