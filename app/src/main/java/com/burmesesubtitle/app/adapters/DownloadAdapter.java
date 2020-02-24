package com.burmesesubtitle.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.burmesesubtitle.app.R;
import com.burmesesubtitle.app.models.CommonModels;

import java.util.ArrayList;
import java.util.List;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.OriginalViewHolder> {

    private List<CommonModels> items = new ArrayList<>();
    private Context ctx;

    private ServerApater.OnItemClickListener mOnItemClickListener;

    private DownloadAdapter.OriginalViewHolder viewHolder;



    public DownloadAdapter(Context context, List<CommonModels> items) {
        this.items = items;
        ctx = context;
    }


    @Override
    public DownloadAdapter.OriginalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DownloadAdapter.OriginalViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_download_item, parent, false);
        vh = new DownloadAdapter.OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final DownloadAdapter.OriginalViewHolder holder, final int position) {

        final CommonModels obj = items.get(position);
//        Log.d("OBJ", obj.getFileSize());
        holder.name.setText(obj.getTitle());
        holder.size.setText(obj.getFileSize());

        holder.download.setPaintFlags(holder.download.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String url = obj.getStremURL();
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

        public TextView name, download, size;
        public LinearLayout itemLayout;

        public OriginalViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            download = v.findViewById(R.id.download);
            size = v.findViewById(R.id.size_tv);
            itemLayout=v.findViewById(R.id.item_layout);
        }
    }

}