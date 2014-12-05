package com.example.campitos.springandroid4;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


public class Inicio extends ActionBarActivity {
    TextView textoEstacion;
    Calendar cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Typeface tf = Typeface.createFromAsset(this.getAssets(), "Cinzel-Regular.otf");
    TextView putaTosMierdera= (TextView) findViewById(R.id.tosPerra);
        putaTosMierdera.setTypeface(tf);
        textoEstacion= (TextView) findViewById(R.id.textoEstacion);
        textoEstacion.setTypeface(tf);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.actualizar){

            CharSequence text = "Actualizando la  estación";
            int duracion = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(getApplicationContext(), text, duracion);
            toast.show();
            cal= Calendar.getInstance();
            final int hora= cal.get(Calendar.HOUR_OF_DAY);
            final   int minuto=cal.get(Calendar.MINUTE);
            new AsyncTask<String, Integer, String>(){
                String mensaje="nada de nada!!";
                ArrayList<Estacion> temperaturas;
                public void onPreExecute(){

                    textoEstacion.setText("Cargando esta mamada...");
                }
                @Override
                protected String doInBackground(String... params) {
                    try{
                        temperaturas=leerTemepraturas("nada");
                    }catch(Exception e){
                        textoEstacion.setText(e.getMessage());
                    }
                    return mensaje;
                }
                public  void onPostExecute(String mensaje){
                try{
                       textoEstacion.setText("La temperatura a las "+hora+":"+minuto+" es de "+temperaturas.get(hora).getTemperatura()+" °C");
                }catch(Exception e){
                          textoEstacion.setText(e.getMessage());
                  }
                }


            }.execute(null,null,null);

        }
        if(id==R.id.salir){
            finish();
        }


        return super.onOptionsItemSelected(item);
    }


    public ArrayList<Estacion> leerTemepraturas(String miurl)throws Exception{
        String leido="nada se leyo  :(";

        HttpHeaders requestHeaders=new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));

        HttpEntity<?> requestEntity=new HttpEntity<Object>(requestHeaders);

        String url="http://campitos-ley.whelastic.net/uv/servicios/estacion/temperatura";

        RestTemplate restTemplate=new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        ResponseEntity<String> responseEntity=restTemplate.exchange(url, HttpMethod.GET,requestEntity, String.class);
        leido=responseEntity.getBody();

        ObjectMapper mapper=new ObjectMapper();
        ArrayList<Estacion> temperaturas=mapper.readValue(leido, new TypeReference<ArrayList<Estacion>>(){});

        return temperaturas;

        }


}
