<h1 align="center">
      Spring Tutorial 🌱
    <br/>
</h1>

<p align="center">
    <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
    <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
    <img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"/><br/>
    <b>CEOS 18th Backend Study</b><br><br>
</p>

<div align="center">

### 📒 목차
[0주차 | 스프링의 이해](#0주차-미션-주제) <br>
[1주차 | 스프링 튜토리얼](#1주차-미션-주제) <br><br>

### 0주차 미션 주제
### Spring이 지원하는 기술들을 조사한다 👩🏻‍💻
<br><br>

</div>

<h2 align="center">
🌼 IoC/DI <br>
</h2>

<p align="center">
✦ IoC (Inversion of Control / 제어의 역전)<br>
• 개발자가 아니라, <b>스프링 프레임워크</b>가 프로그램을 <b>제어</b>한다.<br>
• 빈(Bean, 자바 객체)의 생성과 생명주기를 스프링 컨테이너가 담당한다.<br><br>
<img width="350" alt="springcontainer" src="https://github.com/jongmee/spring-tutorial-18th/assets/101439796/6a0aec96-e82b-4af9-a9f3-e761ee551f69"><br><br>
• Beanfactory와 ApplicationContext가 IoC 오브젝트이다.<br><br>
✦ DI (Dependency Injection / 의존성 주입)<br>
• 변수에 값을 할당하는 모든 곳에 <b>의존 관계</b>가 생긴다.<br>
• 대입 연산자(=)에 의해 변수에 값이 할당되는 순간에 의존이 생긴다. 대표적으로 <b>new</b>가 쓰이는 코드이다. <br>
</p>

```java
                                class Chef {
                                  private Pizza pizza;
                                
                                  public Chef() {
                                    pizza = new Pizza();
                                  }
                                }
```
<p align="center">
• Chef 클래스에서 Pizza 클래스에 의존하기 때문에 유연성이 떨어진다. Chef 클래스 코드를 수정해야 한다.
</p>

```java
                          class Chef {
                            private Pizza pizza;

                            public Chef(Pizza pizza) {
                              this.pizza = pizza();
                            }
                          }
                          class Client { 
                            private Chef chef = new Chef(new Pizza());

                            public void chageFoodToPasta() {
                              chef = new Chef(new Pasta());
                            }
                          }
```
<p align="center">
• Client 클래스가 의존 관계를 주입해주기 때문에 Chef 클래스 코드를 변경할 필요가 없다. 즉, 의존성이 줄어든다.<br>
• 스프링에서는 DI를 @Autowired 를 통해 수행한다. <br>
</p>

```java
                        @Configuration
                        class Client {
                          private Pasta pasta;
                        
                          @Autowired
                          public Client(Pasta pasta) {
                            this.pasta = pasta;
                          }
                        }
```
<p align="center">
• @Autowired가 적용된 변수 및 메서드에 스프링이 관리하는 Bean을 자동으로 매핑해준다.<br>
</p>

<h2 align="center">
🌼 AOP
</h2>

<p align="center">
✦ AOP (Aspect Oriented Programming / 관점 지향 프로그래밍)<br>
• 핵심적인 관점(핵심 비즈니스 로직), 부가적인 관점으로 나누어서 로직을 모듈화한다.<br>
• 애플리케이션 전체에 걸쳐 사용되는 부가 기능을 모듈화하여 재사용할 수 있도록 지원하는 것이다.<br>
• OOP는 비즈니스 로직의 모듈화라면, AOP는 <b>인프라 혹은 부가 기능의 모듈화</b>이다. <br>ex. 로깅, 동기화, 오류 검사, 성능 최적(캐싱) 등<br>
• 프록시는 메서드 오버라이딩 개념으로 동작하기 때문에 메서드에만 적용할 수 있다. <br>
</p>

<p align="center">
❓<b>프록시란?</b><br>
제어 흐름을 조정하기 위한 목적으로 중간에 <b>대리자</b>를 두는 디자인 패턴<br>
<img width="350" alt="springcontainer" src="https://github.com/jongmee/spring-tutorial-18th/assets/101439796/7acd4d3d-3c66-4c6c-9266-d53e17f70361"><br>
</p>

```java
                    public interface IService{
                        String runSomething();
                    }
                    
                    public class Service implements IService{
                      public String runSomething(){
                        return "Run Service\n";
                      }
                    }
                    
                    public class Proxy implements IService{
                      IService iservice;
                    
                      public String runSomething(){
                        // 흐름 제어가 주목적. 반환 결과를 그대로 전달
                        iservice = new Service();
                        return iservice.runSomething();
                      }
                    }
                    
                    public class ClientWithProxy{
                      public static void main(String[] args){
                        // 프록시를 이용한 호출
                        IService proxy = new Proxy();
                        System.out.println(proxy.runSomething());
                      }
                    }
```
<p align="center">
AOP는 핵심 모듈 사이에 필요한 기능을 삽입하여 적절한 타이밍에 호출되도록 해주는 기능인데, 스프링은 대상 빈을 <b>프록시</b>로 감싸는 방법을 사용한다. 
즉, 스프링 AOP는 <b>런타임에 프록시 인스턴스가 동적으로 변경되는</b> 다이나믹 프록시 기법으로 구현되어있다. 실제 Bean 대신 프록시 Bean을 등록한다. 모두 <b>동일한 인터페이스의 구현체 혹은 클래스의 확장</b>이기에 가능하다. 프록시를 통해 흐름을 제어하느 부가 기능을 추가할 수 있다.
</p>

<div align="center">

|   AOP 용어   |설명|
|:----------:|:--------:|
|   Advice   |실질적인 부가 기능 로직을 정의하는 곳|
| Join point |Advice가 적용될 수 있는 모든 위치 ex. 메서드 실행 시점, 생성자 호출 시점, 필드 값 접근 시점 등등|
|  Pointcut  |조인 포인트 중에서 Advice가 적용될 위치를 선별하는 기능. 스프링 AOP는 프록시 기반이기에 메서드 실행 시점만 가능|
|  Advisor   |스프링 AOP에서만 사용되는 용어로 Advice + Pointcut 한 쌍|
|   Aspect   |Advice + Pointcut을 모듈화 한 것. (@Aspect)|

</div>

<p align="center">
• 스프링에서는 Advice에 관련된 5가지 애노테이션을 제공하는데,<br> @Around, @Before, @AfterReturning, @AfterThrowing, @After이다.<br>
</p>

```java
@Slf4j
@Aspect
@Component
public class LogAop {

    // com.aop.controller 이하 패키지의 모든 클래스의 모든 메서드
    @Pointcut("execution(* com.aop.controller..*.*(..))")
    private void cut(){}

    // Advice
    @Before("cut()")
    public void beforeParameterLog(JoinPoint joinPoint) {
        
        Method method = getMethod(joinPoint);
        log.info("======= method name = {} =======", method.getName());

        Object[] args = joinPoint.getArgs();
        if (args.length <= 0) log.info("no parameter");
        for (Object arg : args) {
            log.info("parameter type = {}", arg.getClass().getSimpleName());
            log.info("parameter value = {}", arg);
        }
    }

    // Advice
    @AfterReturning(value = "cut()", returning = "returnObj")
    public void afterReturnLog(JoinPoint joinPoint, Object returnObj) {
        
        Method method = getMethod(joinPoint);
        log.info("======= method name = {} =======", method.getName());

        log.info("return type = {}", returnObj.getClass().getSimpleName());
        log.info("return value = {}", returnObj);
    }

    // 메서드 정보 가져오기
    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }
}
```

<h2 align="center">
🌼 PSA
</h2>

<p align="center">
✦ PSA (Portable Service Abstraction / 휴대성 추상 서비스)<br>
• 변경에 있어서 유연하게 대처할 수 있는 서비스를 제공한다.<br>
• 추상화 계층을 사용하여 어떤 기술을 내부에 숨기고 개발자에게 편의성을 제공해주는 것이 Service Abstraction이다.<br>
• 하나의 추상화로 여러 서비스를 묶어둔 것을 Spring에서 Portable Service Abstraction이라고 한다. <br>
• 예를 들어, Jdbc를 통해 DB에 접근(DatasourceTransactionManager)할 수 있고 ORM을 이용하고자한다면 JPA(JpaTransactionManager)를 통해서 접근할 수도 있다. 어떠한 경우라도 @Transactional 어노테이션을 이용하면 트랜잭션을 유지하는 기능을 추가할 수 있다.<br>
</p>

<h2 align="center">
🌼 느낀점과 배운점
</h2>

<p align="center">
세 기술 모두 기본적인 개념은 알고 있었지만 실제 자바 코드와 스프링에서의 활용을 중심으로 공부하니 더 와닿았다. 
저번 학기의 스프링 스터디에서 공부한 디자인 패턴도 추상적으로만 알고 있었는데 스프링에 어떻게 이용되는지 배울 수 있었다.
또한 다른 팀원들의 스터디 PR을 보고 생성자 주입을 사용해야 하는 이유, final 제어자의 역할과 장점 등 당연하게 쓰고 있던 코드 하나하나를 새롭게 이해할 수 있었다. 혼자 공부했더라면 그냥 지나쳤을 텐데 스터디의 장점을 다시금 깨달았다.
</p>

<br><br>
<div align="center">

### 1주차 미션 주제
</div>

<h4 align="center">
1️⃣ spring-boot-tutorial-18th를 완료한다 🎖 <br><br>
2️⃣ H2 데이터베이스를 연결한다 📀<br><br>
3️⃣ 스프링 어노테이션을 심층 분석한다 📓<br><br>
4️⃣ 단위 테스트와 통합 테스트를 탐구한다 🧪
</h4><br><br>

<div align="center">

## 🌼 spring-boot-tutorial-18th 완료 사항
• 스터디 자료에서 약간 변경한 부분들
</div>

```java
@Data
@Entity
public class Plant {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

@Column(nullable = false)
private String name;
}

public interface PlantRepository extends JpaRepository<Plant, Long> {}
public class GardenService {}
public class GardenController {}
```
<div align="center">
• H2에 튜플 넣고 api 요청 보내 확인하기 <br>
<img width="500" alt="스크린샷 2023-09-19 오후 9 04 24" src="https://github.com/jongmee/spring-tutorial-18th/assets/101439796/3d339bfc-4185-4b78-badd-5a5c27b3f227">
<img width="500" alt="스크린샷 2023-09-19 오후 10 48 06" src="https://github.com/jongmee/spring-tutorial-18th/assets/101439796/a6d18f43-e59b-4874-be79-17ac7edaac3f">

<br><br>

## 🌼 Spring Annotation
✦ <b>Annotation</b> 이란?<br>
• 프로그래밍 언어에 영향을 미치지 않으면서 유용한 정보를 제공하는 메타 데이터이다.<br>
• 코드를 어떻게 컴파일하고 런타임에서 어떻게 처리할 것인지에 대한 정보이다. <br>
• 자바에서 제공하는 표준 어노테이션, 어노테이션을 정의할 때 쓰는 메타 어노테이션, 사용자정의 어노테이션이 있다. <br><br>
⬇️ 메타 어노테이션 종류

|   Annotation    |                                                                      기능                                                                       |
|:---------------:|:---------------------------------------------------------------------------------------------------------------------------------------------:|
|   @Target   |                      애너테이션이 적용가능한 대상을 지정한다. TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VAIABLE 중 여러개를 선택할 수 있다.                       |
|   @Retention   | 애너테이션이 유지되는 시간을 지정한다. SOURCE(소스 파일에만 존재하고 클래스 파일에는 존재하지 않음), CLASS(기본값으로, 클래스 파일 존재하고 실행시 사용 불가능함), RUNTIME(클래스 파일에 존재하고 실행 시 사용 가능함) 중 선택한다. |
|   @Documented   |                                                        애너테이션을 javadoc으로 작성한 문서에 포함시킨다.                                                        |
|   @Inherited   |                                                             애너테이션을 자식 클래스에 상속한다.                                                              |
|   @Repeatable   |                                                           반복해서 붙일 수 있는 애너테이션을 정의한다.                                                            |
<br>
⬇️ 어노테이션 타입 정의하기
</div>

```java
@interface 애너테이션이름 {
	타입 요소이름();
}
```
<div align="center">
• 요소의 타입은 기본형, String, enum, 애너테이션, Class만 허용하고 매개변수, 예외는 선언 불가하다.<br>
• 'default'를 사용해서 기본값을 지정할 수 있다.<br>
• 요소의 타입이 배열인 경우, 괄호{ }를 사용해야 한다.<br>
• @Test 와 같이 요소가 하나도 정의되지 않은 어노테이션을 '마커 어노테이션'이라고 한다.<br>
• 지난 프로젝트에서 MockMvc로 테스트 시 커스텀 @WithMockUser 를 통해 Authentication을 주입했는데, 프로젝트에서 이렇게 어노테이션을 사용할 수 있다. (참고 사이트: https://smjeon.dev/etc/with-mock-user/) <br><br>

⬇️ @Component 의 구조와 doScan 메서드의 선언부
</div>

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface Component {
    String value() default "";
}

// ClassPathBeanDefinitionScanner.java
protected Set<BeanDefinitionHolder> doScan(String... basePackages){...}
```

<div align="center">
• 스프링은 구동 단계에서 ClassPathBeanDefinitionScanner.java 의 doScan으로 ClassPath내에 있는 패키지의 모든 클래스를 읽어서, 어노테이션이 붙은 클래스에 대해 컨테이너에 빈 등록 등의 작업을 수행한다.<br>
<br>
✦ <b>Bean</b>과 <b>ApplicationContext</b><br>
• Bean은 인스턴스화된 객체로 스프링 컨테이너에 등록된 자바 객체이다.<br>
• 빈 팩토리(Bean Factory)는 IoC 컨테이너로 빈의 생성과 관계설정 같은 제어를 담당한다. ApplicationContext는  빈 팩토리를 상속받고 추가적인 기능을 더한 스프링 컨테이너이다.<br>
• 스프링 컨테이너는 XML을 기반으로 만들거나 <b>Annotation 기반의 자바 설정 클래스</b>로 만든다. <br>
• ApplicationContext는 인터페이스로, 구현체에는 AnnotationConfigApplicationContext가 있다. 아래와 같이 자바 설정 클래스를 기반으로 스프링 컨테이너를 만들 수 있다.<br>
</div>


```java

import java.beans.BeanProperty;

public class Application {

  public static void main(String[] args) {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    
    MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
    Member member = new Member(1L, "memberA");
    memberService.join(member);
  }

}

@Configuration
public class AppConfig {

  @Bean
  public MemberRepository memberRepository() {
    return new MemberRepository();
  }
  
  @Bean
  public MemberService memberService() {
    return new MemberService(memberRepository());
  }
  
}
```

<div align="center">
• 설정 정보를 담은 Configuration 클래스에서 @Bean을 통해 수동으로 빈을 등록해주었다.<br>
• 스프링 컨테이너 내에서 빈 저장소가 존재하고 key로 빈 이름(기본적으로는 메서드 이름)을, value로 실제 빈 객체를 가지고 있다.<br>
• 스프링 컨테이너는 기본적으로 빈을 싱글톤으로 관리한다. 즉, 직접 싱글톤 패턴 구현을 위한 코드를 쓰지 않아도 된다.<br>
• 싱글톤을 사용하면 요청마다 인스턴스를 생성하지 않고, <b>하나의 인스턴스만</b> 생성하고 공유해서 비용을 줄일 수 있다.<br>
<br>
✦ <b>Singleton</b> 패턴에 필요한 요소<br>
• new를 실행할 수 없도록 생성자에 접근 제어자 private을 지정한다.<br>
• 유일한 단일 객체를 참조할 정적 참조 변수와 이 객체를 반환할 정적 메서드가 필요하다.<br>
</div>

```java
public  class Singleton {
  static Singleton singletonObject;
  
  private Singleton() {};
  
  public static Singleton getInstance() {
    if(singletonObject == null){
      singletonObject = new Singleton();
    }
    
    return singletonObject;
  }
}
```

<div align="center">
<br>
✦ <b>Component Scan</b><br>
• 설정 정보가 없어도 자동으로 스프링을 등록하는 기능으로 @Component 가 붙은 클래스를 스캔해서 등록한다. (@ComponentScan)<br>
• 빈 이름은 맨 앞글자를 소문자로 바꾼 클래스명이다. <br>
• @SpringBootApplication에 @ComponentScan이 포함되어 있어 따로 작성하지 않아도 된다. 스프링 부트의 메인 클래스는 루트 패키지에 두는 것이 좋다.
<br><br>
⬇️ @Component 외의 스캔 대상

|   Annotation    |                     기능                      |
|:---------------:|:-------------------------------------------:|
|   @Controller   |              스프링 MVC 컨트롤러로 인식               |
|   @Repository   | 스프링 데이터 접근 계층으로 인식하고, 데이터 계층 예외를 스프링 예외로 변환 |
| @Configuration  |     스프링 설정 정보로 인식, 스프링 빈이 싱글톤을 유지하도록 처리     |
|    @Service     |                  부가적 기능 없음                  |

</div>

```java
@ComponentScan
public class AutoAppConfig {
  
}


@Component
public class MemberRepository{ ... }


@Component
public class MemberService{
  
  private final MemberRepository memberRepository;
  
  @Autowired
  public MemberService(MemberRepository memberRepository){
    this.memberRepository = memberRepository;
  }
}
```

<div align="center">
<br>
✦ Reference<br>
1) [도서] 자바의 정석<br>
2) [스프링 Document] https://docs.spring.io/spring-framework/reference/core/ <br>
3) [도서] 스프링을 위한 자바 객체 지향의 원리와 이해

<br><br>

## 🌼 Integration • Unit Test
✦ <b>단위 테스트</b>란?<br>
• 하나의 모듈을 기준으로 독립적으로 진행되는 가장 작은 단위의 테스트이다. <br>
• Java는 주로 JUnit 테스트 프레임워크로 테스트한다.<br>
</div>

```java
@WebMvcTest(controllers = {GardenController.class})
public class GradenControllerUnitTest {
  
  @Autowired
  private MockMvc mockMvc;
  
  @Test
  @DisplayName("기본 컨트롤러를 유닛 테스트한다.")
  public void 컨트롤러에서_STATUS_200_반환하기() throws Exception {
    // when & then
    mockMvc.perform(get("/")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
```
<div align="center">
• @WebMvcTest 는 Controller만을 로드하여  Controller layer를 테스트하기 적합하다.<br><br>
✦ <b>통합 테스트</b>란?<br>
• 여러 모듈들을 통합적으로 확인하는 테스트이다.<br>
• 스프링부트는 @SpringBootTest 로 통합 테스트한다.<br>
• 애플리케이션의 설정, 모든 Bean을 로드하고 실제 애플리케이션을 localhost에서 live 테스트한다. 데이터베이스 커넥션도 테스트할 수 있다. <br>
• 단위 테스트에서 발견하기 어려운 버그를 찾을 수 있으나 디버깅이 상대적으로 어렵다.
</div>

```java
// 스터디 자료
@SpringBootTest
@AutoConfigureMockMvc
class GardenControllerTest {

  @Autowired
  private MockMvc mvc;

  @DisplayName("기본 컨트롤러를 테스트한다.")
  @Test
  public void 컨트롤러에서_STATUS_200_반환하기() throws Exception {
    // when & then
    mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(equalTo("Welcome to My Garden in Spring!")));
  }
}
```

<div align="center">

## 🌼 느낀점과 배운점
스프링 부트가 제공하는 편리성 덕분에 기술의 사용법만 알고 넘어간 적이 많았는데, (어노테이션의 구조도 제대로 숙지하지 못했었다) 이번 기회를 통해 기초부터 다시 돌아볼 수 있었다. 구현 방식을 제대로 공부하니 미처 최적화하지 못했던 코드나 더 간편한 어노테이션을 효율적으로 사용하는 법을 깨달을 수 있었다. 그리고 공부할 게 정말 많다.. 

</div>
