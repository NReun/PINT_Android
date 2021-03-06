package com.example.pint_android_v3.quem_vai_consigo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pint_android_v3.DataBase.BaseDadosInterface;
import com.example.pint_android_v3.DataBase.DadosUtilizador.Model_User_Information;
import com.example.pint_android_v3.DataBase.ListagemCidadao.ModelListagemCidadao;
import com.example.pint_android_v3.DataBase.ListagemCidadao.dataListagemCidadao;
import com.example.pint_android_v3.DataBase.ListagemPassageirosCondutor.ModelListagemPassageirosCondutor;
import com.example.pint_android_v3.DataBase.ListagemPassageirosCondutor.DataListagemCondutor;
import com.example.pint_android_v3.R;
import com.example.pint_android_v3.barra_lateral.barra_lateral_pro;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class quem_vai_consigo extends barra_lateral_pro {

    private int user_id;
    private int viagem_id;
    private String BASE_URL ="https://pintbackend.herokuapp.com";
    private ArrayList<dataListagemCidadao> informacaoViagem;
    ArrayList<String> ListNome;
    ArrayList<String> ListLocalidade;
    ArrayList<Integer> ListIdPass;
    private TextView Nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quem_vai_consigo);

        Intent X = getIntent();
        Bundle b = X.getExtras();
        if(b!=null){
            user_id = (int) b.get("user_id");
            viagem_id = (int) b.get("idViagem");
        }
        Nome = findViewById(R.id.condutor_nome_quem_vai_consigo);

        getInformationFromdb(viagem_id);

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

        Call<ModelListagemCidadao> call = baseDadosInterface.executeGetListagemQuery(id);

        call.enqueue(new Callback<ModelListagemCidadao>() {
            @Override
            public void onResponse(Call<ModelListagemCidadao> call, Response<ModelListagemCidadao> response) {
                if (!response.isSuccessful()){
                    Log.i("Erro", "Erro a ir ao link");
                }
                if (response.code() == 200){
                    if (response.body().getDataListagemCidadao().size() != 0) {
                        informacaoViagem = response.body().getDataListagemCidadao();
                        int id_motorista = Integer.parseInt(informacaoViagem.get(0).getId_motorista());
                        Get_user_id_information(id_motorista);
                        PopulateLista();
                    }else
                        Log.i("Erro", response.message());
                }
            }

            @Override
            public void onFailure(Call<ModelListagemCidadao> call, Throwable t) {
                Log.i("Failure:", t.toString());
            }
        });

    }



    private void PopulateLista()
    {
       ListNome = new ArrayList<>();
       ListLocalidade = new ArrayList<>();
       ListIdPass = new ArrayList<>();

        for (int i = 0; i < informacaoViagem.size(); i++) {
            ListNome.add(informacaoViagem.get(i).getNome());
            ListLocalidade.add(informacaoViagem.get(i).getMorada_utilizador());
            ListIdPass.add(informacaoViagem.get(i).getIdPass());
        }
        SetTexts();
        HideButtons(ListNome.size());//Esconde os botões
    }


    private void HideButtons(int num)
    {
        if(num >= 4) return;

        ImageView Contentor;
        TextView Nome;
        TextView Localidade;
        ImageView Perfil;
        ImageView Caixa = findViewById(R.id.contentor_outros_tripulantes);

        if(num <= 3)
        {
            Contentor = findViewById(R.id.contentor4_quem_vai_consigo2);
            Perfil = findViewById(R.id.tripulante_image_4_quem_vai_consigo2);
            Localidade = findViewById(R.id.tripulante_localidade_4_quem_vai_consigo2);
            Nome = findViewById(R.id.tripulante_nome_4_quem_vai_consigo2);

            Contentor.setVisibility(View.GONE);
            Perfil.setVisibility(View.GONE);
            Localidade.setVisibility(View.GONE);
            Nome.setVisibility(View.GONE);

        }
        if(num <=2)
        {
            Contentor = findViewById(R.id.contentor3_quem_vai_consigo);
            Perfil = findViewById(R.id.tripulante_image_3_quem_vai_consigo);
            Localidade = findViewById(R.id.tripulante_localidade_3_quem_vai_consigo);
            Nome = findViewById(R.id.tripulante_nome_3_quem_vai_consigo);

            Contentor.setVisibility(View.GONE);
            Perfil.setVisibility(View.GONE);
            Localidade.setVisibility(View.GONE);
            Nome.setVisibility(View.GONE);

        }
        if(num <=1)
        {
            Contentor = findViewById(R.id.contentor2_quem_vai_consigo2);
            Perfil = findViewById(R.id.tripulante_image_2_quem_vai_consigo2);
            Localidade = findViewById(R.id.tripulante_localidade_2_quem_vai_consigo2);
            Nome = findViewById(R.id.tripulante_nome_2_quem_vai_consigo2);

            Contentor.setVisibility(View.GONE);
            Perfil.setVisibility(View.GONE);
            Localidade.setVisibility(View.GONE);
            Nome.setVisibility(View.GONE);


        }




    }

    private void SetTexts()
    {
        int num = ListNome.size();

        TextView Nome;

        TextView Localidade;


        if(num >= 4)
        {
            Localidade = findViewById(R.id.tripulante_localidade_4_quem_vai_consigo2);
            Nome = findViewById(R.id.tripulante_nome_4_quem_vai_consigo2);
            Nome.setText(ListNome.get(3));
            Localidade.setText(ListLocalidade.get(3));


        }
        if(num >= 3)
        {
            Localidade = findViewById(R.id.tripulante_localidade_3_quem_vai_consigo);
            Nome = findViewById(R.id.tripulante_nome_3_quem_vai_consigo);
            Nome.setText(ListNome.get(2));
            Localidade.setText(ListLocalidade.get(2));


        }
        if(num >=2)
        {
            Localidade = findViewById(R.id.tripulante_localidade_2_quem_vai_consigo2);
            Nome = findViewById(R.id.tripulante_nome_2_quem_vai_consigo2);
            Nome.setText(ListNome.get(1));
            Localidade.setText(ListLocalidade.get(1));


        }
        if(num >=1)
        {
            Localidade = findViewById(R.id.tripulante_localidade_1_quem_vai_consigo);
            Nome = findViewById(R.id.tripulante_nome_1_quem_vai_consigo);
            Nome.setText(ListNome.get(0));
            Localidade.setText(ListLocalidade.get(0));

        }




    }

    public void Get_user_id_information(int id){
        Retrofit retrofit;
        BaseDadosInterface baseDadosInterface;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        baseDadosInterface =  retrofit.create(BaseDadosInterface.class);

        //Log.i("O id do user:", ""+ id);
        Call<Model_User_Information> call = baseDadosInterface.executeGetUser(id);

        call.enqueue(new Callback<Model_User_Information>() {
            @Override
            public void onResponse(Call<Model_User_Information> call, Response<Model_User_Information> response) {
                if (!response.isSuccessful()){
                    //makeToastFordesambiguacao("Erro a ir ao link");
                    Log.i("Erro", "Erro a ir ao link class perfil_cliente");
                }
                if (response.code() == 200){
                    if (response.body() != null) {
                        Log.i("user_perfil_cliente", response.body().getGet_user().get(0).toString());
                        Nome.setText(response.body().getGet_user().get(0).getNome_utilizador());


                    }
                    //makeToastFordesambiguacao("Erro Server Info");
                }
                else{
                    //makeToastFordesambiguacao("Erro: 'Sem data'"+ response.message());
                    Log.i("Erro", "" + response.message());

                }

            }

            @Override
            public void onFailure(Call<Model_User_Information> call, Throwable t) {
                Log.i("Failure:", t.toString());
                //makeToastFordesambiguacao("Failure: "+ t.toString());
            }
        });


    }



}
