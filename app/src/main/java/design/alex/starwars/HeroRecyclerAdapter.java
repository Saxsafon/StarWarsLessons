package design.alex.starwars;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import design.alex.starwars.model.entity.People;
import design.alex.starwars.model.rest.RawPeople;

public class HeroRecyclerAdapter
        extends
        RecyclerView.Adapter {

    public interface Listener {

        void onClickPeople(People people);
    }

    private static final int VIEW_PROGRESS = 1;
    private static final int VIEW_ITEM = 2;

    private List<People> mPeoples = new ArrayList<>();
    private Listener mListener;

    public void addAll(List<People> peoples) {
        mPeoples.addAll(peoples);
        notifyDataSetChanged();
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public void showProgress() {
        if (mPeoples.isEmpty() || !(mPeoples.get(getItemCount() - 1) instanceof People.Empty)) {
            mPeoples.add(new People.Empty());
            notifyItemInserted(getItemCount() - 1);
        }
    }

    public void hideProgress() {
        if (!mPeoples.isEmpty() && mPeoples.get(getItemCount() - 1) instanceof People.Empty) {
            mPeoples.remove(getItemCount() - 1);
            notifyItemRemoved(getItemCount());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_card_wrapper, viewGroup, false);
            return new HeroViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_progress, viewGroup, false);
            return new ProgressViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mPeoples.get(position) instanceof People.Empty) {
            return VIEW_PROGRESS;
        } else {
            return VIEW_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof HeroViewHolder) {
            ((HeroViewHolder)viewHolder).setPosition(position);
            People people = mPeoples.get(position);
            ((HeroViewHolder)viewHolder).bind(people);
        }
    }

    @Override
    public int getItemCount() {
        return mPeoples.size();
    }



    public class HeroViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mHeroNameTextView;
        private LinearLayout mContainer;
        private int mPosition;

        HeroViewHolder(@NonNull View itemView) {
            super(itemView);
            mContainer = itemView.findViewById(R.id.container);
            mHeroNameTextView = itemView.findViewById(R.id.person_name_text_view);
            mContainer.setOnClickListener(this);
        }

        void setPosition(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onClickPeople(mPeoples.get(mPosition));
            }
        }

        public void bind(People people) {
            mHeroNameTextView.setText(people.getName());
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        public ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
