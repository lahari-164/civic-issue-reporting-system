package com.civic.repository;

import com.civic.entity.Issue;
import com.civic.enums.Category;
import com.civic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByCategory(Category category);
    List<Issue> findByReportedBy(User user);
    List<Issue> findByAssignedOfficer(User officer);
    long countByCategory(Category category);
}