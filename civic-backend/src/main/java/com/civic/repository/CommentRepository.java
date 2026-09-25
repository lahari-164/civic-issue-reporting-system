package com.civic.repository;

import com.civic.entity.Comment;
import com.civic.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByIssueOrderByCreatedAtAsc(Issue issue);
}