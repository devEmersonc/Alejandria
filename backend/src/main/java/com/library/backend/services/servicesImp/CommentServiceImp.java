package com.library.backend.services.servicesImp;

import com.library.backend.entities.Comment;
import com.library.backend.entities.Post;
import com.library.backend.entities.User;
import com.library.backend.repositories.CommentRepository;
import com.library.backend.repositories.PostRepository;
import com.library.backend.repositories.UserRepository;
import com.library.backend.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAllByOrderByIdDesc();
    }

    @Override
    public void deleteComment(Long comment_id) {
        commentRepository.deleteById(comment_id);
    }

    @Override
    public Comment addNewComment(Comment comment, Long user_id, Long post_id) {
        User user = userRepository.findById(user_id).orElse(null);
        Post post = postRepository.findById(post_id).orElse(null);
        Comment newComment = new Comment();

        newComment.setContent(comment.getContent());
        newComment.setUser(user);
        newComment.setPost(post);

        return commentRepository.save(newComment);
    }
}
