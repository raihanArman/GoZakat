package id.co.myproject.gozakat.view.zakat;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import id.co.myproject.gozakat.R;

import static id.co.myproject.gozakat.util.Helper.KODE_ZAKAT_EMAS;
import static id.co.myproject.gozakat.util.Helper.KODE_ZAKAT_MAL;
import static id.co.myproject.gozakat.util.Helper.KODE_ZAKAT_PERAK;
import static id.co.myproject.gozakat.util.Helper.KODE_ZAKAT_PROFESI;
import static id.co.myproject.gozakat.util.Helper.rupiahFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfaqDialogFragment extends DialogFragment {

    TextView tv_nominal, tv_alihkan, tv_tidak, tv_keterangan;
    int hasilZakatMal;

    public InfaqDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_infaq_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_alihkan = view.findViewById(R.id.tv_alihkan);
        tv_nominal = view.findViewById(R.id.tv_nominal);
        tv_tidak = view.findViewById(R.id.tv_tidak);
        tv_keterangan = view.findViewById(R.id.tv_keterangan);

        String nominal = getArguments().getString("nominal");
        int type_zakat = getArguments().getInt("type_zakat", 0);
        if (type_zakat == KODE_ZAKAT_PROFESI) {
            tv_nominal.setText(rupiahFormat(Integer.parseInt(nominal)));
        }else if (type_zakat == KODE_ZAKAT_MAL){
            int zakat_mal = getArguments().getInt("type_zakat_mal", 0);
            if (zakat_mal == KODE_ZAKAT_EMAS){
                hasilZakatMal = (int)Double.parseDouble(nominal) * 666000;
                tv_keterangan.setText("Emas bersih anda sebesar");
                tv_nominal.setText((int)Double.parseDouble(nominal)+" gram");
            }else if(zakat_mal == KODE_ZAKAT_PERAK){
                hasilZakatMal = (int)Double.parseDouble(nominal) * 13930;
                tv_keterangan.setText("Perak bersih anda sebesar");
                tv_nominal.setText((int)Double.parseDouble(nominal)+" gram");
            }else {
                String nominalEmas = getArguments().getString("nominal_emas");
                String nominalPerak = getArguments().getString("nominal_perak");
                int total = (int)Double.parseDouble(nominalEmas)+(int)Double.parseDouble(nominalPerak);
                int hasilZakatMalEmas = (int)Double.parseDouble(nominalEmas) * 666000;
                int hasilZakatPerak = (int)Double.parseDouble(nominalPerak) * 13930;
                hasilZakatMal = hasilZakatMalEmas+hasilZakatPerak;
                tv_keterangan.setText("Emas dan Perak bersih anda sebesar");
                tv_nominal.setText(total+" gram");
            }
        }
        tv_tidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        tv_alihkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                InputInfaqFragment inputInfaqFragment = new InputInfaqFragment();
                Bundle bundle = new Bundle();
                if (type_zakat == KODE_ZAKAT_MAL) {
                    bundle.putString("nominal", String.valueOf(hasilZakatMal));
                }else {
                    bundle.putString("nominal", String.valueOf(nominal));
                }
                inputInfaqFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_profesi, inputInfaqFragment)
                        .addToBackStack("")
                        .commit();
                dismiss();
            }
        });

    }
}
