package com.jumbomob.invistoo.model.network;

import com.jumbomob.invistoo.model.entity.Asset;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * @author maiko.trindade
 * @since 04/02/2016
 */
public interface AssetInterface {

    @GET("/assets")
    Call<List<Asset>> getAssets();

}
