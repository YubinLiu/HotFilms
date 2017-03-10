package com.example.hotfilm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotfilm.R;
import com.example.hotfilm.activity.DetailActivity;
import com.example.hotfilm.activity.MainActivity;
import com.example.hotfilm.bean.Film;
import com.example.hotfilm.util.ShowToastUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.hotfilm.activity.MainActivity.screenWidth;

/**
 * Created by yubin on 2017/2/15.
 */

public class FilmAdapter
        extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {

    private List<Film> mPosterUrlList;

    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {

        View posterView;

        ImageView posterImage;

        TextView mainFilmName;

        TextView mainVoteAverage;

        public ViewHolder(View view) {
            super(view);
            posterView = view;
            posterImage = (ImageView) view.findViewById(R.id.poster_film_img);
            mainFilmName = (TextView) view.findViewById(R.id.main_film_name);
            mainVoteAverage = (TextView) view.findViewById(R.id.main_vote_average);
        }
    }

    public FilmAdapter(Context context, List<Film> posterUrlList) {
        mContext = context;
        mPosterUrlList = posterUrlList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.film_poster_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        fitPosterSize(holder);
        holder.posterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Film film = MainActivity.filmList.get(position);
                DetailActivity.actionStart(mContext, film);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(mContext)
                .load(mPosterUrlList.get(position).getSmallPosterUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.posterImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        ShowToastUtil.showToast(R.string.dataLoadSucceed);
                    }

                    @Override
                    public void onError() {
                        ShowToastUtil.showToast(R.string.dataLoadFailed);
                    }
                });
        holder.mainFilmName.setText("影片: " + mPosterUrlList.get(position).getTitle());
        holder.mainVoteAverage.setText("评分: " + mPosterUrlList.get(position).getVoteAverage()+ " / 10");
    }

    @Override
    public int getItemCount() {
        return mPosterUrlList.size();
    }


    private void fitPosterSize(ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.posterImage.getLayoutParams();
        lp.width = MainActivity.screenWidth / 2;
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        holder.posterImage.setLayoutParams(lp);
        holder.posterImage.setMaxWidth(screenWidth / 2);
        holder.posterImage.setMaxHeight((int) ((screenWidth / 2) * 1.4));
    }
}
