package com.redditClone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redditClone.model.Subreddit;

public interface SubredditRepository extends JpaRepository<Subreddit, Long>{

}
