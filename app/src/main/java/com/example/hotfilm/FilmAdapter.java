package com.example.hotfilm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.hotfilm.MainActivity.screenWidth;

/**
 * Created by yubin on 2017/2/15.
 */

public class FilmAdapter
        extends RecyclerView.Adapter<FilmAdapter.ViewHolder>{

    private List<String> mPosterUrlList;

    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{

        View posterView;

        ImageView posterImage;

        public ViewHolder(View view) {
            super(view);
            posterView = view;
            posterImage = (ImageView) view.findViewById(R.id.poster_film_img);
        }
    }

    public FilmAdapter(Context context, List<String> posterUrlList) {
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
                FilmDetail filmDetail = MainActivity.filmDetailList.get(position);
                DetailActivity.actionStart(mContext, filmDetail);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(mContext)
                .load(mPosterUrlList.get(position))
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.posterImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(mContext, "数据加载成功！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(mContext, "数据加载失败，请刷新重试！", Toast.LENGTH_SHORT).show();
                    }
                });
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
        holder.posterImage.setMaxHeight((int)((screenWidth / 2) * 1.4));
    }
}
