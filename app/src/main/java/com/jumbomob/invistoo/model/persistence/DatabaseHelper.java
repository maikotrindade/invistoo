package com.jumbomob.invistoo.model.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vistajet.aea.model.persistence.DatabaseContract.*;
import com.vistajet.aea.util.AeaApplication;

/**
 * Helper class for managing {@link SQLiteDatabase} that stores e-crew's data.
 *
 * @author maiko.trindade
 * @since 22/12/2015
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static DatabaseHelper sInstance;
    private Context mContext;

    private static final String DATABASE_NAME = "ecrew.db";
    private static final int DATABASE_VERSION = 1;

    public static synchronized DatabaseHelper getInstance() {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(AeaApplication.getInstance());
        }
        return sInstance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    String CREATE_CREW_TB = "CREATE TABLE " + Tables.CREW + "("
            + CrewTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CrewTable.COLUMN_CODE + " TEXT NOT NULL COLLATE NOCASE,"
            + CrewTable.COLUMN_PERSON_NAME + " TEXT NOT NULL,"
            + CrewTable.COLUMN_DATE_OF_BIRTH + " TEXT NULL)";

    String CREATE_CREW_CONTACT_TB = "CREATE TABLE " + Tables.CREW_CONTACT + "("
            + CrewContactTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CrewContactTable.COLUMN_EMAIL + " TEXT NULL,"
            + CrewContactTable.COLUMN_PHONE + " TEXT NULL,"
            + CrewContactTable.COLUMN_FK_CREW + " INTEGER NOT NULL,"
            + " FOREIGN KEY(" + CrewContactTable.COLUMN_FK_CREW + ")" +
            " REFERENCES " + Tables.CREW + " (" + CrewTable.COLUMN_ID + "))";

    String CREATE_CREW_PASSPORT_TB = "CREATE TABLE " + Tables.CREW_PASSPORT + "("
            + CrewPassportTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CrewPassportTable.COLUMN_NUMBER + " TEXT NOT NULL,"
            + CrewPassportTable.COLUMN_ISSUE_DATE + " TEXT NOT NULL,"
            + CrewPassportTable.COLUMN_DISPLAY_NAME + " TEXT NOT NULL,"
            + CrewPassportTable.COLUMN_EXPIRY_DATE + " TEXT NOT NULL,"
            + CrewPassportTable.COLUMN_COUNTRY + " TEXT NOT NULL,"
            + CrewPassportTable.COLUMN_COUNTRY_OF_ISSUE + " TEXT NOT NULL,"
            + CrewPassportTable.COLUMN_IS_DEFAULT + " INTEGER NOT NULL,"
            + CrewPassportTable.COLUMN_IS_ON_HAND + " INTEGER NOT NULL,"
            + CrewPassportTable.COLUMN_IS_INVALID + " INTEGER NOT NULL,"
            + CrewPassportTable.COLUMN_FK_CREW + " INTEGER NOT NULL,"
            + " FOREIGN KEY(" + CrewPassportTable.COLUMN_FK_CREW + ")" +
            " REFERENCES " + Tables.CREW + " (" + CrewTable.COLUMN_ID + "))";

    String CREATE_VISA_TB = "CREATE TABLE " + Tables.VISA + "("
            + VisaTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + VisaTable.COLUMN_ISSUED_BY_COUNTRY + " TEXT NOT NULL,"
            + VisaTable.COLUMN_NUMBER + " TEXT NOT NULL,"
            + VisaTable.COLUMN_TYPE + " TEXT NOT NULL,"
            + VisaTable.COLUMN_VALID_FROM_DATE + " TEXT NOT NULL,"
            + VisaTable.COLUMN_VALID_TO_DATE + " TEXT NOT NULL,"
            + VisaTable.COLUMN_FK_PASSPORT + " INTEGER NOT NULL,"
            + " FOREIGN KEY(" + VisaTable.COLUMN_FK_PASSPORT + ")" +
            " REFERENCES " + Tables.CREW_PASSPORT + " (" + CrewPassportTable.COLUMN_ID + "))";

    String CREATE_AIRPORT_TB = "CREATE TABLE " + Tables.AIRPORT + "("
            + AirportTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + AirportTable.COLUMN_ICAO + " TEXT UNIQUE NOT NULL,"
            + AirportTable.COLUMN_NAME + " TEXT NOT NULL,"
            + AirportTable.COLUMN_COUNTRY + " TEXT NOT NULL,"
            + AirportTable.COLUMN_CITY + " TEXT NOT NULL)";

    String CREATE_AIRCRAFT_TB = "CREATE TABLE " + Tables.AIRCRAFT + "("
            + AircraftTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + AircraftTable.COLUMN_CODE + " TEXT NOT NULL,"
            + AircraftTable.COLUMN_TYPE_ID + " INTEGER NOT NULL,"
            + AircraftTable.COLUMN_TYPE_NAME + " TEXT NOT NULL,"
            + AircraftTable.COLUMN_FIRE_CATEGORY + " TEXT NOT NULL,"
            + AircraftTable.COLUMN_MTOM + " TEXT NULL,"
            + AircraftTable.COLUMN_NUMBER_OF_SEATS + " INTEGER NULL,"
            + AircraftTable.COLUMN_SUB_CHARTER + " INTEGER NULL,"
            + AircraftTable.COLUMN_TAIL_NUMBER + " TEXT NOT NULL)";

    String CREATE_FLIGHT_COSTUMER_TB = "CREATE TABLE " + Tables.FLIGHT_COSTUMER + "("
            + CostumerTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CostumerTable.COLUMN_NAME + " TEXT NOT NULL,"
            + CostumerTable.COLUMN_ACCOUNT_ID + " INTEGER NOT NULL,"
            + CostumerTable.COLUMN_ACCOUNT_NAME + " TEXT NOT NULL,"
            + CostumerTable.COLUMN_ACCOUNT_TYPE_ID + " INTEGER NOT NULL,"
            + CostumerTable.COLUMN_ACCOUNT_TYPE_NAME + " TEXT NOT NULL)";

    String CREATE_FLIGHT_LEG_TB = "CREATE TABLE " + Tables.FLIGHT_LEG_STATUS + "("
            + FlightLegTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FlightLegTable.COLUMN_NAME + " TEXT NULL,"
            + FlightLegTable.COLUMN_DESCRIPTION + " TEXT NULL)";

    String CREATE_FLIGHT_TB = "CREATE TABLE " + Tables.FLIGHT + "("
            + FlightTable.COLUMN_LOCAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FlightTable.COLUMN_ID + " INTEGER NOT NULL,"
            + FlightTable.COLUMN_NUMBER_OF_PASSENGERS + " INTEGER NOT NULL,"
            + FlightTable.COLUMN_CALL_SIGN + " TEXT NOT NULL,"
            + FlightTable.COLUMN_BOOKING_DATE + " TEXT NOT NULL,"
            + FlightTable.COLUMN_LAST_UPDATE + " TEXT NOT NULL,"
            + FlightTable.COLUMN_REMARKS + " TEXT NULL,"
            + FlightTable.COLUMN_IS_DEMO + " INTEGER NOT NULL,"
            + FlightTable.COLUMN_IS_SENSITIVE + " INTEGER NOT NULL,"
            + FlightTable.COLUMN_DATE + " TEXT NOT NULL,"
            + FlightTable.COLUMN_DEPARTURE_TIME + " TEXT NOT NULL,"
            + FlightTable.COLUMN_ARRIVAL_TIME + " TEXT NOT NULL,"
            + FlightTable.COLUMN_LOCAL_DEPARTURE_TIME + " TEXT NOT NULL,"
            + FlightTable.COLUMN_LOCAL_ARRIVAL_TIME + " TEXT NOT NULL,"
            + FlightTable.COLUMN_IS_CLOSED + " INTEGER NOT NULL,"
            + FlightTable.COLUMN_FK_AIRCRAFT + " INTEGER NOT NULL,"
            + FlightTable.COLUMN_FK_COSTUMER + " INTEGER NOT NULL,"
            + FlightTable.COLUMN_FK_DEPARTURE_AIRPORT + " INTEGER NOT NULL,"
            + FlightTable.COLUMN_FK_ARRIVAL_AIRPORT + " INTEGER NOT NULL,"
            + FlightTable.COLUMN_FK_STATUS + " INTEGER NULL,"
            + " FOREIGN KEY(" + FlightTable.COLUMN_FK_AIRCRAFT + ")" +
            " REFERENCES " + Tables.AIRCRAFT + " (" + AircraftTable.COLUMN_ID + "), "
            + " FOREIGN KEY(" + FlightTable.COLUMN_FK_COSTUMER + ")" +
            " REFERENCES " + Tables.FLIGHT_COSTUMER + " (" + CostumerTable.COLUMN_ID + "), "
            + " FOREIGN KEY(" + FlightTable.COLUMN_FK_DEPARTURE_AIRPORT + ")" +
            " REFERENCES " + Tables.AIRPORT + " (" + AirportTable.COLUMN_ID + "), "
            + " FOREIGN KEY(" + FlightTable.COLUMN_FK_ARRIVAL_AIRPORT + ")" +
            " REFERENCES " + Tables.AIRPORT + " (" + AirportTable.COLUMN_ID + "), "
            + " FOREIGN KEY(" + FlightTable.COLUMN_FK_STATUS + ")" +
            " REFERENCES " + Tables.FLIGHT_LEG_STATUS + " (" + FlightLegTable.COLUMN_ID + "))";

    String CREATE_FLIGHT_PERMIT_TB = "CREATE TABLE " + Tables.FLIGHT_PERMIT + "("
            + FlightPermitTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FlightPermitTable.COLUMN_CREATED_DATE + " INT NOT NULL,"
            + FlightPermitTable.COLUMN_JET_PLANNER_ROUTE + " INT NOT NULL,"
            + FlightPermitTable.COLUMN_NEEDS_REVIEW + " INT NOT NULL,"
            + FlightPermitTable.COLUMN_SLOT_TYPE + " INT NOT NULL,"
            + FlightPermitTable.COLUMN_SERVICE_PROVIDER_COMPANY + " INT NOT NULL,"
            + FlightPermitTable.COLUMN_SERVICE_TYPE_NAME + " INT NOT NULL,"
            + FlightPermitTable.COLUMN_FK_FLIGHT + " INT NOT NULL,"
            + FlightPermitTable.COLUMN_FK_AIRPORT + " INT NOT NULL,"
            + " FOREIGN KEY(" + FlightPermitTable.COLUMN_FK_FLIGHT + ")"
            + " REFERENCES " + Tables.FLIGHT + " (" + FlightTable.COLUMN_ID + ")"
            + " FOREIGN KEY(" + FlightPermitTable.COLUMN_FK_AIRPORT + ")"
            + " REFERENCES " + Tables.AIRCRAFT + " (" + AircraftTable.COLUMN_ID + "))";

    String CREATE_FLIGHT_HAS_CREW_TB = "CREATE TABLE " + Tables.FLIGHT_HAS_CREW + "("
            + FlightHasCrewTable.COLUMN_ID + " INTEGER PRIMARY KEY,"
            + FlightHasCrewTable.COLUMN_FUNCTION + " TEXT NULL,"
            + FlightHasCrewTable.COLUMN_FK_FLIGHT + " INTEGER NOT NULL,"
            + FlightHasCrewTable.COLUMN_FK_CREW + " INTEGER NOT NULL,"
            + " FOREIGN KEY(" + FlightHasCrewTable.COLUMN_FK_FLIGHT + ")" +
            " REFERENCES " + Tables.FLIGHT + " (" + FlightTable.COLUMN_LOCAL_ID + "), "
            + " FOREIGN KEY(" + FlightHasCrewTable.COLUMN_FK_CREW + ")" +
            " REFERENCES " + Tables.CREW + " (" + CrewTable.COLUMN_ID + "))";

    String CREATE_BAGGAGE_PREF_CATEGORY_TB = "CREATE TABLE " + Tables.BAGGAGE_PREF_CATEGORY + "("
            + BaggageCategoryTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + BaggageCategoryTable.COLUMN_NAME + " TEXT NOT NULL,"
            + BaggageCategoryTable.COLUMN_DESCRIPTION + " TEXT NULL)";

    String CREATE_BAGGAGE_PREFERENCES_TB = "CREATE TABLE " + Tables.BAGGAGE_PREFERENCES + "("
            + BaggageTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + BaggageTable.COLUMN_CREATED_DATE + " TEXT NOT NULL,"
            + BaggageTable.COLUMN_DESCRIPTION + " TEXT NULL,"
            + BaggageTable.COLUMN_PROFILE_ID + " INT NOT NULL,"
            + BaggageTable.COLUMN_CATEGORY + " INT NOT NULL)";

    String CREATE_CATERING_PREF_CATEGORY_TB = "CREATE TABLE " + Tables.CATERING_PREF_CATEGORY + "("
            + CateringCategoryTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CateringCategoryTable.COLUMN_NAME + " TEXT NOT NULL,"
            + CateringCategoryTable.COLUMN_DESCRIPTION + " TEXT NULL)";

    String CREATE_CATERING_PREFERENCES_TB = "CREATE TABLE " + Tables.CATERING_PREFERENCES + "("
            + CateringTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CateringTable.COLUMN_CREATED_DATE + " TEXT NOT NULL,"
            + CateringTable.COLUMN_DESCRIPTION + " TEXT NULL,"
            + CateringTable.COLUMN_PROFILE_ID + " INT NOT NULL,"
            + CateringTable.COLUMN_CATEGORY + " INT NOT NULL)";

    String CREATE_ENTERTAINMENT_PREF_CATEGORY_TB = "CREATE TABLE " + Tables.ENTERTAINMENT_PREF_CATEGORY + "("
            + EntertainmentCategoryTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + EntertainmentCategoryTable.COLUMN_NAME + " TEXT NOT NULL,"
            + EntertainmentCategoryTable.COLUMN_DESCRIPTION + " TEXT NULL)";

    String CREATE_ENTERTAINMENT_PREFERENCES_TB = "CREATE TABLE " + Tables.ENTERTAINMENT_PREFERENCES + "("
            + EntertainmentTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + EntertainmentTable.COLUMN_CREATED_DATE + " TEXT NOT NULL,"
            + EntertainmentTable.COLUMN_DESCRIPTION + " TEXT NULL,"
            + EntertainmentTable.COLUMN_PROFILE_ID + " INT NOT NULL,"
            + EntertainmentTable.COLUMN_CATEGORY + " INT NOT NULL)";

    String CREATE_FLIGHT_EXPERIENCE_PREF_CATEGORY_TB = "CREATE TABLE " + Tables.FLIGHT_EXPERIENCE_PREF_CATEGORY + "("
            + FlightExperienceCategoryTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FlightExperienceCategoryTable.COLUMN_NAME + " TEXT NOT NULL,"
            + FlightExperienceCategoryTable.COLUMN_DESCRIPTION + " TEXT NULL)";

    String CREATE_FLIGHT_EXPERIENCE_PREFERENCES_TB = "CREATE TABLE " + Tables.FLIGHT_EXPERIENCE_PREFERENCES + "("
            + FlightExperienceTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FlightExperienceTable.COLUMN_CREATED_DATE + " TEXT NOT NULL,"
            + FlightExperienceTable.COLUMN_DESCRIPTION + " TEXT NULL,"
            + FlightExperienceTable.COLUMN_PROFILE_ID + " INT NOT NULL,"
            + FlightExperienceTable.COLUMN_CATEGORY + " INT NOT NULL)";

    String CREATE_PLACE_TB = "CREATE TABLE " + Tables.PLACE + "("
            + PlaceTable.COLUMN_ID + " INTEGER PRIMARY KEY,"
            + PlaceTable.COLUMN_CITY + " TEXT NULL,"
            + PlaceTable.COLUMN_COUNTRY + " TEXT NULL)";

    String CREATE_PASSENGER_TB = "CREATE TABLE " + Tables.PASSENGER + "("
            + PassengerTable.COLUMN_LOCAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + PassengerTable.COLUMN_ID + " INT NULL,"
            + PassengerTable.COLUMN_FIRST_NAME + " TEXT NULL,"
            + PassengerTable.COLUMN_LAST_NAME + " TEXT NULL,"
            + PassengerTable.COLUMN_DATE_OF_BIRTH + " TEXT NULL,"
            + PassengerTable.COLUMN_PAX_STATUS + " INT DEFAULT 0,"
            + PassengerTable.COLUMN_LEAD_PAX + " INT NULL,"
            + PassengerTable.COLUMN_GENDER + " INT NULL,"
            + PassengerTable.COLUMN_REMOVED + " INT DEFAULT 0,"
            + PassengerTable.COLUMN_LEAD_BENEFICIARY + " INT NULL,"
            + PassengerTable.COLUMN_SALUTATION + " TEXT NULL,"
            + PassengerTable.COLUMN_PLAN_ID + " INT NULL,"
            + PassengerTable.COLUMN_LAST_UPDATE + " TEXT NOT NULL,"
            + PassengerTable.COLUMN_FK_BAGGAGE_PREF + " TEXT NULL,"
            + PassengerTable.COLUMN_FK_CATERING_PREF + " INTEGER NULL,"
            + PassengerTable.COLUMN_FK_ENTERTAINMENT_PREF + " INTEGER NULL,"
            + PassengerTable.COLUMN_FK_FLIGHT_EXP_PREF + " INTEGER NULL,"
            + PassengerTable.COLUMN_FK_PLACE_OF_BIRTH + " INTEGER NULL,"
            + " FOREIGN KEY(" + PassengerTable.COLUMN_FK_BAGGAGE_PREF + ")"
            + " REFERENCES " + Tables.BAGGAGE_PREFERENCES + " (" + BaggageTable.COLUMN_ID + "),"
            + " FOREIGN KEY(" + PassengerTable.COLUMN_FK_CATERING_PREF + ")"
            + " REFERENCES " + Tables.CATERING_PREFERENCES + " (" + CateringTable.COLUMN_ID + "),"
            + " FOREIGN KEY(" + PassengerTable.COLUMN_FK_ENTERTAINMENT_PREF + ")"
            + " REFERENCES " + Tables.ENTERTAINMENT_PREFERENCES + " (" + EntertainmentTable.COLUMN_ID + "),"
            + " FOREIGN KEY(" + PassengerTable.COLUMN_FK_FLIGHT_EXP_PREF + ")"
            + " REFERENCES " + Tables.FLIGHT_EXPERIENCE_PREFERENCES + " (" + FlightExperienceTable.COLUMN_ID + "),"
            + " FOREIGN KEY(" + PassengerTable.COLUMN_FK_PLACE_OF_BIRTH + ")" +
            " REFERENCES " + Tables.PLACE + " (" + PlaceTable.COLUMN_ID + "))";

    String CREATE_PASSENGER_PASSPORT_TB = "CREATE TABLE " + Tables.PASSENGER_PASSPORT + "("
            + PassengerPassportTable.COLUMN_LOCAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + PassengerPassportTable.COLUMN_ID + " INTEGER,"
            + PassengerPassportTable.COLUMN_NUMBER + " TEXT NOT NULL,"
            + PassengerPassportTable.COLUMN_ISSUE_DATE + " TEXT NULL,"
            + PassengerPassportTable.COLUMN_DISPLAY_NAME + " TEXT NULL,"
            + PassengerPassportTable.COLUMN_EXPIRY_DATE + " TEXT NULL,"
            + PassengerPassportTable.COLUMN_COUNTRY + " TEXT NULL,"
            + PassengerPassportTable.COLUMN_COUNTRY_OF_ISSUE + " TEXT NULL,"
            + PassengerPassportTable.COLUMN_DOCUMENT_TYPE + " TEXT NULL,"
            + PassengerPassportTable.COLUMN_FK_PASSENGER + " INT NOT NULL,"
            + " FOREIGN KEY(" + PassengerPassportTable.COLUMN_FK_PASSENGER + ")"
            + " REFERENCES " + Tables.PASSENGER + " (" + PassengerTable.COLUMN_ID + "))";

    String CREATE_PASSENGER_PROFILE_TB = "CREATE TABLE " + Tables.PASSENGER_PROFILE + "("
            + PassengerProfileTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + PassengerProfileTable.COLUMN_KEY + " TEXT NOT NULL,"
            + PassengerProfileTable.COLUMN_VALUE + " TEXT NOT NULL,"
            + PassengerProfileTable.COLUMN_FK_PASSENGER + " INT NOT NULL,"
            + " FOREIGN KEY(" + PassengerProfileTable.COLUMN_FK_PASSENGER + ")"
            + " REFERENCES " + Tables.PASSENGER + " (" + PassengerTable.COLUMN_ID + "))";

    String CREATE_PASSENGER_NATIONALITY_TB = "CREATE TABLE " + Tables.PASSENGER_NATIONALITY + "("
            + PassengerNationalityTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + PassengerNationalityTable.COLUMN_TITLE + " TEXT NOT NULL,"
            + PassengerNationalityTable.COLUMN_FK_PASSENGER + " INT NOT NULL,"
            + " FOREIGN KEY(" + PassengerNationalityTable.COLUMN_FK_PASSENGER + ")"
            + " REFERENCES " + Tables.PASSENGER + " (" + PassengerTable.COLUMN_ID + "))";

    String CREATE_PASSENGER_LANGUAGE_TB = "CREATE TABLE " + Tables.PASSENGER_LANGUAGE + "("
            + PassengerLanguageTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + PassengerLanguageTable.COLUMN_TITLE + " TEXT NULL,"
            + PassengerLanguageTable.COLUMN_IS_PREFERRED + " INT NULL,"
            + PassengerLanguageTable.COLUMN_FK_PASSENGER + " INT NOT NULL,"
            + " FOREIGN KEY(" + PassengerLanguageTable.COLUMN_FK_PASSENGER + ")"
            + " REFERENCES " + Tables.PASSENGER + " (" + PassengerTable.COLUMN_ID + "))";

    String CREATE_PASSENGER_PHOTO_TB = "CREATE TABLE " + Tables.PASSENGER_PHOTO + "("
            + PassengerPhotoTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + PassengerPhotoTable.COLUMN_DATE + " TEXT NULL,"
            + PassengerPhotoTable.COLUMN_FILE_KEY + " TEXT NULL,"
            + PassengerPhotoTable.COLUMN_LOCAL_FILE_PATH + " TEXT NULL,"
            + PassengerPhotoTable.COLUMN_FILE_SIZE + " INTEGER NULL,"
            + PassengerPhotoTable.COLUMN_ITEM_PROFILE_ID + " INTEGER NULL,"
            + PassengerPhotoTable.COLUMN_SOURCE + " TEXT NULL,"
            + PassengerPhotoTable.COLUMN_CREATED_DATE + " TEXT NULL,"
            + PassengerPhotoTable.COLUMN_FK_PASSENGER + " INT NOT NULL,"
            + " FOREIGN KEY(" + PassengerPhotoTable.COLUMN_FK_PASSENGER + ")"
            + " REFERENCES " + Tables.PASSENGER + " (" + PassengerTable.COLUMN_ID + "))";

    String CREATE_FLIGHT_HAS_PASSENGER_TB = "CREATE TABLE " + Tables.FLIGHT_HAS_PASSENGER + "("
            + FlightHasPassengerTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FlightHasPassengerTable.COLUMN_FK_FLIGHT + " INT NOT NULL,"
            + FlightHasPassengerTable.COLUMN_FK_PASSENGER + " INT NOT NULL,"
            + " FOREIGN KEY(" + FlightHasPassengerTable.COLUMN_FK_FLIGHT + ")"
            + " REFERENCES " + Tables.FLIGHT + " (" + FlightTable.COLUMN_ID + ")"
            + " FOREIGN KEY(" + FlightHasPassengerTable.COLUMN_FK_PASSENGER + ")"
            + " REFERENCES " + Tables.PASSENGER + " (" + PassengerTable.COLUMN_ID + "))";

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_CREW_TB);
        database.execSQL(CREATE_CREW_CONTACT_TB);
        database.execSQL(CREATE_CREW_PASSPORT_TB);
        database.execSQL(CREATE_VISA_TB);

        database.execSQL(CREATE_AIRPORT_TB);
        database.execSQL(CREATE_AIRCRAFT_TB);
        database.execSQL(CREATE_FLIGHT_COSTUMER_TB);
        database.execSQL(CREATE_FLIGHT_LEG_TB);
        database.execSQL(CREATE_FLIGHT_TB);
        database.execSQL(CREATE_FLIGHT_PERMIT_TB);
        database.execSQL(CREATE_FLIGHT_HAS_CREW_TB);

        database.execSQL(CREATE_BAGGAGE_PREF_CATEGORY_TB);
        database.execSQL(CREATE_BAGGAGE_PREFERENCES_TB);
        database.execSQL(CREATE_CATERING_PREF_CATEGORY_TB);
        database.execSQL(CREATE_CATERING_PREFERENCES_TB);
        database.execSQL(CREATE_ENTERTAINMENT_PREF_CATEGORY_TB);
        database.execSQL(CREATE_ENTERTAINMENT_PREFERENCES_TB);
        database.execSQL(CREATE_FLIGHT_EXPERIENCE_PREF_CATEGORY_TB);
        database.execSQL(CREATE_FLIGHT_EXPERIENCE_PREFERENCES_TB);
        database.execSQL(CREATE_PLACE_TB);
        database.execSQL(CREATE_PASSENGER_TB);
        database.execSQL(CREATE_PASSENGER_PASSPORT_TB);
        database.execSQL(CREATE_PASSENGER_PROFILE_TB);
        database.execSQL(CREATE_PASSENGER_NATIONALITY_TB);
        database.execSQL(CREATE_PASSENGER_LANGUAGE_TB);
        database.execSQL(CREATE_PASSENGER_PHOTO_TB);
        database.execSQL(CREATE_FLIGHT_HAS_PASSENGER_TB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }
}