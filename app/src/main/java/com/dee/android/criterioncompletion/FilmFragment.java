package com.dee.android.criterioncompletion;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.UUID;

public class FilmFragment extends Fragment {
    private Film mFilm;
    private TextView mTitleField;
    private CheckBox mHasWatchedCheckBox;
    private Button mFavoritesButton;
    private RatingBar mRatingBar;
    private CriterionCollection criterionCollection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID filmId = (UUID) getActivity().getIntent().getSerializableExtra(FilmActivity.EXTRA_FILM_ID);
        mFilm = CriterionCollection.get(getActivity()).getFilm(filmId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        criterionCollection = CriterionCollection.get(getActivity());
        View v = inflater.inflate(R.layout.fragment_film, container, false);

        mTitleField = (TextView) v.findViewById(R.id.film_detail_title);
        mTitleField.setText(mFilm.getTitle());

        mRatingBar = (RatingBar) v.findViewById(R.id.rating_bar);
        mRatingBar.setRating(mFilm.getRating());
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mFilm.setRating(v);
                criterionCollection.updateFilmInDb(mFilm);
            }
        });

        mHasWatchedCheckBox = (CheckBox) v.findViewById(R.id.film_detail_has_watched);
        mHasWatchedCheckBox.setChecked(mFilm.hasWatched());
        mHasWatchedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mFilm.setHasWatched(b);
                criterionCollection.updateFilmInDb(mFilm);
            }
        });

        mFavoritesButton = (Button) v.findViewById(R.id.film_detail_add_to_favorites);
        mFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFilm.setFavorite(true);
                criterionCollection.updateFilmInDb(mFilm);
                view.setEnabled(false);
            }
        });
        mFavoritesButton.setEnabled(!mFilm.isFavorite());

        return v;
    }
}
