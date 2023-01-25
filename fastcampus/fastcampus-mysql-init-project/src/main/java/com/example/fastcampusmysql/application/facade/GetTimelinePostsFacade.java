package com.example.fastcampusmysql.application.facade;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.entity.Timeline;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.TimelineReadService;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetTimelinePostsFacade {

    final private FollowReadService followReadService;
    final private PostReadService postReadService;
    final private TimelineReadService timelineReadService;

    public PageCursor<Post> execute(Long memberId, CursorRequest cursorRequest) {
        /*
        1. meberId -> follow 조회
        2. 1번 결과로 게시물 조회
         */
        var followings = followReadService.getFollowers(memberId);
        var followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();
        return postReadService.getPostCursors(followingMemberIds, cursorRequest);
    }

    public PageCursor<Post> executeByTimeline(Long memberId, CursorRequest cursorRequest) {
        /*
        1. Timeline 조회
        2. n번에 해당하는 게시물 조회
         */
        var timelines = timelineReadService.getTimelines(memberId, cursorRequest);
        var postIds = timelines.stream().map(Timeline::getPostId).toList();
        var post = postReadService.getPosts(postIds);

        return new PageCursor(timelines.nextCursorRequest(), post);
    }
}
