package com.redditClone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redditClone.model.Post;
@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

}
