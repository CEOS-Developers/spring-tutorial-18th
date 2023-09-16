# Spring 프레임워크란

Spring 은 IoC/DI, AOP, PSA 등의 개념을 바탕으로 객체지향적인 설계를 지향하는 자바 진영의 OOP 프레임워크이다.

![Untitled](https://github.com/CEOS-Developers/spring-tutorial-18th/assets/48885608/b1d05181-6604-4f20-a5a0-a38d49a425a2)

# 스프링 삼각형

POJO(Plain Old Java Object)를 기반으로 하는 **IoC/DI**, **AOP**, **PSA** 의 스프링 3대 프로그래밍 모델을 스프링 삼각형이라고 한다.

# IoC/DI

- IoC(Inversion of Control / 제어의 역전)
- DI(Dependency Injection / 의존성 주입)

## 의존성이란

**💡 전체는 부분에 의존한다**

![Untitled 1](https://github.com/CEOS-Developers/spring-tutorial-18th/assets/48885608/c1a0ded8-399e-4711-813e-08d0758254fe)

Tire 는 여러 종류의 Tire 가 있으므로 Tire interface 를 구현한 KoreaTire 와 AmericaTire 이 있다고 가정해보자.

전체 : Car

부분 : Tire

Car 에는 Tire 가 필요하다 ( ⇒ 즉, Car 는 Tire 에 의존적이다 )

## 의존성 주입(DI)이 필요한 이유

```java

public class Car {

  Tire tire;

  public Car() {
    tire = new KoreaTire();
    // tire = new AmericaTire();
  }

}
```

Car 클래스의 생성자에서 KoreaTire 를 생성한다

위 코드는 의존성을 외부에서 주입받은 것이 아니고, Car 클래스 내부에서 의존성을 직접 해결한 경우이다.

그렇다면 위 코드의 문제점은 무엇일까?

- 문제점 1 : 모든 Car 가 같은 Tire 을 사용하게 된다.

```java
Car car1 = new Car();
Car car2 = new Car();

// car1 과 car2 모두 KoreaTire 로 고정됨
```

- 문제점 2 : Car 의 tire 을 바꾸고 싶다면 Car 클래스의 생성자를 직접 수정해야한다.

```java

public class Car {

  Tire tire;

  public Car() {
    // tire = new KoreaTire();
    tire = new AmericaTire();
  }

}
```

🚨 Car 클래스와 Tire 클래스의 **결합도가 강해서** 코드의 **확장성**이 떨어진다. ChinaTire, JapanTire 등이 생길 때 매번 코드를 수정해야 한다

의존성 주입 으로 위 문제를 해결할 수 있다

의존성 주입에는 크게 세 가지 방법이 있다

1. 생성자 주입
2. setter 주입
3. 필드 주입

### 생성자 주입

```java
public class Car {

  Tire tire;

  public Car(Tire tire) {
    this.tire = tire;
  }

}
```

✅ 생성자의 인자로 tire 를 받는다

외부에서 Car 의 tire 의존성을 주입해준다

```java
Tire tire = new KoreaTire();

Car car = new Car(tire); // 생성자 주입
```

✅ Car 입장에서는 어떤 Tire 를 장착할 지 고민하지 않아도 된다. (책임을 외부에 위임)
✅ Car 클래스와 Tire 클래스의 **결합이 느슨해진다**

### setter 주입

다른 말로는 속성을 통한 의존성 주입이라고도 한다.

```java
public class Car {

  Tire tire;

  public void setTire(Tire tire) {
    this.tire = tire
  }

}
```

✅ setter 에서 tire 를 받아서 tire 필드를 설정한다.

```java
Tire tire = new KoreaTire();

Car car = new Car();

car.setTire(tire); // setter 주입
```

### 필드 주입

```java
import org.springframework.beans.factory.annotation.Autowired;

public class Car {

 @Autowired
 Tire tire;

}
```

```java
import org.springframework.stereotype.Component;

@Bean
public class KoreaTire implements Tire {
    // ...
}
```

✅ @Bean 같은 어노테이션을 사용하면 Bean 에 Tire 가 등록된다.

✅ 필드 주입은 한 번 주입된 의존성을 변경하지 않고 유지하는 경우에 적합하다

(cf. @Qualifier 어노테이션을 사용하면 직접 Bean 을 선택할 수 있긴 함)

## IoC 란?

객체들 간의 관계 및 호출을 개발자가 아니라 스프링 프레임워크에게 맡기는 것을 **제어의 역전** 이라고 한다.

- Spring IoC 컨테이너의 역할

  1. **빈(Bean) 관리**

     IoC 컨테이너는 애플리케이션에서 사용되는 객체(빈)를 생성하고 관리한다.

     개발자는 객체 생성과 관리에 대한 부분을 신경 쓰지 않아도 된다

  2. **의존성 주입(Dependency Injection)**

     IoC 컨테이너는 빈 간의 의존성을 관리하고 필요한 의존성을 주입한다. 이로써 객체 간의 결합도를 낮추고, 코드의 재사용성과 유지보수성을 향상시킵니다.

  3. **라이프사이클 관리**

     IoC 컨테이너는 빈의 라이프사이클을 관리하며, 초기화와 소멸 시점에 콜백 메서드를 호출할 수 있다.

     예를 들어, 빈 초기화 시 특정 설정을 수행하거나, 빈 소멸 시 리소스를 정리하는 등의 작업을 수행할 수 있다.

  4. **설정 관리:** Spring IoC 컨테이너는 애플리케이션 설정을 관리하고, XML, Java 설정 클래스, 어노테이션 기반의 설정을 지원한다.

- IoC vs DI
  - IoC 는 디자인패턴이고, DI 는 IoC 를 구현한 방식의 하나이다.
    DI 는 IoC 의 핵심 원칙 중 하나인 “의존성 역전” 을 실제로 구현하는 방법 중 하나일 뿐이다.
  - DI 는 IoC 의 하위개념이다.

---

# AOP

- AOP 란?
  Aspect-Oriented Programming : 관점 지향 프로그래밍

DI 가 의존성(new)의 주입이라면, AOP 는 로직(code)의 주입이다.

코드 = 핵심 관심사 + 횡단 관심사

- 핵심 관심사 : 각 모듈 별 핵심 로직
- 횡단 관심사(cross-cutting concern) : 여러 모듈에 공통적으로 나타나는 로직

![Untitled 2](https://github.com/CEOS-Developers/spring-tutorial-18th/assets/48885608/29a776fb-ddc6-4464-b09a-65509249b7c7)

- 메서드에 로직을 주입할 수 있는 곳
  ![Untitled 3](https://github.com/CEOS-Developers/spring-tutorial-18th/assets/48885608/7f5f6390-b0ed-426e-800b-c05db066c450)
  - Around
  - Before
  - After
  - AfterRetruning
  - AfterThrowing

---

# PSA

- PSA(Portable Service Abstraction) : 일관성 있는 서비스 추상화

어댑터 패턴을 적용해서 같은 일을 하는 다수의 기술을 공통의 인터페이스로 제어할 수 있게 한 것을 PSA(일관성 있는 서비스 추상화) 라고 한다.

예를 들어 MySQL, PostgreSQL 등 다른 기술들을 사용하더라도 JDBC 라는 공통의 표준 스펙(인터페이스)을 통해서 공통된 방식으로 코드를 작성할 수 있다.
