package com.jumbomob.invistoo.model.persistence;

import com.jumbomob.invistoo.util.InvistooApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Implementation of Auto Increment feature for Realm
 * RealmAutoIncrement is a singleton which maintain the last id saved from each database model.
 * <p/>
 * To get next id from anyone model, simple call the method getNextIdFromModel.
 *
 * @see #getNextIdFromModel(Class)
 * @since 13/08/2016
 */
public final class RealmAutoIncrement {

    private Map<Class<? extends RealmObject>, AtomicLong> modelMap = new HashMap<>();
    private static RealmAutoIncrement autoIncrementMap;

    /**
     * Utility method which query for all models saved and get the bigger model id saved
     * Used to guarantee which the last model id saved is really the last
     *
     * @param clazz Model which should get the last id
     * @return The last id saved from model passed
     */
    private int getLastIdFromModel(Class<? extends RealmObject> clazz) {
        final Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        String primaryKeyColumnName = "id";
        Number lastId = realm.where(clazz).max(primaryKeyColumnName);
        return lastId == null ? 0 : lastId.intValue();
    }

    /**
     * Search in modelMap for the last saved id from model passed and return the next one
     *
     * @param clazz Model to search the last id
     * @return The next id which can be saved in database for that model,
     * {@code null} will be returned when this method is called by reflection
     */
    public Long getNextIdFromModel(Class<? extends RealmObject> clazz) {
        modelMap.put(clazz, new AtomicLong(getLastIdFromModel(clazz)));

        if (isValidMethodCall()) {
            AtomicLong modelId = modelMap.get(clazz);
            if (modelId == null) {
                return -1L;
            }
            return modelId.incrementAndGet();
        }
        return null;
    }

    /**
     * Utility method to validate if the method is called from reflection,
     * in this case is considered a not valid call otherwise is a valid call
     *
     * @return The boolean which define if the method call is valid or not
     */
    private boolean isValidMethodCall() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            if (stackTraceElement.getMethodName().equals("newInstance")) {
                return false;
            }
        }
        return true;
    }

    public static RealmAutoIncrement getInstance() {
        if (autoIncrementMap == null) {
            autoIncrementMap = new RealmAutoIncrement();
        }
        return autoIncrementMap;
    }
}