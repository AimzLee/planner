package kr.co.team.planner;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import kr.co.team.planner.entity.*;
import kr.co.team.planner.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private GuestBookRepository guestBookRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ReplyRepository replyRepository;

    //@Test
    public void insertDummyMembers() {
        for(int i=1; i<=100; i++) {
            Member member = Member.builder()
                    .email("member"+i+"@study.hard")
                    .name("name"+i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("name"+i))
                    .build();
            member.addMemberRole(MemberRole.USER);
            if(i > 80) member.addMemberRole((MemberRole.MANAGER));
            if(i > 90) member.addMemberRole((MemberRole.ADMIN));
            memberRepository.save(member);
        }
    }

    //@Test
    public void testFindMemberByEmail() {
        Optional<Member> member = memberRepository.findByEmail("member95@study.hard", false);
        System.out.println(member.get());
    }

    //@Test
    public void insertDummyMember() {
        Member member = Member.builder()
                .email("member200@study.hard")
                .name("name200")
                .fromSocial(false)
                .password(passwordEncoder.encode("name200"))
                .build();
        member.addMemberRole(MemberRole.USER);
        member.addMemberRole((MemberRole.MANAGER));
        member.addMemberRole((MemberRole.ADMIN));
        memberRepository.save(member);
    }

    //@Test //-> ?????? ??????! PK ????????? ???????????????...  '???????????????!'??? ???????????????, '????????? ??? ???!'??? ????????? ??????! ????????????!
    public void testDeleteMemberById() {
        String email = "member200@study.hard";
        memberRepository.deleteById(email);
    }

    //@Test
    public void insertGuestBook(){
        for(int i=1; i<=300; i=i+1){
            GuestBook guestBook = GuestBook.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer("User..." + i)
                    .build();
            guestBookRepository.save(guestBook);
        }
    }

    //@Test
    public void updateGuestBook(){
        GuestBook guestBook = GuestBook.builder()
                .gno(299L)
                .title("?????? ??????")
                .content("?????? ??????")
                .writer("????????? ??????")
                .build();
        guestBookRepository.save(guestBook);
    }

    //@Test
    public void testQuery(){
        //paging ??????
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        //Querydsl Entity ????????????
        QGuestBook qGuestBook = QGuestBook.guestBook;

        //?????? ??????
        BooleanBuilder builder = new BooleanBuilder();
        //title??? 1??? ????????? ???????????? ??????
        String keyword = "1";
        BooleanExpression expression = qGuestBook.title.contains(keyword);
        //????????? ??????
        builder.and(expression);
        //?????? ??????
        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);
        //??????
        for(GuestBook guestBook : result){
            System.out.println(guestBook);
        }
    }

    //@Test
    //title ?????? content ??? 1??? ???????????? ??????
    //gno ??? ?????? 200?????? ?????? ????????? ??????
    public void testSelectQuery(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        QGuestBook qGuestBook = QGuestBook.guestBook;

        BooleanBuilder builder = new BooleanBuilder();

        String keyword = "1";

        BooleanExpression exTitle = qGuestBook.title.contains(keyword);
        BooleanExpression exContent = qGuestBook.content.contains(keyword);
        //2?????? ????????? or??? ???????????? ??????
        builder.and(exTitle.or(exContent));
        //gno??? 200 ?????? ?????? ??????
        BooleanExpression exGno = qGuestBook.gno.lt(202L);
        builder.and(exGno);

        //?????? ??????
        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);
        //??????
        for(GuestBook guestBook : result){
            System.out.println(guestBook);
        }
    }

    //@Test
    public void insertBoards(){
        for(int i=1; i<=100; i=i+1){
            Member member = Member.builder()
                    .email("member"+i+"@study.hard")
                    .name("name"+i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("name"+i))
                    .build();

            Board board = Board.builder()
                    .title("??????..." + i)
                    .content("??????..." + i)
                    .writer(member)
                    .build();
            boardRepository.save(board);
        }
    }

    //@Test
    public void insertReplys(){
        Random r = new Random();
        for(long i=1; i<=300; i=i+1){
            Board board = Board.builder()
                    .bno((long)(r.nextInt(100) + 1))
                    .build();

            Reply reply = Reply.builder()
                    .rno(i)
                    .text("??????..." + i)
                    .replyWriter("??????")
                    .board(board)
                    .build();
            replyRepository.save(reply);
        }
    }


}