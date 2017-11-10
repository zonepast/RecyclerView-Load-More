package com.example.femmy.reloadmore;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public Context context;
    private static RecycleAdapter adapter;
    private LinearLayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private List<DataModel> dataModelList = new ArrayList<>();
    private List<NewDataModel> newDataModellist = new ArrayList<>();
    ApiInterface apiInterface;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataModelList= new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new RecycleAdapter(MainActivity.this,newDataModellist,recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        adapter.setOnLoadMoreListener(new RecycleAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.e("LoadMore", "onLoadMore called");
                dataModelList.add(null);
               // adapter.notifyDataSetChanged();
                adapter.notifyItemInserted(newDataModellist.size()-1);

                Log.e("LoadMore", "Loading new data... (" + 5 + ") posts");
                //remove progress item
                //dataModelList.remove(dataModelList.size() - 1);
                newDataModellist.remove(newDataModellist.size() - 1);
                adapter.notifyItemRemoved(newDataModellist.size());
                loadMore("8");
                adapter.setLoaded();
            }
        });
        apiInterface = ServiceGenerator.createService(ApiInterface.class);
        load("8");
    }



    private void load(String userId) {
        Log.d(TAG, "loadFirstPage: ");
        Call<List<DataModel>> call = apiInterface.getJSONData("8");
        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                if(response.isSuccessful()){
                    progressBar.setVisibility(View.VISIBLE);
                    int itemCount =0;
                    for (int i=itemCount;i<=itemCount+12;i++)
                    {
                        Log.e("Response",""+response.body().get(i).getCAreaName());
                        NewDataModel dataModel = new NewDataModel();
                        dataModel.setCAreaName(response.body().get(i).getCAreaName());
                        newDataModellist.add(dataModel);

                    }
                    adapter = new RecycleAdapter(MainActivity.this,newDataModellist,recyclerView);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());

//                        dataModelList.addAll(response.body());
//                        adapter.notifyDataSetChanged();

                }else{
                    Log.e(TAG," Response Error "+String.valueOf(response.code()));
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                Log.e(TAG," Response Error "+t.getMessage());
            }
        });
    }

    private void loadMore(String userId) {


        Log.d(TAG, "loadNextPage: ");
        //dataModelList.add(new DataModel());
      /*  adapter.setData(dataModelList);
        adapter.notifyItemInserted(dataModelList.size()-1);
*/
        Call<List<DataModel>> call = apiInterface.getJSONData("8");
        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                if(response.isSuccessful())
                {
//                    newDataModellist.remove(newDataModellist.size()-1);
                    int itemCount = newDataModellist.size();
                    for (int i=itemCount;i<=itemCount+12;i++)
                    {
                        Log.e("Response",""+response.body().get(i).getCAreaName());
                        NewDataModel dataModel = new NewDataModel();
                        dataModel.setCAreaName(response.body().get(i).getCAreaName());
                        newDataModellist.add(dataModel);
                    }
                    adapter.notifyDataSetChanged();
//                    adapter = new RecycleAdapter(MainActivity.this,newDataModellist,recyclerView);
                    //adapter.notifyDataSetChanged();
//                    recyclerView.setAdapter(adapter);
//                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                }

                else{
                    Log.e(TAG," Load More Response Error "+String.valueOf(response.code()));
                }

//                progressBar.setVisibility(View.GONE);
//                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                Log.e(TAG," Load More Response Error "+t.getMessage());
            }
        });
    }
}
