package com.example.webserviceretrofit;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private GitService serviceGit;
    private ViaCepService service;
    private EditText cepInput;
    private EditText loginInput;
    private TextView resultTextView;

    private Button searchButton;

    private WebView W = findViewById(R.id.webb);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        cepInput = findViewById(R.id.cepInput);
        loginInput = findViewById(R.id.login);
        resultTextView = findViewById(R.id.resultTextView);
        searchButton = findViewById(R.id.searchButton);
        Button bgit = findViewById(R.id.button);


        bgit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarGit(loginInput.getText().toString());
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("https://viacep.com.br/ws/")
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ViaCepService.class);
        serviceGit = retrofit.create(GitService.class);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarCep(cepInput.getText().toString());
            }
        });

    }

    private void buscarCep(String cep) {
        Call<CepResponse> call = service.getCep(cep);
        call.enqueue(new Callback<CepResponse>() {
            @Override
            public void onResponse(Call<CepResponse> call, Response<CepResponse> response) {
                if (response.isSuccessful()) {
                    CepResponse cepResponse = response.body();
                    resultTextView.setText(cepResponse.toString());
                    System.out.println( cepResponse.getUf() );
                }
            }

            @Override
            public void onFailure(Call<CepResponse> call, Throwable t) {
                resultTextView.setText("Erro: " + t.getMessage());
            }
        });
    }



    private void buscarGit(String login) {
        Call<GitResponse> call = serviceGit.getUsuario(login);
        call.enqueue(new Callback<GitResponse>() {
            @Override
            public void onResponse(Call<GitResponse> call, Response<GitResponse> response) {
                if (response.isSuccessful()) {
                    GitResponse gitresponse = response.body();
                    resultTextView.setText(gitresponse.avatar_url.toString());
                    W.loadUrl(gitresponse.avatar_url.toString());
                    System.out.println( gitresponse.repos_url );
                }
            }

            @Override
            public void onFailure(Call<GitResponse> call, Throwable t) {
                resultTextView.setText("Erro: " + t.getMessage());
            }
        });
    }
}