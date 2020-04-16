package id.co.myproject.gozakat.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.gozakat.R;
import id.co.myproject.gozakat.model.ZakatHistory;
import id.co.myproject.gozakat.request.ApiRequest;

import static id.co.myproject.gozakat.util.Helper.rupiahFormat;

public class RiwayatPembayaranAdapter extends RecyclerView.Adapter<RiwayatPembayaranAdapter.ViewHolder> {
    List<ZakatHistory> zakatHistoryList = new ArrayList<>();
    Context context;
    public RiwayatPembayaranAdapter(Context context) {
        this.context = context;
    }

    public void setZakatHistoryList(List<ZakatHistory> zakatHistoryList){
        this.zakatHistoryList.clear();
        this.zakatHistoryList.addAll(zakatHistoryList);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RiwayatPembayaranAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pembayaran, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatPembayaranAdapter.ViewHolder holder, int position) {
        String tanggal = DateFormat.format("dd MMM yyyy", zakatHistoryList.get(position).getTanggalDistribusi()).toString();
        String jam = DateFormat.format("HH:mm", zakatHistoryList.get(position).getTanggalDistribusi()).toString();
        holder.tvJam.setText(jam);
        holder.tvTanggal.setText(tanggal);
        holder.tvNamaMasjid.setText(zakatHistoryList.get(position).getNamaMasjid());
        String nominal = zakatHistoryList.get(position).getNominal();
        if (zakatHistoryList.get(position).getJenisZakat().equals("Infaq")){
            nominal = rupiahFormat(Integer.parseInt(nominal));
        }else if (zakatHistoryList.get(position).getJenisZakat().equals("Mal")){
            nominal = nominal+" gram";
        }else if (zakatHistoryList.get(position).getJenisZakat().equals("Profesi")){
            nominal = rupiahFormat(Integer.parseInt(nominal));
        }
        holder.tvNominal.setText("Nominal : "+nominal);
    }

    @Override
    public int getItemCount() {
        return zakatHistoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTanggal, tvNamaMasjid, tvNominal, tvJam;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal);
            tvNamaMasjid = itemView.findViewById(R.id.tv_nama_masjid);
            tvNominal = itemView.findViewById(R.id.tv_nominal);
            tvJam = itemView.findViewById(R.id.tv_jam);
        }
    }
}
