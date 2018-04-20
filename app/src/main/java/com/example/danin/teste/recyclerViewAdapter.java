package com.example.danin.teste;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

import java.util.ArrayList;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.ViewHolder>{


    @NonNull

    //vars
    //private ArrayList<String> mNames = new ArrayList<>();
   // private ArrayList<String> mImagesURL = new ArrayList<>();
    private Context mContext;
    private List<Movie> mData;

    public recyclerViewAdapter(Context mContext, List<Movie> mData) {
        this.mData=mData;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.card_movie,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       // holder.name.setText(mNames.get(position));
        //holder.image.setImageDrawable(mImagesURL.get(position));
        holder.tv_movie_title.setText(mData.get(position).getTitle());
        new DownloadImageTask(holder.img_movie_poster).execute(mData.get(position).getPoster());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_movie_title;
        ImageView img_movie_poster;
        //ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
           // image= itemView.findViewById(R.id.image);
            //name = itemView.findViewById(R.id.name);
            tv_movie_title=(TextView) itemView.findViewById(R.id.movie_title_id);
            img_movie_poster=(ImageView) itemView.findViewById(R.id.movie_poster_id);
        }

       // TextView name;




    }

private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}


}
