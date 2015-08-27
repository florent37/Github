package com.github.florent37.github.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.github.florent37.carpaccio.controllers.adapter.Holder;
import com.github.florent37.github.R;
import com.github.florent37.github.repo.Repo;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by florentchampigny on 31/07/15.
 */
public class RepoHolder extends Holder{

    int textColor;

    @Bind(R.id.newStars)
    TextView newStars;
    @Bind(R.id.newForks)
    TextView newForks;

    public RepoHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        textColor = newStars.getCurrentTextColor();
    }

    public void onBind(Object object) {
        Repo repo = (Repo)object;
        if(repo.getNewStarsCount() > 0) {
            newStars.setTextColor(Color.RED);
        }
        else
            newStars.setTextColor(textColor);
    }

}
