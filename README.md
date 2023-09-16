# spring-tutorial-18th
CEOS 18th Backend Study - Spring Tutorial
  
  
  
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
    
    [https://code-lab1.tistory.com/193](https://code-lab1.tistory.com/193)
    
    [https://dev-coco.tistory.com/83](https://dev-coco.tistory.com/83)
