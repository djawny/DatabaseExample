package com.offcasoftware.databaseexample.net;

import com.offcasoftware.databaseexample.model.Person;

import java.util.List;

public interface PersonService {

    List<Person> downloadPersons();

}
