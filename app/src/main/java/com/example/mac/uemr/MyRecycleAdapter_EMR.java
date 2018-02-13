package com.example.mac.uemr;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mac on 17/3/12.
 */

public class MyRecycleAdapter_EMR extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;


    private List<BeanClassForRecyclerView_EMR> moviesList;


    private final LayoutInflater mLayoutInflater;

    //private static final int EMPTY_VIEW = 1;
    private static final int VIEW_PROG = 2;

    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;


    public MyRecycleAdapter_EMR(MainActivity mainActivityContacts, List<BeanClassForRecyclerView_EMR> moviesList, RecyclerView recyclerView) {
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
                .inflate(R.layout.emr_row, parent, false);

//        if(viewType==EMPTY_VIEW){
//            return new EmptyViewHolder(mLayoutInflater.inflate(R.layout.empty_view, parent, false));
        if(viewType==VIEW_PROG){
            return new ProgressViewHolder(mLayoutInflater.inflate(R.layout.progressbar_item, parent, false));
        }else{
            return new ItemHolder_EMR(itemView);
        }

    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder_EMR) {

            BeanClassForRecyclerView_EMR movie = moviesList.get(position);
            ItemHolder_EMR itemHolder = (ItemHolder_EMR) holder;

            itemHolder.historyID.setText(movie.getHistoryID());
            itemHolder.diseaseName.setText(movie.getDiseaseName());
            itemHolder.description.setText((movie.getDescription()));
            itemHolder.medicineQuantity.setText(movie.getMedicineQuantity()+"");
            itemHolder.medicineName.setText(movie.getMedicineName());
            itemHolder.price.setText(movie.getPrice());
            itemHolder.date.setText(movie.getDate());
            if (movie.getType()){
                itemHolder.back.setBackgroundResource(R.drawable.retangleblue);
            }
            else{
                itemHolder.back.setBackgroundResource(R.drawable.retangle);
            }


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

        return moviesList.get(position) != null ? super.getItemViewType(position) : VIEW_PROG;
    }



}



