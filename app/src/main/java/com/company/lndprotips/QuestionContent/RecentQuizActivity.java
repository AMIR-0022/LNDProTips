package com.company.lndprotips.QuestionContent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;

import com.company.lndprotips.Adapters.RecentQuizAdapter;
import com.company.lndprotips.DbHelper.DbHelper;
import com.company.lndprotips.MainActivity;
import com.company.lndprotips.QuestionModel.DbModel;
import com.company.lndprotips.R;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class RecentQuizActivity extends AppCompatActivity {

    DbHelper dbHelper;
    RecentQuizAdapter adapter;
    RecyclerView recentQuizRecyclerView;
    ArrayList<DbModel> recentQuizList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_quiz);

        // set tool bar and its back option
        Toolbar toolbar = findViewById(R.id.recentQuizToolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("All Recent Quiz");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recentQuizRecyclerView = findViewById(R.id.recentQuizRecyclerView);
        dbHelper = DbHelper.getInstance(RecentQuizActivity.this);

        // get data from table and set to recycler view
        recentQuizList = dbHelper.fetchRecentQuiz();
        adapter = new RecentQuizAdapter(recentQuizList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RecentQuizActivity.this, LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setStackFromEnd(true);
        recentQuizRecyclerView.setLayoutManager(linearLayoutManager);
        recentQuizRecyclerView.setItemAnimator(new DefaultItemAnimator());
        recentQuizRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recentQuizRecyclerView.setAdapter(adapter);

        // call method for swipe to delete item
        swipeToDelete();

    }

    // click on back option of tool bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    // to swipe item of recycler view to delete
    void swipeToDelete(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAbsoluteAdapterPosition();
                if (direction == ItemTouchHelper.LEFT){
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RecentQuizActivity.this);
                    builder.setCancelable(false);
                    builder.setTitle("Delete Quiz Result");
                    builder.setMessage("Are You Sure Delete ?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteItem(position);
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adapter.notifyItemChanged(position);
                        }
                    });
                    android.app.AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                float swipeThreshold = 0.3f; // Percentage of the item's width to show the swipe
                float itemWidth = viewHolder.itemView.getWidth();
                float threshold = itemWidth * swipeThreshold;

                if (dX > threshold) {
                    dX = threshold;
                } else if (dX < -threshold) {
                    dX = -threshold;
                }
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(RecentQuizActivity.this , R.color.light_red))
                        .addSwipeLeftCornerRadius(TypedValue.COMPLEX_UNIT_DIP, 10)
                        .addSwipeLeftActionIcon(R.drawable.baseline_delete_24)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recentQuizRecyclerView);
    }

    // delete recent quiz recycler item
    private void deleteItem(int position) {
        // Remove item from the SQLite database
        dbHelper.deleteRecentQuiz(recentQuizList.get(position).getId());

        // Remove item from the list
        recentQuizList.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, recentQuizList.size() - position);

        // Set the result code and pass any necessary data
        Intent resultIntent = new Intent();
        resultIntent.putExtra("deletedPosition", position);
        setResult(Activity.RESULT_OK, resultIntent);

        // Finish the SecondActivity
//        finish();
    }

}