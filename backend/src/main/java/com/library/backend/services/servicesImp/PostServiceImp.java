package com.library.backend.services.servicesImp;

import com.library.backend.entities.Post;
import com.library.backend.entities.User;
import com.library.backend.repositories.PostRepository;
import com.library.backend.repositories.UserRepository;
import com.library.backend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImp implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByIdDesc();
    }

    @Override
    public Post saveNewPost(Post post, Long user_id) {
        Post newPost = new Post();
        User user = userRepository.findById(user_id).orElse(null);

        newPost.setTitle(post.getTitle());
        newPost.setDescription(post.getDescription());
        newPost.setUser(user);

        return postRepository.save(newPost);
    }

    @Override
    public Post getPost(Long post_id){
        return postRepository.findById(post_id).orElse(null);
    }
}
