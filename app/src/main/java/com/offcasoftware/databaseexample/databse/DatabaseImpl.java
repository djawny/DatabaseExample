package com.offcasoftware.databaseexample.databse;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.offcasoftware.databaseexample.model.Address;
import com.offcasoftware.databaseexample.model.Order;
import com.offcasoftware.databaseexample.model.Person;
import com.offcasoftware.databaseexample.model.PersonQuery;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseImpl extends OrmLiteSqliteOpenHelper implements Database {

    private final static String DATABASE_NAME = "persons";
    private final static int DATABASE_VERSION = 3;

    private RuntimeExceptionDao<Person, Integer> mPersonsDao;
    private RuntimeExceptionDao<Address, Integer> mAddressDao;

    public DatabaseImpl(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mPersonsDao = getRuntimeExceptionDao(Person.class);
        mAddressDao = getRuntimeExceptionDao(Address.class);
    }

    @Override
    public void onCreate(final SQLiteDatabase database, final ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Person.class);
            TableUtils.createTableIfNotExists(connectionSource, Address.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Person.class, true);
            TableUtils.dropTable(connectionSource, Address.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        onCreate(database, connectionSource);
    }

    @Override
    public void savePersons(final List<Person> personList) {
        try {
            getWritableDatabase().beginTransaction();

            for (Person person : personList) {
                Address address = person.getAddress();
                mAddressDao.createIfNotExists(address);
                mPersonsDao.create(person);
            }

            getWritableDatabase().setTransactionSuccessful();

        } finally {
            getWritableDatabase().endTransaction();
        }
    }

    @Override
    public List<Person> getPersons() {
        return mPersonsDao.queryForAll();
    }

    @Override
    public void updateClick(int personId, int click) {
        try {
            UpdateBuilder<Person, Integer> updateBuilder
                    = mPersonsDao.updateBuilder();

            Where where = updateBuilder.where();
            where.eq("id", personId);
            updateBuilder.updateColumnValue("clickedCount",
                    click);

            updateBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removePerson(final int personId) {
        mPersonsDao.deleteById(personId);
    }

    @Override
    public List<Person> getFilterPerson(final PersonQuery personQuery) {
        try {
            QueryBuilder<Person, Integer> queryBuilder = mPersonsDao.queryBuilder();

            //flag to add where.and()/or() if we add in previous statement
            boolean hasPreviousWhere = false;

            Where where = queryBuilder.where();

            //check if we should query by person.name
            if (personQuery.getName() != null && personQuery.getName().length() > 0) {
                where.like("name", "%" + personQuery.getName() + "%");
                hasPreviousWhere = true;

            }

            //check if we should query by person.country. If we already query by person.name we
            // add where.or()
            if (personQuery.getCountry() != null && personQuery.getCountry().length() > 0) {
                if (hasPreviousWhere) {
                    where.or();
                }
                where.like("country", "%" + personQuery.getCountry() + "%");
                hasPreviousWhere = true;
            }
            if (personQuery.getClicked() > 0) {
                if (hasPreviousWhere) {
                    where.and();
                }
                where.gt("clickedCount", personQuery.getClicked());
            }

            //if we doesn't query by (person.name, person.country and person.click)
            // we remove where object. In other way we get exception
            // is not possible to query with empty where
            if (!hasPreviousWhere) {
                queryBuilder.setWhere(null);
            }

            //add ordering if we set asc or desc
            Order order = personQuery.getOrder();
            if (order == Order.ASC) {
                queryBuilder.orderBy("name", true);
            } else if (order == Order.DESC) {
                queryBuilder.orderBy("name", false);
            }

            return queryBuilder.query();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public List<Person> getCityPerson(final String city) {
        try {
            QueryBuilder<Person, Integer> queryBuilder = mPersonsDao.queryBuilder();
            QueryBuilder<Address, Integer> addressBuilder = mAddressDao.queryBuilder();

            Where where = addressBuilder.where();
            where.like("city", "%" + city + "%");

            queryBuilder.join(addressBuilder);

            //queryBuilder query will get all data because we doesn't provide any where
            //addressBuilder query will get only data where city contains our query city

            return queryBuilder.query();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
