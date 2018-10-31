package projects.nyinyihtunlwin.freetime.events;

import java.util.List;

import projects.nyinyihtunlwin.freetime.data.vo.SearchResultVO;

/**
 * Created by Dell on 3/14/2018.
 */

public class SearchEvents {

    public static class ErrorInvokingAPIEvent {

        private String errorMsg;

        public ErrorInvokingAPIEvent(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getErrorMsg() {
            return errorMsg;
        }
    }

    public static class SearchResultsDataLoadedEvent {

        private int loadedPageIndex;
        private List<SearchResultVO> loadedSearchResults;

        public SearchResultsDataLoadedEvent(int loadedPageIndex, List<SearchResultVO> loadedSearchResults) {
            this.loadedPageIndex = loadedPageIndex;
            this.loadedSearchResults = loadedSearchResults;
        }

        public int getLoadedPageIndex() {
            return loadedPageIndex;
        }

        public void setLoadedPageIndex(int loadedPageIndex) {
            this.loadedPageIndex = loadedPageIndex;
        }

        public List<SearchResultVO> getLoadedSearchResults() {
            return loadedSearchResults;
        }

        public void setLoadedSearchResults(List<SearchResultVO> loadedSearchResults) {
            this.loadedSearchResults = loadedSearchResults;
        }
    }
}
