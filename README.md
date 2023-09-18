<h1 align="center">
      Spring Tutorial 🌱
    <br/>
</h1>

<p align="center">
    <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
    <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
    <img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"/><br/>
    <b>CEOS 18th Backend Study 0주차</b><br>
</p>

<h3 align="center">
미션 주제: Spring이 지원하는 기술들을 조사한다 👩🏻‍💻
</h3>
<br>

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
• 스프링에서는 DI를 @Autowired 를 통해 수행한다. <br></p>

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
제어 흐름을 조정하기 위한 목적으로 중간에 <b>대리자</b>를 두는 디자인 패턴
</p>
<p align="center">
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

|AOP 용어|설명|
|:--------|:--------:|
|Advice|실질적인 부가 기능 로직을 정의하는 곳|
|Join point|Advice가 적용될 수 있는 모든 위치 ex. 메서드 실행 시점, 생성자 호출 시점, 필드 값 접근 시점 등등|
|Pointcut|조인 포인트 중에서 Advice가 적용될 위치를 선별하는 기능. 스프링 AOP는 프록시 기반이기에 메서드 실행 시점만 가능|
|Advisor|스프링 AOP에서만 사용되는 용어로 Advice + Pointcut 한 쌍|
|Aspect|Advice + Pointcut을 모듈화 한 것. (@Aspect)|

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
