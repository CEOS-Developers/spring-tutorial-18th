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

## AOP

## PSA
