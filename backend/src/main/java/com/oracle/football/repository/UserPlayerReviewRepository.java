package com.oracle.football.repository;

import com.oracle.football.model.User;
import com.oracle.football.model.UserPlayerReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPlayerReviewRepository extends JpaRepository<UserPlayerReview, Integer> {

  List<UserPlayerReview> findAllByUser(User user);

  Optional<UserPlayerReview> findByReviewId(Integer reviewId);
}
