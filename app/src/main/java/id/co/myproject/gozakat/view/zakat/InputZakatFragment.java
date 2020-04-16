package id.co.myproject.gozakat.view.zakat;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import id.co.myproject.gozakat.R;
import id.co.myproject.gozakat.util.KalkulatorListener;
import id.co.myproject.gozakat.util.NumberTextWatcher;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static id.co.myproject.gozakat.util.Helper.KODE_ZAKAT_MAL;
import static id.co.myproject.gozakat.util.Helper.KODE_ZAKAT_PROFESI;
import static id.co.myproject.gozakat.util.Helper.rupiahFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class InputZakatFragment extends Fragment implements KalkulatorListener {
    LinearLayout lv_kalkulator;
    TextView et_penghasilan;
    TextView tv_nisab;
    Spinner sp_bulan;
    TextView tv_lanjut;
    int bulanPilih, type_zakat;
    double totalZakat;

    public InputZakatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_zakat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ZakatActivity.prosesZakat = false;

        lv_kalkulator = view.findViewById(R.id.lv_kalkulator);
        et_penghasilan = view.findViewById(R.id.et_penghasilan);
        sp_bulan = view.findViewById(R.id.sp_bulan);
        tv_lanjut = view.findViewById(R.id.tv_lanjut);
        tv_nisab = view.findViewById(R.id.tv_nisab);
        et_penghasilan.setEnabled(false);

        List<String> bulanList = new ArrayList<>();
        bulanList.add("Januari");
        bulanList.add("Februari");
        bulanList.add("Maret");
        bulanList.add("April");
        bulanList.add("Mei");
        bulanList.add("Juni");
        bulanList.add("Juli");
        bulanList.add("Agustus");
        bulanList.add("September");
        bulanList.add("Oktober");
        bulanList.add("November");
        bulanList.add("Desember");

        List<String> tahunList = new ArrayList<>();
        tahunList.add("2020");
        tahunList.add("2021");
        tahunList.add("2022");
        tahunList.add("2023");

        type_zakat = getArguments().getInt("type_zakat");

        if (type_zakat == KODE_ZAKAT_PROFESI) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item_spinner, R.id.weekofday, bulanList);
            sp_bulan.setAdapter(arrayAdapter);
            tv_nisab.setText("Nisab : Rp. 4.974.320");
            et_penghasilan.setText(rupiahFormat(0));
            ((ZakatActivity)getActivity()).getSupportActionBar().setTitle("Input Zakat Profesi");
        }else if(type_zakat == KODE_ZAKAT_MAL){
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item_spinner, R.id.weekofday, tahunList);
            sp_bulan.setAdapter(arrayAdapter);
            tv_nisab.setText("Nisab Emas : 85 gram\nNisab Perak : 595 gram");
            et_penghasilan.setText(et_penghasilan.getText()+" gram");
            ((ZakatActivity)getActivity()).getSupportActionBar().setTitle("Input Zakat Mal");
        }

        lv_kalkulator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if (type_zakat == KODE_ZAKAT_PROFESI) {
                    KalkulatorProfesiFragmentDialog kalkulatorProfesiFragment = new KalkulatorProfesiFragmentDialog();
                    kalkulatorProfesiFragment.setTargetFragment(InputZakatFragment.this, 300);
                    kalkulatorProfesiFragment.show(fm, "");
                }else if(type_zakat == KODE_ZAKAT_MAL){
                    KalkulatorMalFragmentDialog kalkulatorMalFragmentDialog = new KalkulatorMalFragmentDialog();
                    kalkulatorMalFragmentDialog.setTargetFragment(InputZakatFragment.this, 200);
                    kalkulatorMalFragmentDialog.show(fm, "");
                }
            }
        });

        tv_lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nominal = et_penghasilan.getText().toString();
                if (type_zakat == KODE_ZAKAT_PROFESI){
                    if (!nominal.equals("Rp. 0")){
                        goInputMasjid();
                    }else {
                        Toast.makeText(getActivity(), "Isi nominal terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (!nominal.equals("0 gram")){
                        goInputMasjid();
                    }else {
                        Toast.makeText(getActivity(), "Isi nominal terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        sp_bulan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bulanPilih = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void goInputMasjid(){
        Calendar calendar = Calendar.getInstance();
        if (type_zakat == KODE_ZAKAT_PROFESI) {
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MONTH, bulanPilih);
        } else if (type_zakat == KODE_ZAKAT_MAL) {
            calendar.set(Calendar.YEAR, sp_bulan.getSelectedItemPosition());
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String tanggal = dateFormat.format(calendar.getTime());

        if (!TextUtils.isEmpty(et_penghasilan.getText())) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
//                        InputMasjidFragment inputMasjidFragment = new InputMasjidFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type_zakat", type_zakat);
            if (type_zakat == KODE_ZAKAT_PROFESI){
                bundle.putString("nominal", String.valueOf((int)totalZakat));
            }else {
                bundle.putString("nominal", String.valueOf(totalZakat));
            }
            bundle.putString("jatuh_tempo", tanggal);
//                        inputMasjidFragment.setArguments(bundle);
//                        ((ZakatActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.frame_profesi, inputMasjidFragment)
//                                .addToBackStack("")
//                                .commit();
            HaulDialogFragment haulDialogFragment = new HaulDialogFragment();
            haulDialogFragment.setArguments(bundle);
            haulDialogFragment.show(fm, "");
        } else {
            Toast.makeText(getActivity(), "Isi terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinishedKalkulator(double hasil) {
        totalZakat = hasil;
        if (type_zakat == KODE_ZAKAT_MAL){
            String total_hasil = String.format(String.valueOf(hasil)).replace(".", ",");
            et_penghasilan.setText(total_hasil+" gram");
        }else {
            et_penghasilan.setText(rupiahFormat((int)hasil));
        }
    }

    private String getBulanString(int bulanPilih){
        if (bulanPilih == 0){
            return "01";
        }else if (bulanPilih == 1){
            return "02";
        }else if (bulanPilih == 2){
            return "03";
        }else if (bulanPilih == 3){
            return "04";
        }else if (bulanPilih == 4){
            return "05";
        }else if (bulanPilih == 5){
            return "06";
        }else if (bulanPilih == 6){
            return "07";
        }else if (bulanPilih == 7){
            return "08";
        }else if (bulanPilih == 8){
            return "09";
        }else if (bulanPilih == 9){
            return "10";
        }else if (bulanPilih == 10){
            return "11";
        }else if (bulanPilih == 11){
            return "12";
        }else {
            return "01";
        }
    }

}
