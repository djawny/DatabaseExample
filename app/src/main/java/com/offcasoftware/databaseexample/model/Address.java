package com.offcasoftware.databaseexample.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = Address.DATABASE_TABLE)
public class Address {

    static final String DATABASE_TABLE = "Address";

    @DatabaseField(columnName = "id", generatedId = true)
    private int mId;

    @DatabaseField(columnName = "city", canBeNull = false)
    private String mCity;

    public Address() {

    }

    public Address(final int id, final String city) {
        mId = id;
        mCity = city;
    }

    public int getId() {
        return mId;
    }

    public String getCity() {
        return mCity;
    }
}
