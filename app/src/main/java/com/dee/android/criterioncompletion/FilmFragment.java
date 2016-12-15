package com.dee.android.criterioncompletion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.UUID;

public class FilmFragment extends Fragment {
    private Film mFilm;
    private TextView mTitleField;
    private CheckBox mHasWatchedCheckBox;
    private Button mFavoritesButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID filmId = (UUID) getActivity().getIntent().getSerializableExtra(FilmActivity.EXTRA_FILM_ID);
        mFilm = CriterionCollection.get(getActivity()).getFilm(filmId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_film, container, false);

        mTitleField = (TextView) v.findViewById(R.id.film_detail_title);
        mTitleField.setText(mFilm.getTitle());

        mHasWatchedCheckBox = (CheckBox) v.findViewById(R.id.film_detail_has_watched);
        mHasWatchedCheckBox.setChecked(mFilm.hasWatched());

        mFavoritesButton = (Button) v.findViewById(R.id.film_detail_add_to_favorites);
        mFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFilm.setFavorite(true);
            }
        });
        mFavoritesButton.setEnabled(!mFilm.isFavorite());

        return v;
    }
}
