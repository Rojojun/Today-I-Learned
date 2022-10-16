package com.rojojun.jpanoviceprac;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    public void writeBlog(BlogRequestDto requestDto) {
        Blog blog = new Blog(requestDto);
        blogRepository.save(blog);
    }

    public List<BlogResponseDto> readPost() {
        List<Blog> blogList = blogRepository.findAll();
        List<BlogResponseDto> blogResponseDtoList = new ArrayList<>();
        for (Blog blog : blogList) {
            BlogResponseDto blogResponseDto = new BlogResponseDto(blog);
            blogResponseDtoList.add(blogResponseDto);
        }
        return blogResponseDtoList;
    }


    public BlogResponseDto readOnePost(String user) {
        Blog blog = blogRepository.findByWriter(user);
        return new BlogResponseDto(blog);
    }

    public void updatePostError(int id, BlogRequestDto requestDto) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new RuntimeException("게시글이 존재하지 않습니다.")
        );

        blog.updatePostError(requestDto);
        blogRepository.save(blog);
    }

    public void updatePost(int id, BlogRequest4PutDto requestDto) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new RuntimeException("게시글이 존재하지 않습니다.")
        );

        blog.updatePost(requestDto);
        blogRepository.save(blog);
    }

    public void deletePost(int id) {
        blogRepository.deleteById(id);
    }
}
