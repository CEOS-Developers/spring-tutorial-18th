# CEOS 백엔드 스터디 - 0주차

- Spring Triangle : 스프링의 삼대 요소, **Ioc/DI, AOP, PSA**

## IoC/ DI

### IoC( Inversion  of Control )

- 본디 일반적인 의존성에 대한 제어권은 개발자가 가지고 있음
    
    ```java
    public class AController {
     	private ARepository ownerRepository = new ARepository();
    }
    ```
    

---

- IoC : 제어의 역전
- 객체나 메서드의 호출을 개발자가 결정하는 것이 아닌, 객체의 생성, 생명주기의 관리까지 모든 객체에 대한 제어권이 바뀌었다는 것
    
    ```java
    public class A {
       @Autowired
       private B b;
    }
    ```
    
- IoC 컨테이너 : 인스턴스 생성부터 소멸까지의 인스턴스 생명주기와 의존성을 관리해주는 컨테이너
→ 스프링 프레임워크도 이를 제공 ⇒ 스프링 컨테이너
- IoC 장점
    1. 객체 간 결합도를 낮춤
    2. 유연한 코드 작성 가능
    3. 가독성 증진
    4. 코드 중복 방지
    5. 유지 보수 용이
- *⇒ IoC를 적용하면 스프링 컨테이너에서 미리 생성, 관리하고 있는 **객체를 주입 받아** 사용하기만 하면 됨*

### DI ( Dependency Injection)

- 의존성 주입
- 각 클래스 간의 의존성을 빈 설정 정보(Bean Definition)를 바탕으로 컨테이너가 자동으로 주입해주는 것
- → 코드 내에서 직접적인 연관관계가 발생하지 않아 각 클래스의 변경이 자유로워 짐 : 느슨한 결합

---

다양한 의존관계 주입 방법

- 생성자 주입: 불변, 필수인 의존관계에 사용

```java
@Component
public class OrderServiceImpl implements OrderService {
   private final MemberRepository memberRepository; //무조건 값이 있어야함 = final

   @Autowired
   public OrderServiceImpl(MemberRepository memberRepository) {
       this.memberRepository = memberRepository;
   }
}
```

- 수정자 주입(setter): 선택, 변경가능성이 있는 의존관계에 사용

```java
@Component
public class OrderServiceImpl implements OrderService {
   private MemberRepository memberRepository;

   @Autowired
   public void setMemberRepository(MemberRepository memberRepository) {
       this.memberRepository = memberRepository;
   }
}
```

- 필드 주입: 애플리케이션의 실제 코드와 관계 없는 테스트 코드, 스프링 설정을 목적으로 하는 @Configuration 같은 곳에서만 특별한 용도로 사용

```java
@Component
public class OrderServiceImpl implements OrderService {
   @Autowired
   private MemberRepository memberRepository;
}
```

- 요즘에는 스프링을 포함한 DI 프레임워크 대부분이 생성자 주입을 권장함
    - lombok 라이브러리를 사용하면 `@RequiredArgsConstructor` 기능을 사용하여 final이 붙은 필드를 모아서 생성자를 자동으로 만들어줌

## AOP

### AOP( Aspect Oriented Programming )

- 관점 지향 프로그래밍
- 어떤 로직을 기준으로 핵심적인 관점, 부가적인 관점으로 나누어서 보고 그 관점을 기준으로 각각 모듈화
- → 부가적인 관점에서 보면 before, after 메서드가 공통되는 것처럼, 부가적인 관점에서 공통된 요소를 추출하는 것
    
    ![Untitled](https://github.com/yj-leez/spring-tutorial-18th/assets/77960090/ae2094fe-0499-47a9-a78d-1bc71d085e85)

    

---

### AOP 적용 방식

1. 컴파일 시점
    - 컴파일 시점에 바이트 코드를 조작하여 AOP가 적용된 바이트 코드를 생성하는 방법
    - AspectJ가 제공하는 특별한 컴파일러를 사용해야 하기 때문에 특별할 컴파일러가 필요한 단점
2. 클래스 로딩 시점
    - 순수하게 컴파일 한 뒤, 클래스를 로딩하는 시점에 클래스 정보를 변경하는 방법
3. 런타임 시점
    - 스프링AOP가 사용하는 방법
    - A라는 클래스 타입의 빈을 만들 때 A 타입의 Proxy Bean을 만들어 Proxy Bean이 Aspect 코드를 추가하여 동작하는 방법
    - 프록시는 메서드 오버라이딩 개념으로 동작하기 때문에 메서드에만 적용 가능 → **스프링 빈에만 AOP 적용 가능**

### AOP 구현

```java
// 타켓 메서드의 실행 시간을 측정하는 로직
@Component
@Aspect
public class PerfAspect {

    @Around("execution(* com.example..*.EventService.*(..))")
    public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.currentTimeMillis();
        Object reVal = pjp.proceed();
        System.out.println(System.currentTimeMillis() - begin);
        return reVal;
    }
}
```

- 스프링 AOP는 스프링 빈에만 적용 가능 → `@Component` : 빈으로 등록
- `@Aspect` : 해당 클래스가 Aspect임을 명시
- `@Around` : logPerf() 메서드는 @Around 어노테이션의 execution을 통해 Advice를 적용할 범위를 지정
    - Advice : 실질적으로 프록시에서 수행하게 되는 로직을 정의하게 되는 곳
    - → 여기서는 com.example 아래 패키지 경로의 EventService 객체의 모든 메서드에 이 Aspect 적용
    - 외에도 `@Before`, `@AfterReturning`, `@AfterThrowing`, `@After` 애노테이션 제공

## PSA

### PSA( **Portable Service Abstraction )**

- 환경의 변화와 상관없이 일관된 방식의 기술로의 접근 환경을 제공하는 추상화 구조
- 스프링은 특정 기술에 직접적 영향을 받지 않게끔 객체를 POJO 기반으로 한 번씩 더 추상화된 Layer를 가지고 있음

---

- Spring Web MVC
    - 뒷단에 Spring이 제공해주는 기능이 숨겨져있는 `@Controller`, `@GetMapping` 등의 애노테이션을 사용하여 간편하게 서블릿 개발
    - → 서블릿을 low level로 개발하지 않아도 됨
- Spring Transaction
    - `@Transactional` 을 활용하여 low level로 트랜잭션 처리할 필요 없이 간단하게 트랜잭션 처리 가능

### 참고
    
    [https://isoomni.tistory.com/entry/TISPRING-IOC-DI-정의-장점](https://isoomni.tistory.com/entry/TISPRING-IOC-DI-%EC%A0%95%EC%9D%98-%EC%9E%A5%EC%A0%90)
    
    [https://velog.io/@backtony/Spring-AOP-총정리](https://velog.io/@backtony/Spring-AOP-%EC%B4%9D%EC%A0%95%EB%A6%AC)
<br/>
<br/>
<br/>
<br/>

# CEOS 백엔드 스터디 - 1주차

## 1️⃣ spring-boot-tutorial-18th 를 완료해요
<img width="919" alt="스크린샷 2023-09-23 오후 8 41 55" src="https://github.com/yj-leez/spring-tutorial-18th/assets/77960090/f35bb835-e9d4-4072-8a14-bf02c7b029a5">


## 2️⃣ H2 데이터베이스를 연결해요
<img width="450" alt="스크린샷 2023-09-23 오후 6 03 13" src="https://github.com/yj-leez/spring-tutorial-18th/assets/77960090/c5b014ff-c728-48a3-8e14-a08c80b61fef">


## 3️⃣ 스프링 어노테이션을 심층 분석해요

### 어노테이션

- 사전적 의미는 주석, 자바에서는 특별한 의미를 담은 주석
- 자바코드에 주석처럼 달아 **프로그램에게 추가적인 정보를 제공해주는 메타데이터**
- 자바나 스프링이 제공해주는 것도 있고, 사용자가 직접 만들 수도 있음
- 어노테이션의 용도
    - 컴파일러에게 코드 작성 문법 에러를 체크하도록 정보를 제공
    - 소프트웨어 개발 툴이 빌드나 배치시 코드를 자동으로 생성할 수 있도록 정보를 제공
    - 실행시(런타임시) 특정 기능을 실행하도록 정보를 제공
- 어노테이션을 실행하는 순서
    1. 어노테이션을 정의
    2. 클래스에 어노테이션을 배치
    3. 코드가 실행되는 중에 Reflection을 이용하여 추가 정보를 획득하여 기능을 실시
        
        ** 자바 리플렉션 : 다른 언어에는 존재하지않는 특별한 기능으로, 컴파일 시간이 아닌 실행 시간에 동적으로 특정 클래스의 정보를 객체를 통해 분석 및 추출해내는 프로그래밍 기법
        

### @Component, @Bean

- @Componenet : 개발자가 직접 작성한 Class를 Bean으로 등록할 때 사용
    
    ```java
    @Component
    public class Student {
         public Student ( ) { system.out printin("hi"); }
    }
    
    @Configuration
    @ComponentScan
    public class AppConfig {
    }
    ```
    
    **→ @Configuration이 붙은 클래스에 @ComponentScan를 붙여 @Component가 붙은 객체를 찾아 스프링 컨테이너에 자동으로 빈 등록**
    
- @Bean : 개발자가 직접 제어가 불가능한 외부라이브러리 등을 Bean으로 등록할 때 사용
    
    ```java
    @Configuration
    public class ApplicationConfig {
        @Bean (name="myarray")
        public ArrayList<String> array () { return new ArrayList<String>(); }
    }
    ```
    
    @Configuration : 1개 이상의 Bean을 등록하고 있다고 명시할 때 사용, 해당 클래스가 Bean 구성 Class임을 알려줌. 그래서 @Bean을 사용하는 class의 경우 @Configuration을 같이 사용.
    
    **→ @Configuration 구성 정보에 @Bean 어노테이션을 통해 스프링 컨테이너에 직접 빈으로 등록**
    

### 스프링 컨테이너 생성 과정

1. 스프링 컨테이너 (ApplicationContext) 생성
    
    <img width="705" alt="1" src="https://github.com/yj-leez/spring-tutorial-18th/assets/77960090/866319b0-6e79-44db-a68c-48e9f611facb">
    
    - 스프링 컨테이너 생성할 때는 구성 정보를 지정해줘야함. 여기서는 AppConfig.class를 구성정보로 지정하였음.
2. 스프링 빈 등록
    
    <img width="703" alt="2" src="https://github.com/yj-leez/spring-tutorial-18th/assets/77960090/d825acec-34b4-47a7-8e83-a4f7aff3a826">
    
    - 스프링 컨테이너는 파라미터로 넘어온 설정 클래스 정보를 사용해서 스프링 빈을 등록
3. 스프링 빈 의존관계 설정 - 준비
    
    <img width="703" alt="3" src="https://github.com/yj-leez/spring-tutorial-18th/assets/77960090/779517c2-4a01-45b0-9560-f672a427c016">
    
4. 스프링 빈 의존관계 설정 - 완료
    
    <img width="703" alt="4" src="https://github.com/yj-leez/spring-tutorial-18th/assets/77960090/af614432-b540-4281-a79b-d2b16b188032">

    - 스프링 컨테이너는 설정 정보를 참고해서 의존관계를 주입(DI)

### 스프링 컨테이너

<img width="533" alt="5" src="https://github.com/yj-leez/spring-tutorial-18th/assets/77960090/9f53e745-c80f-4f9c-a949-f6cbfcc6a6f9">

- BeanFactory: 스프링 컨테이너의 최상위 인터페이스, getBean()을 제공
- ApplicationContext: BeanFactory의 기능을 상속받아 빈 관리 기능 + 부가적인 관리 기능 제공

---

스프링 컨테이너의 기본 빈 등록 방식

- 싱글톤 컨테이너 : 싱글톤 패턴 문제를 해결하기 위해 객체 인스턴스를 싱글톤(1개만 생성)으로 관리
    
    덕분에 고객의 요청이 올 때 마다 객체를 생성하는 것이 아니라, 이미 만들어진 객체를 공유하여 효율적으로 재사용 가능
    
    <img width="566" alt="6" src="https://github.com/yj-leez/spring-tutorial-18th/assets/77960090/7d7bcf22-33f7-47fd-9ac3-de905b2af7ba">
    
   
## 4️⃣ **단위 테스트와 통합 테스트 탐구**

> 예를 들어, 슬라임이라는 몬스터가 있고 유저가 슬라임을 때려잡는 상황일  때 다음과 같은 함수가 있다고 가정,
> 
> - 유저가 슬라임에게 달려가는 Move 함수
> - 유저가 슬라임을 공격하는 Attack 함수
> - 슬라임을 때려잡은 후 전리품을 수집하는 Gather 함수
> 
> **단위 테스트**는 Move, Attack, Gather 함수가 잘 동작하는지 (여러 가지 입력 값을 줘봐서 제대로 된 출력 값이 나오는지) 함수 하나하나를 테스트를 하는 것에 비유할 수 있음
> 
> **통합 테스트**는 유저가 슬라임을 때려잡고 전리품을 수집할 텐데 실제로 **데이터베이스**에 수집한 전리품이 잘 들어갔는지 테스트를 하는 것에 비유할 수 있음
> 

- 단위 테스트 : 전체 코드 중 작은 부분을 테스트하는 것으로, 간단하고 명확해야함
    
    코드의 설계가 좋지 못하다면, 단위 테스트도 어려워지므로, 함수 하나하나 테스트 코드를 작성하는 단위 테스트는 좀 더 나은 코드를 만들 수 있도록 도와줌
    
    만약 테스트와 네트워크나 데이터베이스 같은 외부 리소스가 포함된다면 유닛 테스트가 아님
    
- 통합 테스트 : 각각의 시스템들이 서로 어떻게 상호작용하고 제대로 작동하는지 테스트하는 것을 의미
    
    두 개의 다른 분리된 시스템끼리 잘 통신하고 있는지 증명하고 싶을 때 주로 사용
    

참고

[https://pearlluck.tistory.com/332](https://pearlluck.tistory.com/332)

[https://cjwoov.tistory.com/9](https://cjwoov.tistory.com/9)

