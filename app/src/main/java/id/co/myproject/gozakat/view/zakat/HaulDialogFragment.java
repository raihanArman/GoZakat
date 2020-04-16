package id.co.myproject.gozakat.view.zakat;


import android.content.SharedPreferences;
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
import id.co.myproject.gozakat.request.ApiRequest;
import id.co.myproject.gozakat.util.Helper;

import static id.co.myproject.gozakat.util.Helper.rupiahFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class HaulDialogFragment extends DialogFragment {

    TextView tv_peringatan, tv_ya, tv_belum;
    ApiRequest apiRequest;
    SharedPreferences sharedPreferences;
    String nominal;

    public HaulDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_haul_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_peringatan = view.findViewById(R.id.tv_peringatan);
        tv_ya = view.findViewById(R.id.tv_ya);
        tv_belum = view.findViewById(R.id.tv_belum);

        int type_zakat = getArguments().getInt("type_zakat", 0);
        String nominal = getArguments().getString("nominal");
        String nominalZakat;
        String jatuh_tempo = getArguments().getString("jatuh_tempo");
        if(type_zakat == Helper.KODE_ZAKAT_PROFESI){
            String rupiahNominal = nominal.replace(".", "");
            nominalZakat = rupiahFormat(Integer.parseInt(rupiahNominal));
        }else {
            nominalZakat = nominal+" gram";
        }
        tv_peringatan.setText("Apakah zakat anda yang sebesar "+nominalZakat+" sudah sampai haul ?");
        tv_ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                InputMasjidFragment inputMasjidFragment = new InputMasjidFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("type_zakat", type_zakat);
                bundle.putString("nominal", nominal);
                bundle.putString("jatuh_tempo", jatuh_tempo);
                inputMasjidFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_profesi, inputMasjidFragment)
                        .addToBackStack("")
                        .commit();
                dismiss();
            }
        });

        tv_belum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }
}
