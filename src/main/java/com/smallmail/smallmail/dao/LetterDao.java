package com.smallmail.smallmail.dao;

import java.util.List;
import com.smallmail.smallmail.model.entity.Letter;

public interface LetterDao {
    Letter create(Letter letter);

    List<Letter> getAllByPhrase(String phrase, Long id);

    Letter update(Letter letter);

    Letter getById(Long id);

    List<Letter> getAllByUserId(Long id);

    void deleteById(Long id);
}
