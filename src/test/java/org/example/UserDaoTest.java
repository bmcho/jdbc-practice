package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest {

    @BeforeEach
    void setUp() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("db_schema.sql"));
        DatabasePopulatorUtils.execute(resourceDatabasePopulator, ConnectionManager.getDataSource());
    }

    @Test
    void givenNothing_whenCreatDaoFromNewUser_thenDoseNotAnyExceptions() throws SQLException {
        UserDao userDao = new UserDao();

        userDao.create(new User("bmcho", "password", "name", "test@test.com"));

        User user = userDao.findByUserId("bmcho");
        assertThat(user).isEqualTo(new User("bmcho", "password", "name", "test@test.com"));
    }

    @Test
    void givenWrongUserId_whenCreatDaoFromNewUser_thenFindUserIsNull() throws SQLException {
        UserDao userDao = new UserDao();

        userDao.create(new User("bmcho", "password", "name", "test@test.com"));

        User user = userDao.findByUserId("abc");
        assertThat(user).isNull();
    }
}
