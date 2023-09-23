# Annotation
- 자바 소스 코드에 추가하여 사용할 수 있는 메타데이터의 일종
- 클래스와 메서드에 추가하여 다양한 기능을 부여
- Spring Bean을 Spring IoC Container에 등록하는 방법의 일종

## Annotation의 사용 이유
- 컴파일러에게 코드 작성 문법 에러를 체크하도록 정보를 제공
- 소프트웨어 개발 툴이 빌드나 배치시 코드를 자동으로 생성할 수 있도록 정보를 제공
- 실행시(런타임시)특정 기능을 실행하도록 정보를 제공

## Annotation 사용 순서
1. 어노테이션을 정의한다.
2. 클래스에 어노테이션을 배치한다.
3. 코드가 실행되는 중에 Reflection을 이용하여 추가 정보를 획득하여 기능을 실시한다

## Annotation의 장점
- 코드량 감소
- 유지보수 용이
- 생산성 증가

## Annotation 종류
> 대표적인 Annotation
### Spring Annotation
- ```@Override```
- ```@Deprecated```
- ```@Bean```
- ```@Controller```
- ```@RequestHeader```
- ```@RequestMapping```
- ```@RequestParam```
- ```@RequestBody```
- ```@ModelAttribute```
- ```@ResponseBody```
- ```@Autowired```
- ```@GetMapping```
- ```@PostMapping```
- ```@SpringBootTest```
- ```@Test```

### Lombok Annotation
- ```@Setter```
- ```@Getter```

## Annotation을 통한 Bean 등록
- ```@Component```
  - 해당 어노테이션이 등록되어있는 경우, Spring이 Annotation을 확인하고 자체적으로 Bean 등록

- ```@ComponentScan```
  - ```@Component```, ```@Service```, ```@Repository```, ```@Controller```, ```@Configuration``` 중 1개라도 등록된 클래스를 찾으면, Context에 ```Bean```으로 등록
  - ```@ComponentScan``` Annotation이 있는 클래스의 하위 ```Bean```을 등록 될 클래스들을 스캔하여 ```Bean```으로 등록

### 예시
- Controller를 등록하는 경우 -> ```@Controller``` Annotation 사용
```java
@Controller
public class ExController {

  @GetMapping("/")
  public String index() {
    return "Greetings from Spring Boot!";
  }

}
```
- ```@Controller``` Annotation에 ```@Component``` Annotation이 포함되어 있음
- 따라서, Spring은 ```ExController```를 ```Bean```으로 등록
```java
// Controller.java

// -- 일부 생략 --
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Controller {

	/**
	 * The value may indicate a suggestion for a logical component name,
	 * to be turned into a Spring bean in case of an autodetected component.
	 * @return the suggested component name, if any (or empty String otherwise)
	 */
	@AliasFor(annotation = Component.class)
	String value() default "";

}
```

### 참고 자료
- [스프링 빈(Spring Bean)이란? 개념 정리](https://melonicedlatte.com/2021/07/11/232800.html)
- [스프링(Spring)에서 자주 사용하는 Annotation 개념 및 예제 정리](https://melonicedlatte.com/2021/07/18/182600.html)
- [[Spring Boot] 어노테이션 정리](https://sddev.tistory.com/225)

