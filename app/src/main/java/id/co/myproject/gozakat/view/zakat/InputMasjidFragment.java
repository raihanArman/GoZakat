package id.co.myproject.gozakat.view.zakat;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.gozakat.R;
import id.co.myproject.gozakat.adapter.MasjidAdapter;
import id.co.myproject.gozakat.model.Masjid;
import id.co.myproject.gozakat.request.ApiRequest;
import id.co.myproject.gozakat.request.RetrofitRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.co.myproject.gozakat.util.Helper.KODE_ZAKAT_MAL;
import static id.co.myproject.gozakat.util.Helper.KODE_ZAKAT_PROFESI;
import static id.co.myproject.gozakat.util.Helper.rupiahFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputMasjidFragment extends Fragment {

    String nominal, jatuhTempo;
    TextView tv_jenis_zakat;
    public static boolean selectMasjid = false;
    int type_zakat;
    EditText et_cari;
    TextView tv_nominal, tv_lanjut;
    RecyclerView rv_masjid;
    MasjidAdapter masjidAdapter;
    ApiRequest apiRequest;
    public static TextView tv_masjid_pilihan;
    public static String idMasjid, namaMasjid;

    public InputMasjidFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_masjid, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        ZakatActivity.prosesZakat = true;
        ((ZakatActivity)getActivity()).getSupportActionBar().setTitle("Input Masjid");
        nominal = getArguments().getString("nominal");
        jatuhTempo = getArguments().getString("jatuh_tempo");
        type_zakat = getArguments().getInt("type_zakat");

        et_cari = view.findViewById(R.id.et_cari);
        tv_jenis_zakat = view.findViewById(R.id.tv_jenis_zakat);
        tv_nominal = view.findViewById(R.id.tv_nominal);
        tv_lanjut = view.findViewById(R.id.tv_lanjut);
        rv_masjid = view.findViewById(R.id.rv_masjid);
        tv_masjid_pilihan = view.findViewById(R.id.tv_masjid_pilihan);

        String nominalzakat = "";
        if (type_zakat == KODE_ZAKAT_MAL){
            nominalzakat = nominal+" gram";
        }else if ((type_zakat == KODE_ZAKAT_PROFESI)){
            nominalzakat = rupiahFormat(Integer.parseInt(nominal));
        }else {
            tv_jenis_zakat.setText("Infaq Nominal");
            nominalzakat = rupiahFormat(Integer.parseInt(nominal));
        }

        tv_nominal.setText(nominalzakat);

        masjidAdapter = new MasjidAdapter(getActivity());
        rv_masjid.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_masjid.setAdapter(masjidAdapter);

        loadDataMasjid();

        tv_lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectMasjid) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    Bundle bundle = new Bundle();
                    bundle.putString("id_masjid", idMasjid);
                    bundle.putString("nama_masjid", namaMasjid);
                    bundle.putString("nominal", nominal);
                    bundle.putInt("type_zakat", type_zakat);
                    bundle.putString("jatuh_tempo", jatuhTempo);
                    PeringatanFragment peringatanFragment = new PeringatanFragment();
                    peringatanFragment.setArguments(bundle);
                    peringatanFragment.show(fm, "");
                }else {
                    Toast.makeText(getActivity(), "Pilih masjid terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        et_cari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                loadCariMasjid(editable.toString());
            }
        });

    }

    private void loadCariMasjid(String toString) {
        Call<List<Masjid>> getMasjidRequest=  apiRequest.getMasjidCariRequest(toString);
        getMasjidRequest.enqueue(new Callback<List<Masjid>>() {
            @Override
            public void onResponse(Call<List<Masjid>> call, Response<List<Masjid>> response) {
                if (response.isSuccessful()){
                    List<Masjid> masjidList = response.body();
                    masjidAdapter.setMasjidList(masjidList);
                }
            }

            @Override
            public void onFailure(Call<List<Masjid>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataMasjid() {
        Call<List<Masjid>> getMasjidRequest=  apiRequest.getMasjidRequest();
        getMasjidRequest.enqueue(new Callback<List<Masjid>>() {
            @Override
            public void onResponse(Call<List<Masjid>> call, Response<List<Masjid>> response) {
                if (response.isSuccessful()){
                    List<Masjid> masjidList = response.body();
                    masjidAdapter.setMasjidList(masjidList);
                }
            }

            @Override
            public void onFailure(Call<List<Masjid>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
