package com.rojojun.jpainter.repository;

import com.rojojun.jpainter.model.Members;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembersRepository extends JpaRepository<Members, Integer> {
}
