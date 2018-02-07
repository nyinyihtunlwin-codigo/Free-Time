package projects.nyinyihtunlwin.zcar.viewholders;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.zcar.R;
import projects.nyinyihtunlwin.zcar.data.vo.GenreVO;

/**
 * Created by Dell on 2/6/2018.
 */

public class GenreViewHolder extends BaseViewHolder<GenreVO> {

    @BindView(R.id.tv_genre_name)
    TextView tvGenreName;

    public GenreViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(GenreVO mData) {
        tvGenreName.setText(mData.getGenreName());
    }

    @Override
    public void onClick(View view) {

    }
}
