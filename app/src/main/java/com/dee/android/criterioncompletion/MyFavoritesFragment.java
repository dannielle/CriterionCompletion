package com.dee.android.criterioncompletion;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyFavoritesFragment extends NavFragment {

    private RecyclerView mFavoriteListRecyclerView;
    private CriterionCollection criterionCollection;
    private FavoriteFilmsAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_favorites, container, false);

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setTitle("Favs");
        toolbar.setTitleTextColor(Color.WHITE);

        mFavoriteListRecyclerView = (RecyclerView) v.findViewById(R.id.favorite_films_recycler_view);
        mFavoriteListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }

    private void updateUI() {
        criterionCollection = CriterionCollection.get(getActivity());
        List<Film> films = criterionCollection.getFavoriteFilms();

        if (mAdapter == null) {
            mAdapter = new FavoriteFilmsAdapter(films);
            mFavoriteListRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private class FilmHolder extends RecyclerView.ViewHolder {

        private final TextView mTitleTextView;
        private final TextView mRatingTextView;
        private final RatingBar mRatingBarView;
        private Film mFilm;

        public FilmHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_favorite_film, parent, false));
            mTitleTextView = (TextView) itemView.findViewById(R.id.film_title);
            mRatingTextView = (TextView) itemView.findViewById(R.id.film_rating);
            mRatingBarView = (RatingBar) itemView.findViewById(R.id.rating_bar);
        }

        public void bind(Film film) {
            mFilm = film;
            mTitleTextView.setText(mFilm.getTitle());
            mRatingTextView.setText(Float.toString(mFilm.getRating()));
            mRatingBarView.setRating(mFilm.getRating());
        }
    }

    private class FavoriteFilmsAdapter extends RecyclerView.Adapter<FilmHolder> {

        private List<Film> mFavoriteFilms;

        public FavoriteFilmsAdapter(List<Film> films) {
            mFavoriteFilms = films;
        }

        @Override
        public FilmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new FilmHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(FilmHolder holder, int position) {
            Film film = mFavoriteFilms.get(position);
            holder.bind(film);
        }

        @Override
        public int getItemCount() {
            return mFavoriteFilms.size();
        }
    }
}
