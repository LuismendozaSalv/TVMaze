package com.mendosal.tvmaze.presentation.ui.favorite;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mendosal.tvmaze.R;
import com.mendosal.tvmaze.presentation.ui.show.MyShowRecyclerViewAdapter;
import com.mendosal.tvmaze.presentation.ui.show.ShowFragmentDirections;
import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;
import com.mendosal.tvmaze.retrofit.network.Resource;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavoriteFragment extends Fragment implements MyShowRecyclerViewAdapter.OnShowListener{
    private int mColumnCount = 2;
    private View view;
    private FavoriteViewModel favoriteViewModel;
    private MyShowRecyclerViewAdapter adapter;
    private RecyclerView rvFavoritesShows;
    private List<ShowEntity> favoriteShowList;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoriteViewModel = new ViewModelProvider(requireActivity()).get(FavoriteViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorite, container, false);
        Context context = view.getContext();
        rvFavoritesShows = view.findViewById(R.id.rvShows);
        if (mColumnCount <= 1) {
            rvFavoritesShows.setLayoutManager(new LinearLayoutManager(context));
        } else {
            rvFavoritesShows.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        adapter = new MyShowRecyclerViewAdapter(getActivity(), favoriteShowList, this);
        rvFavoritesShows.setAdapter(adapter);
        loadFavoritesShows();
        return view;
    }

    private void loadFavoritesShows() {
        favoriteViewModel.getFavoriteShows().observe(requireActivity(), new Observer<Resource<List<ShowEntity>>>() {
            @Override
            public void onChanged(Resource<List<ShowEntity>> listResource) {
                adapter.setShowList(listResource.data);
            }
        });
    }

    @Override
    public void onShowClick(int position) {
        FavoriteFragmentDirections.ActionFavoriteFragmentToShowDetailFragment action =
                FavoriteFragmentDirections.actionFavoriteFragmentToShowDetailFragment();
        ShowEntity selectedShow = adapter.getCurrentList().get(position);
        action.setShowId(selectedShow.getId());
        Navigation.findNavController(view).navigate(action);
    }
}