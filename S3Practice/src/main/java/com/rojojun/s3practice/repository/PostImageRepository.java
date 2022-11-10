package com.rojojun.s3practice.repository;

import com.rojojun.s3practice.model.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Integer> {
}
