package com.jumbomob.invistoo.model.persistence;

import com.jumbomob.invistoo.model.entity.Question;
import com.jumbomob.invistoo.util.InvistooApplication;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * @author maiko.trindade
 * @since 29/03/2016
 */
public class QuestionDAO {

    private static QuestionDAO sDaoInstance;

    public static synchronized QuestionDAO getInstance() {
        if (sDaoInstance == null) {
            sDaoInstance = new QuestionDAO();
        }
        return sDaoInstance;
    }

    public void insert(final Question question) {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(question);
        realm.commitTransaction();
    }

    public List<Question> findAll() {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        RealmQuery<Question> query = realm.where(Question.class);
        RealmResults<Question> questions = query.findAll();
        return questions;
    }

}
