package id.co.myproject.gozakat.view.lainnya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.gozakat.R;
import id.co.myproject.gozakat.adapter.HistoryLainnyaAdapter;
import id.co.myproject.gozakat.adapter.ZakatLainnyaAdapter;
import id.co.myproject.gozakat.model.ZakatHistory;
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

public class HistoryLainnyaActivity extends AppCompatActivity {

    RecyclerView rv_histori;
    ImageView iv_back;
    SharedPreferences sharedPreferences;
    ApiRequest apiRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_lainnya);

        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);

        int idUser = sharedPreferences.getInt("id_user", 0);

        rv_histori = findViewById(R.id.rv_histori);
        iv_back = findViewById(R.id.iv_back);

        HistoryLainnyaAdapter zakatLainnyaAdapter = new HistoryLainnyaAdapter(this, apiRequest);
        rv_histori.setLayoutManager(new LinearLayoutManager(this));
        rv_histori.setAdapter(zakatLainnyaAdapter);


        Call<List<ZakatHistory>> getHistoryRequest = apiRequest.getHistoryRequest(idUser);
        getHistoryRequest.enqueue(new Callback<List<ZakatHistory>>() {
            @Override
            public void onResponse(Call<List<ZakatHistory>> call, Response<List<ZakatHistory>> response) {
                if (response.isSuccessful()){
                    List<ZakatHistory> zakatHistoryList = response.body();
                    zakatLainnyaAdapter.setZakatHistoryList(zakatHistoryList);
                }
            }

            @Override
            public void onFailure(Call<List<ZakatHistory>> call, Throwable t) {
                Toast.makeText(HistoryLainnyaActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
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
