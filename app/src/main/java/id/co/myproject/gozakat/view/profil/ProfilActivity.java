package id.co.myproject.gozakat.view.profil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.gozakat.BuildConfig;
import id.co.myproject.gozakat.R;
import id.co.myproject.gozakat.adapter.RiwayatPembayaranAdapter;
import id.co.myproject.gozakat.model.Muzakki;
import id.co.myproject.gozakat.model.ZakatHistory;
import id.co.myproject.gozakat.request.ApiRequest;
import id.co.myproject.gozakat.request.RetrofitRequest;
import id.co.myproject.gozakat.view.MainActivity;
import id.co.myproject.gozakat.view.login.LoginActivity;
import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ProfilActivity extends AppCompatActivity {

    ImageView ivUser, ivSetting, ivBack;
    TextView tvUser, tvEmail, tv_riwayat;
    LinearLayout lv_no_riwayat;
    Button btnLogOut;
    RecyclerView rv_pembayaran;
    RiwayatPembayaranAdapter riwayatPembayaranAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int idUser;
    ApiRequest apiRequest;
    public static boolean statusUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        idUser = sharedPreferences.getInt("id_user", 0);
        ivUser = findViewById(R.id.iv_user);
        ivBack = findViewById(R.id.iv_back);
        ivSetting = findViewById(R.id.iv_setting);
        tv_riwayat = findViewById(R.id.tv_riwayat);
        lv_no_riwayat = findViewById(R.id.lv_no_riwayat);
        tvUser = findViewById(R.id.tv_user);
        tvEmail = findViewById(R.id.tv_email);
        btnLogOut = findViewById(R.id.btn_log_out);
        rv_pembayaran = findViewById(R.id.rv_pembayaran);

        ivSetting.setVisibility(View.VISIBLE);
        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilActivity.this, EditProfilActivity.class);
                intent.putExtra("id_user", idUser);
                startActivity(intent);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        loadDataUser();

        riwayatPembayaranAdapter = new RiwayatPembayaranAdapter(this);
        rv_pembayaran.setLayoutManager(new LinearLayoutManager(this));
        rv_pembayaran.setAdapter(riwayatPembayaranAdapter);
        loadDataPembayaran();

    }

    private void loadDataPembayaran() {
        Call<List<ZakatHistory>> getHistoryRequest = apiRequest.getHistoryRequest(idUser);
        getHistoryRequest.enqueue(new Callback<List<ZakatHistory>>() {
            @Override
            public void onResponse(Call<List<ZakatHistory>> call, Response<List<ZakatHistory>> response) {
                if (response.isSuccessful()){
                    List<ZakatHistory> zakatHistoryList = response.body();
                    if (zakatHistoryList.size() <= 0){
                        lv_no_riwayat.setVisibility(View.VISIBLE);
                        tv_riwayat.setVisibility(View.GONE);
                        rv_pembayaran.setVisibility(View.GONE);
                    }else {
                        lv_no_riwayat.setVisibility(View.GONE);
                        tv_riwayat.setVisibility(View.VISIBLE);
                        rv_pembayaran.setVisibility(View.VISIBLE);
                        riwayatPembayaranAdapter.setZakatHistoryList(zakatHistoryList);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ZakatHistory>> call, Throwable t) {
                Toast.makeText(ProfilActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void signOut() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Proses ...");
            progressDialog.show();
            editor.putInt("id_user", 0);
            editor.commit();
            auth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }


    private void loadDataUser() {
        Call<Muzakki> muzakkiCall = apiRequest.getMuzakkiItemRequest(idUser);
        muzakkiCall.enqueue(new Callback<Muzakki>() {
            @Override
            public void onResponse(Call<Muzakki> call, Response<Muzakki> response) {
                if (response.isSuccessful()){
                    Muzakki muzakki = response.body();
                    String avatar = muzakki.getAvatar();
                    if (muzakki.getAvatar().equals("")){
                        avatar = BuildConfig.BASE_URL_GAMBAR+"muzakki/muzakki.png";
                        Glide.with(ProfilActivity.this).load(avatar).into(ivUser);
                    }else {
                        Glide.with(ProfilActivity.this).load(BuildConfig.BASE_URL_GAMBAR+"muzakki/"+avatar).into(ivUser);
                    }
                    tvUser.setText(muzakki.getNama());
                    tvEmail.setText(muzakki.getEmail());
                }
            }

            @Override
            public void onFailure(Call<Muzakki> call, Throwable t) {
                Toast.makeText(ProfilActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (statusUpdate) {
            loadDataUser();
        }
    }
}
