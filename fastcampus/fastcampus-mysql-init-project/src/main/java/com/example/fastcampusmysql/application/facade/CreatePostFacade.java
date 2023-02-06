package com.example.fastcampusmysql.application.facade;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import com.example.fastcampusmysql.domain.post.service.TimelineWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CreatePostFacade {
    final private PostWriteService postWriteService;
    final private FollowReadService followReadService;
    final private TimelineWriteService timelineWriteService;

    // 대용량데이터 같은 경우에는 트랜젝셔널의 단위(블록)을 크게 잡는 경우 -> 문제 발생!
    // ex) S3에 업로드 되는 코드가 있는데 그 부분까지 같이 트래잭셔널을 걸게 된다면?
    // S3때문에 트랜잭션이 길어지면서 커넥션문제가 생길 수 있음
    //@Transactional 그렇기 때문에 이 부분은 고민 할 문제 -> Facade영역은 단위가 매우 크기 때문
    public Long excute(PostCommand postCommand) {
        var postId = postWriteService.create(postCommand);
        var followMemberIds = followReadService.getFollowings(postCommand.memberId())
                .stream().map(Follow::getFromMemberId)
                .toList();
        timelineWriteService.deliveryToTimeline(postId, followMemberIds);

        return postId;
    }
}
