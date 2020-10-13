package com.akilisoft.tech.resto;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.akilisoft.tech.resto.View.CartActivity;
import com.akilisoft.tech.resto.util.TableSqliteController;

public class LoginActivity extends Activity  {
    private Button validerButton,cancelButton;
    private EditText nom,password;

    private TextView nombreTentative;
    private int counter = 3;
    private TableSqliteController tableSqliteController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        validerButton = (Button)findViewById(R.id.button);
        nom = (EditText)findViewById(R.id.editText);
        password = (EditText)findViewById(R.id.editText2);
        tableSqliteController = new TableSqliteController(this);

        cancelButton = (Button)findViewById(R.id.button2);
        nombreTentative = (TextView)findViewById(R.id.textView3);
        nombreTentative.setVisibility(View.GONE);

        validerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomre = nom.getText().toString();
                String passre = password.getText().toString();
                boolean isAuth = tableSqliteController.auth(nomre,passre);
                Intent myintent = new Intent(getBaseContext(),MainActivity.class);

                if(isAuth){
                    //nomre.equals("admin") && passre.equals("admin")
                    startActivity(myintent);
                }else{
                    Toast.makeText(getApplicationContext(),"Identifiants invalides",Toast.LENGTH_SHORT).show();
                    nombreTentative.setVisibility(View.VISIBLE);
                    nombreTentative.setBackgroundColor(Color.RED);
                    counter--;
                    nombreTentative.setText(Integer.toString(counter));

                    tableSqliteController.userCreate(nomre,passre);
                    if(counter==0){
                        validerButton.setEnabled(false);
                    }

                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}