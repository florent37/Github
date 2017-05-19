package com.github.florent37.github.repo;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 07/03/2017.
 */

public class RepoAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<Repo> items;

    public RepoAdapter() {
        items = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.buildFor(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Repo> repos){
        this.items.clear();
        this.items.addAll(repos);
        notifyDataSetChanged();
    }


}
