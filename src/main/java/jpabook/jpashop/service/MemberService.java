package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    // @RequiredArgsConstructor + final : 생성자 주입방식
    private final MemberRepository memberRepository;

    /*
        회원가입
    */
    @Transactional
    public Long join(Member member) {

        validateDuplicatemember(member);  // 중복회원 검증 메소드

        memberRepository.save(member);

        return member.getId();
    }

    // 멀티쓰레드 환경에서는 동시에 로직을 통과할경우 중복가입이 될 수 있다.
    // 따라서 실무에서는 DB에 회원 name을 unique 제약조건으로 잡아야한다.
    private void validateDuplicatemember(Member member) {

        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /*
        회원 전체 조회
     */
    public List<Member> findMembers() {

        return memberRepository.findAll();
    }

    /*
        회원 한명 조회
     */
    public Member findOne(Long memberId) {

        return memberRepository.findOne(memberId);
    }
    
    /*
        회원 수정
     */
    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id); // 영속성 상태
        member.setName(name);  // 값 set

        // Transaction에 의해 commit
    }
}
