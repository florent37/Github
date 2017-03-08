package com.github.florent37.github.repo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.florent37.github.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by florentchampigny on 07/03/2017.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    public static final int LAYOUT = R.layout.cell_repo;

    public static ViewHolder buildFor(ViewGroup container){
        return new ViewHolder(LayoutInflater.from(container.getContext()).inflate(LAYOUT, container, false));
    }

    @BindView(R.id.repoName)
    TextView repoName;

    @BindView(R.id.repoStars)
    TextView repoStars;

    @BindView(R.id.repoForks)
    TextView repoForks;

    public ViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Repo repo) {
        repoName.setText(repo.getName());
        repoStars.setText(repo.getStars());
        repoForks.setText(repo.getForks());
    }
}
