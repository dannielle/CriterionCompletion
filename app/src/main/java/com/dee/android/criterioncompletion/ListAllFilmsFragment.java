package com.dee.android.criterioncompletion;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListAllFilmsFragment extends NavFragment {

    private RecyclerView mFilmListRecyclerView;
    private ListAllFilmsAdapter mAdapter;
    private CriterionCollection criterionCollection;
    private SearchView mSearchView;
    private RadioGroup mRadioGroupView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_all_films, container, false);

        mFilmListRecyclerView = (RecyclerView) v.findViewById(R.id.all_films_recycler_view);
        mFilmListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        mSearchView = (SearchView) v.findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.filter(newText);
                return true;
            }
        });

        mRadioGroupView = (RadioGroup) v.findViewById(R.id.sort_radio_group);
        mRadioGroupView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch(checkedId) {
                    case R.id.sort_by_title:
                        criterionCollection.setOrder("title");
                        break;
                    case R.id.sort_by_country:
                        criterionCollection.setOrder("country");
                        break;
                    case R.id.sort_by_year:
                        criterionCollection.setOrder("year");
                        break;
                    case -1:
                        criterionCollection.setOrder("title");
                        break;
                    default:
                        break;
                }
                updateUI();
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        criterionCollection = CriterionCollection.get(getActivity());
        List<Film> films = criterionCollection.getFilms();

        if (mAdapter == null) {
            mAdapter = new ListAllFilmsAdapter(films);
            mFilmListRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class FilmHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mTitleTextView;
        private final CheckBox mHasWatchedCheckBox;
        private final TextView mDirectorTextView;
        private final TextView mYearTextView;
        private Film mFilm;

        public FilmHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_film, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.film_title);
            mDirectorTextView = (TextView) itemView.findViewById(R.id.film_director);
            mYearTextView = (TextView) itemView.findViewById(R.id.film_year);

            mHasWatchedCheckBox = (CheckBox) itemView.findViewById(R.id.hasWatched);
            mHasWatchedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    criterionCollection.watched(mFilm, b);
                    bind(mFilm);
                }
            });
        }

        public void bind(Film film) {
            mFilm = film;
            mHasWatchedCheckBox.setChecked(mFilm.hasWatched());
            mTitleTextView.setText(mFilm.getTitle());
            mDirectorTextView.setText(mFilm.getDirector());
            mYearTextView.setText(mFilm.getYear());
            if ( !mFilm.hasWatched() ) {
                mTitleTextView.setTypeface(null, Typeface.BOLD);
                itemView.setBackgroundColor(Color.WHITE);
            } else {
                mTitleTextView.setTypeface(null, Typeface.NORMAL);
                itemView.setBackgroundColor(Color.LTGRAY);
            }
        }

        @Override
        public void onClick(View view) {
            Intent intent = FilmActivity.newIntent(getActivity(), mFilm.getId());
            startActivity(intent);
        }
    }

    private class ListAllFilmsAdapter extends RecyclerView.Adapter<FilmHolder> {

        private List<Film> mFilms;

        public ListAllFilmsAdapter(List<Film> films) {
            mFilms = films;
        }

        @Override
        public FilmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new FilmHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(FilmHolder holder, int position) {
            Film film = mFilms.get(position);
            holder.bind(film);
        }

        @Override
        public int getItemCount() {
            return mFilms.size();
        }

        public void filter(String query) {
            mFilms = new ArrayList<>();
            if(query.isEmpty()){
                mFilms.addAll(criterionCollection.getFilms());
            } else{
                query = query.toLowerCase();
                for(Film film: criterionCollection.getFilms()){
                    if(film.getTitle().toLowerCase().contains(query) ||
                            film.getCountry().toLowerCase().contains(query) ||
                            film.getYear().contains(query) ||
                            film.getDirector().toLowerCase().contains(query)){
                        mFilms.add(film);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }
}
