package com.company.example.dao;

import com.company.example.model.User;
import com.jtool.db.mysql.annotation.Table;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Table(tableName = "user", primaryKeyName = "id")
public class UserDAO extends AbstractExampleDAO {

    @Override
    protected RowMapper<?> makeRowMapperInstance() {
        return (rs, rowNum) -> {
            User o = new User();
            o.setId(rs.getInt("id"));
            o.setUsername(rs.getString("username"));
            o.setUserpwd(rs.getString("userpwd"));
            o.setAddtime(new Date(rs.getTimestamp("addtime").getTime()));

            return o;
        };
    }

    @Async("dbExecutor")
    public void addUser(User user) {
        add(user);
    }

    public Optional<User> getUser(String username) {
        return select().where("username = ?", username).execAsPojoOpt();
    }

    public List<User> getUserList() {
        return select().orderByDesc("addtime").execAsList();
    }

}
