package com.offcasoftware.databaseexample.view;

import com.offcasoftware.databaseexample.R;
import com.offcasoftware.databaseexample.model.Order;
import com.offcasoftware.databaseexample.model.Person;
import com.offcasoftware.databaseexample.model.PersonQuery;
import com.offcasoftware.databaseexample.repository.PersonRepository;
import com.offcasoftware.databaseexample.repository.PersonRepositoryImpl;
import com.offcasoftware.databaseexample.view.adapter.PersonAdapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements PersonAdapter.PersonInterface {

    @BindView(R.id.order_none)
    RadioButton mOrderNone;

    @BindView(R.id.order_asc)
    RadioButton mOrderAsc;

    @BindView(R.id.order_desc)
    RadioButton mOrderDesc;

    @BindView(R.id.query_name)
    EditText mPersonName;

    @BindView(R.id.query_country)
    EditText mPersonCountry;

    @BindView(R.id.query_click)
    EditText mPersonClick;

    @BindView(R.id.query_city)
    EditText mPersonCity;

    @BindView(R.id.persons_recycler_view)
    RecyclerView mRecyclerView;

    private PersonAdapter mPersonAdapter;
    private PersonRepository mPersonRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
        displayAllData();
    }

    @Override
    public void onPersonClick(final Person person) {
        Toast.makeText(this, person.getAddress().getCity(), Toast.LENGTH_SHORT).show();
        mPersonRepository.updateClick(person.getId(), person.getClicked() + 1);
        getFilteredPerson();
    }

    @Override
    public void onPersonDeleteClick(final Person person) {
        mPersonRepository.removePerson(person.getId());
        getFilteredPerson();
    }

    @OnClick(R.id.button_search)
    public void onSearchClick(View view) {
        getFilteredPerson();
    }

    private void init() {
        mPersonAdapter = new PersonAdapter(this);
        mPersonAdapter.setPersonInterface(this);
        mPersonRepository = PersonRepositoryImpl.getInstance();
        mRecyclerView.setAdapter(mPersonAdapter);
    }

    private void displayAllData() {
        List<Person> persons = mPersonRepository.getPersons();
        mPersonAdapter.swapData(persons);
    }

    //if user provide city then we will search ONLY by search (we use JOIN)
    //otherwise we will search by rest fields
    private void getFilteredPerson() {
        String city = mPersonCity.getText().toString().trim();

        if (TextUtils.isEmpty(city)) {

            Order order = Order.NONE;
            if (mOrderAsc.isChecked()) {
                order = Order.ASC;
            } else if (mOrderDesc.isChecked()) {
                order = Order.DESC;
            }

            String name = mPersonName.getText().toString().trim();
            String country = mPersonCountry.getText().toString().trim();
            String click = mPersonClick.getText().toString().trim();
            int clicked = 0;
            if (!TextUtils.isEmpty(click)) {
                clicked = Integer.parseInt(click);
            }

            PersonQuery personQuery = new PersonQuery(order, name, country, clicked);
            List<Person> persons = mPersonRepository.getFilterPerson(personQuery);
            mPersonAdapter.swapData(persons);
        } else {
            List<Person> persons = mPersonRepository.getCityPerson(city);
            mPersonAdapter.swapData(persons);
        }
    }
}
