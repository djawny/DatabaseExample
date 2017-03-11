package com.offcasoftware.databaseexample.model;

public class PersonQuery {

    private final Order mOrder;
    private final String mName;
    private final String mCountry;
    private final int mClicked;

    public PersonQuery(final Order order, final String name, final String country, final int clicked) {
        mOrder = order;
        mName = name;
        mCountry = country;
        mClicked = clicked;
    }

    public Order getOrder() {
        return mOrder;
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
}
