package com.example.xinliu.intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistActivity extends AppCompatActivity {
    private EditText edUsername;
    private EditText edPassword;
    private Button btnRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        edUsername = (EditText) findViewById(R.id.ed_username);
        edPassword = (EditText) findViewById(R.id.ed_password);
        btnRegist = (Button) findViewById(R.id.btn_regist);
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistActivity.this, ShowActivity.class);
                intent.putExtra("username", edUsername.getText().toString().trim());
                intent.putExtra("password", edPassword.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
}
