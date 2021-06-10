package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    private ArrayList<Word> arrayList;

    public WordAdapter(ArrayList<Word> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @NotNull
    @Override
    public WordAdapter.WordViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word,parent,false);
        WordViewHolder holder = new WordViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WordAdapter.WordViewHolder holder, int position) {
        holder.tv_word.setText(arrayList.get(position).getWord());
        holder.tv_mean.setText(arrayList.get(position).getMean());
    }
    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class WordViewHolder extends RecyclerView.ViewHolder {

        protected TextView tv_word;
        protected TextView tv_mean;

        public WordViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.tv_word = (TextView) itemView.findViewById(R.id.word_text);
            this.tv_mean = (TextView) itemView.findViewById(R.id.mean_text);

        }
    }
}
