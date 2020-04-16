package id.co.myproject.gozakat.view;

import androidx.appcompat.app.AppCompatActivity;
import id.co.myproject.gozakat.R;
import id.co.myproject.gozakat.view.zakat.ZakatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import static id.co.myproject.gozakat.util.Helper.KODE_ZAKAT_MAL;
import static id.co.myproject.gozakat.util.Helper.KODE_ZAKAT_PROFESI;

public class JenisZakatActivity extends AppCompatActivity {

    ImageView iv_back;
    LinearLayout lv_profesi, lv_mal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jenis_zakat);
        iv_back = findViewById(R.id.iv_back);
        lv_profesi = findViewById(R.id.lv_profesi);
        lv_mal = findViewById(R.id.lv_mal);

        lv_profesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JenisZakatActivity.this, ZakatActivity.class);
                intent.putExtra("type_zakat", KODE_ZAKAT_PROFESI);
                startActivity(intent);
            }
        });

        lv_mal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JenisZakatActivity.this, ZakatActivity.class);
                intent.putExtra("type_zakat", KODE_ZAKAT_MAL);
                startActivity(intent);
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
