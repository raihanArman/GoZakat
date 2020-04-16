package id.co.myproject.gozakat.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.gozakat.BuildConfig;
import id.co.myproject.gozakat.R;
import id.co.myproject.gozakat.model.Mustahiq;
import id.co.myproject.gozakat.model.ZakatHistory;
import id.co.myproject.gozakat.request.ApiRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.co.myproject.gozakat.util.Helper.rupiahFormat;

public class HistoryLainnyaAdapter extends RecyclerView.Adapter<HistoryLainnyaAdapter.ViewHolder> {
    List<ZakatHistory> zakatHistoryList = new ArrayList<>();
    Context context;
    ApiRequest apiRequest;

    public HistoryLainnyaAdapter(Context context, ApiRequest apiRequest) {
        this.context = context;
        this.apiRequest = apiRequest;
    }

    public void setZakatHistoryList(List<ZakatHistory> zakatHistoryList){
        this.zakatHistoryList.clear();
        this.zakatHistoryList.addAll(zakatHistoryList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryLainnyaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_histori, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryLainnyaAdapter.ViewHolder holder, int position) {
        String jatuhTempo = DateFormat.format("dd MMM yyyy", zakatHistoryList.get(position).getJatuhTempo()).toString();
        String tglDistribusi = DateFormat.format("dd MMM yyyy", zakatHistoryList.get(position).getTanggalDistribusi()).toString();
        holder.tv_jatuh_tempo.setText("Jatuh tempo : "+jatuhTempo);
        holder.tv_nama_mustahiq.setText(zakatHistoryList.get(position).getNamaMustahiq());
        Call<Mustahiq> getMustahiq = apiRequest.getMustahiqItemRequest(zakatHistoryList.get(position).getIdMustahiq());
        getMustahiq.enqueue(new Callback<Mustahiq>() {
            @Override
            public void onResponse(Call<Mustahiq> call, Response<Mustahiq> response) {
                if (response.isSuccessful()){
                    Mustahiq mustahiq = response.body();
                    String foto = mustahiq.getFoto();
                    if (mustahiq.getFoto().equals("")){
                        foto = BuildConfig.BASE_URL_GAMBAR+"mustahiq/mustahiq.png";
                    }else {
                        foto = BuildConfig.BASE_URL_GAMBAR+"mustahiq/"+foto;
                    }
//                    Toast.makeText(context, ""+mustahiq.getNamaMustahiq(), Toast.LENGTH_SHORT).show();
                    Glide.with(context).load(foto).into(holder.iv_mustahiq);
                    holder.tv_jenis_mustahiq.setText(mustahiq.getJenis_mustahiq());
                    holder.tv_alamat_mustahiq.setText(mustahiq.getAlamat());
                }
            }

            @Override
            public void onFailure(Call<Mustahiq> call, Throwable t) {
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        String nominal = zakatHistoryList.get(position).getNominal();
        if (zakatHistoryList.get(position).getJenisZakat().equals("Infaq")){
            nominal = rupiahFormat(Integer.parseInt(nominal));
        }else if (zakatHistoryList.get(position).getJenisZakat().equals("Mal")){
            nominal = nominal+" gram";
        }else if (zakatHistoryList.get(position).getJenisZakat().equals("Profesi")){
            nominal = rupiahFormat(Integer.parseInt(nominal));
        }
        holder.tv_nominal.setText("Nominal : "+nominal);
        holder.tv_nama_masjid.setText("Penyalur : "+zakatHistoryList.get(position).getNamaMasjid());
        holder.tv_tanggal_distribusi.setText("Tanggal penyaluran : "+tglDistribusi);
    }

    @Override
    public int getItemCount() {
        return zakatHistoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_jatuh_tempo, tv_nama_mustahiq, tv_jenis_mustahiq, tv_alamat_mustahiq, tv_nominal, tv_nama_masjid, tv_tanggal_distribusi;
        ImageView iv_mustahiq;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_mustahiq = itemView.findViewById(R.id.iv_mustahiq);
            tv_jatuh_tempo = itemView.findViewById(R.id.tv_jatuh_tempo);
            tv_nama_mustahiq = itemView.findViewById(R.id.tv_nama_mustahiq);
            tv_jenis_mustahiq = itemView.findViewById(R.id.tv_jenis_mustahiq);
            tv_alamat_mustahiq = itemView.findViewById(R.id.tv_alamat_mustahiq);
            tv_nominal = itemView.findViewById(R.id.tv_nominal);
            tv_nama_masjid = itemView.findViewById(R.id.tv_nama_masjid);
            tv_tanggal_distribusi = itemView.findViewById(R.id.tv_distribusi);
        }
    }
}
