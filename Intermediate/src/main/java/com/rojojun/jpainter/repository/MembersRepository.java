package com.rojojun.jpainter.repository;

import com.rojojun.jpainter.model.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembersRepository extends JpaRepository<Members, Integer> {
    Optional<Members> findByNickname(String nickname);
}
