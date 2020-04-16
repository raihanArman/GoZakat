package id.co.myproject.gozakat.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.gozakat.BuildConfig;
import id.co.myproject.gozakat.R;
import id.co.myproject.gozakat.model.Masjid;
import id.co.myproject.gozakat.view.zakat.InputMasjidFragment;

public class MasjidAdapter extends RecyclerView.Adapter<MasjidAdapter.ViewHolder> {
    List<Masjid> masjidList = new ArrayList<>();
    Context context;
    int row_index = -1;

    public MasjidAdapter(Context context) {
        this.context = context;
    }

    public void setMasjidList(List<Masjid> masjidList){
        this.masjidList.clear();
        this.masjidList.addAll(masjidList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MasjidAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_masjid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MasjidAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(BuildConfig.BASE_URL_GAMBAR+"masjid/"+masjidList.get(position).getGambar()).into(holder.iv_masjid);
        holder.tv_alamat.setText(masjidList.get(position).getAlamat());
        holder.tv_masjid.setText(masjidList.get(position).getNamaMasjid());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                notifyDataSetChanged();
                InputMasjidFragment.tv_masjid_pilihan.setText("Pilihan : "+masjidList.get(position).getNamaMasjid());
                InputMasjidFragment.tv_masjid_pilihan.setVisibility(View.VISIBLE);
                InputMasjidFragment.idMasjid = masjidList.get(position).getIdMasjid();
                InputMasjidFragment.namaMasjid = masjidList.get(position).getNamaMasjid();
                InputMasjidFragment.selectMasjid = true;
//                Toast.makeText(context, "ID masjid : "+InputMasjidFragment.idMasjid, Toast.LENGTH_SHORT).show();
            }
        });

        if(row_index == position){
            holder.rv_masjid_container.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
            holder.tv_masjid.setTextColor(Color.parseColor("#FFEB3B"));
            holder.tv_alamat.setTextColor(Color.parseColor("#ffffff"));
        }else {
            holder.rv_masjid_container.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.tv_masjid.setTextColor(Color.parseColor("#000000"));
            holder.tv_alamat.setTextColor(Color.parseColor("#000000"));
        }

    }

    @Override
    public int getItemCount() {
        return masjidList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_masjid;
        TextView tv_masjid, tv_alamat;
        RelativeLayout rv_masjid_container;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_masjid = itemView.findViewById(R.id.iv_masjid);
            tv_alamat = itemView.findViewById(R.id.tv_alamat);
            tv_masjid = itemView.findViewById(R.id.tv_masjid);
            rv_masjid_container = itemView.findViewById(R.id.rv_masjid_container);
        }
    }
}
