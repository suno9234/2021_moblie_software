package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private ArrayList<BoardItem> arrayList;

    public BoardAdapter(ArrayList<BoardItem> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_item,parent,false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BoardAdapter.ViewHolder holder, int position) {
        holder.text.setText(arrayList.get(position).getBoardText());
        holder.title.setText(arrayList.get(position).getBoardTitle());
        holder.writer.setText(arrayList.get(position).getBoardWriter());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView text;
        protected TextView title;
        protected TextView writer;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.text = (TextView)itemView.findViewById(R.id.text);
            this.title = (TextView)itemView.findViewById(R.id.title);
            this.writer = (TextView)itemView.findViewById(R.id.writer);

        }
    }
}
