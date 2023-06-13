package com.company.lndprotips;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.company.lndprotips.Adapters.RecentQuizAdapter;
import com.company.lndprotips.DbHelper.DbHelper;
import com.company.lndprotips.QuestionContent.PracticeSetActivity;
import com.company.lndprotips.QuestionContent.RecentQuizActivity;
import com.company.lndprotips.QuestionModel.DbModel;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 1;
    Boolean exitFlag = false;
    CardView cardMathQuiz, cardEnglishQuiz, cardUrduQuiz;
    TextView seeAllRecentQuiz;

    DbHelper dbHelper;
    RecentQuizAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<DbModel> recentQuizList = new ArrayList<>();

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set custom tool-Bar
        Toolbar toolbar = findViewById(R.id.mainToolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("");
        }

        // find all the values of ids
        cardMathQuiz  = findViewById(R.id.cardMathQuiz);
        cardEnglishQuiz = findViewById(R.id.cardEnglishQuiz);
        cardUrduQuiz = findViewById(R.id.cardUrduQuiz);
        seeAllRecentQuiz = findViewById(R.id.seeAllRecentQuiz);
        recyclerView = findViewById(R.id.recyclerView);
        dbHelper = DbHelper.getInstance(MainActivity.this);

        // click listener of all the categories of quiz
        cardMathQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuizActivity("math");
            }
        });
        cardEnglishQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuizActivity("english");
            }
        });
        cardUrduQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuizActivity("urdu");
            }
        });

        // see all the recent Quiz
        seeAllRecentQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecentQuizActivity.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
            }
        });

        // get data from table and set to recycler view
        recentQuizList = dbHelper.fetchRecentQuiz();
        adapter = new RecentQuizAdapter(recentQuizList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        // call method for swipe to delete item
        swipeToDelete();

    }

    // next quiz activity
    void nextQuizActivity(String quizCategory){
        Intent intent = new Intent(MainActivity.this, PracticeSetActivity.class);
        intent.putExtra("quizType", quizCategory);
        startActivity(intent);
    }

    // set the main-menu of app-bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    // set the click-listener of menu option items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.option_share) {
            shareAppLink();
        } else if (id == R.id.option_rate) {
            rateOurApp();
        } else if (id == R.id.option_more) {
            moreOurApp();
        } else {
            showAlertDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    // -----> Back Button Showing Dialog
    @Override
    public void onBackPressed() {
        showAlertDialog();
        if (exitFlag) {
            super.onBackPressed();
        }
    }

    // -----> Exit confirmation dialog
    private void showAlertDialog() {
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setCancelable(false)
                .setTitle("Exit")
                .setMessage("Are you sure want to exit?")
                .setIcon(R.drawable.baseline_error_24)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exitFlag = false;
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exitFlag = true;
                        finish();
                    }
                })
                .create();
        dialog.show();
    }

    // Option --> share app
    private void shareAppLink() {
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String body = "https://play.google.com/store/apps/details?id=" + getPackageName();
        String sub = "Your Subject";
        myIntent.putExtra(Intent.EXTRA_SUBJECT, sub);
        myIntent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(myIntent, "Share Using"));
    }

    // Option --> Rate our app
    private void rateOurApp() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    // Option --> More app
    private void moreOurApp() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://search?q=pub:" + "Codingkey")));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/developer?id=Codingkey")));
        }
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
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
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
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this , R.color.light_red))
                        .addSwipeLeftCornerRadius(TypedValue.COMPLEX_UNIT_DIP, 10)
                        .addSwipeLeftActionIcon(R.drawable.baseline_delete_24)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recyclerView);
    }

    // delete recent quiz recycler item
    private void deleteItem(int position) {
        // Remove item from the SQLite database
        dbHelper.deleteRecentQuiz(recentQuizList.get(position).getId());

        // Remove item from the list
        recentQuizList.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, recentQuizList.size() - position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null && data.hasExtra("deletedPosition")) {
                int deletedPosition = data.getIntExtra("deletedPosition", -1);
                if (deletedPosition != -1) {
                    // Update the list and notify the adapter of the deletion
                    recentQuizList.remove(deletedPosition);
                    adapter.notifyItemRemoved(deletedPosition);
                }
            }
        }
    }

}