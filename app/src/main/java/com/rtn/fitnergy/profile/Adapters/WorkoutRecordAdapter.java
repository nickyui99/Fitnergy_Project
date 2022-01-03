package com.rtn.fitnergy.profile.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.rtn.fitnergy.R;
import com.rtn.fitnergy.profile.model.WorkoutRecordModel;

import java.util.ArrayList;


public class WorkoutRecordAdapter extends RecyclerView.Adapter<WorkoutRecordAdapter.ViewHolder> {
    Context context;
    ArrayList<WorkoutRecordModel> workoutRecordArrayList;

    public WorkoutRecordAdapter(Context context, ArrayList<WorkoutRecordModel> list) {
        this.context = context;
        this.workoutRecordArrayList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_record_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkoutRecordModel currentModel = workoutRecordArrayList.get(position);
        holder.textViewWorkoutPlanName.setText(currentModel.getWorkoutPlanName());
        holder.textViewWorkoutDuration.setText(String.valueOf(currentModel.getWorkoutDuration()) + " minutes");
    }

    @Override
    public int getItemCount() {
        return workoutRecordArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewWorkoutPlanName, textViewWorkoutDuration;
        ConstraintLayout cl_workoutRecord;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewWorkoutPlanName = itemView.findViewById(R.id.textViewWorkoutPlanName);
            textViewWorkoutDuration = itemView.findViewById(R.id.textViewWorkoutDuration);
        }
    }
}
