package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext  // springboot가 EntityManager 인터페이스를 주입해준다
    private EntityManager em;

    public Long save(Member member) {

        em.persist(member);

        return member.getId();  // command랑 query랑 분리해서 가급적이면 return값 안만들고 Id정도만 조회용으로 받음
    }

    public Member find(Long id) {

        return em.find(Member.class, id);
    }
}
