package org.springboot.questionservice.repository;


import org.springboot.questionservice.model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Questions, Long> {

    List<Questions> findAllByCategory(String category);

    Questions findById(Integer id);

    @Query(value = "SELECT * FROM questions where category=:category ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Questions> findRandomQuestionsByCategory(String category, int limit);
}
