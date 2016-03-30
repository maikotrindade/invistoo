package com.jumbomob.invistoo.model.persistence.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.jumbomob.invistoo.model.entity.Question;
import com.jumbomob.invistoo.model.persistence.DatabaseContract.QuestionTable;
import com.jumbomob.invistoo.model.persistence.DatabaseContract.Tables;
import com.jumbomob.invistoo.util.DatabaseUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maiko.trindade
 * @since 29/03/2016
 */
public class QuestionDAO extends BaseDAO {

    private static QuestionDAO sDaoInstance;

    public static synchronized QuestionDAO getInstance() {
        if (sDaoInstance == null) {
            sDaoInstance = new QuestionDAO();
        }
        return sDaoInstance;
    }

    public long insert(final Question question) {
        long assetId = 0;
        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(QuestionTable.COLUMN_ID, question.getId());
            values.put(QuestionTable.COLUMN_QUESTION, question.getQuestion());
            values.put(QuestionTable.COLUMN_ANSWER, question.getAnswer());
            values.put(QuestionTable.COLUMN_GROUP, question.getGroup());
            assetId = database.insertOrThrow(Tables.QUESTION, null, values);
            
            database.setTransactionSuccessful();

        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            database.endTransaction();
        }
        return assetId;
    }

    public List<Question> findAll() {
        final String columns = DatabaseUtil.joinStrings(QuestionTable.projection);
        String query = "SELECT " + columns + " FROM " + Tables.QUESTION;

        List<Question> questions = new ArrayList<>();
        final Cursor cursor = database.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                final Question question = cursorToQuestion(cursor);
                questions.add(question);
                cursor.moveToNext();
            }
        }
        cursor.close();

        return questions;
    }

    private Question cursorToQuestion(Cursor cursor) {
        Question question = new Question();
        question.setId(cursor.getString(0));
        question.setQuestion(cursor.getString(1));
        question.setAnswer(cursor.getString(2));
        question.setGroup(cursor.getString(3));
        return question;
    }
}
