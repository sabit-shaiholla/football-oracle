package com.oracle.football.repository;

import com.oracle.football.model.UserPlayerReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPlayerReviewRepository extends JpaRepository<UserPlayerReview, Long> {
}
