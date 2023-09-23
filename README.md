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
