package com.bione.network;


import androidx.annotation.NonNull;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Developer: Bione
 * <p>
 * Retrofit Response resolver
 */

/**
 * Custom Retrofit ResponseResolver
 *
 * @param <T> the response type
 */
public abstract class ResponseResolver<T> implements Callback<T> {

    /**
     * On Api response success
     *
     * @param t the response
     */
    public abstract void onSuccess(T t);

    /**
     * On Api error received
     *
     * @param error the error sent by the endpoint
     */
    public abstract void onError(ApiError error);

    /**
     * Indicates failure of the request
     *
     * @param throwable the throwable generated
     */
    public abstract void onFailure(Throwable throwable);

    @Override
    public void onResponse(@NonNull final Call<T> t, @NonNull final Response<T> tResponse) {

//        ProgressDialog.dismissProgressDialog();
        if (tResponse.isSuccessful()) {
//            Log.d("override onResponse 1111", " : " + tResponse.body().toString());
//            Log.d("override onResponse 2222", " : " + new Gson().toJson(tResponse.body()));
            onSuccess(tResponse.body());
//            onSuccess(tResponse.body());
        } else {
            onError(ErrorUtils.parseError(tResponse));
        }

    }

    @Override
    public void onFailure(@NonNull final Call<T> t, @NonNull final Throwable throwable) {
        onFailure(throwable);
//        ProgressDialog.dismissProgressDialog();
    }


}
