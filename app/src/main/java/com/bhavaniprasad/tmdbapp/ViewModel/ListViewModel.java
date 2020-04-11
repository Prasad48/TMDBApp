package com.bhavaniprasad.tmdbapp.ViewModel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bhavaniprasad.tmdbapp.Model.ListModel;
import com.bhavaniprasad.tmdbapp.remote.ApiMaker;
import com.bhavaniprasad.tmdbapp.remote.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListViewModel extends ViewModel {
    public MutableLiveData<Boolean> showProgressBar = new MutableLiveData<>();
    public MutableLiveData<Boolean> show_networkError = new MutableLiveData<>();


    Call<ListModel> ListCall;

    public MutableLiveData<ArrayList<ListModel>> arrayListMutableLiveData = new MutableLiveData<>();
    private ArrayList<ListModel> arrlist;

    public LiveData<Boolean> getShowProgressBar() {
        return showProgressBar;
    }


    public MutableLiveData<Boolean> getShow_networkError() {
        return show_networkError;
    }

    public MutableLiveData<ArrayList<ListModel>> getMutableLiveData(final Context context) {
        arrlist=new ArrayList<>();
        ApiService apiService = new ApiMaker().getService();
        ListCall= apiService.getList("popularity.desc","fd75d8c708d418f9ee6280f179e7f399");
        ListCall.enqueue(new Callback<ListModel>() {
            @Override
            public void onResponse(Call<ListModel> call, Response<ListModel> response) {
                if (response.isSuccessful()) {
                    arrlist=new ArrayList<>();
                    arrlist.add(response.body());
                    arrayListMutableLiveData.setValue(arrlist);
                    showProgressBar.setValue(false);
                } else {
                    Log.d("error message", "error");
                }
            }

            @Override
            public void onFailure(Call<ListModel> call, Throwable t) {
                String error_message= t.getMessage();
                Log.d("Error loading data", error_message);
                showProgressBar.setValue(false);
                show_networkError.setValue(true);
            }

        });
        return arrayListMutableLiveData;
    }


}
