package com.dee.android.criterioncompletion;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class ListAllFilmsFragment extends NavFragment {

    private RecyclerView mFilmListRecyclerView;
    private ListAllFilmsAdapter mAdapter;
    private CriterionCollection criterionCollection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_all_films, container, false);

        setToolbar("All", v);

        mFilmListRecyclerView = (RecyclerView) v.findViewById(R.id.all_films_recycler_view);
        mFilmListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

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
        private Film mFilm;

        public FilmHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_film, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.film_title);

            mHasWatchedCheckBox = (CheckBox) itemView.findViewById(R.id.hasWatched);
            mHasWatchedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    criterionCollection.watched(mFilm, b);
                }
            });
        }

        public void bind(Film film) {
            mFilm = film;
            mHasWatchedCheckBox.setChecked(mFilm.hasWatched());
            mTitleTextView.setText(mFilm.getTitle());
            if (mFilm.hasWatched()) {
                mTitleTextView.setTypeface(null, Typeface.BOLD);
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
    }
}
