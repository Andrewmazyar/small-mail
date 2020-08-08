package com.smallmail.smallmail.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.smallmail.smallmail.dao.LetterDao;
import com.smallmail.smallmail.dao.UserDao;
import com.smallmail.smallmail.model.entity.Letter;
import com.smallmail.smallmail.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class LetterDaoImpl implements LetterDao {
    private static final Logger LOGGER = LogManager.getLogger(LetterDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;
    private final UserDao userDao;

    public LetterDaoImpl(JdbcTemplate jdbcTemplate, UserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
    }

    @Override
    public Letter create(Letter letter) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                String sql = "INSERT INTO letters (timeStamp, theme, body,"
                        + " user_id, owner, receivers) VALUES(?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, letter.getTime().toString());
                statement.setString(2, letter.getTheme());
                statement.setString(3, letter.getBody());
                statement.setLong(4, letter.getSender().getId());
                statement.setString(5, letter.getOwner());
                statement.setString(6, letter.getReceivers());
                LOGGER.info("user was created " + letter.getTheme() + " successfully");
                return statement;
            }
        }, holder);
        Long primaryKey = holder.getKey().longValue();
        letter.setId(primaryKey);
        addLetterForUser(letter.getRecipient(), letter.getId());
        return letter;
    }

    @Override
    public List<Letter> getAllByPhrase(String phrase, Long id) {
        String word = "%" + phrase + "%";
        String sql = "SELECT * FROM letters AS l LEFT JOIN user_letters AS ul "
                + "ON  l.id = ul.letter_id WHERE l.theme LIKE ? OR l.body LIKE ? "
                + "GROUP BY l.timeStamp HAVING ul.user_id = ? OR l.user_id = ? "
                + "ORDER BY l.timeStamp DESC LIMIT(20)";
        List<Letter> letters = jdbcTemplate.query(sql, new Object[]{word , word, id, id},
                (rs, rowNum) ->
                        new Letter(
                                rs.getLong("id"),
                                LocalDateTime.parse(rs.getString("timeStamp")),
                                rs.getString("theme"),
                                rs.getString("body"),
                                userDao.getById(rs.getLong("user_id")),
                                rs.getString("owner"),
                                rs.getString("receivers")
                        )
        );
        for (Letter let : letters) {
            let.setRecipient(addUserToLetter(let.getId()));
        }
        return letters;
    }

    @Override
    public Letter update(Letter letter) {
        Long userId = letter.getSender() == null ? null : letter.getSender().getId();
        String sql = "UPDATE letters SET  user_id = ?  WHERE id = ?";
        jdbcTemplate.update(sql, userId, letter.getId());
        deleteLetterForUser(letter.getId());
        if (!letter.getRecipient().isEmpty()) {
            addLetterForUser(letter.getRecipient(), letter.getId());
        }
        return letter;
    }

    @Override
    public Letter getById(Long id) {
        String sql = "SELECT * FROM letters WHERE id = ?";
        Letter letter = (Letter) jdbcTemplate.queryForObject(sql, new Object[]{id},
                (rs, rowNum) ->
                        new Letter(
                                rs.getLong("id"),
                                LocalDateTime.parse(rs.getString("timeStamp")),
                                rs.getString("theme"),
                                rs.getString("body"),
                                userDao.getById(rs.getLong("user_id")),
                                rs.getString("owner"),
                                rs.getString("receivers")
                        )
        );
        letter.setRecipient(addUserToLetter(letter.getId()));
        return letter;
    }

    @Override
    public List<Letter> getAllByUserId(Long id) {
        String sql = "SELECT * FROM letters AS l LEFT JOIN user_letters AS ul "
                + "ON  l.id = ul.letter_id GROUP BY l.timeStamp HAVING ul.user_id = ?"
                + " OR l.user_id = ? ORDER By l.timeStamp DESC";
        List<Letter> letters = jdbcTemplate.query(sql, new Object[]{id, id},
                (rs, rowNum) ->
                new Letter(
                        rs.getLong("id"),
                        LocalDateTime.parse(rs.getString("timeStamp")),
                        rs.getString("theme"),
                        rs.getString("body"),
                        userDao.getById(rs.getLong("user_id")),
                        rs.getString("owner"),
                        rs.getString("receivers")
                        )
        );
        for (Letter let : letters) {
            let.setRecipient(addUserToLetter(let.getId()));
        }
        return letters;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE letters WHERE id = ?";
        jdbcTemplate.update(sql, id);

    }

    private void addLetterForUser(List<User> users, Long letterId) {
        for (User user : users) {
            String sqlRole = "INSERT INTO user_letters (user_id, letter_id) VALUES(?, ?)";
            jdbcTemplate.update(sqlRole, user.getId(), letterId);
        }
    }

    private void deleteLetterForUser(Long letterId) {
        String sql = "DELETE user_letters WHERE letter_id = ?";
        jdbcTemplate.update(sql, letterId);
    }
    private List<User> addUserToLetter(Long id) {
        List<User> users = new ArrayList<>();
        String numberSql = "SELECT user_id FROM user_letters WHERE letter_id = ?";
        List<Long> numbers = jdbcTemplate.query(numberSql, new Object[]{id},
                (rs, rowNum) ->
                        new Long(
                                rs.getLong("user_id")
                        ));
        for (Long l : numbers) {
            users.add(userDao.getById(l));
        }
        return users;
    }
}
