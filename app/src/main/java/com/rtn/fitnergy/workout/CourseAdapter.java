package com.rtn.fitnergy.workout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rtn.fitnergy.R;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter{

    private ClickListener listener;
    private ArrayList<Course> courseList;
    private ClickListener onClickListener;

    public CourseAdapter(ArrayList<Course> courseList, ClickListener clickListener){
        this.courseList = courseList;
        this.onClickListener = clickListener;
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView courseName, courseDesc;
        private Button btn;
        ClickListener clickListener;

        public CourseViewHolder(final View view, ClickListener listener){
            super(view);

            courseName = (TextView) view.findViewById(R.id.tvCourse_name);
            courseDesc = (TextView) view.findViewById(R.id.tvCourse_desc);
            btn = (Button) view.findViewById(R.id.btn_Start);
            this.clickListener = listener;

            btn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onPosClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list, parent, false);
        return new CourseViewHolder(itemView, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        String name = courseList.get(position).getName();
        String desc = courseList.get(position).getDesc();
        CourseViewHolder viewHolder = (CourseViewHolder)holder;
        viewHolder.courseName.setText(name);
        viewHolder.courseDesc.setText(desc);
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public interface ClickListener {
        void onPosClick(int position);
    }
}

