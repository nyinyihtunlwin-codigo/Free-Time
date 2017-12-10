package projects.nyinyihtunlwin.zcar.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Dell on 12/6/2017.
 */

public abstract class BaseViewHolder<W> extends RecyclerView.ViewHolder implements View.OnClickListener {

    private W mData;

    public BaseViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    public abstract void setData(W mData);

}
