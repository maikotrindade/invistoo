package com.jumbomob.invistoo.model.network;

import com.jumbomob.invistoo.model.entity.Question;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * @author maiko.trindade
 * @since 28/03/2016
 */
public interface QuestionInterface {

    @GET("/questions")
    Call<List<Question>> getQuestions();

}
