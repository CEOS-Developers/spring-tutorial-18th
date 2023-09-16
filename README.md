# spring-tutorial-18th
CEOS 18th Backend Study - Spring Tutorial


## 스프링의 3대 요소(Spring Triangle)
### IoC/DI
***
* IoC - Inversion of Control : 제어의 역전
* DI - Dependency Injection : 의존성 주입
- **메소드나 객체의 호출작업을 개발자가 결정하는 것이 아니라, 외부에서 결정되는 것을 의미**
- 객체의 의존성을 역전시켜 객체 간의 결합도를 줄이고 유연한 코드 작성 가능

  → 가독성 및 코드 중복, 유지 보수 용이
- 객체의 생성 및 실행 과정을 [객체 생성 -> 의존성 객체 생성/주입 -> 의존성 객체 메소드 호출] 의 순서로 본다면   
기존에는 클래스 내부에서 의존성 객체를 생성했지만   
스프링에서는 의존성 객체를 생성하는 것이 아니라 주입한다   
스스로 만드는 것이 아니라 제어권을 스프링에게 위임하여 스프링이 만들어놓은 객체를 주입한다는 뜻
- 스프링이 모든 의존성 객체를 스프링이 실행될 때 다 만들어주고 필요한 곳에 주입해주기 때문에 Bean들은 싱글턴 패턴의 특징을 가지고   
제어의 흐름을 사용자가 컨트롤 하는 것이 아니라 스프링에게 맡겨 작업을 처리하게 된다 

<br/>

### AOP
***
* AOP - Aspect Oriented Programming : 관점 지향 프로그래밍
* 어떤 로직을 핵심적인 관점(비즈니스 로직)과 부가적인 관점(데이터베이스 연결, 로깅, 파일 입출력)으로 나누어서 보고   
그 관점을 기준으로 각각 모듈화
* AOP에서 로직을 모듈화한다는 것은 코드를 부분적으로 나누어 모듈화하겠다는 의미하고   
  이때, 소스 코드 상에서 다른 부분에 계속 반복해서 쓰는 코드들을 발견할 수 있는데   
  이것을 흩어진 관심사 (Crosscutting Concerns)라고 한다 → 공통 관심 사항
- 이 흩어진 관심사를 Aspect로 모듈화하고 핵심적인 비즈니스 로직에서 분리하여 재사용하겠다는 것이 AOP의 취지
* AOP가 필요한 상황 : 모든 메소드의 호출 시간을 측정하고 싶다면?
    * 천 개가 넘는 메소드에 다 들어가서 시작과 끝에 호출 시간 남기기   
각 메소드 시작과 끝에 시간 측정 로직 집어넣기  
호출 시간 초단위로 계산했는데 ms로 바꾸라고 하면 또 각각 다 수정해야 한다 
    * 공통 관심 사항(cross-cutting concern) vs 핵심 관심 사항(core concern) 구분
  ```java
    public Long join(Member member) { //회원가입
        long start = System.currentTimeMillis();
  
        try {
            validateDuplicateember(member); //중복회원 검증
            memberRepository.save(member);
            return member.getId();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join = " + timeMs + "ms");
        }
    }
  ```
    * 위 코드의 문제점 
      * 회원가입, 회원 조회에 시간을 측정하는 기능은 **핵심 관심 사항**이 아니다
      - try문 안의 기능은 핵심 비즈니스 로직이지만 시간을 측정하는 로직은 핵심이 아니다
      - 시간을 측정하는 로직은 **공통 관심 사항**이다
      - 시간을 측정하는 로직과 핵심 비즈니스의 로직이 섞여서 유지보수가 어렵다
      - 시간을 측정하는 로직을 별도의 공통 로직으로 만들기 매우 어렵다
      - 시간을 측정하는 로직을 변경할 때 모든 로직을 찾아가면서 변경해야 한다
    
    ```java
    @Aspect
    @Component
    public class TimeTraceAop {
        @Around("execution(* hello.hellospring..*(..))")
        public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
            long start = System.currentTimeMillis();
            ystem.out.println("START : " + joinPoint.toString());
            try {
            return joinPoint.proceed();
            } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END : " + joinPoint.toString() + " " + timeMs + "ms");
            }
        }
    }
    ```
    * 위 코드 설명
      * **@Aspect** 어노테이션 사용해야 AOP로 사용 가능
      * **@Component** 스프링빈으로 등록 (@Component 쓰기도 하는데 스프링빈에 직접 등록 더 선호)
      * **@Around**로 이 공통 관심 사항을 어디에 적용할 지 설정, 타겟팅  
  실행하는 패키지명, 밑에 하위 전체로 설정, 타겟은 따로 다 설정할 수 있음
      * **joinPoint**  
  argument 뭔지, 어느 타겟에서 호출하는지, 지금 내가 누군지 등 여러 메소드   
    호출이 될 때마다 중간에서 인터셉트가 걸리는 것  
  필요하면 중간에 어떤 조건이면 넘어가지마 라는 설정도 가능   
  이렇게 중간에 인터셉트해서 사용할 수 있는 기술이 AOP

  * 해결
    * 회원가입, 회원 조회 등 핵심 관심사항과 시간을 측정하는 공통 관심 사항 분리
    - 시간을 측정하는 로직을 별도의 공통 로직으로 만들었다
    - 핵심 관심 사항을 깔끔하게 유지할 수 있다
    - 변경이 필요하면 이 로직만 변경하면 된다
    - 원하는 적용 대상을 선택할 수 있다

### PSA   
***
* PSA - Portable Service Abstraction : 추상화 서비스
*  ex. @Transactional 어노테이션을 선언하는 것 만으로 별도의 코드 추가 없이 트랜잭션 서비스를 사용할 수 있다
*  내부적으로 트랜잭션 코드가 추상화되어 숨겨져 있기 때문이고   
   이렇게 추상화 계층을 사용하여 어떤 기술을 내부에 숨기고 개발자에게 편의성을 제공해주는 것을 서비스 추상화(Service Abstraction) 라고 한다
* JDBC, JPA 둘 중 어떤 방법을 사용하더라도 DB에 접근할 수 있고, @Transactional 어노테이션을 이용해 트랜잭션을 유지하는 기능을 추가할 수 있다   
-> 이렇게 하나의 추상화로 여러 서비스를 묶어둔 것을 Spring에서 Portable Service Abstraction 이라고 한다 
* Spring Web MVC   
@Controller를 통해 요청을 매핑할 수 있는 컨트롤러 역할을 수행하는 클래스가 된다
* Spring Transaction   
@Transaction을 이용해 단순히 메소드에 어노테이션을 붙여줌으로써 트랜잭션 처리가 이루어진다
* Spring Cache   
@Cacheable 어노테이션을 붙여줌으로써 구현체를 크게 신경쓰지 않아도 필요에 따라 바꿔 쓸 수 있다 
