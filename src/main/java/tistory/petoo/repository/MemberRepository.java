package tistory.petoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tistory.petoo.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {

}