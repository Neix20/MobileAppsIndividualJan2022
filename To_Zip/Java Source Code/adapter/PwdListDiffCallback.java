package my.edu.utar.neixpasswordmanager.adapter;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import my.edu.utar.neixpasswordmanager.data.PasswordElem;

public class PwdListDiffCallback extends DiffUtil.Callback{
    private List<PasswordElem> oldList;
    private List<PasswordElem> newList;

    public PwdListDiffCallback(List<PasswordElem> oldList, List<PasswordElem> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return this.oldList.size();
    }

    @Override
    public int getNewListSize() {
        return this.newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        PasswordElem oldPwd = oldList.get(oldItemPosition);
        PasswordElem newPwd = newList.get(newItemPosition);
        return oldPwd.id == newPwd.id;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        PasswordElem oldPwd = oldList.get(oldItemPosition);
        PasswordElem newPwd = newList.get(newItemPosition);
        return oldPwd.equals(newPwd);
    }
}
