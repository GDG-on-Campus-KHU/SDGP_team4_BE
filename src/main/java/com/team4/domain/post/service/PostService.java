package com.team4.domain.post.service;

import com.team4.domain.post.dao.LikePostRepository;
import com.team4.domain.post.domain.LikePost;
import com.team4.domain.member.dao.MemberRepository;
import com.team4.domain.member.domain.Member;
import com.team4.domain.member.exception.MemberNotFoundException;
import com.team4.domain.post.dao.PostRepository;
import com.team4.domain.post.domain.Post;
import com.team4.domain.post.dto.PostInfoDto;
import com.team4.domain.post.dto.PostLikeDto;
import com.team4.domain.post.dto.PostSimpleDto;
import com.team4.domain.post.dto.PostUpdateDto;
import com.team4.domain.post.exception.PostAuthException;
import com.team4.domain.post.exception.PostNotFoundException;
import com.team4.domain.travel.dao.TravelRepository;
import com.team4.domain.travel.domain.Travel;
import com.team4.domain.travel.exception.TravelNotFoundException;
import com.team4.global.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final TravelRepository travelRepository;
    private final LikePostRepository likePostRepository;

    public Page<PostSimpleDto> showPosts(String nickname, Pageable pageable) {
        Member member = memberRepository.findByNickname(nickname).orElseThrow(MemberNotFoundException::new);

        // 사용자가 좋아요 누른 게시글 ID Set으로 추출
        Set<Long> likedPostIds = likePostRepository.findAllByMember(member).stream()
                .map(likePost -> likePost.getPost().getId())
                .collect(Collectors.toSet());
        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(post -> PostSimpleDto.of(post, likedPostIds.contains(post.getId())));
    }

    @Transactional
    public PostInfoDto showPostInfo(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        Travel travel = travelRepository.findById(post.getTravel().getId()).orElseThrow(TravelNotFoundException::new);
        Member member = memberRepository.findById(travel.getMember().getMemberId()).orElseThrow(MemberNotFoundException::new);
        return PostInfoDto.of(post, travel, member);
    }

    @Transactional
    public PostInfoDto editPostInfo(Long postId, PostUpdateDto postDto) {
        String nickname = JwtService.getLoginMemberNickname();
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        if(!nickname.equals(post.getTravel().getMember().getNickname()))
            throw new PostAuthException();
        Post update = post.update(postDto);
        return PostInfoDto.of(update, update.getTravel(), update.getTravel().getMember());
    }

    @Transactional
    public void offPost(Long postId) {
        String nickname = JwtService.getLoginMemberNickname();
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        Travel travel = travelRepository.findByPost(post).orElseThrow(TravelNotFoundException::new);
        if(!nickname.equals(travel.getMember().getNickname()))
            throw new PostAuthException();
        travel.offPost();
        postRepository.delete(post);
    }

    @Transactional
    public PostLikeDto changePostStatus(Long postId, String nickname) {
        Member member = memberRepository.findByNickname(nickname).orElseThrow(MemberNotFoundException::new);
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        Optional<LikePost> likeStatus = likePostRepository.findByPost(post);
        if(likeStatus.isPresent()) {
            likePostRepository.delete(likeStatus.get());
            post.downLike();
        }
        else {
            likePostRepository.save(new LikePost(member, post));
            post.upLike();
        }

        return new PostLikeDto(post.getId(), post.getLikeCount());
    }
}
