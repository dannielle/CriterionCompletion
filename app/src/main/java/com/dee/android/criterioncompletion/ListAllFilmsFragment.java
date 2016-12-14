package com.dee.android.criterioncompletion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ListAllFilmsFragment extends NavFragment {

    private RecyclerView mFilmListRecyclerView;
    private ListAllFilmsAdapter mAdapter;
    private CriterionCollection criterionCollection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_all_films, container, false);

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

    private class FilmHolder extends RecyclerView.ViewHolder {

        private final TextView mTitleTextView;
        private final TextView mRatingTextView;
        private final CheckBox mHasWatchedCheckBox;
        private final RatingBar mRatingBar;
        private Film mFilm;
        private Button mAddToFavoritesButton;

        public FilmHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_film, parent, false));
            mTitleTextView = (TextView) itemView.findViewById(R.id.film_title);
            mRatingTextView = (TextView) itemView.findViewById(R.id.film_rating);

            mHasWatchedCheckBox = (CheckBox) itemView.findViewById(R.id.hasWatched);

            mAddToFavoritesButton = (Button) itemView.findViewById(R.id.add_to_favorites_button);

            mRatingBar = (RatingBar) itemView.findViewById(R.id.rating_bar);
        }

        public void bind(Film film) {
            mFilm = film;
            mTitleTextView.setText(mFilm.getTitle());
            mRatingTextView.setText(Float.toString(mFilm.getRating()));
            mRatingBar.setRating(mFilm.getRating());

            mHasWatchedCheckBox.setChecked(mFilm.hasWatched());
            mAddToFavoritesButton.setEnabled(! mFilm.isFavorite());

            mHasWatchedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    criterionCollection.watched(mFilm, b);
                    Toast.makeText(getActivity(), Integer.toString(criterionCollection.getWatchedFilms().size()), Toast.LENGTH_LONG).show();
                }
            });

            mAddToFavoritesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Added to Favorites", Toast.LENGTH_LONG).show();
                    criterionCollection.fave(mFilm);
                }
            });

            mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    criterionCollection.rate(mFilm, v);
                }
            });
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
