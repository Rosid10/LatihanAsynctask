package com.rosid.androidasynctask;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class asynctaskAdapter extends RecyclerView.Adapter<asynctaskAdapter.UsserViewHolder> {

    private ArrayList<usserAsynctask> dataList;

    public asynctaskAdapter(ArrayList<usserAsynctask> dataList) {
        this.dataList = dataList;
    }

    @Override

    public UsserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_view, parent, false);
        return new UsserViewHolder(view);
    }

    @Override

    public void onBindViewHolder(UsserViewHolder holder, int position) {
        holder.txtID.setText(dataList.get(position).getId());
        holder.txtNama.setText(dataList.get(position).getNama());
        holder.txtusername.setText(dataList.get(position).getUsername());
        holder.txtEmail.setText(dataList.get(position).getEmail());
        holder.txtAddr.setText(dataList.get(position).getAddr());

    }
    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class UsserViewHolder extends RecyclerView.ViewHolder{
        private TextView txtID, txtNama, txtusername, txtEmail, txtAddr;

        public UsserViewHolder(View itemView) {
            super(itemView);
            txtID = (TextView) itemView.findViewById(R.id.textID);
            txtNama = (TextView) itemView.findViewById(R.id.textNama);
            txtusername = (TextView) itemView.findViewById(R.id.textUsername);
            txtEmail = (TextView) itemView.findViewById(R.id.textEmail);
            txtAddr = (TextView) itemView.findViewById(R.id.textAddr);
        }
    }
}
