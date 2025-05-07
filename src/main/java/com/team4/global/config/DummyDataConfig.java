package com.team4.global.config;

import com.team4.domain.comment.entity.Comment;
import com.team4.domain.comment.repository.CommentRepository;
import com.team4.domain.feedback.entity.FeedbackType;
import com.team4.domain.feedback.entity.PlaceFeedback;
import com.team4.domain.feedback.repository.PlaceFeedbackRepository;
import com.team4.domain.member.dao.MemberRepository;
import com.team4.domain.member.domain.Member;
import com.team4.domain.place.entity.Place;
import com.team4.domain.place.repository.PlaceRepository;
import com.team4.domain.post.dao.PostRepository;
import com.team4.domain.post.domain.Post;
import com.team4.domain.travel.dao.CourseRepository;
import com.team4.domain.travel.dao.TravelRepository;
import com.team4.domain.travel.domain.Course;
import com.team4.domain.travel.domain.Travel;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DummyDataConfig implements CommandLineRunner {
    private final MemberRepository memberRepository;
    private final TravelRepository travelRepository;
    private final PostRepository postRepository;
    private final PlaceRepository placeRepository;
    private final CommentRepository commentRepository;
    private final CourseRepository courseRepository;
    private final PlaceFeedbackRepository feedbackRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if(memberRepository.findByNickname("nickname").isEmpty()) {
            Member member = Member.builder()
                    .nickname("nickname")
                    .password(encoder.encode("password1!"))
                    .region("서울")
                    .build();
            memberRepository.save(member);

            for (int i = 0; i < 2; i++) {
                Travel travel = Travel.builder()
                        .member(member)
                        .title("여행 제목 " + i)
                        .area("서울")
                        .thumbnail("https://dummyimage.com/600x400/000/fff&text=Thumbnail" + i)
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now().plusDays(3))
                        .isPost(true)
                        .build();
                travelRepository.save(travel);

                for (int j = 0; j < 2; j++) {
                    Course course = Course.builder()
                            .travel(travel)
                            .courseDate(LocalDate.now().plusDays(j))
                            .moveTime(60L)
                            .name("코스 이름 " + j)
                            .address("서울특별시 강남구")
                            .description("코스 설명 " + j)
                            .build();
                    courseRepository.save(course);
                }

                Post post = Post.builder()
                        .travel(travel)
                        .title("포스트 제목 " + i)
                        .nickname(member.getNickname())
                        .description("여행 내용 설명 " + i)
                        .likeCount(0L)
                        .build();
                postRepository.save(post);


            }

            for (int i = 0; i < 2; i++) {
                Place place = new Place(
                        "장소 이름 " + i, "서울시 종로구", 37.572950 + i, 126.979357 + i,
                        List.of("https://dummyimage.com/400x300/aaa/fff&text=place" + i)
                );
                placeRepository.save(place);

                Comment comment = new Comment(place, member, true, "여기 진짜 좋더라구요!" + i);
                commentRepository.save(comment);

                PlaceFeedback feedback = new PlaceFeedback(place, member, i % 2 == 0 ? FeedbackType.BEST : FeedbackType.GOOD);
                feedbackRepository.save(feedback);
            }
        }
    }

}
