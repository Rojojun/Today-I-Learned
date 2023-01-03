package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRepository {
    final private NamedParameterJdbcTemplate jdbcTemplate;
    static final private String TABLE = "member";
    private static final RowMapper<Member> rowMapper = (ResultSet resultSet, int rowNum) -> Member.builder()
            .id(resultSet.getLong("id"))
            .email(resultSet.getString("email"))
            .nickname(resultSet.getString("nickname"))
            .birthday(resultSet.getObject("birthday", LocalDate.class))
            .createdAt(resultSet.getObject("createdAt", LocalDate.class).atStartOfDay())
            .build();

    public Optional<Member> findById(Long id){
        /*
        select *
        from Member
        where id = id
         */
        var sql = String.format("SELECT * FROM %s WHERE id = :id", TABLE);
        var param = new MapSqlParameterSource()
                .addValue("id", id);

        var member = jdbcTemplate.queryForObject(sql, param, rowMapper);
        return Optional.ofNullable(member);
    };

    public List<Member> findAllByIdIn(List<Long> ids) {
        // SQL에서 빈 List값을 Return해주도록 하는 로직
        if (ids.isEmpty()){
            return List.of();
        }

        var sql = String.format("SELECT * FROM %s WHERE id in (:ids)", TABLE);
        var params = new MapSqlParameterSource("ids", ids);
        return jdbcTemplate.query(sql, params, rowMapper);
    }

    public Member save(Member member) {
        /*
        Member의 id를 보고 갱신 또는 삽입을 정함 반환값은 id를 담아서 반환
         */
        if (member.getId() == null) {
            return insert(member);
        }
        return update(member);
    }

    private Member insert(Member member) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
        // POJO 객체
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);

        var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return Member.builder()
                .id(id)
                .email(member.getEmail())
                .birthday(member.getBirthday())
                .nickname(member.getNickname())
                .build();
    }

    private Member update(Member member) {
        var sql = String.format("UPDATE %s set email = :email, nickname = :nickname, birthday = :birthday WHERE id = :id", TABLE);
        SqlParameterSource param = new BeanPropertySqlParameterSource(member);
        jdbcTemplate.update(sql, param);
        return member;
    }
}
