package com.offcasoftware.databaseexample.net;

import com.offcasoftware.databaseexample.model.Address;
import com.offcasoftware.databaseexample.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PersonServiceImpl implements PersonService {

    private static final int PERSON_COUNT = 100;

    private final List<Person> mPersons;

    private final String[] mNames = {"Antoni", "Jakub", "Szymon", "Jan", "Filip",
            "Franciszek", "Mikolaj", "Aleksander", "Kacper", "Wojciech",
            "Adam", "Michal", "Marcel", "Wiktor", "Stanislaw",
            "Bartosz", "Mateusz", "Igor", "Nikodem", "Alan"};

    private final String[] mCountry = {"Polska", "Niemcy", "Czechy", "Francja", "Szwecja",
            "Dania", "Finlandia", "Grecja", "Hiszpania", "Wlochy"};

    private final String[] mCity = {"Poznan", "Wroclaw", "Krakow", "Berlin", "Londyn",
            "Barcelona", "Gdansk", "Szczecin", "Turyn", "Ateny"};


    private Random mRandom;

    public PersonServiceImpl() {
        mPersons = new ArrayList<>();
        List<Address> address = new ArrayList<>();

        for (int i = 0; i < mCity.length; i++) {
            address.add(new Address(i, mCity[i]));
        }

        mRandom = new Random();

        for (int i = 0; i < PERSON_COUNT; i++) {
            int index = mRandom.nextInt(mNames.length);
            int index2 = mRandom.nextInt(mCountry.length);
            int index3 = mRandom.nextInt(mCity.length);
            Person person = createPerson(i, mNames[index], mCountry[index2]);
            person.setAddress(address.get(index3));
            mPersons.add(person);
        }
    }

    @Override
    public List<Person> downloadPersons() {
        return mPersons;
    }

    private Person createPerson(int id, String name, String country) {
        return new Person(id, name, country, 0);
    }
}
