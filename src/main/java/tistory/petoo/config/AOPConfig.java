package tistory.petoo.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Aspect
public class AOPConfig {

    // 2022.12.15[프뚜]: @Before(이전) > 어드바이스 타겟 메소드가 호출되기 전에 어드바이스 기능을 수행
    // 2022.12.15[프뚜]: @After(이후) > 타겟 메소드의 결과에 관계없이(즉 성공, 예외 관계없이) 타겟 메소드가 완료 되면 어드바이스 기능을 수행
    // 2022.12.15[프뚜]: @AfterReturning(정상적 반환 이후) > 타겟 메소드가 성공적으로 결과값을 반환 후에 어드바이스 기능을 수행
    // 2022.12.15[프뚜]: @AfterThrowing(예외 발생 이후) > 타겟 메소드가 수행 중 예외를 던지게 되면 어드바이스 기능을 수행
    // 2022.12.15[프뚜]: @Around(메소드 실행 전후) > 어드바이스가 타겟 메소드를 감싸서 타겟 메소드 호출전과 후에 어드바이스 기능을 수행

    @Around("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public Object getAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("[LOG] > getAround method start");

        // 2022.12.15[프뚜]: 메서드 실행
        Object retVal = pjp.proceed();

        System.out.println("[LOG] > getAround method end");
        return retVal;
    }

    @Around("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public Object postAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("[LOG] > postAround method start");

        // 2022.12.15[프뚜]: 클라이언트에서 받아온 파라미터
        Object[] objects = pjp.getArgs();
        Map param = (Map) objects[0];

        System.out.println("[LOG] > param check : " + param);

        // 2022.12.15[프뚜]: 클라이언트에서 받아온 파라미터에 값 추가
        param.put("birth", "0801");

        // 2022.12.15[프뚜]: 메서드 실행
        Object retVal = pjp.proceed();

        // 2022.12.15[프뚜]: 메서드에서 받아온 파라미터에 값 추가
        Map result = (Map) retVal;
        result.put("hobby", "coding");
        System.out.println("[LOG] > param add : " + param);

        System.out.println("[LOG] > postAround method start");
        return retVal;
    }

}