package id.co.myproject.gozakat.view.zakat;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.fragment.app.FragmentManager;
import id.co.myproject.gozakat.R;
import id.co.myproject.gozakat.util.NumberTextWatcher;

import static id.co.myproject.gozakat.util.Helper.KODE_INFAQ;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputInfaqFragment extends Fragment {

    TextView tv_pilih_tanggal, tv_lanjut;
    EditText et_infaq;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter, dateFormatInfaq;
    String tanggalPilih;
    boolean selectTanggal = false;

    public InputInfaqFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_infaq, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ZakatActivity)getActivity()).getSupportActionBar().setTitle("Input Infaq");
        tv_pilih_tanggal = view.findViewById(R.id.tv_pilih_tanggal);
        tv_lanjut = view.findViewById(R.id.tv_lanjut);
        et_infaq = view.findViewById(R.id.et_infaq);

        et_infaq.addTextChangedListener(new NumberTextWatcher(et_infaq));
        String nominal = getArguments().getString("nominal");
//        et_infaq.setText(String.valueOf(nominal));
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateFormatInfaq = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

        tv_pilih_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        tv_lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectTanggal){
                    tanggalPilih = tanggalPilih+" 00:00";
                    InputMasjidFragment inputMasjidFragment = new InputMasjidFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("type_zakat", KODE_INFAQ);
                    String infaq=et_infaq.getText().toString();
                    bundle.putString("nominal", infaq.replace(".", ""));
                    bundle.putString("jatuh_tempo", tanggalPilih);
                    inputMasjidFragment.setArguments(bundle);
                    ((ZakatActivity)view.getContext()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_profesi, inputMasjidFragment)
                            .addToBackStack("")
                            .commit();
                }else {
                    Toast.makeText(getActivity(), "Pilih tanggal terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showDateDialog(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */

                tv_pilih_tanggal.setText("Tanggal terpilih : "+dateFormatInfaq.format(newDate.getTime()));
                tanggalPilih = dateFormatter.format(newDate.getTime());
                selectTanggal = true;
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }
}
