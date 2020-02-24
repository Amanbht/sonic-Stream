package com.burmesesubtitle.app.adapters;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.burmesesubtitle.app.DetailsActivity;
import com.burmesesubtitle.app.R;
import com.burmesesubtitle.app.models.EpiModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DirectorApater extends RecyclerView.Adapter<DirectorApater.OriginalViewHolder> {

    private List<EpiModel> items = new ArrayList<>();
    private Context ctx;
    final DirectorApater.OriginalViewHolder[] viewHolderArray = {null};
    private DirectorApater.OnItemClickListener mOnItemClickListener;
    DirectorApater.OriginalViewHolder viewHolder;
    int i=0;
    private int seasonNo;

    public interface OnItemClickListener {
        void onItemClick(View view, EpiModel obj, int position, OriginalViewHolder holder);
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public DirectorApater(Context context, List<EpiModel> items,String name, int seasonNo) {
        ArrayList<EpiModel> arrayList=new ArrayList<>();
        for(int i=0;i<items.size();i++){
            if(items.get(i).getSeson().equals(name)){
                arrayList.add(items.get(i));
            }
        }

        items.clear();
        this.items = arrayList;
        this.seasonNo = seasonNo;

        ctx = context;
    }


    @Override
    public DirectorApater.OriginalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DirectorApater.OriginalViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_director_name, parent, false);
        vh = new DirectorApater.OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final DirectorApater.OriginalViewHolder holder, final int position) {

        final EpiModel obj = items.get(position);
        holder.name.setText(obj.getEpi());
        holder.size.setText(obj.getFileSize());
        holder.download_ep.setPaintFlags(holder.download_ep.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

//        Picasso.get().load(obj.getImageUrl()).placeholder(R.drawable.poster_placeholder)
//                .into(holder.episodIv);
//


//        if (seasonNo == 0) {
//            if (position==i){
//                chanColor(viewHolderArray[0],position);
//                ((DetailsActivity)ctx).setMediaUrlForTvSeries(obj.getStreamURL(), obj.getSeson(), obj.getEpi());
//                new DetailsActivity().iniMoviePlayer(obj.getStreamURL(),obj.getServerType(),ctx);
//                holder.name.setTextColor(ctx.getResources().getColor(R.color.colorPrimary));
//                holder.playStatusTv.setText("Playing");
//                holder.playStatusTv.setVisibility(View.VISIBLE);
//                viewHolderArray[0] =holder;
//                i = items.size()+items.size() + items.size();
//
//            }
//        }

        holder.download_ep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((DetailsActivity)ctx).setMediaUrlForTvSeries(obj.getStreamURL(), obj.getSeson(), obj.getEpi());
//                boolean castSession = ((DetailsActivity)ctx).getCastSession();
                //Toast.makeText(ctx, "cast:"+castSession, Toast.LENGTH_SHORT).show();
//                if (!castSession) {
//                    new DetailsActivity().iniMoviePlayer(obj.getStreamURL(),obj.getServerType(),ctx);
//                } else {
//                    ((DetailsActivity)ctx).showQueuePopup(ctx, holder.cardView, ((DetailsActivity)ctx).getMediaInfo());
//
//                }

//                chanColor(viewHolderArray[0],position);
//                holder.name.setTextColor(ctx.getResources().getColor(R.color.colorPrimary));
//                holder.playStatusTv.setText("Playing");
//                holder.playStatusTv.setVisibility(View.VISIBLE);
//


//                viewHolderArray[0] =holder;

                String url = obj.getStreamURL();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                ctx.startActivity(i);


            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView name,download_ep, size;
        public MaterialRippleLayout cardView;
        public ImageView episodIv;

        public OriginalViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            download_ep = v.findViewById(R.id.download_ep);
            size = v.findViewById(R.id.size);
            cardView=v.findViewById(R.id.lyt_parent);
//            episodIv=v.findViewById(R.id.image);
        }
    }

    private void chanColor(DirectorApater.OriginalViewHolder holder, int pos){

//        if (holder!=null){
//            holder.name.setTextColor(ctx.getResources().getColor(R.color.grey_20));
//            holder.playStatusTv.setVisibility(View.GONE);
//        }
    }


}