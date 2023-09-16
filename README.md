# Spring 핵심 프로그래밍 모델

## IoC (Inversion of Control)

> 제어의 역전
>
- 객체의 생성과 관리를 개발자가 하는 것이 아니라 프레임워크가 대신하는 것
- 스프링 컨테이너가 필요에 따라 개발자 대신 Bean들을 제어

```java
// 객체 생성
public class A {
	b = new B();
}

// 객체 생성 x, 객체 할당 (가져와 사용)
public class A {
	private B b;
}
```

## DI (Dependency Injection)

> 의존성 주입
>
- IoC 구현을 위해 사용하는 방법 =  DI
- 의존성 : 한 객체가 다른 객체를 사용할 때
- 외부에서 객체를 주입받아 사용(두 객체 간의 관계를 결정)하는 것
- 사용 목적 : 높은 응집성과 낮은 결합도
    - 클래스 레벨에서는 의존관계가 고정 X
    - 런타임 시에 관계를 주입 → 유연성을 확보하고 결합도를 낮춤
- 장점
    - 관심사 분리
    - 테스트 작성 용이
    - 개방폐쇄의 원칙(OCP)
- **`@Autowired`** : 스프링 컨테이터 빈 주입

```java
// 객체 주입, 객체 생성 x
public class A {
	@Autowired
	B b;
}
```

## AOP (Aspect Oriented Programming)

> 관점 지향 프로그래밍; 프로그래밍을 할 때 핵심 관점 & 부가 관점으로 나누어 개발(모듈화)하는 것
>
> - 핵심 관점 : 소프트웨어 시스템의 주요 기능이나 핵심 비즈니스 로직과 관련
> - 부가 관점 : 핵심 관점 이외의 기능이나 시스템 전반에 걸쳐 중복되거나 공통적으로 발생
- 목적 : 흩어진 관심사를 Aspect로 모듈화하고 핵심적인 비즈니스 로직에서 분리하여 재사용
    - 흩어진 관심사 : 소스 코드 상 다른 부분이나 계속 반복해서 쓰는 코드
- 장점
    - 핵심 관점 코드에만 집중
    - 변경과 확장에 유연하게 대응

![image](https://github.com/CEOS-Developers/spring-tutorial-18th/assets/68195241/a758b7ac-9fc7-4c8c-8417-ac01e693305b)

## PSA (Portable Service Abstraction)

> 이식 가능한(일관성 있는) 서비스 추상화; 어느 기술을 사용하든 일관된 방식으로 처리하도록 하는 것
>
- 서비스 추상화(Service Abstraction) : 추상화 계층을 사용해서 어떤 기술을 내부에 숨기고 개발자에게 편의성을 제공해주는 것
- 서비스 추상화(Service Abstraction)로 제공되는 기술을 다른 기술 스택으로 간편하게 바꿀 수 있는 확장성을 갖고 있는 것 = PSA
- PSA 예시
    - Spring Web MVC
    - Spring Transaction
    - Spring Cache

## 참고 자료
[스프링의 콘셉트(IoC, DI, AOP, PSA) 쉽게 이해하기](https://shinsunyoung.tistory.com/133)

[스프링의 3가지 핵심 프로그래밍 모델](https://velog.io/@mon99745/스프링의-3가지-핵심-프로그래밍-모델)