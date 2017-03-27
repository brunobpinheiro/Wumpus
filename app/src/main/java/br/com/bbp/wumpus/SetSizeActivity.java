package br.com.bbp.wumpus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.EmptyStackException;

public class SetSizeActivity extends AppCompatActivity {

    private Button buttonStart;
    private EditText fieldSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_size);

        SharedPreferences sharedPreferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        int recorde = sharedPreferences.getInt("recorde", 0);
        String textoRecorde = recorde < 0 ? "Recorde: " + 0 : "Recorde: " + recorde;

        TextView tvRecorde = (TextView) findViewById(R.id.recorde);
        tvRecorde.setText(textoRecorde);

        buttonStart = (Button) findViewById(R.id.buttonStartId);
        fieldSize = (EditText) findViewById(R.id.fieldSizeId);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fieldSize.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Você deve escrever um número entre 5 e 20", Toast.LENGTH_LONG).show();
                } else {
                    int size = Integer.parseInt(fieldSize.getText().toString());
                    if (size > 4 && size <= 20) {
                        Intent intent = new Intent(SetSizeActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("size", fieldSize.getText().toString());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "O número deve estar entre 5 e 20", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });


    }

    @Override
    protected void onResume() {
        SharedPreferences sharedPreferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        int recorde = sharedPreferences.getInt("recorde", 0);
        String textoRecorde = recorde < 0 ? "Recorde: " + 0 : "Recorde: " + recorde;

        TextView tvRecorde = (TextView) findViewById(R.id.recorde);
        tvRecorde.setText(textoRecorde);

        super.onResume();
    }
}
