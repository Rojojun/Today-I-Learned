package com.rojojun.jpanoviceprac;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    Blog findByWriter(String writer);
}
