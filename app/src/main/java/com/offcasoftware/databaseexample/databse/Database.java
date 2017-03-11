package com.offcasoftware.databaseexample.databse;

import com.offcasoftware.databaseexample.model.Person;
import com.offcasoftware.databaseexample.model.PersonQuery;

import java.util.List;

public interface Database {

    void savePersons(List<Person> personList);

    List<Person> getPersons();

    void updateClick(int personId, int click);

    void removePerson(int personId);

    List<Person> getFilterPerson(PersonQuery personQuery);

    List<Person> getCityPerson(String city);

}
