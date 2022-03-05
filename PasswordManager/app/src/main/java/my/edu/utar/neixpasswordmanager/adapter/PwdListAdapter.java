package my.edu.utar.neixpasswordmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import my.edu.utar.neixpasswordmanager.R;
import my.edu.utar.neixpasswordmanager.data.PasswordElem;

public class PwdListAdapter extends RecyclerView.Adapter<PwdListAdapter.PwdViewHolder>{

    private List<PasswordElem> mPwdList;
    private LayoutInflater mInflater;

    public PwdListAdapter(Context mContext) {
        this.mPwdList = new ArrayList<PasswordElem>();
        this.mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public PwdListAdapter.PwdViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View mItemView = mInflater.inflate(R.layout.pwd_list_item, parent, false);
        return new PwdViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(PwdListAdapter.PwdViewHolder viewHolder, int position) {
        PasswordElem curPwd = mPwdList.get(position);

        // Bind Password Element to RecycleView List Item
        viewHolder.title_item_txt.setText(curPwd.title);
        viewHolder.name_item_txt.setText(curPwd.username);
        viewHolder.website_item_txt.setText(curPwd.website);
    }

    @Override
    public int getItemCount() {
        return mPwdList.size();
    }

    public void updateList(List<PasswordElem> pwdList) {
        PwdListDiffCallback diffCallback = new PwdListDiffCallback(this.mPwdList, pwdList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.mPwdList.clear();
        this.mPwdList.addAll(pwdList);
        diffResult.dispatchUpdatesTo(this);
    }

    class PwdViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        TextView title_item_txt;
        TextView name_item_txt;
        TextView website_item_txt;

        PwdListAdapter mAdapter;

        public PwdViewHolder(View itemView, PwdListAdapter mAdapter) {
            super(itemView);
            title_item_txt = itemView.findViewById(R.id.title_item_txt);
            name_item_txt = itemView.findViewById(R.id.name_item_txt);
            website_item_txt = itemView.findViewById(R.id.website_item_txt);
            this.mAdapter = mAdapter;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();

            // Spawn Edit page Activity
            Toast.makeText(view.getContext(), mPosition + "", Toast.LENGTH_SHORT).show();

            // Notify the adapter that the data has changed so it can
            // update the RecyclerView to display the data.
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public boolean onLongClick(View view) {
            // Delete
            return false;
        }
    }
}
