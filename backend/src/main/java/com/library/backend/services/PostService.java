package com.library.backend.services;

import com.library.backend.entities.Post;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts();

    Post saveNewPost(Post post, Long user_id);
}
