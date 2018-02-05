package projects.nyinyihtunlwin.zcar.network.responses.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import projects.nyinyihtunlwin.zcar.data.vo.GenreVO;
import projects.nyinyihtunlwin.zcar.network.responses.BaseResponse;

/**
 * Created by Dell on 2/5/2018.
 */

public class MovieGenresResponse extends BaseResponse {

    @SerializedName("genres")
    private List<GenreVO> genres;

    public MovieGenresResponse(List<GenreVO> genres) {
        this.genres = genres;
    }

    public List<GenreVO> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreVO> genres) {
        this.genres = genres;
    }
}
