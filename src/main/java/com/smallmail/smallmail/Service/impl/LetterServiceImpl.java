package com.smallmail.smallmail.Service.impl;

import java.time.LocalDateTime;
import java.util.List;
import com.smallmail.smallmail.Service.LetterService;
import com.smallmail.smallmail.model.entity.Letter;
import com.smallmail.smallmail.repository.LetterRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LetterServiceImpl implements LetterService {
    private static final Logger LOGGER = LogManager.getLogger(LetterServiceImpl.class);

    private final LetterRepository letterRepository;

    public LetterServiceImpl(LetterRepository letterRepository) {
        this.letterRepository = letterRepository;
    }

    @Override
    public Letter create(Letter letter) {
        letter.setTime(LocalDateTime.now());
        LOGGER.info("Letter was created " + letter.getTime() + " by owner" + letter.getOwner());
        return letterRepository.save(letter);
    }

    @Override
    public List<Letter> getByPhrase(String phrase, Long id) {
        return letterRepository.findAllByPhrase(phrase, id);
    }

    @Override
    public Letter update(Letter letter) {
        LOGGER.info("Letter was updated by user " + letter.getOwner());
        return letterRepository.saveAndFlush(letter);
    }

    @Override
    public Letter getById(Long id) {
        return letterRepository.findById(id).get();
    }

    @Override
    public Letter getByTheme(String theme) {
        return letterRepository.findByTheme(theme);
    }

    @Override
    public List<Letter> getAllByUser(Long id) {
        return letterRepository.findAllByUser(id);
    }

    @Override
    public List<Letter> getAll() {
        return letterRepository.findAll();
    }

    @Override
    public void remove(Long id) {
        letterRepository.deleteById(id);
    }
}
