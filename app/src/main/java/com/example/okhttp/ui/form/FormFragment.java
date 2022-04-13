package com.example.okhttp.ui.form;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.okhttp.App;
import com.example.okhttp.R;
import com.example.okhttp.data.models.Post;
import com.example.okhttp.databinding.FragmentFormBinding;
import com.example.okhttp.ui.posts.PostsAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormFragment extends Fragment {

    private FragmentFormBinding binding;
    private NavController controller;
    private NavHostFragment navHostFragment;
    private int id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(getLayoutInflater(), container, false);
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        controller = navHostFragment.getNavController();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            Post post = (Post) getArguments().getSerializable("post");
            id = post.getId();
            binding.etTitle.setText(post.getTitle());
            binding.etDescription.setText(post.getContent());
            binding.etUserId.setText(String.valueOf(post.getId()));
        }
        binding.btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Post post = new Post(
                        binding.etTitle.getText().toString()
                        ,binding.etDescription.getText().toString(),
                        Integer.parseInt(binding.etUserId.getText().toString()),
                        35


                );

                if (getArguments() == null) {
                    App.api.createPost(post).enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            if (response.isSuccessful()) {
                                controller.popBackStack();
                            }
                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {
                            Log.e(TAG, "onFailure:  " + t.getLocalizedMessage());
                        }
                    });
                }else {
                    App.api.updatePost(id,post).enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            if (response.isSuccessful()) {
                                controller.popBackStack();
                            }
                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
}