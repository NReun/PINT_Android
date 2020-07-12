package com.example.pint_android_v3.notificacoes;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pint_android_v3.DataBase.BaseDadosInterface;
import com.example.pint_android_v3.DataBase.ViagemUnica.ModelViagemUnica;
import com.example.pint_android_v3.DataBase.ViagensInformacao.dataViagem;
import com.example.pint_android_v3.R;
import com.example.pint_android_v3.barra_lateral.barra_lateral_condutor;
import com.example.pint_android_v3.barra_lateral.barra_lateral_pro;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class notificacoes_mais_info_condutor extends barra_lateral_condutor {

    private int user_id;
    private int id_viagem;
    private String BASE_URL ="http://10.0.2.2:3000";
    private ArrayList<dataViagem> informacaoViagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacoes_mais_info);

        Intent X = getIntent();
        Bundle b = X.getExtras();
        if(b!=null){
            user_id = (int) b.get("user_id");
            //Log.i("id_user", ""+ id_user);

        }
        getInformationFromdb(id_viagem);
        Bar_Settings(user_id);
    }

    private void getInformationFromdb(int id) {
        if(id == 0) return;

        Retrofit retrofit;
        BaseDadosInterface baseDadosInterface;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        baseDadosInterface =  retrofit.create(BaseDadosInterface.class);

        //Log.i("O id do user:", ""+ id);
        Call<ModelViagemUnica> call = baseDadosInterface.executeViagemUnica(id);

        call.enqueue(new Callback<ModelViagemUnica>() {
            @Override
            public void onResponse(Call<ModelViagemUnica> call, Response<ModelViagemUnica> response) {
                if (!response.isSuccessful()){
                    Log.i("Erro", "L99 viagens efetuadas");
                }
                if (response.code() == 200){
                    if (response.body().getDataViagem() != null) {
                        //Log.i("body", "" + response.body().getDataViagem().get(0).toString());
                        informacaoViagem = response.body().getDataViagem();
                        populateContainer();


                    }else
                        Log.i("Erro", "L105 listagem condutor");
                }
                else{
                    Log.i("Erro", "L109 listagem condutor sem sata");
                }
            }

            @Override
            public void onFailure(Call<ModelViagemUnica> call, Throwable t) {
                Log.i("Failure:", t.toString());
                //makeToastFordesambiguacao("Failure: "+ t.toString());
            }
        });

    }

    private void populateContainer()
    {
        ImageView CheckDog = findViewById(R.id.canideo_check_not_mais_info);
        ImageView CheckMala = findViewById(R.id.mala_check_not_mais_info);
        ImageView CheckWheel = findViewById(R.id.wheel_check_not_mais_info);
        TextView Origem = findViewById(R.id.local_user_textView_origem_not_mais_info);
        TextView  Destino = findViewById(R.id.local_user_textView_not_mais_info);
        TextView Hora = findViewById(R.id.local_user_textView_hora_not_mais_info);
        TextView Dia = findViewById(R.id.local_user_textView_dia_not_mais_info);

        if(informacaoViagem.get(0).getAnimal() == 0)
        {
            CheckDog.setVisibility(View.GONE);

        }
        if(informacaoViagem.get(0).getBagagem_pedido() == 0)
        {
            CheckMala.setVisibility(View.GONE);

        }
        if(informacaoViagem.get(0).getNecessidadesespeciais_pedido() == 0)
        {
            CheckWheel.setVisibility(View.GONE);
        }
        Origem.setText(informacaoViagem.get(0).getOrigemNome());
        Destino.setText(informacaoViagem.get(0).getDestinoNome());
        Hora.setText(informacaoViagem.get(0).getHora_viagem() + "h");
        Dia.setText(informacaoViagem.get(0).getDia_viagem());





    }

}

