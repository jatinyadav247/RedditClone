package com.redditClone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redditClone.model.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

}
