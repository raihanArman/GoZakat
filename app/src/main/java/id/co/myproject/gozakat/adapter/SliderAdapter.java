package id.co.myproject.gozakat.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import id.co.myproject.gozakat.R;

public class SliderAdapter extends PagerAdapter {

    private List<Integer> bannerList;
    Context context;
    private LayoutInflater layoutInflater;

    public SliderAdapter(List<Integer> bannerList, Context context) {
        this.bannerList = bannerList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return bannerList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.slider_layout, container, false);
        ImageView banner = view.findViewById(R.id.banner_slider);
        Glide.with(context).load(bannerList.get(position)).into(banner);
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
