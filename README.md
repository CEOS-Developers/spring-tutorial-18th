# CEOS 18th Backend Study - 0주차 미션
Spring이 지원하는 기술들(IoC/DI, AOP, PSA 등)을 자유롭게 조사합니다.

## IoC/DI

IoC은 Inversion of Control의 약자로, 제어의 역전을 의미한다. 프로그램의 제어 흐름을 직접 제어하는 것이 아니라 외부에서 관리하는 것이다.

기존의 프로그램은 클라이언트 입장의 구현 객체가 서버 입장의 구현 객체를 <b>직접 생성하여 연결 및 실행</b>하였다. 다음과 같은 OrderServiceImpl이 있다.

```
public class OrderServiceImpl {

  private final MemberRepository memberRepository = new MemoryMemberRepository();

  private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

}
```

위 코드에서 OrderServiceImpl은 MemoryMemberRepository, FixDiscountPolicy를 사용하는 클라이언트 입장의 구현 객체이고, MemoryMemberRepository, FixDiscountPolicy은 서버 입장의 구현 객체이다. 이때, OrderServiceImpl은 직접 구현체를 생성하여 연결 및 실행하므로 프로그램의 제어 흐름을 직접 제어하고 있다. 

현재 할인 정책은 FixDiscountPolicy로, 고정 금액 할인이 이루어진다. 그런데 만약 주문 가격 대비 %로 할인을 할 수 있도록 정책을 변경하려면 어떻게 해야할까? 다음과 같이 클라이언트인 OrderServiceImpl의 코드를 고쳐야 한다.

```
public class OrderServiceImpl {

  private final MemberRepository memberRepository = new MemoryMemberRepository();

///  private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
  private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

}
```

하지만 위와 같은 코드가 좋은 코드는 아니다. 좋은 객체 지향 설계의 5가지 원칙(SOLID) 중 OCP와 DIP를 위반하고 있기 때문이다.

- OCP(Open/Closed Principle) : 개방-폐쇠 원칙으로, 소프트웨어 요소가 확장에는 열려있으나 변경에는 닫혀있어야 함을 의미한다. 즉, 기존의 코드를 변경하지 않으면서(Closed), 기능을 추가할 수 있도록(Open) 설계가 되어야 한다는 원칙이다.

- DIP(Dependency Inversion Principle) : 의존관계 역전 원칙으로, 구체화에 의존하지 않고 추상화에 의존해야 함을 의미한다. 즉, 클라이언트가 구현 클래스에 의존하지 않고, 인터페이스에 의존해야 한다는 원칙이다.

해결을 위해 OrderServiceImpl이 인터페이스에만 의존하도록 코드를 변경한다.

```
public class OrderServiceImpl {

  private final MemberRepository memberRepository;
  private final DiscountPolicy discountPolicy;

  public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {

    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
  }
}
```
이 경우, OrderServiceImpl이 의존하고 있는 인터페이스의 구현 객체는 생성자를 통해 <b>외부에서 대신 생성하여 주입</b>해준다. 이것을 DI(Dependency Injection), 의존관계 주입 또는 의존성 주입이라고 한다.

이렇게 되면 OrderServiceImpl은 오직 자신의 로직을 실행하는 역할만 담당한다. 프로그램의 제어 흐름은 인터페이스의 구현 객체를 생성하고 주입하는 쪽에서 가져가는 것이다. 이것을 IoC(Inversion of Control), 제어의 역전이라고 한다.

객체를 생생하고 관리하면서 의존관계를 연결해주는 겻을 IoC 컨테이너 또는 DI 컨테이너라고 한다. 스프링의 경우, @Bean이 붙은 메서드를 호출하여 반환된 객체를 <b>스프링 컨테이너</b>에 등록한다. 스프링 컨테이너에 등록된 객체를 <b>스프링 빈</b>이라고 한다.

### 참고 
- https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%95%B5%EC%8B%AC-%EC%9B%90%EB%A6%AC-%EA%B8%B0%EB%B3%B8%ED%8E%B8
- https://pangtrue.tistory.com/228
- https://hckcksrl.medium.com/solid-%EC%9B%90%EC%B9%99-182f04d0d2b

## AOP

AOP는 Aspect Oriented Programming의 약자로, 관점 지향 프로그래밍을 의미한다. 어떤 로직을 기준으로 핵심적인 관점, 부가적인 관점으로 나누어서 보고 그 관점을 기준으로 공통된 로직이나 기능을 하나의 단위로 묶는다.

프로그램에는 핵심적인 비즈니스 로직이 포함되는 <b>핵심 관심 사항(core concern)</b>이 있고, 프로그램 전체에 반복적으로 적용되는 공통적인 부가 기능 로직이 포함되는 <b>공통 관심 사항(cross-cutting concern)</b>이 있다.

공통 관심 사항의 코드를 핵심 관심 사항의 코드와 분리하여, 코드의 간결성을 높이고 유연한 변경과 무한한 확장이 가능하도록 하는 것이 AOP의 목적이다.

AOP를 적용하는 방식에는 어느 시점에 적용하느냐에 따라 컴파일 시점 적용, 클래스 로딩 시점 적용, 런타임 시점 적용이 있으며, 스프링은 런타임 시점 적용 방식을 사용한다.

런타임 시점 적용은 컴파일, 클래스 로딩, main() 메서드의 실행 이후에 자바가 제공하는 범위 내에서 부가 기능을 적용하는 방식이다. 이미 런타임 중이므로 코드를 조작하기 어려워 여러 개념과 기능을 활용하여 프록시를 통해 부가 기능을 적용한다.

프록시는 메서드 실행 시점에서만 다음 타겟을 호출할 수 있기 때문에, 런타임 시점에 부가기능을 적용하는 방식은 메서드의 실행 지점으로 제한된다.

예를 들어, MemberController, MemberService, MemberRepository 내 메서드의 호출 시간을 측정하고 싶다고 하면 가장 기본적인 방법은 각각의 메서드에 시간 측정 로직을 추가하는 것이다.

```
public class MemberService {
  public Long join(Member member) {

    long start = System.currentTimeMillis(); // 호출 시작 시간

    // 회원 가입 로직

    long finish = System.currentTimeMillis(); // 호출 종료 시간
    long timeMs = finish - start; // 호출 소요 시간
  }

  public List<Member> findMembers() {

    long start = System.currentTimeMillis(); // 호출 시작 시간

    // 전체 회원 조회 로직

    long finish = System.currentTimeMillis(); // 호출 종료 시간
    long timeMs = finish - start; // 호출 소요 시간
  }
}
```

이때, AOP를 적용하면 회원 가입, 회원 조회 등의 핵심 관심 사항과 호출 소요 시간을 측정하는 공통 관심 사항을 분리할 수 있다. 이때, 다음과 같이 시간을 측정하는 로직을 별도의 공통 로직으로 만들어 원하는 적용 대상에 적용한다.

```
@Component
@Aspect
public class TimeTraceAop {

  @Around("execution(* hello.hellospring..*(..))") // 원하는 적용 대상 선택
  public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

    long start = System.currentTimeMillis();

    System.out.println("START: " + joinPoint.toString());

    try {
      return joinPoint.proceed();
    } finally {
      long finish = System.currentTimeMillis();
      long timeMs = finish - start;

      System.out.println("END: " + joinPoint.toString()+ " " + timeMs + "ms");
    }
  }
}
```

프록시는 AOP 적용 대상(타겟)을 감싸서 타겟의 요청을 대신 처리한다. 클라이언트에서 타겟을 호출하면 타겟을 감싸고 있는 프록시가 호출되어 타겟 메소드 실행전에 선처리, 타겟 메소드 실행 후 후처리를 실행시키도록 구성되어 있다. 즉, 프록시는 호출을 가로채 부가 기능을 수행 후 원래의 타겟 메소드를 호출한다.

### 참고
- https://velog.io/@kai6666/Spring-Spring-AOP-%EA%B0%9C%EB%85%90
- https://engkimbs.tistory.com/746
- https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%9E%85%EB%AC%B8-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8
- https://n1tjrgns.tistory.com/261

## PSA
