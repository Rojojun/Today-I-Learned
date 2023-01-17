package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.entity.Timeline;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Repository
public class TimelineRepository {
    final static String TABLE ="Timeline";

    final private NamedParameterJdbcTemplate jdbcTemplate;

    final static private RowMapper<Timeline> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> Timeline.builder()
            .id(resultSet.getLong("id"))
            .memberId(resultSet.getLong("memberId"))
            .postId(resultSet.getLong("postId"))
            .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
            .build();

    public Timeline save(Timeline timeline) {

    }

    private Timeline insert(Timeline post) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)

                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(post);
        var id = jdbcInsert.executeAndReturnKey(params).longValue();

        return Timeline.builder()
                .id(id)
                .memberId(post.getMemberId())
                .postId(post.getPostId())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
