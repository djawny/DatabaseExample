package com.offcasoftware.databaseexample.repository;

import com.offcasoftware.databaseexample.application.AndroidApplication;
import com.offcasoftware.databaseexample.databse.Database;
import com.offcasoftware.databaseexample.model.Person;
import com.offcasoftware.databaseexample.model.PersonQuery;
import com.offcasoftware.databaseexample.net.PersonService;
import com.offcasoftware.databaseexample.net.PersonServiceImpl;

import java.util.List;

public class PersonRepositoryImpl implements PersonRepository {

    private static final PersonRepositoryImpl ourInstance = new PersonRepositoryImpl();

    public static PersonRepositoryImpl getInstance() {
        return ourInstance;
    }

    private final PersonService mPersonService;
    private final Database mDatabase;

    private PersonRepositoryImpl() {
        mPersonService = new PersonServiceImpl();
        mDatabase = AndroidApplication.getDatabase();
    }

    @Override
    public List<Person> getPersons() {
        List<Person> personsPersistent = mDatabase.getPersons();
        if (personsPersistent.isEmpty()) {
            List<Person> person = mPersonService.downloadPersons();
            mDatabase.savePersons(person);
            return person;
        }

        return mDatabase.getPersons();
    }

    @Override
    public void updateClick(final int personId, final int click) {
        mDatabase.updateClick(personId, click);
    }

    @Override
    public void removePerson(final int personId) {
        mDatabase.removePerson(personId);
    }

    @Override
    public List<Person> getFilterPerson(final PersonQuery personQuery) {
        return mDatabase.getFilterPerson(personQuery);
    }

    @Override
    public List<Person> getCityPerson(final String city) {
        return mDatabase.getCityPerson(city);
    }


}
