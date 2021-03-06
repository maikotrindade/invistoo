package com.jumbomob.invistoo.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author maiko.trindade
 * @since 28/03/2016
 */
public class Question extends RealmObject implements Parcelable {

    @SerializedName("_id")
    @Expose
    @PrimaryKey
    private Long id;

    @SerializedName("question")
    @Expose
    private String question;

    @SerializedName("answer")
    @Expose
    private String answer;

    @SerializedName("group")
    @Expose
    private String group;

    @SerializedName("datetime")
    @Expose
    private String datetime;

    @SerializedName("author")
    @Expose
    private String author;

    @SerializedName("referenceUrl")
    @Expose
    private String referenceUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReferenceUrl() {
        return referenceUrl;
    }

    public void setReferenceUrl(String referenceUrl) {
        this.referenceUrl = referenceUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.question);
        dest.writeString(this.answer);
        dest.writeString(this.group);
        dest.writeString(this.datetime);
        dest.writeString(this.author);
        dest.writeString(this.referenceUrl);
    }

    public Question() {
    }

    protected Question(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.question = in.readString();
        this.answer = in.readString();
        this.group = in.readString();
        this.datetime = in.readString();
        this.author = in.readString();
        this.referenceUrl = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
