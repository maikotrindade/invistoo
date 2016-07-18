package com.jumbomob.invistoo.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author maiko.trindade
 * @since 28/03/2016
 */
public class Question extends RealmObject {

    @SerializedName("_id")
    @Expose
    @PrimaryKey
    private String id;

    @SerializedName("question")
    @Expose
    private String question;

    @SerializedName("answer")
    @Expose
    private String answer;

    @SerializedName("group")
    @Expose
    private String group;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
