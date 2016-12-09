package com.example.keith.jogos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
    Classe da activity principal do projeto que contem a lista dos jogos mostrados numa ListView
*/
public class MainActivity extends AppCompatActivity {

    /*
        Funcao chamada quando a activity eh criada
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Token Lab Games");

        ArrayList<Game> gamesInfo = null;
        final ListView list = (ListView) findViewById(R.id.lvGames);
        ArrayList<String> gameNames = new ArrayList<String>();
        try {
            gamesInfo = getGames();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i=0;i<gamesInfo.size();i++)
            gameNames.add(gamesInfo.get(i).getName());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,gameNames);
        list.setAdapter(arrayAdapter);

        //codigo que cria um bundle com as informacoes do jogo selecionado e chama a activity GameDetails
        final ArrayList<Game> finalGamesInfo = gamesInfo;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("gameName", finalGamesInfo.get(position).getName());
                bundle.putString("image", finalGamesInfo.get(position).getImage());
                bundle.putString("releaseDate", finalGamesInfo.get(position).getRelease_date());
                bundle.putString("trailer", finalGamesInfo.get(position).getTrailer());
                bundle.putStringArrayList("platforms",finalGamesInfo.get(position).getPlatforms());
                Intent intentGameDetail = new Intent(view.getContext(), GameDetail.class);
                intentGameDetail.putExtras(bundle);
                startActivityForResult(intentGameDetail, 0);

            }
        });

    }

    /*
        Funcao chamada quando um item do options menu eh selecionado.
        Parametro: MenuItem
        Retorno: boolean
    */
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), UserInfos.class); //cria uma intent para abrir a tela do usuario
        User user = null;
        Bundle bundle = null;
        try {
            user = getUser();
            bundle  = getUserInfos(user);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        myIntent.putExtras(bundle);
        startActivityForResult(myIntent, 0); //starta a activity das informacoes do usuario
        return true;
    }

    /*
        Funcao que cria uma instancia da classe usuario e seta seus valores com os dados obtidos do arquivo json
        Retorno: retorna o objeto user instanciado
    */
    private User getUser() throws IOException, JSONException {
        JSONObject json = new JSONObject(readData("userInfo.json"));
        User user = new User();
        user.setName(json.getString("name"));
        user.setLastName(json.getString("lastname"));
        user.setAvatar(json.getString("avatar"));
        user.setEmail(json.getString("email"));

        String date,d,m,y,aux = json.getString("birthday");
        d = aux.substring(8,10);
        m = aux.substring(5,7);
        y = aux.substring(0,4);
        date = d+"/"+m+"/"+y;
        user.setBirthday(date);
        user.setAddress(json.getString("address"));
        user.setCity(json.getString("city"));
        user.setCountry(json.getString("country"));

        return user;
    }

    /*
        Funcao que cria uma instancia da classe bundle e insere os dados do usuario para depois ser passado para outra activity
        Retorno: retorna o objeto bundle instanciado
    */
    private Bundle getUserInfos(User user) throws IOException, JSONException {

        ArrayList<String> userInfos = new ArrayList<String>();
        Bundle bundle = new Bundle();
        bundle.putString("name", user.getName());
        bundle.putString("lastname",user.getLastName() );
        bundle.putString("avatar", user.getAvatar());
        bundle.putString("email", user.getEmail());
        bundle.putString("address",user.getAddress());
        bundle.putString("city",user.getCity());
        bundle.putString("country",user.getCountry());
        bundle.putString("birthday",user.getBirthday());

        return bundle;
    }

    /*
        Funcao que utiliza o metodo inflate para criarmos um botao na actionBar
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /*
        Funcao que retorna um arrayList de jogos
    */
    private ArrayList<Game> getGames() throws IOException, JSONException {
        JSONObject json = new JSONObject(readData("games.json"));
        ArrayList<Game> games = saveGames(json);
        return games;
    }

    /*
        Funcao que le o texto dos jogos do arquivo json armazenado na pasta assets
        Retorno: String, retorna uma string com o texto json do arquivo
     */
    private String readData(String file) throws IOException {
        InputStreamReader isr = new InputStreamReader(getAssets().open(file));
        BufferedReader reader = new BufferedReader(isr);
        String data = null;
        StringBuilder sb = new StringBuilder();
        while((data = reader.readLine()) != null){
            sb.append(data+"\n");
        }
        reader.close();
        isr.close();
        return sb.toString();
    }

    /*
        Funcao que cria um ArrayList de jogos para armazenar os dados de cada um deles.
        Parametro: JSONObject, recebe um objeto json para retirar os dados e adicionar no ArrayList de jogos.
        Retorno: Retorna o ArrayList de jogos
     */
    private ArrayList<Game> saveGames(JSONObject json) throws JSONException {
        ArrayList<Game> games = new ArrayList<Game>();
        JSONArray ja = json.getJSONArray("games");

        for(int i=0;i<ja.length();i++){
            Game g = new Game();
            JSONObject jo = ja.getJSONObject(i);
            g.setName(jo.getString("name"));
            g.setImage(jo.getString("image"));
            g.setRelease_date(jo.getString("release_date"));
            g.setTrailer(jo.getString("trailer"));
            JSONArray plat = jo.getJSONArray("platforms");
            for(int j=0;j<plat.length()-1;j++){
                g.addPlatforms(plat.getString(j));
            }
            games.add(g);
        }

        return games;
    }


}
