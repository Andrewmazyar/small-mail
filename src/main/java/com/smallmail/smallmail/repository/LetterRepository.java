package com.smallmail.smallmail.repository;

import java.util.List;
import com.smallmail.smallmail.model.entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {

    @Query("SELECT l FROM Letter AS l LEFT JOIN l.recipient AS r "
            + "WHERE l.theme LIKE %:phrase% OR l.body LIKE %:phrase% GROUP BY l.time "
            + "HAVING r.id = :id OR l.sender.id = :id ORDER BY l.time DESC ")
    List<Letter> findAllByPhrase(@Param("phrase") String phrase, @Param("id") Long id);

    Letter findByTheme(String theme);

    @Query("SELECT l FROM Letter AS l LEFT JOIN l.recipient AS r "
            + "WHERE l.sender.id = :sender OR r.id = :sender ORDER BY l.time DESC")
    List<Letter> findAllByUser(@Param("sender") Long sender);
}
