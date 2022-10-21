package com.rojojun.jpainter.repository;

import com.rojojun.jpainter.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    Blog findByWriter(String writer);
}
