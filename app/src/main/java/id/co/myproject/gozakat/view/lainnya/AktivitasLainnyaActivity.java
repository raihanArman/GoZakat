package id.co.myproject.gozakat.view.lainnya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.gozakat.R;
import id.co.myproject.gozakat.adapter.ZakatLainnyaAdapter;
import id.co.myproject.gozakat.model.Zakat;
import id.co.myproject.gozakat.request.ApiRequest;
import id.co.myproject.gozakat.request.RetrofitRequest;
import id.co.myproject.gozakat.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class AktivitasLainnyaActivity extends AppCompatActivity {

    RecyclerView rv_aktivitas;
    ImageView iv_back;
    SharedPreferences sharedPreferences;
    ApiRequest apiRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivitas_lainnya);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);

        int idUser = sharedPreferences.getInt("id_user", 0);

        rv_aktivitas = findViewById(R.id.rv_aktivitas);
        iv_back = findViewById(R.id.iv_back);

        ZakatLainnyaAdapter zakatLainnyaAdapter = new ZakatLainnyaAdapter(this, apiRequest);
        rv_aktivitas.setLayoutManager(new LinearLayoutManager(this));
        rv_aktivitas.setAdapter(zakatLainnyaAdapter);

        Call<List<Zakat>> getZakatRequest = apiRequest.getZakatRequest(idUser);
        getZakatRequest.enqueue(new Callback<List<Zakat>>() {
            @Override
            public void onResponse(Call<List<Zakat>> call, Response<List<Zakat>> response) {
                if (response.isSuccessful()){
                    List<Zakat> zakatList = response.body();
                    zakatLainnyaAdapter.setZakatList(zakatList);
                }
            }

            @Override
            public void onFailure(Call<List<Zakat>> call, Throwable t) {
                Toast.makeText(AktivitasLainnyaActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
