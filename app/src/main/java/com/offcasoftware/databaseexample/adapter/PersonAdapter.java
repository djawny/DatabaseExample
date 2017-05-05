package com.offcasoftware.databaseexample.adapter;

import com.offcasoftware.databaseexample.R;
import com.offcasoftware.databaseexample.model.Person;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    public interface PersonInterface {

        void onPersonClick(Person person);

        void onPersonDeleteClick(Person person);

    }

    private final LayoutInflater mLayoutInflater;
    private final List<Person> mPersons;

    private PersonInterface mPersonInterface;

    public PersonAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mPersons = new ArrayList<>();
    }


    @Override
    public PersonViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = mLayoutInflater.inflate(R.layout.row_person_list, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder holder, final int position) {
        final Person person = getItem(position);

        holder.mPersonId.setText(String.valueOf(person.getId()));
        holder.mPersonName.setText(person.getName());
        holder.mPersonCountry.setText(person.getCountry());
        holder.mPersonClick.setText(String.valueOf(person.getClicked()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mPersonInterface.onPersonClick(person);
            }
        });

        holder.mPersonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mPersonInterface.onPersonDeleteClick(person);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPersons.size();
    }

    public void setPersonInterface(final PersonInterface personInterface) {
        mPersonInterface = personInterface;
    }

    public void swapData(final List<Person> data) {
        if (data != null) {
            mPersons.clear();
            mPersons.addAll(data);
            notifyDataSetChanged();
        }
    }

    private Person getItem(final int position) {
        return mPersons.get(position);
    }

    static class PersonViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.person_id)
        TextView mPersonId;

        @BindView(R.id.person_name)
        TextView mPersonName;

        @BindView(R.id.person_country)
        TextView mPersonCountry;

        @BindView(R.id.person_click)
        TextView mPersonClick;

        @BindView(R.id.person_delete)
        ImageView mPersonDelete;


        public PersonViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
