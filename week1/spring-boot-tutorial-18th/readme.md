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
1️⃣ Intellij로 Spring 코드 첫 실행 시 finished with non-zero exit value 1 오류
🔸 원인: Intellij 내 Build and run using과 Run tests using 설정 변경이 필요
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
