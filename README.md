# 💙 CEOS 18th Backend Study 💙
> Spring tutorial

![인텔리제이](https://github.com/CEOS-Developers/spring-tutorial-18th/assets/121355994/6e1c9bd9-ecca-4d96-aafc-4d50539f5c4e)

## ⭐ Spring의 POJO란 무엇인가?
POJO는 Plain Old Java Object 의 약자로 getter, setter처럼 기본적인 기능만 가진 자바 객체를 의미한다.
스프링은 POJO 방식을 기반으로 동작하는데, 이렇게 하면 좋은 점은 비지니스 로직과 사용되는 기술을 분리해서 사용할 수 있다는 점이다. <b>즉, 비지니스 로직을 담은 코드에서 기술적인 복잡함과 변경사항을 제거할 수 있는 것이다.</b>

예를 들어, Java에서 DB를 사용할 때 Jdbc를 사용한다. 그러면 기본적인 코드는 다음과 같다.


``` JAVA
public void  DBTest() {
    String serverURL = "jdbc:mysql://localhost:3306/sys";    // 주소:포트/db명
    String id = "root"; // 계정명
    String pw = "1234"; // 비밀번호

    try {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(serverURL, id, pw);

        String sql = "insert into emp(empno, ename, age, deptno, mgr) values(1, '오라클', 22, 0423, 05)";

        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.executeUpdate();
    } catch (ClassNotFoundException e) {
        System.out.println("드라이버가 존재하지 않습니다");
    } catch (SQLException e) {
        e.printStackTrace();
    }finally {
        pstmt.close();
        conn.close();
    }
}
```
그런데 Spring을 이용해서 구현하면, 이렇게 간단하게 구현이 된다.
``` JAVA
@Autowired
private JdbcTemplate jdbcTemplate;

@Transactional
public void DBTest() {
    String sql_1 = "insert into emp(empno, ename, age, deptno, mgr) values(1, '오라클', 22, 0423, 05)";
    String sql_2 = "insert into emp(empno, ename, age, deptno, mgr) values(2, 'mysql', 23, 0424, 06)";

    jdbcTemplate.update(sql_1);
    jdbcTemplate.update(sql_2);
}
```
코드가 간단해졌을뿐만 아니라 <b>기술 제약이 변경되었을 때 기존 메서드 안에서 코드를 수정할 필요가 없어졌다.</b> 이처럼 Spring은 적용한 기술이 메서드나 Class 코드에 직접 반영되지 않도록 하는 특징을 가지는데, 이를 비침투적인 기술이라고 하며 POJO 프로그래밍을 가능하게 한다.
<br><br>
![캡처](https://github.com/nzeong/Web-3.0/assets/121355994/87901c5c-af9a-4eae-98b1-f2cfd42a6c8c)
<br><br>
이제 POJO 프로그래밍을 가능하게 하는 <b>3가지 기술</b>에 대해 알아보자.

## 🌟 IoC/DI

IoC는 Inversion of Control의 약자로 제어의 역전이라는 의미이고, DI는 Dependency Injection의 약자로 의존성 주입이라는 의미이다. IoC/DI에 대해 알기 위해서는 먼저 <b>의존성</b>이라는 개념에 대해 알아야 한다.

```JAVA
public class A {

    private B b;

    public A() {
        b = new B_1();
    }

    public void useB() {
        b.method_1();
        b.method_2();
    }

}

interface B {
    void method_1();
    void method_2();
}

class B_1 {

    public void method_1() {
        System.out.println("B_1`s method_1");
    }

    public void method_2() {
        System.out.println("B_1`s method_2");
    }

}

class B_2 {

    public void method_1() {
        System.out.println("B_2`s method_1");
    }

    public void method_2() {
        System.out.println("B_2`s method_2");
    }

}
```
위 코드를 보면 class A가 class B의 메서드를 사용하고 있다. 이런 관계를 <b>A가 B에 의존하고 있다</b>고 한다. 왜냐하면 B의 로직에 따라 A가 영향을 받기 때문이다. 이렇게 코드를 짰을 때의 문제점은 class A가 본인이 가지고 있는 method의 로직만 신경쓰면 되는 것이 아니라 B interface의 어떤 구현 클래스를 사용할지까지도 결정해야 한다는 점이다. 즉, A class는 본인의 로직에 대한 책임뿐만 아니라 B class의 구현에 대한 책임까지 지고 있는 것이다. 이렇게 되면 좋은 객체지향설계의 원칙(SOLID) 중에서도 OCP, DIP원칙에 어긋나게 된다. 따라서 이 <b>의존관계의 구현을 제 3자에게 위임해야</b> 하고, 이를 <b>IoC/DI</b>라고 부른다. 

```JAVA
public class Factory {
    public A a() {
        return new A();
    }

    public B b() {
        return new B_1();
        // return new B_2();
    }
}

public class A {

    private B b;

    public A(B b) {
        this.b = b;
    }
}
```
이제 Factory class(제 3자)에서 B에 대한 return을 받아 A의 멤버변수 B에 주입한다. 현재는 B_1 class를 구현으로 사용하고 있지만 추후 변경된다면 class A는 변경할 필요가 없으며 Factory Class의 B에 대한 return 부분을 return new B_2()로 변경하기만 하면 된다. 이렇게 책임에 대한 분리가 이루어졌다.
<br><br>
Spring에서는 <b>스프링 컨테이너와 스프링 빈</b>이라는 기능을 제공하여 IoC/DI 기능을 쉽게 구현할 수 있다. 컴포넌트 스캔(@Component)을 통해 스프링 컨테이너에 등록된 자바 객체들을 스프링 빈이라고 하고, 스트링 빈들을 자동의존관계 주입(@Autowired)을 통해 연결해주면서 의존성을 주입하게 된다.

```JAVA
@Component 
public class FixDiscountPolicy implements DiscountPolicy{

    @Override
    public int discount(Member member, int price) {
        이하생략
    }
}
```

인터페이스 DiscountPolicy의 구현체인 FixDiscountPolicy를 OrderServiceImpl에 주입해줄 것이기에 @Component를 통해 위 클래스를 스프링 빈으로 등록해주었다.

```JAVA
@Component // 해당 클래스를 스프링 빈으로 등록
public class OrderServiceImpl implements OrderService{

    private final DiscountPolicy discountPolicy; // 인터페이스에 의존

    @Autowired // 생성자 주입을 통해 구체적인 구현체 설정해주기(이 경우 위에서 스프링 빈으로 등록한 FixDiscountPolicy가 주입)
    public OrderServiceImpl(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy; // 인터페이스에 의존
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        int discountPrice = discountPolicy.discount(itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
```
보통 위처럼 <b>생성자 주입</b>을 이용해서 의존관계 주입을 해주는 경우가 많다. 


## 🌟 AOP
AOP는 Aspect Oriented Programming의 약자로 <b>관점 지향 프로그래밍</b>이라고 불린다. 관점 지향 프로그래밍은 어떤 기능을 핵심적인 관점(Core Concern)과 공통적인 관점(Cross-Cutting Concern)으로 분리시켜서 보고, 그 관점을 기준으로 각각을 모듈화하겠다는 것이다. 업무 로직을 포함하는 기능을 핵심 기능(Core Concern), 소스 코드상에서 다른 부분에 계속 반복해서 쓰는 코드들을 <b>공통 기능(Cross-Cutting Concern)</b>이라고 한다. AOP에서는 이 공통 기능을 <b>애스팩트(Aspect)</b>로 정의하여 핵심 기능에서 공통 기능을 분리함으로써 핵심 기능을 설계하고 구현할 때 객체지향적인 가치를 지킬 수 있게 도와준다. 

``` JAVA
class A {
    method a() {
    AAAA
    business Logic..
    BBBB
    }
    
    method b() {
    AAAA
    business Logic..
    BBBB
    }
}
 
class B {	
    method c() {
    AAAA
    business Logic..
    BBBB
    }
}
```
위의 메소드들은 핵심 기능을 하는 business Logic과 부가 기능을 하는 코드 AAAA, BBBB로 나눠볼 수 있다. AAAA, BBBB가 여기저기서 사용되고 흩어져있기 때문에 코드 변경이 필요한 경우 일일이 다 찾아서 바꿔줘야 한다는 단점이 있다. AOP는 그렇게 하지 않고 여러 곳에서 사용되는 중복된 코드를 떼어내서 분리하고, method a,b,c는 각각 business Logic만을 갖고 있도록 한다. 여기서의 AAAA, BBBB가 AOP에서 말하는 Aspect이다. 이로 인해 여러 곳에서 사용되는 공통적인 코드들을 한 번에 모아서 생성유지보수하기 쉽다는 장점이 있다.
<br> <br>
AOP가 필요한 상황은 <b>메소드의 호출 시간을 측정하는 경우</b>이다. 코드를 통해 알아보자.
```JAVA
@Component // AOP 쓸 때 스프링 빈에 등록해주어야 한다
@Aspect // AOP 클래스로 설정
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))") // 어떤 클래스에 어떻게 AOP를 적용해줄 것인지 알려주기
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed(); // 대상 객체의 매서드 실행
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString()+ " " + timeMs +
                    "ms");
        }
    }
}
```

### 🌟 PSA

특정 기술이 <b>추상화</b>되어있다는 것은 개발자가 기술의 내부를 모르더라도 기술을 이용할 수 있도록 하는 것을 의미한다. 예를 들어, 우리는 JDBC Driver를 사용해 데이터베이스에 접근하지만 JDBC Driver가 어떻게 구현되어 있는지는 몰라도 된다. 기술의 사용법만 개발자에게 제공함으로써 실제 구현부를 모르더라도 해당 기술을 이용할 수 있도록 하는 것이다.
<br><br>
PSA(Portable Service Abstraction)란 환경의 변화와 관계없이(ex. JCacheManage 사용하다가 ConcurrentMapCacheManager로 바꿈) 일관된 방식의 기술 접근 환경을 제공하는 추상화 구조를 말한다. 한마디로, "잘 만든 인터페이스 하나가 열 클래스 부럽지 않다"에서의 인터페이스가 PSA라고 생각하면 된다. Portable은 휴대용이라는 의미로 CacheManager의 종류를 비즈니스 로직의 수정없이 언제든지 변경할 수 있다는 말이고, 이게 가능한 이유는 CacheManager들이 <b>'공통적인 인터페이스=PSA'</b>를 가지고 있기 때문이다. 이러한 점에서 Spring PSA는 확장(인터페이스 구현)에는 열려 있으나 변경(인터페이스 역할)에는 닫혀 있어야 한다는 OCP의 대표적인 예라고도 할 수 있다.

Spring MVC 같은 경우에도 Java에서 Client의 요청을 받아오기 위해서 HttpServelet을 이용해야 하는데, 그러면 코드가 엄청 길어진다.

```JAVA
public class CocoServlet extends HttpServlet {
 
	// GET
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}
	
	// POST
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}
}
```
근데 Spring의 @Controller와 @RequestMapping 어노테이션을 사용하면 HttpServelet Class 가 없어도 Http통신을 할 수 있다. 이러면 코드가 엄청 간단해진다.

```JAVA
@Controller
class OwnerController {
 
	@GetMapping(value = "/members") // GET 이렇게 줄어든다
	public String list(Model model) {
	 List<Member> members = memberService.findMembers();
	 model.addAttribute("members", members);
	 return "members/memberList";
	}

	@PostMapping(value = "/members/new") // POST 이렇게 줄어든다
	public String create(MemberForm form) {
	 Member member = new Member();
	 member.setName(form.getName());
	 memberService.join(member);
	 return "redirect:/";
	}
}
```
보통은 @어노테이션을 사용해서 기술을 사용한다.

### 참고자료
- https://sabarada.tistory.com/66
- https://dev-coco.tistory.com/81
- https://dev-coco.tistory.com/83
- https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%95%B5%EC%8B%AC-%EC%9B%90%EB%A6%AC-%EA%B8%B0%EB%B3%B8%ED%8E%B8
---
확실히 스프링은 객체 지향 프로그래밍을 하기에 정말 좋은 프레임워크같다. 내가 느낀 스프링의 핵심은 분리인 것 같다. 공통 로직과 개별 로직의 간의 분리를 통해 유지보수나 수정을 쉽게 할 수 있고 공통 로직의 추상화를 통해 기술을 쉽게 이용할 수 있다. 원래 IoC/DI는 알았어도 AOP나 PSA에 대해서는 잘 몰랐는데 이번 기회에 확실하게 정리해볼 수 있어서 좋았다. 어째 공부를 하면 할수록 더 공부할 게 늘어나는 느낌이 든다. 파이팅 🤲

---
> Spring tutorial - 2
## ⭐ Spring 어노테이션
### 어노테이션이란 무엇이며, Java에서 어떻게 구현될까?
어노테이션은 메타 데이터라고 볼 수 있다. 메타 데이터란 애플리케이션이 처리해야 할 데이터가 아니라, 컴파일 과정과 실행 과정에서 코드를 어떻게 컴파일하고 처리할 지를 알려주는 데이터이다. 어노테이션은 다음 세 가지 용도로 사용된다. <br> 
- 컴파일러에게 코드 문법 에러를 체크하도록 정보 제공
- 소프트웨어 개발 툴이 빌드나 배치 시 코드를 자동으로 생성할 수 있도록 정보를 제공
- 런타임 시 특정 기능을 실행하도록 정보를 제공 <br>
자바에서는 어노테이션 타입을 정의하는 것이 인터페이스를 정의하는 것과 유사하다.
```java
public @interface AnnotationName {
    String value();
    int elementName2() default 5;
}
```
이렇게 정의한 어노테이션을 코드에서 적용할 때에는 @AnnotationName("값"), @AnnotationName(value="값", elementName=3)으로 사용할 수 있다.

### 스프링에서 어노테이션을 통해 Bean을 등록할 때, 어떤 일련의 과정이 일어날까?
 
<b>스프링 빈의 이벤트 라이프 사이클</b>은 다음과 같다. <br>
- 스프링 컨테이너 생성: new AnnotationConfigApplicationContext(AppConfig.class)
- 어노테이션을 통해 스프링 빈으로 등록: 스프링 컨테이너는 파라미터로 넘어온 설정 클래스 정보를 사용해서 스프링 빈을 등록한다
- 스프링 빈 의존관계 설정: 스프링 컨테이너는 설정 정보를 참고해서 의존관계를 주입(DI)한다
- 초기화 콜백
- 사용
- 소멸전 콜백
- 스프링 종료 <br>
스프링 빈은 간단하게 객체 생성 -> 의존관계 주입과 같은 라이프사이클을 가진다. 스프링 빈은 객체를 생성하고, 의존관계 주입이 다 끝난 다음에야 필요한 데이터를 사용할 수 있는 준비가 완료된다. 따라서 초기화 작업은 의존관계 주입이 모두 완료되고 난 다음에 호출해야 한다. 그 시점을 알려주기 위하여 스프링은 개발자에게 초기화 콜백과 소멸 콜백을 준다. 따라서 안전하게 작업을 진행할 수 있다.

### `@ComponentScan` 과 같은 어노테이션을 사용하여 스프링이 컴포넌트를 어떻게 탐색하고 찾는지의 과정을 깊게 파헤쳐보자.

@ComponentScan 은 @Component 가 붙은 모든 클래스를 스프링 빈으로 등록한다. 
이 방법은 Bean 객체를 컴포넌트 클래스에 구현하면 애플리케이션 실행 시 스프링이 컴포넌트 클래스들을 탐색, Bean 객체들을 컨테이너에 등록하는 방법이다. 이를 컴포넌트 스캔(Component Scan)이라 하며 컴포넌트 클래스는 위처럼 @Component 어노테이션이 붙은 클래스이다. 이 클래스의 객체가 스프링 컨테이너에 Bean 객체로 자동으로 등록되는 것이다.
이때 @Component 어노테이션에 문자열을 전달할 수 있는데 이는 컨테이너에 등록하는 Bean 객체의 이름을 지정하는 것이다. 이 이름은 설정 클래스에서 Bean 객체의 이름을 이용해 객체를 얻어오는 getBean 메서드에서 사용할 수 있고 지정하지 않을 경우 이 경우 맨 처음 글자를 소문자로 바꾼 클래스의 이름을 Bean 객체의 이름으로 등록한다.

@ComponentScan 을 사용하지 않고 @Bean을 사용하면 다음과 같다.

```java
@Configuration
public class AppContextMember {
    @Bean
    public MemberDAO memberDAO(){
        MemberDAO memberDAO = new MemberDAO();
        memberDAO.setUnique("Manual-injected dependencies");
        return memberDAO;
    }

    @Bean
    public String memberDAOMessage(){
        return "Auto-injected dependencies";
    }

    @Bean
    public MemberRegisterService memberRegisterService(){
        return new MemberRegisterService();
    }

    @Bean
    public ChangePasswordService changePasswordService(){
        return new ChangePasswordService();
    }
}
```
MemberDAO, MemberRegisterService, ChangePasswordService 객체를 직접 Bean 객체로 등록하고 있는 것을 볼 수 있다. 하지만 각 클래스에 @Component 어노테이션을 붙여 컴포넌트로 등록한 후 설정 클래스에서 @ComponentScan 어노테이션을 이용하여 컴포넌트 스캔을 수행하면 다음처럼 코드를 줄일 수 있다.
```java
 @Configuration
 @ComponentScan(basePackages = {"springdemo"})
 public class AppContextMember {
 
     @Bean
     public String memberDAOMessage(){
         return "Auto-injected dependencies";
     }
 }
```
물론 이 경우 객체로 등록할 클래스들이 springdemo 패키지 내에 컴포넌트로 구현되어 있어야 할 것이다.
<br>
컴포넌트 스캔은 @Component 뿐만 아니라 다음과 내용도 추가로 대상에 포함한다.
- @Component : 컴포넌트 스캔에서 사용
- @Controller : 스프링 MVC 컨트롤러에서 사용
- @Service : 스프링 비즈니스 로직에서 사용
- @Repository : 스프링 데이터 접근 계층에서 사용
- @Configuration : 스프링 설정 정보에서 사용

## ⭐ 단위 테스트와 통합 테스트 
### 단위 테스트
단위 테스트는 프로그램에서 테스트를 할 수 있는 가장 작은 단위를 실행해서 단위가 정상적으로 흘러가는지를 테스트하는 것을 말한다. 대체적으로는 <b>메소드</b>를 기준으로 단위 테스트를 진행한다. 단위 테스트의 크기가 줄어들수록 단위의 복잡도가 줄어들기 때문에, 최소한 단위는 가장 작은 단위로 실행한다. 단위 테스트를 작성해야 하는 이유는 다음과 같다. <br>
- 코드를 수정하거나 기능을 추가할 때 수시로 빠르게 검증 할 수 있다.
- 리팩토링 시에 안정성을 확보할 수 있다.
- 개발 및 테스팅에 대한 시간과 비용을 절감할 수 있다. <br>

일반적으로는 먼저 기능을 개발한 다음에 테스트 케이스를 만들어 단위 테스트를 진행하지만, 이것보다는 테스트 케이스를 먼저 만든 후에 개발을 하는 것이 단위 테스트의 효율성을 더욱 증가시킨다. 
이처럼 테스트 코드를 먼저 작성하는 개발 방법론을 테스트 주도 개발(Test-Driven Development, TDD)로 부른다.

### 통합 테스트
통합 테스트는 단위 테스트보다 큰 개념으로, 캐시, 데이터 베이스와 같은 외부 환경까지 고려한 것을 테스트한다. 따라서 통합 테스트는 여러 개의 단위 테스트를 합쳐 정상적으로 작동하는지 테스트하는 단계라고 할 수 있다. 
그런데 통합 테스트를 하는 데에는 단위 테스트에 비해 여러 개의 버그가 잠재되어 있을 확률이 높고 모든 버그들을 수정하고 테스트를 반복하는 비용은 기하급수적으로 늘어난다. 그렇기에 개발 및 테스팅에 대한 비용을 줄이기 위해 단위 테스트를 작성하는 것이 좋다.

### 스터디 자료의 단위 테스트 예제는 엄밀한 의미의 단위 테스트라고 부를 수 있을까?
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
findAll() 메소드 기준으로 테스트를 진행한다는 점에서는 단위 테스트라고 할 수 있지만 해당 테스트에서는 수정해야하는 부분이 많다. 먼저 단순히 findAll() 메소드를 반환하는 것으로 해당 메소드가 정상적으로 동작함을 검증할 수 없다. 
findAll()은 어떤 데이터가 TestRepository에 저장되었을 때 그 데이터를 반환해주는 메소드이기 때문에 save() 메소드를 먼저 검증해주어야 한다.
그래서 save() 메소드 테스트를 먼저 진행하고, findAll() 메소드 테스트를 진행할 때 임의로 몇몇 데이터를 삽입하고 그 데이터들이 모두 반환되느냐를 체크하는 것이 정확할 것이다.
또 해당 테스트의 return 값은 true 혹은 false로 나타나는 boolean을 사용해야한다.
좋은 테스트의 법칙으로 F.I.R.S.T 법칙이라는 것이 있는데, 그중에 S는 self-validating, 즉 자가 검증하는 것을 의미하며 테스트의 결과가 Boolean 값으로 나와야 한다는 것을 의미한다. 하지만 해당 테스트는 return 값을 List 로 받고 있기에 수정해주는 것이 좋다.
