<h1 align="center">
      📂  CEOS BE Spring 📂 
    <br/>
</h1>

<div align="center">

### 💛 Chapter 💛
[WEEK 0 | 스프링의 이해](#CEOS-WEEK-0:-스터디-안내-및-스프링의-이해) <br>
[WEEK 1 | 스프링 튜토리얼](#CEOS-WEEK-1:스프링-튜토리얼) <br>
[WEEK 2 | DB 모델링과 JPA](#CEOS-WEEK-2:DB-모델링과-JPA-🥕) <br>
[WEEK 3 | CRUD API 만들기](#CEOS-WEEK-3:-CRUD-API-만들기-🎁) <br>


</div>

# 📂 CEOS WEEK 0: 스터디 안내 및 스프링의 이해
<br>

### 🌱 0주차 목표

### 1️⃣ IntelliJ IDEA(Ultimate Edition)를 설치해요
### 2️⃣ Spring이 지원하는 기술들(IoC/DI, AOP, PSA 등)을 자유롭게 조사해요

---

### 🌱 0주차 미션

### 1️⃣ IntelliJ IDEA 설치

![image](https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/0c62a68b-991c-44d5-a2e8-5ba3d63512fc)

➡ 설치 완료

### 2️⃣ Spring이 지원하는 기술 조사

#### 1. IoC/DI

<b> (1) IoC </b>
 - 정의
   - IoC란? Inversion of Control로 **제어의 역전**을 의미합니다. 
 - 특징:
    - 메소드나 객체의 호출작업을 개발자가 결정하는 것이 아니라, 외부에서 결정되는 것입니다.
    - 개발자가 직접 객체를 관리하지 않고 **스프링 컨테이너에서 제어 객체를 생성하여 해당 객체에 주입** 시켜줄 수 있습니다.
    - 기존의 객체가 만들어지고 실행되는 과정과 다릅니다.
      - 🔎<기존> 1.  객체 생성 2.  의존성 객체 생성_   _클래스 내부에서 생성_ 3.  의존성 객체 메소드 호출
      - 🔎<스프링> 1. 객체 생성  2. 의존성 객체 주입_   _스스로가 만드는것이 아니라 제어권을  스프링에게 위임하여 스프링이 만들어놓은 객체 주입._  3. 의존성 객체 메소드 호출


🎯개발자가 직접 객체를 생성하여 코드를 제어하는 경우
```Java
public class A {  
  
   private B b;  
  
   public A()  
   b = new B();  
 }  
}
```
➡ 직접 객체를 제어하여 A객체는 B객체에게 의존하고 있는 걸 클래스를 통해 표현합니다.
<br></br>

🎯컨테이너에 의해서 생성한 객체를 사용하는 경우
```Java
public class A {  
  
   @Autowired  
   private B b;  
  
}
```
➡ B라는 객체를 @Autowired 통해 객체를 주입 받을 수 있게 됩니다.
<br>

#### (2) DI
- 정의
    - DI란? Dependency Injection로 **의존 관계 주입** 기능입니다.
- 특징:
    - A가 B에 의존한다는 의미는 B가 어떠한 이유로 변경이 발생하면 그 영향이 A에 미친다는 것입니다. 그래서 클래스간에 직접적 의존 관계를 맺는 것보다 느슨하게 인터페이스를 통해 의존관계 맺어서 결합도를 낮추는 것이 좋기에 등장했습니다.
    -  **객체를 직접 생성하는 게 아니라 외부에서 생성한 후 주입 시켜주는 방식**입니다.

- 종류:

**(1) 생성자 주입(Constructor-based Dependency Injection)**: 생성자 앞에 `@Autowired` 어노테이션을 명시
```Java
@Service  
public  class  MemberService {
   private MemberRepository memberRepository; 
   
   @Autowired  
   public  MemberService(MemberRepository memberRepository) {                
   this.memberRepository = memberRepository; 
   } 
 }
```

**(2) 세터 주입(Setter-based Dependency Injection)**: setter 함수에  `@Autowried` 어노테이션을 명시

```Java
@Service  
public  class  MemberService {
   private MemberRepository memberRepository; 

   @Autowired  
   public  void  setMemberRepository(MemberRepository memberRepository) {
   this.memberRepository = memberRepository; 
   } 
 }
```
**(3) 필드 주입(Field-Based Dependency Injection)**: 필드 앞에 `@Autowired` 어노테이션을 명시

```Java
@Service  
public  class  MemberService { 
   @Autowired  
   private MemberRepository memberRepository; 
}
```
<br></br>
#### 2. AOP
- 정의
    - AOP란? Aspect Oriented Programming으로, 핵심 로직과 부가 기능을 분리하여 어플리케이션 전체에 걸쳐 사용되는 부가 기능을 모듈화하여 재사용할 수 있도록 지원하는 것입니다.
    - 즉, **공통된 기능을 재사용**하는 기법입니다.
- 특징:
    - 공통 관심 사항과 핵심 관심 사항(코어 코드)을 분리하여 **반복된 작업을 줄입니다**.
    - 프록시 객체를 자동으로 만들어 실제 객체의 기능을 실행하기 전,후에 공통기능을 호출합니다.
      ![image](https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/46f780c6-a838-408c-8a85-3c2005e0db75)

- 용어:

|용어  | 내용 |
|--|--|
|Advice  | **언제** 공통기능을 핵심로직에 적용할 지 정의 |
|JointPoint | Advice를 적용가능한 지점(**메서드**) |
|PointCut  | 실제 Advice가 **적용되는 JointPoint** |
|Weaving  | Advice를 **핵심로직 코드에 적용**하는 것 |
|Aspect  | **공통기능** |

- 구현방법:
    - Aspect로 사용할 클래스에 **@Aspect** 애노테이션을 붙입니다.
    - **@Pointcut** 애노테이션으로 공통기능을 적용한 Pointcut을 정의합니다.
    - 공통기능을 구현한 메서드에 원하는 시점에 따라 **Advice**를 택하여 애노테이션을 적용합니다.



#### 3. PSA
- 정의
    - PSA란? Portable Service Abstraction으로, 환경의 변화와 관계없이 일관된 방식의 기술로의 접근 환경을 제공하는 추상화 구조입니다.
- 예시:
  **(1) Spring Web MVC**
    - `@Controller`, ` @GetMapping`,  `@PostMapping`과 같은 어노테이션과 뒷단의 여러 가지 복잡한 인터페이스들 그리고 기술들을 기반으로 하여 사용자가 기존 코드를 수정하지 않고 간편하게 바꿀 수 있습니다.

  **(2) Spring Transaction**
    - `@Transactional` 어노테이션을 사용하여 트랜잭션 처리를 합니다.

  **(3) Spring Cache**
    - `@Cacheable`, `@CachePut`, `@CacheEvict` 등의 애노테이션으로  캐시 관련 동작을 지정합니다.
      <br></br>

#### 4. Spring Data
- 정의
    - Spring Data란? DB에 대한 특성은 유지하며, 데이터 액세스 방법에 대하여 친숙하고 익숙한 접근 방법을 제시하는 목적을 가진 Spring 기반 프로그래밍 모델입니다.
- 특징
    - Repository라는 제네릭한 인터페이스를 제공하여 공통된 연산에 implementation을 동적으로 제공합니다.
    - 각 데이터 저장소는 Spring Data의 Repository를 구현하여 자신의 데이터 저장소에 맞는 repository를 제공합니다.
    - 사용하려는 repository를 상속하여 각 저장소에서 정의한 convention에 맞게 메서드만 선언하기만 하면 Spring Data가 Runtime시 이름에 맞는 적절한 구현 내용을 제공합니다.
- 예시
    - Person 엔터티를 데이터베이스에 저장하고, findByLastName 메서드를 사용하여 데이터를 조회하고 출력합니다.

```Java
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
}
```

```Java
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByLastName(String lastName);
}
```   
---
#### 🙋‍♀️느낀 점
이번 주차 스터디를 통해서 Spring의 로직과 개념에 대한 이해를 향상시킬 수 있었습니다.
이전에 Spring Boot로 프로젝트를 개발한 경험이 있지만 Spring Framework의 핵심적인 동작 원리와 개념에 대한 이해가 부족하다고 느끼고 있었습니다.

IoC/DI로 객체의 생성과 의존성 관리를 스프링 컨테이너에게 위임하여 유연한 코드를 작성할 수 있다는 것을 알게 됐습니다. 또한, AOP를 통해서 핵심 로직과 부가 기능을 분리하여 가독성이 높고 재사용성을 높일 수 있다는 점도 알 수 있었습니다. PSA로는 추상회된 구조를 통해 일관된 방식으로 다양한 기술에 접근할 수 있다는 것도 알게 되었습니다. 평소 사용했던 Spring Data에 대한 부분도 공식 문서를 통해 다시 정리해볼 수 있었습니다.

이번 주차에서 배운 내용을 토대로 실제 프로젝트에 적용하여, 효율적이고 안정된 구조의 애플리케이션을 개발하고자 합니다!

---
#### 🏄‍♀️참고자료
IoC/DI
- https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html
- https://biggwang.github.io/2019/08/31/Spring/IoC,%20DI%EB%9E%80%20%EB%AC%B4%EC%97%87%EC%9D%BC%EA%B9%8C/
- https://velog.io/@gillog/Spring-DIDependency-Injection
- https://baek.dev/post/21/
- https://mimah.tistory.com/entry/Spring-%EC%9D%98%EC%A1%B4%EC%84%B1-%EC%A3%BC%EC%9E%85-Dependency-Injection-DI-%EC%98%88%EC%A0%9C

AOP

- https://life-with-coding.tistory.com/479
- https://java-is-happy-things.tistory.com/41

PSA
- https://gwamssoju.tistory.com/96

Spring Data
- https://spring.io/projects/spring-data
- https://ckddn9496.tistory.com/99
---
# 📂 CEOS WEEK 1: 스프링 튜토리얼
<br>

## 🌱 1주차 목표

### 1️⃣ spring-boot-tutorial-18th 수행
### 2️⃣ H2 데이터베이스 연결
### 3️⃣ 스프링 어노테이션 심층 분석
### 4️⃣ 단위 테스트와 통합 테스트 탐구
<br>

## 🌱1주차 미션

## 1️⃣ spring-boot-tutorial-18th 수행
**(1) Spring Initializr 로 시작**
<img width="728" alt="스크린샷 2023-09-21 오후 3 44 47" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/74f3b184-50a9-41db-9a27-d40f0ca41a72">
- 프로젝트 설정
    -   Project : Gradle - Groovy
    -   Language : Java
    -   Spring Boot : 3.1.2
    -   Project Metadata
        -   Group : com.ceos18
        -   Artifact : spring-boot
        -    Name : spring-boot
        -   Description : Demo project for Spring Boot
        -   Package name : com.ceos18.spring-boot
        -   Packaging : Jar
        -   Java version : 17
    -  Dependencies : Spring Web
        -   🕵🏻 **Spring Web**이란? Web MVC를 사용하여 웹 애플리케이션을 만드는데 필요한 스프링부트의 기본적인 요소(annotation 등)를 가지고 있습니다.
        -   또한, 내장형 컨테이너로 tomcat 을 탑재하고 있습니다.<br></br>

**(2) IntelliJ에서 gitignore 파일 추가 후 Run application**
<img width="1215" alt="스크린샷 2023-09-21 오후 4 56 37" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/916f88d7-d6ae-47be-b37d-7036c341d20e">

>💡Tip: 로그의 색상을 단위별로 세팅하고 싶다면 resources - application.properties 파일에  
`spring.output.ansi.enabled=always` 코드를 추가하기!
<br></br>

**(3) localhost:8080에서 서버 접속 확인**
<img width="435" alt="스크린샷 2023-09-21 오후 5 06 19" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/0f16f3f2-7b5b-46f0-91d7-5a466b19f4e4">

resources/static/index.html 생성해 테스트 해보았습니다!
<br></br>
**(4) 간단한 Web Application 만들기**

**1. Controller 생성 및 Application class 수정 후 Application 실행
2. Bean 확인
3. 서버 작동 확인**<br></br>
   방법1) curl 명령어<br></br>
   <img width="410" alt="스크린샷 2023-09-21 오후 5 30 00" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/1748029c-24e1-41a7-9bee-2510feae3f21">
   <br></br>
   방법2) 웹 브라우저 <br></br>
   <img width="283" alt="스크린샷 2023-09-21 오후 5 31 46" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/c534c597-d580-4554-a25b-31495800b13e"><br></br>

**(5) 단위 테스트 실행**

**1. build.gradle에 dependency 추가**
```java
testImplementation('org.springframework.boot:spring-boot-starter-test')
```
**2. Controller에 대한 Test Class 추가**
<img width="1325" alt="스크린샷 2023-09-21 오후 5 43 19" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/d88d91e1-1ecf-4ff0-91a7-30a010962b21">

### 2️⃣ H2 데이터베이스를 연결해요
**1. H2 Database Engine 를 설치해요**
<img width="941" alt="스크린샷 2023-09-21 오후 5 57 04" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/4dccdd47-6fda-48f1-a5a5-03fd8f0e1f82">

데이터베이스 파일을 생성한 후 TCP 소켓을 통해 접속하는 이유?

파일 직접 접근이 아닌 TCP 소켓을 통해 접속해야 어플리케이션과 콘솔이 동시에 접근했을 때 오류가 발생하지 않기 때문이다.
<br></br>

**2. dependency 추가**
**3. application.yml 작성**
**4. Domain, Repository, Service, Controller 를 작성**
=> GitHub에 업로드 완료!
<br></br>

## 3️⃣ 스프링 어노테이션을 심층 분석해요

### 🔗 어노테이션이란 무엇이며, Java에서 어떻게 구현될까요? 🔗

**(0) Bean 이란?**
- **정의**: 애플리케이션의 주요 구성 요소 중 하나로, **객체를 생성하고 관리하는 역할**을 합니다.

**(1) Annotation이란?**
-  **정의**: Java에서 코드 사이에 주석처럼 쓰이며 특별한 의미, 기능을 수행하도록 하는 기술입니다.  즉, 프로그램에게 추가적인 정보를 제공해주는 메타데이터라고 볼 수 있습니다.
- **특징**:
    - 컴파일러에게  코드 작성 문법 에러를 체크하도록 정보를 제공합니다.
    - 소프트웨어 개발 툴이  빌드나  배치시  코드를 자동으로 생성할 수 있도록 정보를 제공합니다.
    - 실행 시 특정 기능을 실행하도록 정보를 제공합니다.
    - `@Bean` vs  `@Component`
      **공통**:   `Spring(IOC) Container`에 Bean을 등록하도록 하는 메타데이터를 기입하는 어노테이션이다.
      **차이**: `@Bean`의 경우, 개발자가 직접 제어가 불가능한 외부 라이브러리등을 `Bean`으로 만들려할 때 사용된다.
      `@Component` 의 경우, 개발자가 직접 작성한 Class를 Bean으로 등록하기 위한 어노테이션이다. <br></br>
- **종류**

  **[1] @Component**

- 개발자가 생성한 Class를 Spring의 Bean으로 등록할 때 사용하는 Annotation
- 빈으로 등록된 클래스는 다른 컴포넌트나 서비스에서 주입(DI)하여 사용할 수 있습니다.

```java
@Component
public class CEOSMember {

    private String position;
    private String name;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```
`@Component` 어노테이션을 사용하였기에 `CEOSMember` 클래스는 Spring 빈으로 정의됩니다.<br></br>

**[2] @Controller**

- Spring MVC의 **Controller로 사용되는 클래스 선언을 단순화** 시켜주는 Annotation
- Controller 클래스는 **웹 요청을 처리하고 적절한 응답을 생성**하는 역할을 합니다.
  <br></br>

**[3] @RestController**
- **@Controller에 @ResponseBody가 결합**된 Annotation
- Controller class에 @RestController를 붙이면, Controller class 하위 메서드에 @ResponseBody 어노테이션을 붙이지 않아도 문자열과 JSON 등을 전송할 수 있습니다.
  <br></br>

**[4] @RequestMapping**
- **요청 URL을 어떤 method가 처리할지 mapping**해주는 Annotation
- 요청을 받는 형식인 **GET, POST, PATCH, PUT, DELETE** 를 정의합니다. (정의하지 않는다면, 자동적으로 GET으로!) <br></br>

**[5] @ResponseBody**
- **자바 객체를 json 기반의 HTTP Body로 변환**하는 Annotation

```java
@Controller
public class CEOSMemberController {

    private final CEOSMemberService ceosMemberService;

    public CEOSMemberController(CEOSMemberService ceosMemberService) {
        this.ceosMemberService = ceosMemberService;
    }

    @GetMapping("/ceos-member")
    @ResponseBody
    public String getCEOSMemberInfo() {
        CEOSMember ceosMember = ceosMemberService.getCEOSMember();
        return "Position: " + ceosMember.getPosition() + ", Name: " + ceosMember.getName();
    }
}
```
-   `@Controller` 가  **클라이언트의 요청을 받고 처리**하는 역할을 합니다.
- `@RequestMapping("/ceos-member")`: 이 어노테이션은 이 컨트롤러가 처리하는 모든 요청 URL의 시작 경로를 "/ceos-member"로 지정합니다. **즉, "/ceos-member"로 시작하는 모든 URL을 이 컨트롤러가 다룹니다.**
- `@GetMapping`: HTTP **GET 요청**을 처리합니다.
- `@ResponseBody`: 해당 메서드가 **반환하는 문자열이 직접 HTTP 응답의 본문으로** 전송되도록 합니다. 즉, 메서드가 반환하는 값은 HTTP 응답의 내용으로 클라이언트에게 전달됩니다.
  <br></br>

**[6] @Service**
- **비즈니스 로직을 수행하는 Class**라는 것을 나타내는 Annotation
```java
@Service
public class CEOSMemberService {

    private final CEOSMember ceosMember;

    public CEOSMemberService(CEOSMember ceosMember) {
        this.ceosMember = ceosMember;
    }

    public String getPosition() {
        return ceosMember.getPosition();
    }
}
```
`CEOSMemberController` 는 `getCEOSMemberInfo` 메서드에서 `CEOSMemberService`를 호출할 때 `position` 값을 반환하게 됩니다. <br></br>

**[7] @Repository**
- **DB연동 작업을 하는 클래스인 DAO에 특화**된 Annotation
- 해당 클래스에서 발생하는 **DB 관련 예외**를 spring의 DAOException으로 전환할 수 있는 장점이 있습니다.
```java
@Repository
public interface CEOSMemberRepository extends JpaRepository<CEOSMember, Long> {
    List<CEOSMember> findByPosition(String position);
}
```
`position` 값을 기준으로 `CEOSMember`를 조회할 수 있습니다.
✴️ 예를 들어, `Leader`라는 `position` 값을 가진 멤버를 조회하려면 다음과 같이 사용할 수 있습니다!
```java
List<CEOSMember> ceosMembers = ceosMemberRepository.findByPosition("Leader");
```

<br></br>

### 🔗 스프링에서 어노테이션을 통해 Bean을 등록할 때, 어떤 일련의 과정이 일어나는지 탐구해보세요. 🔗

참고) Bean Life Cycle
![](https://blog.kakaocdn.net/dn/lc33n/btrnVaFWE0Z/QJMpuILvLXXOXwkoX2YF21/img.png)

**1) 패키지와 클래스 스캔**: 패키지들을 스캔하여 패키지 내에 있는 클래스들 중 어노테이션을 확인합니다.
**2) 어노테이션 처리**: 클래스에 사용된 어노테이션을 처리합니다.
**3) 클래스를 Bean으로 등록**
**4) 의존성 주입**: `@Autowired`, `@Inject`, 또는 생성자 주입을 사용하여 의존성을 주입합니다.
**5) Bean 초기화 작업 수행**: Bean 초기화 메서드(`@PostConstruct` 어노테이션이 적용된 메서드/`InitializingBean` 인터페이스를 구현한 `afterPropertiesSet()` 메서드)를 호출합니다.
(ex)
```java
public interface InitializingBean {
    void afterPropertiesSet() throws Exception;
}
```
`InitializingBean`을 구현하여 초기화 메서드를 설정할 수 있습니다.
**6) Bean 사용**
**7) Bean 소멸 전 callback** :
```java
public interface DisposableBean {
    void destroy() throws Exception;
}
```
`InitializingBean`을 구현하여 소멸 메서드를 설정할 수 있습니다.

### 🔗 `@ComponentScan` 과 같은 어노테이션을 사용하여 스프링이 컴포넌트를 어떻게 탐색하고 찾는지의 과정을 깊게 파헤쳐보세요. ### 
- `@ComponentScan` 이란? 빈으로 등록 될 준비를 마친 클래스들을 스캔하여, 빈으로 등록해주는 것입니다.
- **동작과정**
  **ConfigurationClassParser 가 Configuration 클래스를 파싱**
  @Configuration 어노테이션 클래스를 파싱하는 것입니다.
  ⬇  
  **ComponentScan 설정 파싱**
  base-package 에 설정한 패키지를 기준으로  ComponentScanAnnotationParser가 스캔하기 위한 설정을 파싱합니다.
  ⬇  
  **모든 클래스 로딩**
  base-package 설정을 바탕으로 모든 클래스를 로딩합니다.
  ⬇  
  **생성할 빈에 대하여 정의**
  ClassLoader가 로딩한 클래스들을 BeanDefinition으로 정의합니다.
  ⬇  
  **빈 생성**
  생성할 빈에 대한 정의를 토대로 빈을 생성합니다.

- **기본 스캔 대상**
    -   @Component
    -   @Controller
    -   @Service
    -   @Repository
    -   @Configuration
- **스캔 범위**
    -   **`@ComponentScan`  어노테이션이 있는 파일의 패키지 아래**를 찾는다.
    -   **`basePackages`  /  `basePackageClasses`로 지정도 가능**
        **- 스캔 범위를 지정하고 싶다면?**
```java
// 1.단일 경로
@ComponentScan(basePackage = "경로")  
// 2.다수 경로
@ComponentScan(basePackage = "경로1", "경로2")  
// 3.특정 파일 이용하여, 해당 위치부터 탐색 경로 설정  
@ComponentScan(basePackageClass = 파일명1.class, 파일명2.class)
```

- **Filter를 사용하여 스캔 대상 범위 지정**
    - includeFilters : 컴포넌트 스캔 대상을 추가로 지정
    - excludeFilters : 컴포넌트 스캔에서 제외할 대상을 지정
    -   ANNOTATION : 기본값. 어노테이션으로 인식해서 동작
    -   ASSIGNABLE_TYPE : 지정한 타입과 자식 타입을 인식해서 동작
    -   ASPECTJ : AspectJ 패턴 사용
    -   REGEX : 정규표현식
    -   CUSTOM : TypeFilter라는 인터페이스를 구현해서 처리

- **예시**
  `includeFilters`로 `beanA`를 컴포넌트 스캔에 포함하고,  
  `excludeFilters`를 이용하여  `beanB`를 컴포넌트 스캔에서 제외해보겠습니다.

코드는 [woply님의 블로그](https://velog.io/@woply/%EC%BB%B4%ED%8F%AC%EB%84%8C%ED%8A%B8-%EC%8A%A4%EC%BA%94-%EB%8C%80%EC%83%81%EC%9D%84-%EC%B6%94%EA%B0%80-%EB%98%90%EB%8A%94-%EC%A0%9C%EC%99%B8%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95)를 통해 테스트 해보았습니다.
```java
public class ComponentFilterAppConfigTest {

    @Test
    void filterScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA = ac.getBean("beanA", BeanA.class);
        Assertions.assertThat(beanA).isNotNull();

        assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> ac.getBean("beanB", BeanB.class));

    }

    @Configuration
    @ComponentScan(
            includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
            excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
    )
    static class ComponentFilterAppConfig {
    }
}
```
**컴포넌트 스캔 대상에 추가할 애노테이션**
```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {

}
```

**컴포넌트 스캔 대상에서 제외할 애노테이션**

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
}
```

**컴포넌트 스캔 대상에 추가할 클래스**

```java
@MyIncludeComponent
public class BeanA {
}
```

**컴포넌트 스캔 대상에서 제외할 클래스**

```java
@MyExcludeComponent
public class BeanB {
}
```

**🔎결과🔎**
1.  `BeanA` 클래스는 `MyIncludeComponent` 어노테이션을 가지고 있으므로 스캔 대상이며, 빈으로 등록됩니다.
2.  `BeanB` 클래스는 `MyExcludeComponent` 어노테이션을 가지고 있으므로 스캔 대상에서 제외되므로 해당 빈을 찾을 수 없으며 `NoSuchBeanDefinitionException`이 발생합니다.
```java
Let's inspect the beans provided by Spring Boot:
beanA
```

---
### 4️⃣ 단위 테스트와 통합 테스트 탐구
### 🔗 단위 테스트와 통합 테스트의 의미를 알아봅시다! 🔗

**(1) 단위 테스트란?**

- 정의:  전체 애플리케이션의 **단일 부분(모듈, 컴포넌트)** 을 완전히 분리하여 집중적으로 테스트하는 것
- 특징:
    - 테스트 대상 단위의 크기를 작게 설정해서 단위 테스트를 최대한 간단하고 디버깅하기 쉽게 작성해야 해야 합니다.

**(2) 통합 테스트란?**
- 정의:  **두 소프트웨어 단위 또는 모듈 간의 인터페이스**를 테스트하는 것
- 특징:
    - 장점은, 단위 테스트에서 발견하기 어려운 버그를 찾을 수 있다는 것입니다.
    - 단점은, 단위 테스트보다 더 많은 코드를 테스트하기 때문에 신뢰성이 떨어질 수 있다는 점과 어디서 에러가 발생했는지 확인하기 쉽지 않아 유지보수하기 힘들다는 점입니다.



| 어노테이션  | 설명 | 부모 클래스  | Bean |
|--|--|--|--|
|@SpringBootTest  |통합 테스트, 전체  | IntegrationTest |Bean 전체  |
|@WebMvcTest  |단위 테스트, Mvc 테스트  |MockApiTest  | MVC 관련된 Bean |
|@DataJpaTest  |단위 테스트, Jpa 테스트  |RepositoryTest  |JPA 관련 Bean  |
|None  |단위 테스트, Service 테스트  |MockTest  |None  |
|None  |POJO, 도메인 테스트|None  |  |

```java
@SpringBootTest  
@AutoConfigureMockMvc  
public class HelloControllerTest {  
  
@Autowired  
private MockMvc mvc;  
  
@DisplayName("DisplayName : 테스트 이름을 설정할 수 있습니다")  
@Test  
public void getHello() throws Exception {  
mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))  
.andExpect(status().isOk())  
.andExpect(content().string(equalTo("Greetings from Spring Boot!")));  
}  
}
```
Spring Boot 에서는 클래스 상단에 `@SpringBootTest` 어노테이션을 붙여 통합 테스트를 수행합니다!
>-   `@SpringBootTest`: 통합 테스트를 설정. Spring 애플리케이션 컨텍스트를 로드하여 테스트를 실행
>-   `@AutoConfigureMockMvc`: 컨트롤러를 테스트할 때 `MockMvc` 객체를 자동으로 구성
>- `MockMvc`: 컨트롤러의 엔드포인트를 모의 요청하고 응답을 검증하는 데 사용
>-   `@Test`: 테스트 메서드를 표시
>-   `MvcResultMatchers`: 사용하여 HTTP 응답의 상태와 내용을 검증. HTTP 상태 코드가 OK(200)이고 응답 본문의 내용이 "Greetings from Spring Boot!"인지를 확인합니다!

즉, 컨트롤러 동작을 테스트하고 해당 컨트롤러의 엔드포인트가 예상대로 동작하는지를 검증합니다.

### 🔗 스터디 자료의 단위 테스트 예제는 엄밀한 의미의 단위 테스트라고 부를 수 있을까요? 아니라면 엄밀한 의미의 단위 테스트로 구현하기 위해 어떻게 바꾸어야 할지 생각해 보아요. 😁 🔗
```java
@Service
@RequiredArgsConstructor
public class TestService {

		private final TestRepository testRepository;

		/* Read All*/
		@Transactional(readOnly = true)
		public List<Test> fetchAllTests() {
				return testRepository.findAll();
		}
}
```
엄밀한 의미의 단위 테스트라고 부를 수 없다고 생각합니다.
DB 읽기 작업을 수행하고 있으며 component를 독립시켜 테스트하고 있지 않기 때문입니다.

단위 테스트로 구현하기 위해서,`TestRepository`의 Mock 객체를 생성하고 이를 `TestService`에 주입하여 `fetchAllTests` 메서드를 테스트해볼 수 있습니다.

🧺🧺🧺 ~HelloControllerTest가 예제인줄 알고 썼던 내용..~🧺🧺🧺
1) **테스트 환경에서 필요한 Bean만 수동으로 설정하기**

- Spring Boot 애플리케이션 컨텍스트를 로드하지 않도록 변경합니다.  `@SpringBootTest` 는 필요한 빈을
  모두 초기화하므로 통합 테스트를 수행할 때 사용합니다.
- `@SpringBootTest` 대신에 `@RunWith(SpringRunner.class)` 와  `@WebMvcTest` 를 사용하여, 테스트 환경에서 필요한 빈만 수동으로 설정합니다.

```java
@RunWith(SpringRunner.class)  
@WebMvcTest(HelloController.class)
public  class  HelloControllerTest {
      ...
}
```
**2) `TestController`에서 의존하는 `TestService`를 Mock 객체로 대체**
```java
@MockBean  private TestService testService;
```
이후에 Test 메서드에서 Mock 객체의 동작을 정의해주는 것으로 수정합니다.

---
#### 🙋‍♀️느낀 점
이번 주차 스터디를 통해서 스프링 부트를 초기 세팅하고, H2 데이터베이스를 연결해보았습니다. 스프링 어노테이션에 대해서도 공부해보고 단위 테스트와 통합 테스트에 대해서도 배워볼 수 있었습니다.

이 중 가장 많이 배울 수 있던 부분은 테스트 파트였습니다. 기존에는 통합 테스트 위주로만 작성하던 습관을 개선하고 단위 테스트에 대해서도 초점을 맞추고자 합니다. 테스트 코드를 작성하고 실행함으로써 코드의 신뢰성을 높일 수 있다는 것을 몸소 느꼈고, 앞으로의 개발 프로세스에서 이를 더욱 활용하고자 합니다! 😊


---
#### ☄️Troubleshooting
1️⃣ Intellij로 Spring 코드 첫 실행 시 finished with non-zero exit value 1 오류 <br>
🔸 원인: Intellij 내 Build and run using과 Run tests using 설정 변경이 필요 <br>
🔸 해결방법:
1.  [File > Settings] 메뉴 클릭 (맥 기준 단축키 : Command + ,)
2.  [Build, Excution, Deployment > Build Tools > Gradle] 클릭
3.  Build and run using과 Run tests using이 아마도  **Gradle(Default)** 로 되어있을텐데, 이것을  
    **Intellij IDEA**로 바꿔준다.

---
#### 🏄‍♀️참고자료
Spring-boot-tutorial-18th 수행
- https://daegwonkim.tistory.com/230

스프링 어노테이션
- https://melonicedlatte.com/2021/07/18/182600.html
- https://sddev.tistory.com/225
- https://hongs-coding.tistory.com/115
- https://dahye-jeong.gitbook.io/til/spring/2021-05-17-bean-lifecycle
- https://jh-labs.tistory.com/108
- https://velog.io/@hyun-jii/%EC%8A%A4%ED%94%84%EB%A7%81-component-scan-%EA%B0%9C%EB%85%90-%EB%B0%8F-%EB%8F%99%EC%9E%91-%EA%B3%BC%EC%A0%95
- https://ittrue.tistory.com/229
- https://velog.io/@woply/%EC%BB%B4%ED%8F%AC%EB%84%8C%ED%8A%B8-%EC%8A%A4%EC%BA%94-%EB%8C%80%EC%83%81%EC%9D%84-%EC%B6%94%EA%B0%80-%EB%98%90%EB%8A%94-%EC%A0%9C%EC%99%B8%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95

단위 테스트&통합 테스트
- https://tecoble.techcourse.co.kr/post/2021-05-25-unit-test-vs-integration-test-vs-acceptance-test/
- https://parkcheolu.tistory.com/410
- https://velog.io/@kimhalin/Test-%EC%BD%94%EB%93%9C-%ED%83%90%EA%B5%AC1-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%A2%85%EB%A5%98
---
# 📂 CEOS WEEK 2: DB 모델링과 JPA 🥕
<br>

## 🥕 2주차 목표
### 1️⃣ 당근의 DB를 모델링해요
### 2️⃣ Repository 단위 테스트를 진행해요
<br>

## 🥕2주차 미션

### 1️⃣ 당근의 DB를 모델링해요

###  **💭 DB 설계 과정 💭**
**기획서는 당근의 UI로 가정하였습니다.
당근의 '중고거래'를 메인 서비스로, 선정하였습니다.
기획서를 바탕으로 요구사항 및 기능을 분석하여 DB를 설계하였습니다.  
화면별 요구사항에 초점을 맞추어 진행해보았습니다!**

### **(1) USER**
- 회원 정보를 담고 있는 테이블입니다.
- 요구사항 분석:
    - 회원 가입 시 닉네임, 전화번호, 비밀번호를 입력한다.
    - 회원 정보에는 이메일, 프로필 이미지, 동네, 매너온도가 있다.
- 모델링:

**📦 Table 📦**
<br>
<img width="334" alt="스크린샷 2023-09-26 오후 8 24 20" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/69ad2bc9-a312-4579-918f-4bd8baaa04c1">

**🔑 Keys 🔑**
주요 Key를 소개합니다.
- **userId**:
    -   PK(기본키)로, 유저를 고유하게 식별하기 위해 사용됩니다.
    -    'AUTO_INCREMENT' 속성을 가지며, 새로운 사용자가 추가될 때마다 자동으로 1씩 증가합니다.
    -  NULL일 수 없으며 각 유저는 유일한 'userId' 값을 가져야 합니다.
- **email**:
    -   NULL 값을 허용하도록 설정하였으므로, 추후 등록할 수 있습니다.
- ****temperature****:
    -   36.5로 기본값을 설정해놓았습니다.

**✴️  제외한 옵션✴️**
- 국가
- 당근 약관 및 동의사항 동의여부, 최소 연령 확인 => 미선택 시 가입을 하지 못하므로 DB에는 포함하지 않았습니다.


### **(2) POST**
- 게시물 정보를 담고 있는 테이블입니다.
- 요구사항 분석:
    - 회원은 '내 물건 팔기'를 통해 판매 상품을 등록할 때, 상품 이미지, 제목, 카테고리, 거래방식, 가격, 가격 제안받기 여부, 상품 설명, 거래 희망 장소를 작성합니다.

**📦 Table 📦**
<br>
<img width="454" alt="스크린샷 2023-09-26 오후 10 30 02" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/e57415d4-213c-4d8e-ace6-37e9e24daa94">

**🔑 Keys 🔑**
주요 Key를 소개합니다.
- **postId**:
    -   PK(기본키)로, 게시물을 고유하게 식별하기 위해 사용됩니다.
- **userId**:
    -   FK(외래키)로, user 테이블의 userId 열과 관련이 있으며, 게시물과 사용자 간의 관계를 나타냅니다.
- **categoryId**:
    -   FK(외래키)로, Category 테이블의 categoryId 열과 관련이 있으며, 게시물의 카테고리를 나타냅니다.
- **dealMethod:**
    -  거래 방식을 나타냅니다.
    - '판매하기'와 '나눔하기' 중 하나의 값을 가질 수 있으므로 enum형으로 정의하였습니다.

**✴️ 제외한 옵션 ✴️**
- 게시물 종류- 알바, 과외/클래스, 농수산물, 부동산, 중고차
- 게시물 작성 옵션- 선호하는 거래 장소

**(3) POSTIMG**
- 게시물의 이미지에 대한 정보를 담고 있는 테이블입니다.
- 하나의 게시물에 1개 이상의 사진이 존재하므로 테이블을 생성하였습니다.

**📦 Table 📦**
<br>
<img width="275" alt="스크린샷 2023-09-26 오후 10 30 24" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/637cee9f-28cb-4f84-8235-29bf351ad238">

**🔑 Keys 🔑**
주요 Key를 소개합니다.
- **postImgId**:
    -   PK로, 게시물 이미지를 고유하게 식별하기 위해 사용됩니다.
- **postId**:
    -   FK로, 'post' 테이블의 'postId' 열과 관련이 있으며, 이미지와 게시물 간의 관계를 나타냅니다.


**(4) CATEGORY**
- 게시물의 카테고리 목록 정보를 담고 있는 테이블입니다.
- 요구사항 분석:
    - 회원은 카테고리를 선택해 상품을 볼 수 있습니다.

**📦 Table 📦**
<br>
<img width="317" alt="스크린샷 2023-09-26 오후 10 31 05" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/65010f4d-2ab7-4f7c-a4e0-defbd8a53b75">

**🔑 Keys 🔑**
-  간단해서 생략하도록 하겠습니다!

**(5) CHATROOM**
- 구매자와 판매자의 채팅방에 대한 정보를 담고 있는 테이블입니다.
- 요구사항 분석:
    - 회원 간의 채팅을 통해 거래를 할 수 있습니다.

**📦 Table 📦**
<br>
<img width="343" alt="스크린샷 2023-09-26 오후 10 31 33" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/40c2b96d-5a60-479e-88fc-721c5fad697d">



**🔑 Keys 🔑**
- **chatroomId**:
    -   PK(기본키)로, 채팅방을 고유하게 식별하기 위해 사용됩니다.
-  **sellerUserId:**
    -  FK로, 채팅방의 판매자 유저를 나타냅니다.
-  **buyerUserId:**
    -  FK로, 채팅방의 구매자 유저를 나타냅니다.

**(6) CHATCONTENT**
- 구매자와 판매자의 채팅방 내용에 대한 정보를 담고 있는 테이블입니다.

**📦 Table 📦**
<br>
<img width="350" alt="스크린샷 2023-09-26 오후 10 32 00" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/61e4f155-e79a-4080-8084-6de507a446f8">

**🔑 Keys 🔑**
- **chatId**:
    -   PK(기본키)로, 채팅 내용을 고유하게 식별하기 위해 사용됩니다.
-  **chatroomId:**
    -   FK로, 채팅 메시지가 어떤 채팅방에 속한 것인지를 식별하는 데 사용됩니다.

**(7) AREA**
- 지역 정보를 담고 있는 테이블입니다.

**📦 Table 📦**
<br>
<img width="346" alt="스크린샷 2023-09-26 오후 10 32 32" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/9752abb9-0298-4cc4-9b36-0c9a44773643">

**🔑 Keys 🔑**
- **areaId**:
    -   PK(기본키)로, 지역을 고유하게 식별하기 위해 사용됩니다.
- **latitude**:
    -   decimal 형으로 위도 정보를 저장합니다.
- **longitude**:
    -   decimal 형으로 경도 정보를 저장합니다.


**(8) USER_AREA**
- 회원이 설정한 동네 정보를 담고 있는 테이블입니다.
- 예) 여의동, 여의도동

**📦 Table 📦**
<br>
<img width="302" alt="스크린샷 2023-09-26 오후 10 33 02" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/fbe853dd-ca4a-4c4a-8611-c018aceecc5f">

**🔑 Keys 🔑**
- **areaId**:
    -   FK로, 지역 정보를 나타냅니다.
-  **userId:**
    -   FK로, 사용자와 지역 간의 관계를 나타냅니다.


**(9) WISHLIST**
- 관심 목록에 추가한 게시물 정보를 담고 있는 테이블입니다.

**📦 Table 📦**
<br>
<img width="330" alt="스크린샷 2023-09-26 오후 10 33 25" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/8653f7a2-9a1f-4edf-b9d0-22ae15366314">

**🔑 Keys 🔑**
- **wishlistId**:
    -   PK로, 관심 게시물을 고유하게 식별하기 위해 사용됩니다.
-  **userId:**
    -   FK로, 유저 정보를 식별합니다.
-  **postId:**
    -   FK로, 찜 목록 항목과 게시물 간의 관계를 나타냅니다.

**(10) REVIEW**
- 리뷰 게시물 테이블입니다.

**📦 Table 📦**
<br>
<img width="427" alt="스크린샷 2023-09-26 오후 10 33 56" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/0c2fddc7-120a-452f-8eb9-dc08be3cc65b">



**🔑 Keys 🔑**
- **reviewId**:
    -   PK로, 리뷰 게시물을 고유하게 식별하기 위해 사용됩니다.
-  **reviewerId:**
    -   FK로, 리뷰를 작성한 사용자를 식별합니다.
-  **revieweeId:**
    -   FK로, 리뷰를 받은 사용자를 식별합니다.
-  **preference:**
    -   리뷰의 평가를 나타내는 ENUM 열로, '별로에요', '좋아요!', '최고에요!' 중 하나의 값을 가집니다.

**✴️변경한 사항✴️**
- 기존 당근 서비스와 달리 유저에 대한 리뷰/ 상품에 대한 리뷰가 나뉘는 것이 아닌 유저에만 리뷰가 저장되도록 하였습니다.
- 리뷰 중 '어떤 점이 좋았나요?' 부분은 생략하였습니다.
---
### 2️⃣ Repository 단위 테스트를 진행해요
모델링 제작을 완료하였다면 해당 모델이 제대로 되었는지 확인하기 위해서 `Repository` 계층의 단위 테스트를 작성해봅시다!

-   **ForeignKey 필드를 포함하는 Entity**을 하나 선택하여 테스트를 진행해주세요 ➡️ **🙋‍♀️ `User` 를 선택하였습니다!**

#### 0)  main application 실행 확인
<img width="1351" alt="스크린샷 2023-09-27 오전 12 13 43" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/59f0f913-1d9a-40f1-926b-95cb5ba6b417">

👩🏻‍🔧 *자잘한 트러블슈팅*은 맨 아래 섹션에 있습니다..

#### 1)  given when then 에 따라서 테스트를 작성하기

**1️⃣ given**
```java
User myMelody = User.builder()  
.nickname("마이멜로디")  
.email("mymelody@gmail.com")  
.phone("010-1234-5678")  
.password("mamerie0v0")  
.created(Timestamp.valueOf(LocalDateTime.now()))  
.temperature(BigDecimal.valueOf(36.7))  
.updated(Timestamp.valueOf(LocalDateTime.now()))  
.build();  
  
User kuromi = User.builder()  
.nickname("쿠로미")  
.email("kurooooo@gmail.com")  
.phone("010-3434-3434")  
.password("kuromzzang!")  
.created(Timestamp.valueOf(LocalDateTime.now()))  
.temperature(BigDecimal.valueOf(40))  
.updated(Timestamp.valueOf(LocalDateTime.now()))  
.build();  
  
User kitty = User.builder()  
.nickname("키티")  
.email("kitty@gmail.com")  
.phone("010-1111-3434")  
.password("kitty!0!")  
.created(Timestamp.valueOf(LocalDateTime.now()))  
.temperature(BigDecimal.valueOf(40))  
.updated(Timestamp.valueOf(LocalDateTime.now()))  
.build();
```

> 팁 아닌 팁..? created, updated도 test시 넣어줘야 에러가 나지 않습니다..

**2️⃣ when**
```java
userRepository.save(myMelody);  
userRepository.save(kuromi);  
userRepository.save(kitty);
```

**3️⃣ then**
```java
List<User> users = userRepository.findAll();  
assertThat(users.size()).isEqualTo(3);  
  
// 닉네임이 "쿠로미"인 사용자 찾기  
Optional<User> foundUser1 = userRepository.findByNickname("쿠로미");  
assertTrue(foundUser1.isPresent());  
assertEquals("쿠로미", foundUser1.get().getNickname());  
  
// 이메일이 "mymelody@gmail.com"인 사용자 찾기  
Optional<User> foundUser2 = userRepository.findByEmail("mymelody@gmail.com");  
assertTrue(foundUser2.isPresent());  
assertEquals("mymelody@gmail.com", foundUser2.get().getEmail());  
  
// 이메일 "kitty@gmail.com" 존재 여부 확인  
boolean emailExists = userRepository.existsByEmail("kitty@gmail.com");  
assertTrue(emailExists);  
  
// 닉네임 "쿠로미" 존재 여부 확인  
boolean nicknameExists = userRepository.existsByNickname("키티");  
assertTrue(nicknameExists);
```
<br> 같은 파일 내 코드지만 가독성을 위해 로그 출력 부분은 다른 box에 적어보았습니다~ 

``` java
// 저장된 유저들 로그에 출력  
logger.info("저장된 유저들:");  
for (User user : users) {  
logger.info("User ID: {}, Nickname: {}, Email: {}", user.getId(), user.getNickname(), user.getEmail());  
}  
  
// 유저 정보 로그에 출력  
logger.info("Found User1: ID={}, Nickname={}, Email={}",  
foundUser1.map(User::getId).orElse(null),  
foundUser1.map(User::getNickname).orElse(null),  
foundUser1.map(User::getEmail).orElse(null));  
  
logger.info("Found User2: ID={}, Nickname={}, Email={}",  
foundUser2.map(User::getId).orElse(null),  
foundUser2.map(User::getNickname).orElse(null),  
foundUser2.map(User::getEmail).orElse(null));  
  
logger.info("Email exists: {}", emailExists);  
  
logger.info("Nickname exists: {}", nicknameExists);  
}
```


####  2) 테스트에서 객체를 3개 이상 넣은 이후에 해당 객체가 출력되는지 확인하기
<center>⬇️ <br>
💡 결과 확인! 💡
<img width="1179" alt="스크린샷 2023-09-27 오전 1 06 27" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/e68c25c0-2cdf-43ed-a73a-5d74855988d6">


**✅ 저장된 유저들 출력하기** <br>
**✅ 닉네임이 "쿠로미"인 사용자 찾기** <br>
**✅ 이메일이 "kitty@gmail.com"인 사용자 찾기** <br>
**✅ 이메일 "mymelody@gmail.com" 존재 여부 확인** <br>
**✅ 닉네임 "키티" 존재 여부 확인**

<br><br>
모두 체크 완료!

####  3) 테스트를 수행할 때 발생하는 JPA 쿼리를 조회해보기
**`UserRepository.java`**
``` java
@Repository  
public interface UserRepository extends JpaRepository<User, Integer> {  
  
	Optional<User> findByNickname(String nickname);  
	Optional<User> findByEmail(String email);  
	boolean existsByEmail(String email);  
	boolean existsByNickname(String nickname);  
  
}
```

**1) findByNickname**: 닉네임을 기준으로 사용자를 조회
**2) findByEmail:** 이메일을 기준으로 사용자를 조회
**3) existsByEmail:** 이메일이 데이터베이스에 존재하는지 확인
**4) existsByNickname:** 닉네임이 데이터베이스에 존재하는지 확인

---
###  **❓ 고민해볼 사항 ❓**
- 지역 정보를 담는 테이블을 고민하는 부분이 과제 수행 중 가장 어려웠던 것 같습니다. 스터디 이후 더 개선해봐야할 것 같습니다!

---
### 🤖 Troubleshooting 🤖
**1) entity has no identifier**
분명 entity 파일에 **@Id** 어노테이션을 갖는 필드가 존재함에도 위와 같은 에러가 발생하며 애플리케이션이 실행되지 않았습니다.
**👩🏻‍🔧 해결방법**: 잘못된 패키지에서 Id 클래스를 import 하고 있었습니다.
아래와 같이 변경하면 됩니다!
import org.springframework.data.annotation.Id; ➡️ import javax.persistence.Id;
<br><br>
**2) Caused by: java.sql.SQLSyntaxErrorException: Table 'karrot.post' doesn't exist**
DB 연동도 잘 되어있는데 위와 같은 문제가 발생했습니다. 원인은 엔티티 클래스의 관계 설정 부분이 잘못되어 있어서 발생했던 것..!
**👩🏻‍🔧 해결방법**:
**변경 전)**
``` java
@ManyToOne  
@JoinColumn(name = "postId", nullable = false)  private Post post;
```
**변경 후)**
```java
@ManyToOne  
@JoinColumn(name = "postId", referencedColumnName = "postId", nullable = false)  private Post post;
```
**`referencedColumnName`** 속성을 추가해 연관된 엔티티 ( `Post` 엔티티)의 PK 이름을 지정하니 해결됐습니다~

---
###  **❎ 읽어볼 만한 사이트들 ❎**
https://www.techm.kr/news/articleView.html?idxno=97322
https://aws.amazon.com/ko/solutions/case-studies/danggeun/

---
--- 
# 📂 CEOS WEEK 3: CRUD API 만들기 🎁
<br>

## 🎁 3주차 목표
### 1️⃣ 새로운 데이터를 create하도록 요청하는 API 만들기
### 2️⃣ 모든 데이터를 가져오는 API 만들기
### 3️⃣ 특정 데이터를 가져오는 API 만들기
### 4️⃣ 특정 데이터를 삭제 또는 업데이트하는 API

<br>

## 🎁 3주차 미션

✴️ 아직 회원가입 구현 전이기 때문에,
당근의 주요 기능중 하나인 '**게시물**'과 관련된 API를 만들었습니다.

✳️ 프론트 분들과 협업하기 이전이기 때문에,
API 명세서에 Header, Body, Path Variable, Query String에 대한 정보들은 포함시키지 않았습니다!


### 1️⃣ 새로운 데이터를 create하도록 요청하는 API 만들기

### Info

-   **분류** : `게시판`
-  **기능** : `상품 게시글 작성`
-   **URL** : `api/board/posts`
-   **Method** : `POST`

<img width="651" alt="스크린샷 2023-10-07 13 26 19" src="https://github.com/CEOS-Developers/spring-tutorial-18th/assets/77966605/2b7e1d3a-7ffe-4722-baa9-2c12f54a1712">

**→ 테스트 완료 ✅**

### Request Example

```json
#req.body
{
	"townId": 1,
	"categoryId": 1,
	"postTitle": "쿠로미 인형 판매합니다.",
	"postContent": "홍대 산리오 스토어에서 구매한 쿠로미 인형입니다.",
	"cost": 50000,
	"productImage": "kuromi_doll.jpg",
	"dealMethod": "SELL",
	"postStatus": "IN_PROGRESS"
}

```
-   `"townId"`: 게시글이 속한 지역의 UID
-   `"categoryId"`: 게시글이 속한 카테고리의 UID
-   `"postTitle"`: 게시글의 제목
-   `"postContent"`: 게시글의 본문 내용
-   `"cost"`: 게시글에 표시되는 상품의 가격 또는 비용입니다.
-   `"productImage"`: 게시글에 첨부되는 이미지 URL
-   `"dealMethod"`: 거래 방법 (판매하기 / 나눔하기)
-
-   `"postStatus"`: 게시글의 상태 (판매중 / 예약중 / 거래완료)


### Response Example


```json
#200 Success
{
	"status": "SUCCESS",
	"message": null
}

```
### Comment
**NormalResponseDto**
➤ API 요청의 성공 또는 실패 여부를 나타내기 위해 데이터 전송 객체를 생성했습니다.

```java
public static NormalResponseDto success() {  
	return new NormalResponseDto("SUCCESS");  
}  
  
public static NormalResponseDto fail() {  
	return new NormalResponseDto("FAIL");  
}  
  
```
-   `status`: API 요청의 상태를 나타내는 값으로, "SUCCESS" 또는 "FAIL"로 설정됩니다.

-   `message`: 필요한 경우, 추가적인 상태 메시지를 담을 수 있도록 생서했습니다. 추후 로그인 구현 등에서 상태 메시지를 포함할 때 활용될 예정입니다.

```java
public void setMessage(String message) {  
	this.message = message;  
}
```
TODO: 로그인 구현 후 해당 메서드 사용 예정입니다!

<br>

### 2️⃣ 모든 데이터를 가져오는 API 만들기

### Info

-   **분류** : `게시판`
-  **기능** : `상품 게시글 전체 조회`
-   **URL** : `api/board/posts`
-   **Method** : `GET`

<img width="638" alt="스크린샷 2023-10-07 13 27 14" src="https://github.com/CEOS-Developers/spring-tutorial-18th/assets/77966605/ec35b4c0-4b0f-49cc-a03b-2b423dca6876">


**→ 테스트 완료 ✅**

### Response Example

```json
#200 Success
[
	{
	"id": 1,
	"townId": 1,
	"categoryId": 1,
	"postTitle": "쿠로미 인형 판매합니다.",
	"postContent": "홍대 산리오 스토어에서 구매한 쿠로미 인형입니다.",
	"cost": 50000,	
	"productImage": "kuromi_doll.jpg",
	"dealMethod": "SELL",
	"postStatus": "IN_PROGRESS",
	"createdAt": "2023-10-07 00:19:22"
	},
	{	
	"id": 2,
	"townId": 1,
	"categoryId": 2,
	"postTitle": "마이멜로디 키보드 나눔합니다.",
	"postContent": "직거래 나눔만 가능합니다.",
	"cost": 35000,
	"productImage": "mymelody_keyboard.jpg",
	"dealMethod": "SHARE",
	"postStatus": "IN_PROGRESS",
	"createdAt": "2023-10-07 00:56:28"
	}
]

```
### Comment

➤  stream()으로 `List<Post>` 객체를 스트림 형태로 변환
➤ map 함수로 엔티티(`Post`)를 상품 게시글 응답DTO(`PostResponseDto`)로 변환
➤ collect(Collectors.toList())collect(Collectors.toList())로 다시 리스트로 수집

<br>


### 3️⃣ 특정 데이터를 가져오는 API 만들기

### Info

-   **분류** : `게시판`
-  **기능** : `상품 게시글 조건별 조회`
-   **URL** : `api/board/posts/{postId}`
-   **Method** : `GET`

<img width="647" alt="스크린샷 2023-10-07 13 28 44" src="https://github.com/CEOS-Developers/spring-tutorial-18th/assets/77966605/d797e047-0d14-459c-bd5a-2acbc35c6ea5">

**→ 테스트 완료 ✅**


### **Request Parameters**

-   `keyword`: 검색 키워드로, 상품 게시글의 제목(`postTitle`) 또는 내용(`postContent`) 중 하나 이상에 해당 키워드를 포함한 게시글을 검색합니다.

### Response Example

```json
#200 Success (ex. keyword=마이멜로디)
[
	{	
	"id": 2,
	"townId": 1,
	"categoryId": 2,
	"postTitle": "마이멜로디 키보드 나눔합니다.",
	"postContent": "직거래 나눔만 가능합니다.",
	"cost": 35000,
	"productImage": "mymelody_keyboard.jpg",
	"dealMethod": "SHARE",
	"postStatus": "IN_PROGRESS",
	"createdAt": "2023-10-07 00:56:28"
	}
]
```

### Comment

➤  `findByPostTitleContainingIgnoreCase`와 `findByPostContentContainingIgnoreCase` 메서드로 검색 키워드를 포함한 게시글을 각각 제목과 내용으로 검색합니다.

> ContainingIgnoreCase를 사용하여 대소문자를 구분하지 않도록 했습니다!

➤  제목 검색 결과와 내용 검색 결과를 `responseDtos` 리스트에 추가합니다. 이때 중복 게시물이 들어갈 수 있습니다!
➤  중복된 게시글을 제거하기 위해 `Set`에 `responseDtos`의 내용을 복사합니다.
( `Set` 는 중복된 요소를 허용하지 않으므로 중복이 제거되기 때문입니다.)
➤  중복이 제거된 요소를 가져와 다시 리스트로 변환합니다.

<br>

### 4️⃣ 특정 데이터를 삭제 또는 업데이트하는 API
### Info

-   **분류** : `게시판`
-  **기능** : `상품 게시글 삭제`
-   **URL** : `api/board/post/{postId}`
-   **Method** : `DELETE`

<img width="638" alt="스크린샷 2023-10-07 13 29 47" src="https://github.com/CEOS-Developers/spring-tutorial-18th/assets/77966605/2ccacfae-ecdd-4e5b-a70c-eaa79d6c0b72">

**→ 테스트 완료 ✅**

### **Path Variable**

-   `postId`: 게시물 id로, 해당 id에 해당하는 게시물을 삭제합니다.

### Response Example

```json
#200 Success (ex. keyword=마이멜로디)
[
	{	
	"id": 2,
	"townId": 1,
	"categoryId": 2,
	"postTitle": "마이멜로디 키보드 나눔합니다.",
	"postContent": "직거래 나눔만 가능합니다.",
	"cost": 35000,
	"productImage": "mymelody_keyboard.jpg",
	"dealMethod": "SHARE",
	"postStatus": "IN_PROGRESS",
	"createdAt": "2023-10-07 00:56:28"
	}
]
```

### Comment

➤  `postOptional.isPresent()`로 게시글이 존재하는지 확인합니다.
➤  게시글이 존재할 시, `Post` 엔티티를 가져옵니다.
➤  게시글이 존재하지 않을 시, `KarrotException(ErrorCode.POST_NOT_FOUND)` 게시글이 존재하지 않음을 나타내는 예외를 반환합니다!

<br>

###  **🚫 예외 처리**

####  KarrotException & ErrorCode
➤ **`KarrotException`** 클래스를 사용해 사용자 정의 예외를 생성했습니다.

- 예외 발생 시 HTTP 응답 상태 코드(`status`)와 메시지(`message`)를 설정합니다.
- 예외에 대한 해결 방법(`solution`)도 포함합니다.
-  `ErrorCode` 를 매개변수로 받아와 해당 예외에 대한 정보를 설정합니다.

```java
public KarrotException(ErrorCode errorCode, String message, String solution) {  
	this.message = message;  
	this.status = errorCode.getHttpStatus().value();  
	this.solution = solution;  
}
```

ex) 게시글이 존재하지 않는데 delete 요청한 경우
<img width="692" alt="스크린샷 2023-11-06 16 49 41" src="https://github.com/yeni-choi/yeni-choi/assets/77966605/68e1edcd-c377-42b4-809f-6dbcc794a9d0">


<br>


➤ **`ErrorCode`** enum을 사용해 서로 다른 예외 상황에 대한 정보를 제공합니다.
```java
POST_NOT_FOUND(HttpStatus.NOT_FOUND, "글을 찾지 못했습니다.", "존재하는 글인지 확인해주세요."),  
FORBIDDEN_ARTICLE(HttpStatus.FORBIDDEN, "게시글 수정, 삭제에 대한 권한이 없습니다.", "잘못된 접근입니다. 입력값을 확인해주세요."),  
VALUE_IS_NONNULL(HttpStatus.BAD_REQUEST, "필수값을 입력하지 않았습니다.", "null 값이 허용되지 않으므로 반드시 값을 전달해주세요."),
```



---
###  **🐣 이전 주차 기준 변경 사항  🐣**

- 공통- BaseTimeEntity를 통해 생성 시간/수정 시간 자동화
- CHATCONTENT 테이블- sender 식별 필드 추가
- POST 테이블- id 타입 변경
- PURCHASE 테이블- 생성
  등등...
  다 기록은 하지 못했지만 이외에도 많은 수정을 거쳤습니다.
  꼼꼼하게 코드 리뷰 주신 분들 다시 한 번 감사드립니다 🙇🏻‍♀️

---
###  **❓ 고민해볼 사항 ❓**
- 가격 제안 로직 추가 여부

---
###  **❎ 참고  ❎**

- https://hianna.tistory.com/554

---