package com.example.okhttp.ui.posts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.okhttp.data.models.Post;
import com.example.okhttp.databinding.ItemPostBinding;

import java.util.ArrayList;
import java.util.List;


public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {

    private List<Post> posts = new ArrayList<>();
    private OnClick onLongClick;

    public void setOnLongClick(OnClick onLongClick) {
        this.onLongClick = onLongClick;
    }

    public Post getPosts(int position) {
        return posts.get(position);
    }


    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    public void deletePost(Post post, int position) {
        this.posts.remove(post);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new PostsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        holder.onBind(posts.get(position));

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostsViewHolder extends RecyclerView.ViewHolder{

        private ItemPostBinding binding;

        public PostsViewHolder(@NonNull ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(Post post){
            binding.tvUserId.setText(String.valueOf(post.getId()));
            binding.tvTitle.setText(post.getTitle());
            binding.tvDescription.setText(post.getContent());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onLongClick.onItemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onLongClick.onLongClick(post, getAdapterPosition());
                    return false;
                }
            });

        }
    }

    public interface OnClick {

        void onLongClick(Post post,  int position);
        void onItemClick(int position);
    }
}
