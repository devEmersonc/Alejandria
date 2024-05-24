package com.library.backend.services;

import com.library.backend.entities.Comment;
import com.library.backend.entities.User;

import java.util.List;

public interface CommentService {

    List<Comment> getAllComments();

    void deleteComment(Long comment_id);

    Comment addNewComment(Comment comment, Long user_id, Long post_id);
}
