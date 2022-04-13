package com.example.okhttp.ui.posts;

import static android.service.controls.ControlsProviderService.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.okhttp.App;
import com.example.okhttp.R;
import com.example.okhttp.data.models.Post;
import com.example.okhttp.databinding.FragmentPostBinding;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFragment extends Fragment {

    private FragmentPostBinding binding;
    private NavController controller;
    private NavHostFragment navHostFragment;
    private PostsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PostsAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPostBinding.inflate(getLayoutInflater(), container, false);
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        controller = navHostFragment.getNavController();
        binding.progressBar.setVisibility(View.VISIBLE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fab.setOnClickListener(view1 -> {
            controller.navigate(R.id.action_postFragment_to_formFragment);
        });
        binding.recycler.setAdapter(adapter);

        App.api.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setPosts(response.body());
                    binding.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });

        adapter.setOnLongClick(new PostsAdapter.OnClick() {
            @Override
            public void onLongClick(Post post, int position) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Удаление")
                        .setMessage("Вы точно хотите удалить этот запись?")
                        .setNegativeButton("Нет", null)
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                App.api.deletePost(post.getId()).enqueue(new Callback<Post>() {
                                    @Override
                                    public void onResponse(Call<Post> call, Response<Post> response) {
                                        if (response.isSuccessful()) {
                                            adapter.deletePost(post, position);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Post> call, Throwable t) {
                                        Toast.makeText(requireContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).show();

            }

            @Override
            public void onItemClick(int position) {
                Post post = adapter.getPosts(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("post", post);
                controller.navigate(R.id.formFragment, bundle);
            }
        });

    }
}