package com.smallmail.smallmail.Service;

import java.util.List;
import com.smallmail.smallmail.model.entity.Letter;

public interface LetterService {
    Letter create(Letter letter);

    List<Letter> getByPhrase(String phrase, Long id);

    Letter update(Letter letter);

    Letter getById(Long id);

    List<Letter> getAllByUser(Long id);

    List<Letter> getAll();

    void remove(Long id);
}
