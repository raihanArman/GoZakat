package id.co.myproject.gozakat.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.gozakat.BuildConfig;
import id.co.myproject.gozakat.R;
import id.co.myproject.gozakat.adapter.HistoryAdapter;
import id.co.myproject.gozakat.adapter.ZakatAdapter;
import id.co.myproject.gozakat.model.Muzakki;
import id.co.myproject.gozakat.model.Zakat;
import id.co.myproject.gozakat.model.ZakatHistory;
import id.co.myproject.gozakat.request.ApiRequest;
import id.co.myproject.gozakat.request.RetrofitRequest;
import id.co.myproject.gozakat.view.lainnya.AktivitasLainnyaActivity;
import id.co.myproject.gozakat.view.lainnya.HistoryLainnyaActivity;
import id.co.myproject.gozakat.view.profil.ProfilActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    TextView tv_user, tv_lihat_aktifitas, tv_lihat_history, tv_title_aktifitas, tv_title_histori;
    LinearLayout lv_no_activity;
    ImageView iv_user;
    TextView btn_zakat;
    RecyclerView rv_history, rv_aktivitas;
    ZakatAdapter zakatAdapter;
    HistoryAdapter historyAdapter;
    ApiRequest apiRequest;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_aktivitas = findViewById(R.id.rv_aktivitas);
        rv_history = findViewById(R.id.rv_history);
        tv_user = findViewById(R.id.tv_user);
        tv_lihat_aktifitas = findViewById(R.id.tv_lihat_aktivitas);
        tv_lihat_history = findViewById(R.id.tv_lihat_histori);
        lv_no_activity = findViewById(R.id.lv_no_activity);
        iv_user = findViewById(R.id.iv_user);
        btn_zakat = findViewById(R.id.btn_zakat);
        tv_title_aktifitas = findViewById(R.id.tv_title_aktifitas);
        tv_title_histori = findViewById(R.id.tv_title_histori);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);


        int idUser = sharedPreferences.getInt("id_user", 0);

        btn_zakat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JenisZakatActivity.class);
                startActivity(intent);
            }
        });

        zakatAdapter = new ZakatAdapter(this, apiRequest);
        rv_aktivitas.setLayoutManager(new LinearLayoutManager(this));
        rv_aktivitas.setAdapter(zakatAdapter);

        loadDataUser(idUser);

        loadAktivitas(idUser);

        historyAdapter = new HistoryAdapter(this, apiRequest);
        rv_history.setLayoutManager(new LinearLayoutManager(this));
        rv_history.setAdapter(historyAdapter);

        loadHistori(idUser);

        iv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfilActivity.class);
                startActivity(intent);
            }
        });

        tv_lihat_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HistoryLainnyaActivity.class);
                startActivity(intent);
            }
        });


        tv_lihat_aktifitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AktivitasLainnyaActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loadDataUser(int idUser) {
        Call<Muzakki> getMuzakkiItem = apiRequest.getMuzakkiItemRequest(idUser);
        getMuzakkiItem.enqueue(new Callback<Muzakki>() {
            @Override
            public void onResponse(Call<Muzakki> call, Response<Muzakki> response) {
                if (response.isSuccessful()){
                    Muzakki muzakki = response.body();
                    String avatar = muzakki.getAvatar();
                    if (muzakki.getAvatar().equals("")){
                        avatar = BuildConfig.BASE_URL_GAMBAR+"muzakki/muzakki.png";
                        Glide.with(MainActivity.this).load(avatar).into(iv_user);
                    }else {
                        Glide.with(MainActivity.this).load(BuildConfig.BASE_URL_GAMBAR+"muzakki/"+avatar).into(iv_user);
                    }
                    tv_user.setText(muzakki.getNama());
                }
            }

            @Override
            public void onFailure(Call<Muzakki> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadHistori(int idUser) {
        Call<List<ZakatHistory>> getHistoryRequest = apiRequest.getHistoryRequest(idUser);
        getHistoryRequest.enqueue(new Callback<List<ZakatHistory>>() {
            @Override
            public void onResponse(Call<List<ZakatHistory>> call, Response<List<ZakatHistory>> response) {
                if (response.isSuccessful()){
                    List<ZakatHistory> zakatHistoryList = response.body();
                    if (zakatHistoryList.size()<=0){
                        rv_history.setVisibility(View.GONE);
                        tv_lihat_history.setVisibility(View.GONE);
                        tv_title_histori.setVisibility(View.GONE);
                    }else {
                        rv_history.setVisibility(View.VISIBLE);
                        tv_lihat_history.setVisibility(View.VISIBLE);
                        tv_title_histori.setVisibility(View.VISIBLE);
                        historyAdapter.setZakatHistoryList(zakatHistoryList);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ZakatHistory>> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAktivitas(int idUser) {
        Call<List<Zakat>> getZakatRequest = apiRequest.getZakatRequest(idUser);
        getZakatRequest.enqueue(new Callback<List<Zakat>>() {
            @Override
            public void onResponse(Call<List<Zakat>> call, Response<List<Zakat>> response) {
                if (response.isSuccessful()){
                    List<Zakat> zakatList = response.body();
                    if (zakatList.size() <= 0){
                        rv_aktivitas.setVisibility(View.GONE);
                        tv_lihat_aktifitas.setVisibility(View.GONE);
                        tv_title_aktifitas.setVisibility(View.GONE);
                        lv_no_activity.setVisibility(View.VISIBLE);
                    }else {
                        rv_aktivitas.setVisibility(View.VISIBLE);
                        tv_lihat_aktifitas.setVisibility(View.VISIBLE);
                        tv_title_aktifitas.setVisibility(View.VISIBLE);
                        lv_no_activity.setVisibility(View.GONE);
                        zakatAdapter.setZakatList(zakatList);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Zakat>> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
