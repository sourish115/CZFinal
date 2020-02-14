package com.sendbird.android.sample.dashboard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.sendbird.android.SendBird;
import com.sendbird.android.sample.R;
import com.sendbird.android.sample.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

class DashboardListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<PostsDataModel> possst;

    private Context mContext;

    private OnItemLongClickListener mItemLongClickListener;
    private OnItemClickListener mItemClickListener;

    interface OnItemClickListener {
        void onItemClick(PostsDataModel obj);
    }

    interface OnItemLongClickListener {
        void onItemLongClick(PostsDataModel obj);
    }

    DashboardListAdapter(Context context) {

        mContext = context;
        possst = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ChannelHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ChannelHolder) holder).bind(mContext, position, possst.get(position), mItemClickListener, mItemLongClickListener);
    }

    @Override
    public int getItemCount() {
        return possst.size();
    }

    void setGroupChannelList(List<PostsDataModel> channelList) {
        possst = channelList;
        notifyDataSetChanged();
    }

    void addLast(PostsDataModel str){
        possst.add(str);
        notifyItemInserted(possst.size());
    }

    public void load(){
        PostsDataModel temp = new PostsDataModel("Sourish","Hello people! Hope you all are doing well.","R.drawable.ic");
        try{
            possst.clear();
            possst.add(temp);
            possst.add(temp);
            Log.d("LIST:",possst.get(0).getName());
            Log.d("LIST:",possst.get(1).toString());
            notifyDataSetChanged();
        } catch(Exception e){
            //nothing to load
        }
    }

    /**
     * A ViewHolder that contains UI to display information about a GroupChannel.
     */
    private class ChannelHolder extends RecyclerView.ViewHolder {
        TextView post;
        ImageView img;
        TextView name;

        ChannelHolder(View itemView){
            super(itemView);
            post = itemView.findViewById(R.id.post);
            name = itemView.findViewById(R.id.name);
            img = itemView.findViewById(R.id.displaypic);
        }
        void bind(final Context context, int postition, final PostsDataModel exx, @Nullable final OnItemClickListener clickListener, @Nullable final OnItemLongClickListener longClickListener){
            post.setText(exx.getPost());
            name.setText(exx.getName());
            String profileUrl = SendBird.getCurrentUser() != null ? SendBird.getCurrentUser().getProfileUrl() : "";
            if (profileUrl.length() > 0) {
                ImageUtils.displayRoundImageFromUrl(context, profileUrl, img);
            }
            // Set an OnClickListener to this item.
            if (clickListener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onItemClick(exx);
                    }
                });
            }

            // Set an OnLongClickListener to this item.
            if (longClickListener != null) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        longClickListener.onItemLongClick(exx);

                        // return true if the callback consumed the long click
                        return true;
                    }
                });
            }
        }

    }

    void setOnItemClickListener(OnItemClickListener listener){
        mItemClickListener = listener;
    }

    void setOnItemLongClickLsitener(OnItemLongClickListener listener){
        mItemLongClickListener = listener;
    }

}