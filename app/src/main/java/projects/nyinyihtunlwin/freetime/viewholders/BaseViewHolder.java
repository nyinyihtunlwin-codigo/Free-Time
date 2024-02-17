package projects.nyinyihtunlwin.freetime.viewholders;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Nyi Nyi Htun Lwin on 12/6/2017.
 */

public abstract class BaseViewHolder<W> extends RecyclerView.ViewHolder implements View.OnClickListener {

    private W mData;

    public BaseViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    public abstract void setData(W mData);

}
