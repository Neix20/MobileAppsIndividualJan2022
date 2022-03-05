package my.edu.utar.neixpasswordmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import my.edu.utar.neixpasswordmanager.R;
import my.edu.utar.neixpasswordmanager.data.PasswordElem;
import my.edu.utar.neixpasswordmanager.ui.EditPwdActivity;
import my.edu.utar.neixpasswordmanager.ui.PwdListFragment;

public class PwdListAdapter extends RecyclerView.Adapter<PwdListAdapter.PwdViewHolder> implements Filterable {

    public static final String TAG = PwdListAdapter.class.getSimpleName();

    private List<PasswordElem> mPwdList;
    private List<PasswordElem> mPwdListAll;
    private LayoutInflater mInflater;
    private String[] colors;

    public PwdListAdapter(Context mContext) {
        this.mPwdList = new ArrayList<PasswordElem>();
        this.mPwdListAll = new ArrayList<PasswordElem>();
        this.mInflater = LayoutInflater.from(mContext);
        this.colors = mContext.getResources().getStringArray(R.array.icon_colors);
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

        // Update Icon TextView
        int f_val = (int) curPwd.title.toUpperCase().charAt(0);
        int ind = (int) ((f_val - 65) / 26.0 * colors.length);

        viewHolder.icon_item_txt.getBackground().setColorFilter(Color.parseColor(colors[ind]), PorterDuff.Mode.SRC_ATOP);
        viewHolder.icon_item_txt.setText(curPwd.title.substring(0, 1).toUpperCase());

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

        this.mPwdListAll.clear();
        this.mPwdListAll.addAll(pwdList);

        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public Filter getFilter() {
        return Searched_Filter;
    }

    private Filter Searched_Filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String charString = charSequence.toString();
            List<PasswordElem> filteredList = new ArrayList<>();
            if (charString.isEmpty()) {
                filteredList.addAll(mPwdListAll);
            } else {
                for (PasswordElem pwd : mPwdListAll) {
                    if (pwd.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                        filteredList.add(pwd);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mPwdList.clear();
            mPwdList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    class PwdViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{
        TextView icon_item_txt;
        TextView title_item_txt;
        TextView name_item_txt;
        TextView website_item_txt;

        PwdListAdapter mAdapter;

        public PwdViewHolder(View itemView, PwdListAdapter mAdapter) {
            super(itemView);
            icon_item_txt = itemView.findViewById(R.id.icon_item_txt);
            title_item_txt = itemView.findViewById(R.id.title_item_txt);
            name_item_txt = itemView.findViewById(R.id.name_item_txt);
            website_item_txt = itemView.findViewById(R.id.website_item_txt);

            this.mAdapter = mAdapter;

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onClick(View view) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();

            PasswordElem pwd = mPwdList.get(mPosition);

            // Spawn Edit page Activity
            Intent intent = new Intent(view.getContext(), EditPwdActivity.class);
            intent.putExtra("id", pwd.getId());
            view.getContext().startActivity(intent);
        }


        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            int pos = getAdapterPosition();
            PasswordElem pwd = mPwdList.get(pos);
            MenuItem delete = contextMenu.add(Menu.NONE, 1, (int) pwd.getId(), "Delete"); // groupId, itemId, order, title
        }
    }
}
