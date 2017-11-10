package com.example.femmy.reloadmore;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Android System on 11/9/2017.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NewDataModel> dataModelList;
    private Context context;

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    // The minimum amount of items to have below your current scroll position before loading more.

    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount,visibleItemCount;
    private int previousTotal = 0;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    public RecycleAdapter(final Context context, List<NewDataModel> dataModelList, RecyclerView recyclerView) {
        this.context=context;
        this.dataModelList = dataModelList;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)<= (lastVisibleItem + visibleThreshold)) {
                    // End has been reached

                    Log.i("Yaeye!", "end called");

                    // Do something
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    loading = true;
                }
            }

        });
    }


    public class DataHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        public DataHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.txtmovie);

        }
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return dataModelList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder vh = null;
        if(viewType==VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_data, parent, false);

            vh = new DataHolder(v);
//        }else{
//            View v = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.row_load, parent, false);
//
//            vh = new ProgressViewHolder(v);
//        }
        }
     return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof DataHolder)
        {

            NewDataModel dataModel = dataModelList.get(position);
            holder.getAdapterPosition();
            ((DataHolder) holder).title.setText(dataModel.getCAreaName());

        }
//        else
//        {
//            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
//        }
    }

    public void setLoaded() {
        loading = true;
    }
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }
    interface OnLoadMoreListener{
        void onLoadMore();
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        }
    }
}
