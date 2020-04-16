package id.co.myproject.gozakat.view.zakat;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import id.co.myproject.gozakat.R;
import id.co.myproject.gozakat.util.KalkulatorListener;

import static id.co.myproject.gozakat.util.Helper.KODE_INFAQ;
import static id.co.myproject.gozakat.util.Helper.KODE_ZAKAT_EMAS;
import static id.co.myproject.gozakat.util.Helper.KODE_ZAKAT_LENGKAP;
import static id.co.myproject.gozakat.util.Helper.KODE_ZAKAT_MAL;
import static id.co.myproject.gozakat.util.Helper.KODE_ZAKAT_PERAK;
import static id.co.myproject.gozakat.util.Helper.KODE_ZAKAT_PROFESI;

/**
 * A simple {@link Fragment} subclass.
 */
public class KalkulatorMalFragmentDialog extends DialogFragment{

    EditText et_emas, et_perak;
    TextView tv_batal, tv_selesai;
    double hasil = 0;
    public KalkulatorMalFragmentDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kalkulator_mal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_emas = view.findViewById(R.id.et_emas);
        et_perak = view.findViewById(R.id.et_perak);
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
    }

    private void prosesPerhitungan() {
        double nisab_perak = 595;
//        Mazhab Syafi'i, Maliki, dan Hambali
        double nisab_emas = 85;
//        Mazhab Syafi'i, Maliki, dan Hambali (77.50)

        if(!TextUtils.isEmpty(et_emas.getText()) || !TextUtils.isEmpty(et_perak.getText())) {
            if (!TextUtils.isEmpty(et_emas.getText()) && TextUtils.isEmpty(et_perak.getText())) {
                double emas = Double.parseDouble(et_emas.getText().toString());
                if(emas >= nisab_emas){
                    hasil = emas*0.025;
                    prosesZakat(hasil);
                }else {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    Bundle bundle = new Bundle();
                    bundle.putInt("type_zakat", KODE_ZAKAT_MAL);
                    bundle.putInt("type_zakat_mal", KODE_ZAKAT_EMAS);
                    bundle.putString("nominal", String.valueOf(emas));
                    InfaqDialogFragment infaqDialogFragment = new InfaqDialogFragment();
                    infaqDialogFragment.setArguments(bundle);
                    infaqDialogFragment.show(fm, "");
                    dismiss();
                }
            } else if (TextUtils.isEmpty(et_emas.getText()) && !TextUtils.isEmpty(et_perak.getText())) {
                double perak = Double.parseDouble(et_perak.getText().toString());
                if(perak >= nisab_perak){
                    hasil = perak*0.025;
                    prosesZakat(hasil);
                }else {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    Bundle bundle = new Bundle();
                    bundle.putInt("type_zakat", KODE_ZAKAT_MAL);
                    bundle.putInt("type_zakat_mal", KODE_ZAKAT_PERAK);
                    bundle.putString("nominal", String.valueOf(perak));
                    InfaqDialogFragment infaqDialogFragment = new InfaqDialogFragment();
                    infaqDialogFragment.setArguments(bundle);
                    infaqDialogFragment.show(fm, "");
                    dismiss();
                }
            } else if (!TextUtils.isEmpty(et_emas.getText()) && !TextUtils.isEmpty(et_perak.getText())) {
                double emas = Double.parseDouble(et_emas.getText().toString());
                double perak = Double.parseDouble(et_perak.getText().toString());
                double hasil_emas, hasil_perak;
                if(emas >= nisab_emas && perak >= nisab_perak){
                    hasil_emas = emas*0.025;
                    hasil_perak = perak*0.025;
                    hasil = hasil_emas+hasil_perak;
                    prosesZakat(hasil);
                }else {
                    hasil = emas+perak;
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    Bundle bundle = new Bundle();
                    bundle.putInt("type_zakat", KODE_ZAKAT_MAL);
                    bundle.putInt("type_zakat_mal", KODE_ZAKAT_LENGKAP);
                    bundle.putString("nominal_perak", String.valueOf(perak));
                    bundle.putString("nominal_emas", String.valueOf(emas));
                    InfaqDialogFragment infaqDialogFragment = new InfaqDialogFragment();
                    infaqDialogFragment.setArguments(bundle);
                    infaqDialogFragment.show(fm, "");
                    dismiss();
                }
            }
        }else {
            Toast.makeText(getActivity(), "Isi salah satunya", Toast.LENGTH_SHORT).show();
        }
    }

    private void prosesZakat(double hasil){
        KalkulatorListener listener = (KalkulatorListener) getTargetFragment();
        listener.onFinishedKalkulator(hasil);
        dismiss();
    }

}
