package com.civic.repository;

import com.civic.entity.Issue;
import com.civic.entity.Upvote;
import com.civic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UpvoteRepository extends JpaRepository<Upvote, Long> {
    long countByIssue(Issue issue);
    Optional<Upvote> findByIssueAndUser(Issue issue, User user);
}