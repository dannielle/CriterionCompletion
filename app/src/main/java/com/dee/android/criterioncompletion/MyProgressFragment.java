package com.dee.android.criterioncompletion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class MyProgressFragment extends NavFragment {

    private RecyclerView mWatchedListRecyclerView;
    private CriterionCollection criterionCollection;
    private WatchedFilmsAdapter mAdapter;
    private TextView mProgressNumberView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_progress, container, false);

        mProgressNumberView = (TextView) v.findViewById(R.id.progress_number);

        mWatchedListRecyclerView = (RecyclerView) v.findViewById(R.id.watched_films_recycler_view);
        mWatchedListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
        List<Film> films = criterionCollection.getWatchedFilms();

        mProgressNumberView.setText(Integer.toString(criterionCollection.getWatchedFilms().size()));

        if (mAdapter == null) {
            mAdapter = new WatchedFilmsAdapter(films);
            mWatchedListRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class FilmHolder extends RecyclerView.ViewHolder {

        private final TextView mTitleTextView;
        private final TextView mRatingTextView;
        private final RatingBar mRatingBarView;
        private Film mFilm;

        public FilmHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_watched_film, parent, false));
            mTitleTextView = (TextView) itemView.findViewById(R.id.film_title_a);
            mRatingTextView = (TextView) itemView.findViewById(R.id.film_rating_a);
            mRatingBarView = (RatingBar) itemView.findViewById(R.id.rating_bar);
        }

        public void bind(Film film) {
            mFilm = film;
            mTitleTextView.setText(mFilm.getTitle());
            mRatingTextView.setText(Float.toString(mFilm.getRating()));
            mRatingBarView.setRating(film.getRating());
        }
    }

    private class WatchedFilmsAdapter extends RecyclerView.Adapter<FilmHolder> {

        private List<Film> mWatchedFilms;

        public WatchedFilmsAdapter(List<Film> films) {
            mWatchedFilms = films;
        }

        @Override
        public FilmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new FilmHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(FilmHolder holder, int position) {
            Film film = mWatchedFilms.get(position);
            holder.bind(film);
        }

        @Override
        public int getItemCount() {
            return mWatchedFilms.size();
        }
    }
}
