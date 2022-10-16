package com.rojojun.jpanoviceprac;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @PostMapping("/post")
    public ResponseEntity<Void> writePost(@RequestBody BlogRequestDto requestDto) {

        blogService.writeBlog(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get")
    public List<BlogResponseDto> readPost() {

        return blogService.readPost();
    }

    @GetMapping("/get/{user}")
    public BlogResponseDto readOnePost(@PathVariable String user) {
        return blogService.readOnePost(user);
    }

    // 왜 에러가 나는지 보여 줄 수 있는 예제
    @PutMapping("/patch/{id}")
    public ResponseEntity<Void> updatePostError(@PathVariable int id,
                                           @RequestBody BlogRequestDto requestDto) {
        blogService.updatePostError(id, requestDto);
        return ResponseEntity.ok().build();
    }

    // 에러를 피할 수 있는 예제
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable int id,
                                           @RequestBody BlogRequest4PutDto requestDto) {
        blogService.updatePost(id, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable int id) {
        blogService.deletePost(id);
        return ResponseEntity.ok().build();
    }
}
