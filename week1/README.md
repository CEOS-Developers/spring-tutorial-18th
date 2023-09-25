### 1️⃣ spring-boot-tutorial-18th 를 완료해요

![](https://velog.velcdn.com/images/aeyongdodam/post/e0cec0ec-7b27-4908-9cba-11aefcd454b0/image.png)
![](https://velog.velcdn.com/images/aeyongdodam/post/0636c5f7-f765-417a-8b6e-e3800849c9ec/image.png)

### 2️⃣ H2 데이터베이스를 연결해요

![](https://velog.velcdn.com/images/aeyongdodam/post/2038d763-4d84-4831-b7a5-234a8a7a564b/image.png)
![](https://velog.velcdn.com/images/aeyongdodam/post/dfeace3d-9cf9-40bf-ba9f-bff2e08ec1ae/image.png)

### 스프링 어노테이션을 심층 분석해요

<br/>
- 어노테이션이란 무엇이며, Java에서 어떻게 구현될까요?

- Java5부터 새롭게 추가된 문법요소

- 사전적으로는 "주석"이라는 의미를 가지고 있으며, 자바 코드에 @를 이용해 주석처럼 달아 특수한 의미를 부여해줌

- 어노테이션은 클래스와 메서드에 추가하여 다양한 기능을 부여하는 역할을 함.
  어노테이션을 활용하여 Spring Framework는 해당 클래스가 어떤 역할인지 정하기도 하고, 빈을 주입하기도 하며, 자동으로 getter나 setter를 생성하기도 함

- 어노테이션의 장점 : Annotation을 통하여 코드량이 감소하고 유지보수하기 쉬우며, 생산성이 증가됨

- 어노테이션의 용도 :

      1. 컴파일러에게 코드 작성 문법 에러를 체크하도록 정보를 제공함
      2. 소프트웨어 개발 툴이 빌드나 배치시 코드를 자동으로 생성할 수 있도록 정보를 제공
      3. 실행시(런타임시)특정 기능을 실행하도록 정보를 제공

  <br/>

- 어노테이션을 사용하는 순서 :

      1. 어노테이션을 정의
      2. 클래스에 어노테이션을 배치
      3. 코드가 실행되는 중에 Reflection을 이용하여 추가 정보를 획득하여 기능을 실시

<br/>

https://melonicedlatte.com/2021/07/18/182600.html

https://prinha.tistory.com/entry/%EC%9E%90%EB%B0%94-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%96%B4%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98-annotation%EC%9D%98-%EC%A0%95%EC%9D%98%EC%99%80-%EC%A2%85%EB%A5%98

- 스프링에서 어노테이션을 통해 Bean을 등록할 때, 어떤 일련의 과정이 일어나는지 탐구해보세요.

  - Spring Bean은 스프링 컨테이너에서 관리하는 자바 객체임
  - Bean 등록 Annotation

    1. @Component : 컴포넌트를 나타내는 일반적인 스테리오 타입으로 <bean> 태그와 동일한 역할을 함
    2. @Repository : 퍼시스턴스 레이어, 영속성을 가지는 속성(파일, 데이터베이스)을 가진 클래스
    3. @Service : 서비스 레이어, 비지니스 로직을 가진 클래스
    4. @Controller : 프리젠테이션 레이어, 웹 어플리케이션에서 웹 요청과 응답을 처리하는 클래스

  - 빈의 생명 주기 : 스프링 IoC 컨테이너 생성 -> 스프링 빈 생성(Constructor Injection) -> 의존관계 주입(Setter Injection 및 Field Injection이 일어남) -> 초기화 콜백 사용 -> 소멸전 콜백 ->스프링 종료

    1.  Spring IoC 컨테이너가 만들어지는 과정
        ![](https://velog.velcdn.com/images/aeyongdodam/post/2fa45345-7e02-406a-9efe-7d065154e263/image.png)

    2.  @Configuration 방법을 통해 Bean으로 등록할 수 있는 어노테이션들과 설정파일들을 읽어 IoC 컨테이너 안에 Bean으로 등록
    3.  의존 관계를 주입하기 전의 준비 단계가 존재 -> 이 단계에서 객체의 생성이 일어남
        cf) 생성자 주입 : 객체의 생성과 의존관계 주입이 동시에 일어남
        Setter, Field 주입 : 객체의 생성 ㅡ> 의존관계 주입으로 라이프 사이클이 나누어져 있음
    4.  스프링 빈에게 콜백 메소드를 통해 초기화 시점을 알려줌
    5.  실행
    6.  스프링 컨테이너가 종료되기 직전에도 소멸 콜백 메소드를 통해 소멸 시점을 알려줌
    7.  종료

https://velog.io/@alivejuicy/Spring-Bean-%EB%93%B1%EB%A1%9D-%EB%B0%8F-%EC%83%9D%EB%AA%85%EC%A3%BC%EA%B8%B0#%EB%B9%88-%EC%83%9D%EB%AA%85%EC%A3%BC%EA%B8%B0

https://csy7792.tistory.com/315
<br/>

- `@ComponentScan` 과 같은 어노테이션을 사용하여 스프링이 컴포넌트를 어떻게 탐색하고 찾는지의 과정을 깊게 파헤쳐보세요.

  - Spring Bean 등록 방법

    1. @Bean, @Configuration

       - @Bean 어노테이션 : 클래스를 빈으로 등록하기 위해서는 명시적으로 설정 클래스에서 @Bean 어노테이션을 사용해 수동으로 스프링 컨테이너에 빈을 등록
       - @Configuration 어노테이션 : 설정 클래스는 다음과 같이 @Configuration 어노테이션을 클래스에 붙여주면 되는데, @Bean을 사용해 수동으로 빈을 등록해줄 때에는 메소드 이름으로 빈 이름이 결정됨
       - 어떤 임의의 클래스를 만들어서 @Bean 어노테이션을 붙인다고 되는 것이 아니고, @Bean을 사용하는 클래스에는 **반드시 @Configuration 어노테이션을 활용하여 해당 클래스에서 Bean을 등록하고자 함을 명시**해주어야 함

       ```java
       @Configuration
       public class ResourceConfig {

       			@Bean
       			public MangKyuResource mangKyuResource() {
       					return new MangKyuResource();
       			}
       	}
       ```

    2. @Component 어노테이션
       - 수동으로 직접 빈을 등록하는 작업은 빈으로 등록하는 클래스가 많아질수록 상당히 많은 시간을 차지할 것이고, 생산력 저하를 일으킬 수 있음
       - 컴포넌트 스캔(Component Scan)을 사용해 @Component 어노테이션이 있는 클래스들을 찾아서 자동으로 빈 등록

  - Component Scan : Component Scan은 anotation인 @Component를 명시하여 Bean에 추가하는 방법. 클래스위에 anotation을 추가해주면 자연스럽게 Bean에 객체로 추가가 됨

  - 해당 anotation을 확인하면 바로 Spring의 Bean으로 포함되어 객체를 사용할 수 있게됨

  - @Bean과 @Component의 차이

    - @Bean, @Configuration<br/>

      - 수동으로 스프링 컨테이너에 빈을 등록하는 방법
      - 개발자가 직접 제어가 불가능한 라이브러리를 빈으로 등록할 때 불가피하게 사용
      - 유지보수성을 높이기 위해 애플리케이션 전범위적으로 사용되는 클래스나 다형성을 활용하여 여러 구현체를 빈으로 등록 할 때 사용
      - 1개 이상의 @Bean을 제공하는 클래스의 경우 반드시 @Configuration을 명시해 주어야 싱글톤이 보장됨

    - @Component<br/>

      - 자동으로 스프링 컨테이너에 빈을 등록하는 방법
      - 스프링의 컴포넌트 스캔 기능이 @Component 어노테이션이 있는 클래스를 자동으로 찾아서 빈으로 등록함
      - 대부분의 경우 @Component를 이용한 자동 등록 방식을 사용하는 것이 좋음
      - @Component 하위 어노테이션으로 @Configuration, @Controller, @Service, @Repository 등이 있음

    - 스프링이 컴포넌트를 탐색하고 찾는 과정 : - @Component를 찾는 범위는 기본적으로 @ComponentScan이 붙어있는 클래스의 위치를 포함하여, 하위 디렉토리 모두를 탐색
      cf) + 찾는 범위를 지정하는 방법

      ```java
      @ComponentScan( basePackages = "hello.core", }
      ```

      -> "hello.core" 디렉토리를 포함하여 하위 디렉토리 모두를 탐색하겠다는 의미

      ```java
      basePackages = {"hello.core", "hello.service"}
      ```

      - 이렇게 여러 시작 위치를 지정할 수도 있음
      - basePackageClasses : 지정한 클래스의 패키지를 탐색 시작 위치로 지정한다.
      - @ComponentScan 은 @Component 가 붙은 모든 클래스를 스프링 빈으로 등록함 이때 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자를 사용함
      - 성자에 @Autowired 를 지정하면, 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아서 주입함. 이때 기본 조회 전략은 타입이 같은 빈을 찾아서 주입하는 것.

https://cnu-jinseop.tistory.com/36
https://mangkyu.tistory.com/75#recentComments
https://januaryman.tistory.com/423
https://dev-coco.tistory.com/170

### \***\*단위 테스트와 통합 테스트 탐구\*\***

- 단위 테스트와 통합 테스트의 의미를 알아봅시다!

  - 단위테스트
    - 단위 테스트는 응용 프로그램에서 테스트 가능한 가장 작은 소프트웨어를 실행하여 예상대로 동작하는지 확인하는 테스트
    - 단위 테스트에서 테스트 대상 단위의 크기는 엄격하게 정해져 있지 않음, 그러나 테스트 대상 단위의 크기를 작게 설정해서 단위 테스트를 최대한 간단하고 디버깅하기 쉽게 작성해야함
    - 일반적으로 클래스 또는 메소드 수준으로 정해짐
    - 단위의 크기가 작을수록 단위의 복잡성이 낮아짐 따라서, 단위 테스트를 활용하여 동작을 표현하기 더 쉬워짐
      <br/><br/><br/>
  - 통합테스트 : 통합 테스트는 단위 테스트보다 더 큰 동작을 달성하기 위해 여러 모듈들을 모아 이들이 의도대로 협력하는지 확인하는 테스트
    - 통합 테스트는 단위 테스트와 달리 개발자가 변경할 수 없는 부분(ex. 외부 라이브러리)까지 묶어 검증할 때 사용
    - 이는 DB에 접근하거나 전체 코드와 다양한 환경이 제대로 작동하는지 확인하는데 필요한 모든 작업을 수행할 수 있음.
    - 그러나, 통합 테스트가 응용 프로그램이 완전하게 작동하는 걸 무조건 증명하지는 않음
    - 장점 : 단위 테스트에서 발견하기 어려운 버그를 찾을 수 있음
    - 단점 : 단위 테스트보다 더 많은 코드를 테스트하기 때문에 신뢰성이 떨어질 수 있음. 어디서 에러가 발생했는지 확인하기 쉽지 않아 유지보수하기 힘들 수 있음.

- 스터디 자료의 단위 테스트 예제는 엄밀한 의미의 단위 테스트라고 부를 수 있을까요?

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

위 코드의 경우 서비스 클래스로, 모든 테스트 db를 가져오는 역할을 한다. service단과 controller, repository가 연결되어있다는 점에서 엄밀한 의미의 단위 테스트라고 볼 수 없다. 단위 테스트의 경우 클래스 또는 메소드 수준으로 정의되는 것이 좋다.

https://tecoble.techcourse.co.kr/post/2021-05-25-unit-test-vs-integration-test-vs-acceptance-test/

## 느낀 점

스터디를 하면서 주제에 대한 키워드만 주는 것보다 순차적으로 물음에 대답하는 형식으로 조사하다보니 더 도움이 많이 된 것 같다. 스프링을 많이 써보지 않아서 자료 조사해야 할 것이 많았다. 이전에는 코드를 돌아가게 짜는 것에 급급했다면 이번에는 개념부터 차근차근 다시 복습하는 시간을 가져야할 것 같다.
