package com.example.pint_android_v3.quem_vai_consigo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pint_android_v3.DataBase.BaseDadosInterface;
import com.example.pint_android_v3.DataBase.ListagemPassageirosCondutor.ModelListagemPassageirosCondutor;
import com.example.pint_android_v3.DataBase.ListagemPassageirosCondutor.UtilizadorPassageiroInformacao;
import com.example.pint_android_v3.R;
import com.example.pint_android_v3.barra_lateral.barra_lateral_condutor;
import com.example.pint_android_v3.pagamentoCondutor;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class quem_vai_consigo_condutor extends barra_lateral_condutor {

    private String BASE_URL ="https://pintbackend.herokuapp.com";
    private int user_id;
    private int idViagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quem_vai_consigo_condutor);

        Intent X = getIntent();
        Bundle b = X.getExtras();
        if(b!=null){
            user_id = (int) b.get("user_id");
            idViagem = (int) b.get("idViagem");
            povoarViagemComPassageiros();
        }

        Bar_Settings(user_id);

    }

    private void povoarViagemComPassageiros() {
        //baseDeDadosPassageiros
        ArrayList<String> nomesPassageiros = new ArrayList<>();
        ArrayList<String> localidadesPassageiros = new ArrayList<>();
        ArrayList<Integer> comparenciaPassageiros = new ArrayList<>();
        ArrayList<Integer> idCidadaoList = new ArrayList<>();
        try {
            Retrofit retrofit;
            BaseDadosInterface baseDadosInterface;

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            baseDadosInterface =  retrofit.create(BaseDadosInterface.class);

            Call<ModelListagemPassageirosCondutor> call = baseDadosInterface.executeGetListagem(idViagem);

            call.enqueue(new Callback<ModelListagemPassageirosCondutor>() {
                @Override
                public void onResponse(Call<ModelListagemPassageirosCondutor> call, Response<ModelListagemPassageirosCondutor> response) {
                    if (!response.isSuccessful()) {
                        Log.i("Erro", "Erro a ir ao link");
                    }
                    UtilizadorPassageiroInformacao utilizador;
                    for (int i = 0; i < response.body().getDataListagemCondutor().size(); i++) {
                        Log.i("idPassageiro", "" + response.body().getDataListagemCondutor().get(i).getIdPass());
                        utilizador = response.body().getDataListagemCondutor().get(i).getCidadao().getUtilizador();
                        nomesPassageiros.add(utilizador.getNome_utilizador());
                        localidadesPassageiros.add(utilizador.getMoradaUtilizador());
                        comparenciaPassageiros.add(response.body().getDataListagemCondutor().get(i).getCompareceu());
                        idCidadaoList.add(response.body().getDataListagemCondutor().get(i).getCidadao().getId_Utilizador());
                    }
                    adaptarFixer(nomesPassageiros, localidadesPassageiros, comparenciaPassageiros, idCidadaoList);
                }

                @Override
                public void onFailure(Call<ModelListagemPassageirosCondutor> call, Throwable t) {
                    Log.i("Failure:", t.toString());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void adaptarFixer(ArrayList<String> nomesPassageiros, ArrayList<String> localidadesPassageiros, ArrayList<Integer> comparenciaPassageiros,  ArrayList<Integer> idCidadaoList) {
        TextView nomePassageiroTextView;
        TextView localPassageiroTextView;
        ImageView btnPagamento;
        int contador=0;

        switch (nomesPassageiros.size()){ //preenche os contaners certos com informaçao
            case 4:
                contador++;
                nomePassageiroTextView = findViewById(R.id.tripulante_nome_4_quem_vai_consigo2_condutor);
                localPassageiroTextView = findViewById(R.id.tripulante_localidade_4_quem_vai_consigo2_condutor);
                nomePassageiroTextView.setText(nomesPassageiros.get(3));
                localPassageiroTextView.setText(localidadesPassageiros.get(3));
                btnPagamento = findViewById(R.id.btn_pagamento_4_quem_vai_consigo_condutor);
                if(comparenciaPassageiros.get(3) == 1){
                    //se ja esta registado que compareceu é pq o motorista ja confirmou a existencia de um pagamento
                    btnPagamento.setVisibility(View.GONE);
                }else{
                    btnPagamento.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Click_Pagamento(idCidadaoList.get(3));
                        }
                    });
                }
            case 3:
                contador++;
                nomePassageiroTextView = findViewById(R.id.tripulante_nome_3_quem_vai_consigo_condutor);
                localPassageiroTextView = findViewById(R.id.tripulante_localidade_3_quem_vai_consigo_condutor);
                nomePassageiroTextView.setText(nomesPassageiros.get(2));
                localPassageiroTextView.setText(localidadesPassageiros.get(2));
                btnPagamento = findViewById(R.id.btn_pagamento_3_quem_vai_consigo_condutor);
                if(comparenciaPassageiros.get(2) == 1){
                    //se ja esta registado que compareceu é pq o motorista ja confirmou a existencia de um pagamento
                    btnPagamento.setVisibility(View.GONE);
                }else{
                    btnPagamento.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Click_Pagamento(idCidadaoList.get(2));
                        }
                    });
                }
            case 2:
                contador++;
                nomePassageiroTextView = findViewById(R.id.tripulante_nome_2_quem_vai_consigo2_condutor);
                localPassageiroTextView = findViewById(R.id.tripulante_localidade_2_quem_vai_consigo2_condutor);
                nomePassageiroTextView.setText(nomesPassageiros.get(1));
                localPassageiroTextView.setText(localidadesPassageiros.get(1));
                btnPagamento = findViewById(R.id.btn_pagamento_2_quem_vai_consigo_condutor);
                if(comparenciaPassageiros.get(1) == 1){
                    //se ja esta registado que compareceu é pq o motorista ja confirmou a existencia de um pagamento
                    btnPagamento.setVisibility(View.GONE);
                }else{
                    btnPagamento.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Click_Pagamento(idCidadaoList.get(1));
                        }
                    });
                }
            case 1:
                contador++;
                nomePassageiroTextView = findViewById(R.id.tripulante_nome_quem_vai_consigo_condutor_1);
                localPassageiroTextView = findViewById(R.id.tripulante_localidade_1_quem_vai_consigo_condutor);
                nomePassageiroTextView.setText(nomesPassageiros.get(0));
                localPassageiroTextView.setText(localidadesPassageiros.get(0));
                btnPagamento = findViewById(R.id.btn_pagamento_1_quem_vai_consigo_condutor);
                if(comparenciaPassageiros.get(0) == 1){
                    //se ja esta registado que compareceu é pq o motorista ja confirmou a existencia de um pagamento
                    btnPagamento.setVisibility(View.GONE);
                }else{
                    btnPagamento.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Click_Pagamento(idCidadaoList.get(0));
                        }
                    });
                }
                break;
        }
        ImageView contaner;
        ImageView img;
        TextView txt;
        switch (contador){ //para apagar os que n tem informaçao
            case 0:
                contaner = findViewById(R.id.contentor1_quem_vai_consigo_condutor);
                contaner.setVisibility(View.GONE);
                img = findViewById(R.id.tripulante_image_1_quem_vai_consigo_condutor);
                img.setVisibility(View.GONE);
                img = findViewById(R.id.btn_pagamento_1_quem_vai_consigo_condutor);
                img.setVisibility(View.GONE);
                txt = findViewById(R.id.tripulante_nome_quem_vai_consigo_condutor_1);
                txt.setVisibility(View.GONE);
                txt = findViewById(R.id.tripulante_localidade_1_quem_vai_consigo_condutor);
                txt.setVisibility(View.GONE);
            case 1:
                contaner = findViewById(R.id.contentor2_quem_vai_consigo2_condutor);
                contaner.setVisibility(View.GONE);
                img = findViewById(R.id.tripulante_image_2_quem_vai_consigo2_condutor);
                img.setVisibility(View.GONE);
                img = findViewById(R.id.btn_pagamento_2_quem_vai_consigo_condutor);
                img.setVisibility(View.GONE);
                txt = findViewById(R.id.tripulante_nome_2_quem_vai_consigo2_condutor);
                txt.setVisibility(View.GONE);
                txt = findViewById(R.id.tripulante_localidade_2_quem_vai_consigo2_condutor);
                txt.setVisibility(View.GONE);
            case 2:
                contaner = findViewById(R.id.contentor3_quem_vai_consigo_condutor);
                contaner.setVisibility(View.GONE);
                img = findViewById(R.id.tripulante_image_3_quem_vai_consigo_condutor);
                img.setVisibility(View.GONE);
                img = findViewById(R.id.btn_pagamento_3_quem_vai_consigo_condutor);
                img.setVisibility(View.GONE);
                txt = findViewById(R.id.tripulante_nome_3_quem_vai_consigo_condutor);
                txt.setVisibility(View.GONE);
                txt = findViewById(R.id.tripulante_localidade_3_quem_vai_consigo_condutor);
                txt.setVisibility(View.GONE);
            case 3:
                contaner = findViewById(R.id.contentor4_quem_vai_consigo2_condutor);
                contaner.setVisibility(View.GONE);
                img = findViewById(R.id.tripulante_image_4_quem_vai_consigo2_condutor);
                img.setVisibility(View.GONE);
                img = findViewById(R.id.btn_pagamento_4_quem_vai_consigo_condutor);
                img.setVisibility(View.GONE);
                txt = findViewById(R.id.tripulante_nome_4_quem_vai_consigo2_condutor);
                txt.setVisibility(View.GONE);
                txt = findViewById(R.id.tripulante_localidade_4_quem_vai_consigo2_condutor);
                txt.setVisibility(View.GONE);
            default:
                break;
        }


    }

    public void Click_Pagamento(int idCidadao)
    {
        Intent pagamento = new Intent( quem_vai_consigo_condutor.this ,pagamentoCondutor.class);
        pagamento.putExtra("user_id", user_id);
        pagamento.putExtra("idViagem", idViagem);
        pagamento.putExtra("idCidadao", idCidadao);
        startActivity(pagamento);
    }
}
