package id.co.myproject.gozakat.view.zakat;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import id.co.myproject.gozakat.R;
import id.co.myproject.gozakat.model.Value;
import id.co.myproject.gozakat.request.ApiRequest;
import id.co.myproject.gozakat.request.RetrofitRequest;
import id.co.myproject.gozakat.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.co.myproject.gozakat.util.Helper.KODE_ZAKAT_MAL;
import static id.co.myproject.gozakat.util.Helper.KODE_ZAKAT_PROFESI;

/**
 * A simple {@link Fragment} subclass.
 */
public class PeringatanFragment extends DialogFragment {

    TextView tv_peringatan, tv_ya, tv_belum;
    ApiRequest apiRequest;
    SharedPreferences sharedPreferences;
    String nominal;

    public PeringatanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_peringatan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

        tv_peringatan = view.findViewById(R.id.tv_peringatan);
        tv_ya = view.findViewById(R.id.tv_ya);
        tv_belum = view.findViewById(R.id.tv_belum);

        String id_masjid = getArguments().getString("id_masjid");
        nominal = getArguments().getString("nominal");
        String nama_masjid = getArguments().getString("nama_masjid");
        String jatuh_tempo = getArguments().getString("jatuh_tempo");
        int idUser = sharedPreferences.getInt("id_user", 0);
        int type_zakat = getArguments().getInt("type_zakat");

        tv_peringatan.setText("Apakah anda yakin ingin membayar di "+nama_masjid+" ?");

        tv_belum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        tv_ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Proses ...");
                progressDialog.show();
                String jenisZakat = "";
                if (type_zakat == KODE_ZAKAT_PROFESI){
                    jenisZakat = "Profesi";
                }else if (type_zakat == KODE_ZAKAT_MAL){
                    jenisZakat = "Mal";
                }else {
                    jenisZakat = "Infaq";
                }
//                Toast.makeText(getActivity(), "Id User : "+idUser+", id masjid : "+id_masjid+", nominal : "+nominal+", jenis zakat : "+jenisZakat+", jatuh tempo : "+jatuh_tempo, Toast.LENGTH_SHORT).show();
                Call<Value> inputZakatRequest = apiRequest.inputZakatRequest(
                        idUser,
                        id_masjid,
                        nominal,
                        jenisZakat,
                        jatuh_tempo
                );
                inputZakatRequest.enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        if (response.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.body().getValue() == 1){
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("Pengiriman data berhasil, pembayaran akan dilakukan di masjid yang anda telah pilih");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                });
                                builder.show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
