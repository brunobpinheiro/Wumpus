package br.com.bbp.wumpus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.EmptyStackException;

public class SetSizeActivity extends AppCompatActivity {

    private Button buttonStart;
    private EditText fieldSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_size);

        buttonStart = (Button) findViewById(R.id.buttonStartId);
        fieldSize = (EditText) findViewById(R.id.fieldSizeId);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SetSizeActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("size",fieldSize.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


    }
}
