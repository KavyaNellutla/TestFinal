package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.PostsItemClickListener;
import com.example.myapplication.PostsModel;
import com.example.myapplication.R;
import com.example.myapplication.Utils.AppUtils;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MyViewHolder> {
    private List<PostsModel>postsModelList;
    private PostsItemClickListener postsItemClickListener;
    private int selectedcount=0;
    public PostsAdapter(List<PostsModel> postsModelList, PostsItemClickListener postsItemClickListener) {
        this.postsModelList=postsModelList;
        this.postsItemClickListener=postsItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.title.setText(postsModelList.get(position).getTitle());
        holder.createdat.setText(AppUtils.getFormattedDate(postsModelList.get(position).getCreated_at()));
        holder.switch_select.setChecked(postsModelList.get(position).isSwitchChecked());
        holder.switch_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    selectedcount++;
                    postsModelList.get(position).setSwitchChecked(true);
                }else{
                    selectedcount--;
                    postsModelList.get(position).setSwitchChecked(false);
                }
                postsItemClickListener.OnitemClick(position,selectedcount);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.switch_select.performClick();
            }
        });

    }

    @Override
    public int getItemViewType(int position){
        return position;
    }

    @Override
    public int getItemCount() {
        return postsModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView createdat;
        private Switch switch_select;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title_name);
            createdat=itemView.findViewById(R.id.created);
            switch_select=itemView.findViewById(R.id.switch_select);
        }
    }
}
