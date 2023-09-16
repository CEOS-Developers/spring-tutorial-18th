# Spring이 지원하는 기술들

## 1. IoC/DI (Inversion of Control / Dependency Injection)

### 1-1. 개념 정리

- IoC : 기존에는 개발자가 자바 객체를 생성하고 객체간의 의존관계를 연결시키는 제어권을 가지고 있었음.

```
public class A
{

   private B b;

   public A()
   b = new B();
 }
}
```

이것을 서블릿과 EJB를 관리하는 컨테이너 넘긴 것이 IoC의 핵심임. 객체에 대한 제어권이 컨테이너에게 넘어가면서 객체의 생명주기를 관리하는 권한 또한 컨테이너들이 전담하게 됨. 객체의 생성에서부터 생명주기의 관리까지 모든 객체에 대한 제어권이 바뀐것을 의미하는 것이 제어권의 역전이 IoC임.

```
public class A
{
	@Autowired
	private B b;
}
```

- DI : DI는 Spring에서 새롭게 지원하는 IoC의 한 형태임. 각 계층과C클래스 사이에 필요로 하는 의존관계가 있다면 이를 컨테이너가 자동적으로 연결시켜 주는 것임.

  - cf) 의존도 주입에서 의존도의 의미란? : A클래스 내부에서 B,C 클래스의 메소드를 활용할 때, A클래스의 로직이 변경되어 B,C 클래스로 보내는 객체가 변경된다면 기존과 같게 변경하는 로직을 추가해야함. 이때 A와 B,C는 서로에 대해 의존성이 있는 것

    - DI의 방식 :

      - 필드 주입 : 의존성을 주입하고 싶은 필드에 @Autowired 어노테이션 붙이기

        ```
        public class Dessert
        {
            @Autowired
            private final CakeService cakeService;
            @Autowired
            private final DrinkService drinkService;
        }
        ```

        - 수정자 주입 : setter 메소드에 @Autowired 어노테이션 붙이기

        ```
        public class A
        {
          private B b;

          public void setB(B b)
          {
              this.b = b;
          }
        }
        ```

        - 생성자 주입 : 생성자를 사용하여 의존성을 주입하는 방식

          ```
          public class A
          {
              private B b;

              public A(B b)
              {
                  this.b = b;
              }
          }
          ```

        - 생성자 주입을 사용해야하는 이유 : NullPointerException을 막을 수 있음, 불변성을 활용할 수 있음, 단일 책임 원칙(SRP)을 지킬 수 있도록 유도함

### 1-2. 장점

- IoC :
  - 객체 간 결합도를 낮출 수 있음
    - 유연한 코드 작성 가능
    - 가독성 증진
    - 코드 중복 방지
    - 유지 보수 용이
- DI :
  - 모듈 간의 결합도가 낮아짐
    - 모듈 간 유연성이 높아짐
