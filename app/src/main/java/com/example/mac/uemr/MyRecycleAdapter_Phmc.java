package com.example.mac.uemr;

/**
 * Created by matthew on 17/3/1.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rp on 6/14/2016.
 */
public class MyRecycleAdapter_Phmc extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;

    private List<BeanClassForRecyclerView_Phmc> moviesList;

    private final LayoutInflater mLayoutInflater;

    private static final int VIEW_PROG = 2;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    public MyRecycleAdapter_Phmc(MainActivity mainActivityContacts, List<BeanClassForRecyclerView_Phmc> moviesList, RecyclerView recyclerView) {
        this.moviesList = moviesList;
        this.context = mainActivityContacts;
        this.mLayoutInflater = LayoutInflater.from(context);
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.phmc_row, parent, false);

//        if(viewType==EMPTY_VIEW){
//            return new EmptyViewHolder(mLayoutInflater.inflate(R.layout.empty_view, parent, false));
       if(viewType==VIEW_PROG){
            return new ProgressViewHolder(mLayoutInflater.inflate(R.layout.progressbar_item, parent, false));
        }else{
            return new ItemHolder_Phmc(itemView);
        }

    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder_Phmc) {

        BeanClassForRecyclerView_Phmc movie = moviesList.get(position);
            ItemHolder_Phmc itemHolder = (ItemHolder_Phmc) holder;
            itemHolder.medicineID.setText(movie.getMedicineID());
            itemHolder.medicineName.setText(movie.getMedcineName());
            itemHolder.medicinePrice.setText("¥"+(movie.getMedicinePrice())+"");
            itemHolder.inventory.setText(movie.getInventory()+"");
            itemHolder.image.setImageResource(movie.getImage());
            itemHolder.function.setText(movie.getFunction());

        }else if(holder instanceof ProgressViewHolder){
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {

            return moviesList.size() > 0 ? moviesList.size() : 1;
    }
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoading() {
        loading = true;
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemViewType(int position) {
//        if (moviesList.size() == 0) {
//            return EMPTY_VIEW;
//        }
        return moviesList.get(position) != null ? super.getItemViewType(position) : VIEW_PROG;
    }


}

