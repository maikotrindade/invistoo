package com.jumbomob.invistoo.model.persistence.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.vistajet.aea.model.entity.Aircraft;
import com.vistajet.aea.model.entity.Airport;
import com.vistajet.aea.model.entity.Crew;
import com.vistajet.aea.model.entity.Customer;
import com.vistajet.aea.model.entity.Flight;
import com.vistajet.aea.model.persistence.DatabaseContract.CostumerTable;
import com.vistajet.aea.model.persistence.DatabaseContract.CrewTable;
import com.vistajet.aea.model.persistence.DatabaseContract.FlightHasCrewTable;
import com.vistajet.aea.model.persistence.DatabaseContract.FlightTable;
import com.vistajet.aea.model.persistence.DatabaseContract.Tables;
import com.vistajet.aea.util.DatabaseUtil;
import com.vistajet.aea.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Data Access Object for {@link Asset} domain
 *
 * @author maiko.trindade
 * @since 09/02/2016
 */
public class AssetDAO extends BaseDAO {


    public boolean insert(final Flight flight) {

        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(FlightTable.COLUMN_ID, flight.getId());
            values.put(FlightTable.COLUMN_CALL_SIGN, flight.getCallSign());
            values.put(FlightTable.COLUMN_NUMBER_OF_PASSENGERS, flight.getNumberOfPassengers());
            values.put(FlightTable.COLUMN_REMARKS, flight.getRemarks());
            values.put(FlightTable.COLUMN_IS_DEMO, flight.isDemo() ? 1 : 0);
            values.put(FlightTable.COLUMN_IS_SENSITIVE, flight.isSensitive() ? 1 : 0);
            values.put(FlightTable.COLUMN_FK_STATUS, flight.getStatus());
            //FIXME
            values.put(FlightTable.COLUMN_FK_COSTUMER, 0);
            values.put(FlightTable.COLUMN_BOOKING_DATE, flight.getBookingDate());
            values.put(FlightTable.COLUMN_DATE, flight.getDate());
            values.put(FlightTable.COLUMN_ARRIVAL_TIME, flight.getArrivalTime());
            values.put(FlightTable.COLUMN_DEPARTURE_TIME, flight.getDepartureTime());
            values.put(FlightTable.COLUMN_LOCAL_ARRIVAL_TIME, flight.getLocalArrivalTime());
            values.put(FlightTable.COLUMN_LOCAL_DEPARTURE_TIME, flight.getLocalDepartureTime());
            values.put(FlightTable.COLUMN_LAST_UPDATE, DateUtil.formatDate(new Date()));
            values.put(FlightTable.COLUMN_LAST_UPDATE, DateUtil.formatDate(new Date()));
            //GV doesn't have this 'is closed' attibute
            values.put(FlightTable.COLUMN_IS_CLOSED, 0);

            final Aircraft aircraft = flight.getAircraft();
            if (aircraft != null) {
                //verify if aircraft already exists on database otherwise insert it
                AircraftDAO aircraftDAO = new AircraftDAO();
                Aircraft fromDatabase = aircraftDAO.findByTailNumber(database, aircraft
                        .getTailNumber());
                if (fromDatabase == null) {
                    long id = aircraftDAO.insert(database, flight.getAircraft());
                    aircraft.setId(id);
                } else {
                    aircraft.setId(fromDatabase.getId());
                }
            }
            values.put(FlightTable.COLUMN_FK_AIRCRAFT, aircraft.getId());

            final AirportDAO airportDAO = new AirportDAO();
            final Airport arrivalAirport = flight.getArrivalAirport();
            if (arrivalAirport != null) {
                //verify if airport already exists on database otherwise insert it
                Airport fromDatabase = airportDAO.findbyicao(database, arrivalAirport.getIcao());
                if (fromDatabase == null) {
                    long id = airportDAO.insert(database, arrivalAirport);
                    arrivalAirport.setId(id);
                } else {
                    arrivalAirport.setId(fromDatabase.getId());
                }
            }
            values.put(FlightTable.COLUMN_FK_ARRIVAL_AIRPORT, arrivalAirport.getId());

            final Airport departureAirport = flight.getDepartureAirport();
            if (departureAirport != null) {
                //verify if airport already exists on database otherwise insert it
                Airport fromDatabase = airportDAO.findbyicao(database, departureAirport.getIcao());
                if (fromDatabase == null) {
                    long id = airportDAO.insert(database, departureAirport);
                    departureAirport.setId(id);
                } else {
                    departureAirport.setId(fromDatabase.getId());
                }
            }
            values.put(FlightTable.COLUMN_FK_DEPARTURE_AIRPORT, departureAirport.getId());

            long flightLocalId = insertOrUpdate(values, flight.getId());

            final List<Crew> crewList = flight.getCrews();
            if (crewList != null && !crewList.isEmpty()) {
                final CrewDAO crewDAO = new CrewDAO();
                //it gets crew's data from database or insert it
                for (Crew crew : crewList) {
                    long crewId;
                    final Crew fromDatabaseCrew = crewDAO.findByCode(crew.getCode());
                    if (fromDatabaseCrew == null) {
                        crewId = crewDAO.insert(crew);
                    } else {
                        crewId = fromDatabaseCrew.getId();
                    }
                    insertFlightHasCrew(flightLocalId, crewId, crew.getFunction());
                }
            }

            database.setTransactionSuccessful();
            return true;
        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
            return false;
        } finally {
            database.endTransaction();
        }
    }

    public void insertFlightHasCrew(final long flightId, final long crewId, final String
            crewFunction) {
        ContentValues values = new ContentValues();
        values.put(FlightHasCrewTable.COLUMN_FK_FLIGHT, flightId);
        values.put(FlightHasCrewTable.COLUMN_FK_CREW, crewId);
        values.put(FlightHasCrewTable.COLUMN_FUNCTION, crewFunction);
        database.insertOrThrow(Tables.FLIGHT_HAS_CREW, null, values);
    }

    //TODO this method is gonna be used when L.B will be implemented
    private void insertCostumer(Customer customer) {
        ContentValues values = new ContentValues();
        values.put(CostumerTable.COLUMN_ID, customer.getId());
        values.put(CostumerTable.COLUMN_NAME, customer.getName());
        values.put(CostumerTable.COLUMN_ACCOUNT_ID, customer.getAccountId());
        values.put(CostumerTable.COLUMN_ACCOUNT_NAME, customer.getAccountName());
        values.put(CostumerTable.COLUMN_ACCOUNT_TYPE_ID, customer.getAccountTypeId());
        values.put(CostumerTable.COLUMN_ACCOUNT_TYPE_NAME, customer.getAccountTypeName());
        database.insert(Tables.FLIGHT_COSTUMER, null, values);
    }

    /**
     * @param values   from {@link Flight} to insert or update
     * @param flightId
     * @return Flight local id from database
     */
    private long insertOrUpdate(final ContentValues values, final long flightId) {
        final String query = "SELECT " + FlightTable.COLUMN_LOCAL_ID +
                " FROM " + Tables.FLIGHT +
                " WHERE " + FlightTable.COLUMN_ID + " = ? ";
        final Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(flightId)});
        final boolean isInsert = (!(cursor.moveToFirst()) || cursor.getCount() == 0) ? true : false;
        if (isInsert) {
            return database.insertOrThrow(Tables.FLIGHT, null, values);
        } else {
            final String whereClause = FlightTable.COLUMN_ID + " = " + flightId;
            //GV doesn't have this 'is closed' attibute
            values.remove(FlightTable.COLUMN_IS_CLOSED);
            database.update(Tables.FLIGHT, values, whereClause, null);

            //return passenger localId from database
            cursor.moveToFirst();
            long flightLocalId = cursor.getLong(0);
            cursor.close();
            return flightLocalId;
        }
    }

    public List<Flight> findAll() {
        List<Flight> flights = new ArrayList<>();
        Cursor cursor = database.query(Tables.FLIGHT, FlightTable.projection, null, null, null,
                null, null);
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Flight flight = cursorToFlight(cursor);
                flights.add(flight);
                cursor.moveToNext();
            }
        }
        cursor.close();

        return flights;
    }

    /**
     * @param crew3LC  {@link Crew} code
     * @param fromDate
     * @param toDate
     * @return list of flights on a specific range
     */
    public List<Flight> findByCrewAndDate(final String crew3LC, final Date fromDate, final Date
            toDate) {

        StringBuilder columns = new StringBuilder()
                .append("flight_tb.").append(FlightTable.COLUMN_LOCAL_ID).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_ID).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_NUMBER_OF_PASSENGERS).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_CALL_SIGN).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_BOOKING_DATE).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_LAST_UPDATE).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_REMARKS).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_IS_DEMO).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_IS_SENSITIVE).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_DATE).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_DEPARTURE_TIME).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_ARRIVAL_TIME).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_LOCAL_DEPARTURE_TIME).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_LOCAL_ARRIVAL_TIME).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_IS_CLOSED).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_FK_STATUS).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_FK_AIRCRAFT).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_FK_DEPARTURE_AIRPORT).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_FK_ARRIVAL_AIRPORT).append(", ")
                .append("crew_tb.").append(CrewTable.COLUMN_ID).append(", ")
                .append("crew_tb.").append(CrewTable.COLUMN_CODE).append(", ")
                .append("crew_tb.").append(CrewTable.COLUMN_PERSON_NAME).append(", ")
                .append("crew_tb.").append(CrewTable.COLUMN_DATE_OF_BIRTH).append(", ")
                .append("flight_has_crew_tb.").append(FlightHasCrewTable.COLUMN_FUNCTION);

        String query = "SELECT " + columns.toString() + " FROM " + Tables.FLIGHT +
                " INNER JOIN " + Tables.FLIGHT_HAS_CREW +
                " ON " + Tables.FLIGHT_HAS_CREW + "." + FlightHasCrewTable.COLUMN_FK_FLIGHT +
                " = " + Tables.FLIGHT + "." + FlightTable.COLUMN_LOCAL_ID +
                " INNER JOIN " + Tables.CREW + " ON " + Tables.FLIGHT_HAS_CREW + "." +
                FlightHasCrewTable.COLUMN_FK_CREW + " = " + Tables.CREW + "." + CrewTable.COLUMN_ID
                + " WHERE " + Tables.CREW + "." + CrewTable.COLUMN_CODE + " = ? " +
                " AND " + Tables.FLIGHT + "." + FlightTable.COLUMN_ARRIVAL_TIME +
                " BETWEEN ? AND ? " +
                " GROUP BY " + Tables.FLIGHT + "." + FlightTable.COLUMN_LOCAL_ID +
                " ORDER BY " + Tables.FLIGHT + "." + FlightTable.COLUMN_DEPARTURE_TIME;

        List<Flight> flights = new ArrayList<>();
        final String[] whereClauses = {crew3LC,
                DateUtil.formatDate(fromDate, DateUtil.ISO_8601_FORMAT),
                DateUtil.formatDate(toDate, DateUtil.ISO_8601_FORMAT)};
        final Cursor cursor = database.rawQuery(query, whereClauses);
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Flight flight = new Flight();
                flight.setLocalId(cursor.getInt(0));
                flight.setId(cursor.getInt(1));
                flight.setNumberOfPassengers(cursor.getInt(2));
                flight.setCallSign(cursor.getString(3));
                flight.setBookingDate(cursor.getString(4));
                flight.setLastUpdate(cursor.getString(5));
                flight.setRemarks(cursor.getString(6));
                flight.setIsDemo(DatabaseUtil.getBoolean(cursor.getInt(7)));
                flight.setIsSensitive(DatabaseUtil.getBoolean(cursor.getInt(8)));
                flight.setDate(cursor.getString(9));
                flight.setDepartureTime(cursor.getString(10));
                flight.setArrivalTime(cursor.getString(11));
                flight.setLocalDepartureTime(cursor.getString(12));
                flight.setLocalArrivalTime(cursor.getString(13));
                flight.setIsClosed(DatabaseUtil.getBoolean(cursor.getInt(14)));
                flight.setStatus(cursor.getInt(15));

                final AircraftDAO aircraftDAO = new AircraftDAO();
                final Aircraft aircraft = aircraftDAO.findById(database, (cursor.getInt(16)));
                if (aircraft != null) {
                    flight.setAircraft(aircraft);
                }

                final AirportDAO airportDAO = new AirportDAO();
                final Airport departureAirport = airportDAO.findById(database, (cursor.getInt(17)));
                if (departureAirport != null) {
                    flight.setDepartureAirport(departureAirport);
                }
                final Airport arrivalAirport = airportDAO.findById(database, (cursor.getInt(18)));
                if (arrivalAirport != null) {
                    flight.setArrivalAirport(arrivalAirport);
                }

                List<Crew> crewList = new ArrayList<>();
                Crew crew = new Crew();
                crew.setId(cursor.getInt(19));
                crew.setCode(cursor.getString(20));
                crew.setPersonName(cursor.getString(21));
                crew.setDateOfBirth(cursor.getString(22));
                crew.setFunction(cursor.getString(23));
                crewList.add(crew);

                flight.setCrews(crewList);
                flights.add(flight);
                cursor.moveToNext();
            }
        }
        cursor.close();

        return flights;
    }

    public Flight findCurrentFlightByCrew(final String crew3LC) {
        StringBuilder columns = new StringBuilder()
                .append("flight_tb.").append(FlightTable.COLUMN_LOCAL_ID).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_ID).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_NUMBER_OF_PASSENGERS).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_CALL_SIGN).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_BOOKING_DATE).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_LAST_UPDATE).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_REMARKS).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_IS_DEMO).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_IS_SENSITIVE).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_DATE).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_DEPARTURE_TIME).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_ARRIVAL_TIME).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_LOCAL_DEPARTURE_TIME).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_LOCAL_ARRIVAL_TIME).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_FK_AIRCRAFT).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_FK_DEPARTURE_AIRPORT).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_FK_ARRIVAL_AIRPORT).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_FK_STATUS).append(", ")
                .append("crew_tb.").append(CrewTable.COLUMN_ID).append(", ")
                .append("crew_tb.").append(CrewTable.COLUMN_CODE).append(", ")
                .append("crew_tb.").append(CrewTable.COLUMN_PERSON_NAME).append(", ")
                .append("crew_tb.").append(CrewTable.COLUMN_DATE_OF_BIRTH).append(", ")
                .append("flight_has_crew_tb.").append(FlightHasCrewTable.COLUMN_FUNCTION);

        String query = "SELECT " + columns.toString() + " FROM " + Tables.FLIGHT +
                " INNER JOIN " + Tables.FLIGHT_HAS_CREW +
                " ON " + Tables.FLIGHT_HAS_CREW + "." + FlightHasCrewTable.COLUMN_FK_FLIGHT +
                " = " + Tables.FLIGHT + "." + FlightTable.COLUMN_LOCAL_ID +
                " INNER JOIN " + Tables.CREW + " ON " + Tables.FLIGHT_HAS_CREW + "." +
                FlightHasCrewTable.COLUMN_FK_CREW + " = " + Tables.CREW + "." + CrewTable.COLUMN_ID
                + " WHERE " + Tables.CREW + "." + CrewTable.COLUMN_CODE + " = ? " +
                " AND DATE('NOW') BETWEEN " + Tables.FLIGHT + "." + FlightTable
                .COLUMN_DEPARTURE_TIME +
                " " +
                " AND " + Tables.FLIGHT + "." + FlightTable.COLUMN_ARRIVAL_TIME;

        Flight flight = null;
        final String[] whereClauses = {crew3LC};
        final Cursor cursor = database.rawQuery(query, whereClauses);
        if (cursor != null && cursor.moveToFirst()) {
            flight = new Flight();
            flight.setLocalId(cursor.getInt(0));
            flight.setId(cursor.getInt(1));
            flight.setNumberOfPassengers(cursor.getInt(2));
            flight.setCallSign(cursor.getString(3));
            flight.setBookingDate(cursor.getString(4));
            flight.setLastUpdate(cursor.getString(5));
            flight.setRemarks(cursor.getString(6));
            flight.setIsDemo(DatabaseUtil.getBoolean(cursor.getInt(7)));
            flight.setIsSensitive(DatabaseUtil.getBoolean(cursor.getInt(8)));
            flight.setDate(cursor.getString(9));
            flight.setDepartureTime(cursor.getString(10));
            flight.setArrivalTime(cursor.getString(11));
            flight.setLocalDepartureTime(cursor.getString(12));
            flight.setLocalArrivalTime(cursor.getString(13));
            flight.setStatus(cursor.getInt(14));

            final AircraftDAO aircraftDAO = new AircraftDAO();
            final Aircraft aircraft = aircraftDAO.findById(database, (cursor.getInt(14)));
            if (aircraft != null) {
                flight.setAircraft(aircraft);
            }

            final AirportDAO airportDAO = new AirportDAO();
            final Airport departureAirport = airportDAO.findById(database, (cursor.getInt(15)));
            if (departureAirport != null) {
                flight.setDepartureAirport(departureAirport);
            }
            final Airport arrivalAirport = airportDAO.findById(database, (cursor.getInt(16)));
            if (arrivalAirport != null) {
                flight.setArrivalAirport(arrivalAirport);
            }

            List<Crew> crewList = new ArrayList<>();
            Crew crew = new Crew();
            crew.setId(cursor.getInt(17));
            crew.setCode(cursor.getString(18));
            crew.setPersonName(cursor.getString(19));
            crew.setDateOfBirth(cursor.getString(20));
            crew.setFunction(cursor.getString(21));
            crewList.add(crew);

            flight.setCrews(crewList);

            cursor.close();
        }


        return flight;
    }

    public Flight findNextFlightByCrew(final String crew3LC) {
        StringBuilder columns = new StringBuilder()
                .append("flight_tb.").append(FlightTable.COLUMN_LOCAL_ID).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_ID).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_NUMBER_OF_PASSENGERS).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_CALL_SIGN).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_BOOKING_DATE).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_LAST_UPDATE).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_REMARKS).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_IS_DEMO).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_IS_SENSITIVE).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_DATE).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_DEPARTURE_TIME).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_ARRIVAL_TIME).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_LOCAL_DEPARTURE_TIME).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_LOCAL_ARRIVAL_TIME).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_FK_AIRCRAFT).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_FK_DEPARTURE_AIRPORT).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_FK_ARRIVAL_AIRPORT).append(", ")
                .append("flight_tb.").append(FlightTable.COLUMN_FK_STATUS).append(", ")
                .append("crew_tb.").append(CrewTable.COLUMN_ID).append(", ")
                .append("crew_tb.").append(CrewTable.COLUMN_CODE).append(", ")
                .append("crew_tb.").append(CrewTable.COLUMN_PERSON_NAME).append(", ")
                .append("crew_tb.").append(CrewTable.COLUMN_DATE_OF_BIRTH).append(", ")
                .append("flight_has_crew_tb.").append(FlightHasCrewTable.COLUMN_FUNCTION);

        String query = "SELECT " + columns.toString() + " FROM " + Tables.FLIGHT +
                " INNER JOIN " + Tables.FLIGHT_HAS_CREW +
                " ON " + Tables.FLIGHT_HAS_CREW + "." + FlightHasCrewTable.COLUMN_FK_FLIGHT +
                " = " + Tables.FLIGHT + "." + FlightTable.COLUMN_LOCAL_ID +
                " INNER JOIN " + Tables.CREW + " ON " + Tables.FLIGHT_HAS_CREW + "." +
                FlightHasCrewTable.COLUMN_FK_CREW + " = " + Tables.CREW + "." + CrewTable.COLUMN_ID
                + " WHERE " + Tables.CREW + "." + CrewTable.COLUMN_CODE + " = ? " +
                " AND " + Tables.FLIGHT + "." + FlightTable.COLUMN_DEPARTURE_TIME + " > DATE" +
                "('NOW')" +
                " ORDER BY " + Tables.FLIGHT + "." + FlightTable.COLUMN_DEPARTURE_TIME;

        Flight flight = null;
        final String[] whereClauses = {crew3LC};
        final Cursor cursor = database.rawQuery(query, whereClauses);
        if (cursor != null && cursor.moveToFirst()) {
            flight = new Flight();
            flight.setLocalId(cursor.getInt(0));
            flight.setId(cursor.getInt(1));
            flight.setNumberOfPassengers(cursor.getInt(2));
            flight.setCallSign(cursor.getString(3));
            flight.setBookingDate(cursor.getString(4));
            flight.setLastUpdate(cursor.getString(5));
            flight.setRemarks(cursor.getString(6));
            flight.setIsDemo(DatabaseUtil.getBoolean(cursor.getInt(7)));
            flight.setIsSensitive(DatabaseUtil.getBoolean(cursor.getInt(8)));
            flight.setDate(cursor.getString(9));
            flight.setDepartureTime(cursor.getString(10));
            flight.setArrivalTime(cursor.getString(11));
            flight.setLocalDepartureTime(cursor.getString(12));
            flight.setLocalArrivalTime(cursor.getString(13));
            flight.setStatus(cursor.getInt(14));

            final AircraftDAO aircraftDAO = new AircraftDAO();
            final Aircraft aircraft = aircraftDAO.findById(database, (cursor.getInt(14)));
            if (aircraft != null) {
                flight.setAircraft(aircraft);
            }

            final AirportDAO airportDAO = new AirportDAO();
            final Airport departureAirport = airportDAO.findById(database, (cursor.getInt(15)));
            if (departureAirport != null) {
                flight.setDepartureAirport(departureAirport);
            }
            final Airport arrivalAirport = airportDAO.findById(database, (cursor.getInt(16)));
            if (arrivalAirport != null) {
                flight.setArrivalAirport(arrivalAirport);
            }

            List<Crew> crewList = new ArrayList<>();
            Crew crew = new Crew();
            crew.setId(cursor.getInt(17));
            crew.setCode(cursor.getString(18));
            crew.setPersonName(cursor.getString(19));
            crew.setDateOfBirth(cursor.getString(20));
            crew.setFunction(cursor.getString(21));
            crewList.add(crew);

            flight.setCrews(crewList);

            cursor.close();
        }


        return flight;
    }

    public int updateIsClosed(final long flightLocalId, boolean isClosed) {
        ContentValues values = new ContentValues();
        values.put(FlightTable.COLUMN_IS_CLOSED, isClosed ? 1 : 0);
        final String selection = FlightTable.COLUMN_LOCAL_ID + " = ?";
        return database.update(Tables.FLIGHT, values, selection, new String[]{String.valueOf
                (flightLocalId)});
    }


    private Flight cursorToFlight(Cursor cursor) {
        Flight flight = new Flight();
        flight.setId(cursor.getInt(0));
        flight.setNumberOfPassengers(cursor.getInt(1));
        flight.setCallSign(cursor.getString(2));
        flight.setBookingDate(cursor.getString(3));
        flight.setLastUpdate(cursor.getString(4));
        flight.setRemarks(cursor.getString(5));
        flight.setIsDemo(DatabaseUtil.getBoolean(cursor.getInt(6)));
        flight.setIsSensitive(DatabaseUtil.getBoolean(cursor.getInt(7)));
        flight.setDate(cursor.getString(8));
        flight.setDepartureTime(cursor.getString(9));
        flight.setArrivalTime(cursor.getString(10));
        flight.setLocalDepartureTime(cursor.getString(11));
        flight.setLocalArrivalTime(cursor.getString(12));

        //TODO
//        Customer customer = new Customer();
//        Aircraft aircraft = new Aircraft();
//        Airport departureAirport = new Airport();
//        departureAirport.setCity("SAO PAULO");
//        Airport arrivalAirport = new Airport();
//        FlightLegStatus status = new FlightLegStatus();
//        flight.setAircraft(aircraft);
//        flight.setCustomer(customer);
//        flight.setDepartureAirport(departureAirport);
//        arrivalAirport.setCity("LONDON");
//        flight.setArrivalAirport(arrivalAirport);
//        flight.setStatus(status);
//        flight.setPermits(new ArrayList<FlightPermit>());
        return flight;
    }
}
