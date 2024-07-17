package com.mahendra.jpal.repository;


import com.mahendra.jpal.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcTeacherRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Teacher> rowMapper = new RowMapper<Teacher>() {
        @Override
        public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
            Teacher teacher = new Teacher();
            teacher.setTeacherId(rs.getLong("teacher_id"));
            teacher.setTeacherName(rs.getString("teacher_name"));
            return teacher;
        }


    };

    public List<Teacher> findAll() {
        return jdbcTemplate.query("SELECT * FROM Teacher", rowMapper);
    }

    public Teacher findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Teacher WHERE teacher_id = ?", new Object[]{id}, rowMapper);
    }

    public void save(Teacher teacher) {
        if (teacher.getTeacherId() == 0) {
            jdbcTemplate.update("INSERT INTO Teacher (teacher_name) VALUES (?)", teacher.getTeacherName());
        } else {
            jdbcTemplate.update("UPDATE Teacher SET teacher_name = ? WHERE teacher_ = ?", teacher.getTeacherName(), teacher.getTeacherId());
        }
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM Teacher WHERE teacher_ = ?", id);
    }
}
