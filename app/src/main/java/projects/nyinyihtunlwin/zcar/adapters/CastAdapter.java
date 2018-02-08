package projects.nyinyihtunlwin.zcar.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.data.vo.CastVO;
import projects.nyinyihtunlwin.zcar.viewholders.CastViewHolder;

/**
 * Created by Dell on 2/8/2018.
 */

public class CastAdapter extends BaseRecyclerAdapter<CastViewHolder, CastVO> {
    public CastAdapter(Context context) {
        super(context);
    }

    @Override
    public CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_item_cast, parent, false);
        return new CastViewHolder(view);
    }
}
