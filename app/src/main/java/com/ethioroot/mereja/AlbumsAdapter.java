package com.ethioroot.mereja;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Album> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public AlbumsAdapter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Album album = albumList.get(position);
        holder.title.setText(album.getName());
        holder.count.setText(album.getgeners());

        // loading album cover using Glide library

        Picasso.with(mContext).load(album.getThumbnail()).fit().centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.thumbnail);

        /* Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);
         */
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail=new Intent(mContext,WatchActivity.class);
                detail.putExtra("title",album.getName());
                detail.putExtra("detail",album.getShortDescription());
                detail.putExtra("detail",album.getLongDescription());
                detail.putExtra("id",album.getMovieid());
                detail.putExtra("vidId",album.getVideo());
                detail.putExtra("thumb_small",album.getThumbnail());
                detail.putExtra("category",album.getgeners());
                mContext.startActivity(detail);
            }
        });


    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            final Album album = albumList.get(0);
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Intent detail=new Intent(mContext,WatchActivity.class);
                    detail.putExtra("title",album.getName());
                    detail.putExtra("detail",album.getShortDescription());
                    detail.putExtra("detail",album.getLongDescription());
                    detail.putExtra("id",album.getMovieid());
                    detail.putExtra("vidId",album.getVideo());
                    detail.putExtra("thumb_small",album.getThumbnail());
                    detail.putExtra("category",album.getgeners());
                    mContext.startActivity(detail);
                    Toast.makeText(mContext, "Play Video", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.action_download:
                  AlertDialog.Builder builder1=new AlertDialog.Builder(mContext);
                  builder1.setCancelable(true);
                  builder1.setPositiveButton("Get Points", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          dialog.cancel();
                          Intent adsactivity=new Intent(mContext,AdsActivity.class);
                          mContext.startActivity(adsactivity);
                      }
                  });
                  builder1.setNegativeButton("My Points", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          //
                          Intent profile=new Intent(mContext,ProfileActivity.class);
                          mContext.startActivity(profile);
                          dialog.cancel();
                      }
                  });
                  AlertDialog alertDialog=builder1.create();
                  alertDialog.setTitle("Get 5,000 Point to Download Video. You can Watch Ads to Collect your Points");

                  alertDialog.show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
