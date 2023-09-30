# ceos 스프링 1주차 미션

# 1주차 미션

---

## 1️⃣, 2️⃣ spring-boot-tutorial-18th 와 H2 DB 연동

<br>

`Test` Entity

```java
package com.ceos18.springboot;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Test {

	@Id
	private Long id;
	private String name;
}
```

<br>

`TestRepository`

```java
package com.ceos18.springboot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {}
```

<br>

`TestService`

```java
package com.ceos18.springboot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

<br>

`TestController`

```java
package com.ceos18.springboot;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/tests")
public class TestController {

	private final TestService testService;

	@GetMapping
	public List<Test> getAllTest() {
		return testService.fetchAllTests();
	}

}
```

<br>

> TEST table row 조회

![Untitled](https://github.com/CEOS-Developers/spring-tutorial-18th/assets/48885608/c0213647-0ff6-47d6-9d45-2e92ae6aca95)

<br>

> `/tests` 엔드포인트 확인

![Untitled 1](https://github.com/CEOS-Developers/spring-tutorial-18th/assets/48885608/3acdbf90-67c8-425b-ae0c-ba4e26f45128)

---

## 3️⃣ 스프링 어노테이션 분석

### 스프링 어노테이션이란?

어노테이션(Annotation) 은 자바의 문법으로 **자바 소스코드에 추가하여 사용할 수 있는 주석으로 메타데이터의 일종**이다.

하지만 실질적으로 단순 주석의 기능만을 하는 것이 아니라 **클래스나 메서드 등에 추가하여 다양한 기능을 부여하는 역할을 수행한다.**

예를 들어 Bean 을 주입하기도 하고, 자동으로 getter/setter 를 생성하기도 한다.

스프링 어노테이션을 사용할 때의 장점 :

- 간결한 코드 작성
- 유지보수 향상
- 생산성 증가
- Bean 주입, 컴포넌트 스캔, 의존성 주입 등 스프링 핵심 기능을 쉽게 사용할 수 있음

<br>

### 어노테이션은 Java 에서 어떻게 구현될까?

어노테이션을 가장 크게 두 가지로 나누자면 아래와 같이 나눌 수 있다.

- JDK 에서 제공하는 표준 어노테이션
- 서드파티 프레임워크/라이브러리 에서 제공하는 어노테이션

![Untitled 2](https://github.com/CEOS-Developers/spring-tutorial-18th/assets/48885608/d4f3fd0e-6067-4780-9651-2d1d149abf7f)

<br>

예시로 JDK 에서 지원하는 표준 어노테이션인 `@Override` 의 구현코드인 `Override.java` 를 살펴보자

![Untitled 3](https://github.com/CEOS-Developers/spring-tutorial-18th/assets/48885608/89d49a39-c024-4c4f-9291-b50d66f4d0e9)

<br>

Override 어노테이션은 자바 언어 수준에서 정의된 어노테이션이기 때문에 내부 구현이 없다.

실제 내부 처리는 자바 컴파일러 단과 런타임 환경에서 처리된다.

1. 자바 컴파일러 처리

   컴파일러는 **`@Override`** 어노테이션을 분석하여 해당 메서드가 부모 클래스 또는 인터페이스의 메서드를 오버라이딩하려는 것임을 인식한다.

   즉, **`@Override`** 어노테이션은 컴파일 타임에 정적으로 검사되며, 컴파일러에게 오버라이딩 관련 정보를 제공한다.

2. 런타임 환경에서의 동작

   런타임 때는 어노테이션이 직접적으로 영향을 주지 않는다.

   즉, 런타임 때 어노테이션은 코드의 가독성과 오류 방지 기능을 제공하는 주석의 역할을 한다.

커스텀 어노테이션을 만들 때는 메타 어노테이션을 사용해서 만든다.

1. 커스텀 어노테이션 정의

   `@interface` 라는 키워드를 사용하여 어노테이션을 정의한다.

   ```java
   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.METHOD)
   public @interface MyAnnotation {
       String value() default "";
   }
   ```

  <br>

2. 커스텀 어노테이션 사용

   어노테이션은 클래스, 메서드, 필드 등에 사용할 수 있다.

   <br>

   cf) 기타 어노테이션 적용 범위

   ```java
   public enum ElementType {
       /** Class, interface (including annotation interface), enum, or record
        * declaration */
       TYPE,

       /** Field declaration (includes enum constants) */
       FIELD,

       /** Method declaration */
       METHOD,

       /** Formal parameter declaration */
       PARAMETER,

       /** Constructor declaration */
       CONSTRUCTOR,

       /** Local variable declaration */
       LOCAL_VARIABLE,

       /** Annotation interface declaration (Formerly known as an annotation type.) */
       ANNOTATION_TYPE,

       /** Package declaration */
       PACKAGE,

       /**
        * Type parameter declaration
        *
        * @since 1.8
        */
       TYPE_PARAMETER,

       /**
        * Use of a type
        *
        * @since 1.8
        */
       TYPE_USE,

       /**
        * Module declaration.
        *
        * @since 9
        */
       MODULE,

       /**
        * Record component
        *
        * @jls 8.10.3 Record Members
        * @jls 9.7.4 Where Annotations May Appear
        *
        * @since 16
        */
       RECORD_COMPONENT;
   }
   ```

   이 때 `@` 기호를 사용하여 원하는 범위에 어노테이션을 적용할 수 있다.

   ```java
   public class MyClass {
       @MyAnnotation(value = "Hello")
       public void myMethod() {
           // 메서드 내용
       }
   }
   ```

<br>

### 스프링에서 Bean 을 등록할 때 이뤄지는 과정

cf) 자바 Bean vs 스프링 Bean

- 자바 Bean

  - 필드의 접근제어자는 private 으로만 되어있고, getter/setter 로만 접근 가능한 클래스
  - 기본생성자만을 가지는 클래스

  ⇒ 위 두 가지 조건을 만족하는 클래스를 자바 Bean 이라고 한다. (POJO 와 동일한 개념)

- 스프링 Bean
  - 스프링 IoC 컨테이너가 관리하는 Java 객체
    ⇒ 스프링에 의해 생성되고, 라이프 사이클을 수행하고, 의존성 주입이 일어나는 객체

1. Bean 정의
   <br>

   1-1. XML 파일에서 정의
   <br>

   ```xml
   <!-- applicationContext.xml -->

   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
              http://www.springframework.org/schema/beans/spring-beans.xsd">

       <!-- Bean 정의 -->
       <bean id="myBean" class="com.example.MyBean">
           <!-- 프로퍼티 설정 -->
           <property name="property1" value="value1" />
       </bean>

   </beans>
   ```

   <br>

   1-2. @Configuration 클래스에서 정의
   <br>

   ```java
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;

   @Configuration
   public class AppConfig {

       @Bean
       public MyBean myBean() {
           MyBean bean = new MyBean();
           bean.setProperty1("value1");
           return bean;
       }
   }
   ```

   <br>

   1-3. 어노테이션 기반으로 정의

   컴포넌트 스캔을 통해 어노테이션을 사용하여 Bean을 자동으로 등록하는 방법이다.
   주로 **`@Component`**, **`@Service`**, **`@Repository`**, **`@Controller`** 어노테이션을 사용한다.
   <br>

   ```java
   import org.springframework.stereotype.Component;

   @Component
   public class MyBean {

       // ...

   }
   ```

   <br>

2. Bean 스캐닝 및 등록

   정의된 Bean 을 **스프링 IoC 컨테이너**가 스캔하고 등록한다.

   <br>

3. Bean 생성

   등록된 Bean의 인스턴스를 생성한다.

   스프링은 해당 클래스의 기본생성자를 사용하여 Bean 인스턴스를 생성한다.

   이렇게 생성된 Bean 은 스프링 컨테이너 내부에서 관리된다.

   <br>

4. 의존성 주입

   **`@Autowired`**, **`@Inject`**, **`@Resource`** 등의 어노테이션을 사용하여 Bean 의존성을 주입한다.

   <br>

5. Bean 초기화

   스프링 컨테이너는 Bean 을 생성한 후 초기화 메서드를 호출한다.

   초기화 메서드는 **`@PostConstruct`** 어노테이션을 통해 Bean 을 초기화하고 기타 작업을 수행한다.

   <br>

6. Bean 사용

   Bean 이 초기화되면 어플리케이션 코드에서 해당 Bean 을 사용할 수 있다.

   <br>

7. Bean 소멸

   스프링 컨테이너가 종료될 때, Bean 의 소멸 메서드를 호출한다.

   소멸 메서드는 **`@PreDestroy`** 어노테이션을 사용해서 직접 정의할 수 있다.

   <br>

### 스프링이 컴포넌트를 탐색하는 원리

어노테이션 기반으로 정의된 Bean 일 때 스프링은 컴포넌트 스캔을 통해 Bean 을 등록한다.

( 반면에 `@Configuration`과 `@Bean`으로 정의한 Bean은 명시적으로 설정 클래스에서 정의한 빈으로 처리된다. 이러한 Bean들은 컴포넌트 스캔을 통해 찾지 않고, 직접 설정 클래스에서 정의한 것이므로 컴포넌트 스캔의 대상이 아니다. )

1. 컴포넌트 스캔 수행
   <br>

   스프링 컨테이너는 지정된 패키지 와 그 하위 패키지의 클래스에서 **`@Component`**, **`@Service`**, **`@Repository`**, **`@Controller`** 등과 같은 어노테이션을 가진 클래스를 찾는다.

   <br>

2. Bean 등록
   <br>

   해당 어노테이션이 발견된 클래스는 스프링 컨테이너에 의해 Bean 으로 등록된다.

---

## 4️⃣ 단위테스트와 통합테스트

### 단위테스트란?

단위테스트(Unit Test)는 개별적인 “Unit” 을 대상으로 하는 테스트이다.

“Unit” 은 함수, 메서드, 클래스와 같이 작은 부분을 의미한다.

단위테스트의 특징은 아래와 같다.

- **독립성**: 단위테스트는 다른 유닛과 독립적으로 실행되어야 한다.
- **자동화**: 단위테스트는 자동으로 실행되어야 하며, 빠르게 실행될 수 있어야 한다.
- **반복 가능성**: 동일한 테스트를 반복적으로 실행해도 항상 동일한 결과를 가져와야 한다.

자바 진영에서는 JUnit, Mockito 와 같은 테스트 프레임워크를 사용하여 단위테스트를 작성한다.

<br>

### 통합테스트란?

통합테스트(Intergration Test)는 여러 모듈을 통합하여 전체 시스템을 테스팅하는 방법이다.

여러 레이어 (Service,Repository, Controller) 를 한꺼번에 테스팅하는 코드는 통합테스트 코드이다.

따라서 우리 예제의 테스트 코드는 통합테스트 코드라고 할 수 있다.

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

특히 spring 컨테이너가 올라가는 테스트는 단위 테스트가 아닌 통합 테스트이다. (`@SpringBootTest`)

( 단위 테스트는 순수 자바로만 테스팅 가능해야함 )

---

## 5️⃣ 회고 및 느낀 점

- 실제 어노테이션 클래스 코드를 뜯어보고 분석해본 것은 처음이라서 재밌었고 도움이 많이 됐다.
- Bean 라이프사이클에 대해서 공부할 수 있어서 좋았다.

---

## 6️⃣ Reference

자바의 정석 3판

[Java Bean VS Spring Bean](https://jjingho.tistory.com/10)

[Java에서 어노테이션(Annotation) 이란 무엇인가에 대해 알아보자.](https://honeyinfo7.tistory.com/56)
