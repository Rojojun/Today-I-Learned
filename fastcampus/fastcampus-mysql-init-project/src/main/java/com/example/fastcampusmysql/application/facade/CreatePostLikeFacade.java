package com.example.fastcampusmysql.application.facade;

import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.PostWriteLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePostLikeFacade {
    private final PostReadService postReadService;
    private final MemberReadService memberReadService;
    private final PostWriteLikeService postWriteLikeService;

    public void excute(Long postId, Long memberId){
        var pos = postReadService.getPost(postId);
        var member = memberReadService.getMember(memberId);
        postWriteLikeService.create(pos, member);
    }
}
