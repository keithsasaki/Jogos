package com.example.keith.jogos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/*
    Classe UserInfos que exibe na activity os dados do usuario
*/
public class UserInfos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //habilita o botao de voltar para pagina inciial

        Bundle bundle = getIntent().getExtras(); //pega os dados do usuario passado pelo bundle

        new LoadImage((ImageView) findViewById(R.id.imgUser))//cria uma instancia da classe LoadImage passando para
                .execute(bundle.getString("avatar"));         //o construtor o link da imagem a ser carregada
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
        Funcao que preenche os textViews com os dados do usuario
        Parametro: bundle que contem todas as informacoes do usuario
    */
    private void setTextViews(Bundle bundle){
        String name = bundle.getString("name");
        String lastName = bundle.getString("lastname");
        String email = bundle.getString("email");
        String birthday = bundle.getString("birthday");
        String address = bundle.getString("address");
        String city = bundle.getString("city");
        String country = bundle.getString("country");

        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvLastName = (TextView) findViewById(R.id.tvLastName);
        TextView tvEmail = (TextView) findViewById(R.id.tvEmail);
        TextView tvBirthday = (TextView) findViewById(R.id.tvBirthday);
        TextView tvAddress = (TextView) findViewById(R.id.tvAddress);
        TextView tvCity = (TextView) findViewById(R.id.tvCity);
        TextView tvCountry = (TextView) findViewById(R.id.tvCountry);

        tvName.setText(name);
        tvLastName.setText(lastName);
        tvEmail.setText(email);
        tvBirthday.setText(birthday);
        tvAddress.setText(address);
        tvCity.setText(city);
        tvCountry.setText(country);

        getSupportActionBar().setTitle("Perfil");
    }
}
