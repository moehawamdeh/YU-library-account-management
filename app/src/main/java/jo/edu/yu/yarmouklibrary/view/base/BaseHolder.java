package jo.edu.yu.yarmouklibrary.view.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {
    public BaseHolder(@NonNull View itemView) {
        super(itemView);
    }
    public abstract void bind(T item);
}
