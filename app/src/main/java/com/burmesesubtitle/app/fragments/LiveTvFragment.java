package com.burmesesubtitle.app.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.burmesesubtitle.app.Config;
import com.burmesesubtitle.app.MainActivity;
import com.burmesesubtitle.app.R;
import com.burmesesubtitle.app.adapters.LiveTvCategoryAdapter;
import com.burmesesubtitle.app.network.RetrofitClient;
import com.burmesesubtitle.app.network.apis.LiveTvApi;
import com.burmesesubtitle.app.network.model.LiveTvCategory;
import com.burmesesubtitle.app.utils.ApiResources;
import com.burmesesubtitle.app.utils.Constants;
import com.burmesesubtitle.app.utils.ads.BannerAds;
import com.burmesesubtitle.app.utils.NetworkInst;
import com.burmesesubtitle.app.utils.ToastMsg;
import com.burmesesubtitle.app.utils.ads.FanAds;
import com.burmesesubtitle.app.utils.ads.PopUpAds;
import com.burmesesubtitle.app.utils.ads.StartappAds;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class LiveTvFragment extends Fragment {


    private ShimmerFrameLayout shimmerFrameLayout;
    private RecyclerView recyclerView;
    private LiveTvCategoryAdapter adapter;
    private List<LiveTvCategory> liveTvCategories =new ArrayList<>();

    private ApiResources apiResources;

    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;


    private CoordinatorLayout coordinatorLayout;
    private TextView tvNoItem;

    private RelativeLayout adView;

    private MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.fragment_livetv,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.live_tv));

        initComponent(view);

    }


    private void initComponent(View view) {

        adView=view.findViewById(R.id.adView);
        apiResources=new ApiResources();
        shimmerFrameLayout=view.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmer();
        progressBar=view.findViewById(R.id.item_progress_bar);
        swipeRefreshLayout=view.findViewById(R.id.swipe_layout);
        coordinatorLayout=view.findViewById(R.id.coordinator_lyt);
        tvNoItem=view.findViewById(R.id.tv_noitem);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new LiveTvCategoryAdapter(activity, liveTvCategories);
        recyclerView.setAdapter(adapter);

        if (new NetworkInst(activity).isNetworkAvailable()){
            getLiveTvData();
        }else {
            tvNoItem.setText(getString(R.string.no_internet));
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            coordinatorLayout.setVisibility(View.VISIBLE);
        }


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                coordinatorLayout.setVisibility(View.GONE);
                liveTvCategories.clear();
                recyclerView.removeAllViews();
                adapter.notifyDataSetChanged();
                if (new NetworkInst(activity).isNetworkAvailable()){
                    getLiveTvData();
                }else {
                    tvNoItem.setText(getString(R.string.no_internet));
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    coordinatorLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        loadAd();

    }


    private void loadAd(){

        if (Constants.IS_ENABLE_AD.equals("1")){
            if (Constants.ACTIVE_AD_NETWORK.equals("admob")){
                BannerAds.ShowBannerAds(getContext(), adView);
                PopUpAds.ShowInterstitialAds(getContext());
            }else if (Constants.ACTIVE_AD_NETWORK.equals("fan")){
                FanAds.showBanner(getContext(), adView);
                FanAds.showInterstitialAd(getContext());
            }else if (Constants.ACTIVE_AD_NETWORK.equals("startapp")){
                StartappAds.showBannerAd(getContext(), adView);
            }
        }

    }


    private void getLiveTvData() {

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        LiveTvApi api = retrofit.create(LiveTvApi.class);
        api.getLiveTvCategories(Config.API_KEY).enqueue(new Callback<List<LiveTvCategory>>() {
            @Override
            public void onResponse(Call<List<LiveTvCategory>> call, retrofit2.Response<List<LiveTvCategory>> response) {
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                if (response.code() == 200) {
                    liveTvCategories.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    new ToastMsg(activity).toastIconError("Something went wrong...");
                }

            }

            @Override
            public void onFailure(Call<List<LiveTvCategory>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });

    }
}