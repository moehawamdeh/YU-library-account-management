package jo.edu.yu.yarmouklibrary.view.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

public abstract class BaseRecyclerAdapter<T> extends Adapter<BaseHolder<T>> {
    private List<T>mItems=new ArrayList<>();
    @NonNull
    @Override
    public BaseHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(getResourceID(),parent,false);
        return getHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder<T> holder, int position) {
        holder.bind(mItems.get(position));
    }
    @Override
    public int getItemCount() {
        return mItems.size();
    }
    public void setItems(List<T>item){
        if(item!=null)
            mItems=new ArrayList<>(item);
        notifyDataSetChanged();
    }
    abstract protected BaseHolder<T> getHolder(View view);
    abstract protected int getResourceID();
}
