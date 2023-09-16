# Spring이 지원하는 기술들

## 1. IoC/DI (Inversion of Control / Dependency Injection)

### 1-1. 개념 정리

- IoC : 기존에는 개발자가 자바 객체를 생성하고 객체간의 의존관계를 연결시키는 제어권을 가지고 있었음.

```
public class A
{

   private B b;

   public A()
   b = new B();
 }
}
```

이것을 서블릿과 EJB를 관리하는 컨테이너 넘긴 것이 IoC의 핵심임. 객체에 대한 제어권이 컨테이너에게 넘어가면서 객체의 생명주기를 관리하는 권한 또한 컨테이너들이 전담하게 됨. 객체의 생성에서부터 생명주기의 관리까지 모든 객체에 대한 제어권이 바뀐것을 의미하는 것이 제어권의 역전이 IoC임.

```
public class A
{
	@Autowired
	private B b;
}
```

- DI : DI는 Spring에서 새롭게 지원하는 IoC의 한 형태임. 각 계층과C클래스 사이에 필요로 하는 의존관계가 있다면 이를 컨테이너가 자동적으로 연결시켜 주는 것임.

  - cf) 의존도 주입에서 의존도의 의미란? : A클래스 내부에서 B,C 클래스의 메소드를 활용할 때, A클래스의 로직이 변경되어 B,C 클래스로 보내는 객체가 변경된다면 기존과 같게 변경하는 로직을 추가해야함. 이때 A와 B,C는 서로에 대해 의존성이 있는 것

    - DI의 방식 :

      - 필드 주입 : 의존성을 주입하고 싶은 필드에 @Autowired 어노테이션 붙이기

        ```
        public class Dessert
        {
            @Autowired
            private final CakeService cakeService;
            @Autowired
            private final DrinkService drinkService;
        }
        ```

        - 수정자 주입 : setter 메소드에 @Autowired 어노테이션 붙이기

        ```
        public class A
        {
          private B b;

          public void setB(B b)
          {
              this.b = b;
          }
        }
        ```

        - 생성자 주입 : 생성자를 사용하여 의존성을 주입하는 방식

          ```
          public class A
          {
              private B b;

              public A(B b)
              {
                  this.b = b;
              }
          }
          ```

        - 생성자 주입을 사용해야하는 이유 : NullPointerException을 막을 수 있음, 불변성을 활용할 수 있음, 단일 책임 원칙(SRP)을 지킬 수 있도록 유도함

### 1-2. 장점

- IoC :
  - 객체 간 결합도를 낮출 수 있음
    - 유연한 코드 작성 가능
    - 가독성 증진
    - 코드 중복 방지
    - 유지 보수 용이
- DI :
  - 모듈 간의 결합도가 낮아짐
    - 모듈 간 유연성이 높아짐

## 2. AOP (Aspect-Oriented Programming)

### 2-1. 개념 정리

- Aspect Oriented Programming의 약자로 관점 지향 프로그래밍이라고 함

  - 모듈화란 어떤 공통된 로직이나 기능을 하나의 단위로 묶는 것

- cross-cutting concerns 를 분리하여 중복 코드를 줄이고 모듈성을 향상시킬 수 있음

```
class A {
	method a() {
    	AAAA

        method a가 하는 일들

        BBBB
    }

    method b() {
    	AAAA

        method b가 하는 일들

        BBBB
    }
}

class B {
	method c() {
    	AAAA

        method c가 하는 일들

        BBBB
    }
}
```

위 같은 경우 같은 일을 하는 AAAA,BBBB가 바뀔 경우 전부 다 찾아서 바꾸어야함

- AOP의 로직 흐름
  ![](https://velog.velcdn.com/images/aeyongdodam/post/95b568f4-9209-4e69-84cc-a509cadeda34/image.png)
  출처 : https://engkimbs.tistory.com/746

- Spring AOP는 프록시 디자인 패턴을 사용해서 AOP 효과를 냄.(프록시 패턴을 사용하면 어떤 기능을 추가하려 할때 기존 코드를 변경하지 않고 기능을 추가할 수 있음.)

  - 어떤 클래스가 Spring AOP의 대상이라면 그 기존 클래스의 빈이 만들어질때 Spring AOP가 프록시(기능이 추가된 클래스)를 자동으로 만들고 원본 클래스 대신 프록시를 빈으로 등록함. 그리고 원본 클래스가 사용되는 지점에서 프록시를 대신 사용함

- 주요 개념
  - Aspect : 위에서 설명한 흩어진 관심사를 모듈화 한 것. 주로 부가기능을 모듈화함.
  - Target : Aspect를 적용하는 곳 (클래스, 메서드 등)
  - Advice : 실질적으로 어떤 일을 해야할 지에 대한 것, 실질적인 부가기능을 담은 구현체
  - JointPoint : Advice가 적용될 위치, 끼어들 수 있는 지점임.
  - PointCut : JointPoint의 상세한 스펙을 정의한 것임.

### 2-2. 장점

- 코드 중복 감소함
- 모듈성을 증가시킬 수 있음
- 흩어진 관심사(cross-cutting concerns)의 분리

## 3. PSA (Portable Service Abstraction)

### 3-1. 개념 정리

- 서비스 추상화 : 추상화 계층을 사용하여 어떤 기술을 내부에 숨기고 개발자에게 편의성을 제공해주는 것임.
- 어댑터 디자인 패턴을 이용해서 하나의 추상화로 여러 서비스를 묶어둔 것을 Portable Service Abstraction(PSA)라고 함
  - ex) DB에 접근하는 방법은 여러가지가 있음. 기본적으로 Jdbc를 통해 접근(DatasourceTransactionManager)할 수 있으며 ORM을 이용하고자한다면 JPA(JpaTransactionManager)를 통해서 접근할 수도 있음. 어떠한 경우라도 @Transactional 어노테이션을 이용하면 트랜잭션을 유지하는 기능을 추가할 수 있음.
- 서블릿 어플리케이션임에도 불구하고 서블릿이 전혀 존재하지 않음. 단지 @Controller 어노테이션이 붙어있는 클래스에서 @GetMapping, @PostMapping과 같은 @RequestMapping 어노테이션을 사용해서 요청을 매핑하는 것임.
- 환경과 세부 기술의 변화에 관계없이 일관된 방식으로 기술에 접근할 수 있게 해줌. -> 객체를 POJO 기반으로 한번 씩 더 추상화한 Layer를 가지고 있기 때문임.
  - POJO : 오래된 방식의 간단한 자바 오브젝트 - 특정 기술에 종속되어 동작하는 것이 아닌 순수한 자바 객체 - Java EE 등 중량 프레임워크들에 사용되는 프레임 워크에 종속된 무거운 객체에 반발해서 사용한 용어 - 객체 지향적인 원리에 충실 - 환경과 기술에 종속되지 않고 필요에 따라 재활용할 수 있는 방식으로 설계됨

### 3-2. 장점

- 기술과의 결합도 감소함
- 코드 포터빌리티 향상시킬 수 있음.
- 유지보수성 증가함.

## 참고 자료

https://taeyoungcoding.tistory.com/280

https://velog.io/@ohzzi/Spring-DIIoC-IoC-DI-%EA%B7%B8%EA%B2%8C-%EB%AD%94%EB%8D%B0

https://velog.io/@gillog/Spring-DIDependency-Injection

https://isoomni.tistory.com/entry/TISPRING-IOC-DI-%EC%A0%95%EC%9D%98-%EC%9E%A5%EC%A0%90

https://ss-o.tistory.com/137
