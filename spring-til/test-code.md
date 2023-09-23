# 단위 테스트
> ```Unit Test```
> 응용 프로그램에서 테스트 가능한 가장 작은 소프트웨어를 실행하여 예상대로 동작하는지 확인하는 테스트

- 종류 : 화이트박스 테스트
- 테스트 대상 단위 : 클래스 또는 메소드
  - 테스트 대상 단위의 크기는 작게! <- ```테스트 단위의 복잡성 갑소```
- 프레임워크 : JUnit5
- TDD + 단위 테스트 = GOOD
  - TDD (테스트 주도 개발) : 테스트케이스를 작성 한 후 실제 코드를 개발

# 통합 테스트
> ```Integration```
> 단위 테스트보다 더 큰 동작을 달성하기 위해 여러 모듈들을 모아 이들이 의도대로 협력하는지 확인하는 테스트

- 사용 이유 : 개발자가 변경할 수 없는 부분(ex. 외부 라이브러리)까지 묶어 검증할 때 사용
  - DB에 접근하거나 전체 코드와 다양한 환경이 제대로 작동하는지 확인하는데 필요한 모든 작업을 수행 가능
- 주의사항 : 통합 테스트가 응용 프로그램이 완전하게 작동하는 걸 무조건 증명하지는 않음
- 장점 : 단위 테스트에서 발견하기 어려운 버그를 찾을 수 있음
- 단점 : 단위 테스트보다 더 많은 코드를 테스트하기 때문에 신뢰성이 떨어질 수 있음 & 어디서 에러가 발생했는지 확인하기 쉽지 않아 유지보수하기 힘듦
- 어노테이션 : ```@SpringBootTest```

## 참고 자료
[단위 테스트 vs 통합 테스트 vs 인수 테스트](https://tecoble.techcourse.co.kr/post/2021-05-25-unit-test-vs-integration-test-vs-acceptance-test/)

## 생각해 볼 부분
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
<details>
  <summary>1. 위의 코드를 단위 테스트라고 부를 수 있을까?</summary>
  No, 주어진 코드는 Repository를 사용하여 DB의 Test 테이블에 저장되어있는 모든 정보를 불러오는 Service 클래스로, 실질적인 테스트 코드의 기능을 하고 있지 않다.
</details>

<details>
  <summary>2. 만약 위의 코드가 단위 테스트가 아니라면, 단위 테스트로 바꾸기 위해 어떻게 수정해야하는가?</summary>
  Service 클래스와 별개로, 단위 테스트를 위한 클래스를 test 패키지 내부에 생성하여 JUnit5을 사용한 테스트 코드를 작성할 수 있을 것이다.
</details>