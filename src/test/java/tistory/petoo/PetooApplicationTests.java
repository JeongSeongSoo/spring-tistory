package tistory.petoo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tistory.petoo.entity.Member;
import tistory.petoo.repository.MemberRepository;

@SpringBootTest
class PetooApplicationTests {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	void save() {
		memberRepository.save(Member.builder()
						.userId("ssjeong")
						.password("ssjeong")
						.build());
	}

	@Test
	void select() {
		Member member = memberRepository.findById(2).get();
		System.out.println("[프뚜] > " + member);
	}

}
