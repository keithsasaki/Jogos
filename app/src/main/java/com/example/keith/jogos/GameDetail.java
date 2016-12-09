package com.example.keith.jogos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/*
    Classe GameDetail que exibe os detalhes de cada Jogo, os valores dos campos a serem preenchidos sao passados
    da activity principal por bundle
*/
public class GameDetail extends AppCompatActivity {

    /*
        Funcao chamada quando um item do options menu eh selecionado.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //habilita o botao de voltar para pagina inicial

        Bundle bundle = getIntent().getExtras(); //pega os dados do jogo passado pela bundle

        new LoadImage((ImageView) findViewById(R.id.imgView))//cria uma instancia da classe LoadImage passando para
                .execute(bundle.getString("image"));         //o construtor o link da imagem a ser carregada
        setTextViews(bundle);
    }


    /*
        Funcao chamada quando um item do options menu eh selecionado.
        Parametro: MenuItem
        Retorno: boolean
    */
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class); //cria uma intent para voltar para tela inicial
        startActivityForResult(myIntent, 0); //starta a activity inicial
        return true;
    }

    /*
        Funcao que recebe um bundle por parametro para alterar os textos dos TextViews(nome, releaseDate e Platforms)
        alem de alterar o titulo da tela com o nome do jogo que foi clicado.
        Parametro: bundle, possui os valores do jogo a ser exibido
    */
    private void setTextViews(Bundle bundle){
        String gameName = bundle.getString("gameName");
        String releaseDate = bundle.getString("releaseDate");
        ArrayList<String> platforms = bundle.getStringArrayList("platforms");

        //repeticao para formar a string que sera exibida no campo texto das plataformas
        String strPlatforms = "";
        for(int i=0;i<platforms.size();i++) {
            strPlatforms += platforms.get(i);
            if(i == platforms.size()-2)
                strPlatforms += " e ";
            else if(i != platforms.size()-1)
                strPlatforms += ", ";
        }

        getSupportActionBar().setTitle(gameName); //altera o titulo da tela com o nome do jogo

        //pega os TextViews e altera os textos a serem exibidos
        TextView tvGameName = (TextView) findViewById(R.id.tvGameName);
        TextView tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        TextView tvPlatforms = (TextView) findViewById(R.id.tvPlatforms);
        tvGameName.setText(gameName);
        tvReleaseDate.setText(releaseDate);
        tvPlatforms.setText(strPlatforms);
    }
}
