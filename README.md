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

