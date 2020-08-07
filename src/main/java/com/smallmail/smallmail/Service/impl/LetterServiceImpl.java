package com.smallmail.smallmail.Service.impl;

import java.time.LocalDateTime;
import java.util.List;
import com.smallmail.smallmail.Service.LetterService;
import com.smallmail.smallmail.dao.LetterDao;
import com.smallmail.smallmail.model.entity.Letter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LetterServiceImpl implements LetterService {
    private static final Logger LOGGER = LogManager.getLogger(LetterServiceImpl.class);

    private final LetterDao letterDao;

    public LetterServiceImpl(LetterDao letterDao) {
        this.letterDao = letterDao;
    }

    @Override
    public Letter create(Letter letter) {
        letter.setTime(LocalDateTime.now());
        Letter newLetter = letterDao.create(letter);
        LOGGER.info("Letter was created " + letter.getTime() + " by owner" + letter.getOwner());
        return newLetter;
    }

    @Override
    public List<Letter> getByPhrase(String phrase, Long id) {
        return null;
    }

    @Override
    public Letter update(Letter letter) {
        LOGGER.info("Letter was updated by user " + letter.getOwner());
        return null;
    }

    @Override
    public Letter getById(Long id) {
        return null;
    }

    @Override
    public List<Letter> getAllByUser(Long id) {
        return letterDao.getAllByUserId(id);
    }

    @Override
    public List<Letter> getAll() {
        return null;
    }

    @Override
    public void remove(Long id) {
    }
}
