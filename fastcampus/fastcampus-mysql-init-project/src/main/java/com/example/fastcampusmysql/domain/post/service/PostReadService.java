package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.member.dto.PostDto;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostLikeRepository;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostReadService {
    final private PostRepository postRepository;
    final private PostLikeRepository postLikeRepository;

    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
        // return List(작성일자, 작성회언, 작성 게시물 갯수)
        return postRepository.groupByCreatedDate(request);
    }

    public Page<PostDto> getPost(Long memberId, PageRequest pageRequest) {
        return postRepository.findAllByMemberId(memberId, pageRequest).map(this::toPostDto);
    }

    private PostDto toPostDto(Post post) {
        return new PostDto(post.getId(), post.getContents(), post.getCreatedAt(), postLikeRepository.count(post.getId()));
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId, false).orElseThrow();
    }
    public Page<Post> getPostForSort(Long memberId, Pageable pageRequest) {
        return postRepository.findAllByMemberId(memberId, pageRequest);
    }

    public PageCursor<Post> getPostCursor(Long memberId, CursorRequest cursorRequest) {
        var posts = getPostCursorEx(memberId, cursorRequest);
        var nextKeyValue = posts.stream()
                .mapToLong(Post::getId)
                .min()
                .orElse(CursorRequest.NONE_KEY);
        return new PageCursor<>(cursorRequest.next(nextKeyValue), posts);
    }
    public PageCursor<Post> getPostCursors(List<Long> memberIds, CursorRequest cursorRequest) {
        var posts = getPostCursorEx(memberIds, cursorRequest);
        var nextKeyValue = getNextKey(posts);
        return new PageCursor<>(cursorRequest.next(nextKeyValue), posts);
    }

    public List<Post> getPosts(List<Long> ids) {
        return postRepository.findAllByInId(ids);
    }

    private static long getNextKey(List<Post> posts){
        return posts.stream()
                .mapToLong(Post::getId)
                .min()
                .orElse(CursorRequest.NONE_KEY);
    }

    private List<Post> getPostCursorEx(Long memberId, CursorRequest cursorRequest) {
        // Key가 Null인지 아닌지 분기하는 메소드
        if (cursorRequest.hasKey()) {
            var posts = postRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(cursorRequest.key(), memberId, cursorRequest.size());
        }
        return postRepository.findAllByMemberIdAndOrderByIdDesc(memberId, cursorRequest.size());
    }
    private List<Post> getPostCursorEx(List<Long> memberIds, CursorRequest cursorRequest) {
        // Key가 Null인지 아닌지 분기하는 메소드
        if (cursorRequest.hasKey()) {
            var posts = postRepository.findAllByLessThanIdAndInMemberIdAndOrderByIdDesc(cursorRequest.key(), memberIds, cursorRequest.size());
        }
        return postRepository.findAllByInMemberIdAndOrderByIdDesc(memberIds, cursorRequest.size());
    }
}
