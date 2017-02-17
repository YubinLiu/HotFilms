package com.example.hotfilm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView filmTitle;
    private ImageView filmImg;
    private TextView filmReleaseDate;
    private TextView filmVoteAverage;
    private TextView filmOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //初始化控件
        initViews();

        FilmDetail filmDetail = getIntent().getParcelableExtra("film_detail_data");
        filmTitle.setText(filmDetail.getTitle());
        Picasso.with(this).load(filmDetail.getPosterUrl()).into(filmImg);
        filmReleaseDate.setText("上映时间:\n" + filmDetail.getRealseDate());
        filmVoteAverage.setText("评分:\n" + filmDetail.getVoteAverage() + " / 10");
        filmOverview.setText("主要剧情:\n" + filmDetail.getOverview());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    //该活动启动所需要的数据
    public static void actionStart(Context context, FilmDetail filmDetail) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("film_detail_data", filmDetail);
        context.startActivity(intent);
    }

    private void initViews() {
        filmTitle = (TextView) findViewById(R.id.film_title);
        filmImg = (ImageView) findViewById(R.id.film_img);
        filmReleaseDate = (TextView) findViewById(R.id.film_release_date);
        filmVoteAverage = (TextView) findViewById(R.id.film_vote_average);
        filmOverview = (TextView) findViewById(R.id.film_overview);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
