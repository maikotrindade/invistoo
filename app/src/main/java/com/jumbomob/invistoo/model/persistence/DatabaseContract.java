package com.jumbomob.invistoo.model.persistence;

/**
 * Contract class for e-crew database and its dependencies.
 *
 * @author maiko.trindade
 * @since 22/12/2015
 */
public class DatabaseContract {

    public interface Tables {
        String FLIGHT = "flight_tb";
        String VISA = "visa_tb";
        String CREW_PASSPORT = "crew_passport_tb";
        String CREW_CONTACT = "crew_contact_tb";
        String CREW = "crew_tb";
        String FLIGHT_HAS_CREW = "flight_has_crew_tb";
        String FLIGHT_LEG_STATUS = "flight_leg_status_tb";
        String PASSENGER = "passenger_tb";
        String FLIGHT_HAS_PASSENGER = "flight_has_passenger_tb";
        String PASSENGER_LANGUAGE = "passenger_language_tb";
        String PASSENGER_NATIONALITY = "passenger_nationality_tb";
        String PASSENGER_PROFILE = "passenger_profile_tb";
        String PASSENGER_PASSPORT = "passenger_passport_tb";
        String PASSENGER_PHOTO = "passenger_photo_tb";
        String BAGGAGE_PREFERENCES = "baggage_pref_tb";
        String BAGGAGE_PREF_CATEGORY = "baggage_category_tb";
        String CATERING_PREFERENCES = "catering_pref_tb";
        String CATERING_PREF_CATEGORY = "catering_category_tb";
        String ENTERTAINMENT_PREFERENCES = "entertainment_pref_tb";
        String ENTERTAINMENT_PREF_CATEGORY = "entertainment_category_tb";
        String FLIGHT_EXPERIENCE_PREFERENCES = "flight_experience_pref_tb";
        String FLIGHT_EXPERIENCE_PREF_CATEGORY = "flight_experience_category_tb";
        String AIRPORT = "airport_tb";
        String AIRCRAFT = "aircraft_tb";
        String FLIGHT_PERMIT = "flight_permit_tb";
        String FLIGHT_COSTUMER = "costumer_tb";
        String PLACE = "place_tb";
        String MOVIMENT_MESSAGE = "moviment_message_tb";
        String MESSAGE_TYPE = "message_type_tb";
        String MESSAGE_STATUS = "message_status_tb";
        String FEEDBACK = "feedback_tb";
        String FEEDBACK_FILE = "feedback_file_tb";
        String GLITCH_TYPE = "glitch_type_tb";
        String GLITCH_TYPE_CATEGORY = "glitch_type_category_tb";
        String GLITCH_REASON = "glitch_reason_tb";
        String GLITCH_REASON_CATEGORY = "glitch_reason_category_tb";
        String GLITCH_ORIGIN = "glitch_origin_tb";
        String GLITCH_ORIGIN_CATEGORY = "glitch_origin_category_tb";
        String GLITCH_STATUS = "glitch_status_tb";
        String GLITCH_STATUS_CATEGORY = "glitch_status_category_tb";
        String GLITCH_PRIORITY = "glitch_priority_tb";
        String GLITCH_PRIORITY_CATEGORY = "glitch_priority_category_tb";
        String CATERING_ORDER_REQUEST = "catering_order_request_tb";
        String CATERING_SUPPLIER = "catering_supplier_tb";
        String ORDER_REQUEST_CATEGORY = "order_request_category_tb";
        String ORDER_REQUEST = "order_request_tb";
        String ORDER_REQUEST_ITEM = "order_request_item_tb";
        String ORDER_REQUEST_DELIVERY = "delivery_tb";
        String ORDER_REQUEST_PAYMENT_DETAILS = "payment_details_tb";
    }

    public interface AirportTable {
        String COLUMN_ID = "airport_id";
        String COLUMN_ICAO = "airport_icao";
        String COLUMN_NAME = "airport_name";
        String COLUMN_COUNTRY = "airport_country";
        String COLUMN_CITY = "airport_city";

        String[] projection = {
                COLUMN_ID,
                COLUMN_ICAO,
                COLUMN_NAME,
                COLUMN_COUNTRY,
                COLUMN_CITY};
    }

    public interface AircraftTable {
        String COLUMN_ID = "aircraft_id";
        String COLUMN_CODE = "aircraft_type_code";
        String COLUMN_TYPE_ID = "aircraft_type_id";
        String COLUMN_TYPE_NAME = "aircraft_type_name";
        String COLUMN_FIRE_CATEGORY = "aircraft_fire_category";
        String COLUMN_MTOM = "aircraft_mtom";
        String COLUMN_NUMBER_OF_SEATS = "aircraft_number_of_seats";
        String COLUMN_SUB_CHARTER = "aircraft_sub_charter";
        String COLUMN_TAIL_NUMBER = "aircraft_tail_number";

        String[] projection = {
                COLUMN_ID,
                COLUMN_CODE,
                COLUMN_TYPE_ID,
                COLUMN_TYPE_NAME,
                COLUMN_FIRE_CATEGORY,
                COLUMN_MTOM,
                COLUMN_NUMBER_OF_SEATS,
                COLUMN_SUB_CHARTER,
                COLUMN_TAIL_NUMBER};
    }

    public interface CostumerTable {
        String COLUMN_ID = "costumer_id";
        String COLUMN_NAME = "costumer_name";
        String COLUMN_ACCOUNT_ID = "account_id";
        String COLUMN_ACCOUNT_NAME = "account_name";
        String COLUMN_ACCOUNT_TYPE_ID = "account_type_id";
        String COLUMN_ACCOUNT_TYPE_NAME = "account_type_name";
    }

    public interface FlightLegTable {
        String COLUMN_ID = "flight_leg_status_id";
        String COLUMN_NAME = "flight_leg_status_name";
        String COLUMN_DESCRIPTION = "light_leg_status_description";
    }

    public interface FlightPermitTable {
        String COLUMN_ID = "flight_permit_id";
        String COLUMN_CREATED_DATE = "flight_permit_created_date";
        String COLUMN_JET_PLANNER_ROUTE = "flight_permit_jet_planner_route";
        String COLUMN_NEEDS_REVIEW = "flight_permit_needs_review";
        String COLUMN_SLOT_TYPE = "flight_permit_slot_type";
        String COLUMN_SERVICE_PROVIDER_COMPANY = "flight_permit_service_provider_company_name";
        String COLUMN_SERVICE_TYPE_NAME = "flight_permit_service_type_name";
        String COLUMN_FK_FLIGHT = "flight_tb_flight_id";
        String COLUMN_FK_AIRPORT = "airport_tb_airport_id";
    }

    public interface FlightTable {
        //SQLite id
        String COLUMN_LOCAL_ID = "flight_local_id";
        //Flight leg id
        String COLUMN_ID = "flight_id";
        String COLUMN_NUMBER_OF_PASSENGERS = "flight_number_of_passengers";
        String COLUMN_CALL_SIGN = "flight_call_sign";
        String COLUMN_BOOKING_DATE = "flight_booking_date";
        String COLUMN_LAST_UPDATE = "flight_last_update";
        String COLUMN_REMARKS = "flight_remarks";
        String COLUMN_IS_DEMO = "flight_is_demo";
        String COLUMN_IS_SENSITIVE = "flight_is_sensitive";
        String COLUMN_DATE = "flight_date";
        String COLUMN_DEPARTURE_TIME = "flight_departure_time";
        String COLUMN_ARRIVAL_TIME = "flight_arrival_time";
        String COLUMN_LOCAL_DEPARTURE_TIME = "flight_local_departure_time";
        String COLUMN_LOCAL_ARRIVAL_TIME = "flight_local_arrival_time";
        String COLUMN_IS_CLOSED = "flight_local_is_closed";
        String COLUMN_FK_AIRCRAFT = "aircraft_tb_aircraft_id";
        String COLUMN_FK_COSTUMER = "costumer_tb_costumer_id";
        String COLUMN_FK_DEPARTURE_AIRPORT = "departure_airport_tb_airport_id";
        String COLUMN_FK_ARRIVAL_AIRPORT = "arrival_airport_tb_airport_id";
        String COLUMN_FK_STATUS = "flight_leg_status_tb_flight_leg_status_id";

        String[] projection = {COLUMN_ID,
                COLUMN_NUMBER_OF_PASSENGERS,
                COLUMN_CALL_SIGN,
                COLUMN_BOOKING_DATE,
                COLUMN_LAST_UPDATE,
                COLUMN_REMARKS,
                COLUMN_IS_DEMO,
                COLUMN_IS_SENSITIVE,
                COLUMN_DATE,
                COLUMN_DEPARTURE_TIME,
                COLUMN_ARRIVAL_TIME,
                COLUMN_LOCAL_DEPARTURE_TIME,
                COLUMN_LOCAL_ARRIVAL_TIME,
                COLUMN_IS_CLOSED};
    }

    public interface FlightHasCrewTable {
        String COLUMN_ID = "flight_has_crew_id";
        String COLUMN_FUNCTION = "crew_function";
        String COLUMN_FK_FLIGHT = "flight_tb_flight_id";
        String COLUMN_FK_CREW = "crew_db_crew_tb_id";
    }

    public interface CrewTable {
        String COLUMN_ID = "crew_id";
        String COLUMN_CODE = "crew_code";
        String COLUMN_PERSON_NAME = "crew_person_name";
        String COLUMN_DATE_OF_BIRTH = "crew_date_of_birth";

        String[] projection = {CrewTable.COLUMN_ID,
                COLUMN_CODE,
                COLUMN_PERSON_NAME,
                COLUMN_DATE_OF_BIRTH};
    }

    public interface CrewPassportTable {
        String COLUMN_ID = "passport_id";
        String COLUMN_NUMBER = "passport_number";
        String COLUMN_ISSUE_DATE = "passport_issue_date";
        String COLUMN_DISPLAY_NAME = "passport_display_name";
        String COLUMN_EXPIRY_DATE = "passport_expiry_date";
        String COLUMN_COUNTRY = "passport_country";
        String COLUMN_COUNTRY_OF_ISSUE = "passport_country_of_issue";
        String COLUMN_IS_DEFAULT = "passport_is_default";
        String COLUMN_IS_ON_HAND = "passport_is_on_hand";
        String COLUMN_IS_INVALID = "passport_is_invalid";
        String COLUMN_FK_CREW = "crew_db_crew_tb_id";
    }

    public interface VisaTable {
        String COLUMN_ID = "visa_id";
        String COLUMN_ISSUED_BY_COUNTRY = "visa_issued_by_country";
        String COLUMN_NUMBER = "visa_number";
        String COLUMN_TYPE = "visa_type";
        String COLUMN_VALID_FROM_DATE = "visa_valid_from_date";
        String COLUMN_VALID_TO_DATE = "visa_valid_to_date";
        String COLUMN_FK_PASSPORT = "passport_tb_passport_id";
    }

    public interface CrewContactTable {
        String COLUMN_ID = "contact_id";
        String COLUMN_EMAIL = "contact_email";
        String COLUMN_PHONE = "contact_phone";
        String COLUMN_FK_CREW = "crew_db_crew_tb_id";

        String[] projection = {
                COLUMN_ID,
                COLUMN_EMAIL,
                COLUMN_PHONE,
                COLUMN_FK_CREW
        };
    }

    public interface PassengerTable {
        //SQLite id
        String COLUMN_LOCAL_ID = "passenger_local_id";
        String COLUMN_ID = "passenger_id";
        String COLUMN_FIRST_NAME = "passenger_first_name";
        String COLUMN_LAST_NAME = "passenger_last_name";
        String COLUMN_DATE_OF_BIRTH = "passenger_date_of_birth";
        String COLUMN_PAX_STATUS = "passenger_pax_boarding_status";
        String COLUMN_LEAD_PAX = "passenger_lead_pax";
        String COLUMN_GENDER = "passenger_gender";
        String COLUMN_LEAD_BENEFICIARY = "passenger_lead_beneficiary";
        String COLUMN_SALUTATION = "passenger_salutation";
        String COLUMN_PLAN_ID = "passenger_plan_id";
        String COLUMN_LAST_UPDATE = "passenger_last_update";
        String COLUMN_FK_PLACE_OF_BIRTH = "passenger_place_of_birth_place_id";
        String COLUMN_FK_BAGGAGE_PREF = "passenger_baggage_preference_tb_baggage_pref_id";
        String COLUMN_FK_CATERING_PREF = "catering_pref_tb_catering_pref_id";
        String COLUMN_FK_ENTERTAINMENT_PREF = "entertainment_pref_tb_entertainment_pref_id";
        String COLUMN_FK_FLIGHT_EXP_PREF = "flight_experience_pref_tb_flight_exp_pref_id";
        String COLUMN_REMOVED = "passenger_removed";

        String[] projection = {COLUMN_LOCAL_ID,
                COLUMN_ID,
                COLUMN_FIRST_NAME,
                COLUMN_LAST_NAME,
                COLUMN_DATE_OF_BIRTH,
                COLUMN_PAX_STATUS,
                COLUMN_LEAD_PAX,
                COLUMN_GENDER,
                COLUMN_LEAD_BENEFICIARY,
                COLUMN_SALUTATION,
                COLUMN_PLAN_ID,
                COLUMN_LAST_UPDATE,
                COLUMN_REMOVED};
    }

    public interface FlightHasPassengerTable {
        String COLUMN_ID = "flight_has_passenger_id";
        String COLUMN_FK_FLIGHT = "flight_tb_flight_id";
        String COLUMN_FK_PASSENGER = "passenger_tb_passenger_id";
    }

    public interface PassengerProfileTable {
        String COLUMN_ID = "profile_id";
        String COLUMN_KEY = "profile_key";
        String COLUMN_VALUE = "profile_value";
        String COLUMN_FK_PASSENGER = "passenger_tb_passenger_id";
    }

    public interface PassengerNationalityTable {
        String COLUMN_ID = "nationality_id";
        String COLUMN_TITLE = "nationality_title";
        String COLUMN_FK_PASSENGER = "passenger_tb_passenger_id";
    }

    public interface PassengerLanguageTable {
        String COLUMN_ID = "language_id";
        String COLUMN_TITLE = "language_title";
        String COLUMN_IS_PREFERRED = "language_is_preferred";
        String COLUMN_FK_PASSENGER = "passenger_tb_passenger_id";
    }

    public interface PassengerPhotoTable {
        String COLUMN_ID = "photo_id";
        String COLUMN_DATE = "photo_date";
        String COLUMN_FILE_KEY = "photo_file_key";
        String COLUMN_LOCAL_FILE_PATH = "photo_local_file_path";
        String COLUMN_FILE_SIZE = "photo_file_size";
        String COLUMN_ITEM_PROFILE_ID = "photo_item_profile_id";
        String COLUMN_SOURCE = "photo_source";
        String COLUMN_CREATED_DATE = "photo_creation_date";
        String COLUMN_FK_PASSENGER = "passenger_tb_passenger_id";
        String[] projection = {
                COLUMN_ID,
                COLUMN_DATE,
                COLUMN_FILE_KEY,
                COLUMN_LOCAL_FILE_PATH,
                COLUMN_FILE_SIZE,
                COLUMN_ITEM_PROFILE_ID,
                COLUMN_SOURCE,
                COLUMN_CREATED_DATE,
                COLUMN_FK_PASSENGER
        };
    }

    public interface PassengerPassportTable {
        String COLUMN_LOCAL_ID = "passport_local_id";
        String COLUMN_ID = "passport_id";
        String COLUMN_NUMBER = "passport_number";
        String COLUMN_ISSUE_DATE = "passport_issue_date";
        String COLUMN_DISPLAY_NAME = "passport_display_name";
        String COLUMN_EXPIRY_DATE = "passport_expiry_date";
        String COLUMN_COUNTRY = "passport_country";
        String COLUMN_COUNTRY_OF_ISSUE = "passport_country_of_issue";
        String COLUMN_DOCUMENT_TYPE = "passport_document_type";
        String COLUMN_FK_PASSENGER = "passenger_tb_passenger_id";

        String[] projection = {
                COLUMN_LOCAL_ID,
                COLUMN_ID,
                COLUMN_NUMBER,
                COLUMN_ISSUE_DATE,
                COLUMN_DISPLAY_NAME,
                COLUMN_EXPIRY_DATE,
                COLUMN_COUNTRY,
                COLUMN_COUNTRY_OF_ISSUE,
                COLUMN_DOCUMENT_TYPE
        };
    }

    public interface BaggageCategoryTable {
        String COLUMN_ID = " baggage_cat_id";
        String COLUMN_NAME = " baggage_cat_name";
        String COLUMN_DESCRIPTION = " baggage_cat_description";
    }

    public interface BaggageTable {
        String COLUMN_ID = " baggage_pref_id";
        String COLUMN_CREATED_DATE = " baggage_pref_created_date";
        String COLUMN_DESCRIPTION = " baggage_pref_description";
        String COLUMN_PROFILE_ID = " baggage_pref_person_profile_id";
        String COLUMN_CATEGORY = " baggage_cat_id";
    }

    public interface CateringCategoryTable {
        String COLUMN_ID = " catering_cat_id";
        String COLUMN_NAME = " catering_cat_name";
        String COLUMN_DESCRIPTION = " catering_cat_description";
    }

    public interface CateringTable {
        String COLUMN_ID = " catering_pref_id";
        String COLUMN_CREATED_DATE = " catering_pref_created_date";
        String COLUMN_DESCRIPTION = " catering_pref_description";
        String COLUMN_PROFILE_ID = " catering_pref_person_profile_id";
        String COLUMN_CATEGORY = " catering_cat_id";
    }

    public interface EntertainmentCategoryTable {
        String COLUMN_ID = " entertainment_cat_id";
        String COLUMN_NAME = " entertainment_cat_name";
        String COLUMN_DESCRIPTION = " entertainment_cat_description";
    }

    public interface EntertainmentTable {
        String COLUMN_ID = " entertainment_pref_id";
        String COLUMN_CREATED_DATE = " entertainment_pref_created_date";
        String COLUMN_DESCRIPTION = " entertainment_pref_description";
        String COLUMN_PROFILE_ID = " entertainment_pref_person_profile_id";
        String COLUMN_CATEGORY = " entertainment_cat_id";
    }

    public interface FlightExperienceCategoryTable {
        String COLUMN_ID = " flight_experience_cat_id";
        String COLUMN_NAME = " flight_experience_cat_name";
        String COLUMN_DESCRIPTION = " flight_experience_cat_description";
    }

    public interface FlightExperienceTable {
        String COLUMN_ID = " flight_experience_pref_id";
        String COLUMN_CREATED_DATE = " flight_experience_pref_created_date";
        String COLUMN_DESCRIPTION = " flight_experience_pref_description";
        String COLUMN_PROFILE_ID = " flight_experience_pref_person_profile_id";
        String COLUMN_CATEGORY = " flight_experience_cat_id";
    }

    public interface PlaceTable {
        String COLUMN_ID = "place_id";
        String COLUMN_CITY = "place_city";
        String COLUMN_COUNTRY = "place_country";
    }

    public interface FeedbackTable {
        String COLUMN_ID = "feedback_id";
        String COLUMN_USERNAME = "feedback_reporter_username";
        String COLUMN_CREATED_DATE = "feedback_created_date";
        String COLUMN_TITLE = "feedback_title";
        String COLUMN_TYPE_ID = "feedback_type_id";
        String COLUMN_IS_GLITCH = "feedback_is_glitch";
        String COLUMN_FK_GLITCH_ID = "glitch_type_glitch_tb_id";
        String COLUMN_FK_FLIGHT = "flight_tb_flight_id";
    }

    public interface FeedbackFileTable {
        String COLUMN_ID = "feedback_file_id";
        String COLUMN_NAME = "feedback_file_name";
        String COLUMN_LOCATION = "feedback_file_location";
        String COLUMN_LOCAL_FILE_PATH = "feedback_local_file_url";
        String COLUMN_FILE_URL = "feedback_file_url";
        String COLUMN_IS_ACTIVE = "feedback_file_is_active";
        String COLUMN_CREATED_DATE = "feedback_created_date";
        String COLUMN_FK_FEEDBACK = "feedback_feedback_tb_id";
    }

    public interface GlitchPriorityCategoryTable {
        String COLUMN_CODE = "glitch_priority_code";
        String COLUMN_VALUE = "glitch_priority_value";
    }

    public interface GlitchPriorityTable {
        String COLUMN_ID = "glitch_priority_id";
        String COLUMN_CODE = "glitch_priority_code";
        String COLUMN_DESCRIPTION = "glitch_priority_description";
        String COLUMN_NAME = "glitch_priority_name";
        String COLUMN_FK_CATEGORY = "glitch_priority_tb_glitch_priority_code";
    }

    public interface GlitchStatusCategoryTable {
        String COLUMN_CODE = "glitch_status_code";
        String COLUMN_VALUE = "glitch_status_value";
    }

    public interface GlitchStatusTable {
        String COLUMN_ID = "glitch_status_id";
        String COLUMN_CODE = "glitch_status_code";
        String COLUMN_DESCRIPTION = "glitch_status_description";
        String COLUMN_NAME = "glitch_status_name";
        String COLUMN_FK_CATEGORY = "glitch_status_tb_glitch_status_code";
    }

    public interface GlitchOriginCategoryTable {
        String COLUMN_CODE = "glitch_origin_code";
        String COLUMN_VALUE = "glitch_origin_value";
    }

    public interface GlitchOriginTable {
        String COLUMN_ID = "glitch_origin_id";
        String COLUMN_CODE = "glitch_origin_code";
        String COLUMN_DESCRIPTION = "glitch_origin_description";
        String COLUMN_NAME = "glitch_origin_name";
        String COLUMN_FK_CATEGORY = "glitch_origin_tb_glitch_origin_code";
    }

    public interface GlitchReasonCategoryTable {
        String COLUMN_CODE = "glitch_reason_code";
        String COLUMN_VALUE = "glitch_reason_value";
    }

    public interface GlitchReasonTable {
        String COLUMN_ID = "glitch_reason_id";
        String COLUMN_CODE = "glitch_reason_code";
        String COLUMN_DESCRIPTION = "glitch_reason_description";
        String COLUMN_NAME = "glitch_reason_name";
        String COLUMN_FK_CATEGORY = "glitch_reason_tb_glitch_reason_code";
    }

    public interface GlitchTypeCategoryTable {
        String COLUMN_CODE = "glitch_type_code";
        String COLUMN_VALUE = "glitch_type_value";
    }

    public interface GlitchTypeTable {
        String COLUMN_ID = "glitch_type_id";
        String COLUMN_CODE = "glitch_type_code";
        String COLUMN_DESCRIPTION = "glitch_type_description";
        String COLUMN_NAME = "glitch_type_name";
        String COLUMN_FK_CATEGORY = "glitch_type_tb_glitch_type_code";
    }

    public interface MovimentMessageTable {
        String COLUMN_ID = "mvnt_msg_id";
        String COLUMN_AIRCRAFT_ID = "mvnt_msg_aircraft_id";
        String COLUMN_TIME = "mvnt_msg_mvt_time";
        String COLUMN_NOTES = "mvnt_msg_notes";
        String COLUMN_SENDER_ID = "mvnt_msg_sender_id";
        String COLUMN_SENDER_CODE = "mvnt_msg_sender_code";
        String COLUMN_TIMESTAMP = "mvnt_msg_timestamp";
        String COLUMN_FK_FLIGHT = "flight_tb_flight_id";
    }

    public interface MovimentMessageTypeTable {
        String COLUMN_ID = "message_type_id";
        String COLUMN_TITLE = "message_type_title";
        String COLUMN_FK_MOVIMENT_MESSAGE = "moviment_message_tb_mvnt_msg_id";
    }

    public interface MovimentMessageStatusTable {
        String COLUMN_ID = "message_status_id";
        String COLUMN_TITLE = "message_status_title";
        String COLUMN_FK_MOVIMENT_MESSAGE = "moviment_message_tb_mvnt_msg_id";
    }

    public interface CateringOrderRequestTable {
        String COLUMN_ID = "order_request_id";
        String COLUMN_INFORMATION = "important_information";
        String COLUMN_NUMBER = "order_number";
        String COLUMN_PLACED_BY = "order_placed_by";
        String COLUMN_STATUS = "order_status";
        String COLUMN_PAX = "pax";
        String COLUMN_PURCHASE_ORDER_ID = "purchase_order_id";
        String COLUMN_REGULATIONS = "regulations";
        String COLUMN_PROVIDER_ID = "service_provider_id";
        String COLUMN_PREFERENCES = "flight_preferences";
        String COLUMN_FK_FLIGHT = "flight_tb_flight_id";
        String COLUMN_FK_CATEGORY = "order_request_category_tb_order_req_category_id";
        String COLUMN_FK_SUPPLIER = "catering_supplier_tb_catering_supplier_id";
    }

    public interface CateringSupplierTable {
        String COLUMN_ID = "catering_supplier_id";
        String COLUMN_NAME = "catering_supplier_name";
    }

    public interface PaymentDetaisTable {
        String COLUMN_ID = "payment_details_id";
        String COLUMN_VAT = "vat";
        String COLUMN_BILLING_ADDRESS = "billing_address";
        String COLUMN_BILLING_REFERENCE = "billing_reference";
        String COLUMN_COMPANY = "company";
        String COLUMN_FK_CATERING_ORDER_REQUEST = "catering_order_request_order_request_id";
    }

    public interface DeliveryTable {
        String COLUMN_ID = "delivery_id";
        String COLUMN_AIRPORT = "delivery_airport";
        String COLUMN_DATE = "delivery_date";
        String COLUMN_TIME = "delivery_time";
        String COLUMN_FK_CATERING_ORDER_REQUEST = "catering_order_request_order_request_id";
    }

    public interface OrderRequestTable {
        String COLUMN_ID = "order_request_id";
        String COLUMN_TYPE = "order_catering_type";
        String COLUMN_DESCRIPTION = "order_description";
        String COLUMN_RANK = "order_rank";
        String COLUMN_TITLE = "order_request_title";
        String COLUMN_PO_QUANTITY = "order_total_po_quantity";
        String COLUMN_UNIT = "order_unit";
        String COLUMN_FK_CATERING_ORDER_REQUEST = "catering_order_request_order_request_id";
    }

    public interface OrderRequestItemTable {
        String COLUMN_ID = "order_request_item_id";
        String COLUMN_PO_QUANTITY = "order_request_item_po_quantity";
        String COLUMN_QUANTITY = "order_request_item_quatity";
        String COLUMN_TO_SHARE = "order_selection_to_share";
        String COLUMN_FK_ORDER_REQUEST = "order_request_tb_order_request_id";
    }
}
