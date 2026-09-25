package com.civic.repository;

import com.civic.entity.Officer;
import com.civic.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OfficerRepository extends JpaRepository<Officer, Long> {
    Optional<Officer> findByEmail(String email);
    List<Officer> findByCategoryOrderByIdAsc(Category category);
}