# spring-tutorial-18th

CEOS 18th Backend Study - Spring Tutorial

# 0주차 - 스프링의 이해

스프링을 제대로 이해하기 위해서는 POJO(Plain Old Java Object)를 기반으로, 스프링 삼각형이라고 불리우는 IoC/DI, AOP, PSA라고 하는 스프링의 3대 프로그래밍 모델에 대한 이해가 필요하다.
스프링 프레임워크는 스프링 삼각형의 조합으로 이해할 수 있다.
"자바 객체지향의 원리와 이해" 책을 참고하여 정리한 내용입니다.

### IOC/DI 의존성 주입

- IOC
  - 결합성을 낮추기 위해, 상위 수준 모듈이 하위 수준 모듈에 의존하지 않도록 개발
- DI
  - IOC 라고도 하는 의존 관계 주입(Dependency Injection)
  - 어떤 객체가 사용하는 의존 관계를 직접 만들어 사용하는 것이 아닌, 주입 받아 사용하는 방법
  - 책에서 의존성은 단순하게 new 라고 정의
  - 강한 결합 vs 느슨한 결합
    - 강한 결합 : 객체 내부에서 다른 객체를 생성
      - 코드의 재활용성이 떨어지고, 클래스가 수정되었을 경우 해당 클래스도 같이 수정해야 하는 문제
    - 느슨한 결합 : 객체의 외부에서 생성된 객체를 인터페이스를 통해 넘겨 받기
      - 결합도를 낮출 수 있고, 런타임 시에 의존관계가 결정되기에 유연

```java
// DI 적용하지 않았을 때, 코드의 유연성 떨어짐
public class Car {
    Tire tire;

    public Car() {
        tire = new KoreaTire();
        // tire = new AmericaTire(); -> 이 부분이 유연하지 않음
    }
}

// 적용 시
public class Car {
    Tire tire;

    public Car(Tire tire) {
        this.tire = tire;
    }
}

```

---

### 의존성 주입 방법

- 스프링 IoC 컨테이너
  <img width="618" alt="스크린샷 2023-09-16 오후 9 23 09" src="https://github.com/YoungGyo-00/react-HowWorthy/assets/89639470/17d67dd2-6243-40f9-bb6f-4a26099132ea">
  - 컴포넌트의 중앙 저장소
  - 가장 중요한 인터페이스
    - `BeanFactory` -> 직접적으로 사용 거의 안 함.
      - 스프링 빈 컨테이너에 접근하기 위한 최상위 인터페이스
      - Bean 객체를 생성하고, 관리하는 인터페이스
      - 디자인패턴의 일종인 팩토리 패턴을 구현
      - 구동될 때, Bean 객체를 생성하는 것이 x
      - 클라이언트의 요청이 있을 때 getBean() 객체 생성
    - `ApplicationContext`
      - BeanFactory 상속받은 interface 이며 부가기능 추가
      - 구동되는 시점에 등록된 Bean 객체들을 스캔하여 객체화
  - 빈 설정 소스로부터 빈 정의를 읽고 빈을 구성하고 제공
  - 빈들의 의존 관계를 설정 (객체의 생성을 책임, 의존성 관리)
  - 스프링 컨테이너가 Bean 을 싱글톤으로 관리해주며 문제점 해결 + 장점만 가져감
    - 문제점 해결
      - 싱글톤 패턴 코드 구현 번거로움
      - 구체 클래스에 의존 + 유연성 떨어짐 등
    - 장점
      - 매번 인스턴스 생성할 필요 없이 단 하나만 생성해서 비용 절감

1. xml 설정파일을 통한 빈 등록 후 DI -> 이전 구식 방법
   <img width="613" alt="스크린샷 2023-09-16 오후 9 23 26" src="https://github.com/YoungGyo-00/react-HowWorthy/assets/89639470/50c4a818-b2d5-4dba-8bbc-e534d63130c8">

```java
// XML 파일 사용 -> 예전 방법
// IoC 컨테이너에 등록된 Bean 확인
ApplicationContext context = new ClassPathXmlApplicationContext("xxx.xml", Driver.class);

// 빈 등록 메서드 이름을 통해 객체를 가져옴
Tire tire = (Tire)context.getBean("tire");

Car car = (Car)context.getBean("car");
car.setTire(tire);

```

2. @Autowired, @Resource 를 통한 속성 주입

```java
// @Autowired 적용 전
Tire tire;

public void setTire(Tire tire) {
    this.tire = tire;
}

// @Autowired 적용
@Autowired
Tire tire;

// @Resource 적용
@Resourece
Tire tire;
```

- @Autowired vs @Resource
  - @Autowired
    - 스프링 프레임워크
    - id 매칭 < type 매칭
  - @Resource
    - 표준 자바
    - type 매칭 < id 매칭

---

### Service Locator Pattern (서비스 로케이터 패턴)

<img width="547" alt="스크린샷 2023-09-16 오후 9 23 40" src="https://github.com/YoungGyo-00/react-HowWorthy/assets/89639470/f2669beb-2d51-4fce-b540-ad2509dd896c">

- DI 와 같이, 클래스가 갖는 <b>종속성을 제거</b>하기 위한 디자인 패턴 중 하나
- 두 패턴 모두 IoC 개념의 구현이라는 점에 주목하는 것이 중요합니다.
- 두 패턴의 주요한 차이점은 서비스 로케이터라는 클래스에 종속성이 여전히 존재한다라는 것이다.
- 결국, 로케이터를 통해 필요한 객체를 직접 찾는 방식
- 기본 개념
  - 애플리케이션에 필요할 수 있는 모든 서비스를 보유하는 방법을 알고 있는 Service Locator 생성
  - 요청 시, 각각의 Service Instance 를 싱글톤 Service Locator 가 반환하는 것
- 구성 요소(구현해야 할 Class 들)
- 클라이언트 : 실제 서비스 안에 있는 매서드를 사용하기 위한 클래스?
  - Cache : 재사용하기 위해 서비스 참조를 저장하기 위한 객체
  - Service Locator : 모든 서비스를 보유하는 방법을 알고 있는 서비스 로케이터 코드, 캐시에서 서비스를 반환하기 위한 진입점, 객체를 제공하는 책임을 갖음
  - Initializer : 캐시의 서비스에 대한 참조를 생성 후 등록
  - Service : 원래 구현되어 있는 서비스

```java
// interface 제작
public interface MessageingService {
  String getMessageBody(); // 메서드 1
  String getServiceName(); // 메서드 2
}
```

```java
// interface 구현한 서비스 2개
public class EmailService implements MessagingService {
    @Override
    public String getMessageBody() {
      return "email message";
    }

    @Override
    public String getServiceName() {
      return "EmailService";
    }
}

public class SMSService implements MessagingService {

    @Override
    public String getMessageBody() {
      return "SMS message";
    }

    @Override
    public String getServiceName() {
      return "SMSService";
    }
}
```

- 두 서비스를 정의한 후, 서비스 객체를 생성 하는 클래스

```java
public class InitialContext {
    public Object lookup(String serviceName) { // 서비스 이름을 받았을 때, 일치하는 서비스 객체 생성
        if (serviceName.equalsIgnoreCase("EmailService")) { // 대소문자 구분 x
            return new EmailService();
        } else if (serviceName.equalsIgnoreCase("SMSService")) {
            return new SMSService();
        }
        return null;
    }
}
```

- 캐시에 이전에 생성된 서비스 객체가 있다면, 담는 용도로 사용

```java
public class Cache {
    private List<MessagingService> services = new ArrayList<>();

    public MessagingService getService(String serviceName) {
        // retrieve from the list
    }

    public void addService(MessagingService newService) {
        // add to the list
    }
}

```

- 마지막 모든 서비스를 보유하는 방법을 알고 있는 서비스 로케이터 코드

```java
public class ServiceLocator {

    private static Cache cache = new Cache(); // 이전에 생성한 적이 있는 서비스 담는 객체

    public static MessagingService getService(String serviceName) {

        MessagingService service = cache.getService(serviceName);

        if (service != null) {
            return service;
        }

        InitialContext context = new InitialContext();
        MessagingService service1 = (MessagingService) context
          .lookup(serviceName);
        cache.addService(service1);
        return service1;
    }
}
```

- 실제 서비스 객체를 생성하는 테스트 코드

```java
MessagingService smsService = ServiceLocator.getService("SMSService"); // 처음으로 서비스를 가져오면, 새 인스턴스 생성 후 반환
String sms = smsService.getMessageBody(); // "SMS message"

MessagingService emailService = ServiceLocator.getService("EmailService");
String newEmail = emailService.getMessageBody(); "email message"
```

- 단점

  - 모든 서비스들이 싱글톤 Service Locator 에 대해 참조를 해야한다.
    - 여전히 종속성을 생성
    - 대상이 하나에 몰려 있다는 점
  - 단위 테스트를 하기 어렵게 만듬
    - Service Locator 구현을 대체할 수 없음
    - DI 는 종속 클래스의 모의 객체를 테스트된 인스턴스에 전달할 수 있지만, 서비스 로케이터는 불가능
  - 인터페이스를 쉽게 바꿀 수 있어 문제가 될 법한 인터페이스 변경을 야기.

- Service Locator 패턴은 코드를 분리하는 간단한 패턴
- 이해하기 쉽고, 소규모 애플리케이션에 적합

---

### AOP(Aspect Oriented Programming)

- 관점 지향 프로그래밍
- 로직을 기준으로 핵심 기능과 부가적인 기능을 나누어 모듈화 진행
  - 모듈화 : 어떤 공통된 로직이나 기능을 하나의 단위로 묶음
- 횡단 관심사(Crosscutting Concerns)
  - 소스 코드 상에서 다른 클래스에서도 공통적으로 사용하는 코드
  - `Aspect`를 이용하여 모듈화하여 비즈니스 로직에서 분리 후 재사용
- 주요 개념
  - `PointCut`: Aspect 적용 위치 지정자
    - `"execution(**..repository.*.*(..))"`
    - <b>최근 애플리케이션은 Layered Architecture 기반</b>
    - <b>각 Layer 별 or 2~3 Layers 통합한 PointCut 정의하여 재사용</b>
  - `JoinPoint`: 스프링 프레임워크가 관리하는 빈의 모든 메서드
    - (광의) Aspect 적용이 가능한 모든 지점
    - (협의) 호출된 객체의 메서드
    - `ProceddingJoinPoint joinPoint`
    - `@Around("execution(**..repository.*.*(..))")`가 PointCut 일 때
      - `userRepository.save()` 가 호출된 상태라면
      - JoinPoint -> userRepository 객체의 save()
        - JoinPoint Parameter 를 사용하면, 실행 시점에 호출된 매서드의 정보 확인 가능
  - `Aspect`: Advisor 집합체
  - `Advice`: PointCut 적용할 로직, 즉 메서드
  - `Advisor`: 1개의 Advice + 1개의 PointCut -> Aspect가 나와서 사실상 무쓸모
- 스프링 AOP 특징
  - 인터페이스 기반
  - 프록시 기반
    - 제어 흐름을 조정하기 위한 목적으로 중간에 대리자를 두는 방식
    - `@Before`: 해당 메서드가 실행하기 전
    - `@After`: 해당 메서드가 실행된 후
    - `@Around`: 매서드 실행 전과 후(예외 발생 시에도 실행됨)
    - `@AfterThrowing`: 메소드에서 예외가 발생되면 실행
    - `@AfterReturning`: 메소드가 정상 종료될 때
  - 런타임 기반
    - 컴파일 타임 vs 런타임
    - 컴파일: 코드를 기계어로 바꾸는 것
    - 런타임: 기계어로 바꾼 코드를 실행
- DAO 실행 시간 측정 예제 코드

```java
@Slf4j
@Aspect // 이제 Aspect 사용하겠다
@Component // Bean 등록, 객체 생성과 의존성 주입을 스프링에 위임
public class MeasureTimeAspect {
    // repository 안에 적용, 물론 다른 레이어에도 적용 가능
    // repository 안의 객체의 메서드들은 빈으로 등록해줘야함 -> AOP 적용 대상이기 때문
    // proceed()의 리턴값은 Object
    @Around("execution(**..repository.*.*(..))")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        // before advice
        StopWatch sw = new StopWatch();
        sw.start(); // Start Time Check

        // 기준 이전 코드는 before
        Object result = joinPoint.proceed(); // 시점 중요
        // 기준 이후 코드는 after로 구분

        // after advice
        sw.stop(); // Stop Time Check
        Long total = sw.getTotalTimeMillis();

        // 어떤 클래스의 메서드를 측정했는지 joinPoint 객체 안에 담겨 있음
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String taskName = className + "." + methodName;

        // 실행시간 로그 남기기
        log.info("[ExecutionTime] : {}, {}(ms)", taskName, total)

        return result;
    }
}
```

- Result

```
com.app.apiconfig.ExecutionTimer : 실행 메서드: dbAccess, 실행시간 = 1023ms
```

---

### PSA

- 일관성 있는 서비스 추상화
- ex. JDBC
- 어댑터 페턴을 적용해 같은 일을 하는 다수의 기술을 공통의 인터페이스로 제어할 수 있게 하는 것을 서비스 추상화라고 함
  - 개발 폐쇄 원칙을 활용한 설계 패턴
  - 호출 당하는 쪽의 메서드를 호출하는 쪽의 코드에 대응하도록 중간에 변환기를 통해 호출하는 패턴

---

### 참고자료

- [스프링 의존성 주입(DI) 이란?](https://devlog-wjdrbs96.tistory.com/165)
- [서비스 로케이터 기본 개념](https://martinfowler.com/articles/injection.html#UsingAServiceLocator)
- [java-service-locator-pattern](https://www.baeldung.com/java-service-locator-pattern)
- [AOP를 이용해 로그 데이터 남기기](https://velog.io/@solar/AOP-%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%B4-%EB%A1%9C%EA%B7%B8-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EB%82%A8%EA%B8%B0%EA%B8%B0)
- [AOP(Aspect Oriented Programming) - DAO 실행 시간 측정 예제](https://victorydntmd.tistory.com/178)

---

# 1주차 - 스프링 튜토리얼

`Annotation`을 사용하는 이유는 명명패턴의 단점을 해결하기 위함이다.<br/>

## 명명패턴 단점

```java
// JUnit3 버전까지는 명명패턴 적용
public class TestWorld extends TestCase {
		// 메서드명의 접두사를 test로 시작하게끔
		public void testSafetyOverride() {
				...
		}
}
```

1. 오타가 나면 안됨
   - `tsetSafetyOverride` 로 오타를 내도 JUnit3은 메서드를 무시하고 지나가서 통과한 것처럼
2. 올바른 프로그램 요소에서만 사용된다고 보장 X
   - `TestSafetyMechanisms` 이라고 지어도 JUnit3은 이름에 관심 없음
   - 개발자가 의도한 테스트가 수행되지 않을 수 있음
3. 프로그램 요소를 매개변수로 전달할 마땅한 방법이 없다
   - 특정 예외를 던져야 성공하는 테스트가 있어도, 기대하는 예외 타입을 테스트에 매개변수로 전달해야 함

## Annotation

사전적인 정의로는 `주석` 이란 의미로, Java에서 `Anotation`은 코드 사이에 주석처럼 사용되며 특별한 의미, 기능을 수행하도록 하는 기술이다.<br/>
프로그램에 추가적인 정보를 제공하는 `meta data`라고 할 수 있다<br/>
사용하는 순서는 다음과 같다.

1. Anotation 을 정의한다.
2. 클래스에 Anotation을 배치한다.
3. 코드가 실행되는 중에 `Reflaction`을 이용하여 추가 정보를 획득하여 기능을 실시한다.

### Anotation 동작

이 부분에서는 `Anotation`의 동작 방식을 보여주고자 직접 제작한 작은 테스트 프레임워크(`@Test`)를 통해 보여주도록 하겠다.<br/>

- `Anotation`은 런타임과 컴파일 시에 해석이 된다는 특징
- `Anotation`으로 할 수 있는 일은 명명패턴으로는 처리할 수 없음
- 자바 프로그래머라면, ㅖ외 없이 자바가 제공하는 애너테이션 타입들을 사용

### 메타 애노테이션

```java
// 테스트 메서드임을 선언하는 애너테이션
// 현재는 매개변수가 없는 정적 메서드 전용
@Rentation(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test{

}
```

- `@Target`
  - 자바 컴파일러가 애노테이션 어디에 적용될지 결정하기 위해 사용
    - `CONSTRUCTOR` : 생성자 선언 시
    - `METHOD` : 메소드 선언 시
    - `TYPE` : 클래스, 인터페이스, Enum 선언 시
    - …
- `@Rentation`
  - 애노테이션이 실제 적용되고 유지되는 범위를 나타냄
  - `@Rentation(RetentionPolicy.요소)` 형식으로 사용
    - `SOURCE` : 컴파일 이후에도 JVM에 의해서 계속 참조
    - `CLASS` : 컴파일러가 클래스를 참조할 때까지
    - `RUNTIME` : 컴파일 전까지만 유효, 컴파일 후에는 사라짐
- `Documented`
  - 애노테이션이 지정된 대상의 JavaDoc에 애노테이션의 존재를 표기하도록 지정
- `Inherited`
  - 애노테이션을 사용한 상위 클래스를 상속한 하위 클래스에서도 해당 애노테이션을 갖도록 사용

### 마커 애너테이션

- “아무 매개변수 없이 단순히 대상에 마킹(marking) 한다”의 의미
- 애너테이션 사용시, 오타를 내거나 메서드 선언 외의 프로그램 요소에 달면 `컴파일 오류`

```java
public class Sample {
		@Test public static void m1()   // 성공
		@Test public static void m2() { // 실패
			throw new RuntimeException("실패");
		}
		@Test public void m3() {}       // 잘못 사용 예시
}
```

```java
// 마커 애너테이션 처리 프로그램
public class RunTests {
		public static void main(String[] args) throws Exception {
            int tests = 0;
            int passed = 0;
            // 아직 알려지지 않은 타입(비한정적 타입)
            // 매개변수 타입에 의존하지 않는 제네릭 클래스의 메서드 경우 사용
            Class<?> testClass = Class.forName(args[0]);
            for (Method m : testClass.getDeclaredMethods()) {
                // isAnnotationPresent 가 실행할 메서드를 찾아주는 메서드
                if (m.isAnnotationPresent(Test.class)) {
                        tests++;
                        try {
                m.invoke(null);
                passed++;
                                        // 테스트 메서드가 예외를 던지면 리플랙션 메커니즘이 감싸서 잡아줌
            } catch (InvocationTargetException wrappedExc) {
                Throwable exc = wrappedExc.getCause(); // 정보 추출
                System.out.println(m + " 실패: " + exc);
            } catch (Exception exc) {
                System.out.println("잘못 사용한 @Test: " + m);
            }
                }
            }
		}
}
```

## 매개변수를 받는 애너테이션

```java
// 매개변수 하나를 받는 애너테이션 타입
@Rentation(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test{
		/**
     * Indicates the <em>containing annotation type</em> for the
     * repeatable annotation type.
     * @return the containing annotation type
     */
		Class<? extends Throwable> value(); // value : 매개변수의 타입
}
```

- 특정 예외를 던져야만 성공하는 테스트를 만들기 위해서 `ExceptionTest` 애노테이션을 사용
- 타입이 `Class<? extends Throwable>` 인 매개변수
  - `Throwable` 의 확장한 하위 타입

```java
public class Sample2 {
		// ArithmeticException : 0으로 나누려고 시도할 때
    @ExceptionTest(ArithmeticException.class)
    public static void m1() { // 성공
        int i = 0;
        i = i / i;
    }

    @ExceptionTest(ArithmeticException.class)
    public static void m2() { // 실패 (다른 에러)
        int[] ints = new int[0];
        int i = ints[0];
    }

    @ExceptionTest(ArithmeticException.class)
    public static void m3() {} // 실패 (예외 발생 X)
}
```

```java
// 1개 매개변수를 받는 애너테이션 사용
// 매개변수를 받지 않은 메서드에서 catch 절만 수정
try {
    m.invoke(null);
    System.out.printf("테스트 %s 실패: 예외를 던지지 않음%n", m);
} catch (InvocationTargetException wrappedExc) {
    Throwable exc = wrappedExc.getCause();
    Class<? extends Throwable> excType // 이 부분에서 에러를 보고 기대한 에외가 맞는지
										= m.getAnnotation(ExceptionTest.class).value();
    if (excType.isInstance(exc)) {
        passed++;
    } else {
        System.out.printf("테스트 %s 실패: 기대한 예외 %s, 발생한 예외 %s%n", m, excType.getName(), exc);
    }
} catch (Exception exc) {
    System.out.println("잘못 사용한 @ExceptionTest: " + m);
}
```

- 에러 코드를 테스트하는 것이기 때문에, 필요한 로직이 `catch` 절로 내려옴
- `m.getAnnotation(Exception.class).value()`
  - `Exception.class` 가 붙은 메서드의 매개변수값을 반환
  - 현재 매개변수 값으로 `ArithmeticException` 1개 들어 있음
- 예외의 클래스 파일이 컴파일타임에는 존재했으나, 런타임에는 존재하지 않을 수 있음
  - `TypeNotPresentException` 발생

## 매개변수 2개 이상의 애너테이션

### 1. 배열 매개변수 애너테이션

```java
// 매개변수 여러 개를 받는 애너테이션 타입
@Rentation(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test{
		Class<? extends Throwable>[] value();
}
```

```java
// 원소들을 중괄호로 묶고, 쉼표로 구분
@ExceptionTests({IndexOutOfBoundsException.class, NullPointerException.class})
public static void doublyBad() {

}
```

```java
// 여러 개 매개변수를 받는 애너테이션 사용
// 매개변수를 받지 않은 메서드에서 catch 절만 수정
try {
    m.invoke(null);
    System.out.printf("테스트 %s 실패: 예외를 던지지 않음%n", m);
} catch (InvocationTargetException wrappedExc) {
    Throwable exc = wrappedExc.getCause();
    int oldPassed = passed;
    Class<? extends Throwable>[] excTypes  // 이 부분에서 배열로 매개변수 받기
							= m.getAnnotation(ExceptionTests.class).value();
    for (Class<? extends Throwable> excType : excTypes) {
        if (excType.isInstance(exc)) {
            passed++; // 존재하지 않으면 pass 늘리지 않아서 밑에 if 문 걸리게 됨
            break;
        }
    }
    if (passed == oldPassed) {
        System.out.printf("테스트 %s 실패: %s %n", m, exc);
    }
} catch (Exception exc) {
    System.out.println("잘못 사용한 @ExceptionTest: " + m);
}
```

- 반복문을 순회하면서, 예외가 일치하는지 확인

### 2. `@Repeatable` 메타 에너테이션을 활용

- 하나의 프로그램 요소에 여러 번 달 수 있게 해줌
- 주의 사항
  - `@Repeatable` 을 반환하는 “컨테이너 애너테이션”을 하나 더 정의하고 class 객체를 매개변수로 전달
  - “컨테이너 애너테이션”은 내부 애너테이션 타입의 배열을 반환하는 `value` 매서드 정의
  - 적절한 `@Retention`, `@Target` 을 설정
- 사용 이유
  - 별도의 컨테이너 애노테이션을 정의해야 해서 복잡해보이지만, 여러 값을 받아야하는 속성에 따라 하나하나 명시하는 것이 가독성 면에서 좋음

```java
// 실제 사용할 애노테이션
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(ExceptionTestContainer.class) // 묶고자 하는 애너테이션 자체에 선언
public @interface ExceptionTest {
    Class<? extends Throwable> value(); // value 에 들어갈 애노테이션 타입을 중복해서 사용
}

// 애너테이션 묶음 값을 관리할 컨테이너 애노테이션 작성
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExceptionTestContainer {
    ExceptionTest[] value();
}
```

```java
// 중복으로 애노테이션을 달 수 있음
@ExceptionTest(IndexOutOfBoundsException.class)
@ExceptionTest(NullPointerException.class)
public static void doublyBad() {
}
```

- 주의점
  - 반복 가능 애너테이션을 여러 개 달면, 하나만 달았을 때와 구분하기 위해 컨테이너 애너테이션 타입 적용
  - `getAnnotationByType`
    - 반복 가능 애너테이션, 컨테이너 애너테이션 모두 가져옴
  - `isAnnotationPresent`
    - 반복 가능 애너테이션이 달렸는지 검사하면 의도와 다른 결과 값을 반환
    - 반복 가능 애너테이션과 컨테이너 애너테이션 모두 확이내야 함

```java
//before
if (m.isAnnotationPresent(ExceptionTest.class)) {
		//...
}

//after
if (m.isAnnotationPresent(ExceptionTest.class)
|| m.isAnnotationPresent(ExceptionTestContainer.class)){
		//....
}
```

---

## 스프링에서 Anotation

## 1. 스프링 컨테이너 생성 과정

```java
ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
```

`ApplicationContext`를 스프링 컨테이너라고 부릅니다. <br>
`AnnotationConfigApplicationContext`는 ApplicationContext 인터페이스의 구현체입니다. <br> <br>

1. 위와 같이 AppConfig class를 파라미터로 넘기면 스프링 컨테이너에 key를 빈의 이름으로, value를 빈 객체로 갖는 **스프링 빈 저장소**가 생성됩니다. 이 구성 정보를 활용합니다.
2. **스프링 빈 등록**
   이후, AppConfig를 참고하여, `@Bean`이 달린 모든 메서드를 호출하며, 위에서 설명한 스프링 빈 저장소를 채웁니다. -> 스프링 빈을 채웁니다. 예를 들어, 아래와 같이 AppConfig가 구성 되어 있다면?

```java
  @Bean
  public MemberService memberService() {
    return new MemberServiceImpl(getMemberRepository());
  }

  @Bean
  public OrderService orderService() {
    return new OrderServiceImpl(getMemberRepository(),discountPolicy());
  }

  @Bean
  public MemoryMemberRepository getMemberRepository() {
    return new MemoryMemberRepository();
  }

  @Bean
    public DiscountPolicy discountPolicy() {
    return new RateDiscountPolicy();
  }
```

아래와 같이 스프링 빈 저장소가 채워집니다.
|빈 이름|빈 객체|
|:----:|:----:|
|memberService|memberService@x01|
|orderService|orderService@x02|
|memberRepository|memberRepository@x03|
|discountPolicy|discountPolicy@x04|

**주의:** 빈 이름은 **절대 중복 금지!**, 그리고 단순, 명확하게..

3. **스프링 빈 의존관계 설정! - 준비**
4. 스프링 빈 의존관계 설정 - 완료

- 스프링 컨테이너는 이 설정 정보들을 참고해서 (AppConfig 파일) 의존관계를 주입합니다! **DI!**
- 단순히 자바 코드를 호출하는 것을 넘는 의미가 있다 -> 뒷 차시 싱글톤 컨테이너에서 설명

<br> 결론:
**스프링 컨테이너 생성 과정: 스프링 빈 저장소 생성 -> 스프링 빈 등록 -> 스프링 빈 의존관계 설정 준비 -> 완료**

# 2. 컨테이너 등록된 빈 조회

```java
  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

  @Test
  @DisplayName("모든 빈 출력하기")
  void findAllBean() {
    String[] beanDefinitionNames = agetBeanDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames) {
      Object bean = ac.getBean(beanDefinitionName);
      System.out.println("name = " + beanDefinitionName + ", object = " + bean);
    }
  }

  @Test
  @DisplayName("애플리케이션 빈만 출력하기")
  void findApplicationBean() {
    String[] beanDefinitionNames = agetBeanDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames) {
      BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

      // Role ROLE_APPLICATION: 직접 등록한 애플리케이션 빈
      // Role ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 빈
      if (beanDefinition.getRole() == BeanDefinition.ROLE_INFRASTRUCTURE) {
        Object bean = ac.getBean(beanDefinitionName);
        System.out.println("name = " + beanDefinitionName + ", object = " + bean);
      }
    }
  }
```

- `getBeanDefinitionNames()`: 스프링에 등록된 모든 빈 이름 조회
- `getBean()`: 빈 이름으로 빈 객체를 조회한다.
  [ 애플리케이션 빈 출력용 Role 구분 ]
- `getRole()`로 빈들의 Role을 가져올 수 있다.
- BeanDefinition.ROLE_APPLICATION: 직접 등록한 애플리케이션 빈
- BeanDefinition.ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 빈

# 3. 스프링 빈 조회

평범하게 조회할 때는 `getBean()`이면 된다. 조회 대상 빈이 없으면 `NoSuchBeanDefinitionException` 예외가 발생한다.

- `ac.getBean(빈 이름, 타입)`
- `ac.getBean(타입)`
  구체 타입으로 조회할 수도 있다.

```java
  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

  @Test
  @DisplayName("빈 이름으로 조회")
  void findBeanByName() {
    MemberService memberService = ac.getBean("memberService", MemberService.class);
    assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
  }

  @Test
  @DisplayName("이름 없이 타입으로만 조회")
  void findBeanByType() {
    MemberService memberService = ac.getBean(MemberService.class);
    assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
  }
```

## 3.1 동일한 타입이 둘 이상일 때의 조회

동일한 타입이 둘 이상이면, 타입으로 빈을 가져올 때 오류가 발생한다. 그런 경우는 충분히 있을 수 있고, 그럴 때는 정확한 빈 이름을 지정하면 된다. `getBeansOfType()`을 사용하면 해당 타입의 모든 빈을 조회할 수 있다.

```java
  @Test
  @DisplayName("특정 타입을 모두 조회하기")
  void findBeanByType() {
    Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
    for (Stirng key : beansOfType.keySet()) {
      System.out.println("key = " + key + ", value = " + beansOfType.get(key));
    }
    System.out.println("beansOfType = " + beansOfType);
  }
```

## 3.2 상속관계 스프링 빈 조회 (중요)

스프링 빈을 조회할 떄, **부모 타입을 조회한다면 자식 타입이 전부 끌려 나온다.** depth가 어떻든 고구마 뿌리 뽑듯 전부 끌려 나온다. <br> 모든 스프링 빈을 조회하고 싶으면 어떻게 할까? **모든 자바 객체의 최고 부모인 `Object` 타입으로 조회를 해버리면, 모든 스프링 빈을 조회한다.** -> 부모 타입으로 조회할 때 자식 타입이 어디까지 조회되나는 좀 알고 있어야한다.

```java
  @Test
  @DisplayName("부모 타입으로 모두 조회하기")
  void findAllBeansByParentType() {
    Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
    Assertions.assertThat(beansOfType.size()).isEqualTo(2);
    for (String key : beansOfType.keySet()) {
      System.out.println("key = " + key + ", value = " + beansOfType.get(key));
    }
  }

  @Test
  @DisplayName("부모 타입으로 모두 조회하기 - Object")
  void findAllBeanByObjectType() {
    Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
    for (String key : beansOfType.keySet()) {
        System.out.println("key = " + key + ", value = " + beansOfType.get(key));
    }
  }
```

---

## @ComponentScan

스프링에서는 프로그램을 만들면서 필요에 따라 `Bean`을 등록한다. 빈을 등록하는 과정에서, 설정한 빈들을 찾기 위해 등록될 객체들을 탐색해야 한다. 탐색하는 것을 컴포넌트 스캔이라 부르며, 컴포넌트 스캔은 `@ComponentScan`을 설정 정보에 붙여주면 사용 가능하다.

```java
@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type =  FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

}
```

기본적으로 **`@Component` 애노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록합니다.** 스프링 빈에 등록하고 싶은 클래스들에 `@Component`를 달아주면 됩니다. `@Configuration`이 붙은 설정 정보도 자동으로 등록이 되는데, 이는 `@Configuration`의 소스 코드를 열어보면 `@Component`가 붙어있기 때문!

### 1. 탐색할 패키지 등록

컴포넌트 스캔은 탐색할 패키지의 시작 위치를 지정할 수 있습니다. <br>
`basePackages`에 원하는 패키지를 정리하면, 해당 패키지만을 탐색하여 `Bean`으로 등록하게 되고, 특정 클래스만을 지정해주고 싶은 경우 `basePackageClasses`에 클래스의 이름을 지정하면 됩니다.<br>
예시는 다음과 같습니다.<br>

- `member`, `service` 패키지만을 탐색하고 싶은 경우
- `Controller`, `Model` 클래스를 탐색하고 싶은 경우

```java
@Configuration
@ComponentScan(
  basePackages = { "hello.core.member", "hello.service" },
  basePackageClasses = {Controller.class, Model.class}
  excludeFilters = @ComponentScan.Filter(type =  FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
}
```

패키지가 너무 많거나, 특정 패키지만 스캔하고 싶을 때 위와 같이 원하는 부분만을 지정하여 스캠하도록 작성합니다.

## 2. Default

```java
package ceos18.core;
import ...
@Configuration
@ComponentScan(
  excludeFilters = @ComponentScan.Filter(type =  FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
}
```

`@ComponentScan`이 붙어있는 클래스인 AutoAppConfig가 속해있는 패키지인 ceos18.core 부터 ~ 하위 모든 패키지를 검사하는 것이 기본 설정<br>
Spring Boot나 강사님이 권장하는 방법은 **패키지 위치를 따로 지정하지 않고, 설정 정보 클래스의 위치를 프로젝트 최상단에 두는 것을 권장합니다!**
<br>

예를 들어 `com.ceos18`인 곳에 컴포넌트 스캔이 있으면 자동적으로 `com.ceos18.member`, `com.ceos18.service` 등을 체크해서 등록시켜준다.

### `@SpringBootApplication`

**컴포넌트 스캔은 달 필요가 없다?** <br>
`@SpringBootApplication`은 가장 처음 프로젝트를 만들면, 최상위 클래스에 만들어지는 스프링 부트 어플리케이션의 시작점 클래스를 나타내는 곳으로 `@ComponentScan` Anotation이 소스코드에 붙어 있는 것을 확인할 수 있다.<br>
<img width="1008" alt="스크린샷 2023-09-23 오후 3 03 55" src="https://github.com/GDSC-Hongik/Effective-Java/assets/89639470/6a089127-5d82-4266-99cf-3d6d75c153be">

### 기본 스캔 컴포넌트

아래 애노테이션들은 전부 `@Component`를 가지고 있습니다. **애노테이션 끼리 상속관계를 갖는다기 보다는, 스프링에서 애노테이션이 애노테이션을 들고 있도록 합니다.**

- `@Controller`: 스프링 MVC Controller로 인식
- `@Service`: 특별한 처리 X, 개발자들이 보기 위한 것
- `@Repository`: 스프링 데이터 접근 계층으로 인식, 데이터 계층 예외를 스프링 예외로 변환
- `@Configuration`: 스프링 설정 정보로 인식하고, 스프링 빈이 싱글톤을 유지하도록 추가 처리

### 컴포넌트 스캔 필터

필터를 통해 사용자가 직접 만든 `Anotation`도 추가해줄 수 있다.

```java
@ComponentScan(
  includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
  excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
)
```

---

## 테스트 기법

### 단위 테스트(Unit Test)

하나의 모듈을 기준으로 독립적으로 진행되는 가장 작은 단위의 테스트를 말한다. 여기서 모듈은 애플리케이션에서 작동하는 하나의 기능 또는 메서드를 말하며, 하나의 기능이 올바르게 동작하는지를 독립적으로 테스트하는 것이다.<br>
즉 "어떤 기능이 실행되면 어떤 결과가 나온다"정도의 테스트를 진행한다.<br>
단위 테스트 필요성은 아래와 같다.<br>

- 테스팅에 대한 시간과 비용을 절감할 수 있다.
- 새로운 기능 추가 시에 수시로 바르게 테스트 할 수 있다.
- 리팩토링 시에 안정성을 확보할 수 있다.
- 코드에 대한 문서가 될 수 있다.

좋은 단위 테스트의 특징은 다음과 같다.<br>

- 1개의 테스트 함수에 대해 assert를 최소화하라.
- 1개의 테스트 함수는 1가지 개념만을 테스트 하라

### 통합 테스트(Integration Test)

모듈을 통합하는 과정에서 모듈 간의 호환성을 확인하기 위해 수행되는 테스트를 말한다.<br>
애플리케이션은 보통 여러 개의 모듈들로 구성이 되고, 모듈들끼리 메세지를 주고 받으면서(함수 호출) 기능을 수행한다. 올바르게 연계되어 동작하는지 검증이 필요한데, 통합 테스트는 독립적인 기능에 대한 테스트가 아니라 웹 페이지로부터 API를 호출하여 올바르게 동작하는 지를 확인하는 테스트 방법이다.

### 스터디 자료의 단위 테스트 예제

어떤 것인지 잘 모르겠어서 일단 제출하겠습니다..

### 총정리

이번 1주차 미션을 통해 어노테이션과 테스트 방법들에 대해 깊이 공부할 수 있는 시간이었습니다. 단위 테스트 부분 또한 "클린 코드" 책을 참고하여 정리하고 싶었지만, 시간 상의 문제로 간단하게밖에 정리하지 못했던 점이 아쉽습니다.

### 참고자료

[이펙티브 자바 - Item 39. 명명 패턴보다 애너테이션을 사용하라](https://www.yes24.com/Product/Goods/65551284)<br>
[@ComponentScan이란 무엇인가?](https://mungto.tistory.com/448)<br>
[단위 테스트 vs 통합 테스트 차이](https://mangkyu.tistory.com/143)<br>

---

# 2주차 - DB 모델링과 JPA

당근마켓 DB 모델링을 할 때 아래와 같은 기능을 구현할 수 있도록 참고했다.<br>

- 물건 올리기
- 물건 찾기
- 1:1 채팅
- 구매 확정
- 리뷰(온도)
- 회원 기능

한가지씩 기능에서 어떤 부분을 중점으로 집중해서 설계를 했는지 정리해보겠습니다.<br>
기본적으로 모든 테이블의 공통 컬럼인 생성일(`FRST_REG_DT`)과 수정일(`LAST_CHG_DT`)은 `BaseTimeEntity`로 엔티티를 하나 빼서 자동 등록할 수 있게 설계하였습니다. 참고 부탁드립니다.

### 물건 올리기

물건 올리기 기능은 `물건 팔기` 페이지를 확인하며 필요한 정보들을 테이블에 담았다.<br>
해당 테이블은 `PROD_LIST`이며, 스마트폰을 기준으로 최상단에 이미지가 최대 10개까지 등록할 수 있도록 구현되어 있는데, `PROD_IMG_LIST` 테이블로 분리해서 이미지들을 저장할 수 있게 설계했다.<br>
제목(`PROD_TIT`), 제목을 설정하면 나타나는 키워드(`PROD_KEYW`), 키워드에 따라 선택적으로 등장하는 브랜드(`PROD_BRN`)와 사이즈(`PROD_SIZ`), 거래 방식으로 판매하기와 나눔하기 등 2가지 방법으로 나눌 수 있어(`TRD_CD`)를 Enum 타입으로 설계를 하였고, 각 거래 방식에 따라 박스 하단 가격 제안 받기 혹은 나눔 이벤트 열기 옵션을 선택할 수 있어(`TRD_OPT_CD`), 가격을 설정할 수 있는(`PROD_PRC`), 자세한 설명을 적는 란(`PROD_CONT`), 거래 희망 장소와 보여줄 동네 선택을 마지막에 확인해서 시간이 부족해서 아직 추가하지 못 했습니다.. 제출하고 바로 수정할게요 ㅜㅜ

### 물건 찾기

물건 찾기 기능은 물건 올리기를 설계하며 `PROD_LIST` 테이블에 해당 물건이 현재 팔린 물건인지, 예약되어 있는 물건인지 등을 확인할 수 있는 `TRD_STAT` 컬럼을 추가하며 마무리했습니다.

### 1:1 채팅

채팅 기능은 `CHAT_LIST` 테이블에 정의되어 있습니다.<br>
채팅을 보낸 사람(`SEND_NO`)와 받는 사람(`RECV_NO`)는 유저 테이블 `USR_LIST`의 PK를 FK로 받아와서 설계했고, 채팅창을 고정하는 핀 기능(`CHAT_PIN_YN`), 채팅의 알람 설정 기능(`CHAT_ALM_YN`), 상대 차단 기능(`CHAT_BLK`) 등으로 채팅을 관리하는 역할을 하는 테이블을 구성했습니다.<br>
추가로, 채팅을 주고 받았을 때 메세지를 관리하는 테이블(`CHAT_MSG_LIST`)을 설계했으며, 이는 보낸 사람(`SEND_NO`), 보낸 채팅방(`CHAT_NO`), 메세지 내용(`MSG_CONT`), 보낸 시간 등으로 구성되어 있습니다.

### 구매 확정

구매 확정 기능은 사실 당근 마켓 기준으로, 직접 만나서 거래하는 형식으로 진행되기 때문에 `Order` 테이블을 빼진 않고, `PROD_LIST`의 현재 판매 진행 상태(`TRD_STAT`)를 기준으로 구매가 진행되었는지 확인할 수 있도록 설계했습니다.<br>
추가로, 리뷰를 작성하면 구매자를 알 수 있기 때문에 `REVW_LIST`의 `USR_NO`로 구매자를 확인할 수 있으나, 현재 당근마켓 또한 구매자가 리뷰를 작성하지 않으면 누가 구매했는지 알 수 없는 상태인 것으로 보입니다. 제가 당근마켓을 통해 구매, 판매를 해본 경험이 없어서 더 많은 기능이 있는 건 확인하지 못했습니다..

### 리뷰(온도)

리뷰 기능은 `REVW_LIST` 테이블을 확인하면 알 수 있습니다.<br>
리뷰는 정말 간단하게 진행되는데, 거래가 어땠는지 상태를 총 3가지("별로에요", "좋아요!", "최고에요!")로 표현할 수 있어 이를 Enum 타입으로 `REVW_STAT`로 설계했습니다. 어떤 점이 좋았는지 체크박스 형태로 한 줄? 정도 작성할 수 있기에 `REVW_LINE` 컬럼을 정의했으며, 선택 사항으로 거래했던 경험(`REVW_CONT`)을 작성할 수 있으며 이는 선택사항이기 때문에 NULL도 가능합니다. 거래하는 이미지도 추가할 수 있어, (`REVW_IMG_URL`)을 테이블에 추가했습니다. 이미지 테이블로 옮기지 않은 이유는 1장밖에 첨부할 수 없는데, Join 하는 비용보다 테이블 내에 배치하는 것이 더 비용적으로 아낄 수 있지 않을까.. 라는 개인적인 생각입니다.

### 회원 기능

정말 마지막 회원 기능은 처음 회원가입을 진행해보면서 알 수 있었습니다. 이는 `USR_LIST` 테이블을 확인하면 됩니다.<br>
제일 처음 핸드폰 번호로 로그인을 할 수 있으며(`PH_NM`), 닉네임을 정해서(`NIC_NM`) 회원가입을 할 수 있습니다. 닉네임 같은 경우 12자 이상으로 작성할 수 없기 때문에 `varchar(12)`로 크기를 고정했습니다. 자신이 현재 위치한 곳(`ADR`)과 매너 온도(`MNN_TEMP`)는 36.5도가 기본값이기에 숫자를 점까지 총 4개의 문자를 넣을 수 있도록 크기를 4로 고정했습니다. 주소를 인증했는지(`ADR_CERT_YN`)와, 처음 회원가입을 한 기기는 어떤 것인지(`UA`) -> 이는 나중에 자기는 회원가입을 했던 기억이 없다. 막 이런 컴플레인이 걸렸을 때 해결할 수 있도록 기기값까지 받는다고 하던데 사실 잘 모르겠습니다. 그리고, 개인정보를 동의했는지(`PRI_YN`) 이는 동의를 해야 들어올 수 있기 때문에 default 로 'Y' 값을 주었습니다. 마케팅 수신 동의(`MKTG_YN`)를 마지막으로 모든 테이블의 컬럼을 설명드렸습니다.

### 최종 ERD

<img width="710" alt="스크린샷 2023-09-30 오후 10 03 27" src="https://github.com/Remedi2022/backend/assets/89639470/e3c1a2f3-8996-4cf1-b673-98b84be465bb">

### 놓친 부분

유저가 자신이 원하는 지역을 여러 군데 설정할 수 있으나, 이는 아직 생각을 못 했습니다.

### 설계하며 고려했던 사항들

기본적으로 개인 취향에 따라 디비를 설계하는 방법은 다르겠지만, 개인적으로 테이블, 컬럼 등 대문자와 약어를 사용하는 것을 좋아합니다.. 풀네임으로 컬럼의 이름을 작성하면 너무 오브젝트의 명이 너무 길어 코딩하기에 어려워진다는?? 생각과 이전에는 dbms 자체에서 30바이트로 이름을 설계할 수 있는 것을 제한했었는데,, 물론 요즘에는 100바이트 넘게 가능하다고 하나, 여러모로 길면 불편하다는 판단하에 대부분의 사람들이 30을 기준으로 설계한다고 들었습니다. 이번에는 char와 varchar의 차이점에 대해서 구글링을 통해 알아낸 결과, 고정 크기는 char라는 정보를 알고 이게 또 연산이 더 빠르다는 장점이 있다고 해서 Enum 값으로 설계할 수 있는 부분은 모두 char로 작성해봤습니다. Id 값은 필드를 구분하기 위한 것이면 Varchar, 서로 계산하기 위한 목적이면 INT를 사용한다고 합니다. 물론 체계를 어떻게 나누는지에 따라 다르겠지만, 이번 당근마켓을 기준으로 봤을 때 유저의 Id 값은 #GH249124 등 순서대로 증가하는 것이 아닌, 문자가 섞여 있는 것으로 확인하여 Varchar 작성했습니다. 하나의 테이블에 많은 컬럼을 다 넣은 경향이 없지는 않지만, 조인하는 것이 비용적으로 많이 든다는 생각을 계속 가져가면서 실제로 사용하면서 이 부분은 나눠야겠다라고 판단할 때 나누는 것이 더 좋다고 했던 조언이 기억에 남아 한 테이블에 넣었습니다. 테이블을 쪼개는 것은 성능보다도 사실 데이터 무결성과 정합성 차원에서 먼저 검토를 해야 한다고 하지만, 이 부분에 대해서 이해를 완벽하게 하지 못해 일단 시간이 부족하여 돌아갈 수 있도록 설계했습니다. 여기까지가 이번 모델링을 하면서 고려했던 부분들 입니다.
