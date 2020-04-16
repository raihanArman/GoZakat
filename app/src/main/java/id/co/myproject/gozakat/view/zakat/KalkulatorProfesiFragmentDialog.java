package id.co.myproject.gozakat.view.zakat;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import id.co.myproject.gozakat.R;
import id.co.myproject.gozakat.util.KalkulatorListener;
import id.co.myproject.gozakat.util.NumberTextWatcher;

import static id.co.myproject.gozakat.util.Helper.KODE_ZAKAT_PROFESI;

/**
 * A simple {@link Fragment} subclass.
 */
public class KalkulatorProfesiFragmentDialog extends DialogFragment {

    EditText et_penghasilan, et_pengeluaran;
    TextView tv_batal, tv_selesai;
    double hasil = 0;

    public KalkulatorProfesiFragmentDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kalkulator_profesi, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_penghasilan = view.findViewById(R.id.et_penghasilan);
        et_pengeluaran = view.findViewById(R.id.et_pengeluaran);
        tv_selesai = view.findViewById(R.id.tv_selesai);
        tv_batal = view.findViewById(R.id.tv_batal);

        tv_selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prosesPerhitungan();
            }
        });

        tv_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        et_pengeluaran.addTextChangedListener(new NumberTextWatcher(et_pengeluaran));
        et_penghasilan.addTextChangedListener(new NumberTextWatcher(et_penghasilan));

    }

    private void prosesPerhitungan() {
        String s_penghasilan = et_penghasilan.getText().toString();
        String remove_penghasilan = s_penghasilan.replace(".", "");
        String s_pengeluaran = et_pengeluaran.getText().toString();
        String remove_pengeluaran = s_pengeluaran.replace(".", "");
        int penghasilan = Integer.parseInt(remove_penghasilan);
        int pengeluaran = Integer.parseInt(remove_pengeluaran);
        int harga_beras = 9566;
        int beras = 520;
        int nisab = beras*harga_beras;
        hasil = penghasilan-pengeluaran;
        if (et_penghasilan.getText().equals("") || et_pengeluaran.getText().equals("")) {
            Toast.makeText(getActivity(), "Isi terlebih dahulu", Toast.LENGTH_SHORT).show();
        }else {
            if (penghasilan <= pengeluaran) {
                Toast.makeText(getActivity(), "Jika pengeluaran anda lebih besar atau sama dengan penghasilan, maka anda belum wajib membayar zakat", Toast.LENGTH_SHORT).show();
            } else {
                if(hasil >= nisab){
                    KalkulatorListener listener = (KalkulatorListener) getTargetFragment();
                    double total_zakat = hasil*0.025;
                    listener.onFinishedKalkulator(total_zakat);
                    dismiss();
                }else {
                    int hasilZakat = (int)hasil;
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    Bundle bundle = new Bundle();
                    bundle.putInt("type_zakat", KODE_ZAKAT_PROFESI);
                    bundle.putString("nominal", String.valueOf(hasilZakat));
                    InfaqDialogFragment infaqDialogFragment = new InfaqDialogFragment();
                    infaqDialogFragment.setArguments(bundle);
                    infaqDialogFragment.show(fm, "");
                    dismiss();
                }
            }
        }
    }
}
