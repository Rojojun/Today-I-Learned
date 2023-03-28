package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.application.facade.CreatePostFacade;
import com.example.fastcampusmysql.application.facade.CreatePostLikeFacade;
import com.example.fastcampusmysql.application.facade.GetTimelinePostsFacade;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController // vs @Controller 차이
@RequestMapping("/posts")
public class PostController {
    final private PostWriteService postWriteService;
    final private PostReadService postReadService;
    final private GetTimelinePostsFacade getTimelinePostsFacade;
    final private CreatePostFacade createPostFacade;
    final private CreatePostLikeFacade createPostLikeFacade

    @PostMapping("/upload")
    public Long create(@RequestBody PostCommand command) {
        return createPostFacade.excute(command);
    }
/*
    public Long create(PostCommand command) {
        return postWriteService.create(command);
    }
*/

    @GetMapping("/read")
    public List<DailyPostCount> getDailyPostCounts(DailyPostCountRequest request) {
        return postReadService.getDailyPostCount(request);
    }

    @GetMapping("/daily-post-counts/{memberId}")
    public Page<Post> getPosts(@PathVariable Long memberId, @RequestParam Integer page, @RequestParam Integer size) {
        return postReadService.getPost(memberId, PageRequest.of(page, size));
    }

    @GetMapping("/daily-post-counts/{memberId}")
    public Page<Post> getPostTest(@PathVariable Long memberId, Pageable pageRequest) {
        return postReadService.getPostForSort(memberId, pageRequest);
    }

    // 오프셋 방식보다 자유도가 낮은 편
    @GetMapping("/daily-post-counts/{memberId}/cursor")
    public PageCursor<Post> getPostTestByCursor(@PathVariable Long memberId, CursorRequest cursorRequest) {
        return postReadService.getPostCursor(memberId, cursorRequest);
    }

    @GetMapping("/member/{memberId}/timeline")
    public PageCursor<Post> getTimeline(@PathVariable Long memberId, CursorRequest cursorRequest){
        return getTimelinePostsFacade.execute(memberId, cursorRequest);
    }

    @GetMapping("/member/{memberId}/timeline/pos")
    public PageCursor<Post> getTimelinePos(@PathVariable Long memberId, CursorRequest cursorRequest){
        return getTimelinePostsFacade.executeByTimeline(memberId, cursorRequest);
    }

    @PostMapping("/{postId}/like/v1")
    public void likePost(@PathVariable Long postId) {
        postWriteService.likePost(postId);
    }

    @PostMapping("/{postId}/like/v2")
    public void likePostV2(@PathVariable Long postId, @RequestParam Long memberId) {
        createPostLikeFacade.excute(postId, memberId);
    }
}