package com.redditClone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redditClone.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
