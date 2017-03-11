package com.offcasoftware.databaseexample.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = Person.DATABASE_TABLE)
public class Person {

    static final String DATABASE_TABLE = "persons";

    @DatabaseField(columnName = "id", generatedId = true)
    private int mId;

    @DatabaseField(columnName = "name", canBeNull = false)
    private String mName;

    @DatabaseField(columnName = "country")
    private String mCountry;

    @DatabaseField(columnName = "clickedCount")
    private int mClicked;

    @DatabaseField(columnName = "addressId", foreign = true, foreignAutoRefresh = true)
    private Address mAddress;

    public Person() {

    }

    public Person(final int id, final String name, final String country, final int clicked) {
        mId = id;
        mName = name;
        mCountry = country;
        mClicked = clicked;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getCountry() {
        return mCountry;
    }

    public int getClicked() {
        return mClicked;
    }

    public Address getAddress() {
        return mAddress;
    }

    public void setAddress(final Address address) {
        mAddress = address;
    }
}
