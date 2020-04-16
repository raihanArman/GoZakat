package id.co.myproject.gozakat.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.gozakat.R;
import id.co.myproject.gozakat.model.Masjid;
import id.co.myproject.gozakat.model.Zakat;
import id.co.myproject.gozakat.request.ApiRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.co.myproject.gozakat.util.Helper.rupiahFormat;

public class ZakatLainnyaAdapter extends RecyclerView.Adapter<ZakatLainnyaAdapter.ViewHolder> {

    List<Zakat> zakatList = new ArrayList<>();
    Context context;
    ApiRequest apiRequest;

    public ZakatLainnyaAdapter(Context context, ApiRequest apiRequest) {
        this.context = context;
        this.apiRequest = apiRequest;
    }

    public void setZakatList(List<Zakat> zakatList){
        this.zakatList.clear();
        this.zakatList.addAll(zakatList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ZakatLainnyaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aktivitas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ZakatLainnyaAdapter.ViewHolder holder, int position) {
        String timestamp = DateFormat.format("dd MMM yyyy", zakatList.get(position).getTimestamp()).toString();
        String jatuhTempo = DateFormat.format("dd MMM yyyy", zakatList.get(position).getJatuhTempo()).toString();
        holder.tv_timestamp.setText(timestamp);
        holder.tv_jenis_zakat.setText("Zakat "+zakatList.get(position).getJenisZakat());
        String nominal = zakatList.get(position).getNominal();
        if (zakatList.get(position).getJenisZakat().equals("Infaq")){
            holder.tv_jenis_zakat.setText(zakatList.get(position).getJenisZakat());
            nominal = rupiahFormat(Integer.parseInt(nominal));
        }else if (zakatList.get(position).getJenisZakat().equals("Mal")){
            holder.tv_jenis_zakat.setText("Zakat "+zakatList.get(position).getJenisZakat());
            nominal = nominal+" gram";
        }else if (zakatList.get(position).getJenisZakat().equals("Profesi")){
            holder.tv_jenis_zakat.setText("Zakat "+zakatList.get(position).getJenisZakat());
            nominal = rupiahFormat(Integer.parseInt(nominal));
        }
        holder.tv_nominal.setText("Nominal : "+nominal);
        Call<Masjid> getMasjidItem = apiRequest.getMasjidItemRequest(zakatList.get(position).getIdMasjid());
        getMasjidItem.enqueue(new Callback<Masjid>() {
            @Override
            public void onResponse(Call<Masjid> call, Response<Masjid> response) {
                if (response.isSuccessful()){
                    Masjid masjid = response.body();
                    holder.tv_masjid.setText("Penyalur : "+masjid.getNamaMasjid());
                    holder.tv_alamat_masjid.setText(masjid.getAlamat());
                }
            }

            @Override
            public void onFailure(Call<Masjid> call, Throwable t) {
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.tv_jatuh_tempo.setText("Jatuh tempo : "+jatuhTempo);
    }

    @Override
    public int getItemCount() {
        return zakatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_timestamp, tv_jenis_zakat, tv_nominal, tv_masjid, tv_alamat_masjid, tv_jatuh_tempo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_timestamp = itemView.findViewById(R.id.tv_timestamp);
            tv_jenis_zakat = itemView.findViewById(R.id.tv_jenis_zakat);
            tv_masjid = itemView.findViewById(R.id.tv_masjid);
            tv_nominal = itemView.findViewById(R.id.tv_nominal);
            tv_alamat_masjid = itemView.findViewById(R.id.tv_alamat_masjid);
            tv_jatuh_tempo = itemView.findViewById(R.id.tv_jatuh_tempo);
        }
    }
}
