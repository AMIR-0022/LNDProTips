package com.company.lndprotips.Adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.lndprotips.QuestionModel.DbModel;
import com.company.lndprotips.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;

public class RecentQuizAdapter extends RecyclerView.Adapter<RecentQuizAdapter.ViewHolder> {

    ArrayList<DbModel> recentQuizList = new ArrayList<>();
    public RecentQuizAdapter(ArrayList<DbModel> recentQuizList) {
        this.recentQuizList = recentQuizList;
    }

    @NonNull
    @Override
    public RecentQuizAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(rootView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecentQuizAdapter.ViewHolder holder, int position) {

        holder.tvQuizTitle.setText(recentQuizList.get(position).getQuizTitle());
        holder.tvPercentage.setText(recentQuizList.get(position).getPercentage()+"%");
        holder.tvQuizProgress.setText("Your Progress is: "+recentQuizList.get(position).getCorrect() + "/" + recentQuizList.get(position).getTotalQuestion());

        // --------> set the progress indicator
        // Set Progress
        holder.circularProgressBar.setProgress(Float.parseFloat(recentQuizList.get(position).getPercentage()));
        // or with animation
        holder.circularProgressBar.setProgressWithAnimation(Float.parseFloat(recentQuizList.get(position).getPercentage()), 1000L); // =1s

        // Set Progress Max
        holder.circularProgressBar.setProgressMax(100f);

        // Set ProgressBar Color
        holder.circularProgressBar.setProgressBarColor(Color.BLACK);
        // or with gradient
        holder.circularProgressBar.setProgressBarColorStart(Color.parseColor("#D6FFFB"));
        holder.circularProgressBar.setProgressBarColorEnd(Color.parseColor("#05CAB6"));
        holder.circularProgressBar.setProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);

        // Set background ProgressBar Color
        holder.circularProgressBar.setBackgroundProgressBarColor(Color.parseColor("#D6FFFB"));
        // or with gradient
        holder.circularProgressBar.setBackgroundProgressBarColorStart(Color.parseColor("#D6FFFB"));
        holder.circularProgressBar.setBackgroundProgressBarColorEnd(Color.parseColor("#05CAB6"));
        holder.circularProgressBar.setBackgroundProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);

        // Set Width
        holder.circularProgressBar.setProgressBarWidth(7f); // in DP
        holder.circularProgressBar.setBackgroundProgressBarWidth(3f); // in DP

        // Other
        holder.circularProgressBar.setRoundBorder(true);
        holder.circularProgressBar.setStartAngle(180f);
        holder.circularProgressBar.setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);
    }

    @Override
    public int getItemCount() {
        return recentQuizList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuizTitle, tvQuizProgress, tvPercentage;
        CircularProgressBar circularProgressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuizTitle = itemView.findViewById(R.id.tvQuizTitle);
            tvQuizProgress = itemView.findViewById(R.id.tvProgress);
            tvPercentage = itemView.findViewById(R.id.tvPercentage);
            circularProgressBar = itemView.findViewById(R.id.recyclerCircularProgressBar);
        }
    }

}
