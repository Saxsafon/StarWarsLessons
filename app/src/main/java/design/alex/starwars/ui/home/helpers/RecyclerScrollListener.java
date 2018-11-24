package design.alex.starwars.ui.home.helpers;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class RecyclerScrollListener extends RecyclerView.OnScrollListener {

    public interface Listener {

        void fetchData(int page);
    }

    private Integer mTotalCount;
    private Integer mLastItem;
    private Integer mThreshold = 5;
    private Boolean mIsLoading = false;
    private Boolean mIsFullLoaded = false;

    private RecyclerView.LayoutManager mLayoutManager;

    private Listener mListener;

    public RecyclerScrollListener(RecyclerView.LayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (mLayoutManager == null) {
            return;
        }
        mTotalCount = mLayoutManager.getItemCount();
        mLastItem = ((LinearLayoutManager)mLayoutManager)
                .findLastVisibleItemPosition();
        if (!mIsLoading && mTotalCount < (mLastItem + mThreshold) && !mIsFullLoaded) {
            if (mListener != null) {
                mListener.fetchData((mTotalCount / 10) + 1);
            }
        }
    }

    public void setLoading(Boolean loading) {
        mIsLoading = loading;
    }

    public void setFullLoaded(Boolean fullLoaded) {
        mIsFullLoaded = fullLoaded;
    }
}
