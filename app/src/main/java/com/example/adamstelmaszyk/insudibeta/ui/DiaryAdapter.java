package com.example.adamstelmaszyk.insudibeta.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adamstelmaszyk.insudibeta.R;
import com.example.adamstelmaszyk.insudibeta.db.entity.DiaryEntry;
import com.example.adamstelmaszyk.insudibeta.ui.Fragments.DiaryFragment;

import java.text.SimpleDateFormat;
import java.util.List;

public class DiaryAdapter extends Adapter<DiaryAdapter.DiaryViewHolder> {

    class DiaryViewHolder extends RecyclerView.ViewHolder{
        private final TextView diaryItemView;

        private DiaryViewHolder(View itemView){
            super(itemView);
            diaryItemView = itemView.findViewById(R.id.entryView);
        }
    }
    private final LayoutInflater mInflater;
    private List<DiaryEntry> entryList; //cached copy of entries

    public  DiaryAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public DiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new DiaryViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(DiaryViewHolder holder, int position){
        if(entryList != null){
            DiaryEntry current = entryList.get(position);
            holder.diaryItemView.setText("dawka: " + String.format("%.2f", current.insulinDose) + ", czas: " + new SimpleDateFormat("HH:mm dd-MM-YYYY").format(current.dateTime.getTime()));
        }else {
            //covers the case of data not being ready yet.
            holder.diaryItemView.setText("No entry");
        }
    }

    public void setEntries(List<DiaryEntry> entries){
        entryList = entries;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if (entryList != null)
            return entryList.size();
        else return 0;
    }


}
