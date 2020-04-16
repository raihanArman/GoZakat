package id.co.myproject.gozakat.view.zakat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import id.co.myproject.gozakat.R;
import id.co.myproject.gozakat.util.Helper;
import id.co.myproject.gozakat.view.MainActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ZakatActivity extends AppCompatActivity {

    public static boolean prosesZakat = false;
    int zakat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakat);

        getSupportActionBar().setTitle("Input Zakat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        zakat = getIntent().getIntExtra("type_zakat", 0);

        InputZakatFragment inputZakatProfesiFragment = new InputZakatFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type_zakat", zakat);
        inputZakatProfesiFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_profesi, inputZakatProfesiFragment);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            if (!prosesZakat){
                finish();
            }else {
                getSupportFragmentManager().popBackStackImmediate();
                InputMasjidFragment.selectMasjid = false;
            }
        }else if (item.getItemId() == R.id.info_nav){
            AlertDialog.Builder builder = new AlertDialog.Builder(ZakatActivity.this);
            if (zakat == Helper.KODE_ZAKAT_PROFESI) {
                builder.setMessage("Zakat Profesi adalah zakat yang dikeluarkan dari penghasilan profesi (hasil profesi) bila telah mencapai nisab. Profesi tersebut misalnya pegawai negeri atau swasta, konsultan, dokter, notaris, akuntan, artis, dan wiraswasta.\n\n\nNisab : Rp. 4.974.320");
            }else {
                builder.setMessage("Zakat maal yang dimaksud dalam perhitungan ini adalah zakat yang dikenakan atas uang, emas, surat berharga, dan aset yang disewakan\n\n\nNisab Emas : 85 gram\nNisab Perak : 595 gram");
            }
            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_zakat, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
