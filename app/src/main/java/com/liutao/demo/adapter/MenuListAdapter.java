package com.liutao.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liutao.demo.R;
import com.liutao.demo.bean.MenuItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MenuListViewHolder> {

    private List<MenuItem> mMenuList = new ArrayList<>();

    public MenuListAdapter(List<MenuItem> list){
        this.mMenuList = list;
    }

    @NonNull
    @Override
    public MenuListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_menu,parent,false);
        MenuListViewHolder viewHolder = new MenuListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuListViewHolder holder, int position) {
        final MenuItem item = mMenuList.get(position);
        holder.tv_name.setText(item.getName());
        holder.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener !=null){
                    mOnItemClickListener.onItemClick(v,item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mMenuList != null){
            return mMenuList.size();
        }
        return 0;
    }

    public class MenuListViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name;
        View itemview;

        public MenuListViewHolder(View view){
            super(view);
            this.itemview = view;
            tv_name = (TextView)view.findViewById(R.id.tv_menuname) ;
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View view,MenuItem item);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }
}
