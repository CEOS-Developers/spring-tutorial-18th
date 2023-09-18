# POJO 프로그래밍

> ```스프링의 특징과 목표```
>
>분리됐지만 반드시 필요한 엔터프라이즈 서비스 기술을 POJO 방식으로 개발된 애플리케이션 핵심 로직을 담은 코드에 제공한다.

## POJO
![image](https://github.com/gmkim20713/spring-study/assets/68195241/0351cc22-0d78-48ba-ba3e-ec8cf1dd9828)

- POJO = Plain Old Java Object
- 스프링 애플리케이션은 POJO를 이용해서 만든 **애플리케이션 코드**와, POJO가 어떻게 관계를 맺고 동작하는지를 정의해놓은 **설계정보**로 구분된다.
- POJO로 개발할 수 있게 해주는 가능기술(Enabling Technology)
  - IoC/DI
  - AOP
  - PSA

## POJO의 조건
- 특정 규약에 종속되지 않는다.
- 특정 환경에 종속되지 않는다.
## POJO의 장점
- 특정한 기술과 환경에 종속되지 않는다.
- 자동화된 테스트에 유리하다.
- 객체지향적인 설계를 자유롭게 적용할 수 있다.
## POJO 프레임워크
- POJO 프로그래밍이 가능하도록 기술적인 기반을 제공하는 프레임워크 = POJO 프레임워크
  - Ex) 스프링 프레임워크, 하이버네이트


# IoC (Inversion of Control)
> ```Inversion_of_Control``` ```제어의_역전```

- 객체의 생성과 관리를 개발자가 하는 것이 아니라 프레임워크가 대신하는 것  
- 스프링 컨테이너가 필요에 따라 개발자 대신 Bean들을 제어
  
```java  
// 개발자가 제어하는 경우
public class A {
    private B b;
 
    public A() {
        b = new B();
    }
} 
  
// 프레임워크가 제어하는 경우
public class A {
    
    @Autowired
    private B b; // 필드 주입방식
}
```

## IoC가 필요한 이유
- 역할과 관심을 분리 -> 응집도 증가 & 결합도 감소 -> 유연한 코드 작성 가능
- 프로그램의 진행 흐름과 구체적인 구현 분리
- 비즈니스 로직에 집중
- 객체 간 의존성 낮아짐

## IoC를 구현하는 Pattern
- Service Locator
- Factory
- Abstract Factory
- Strategy
- **Template Method**
- **Dependency Injection**

# DI (Dependency Injection)

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

# AOP (Aspect Oriented Programming)

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

# PSA (Portable Service Abstraction)

> 이식 가능한(일관성 있는) 서비스 추상화; 어느 기술을 사용하든 일관된 방식으로 처리하도록 하는 것
>
- 서비스 추상화(Service Abstraction) : 추상화 계층을 사용해서 어떤 기술을 내부에 숨기고 개발자에게 편의성을 제공해주는 것
- 서비스 추상화(Service Abstraction)로 제공되는 기술을 다른 기술 스택으로 간편하게 바꿀 수 있는 확장성을 갖고 있는 것 = PSA
- PSA 예시
    - Spring Web MVC
    - Spring Transaction
    - Spring Cache

# 참고 자료
[스프링의 콘셉트(IoC, DI, AOP, PSA) 쉽게 이해하기](https://shinsunyoung.tistory.com/133)

[스프링의 3가지 핵심 프로그래밍 모델](https://velog.io/@mon99745/스프링의-3가지-핵심-프로그래밍-모델)

[토비의 스프링 3.1 Vol. 1 스프링의 이해와 원리](http://www.acornpub.co.kr/book/toby-spring3.1-vol1)
