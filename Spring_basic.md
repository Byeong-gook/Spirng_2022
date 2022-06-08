![스프링 핵심 원리 - ](C:\Users\User\Desktop\김영한 강의\스프링 핵심 원리 - 기본편.png)



# 역활과 구현을 분리하면?

- 실세계의 역할과 구현이라는 편리한 컨셉을 다형성을 통해 객체 세상으로 가져올 수 있음
- 유연하고, 변경이 용이
- 확장 가능한 설계
- 클라이언트에 영향을 주지 않는 변경 가능 ( 요구사항이 바껴도 구현체만 나사 바꾸듯이 갈아 끼워주면됨 )
- 인터페이스를 잘 설계하는것이 중요하다.



# 좋은 객체 지향 설계의 다섯가지 원칙 (SOLID)

클린코드로 유명한 로버트 마틴이 좋은 객체 지향 설계의 5가지 원칙을 정리
• SRP: 단일 책임 원칙(single responsibility principle)
• OCP: 개방-폐쇄 원칙 (Open/closed principle)
• LSP: 리스코프 치환 원칙 (Liskov substitution principle)
• ISP: 인터페이스 분리 원칙 (Interface segregation principle)
• DIP: 의존관계 역전 원칙 (Dependency inversion principle)



SRP 단일 책임 원칙
• 한 클래스는 하나의 책임만 가져야 한다.
• 하나의 책임이라는 것은 모호하다.
• 클 수 있고, 작을 수 있다.
• 문맥과 상황에 따라 다르다.
• 중요한 기준은 변경이다. 변경이 있을 때 파급 효과가 적으면 단일 책임 원칙을 잘 따른 것
• 예) UI 변경, 객체의 생성과 사용을 분리



OCP 개방-폐쇄 원칙
Open/closed principle
• 소프트웨어 요소는 확장에는 열려 있으나 변경에는 닫혀 있어야 한다
• 이런 거짓말 같은 말이? 확장을 하려면, 당연히 기존 코드를 변경?
• 다형성을 활용해보자
• 인터페이스를 구현한 새로운 클래스를 하나 만들어서 새로운 기능을 구현
• 지금까지 배운 역할과 구현의 분리를 생각해보자



LSP 리스코프 치환 원칙
Liskov substitution principle
• 프로그램의 객체는 프로그램의 정확성을 깨뜨리지 않으면서 하위 타입의 인스턴스로 바꿀
수 있어야 한다
• 다형성에서 하위 클래스는 인터페이스 규약을 다 지켜야 한다는 것, 다형성을 지원하기 위
한 원칙, 인터페이스를 구현한 구현체는 믿고 사용하려면, 이 원칙이 필요하다.
• 단순히 컴파일에 성공하는 것을 넘어서는 이야기
• 예) 자동차 인터페이스의 엑셀은 앞으로 가라는 기능, 뒤로 가게 구현하면 LSP 위반, 느리
더라도 앞으로 가야함



ISP 인터페이스 분리 원칙
Interface segregation principle
• 특정 클라이언트를 위한 인터페이스 여러 개가 범용 인터페이스 하나보다 낫다
• 자동차 인터페이스 -> 운전 인터페이스, 정비 인터페이스로 분리
• 사용자 클라이언트 -> 운전자 클라이언트, 정비사 클라이언트로 분리
• 분리하면 정비 인터페이스 자체가 변해도 운전자 클라이언트에 영향을 주지 않음
• 인터페이스가 명확해지고, 대체 가능성이 높아진다.



DIP 의존관계 역전 원칙
Dependency inversion principle
• 프로그래머는 “추상화에 의존해야지, 구체화에 의존하면 안된다.” 의존성 주입은 이 원칙
을 따르는 방법 중 하나다.
• 쉽게 이야기해서 구현 클래스에 의존하지 말고, 인터페이스에 의존하라는 뜻
• 앞에서 이야기한 역할(Role)에 의존하게 해야 한다는 것과 같다. 객체 세상도 클라이언트
가 인터페이스에 의존해야 유연하게 구현체를 변경할 수 있다! 구현체에 의존하게 되면 변
경이 아주 어려워진다



객체 지향의 핵심은 다형성



#  관심사의 분리



인터페이스는 인터페이스의 역할만 수행하고

구현체는 구현체의 역할을 수행하는것이 가장 좋은 코드이다.

구현체가 인터페이스에게만 의존하도록



어떤 구현 객체가 주입될지는 외부에서 결정할수 있게 (ex) AppConfig.class



# 수동 빈 설정

@Configuration
public class AppConfig {
 @Bean
 public MemberService memberService() {
 return new MemberServiceImpl(memberRepository());
 }
 @Bean
 public OrderService orderService() {
 return new OrderServiceImpl(
 memberRepository(),
 discountPolicy());
 }
 @Bean
 public MemberRepository memberRepository() {
 return new MemoryMemberRepository();
 }
 @Bean
 public DiscountPolicy discountPolicy() {
 return new RateDiscountPolicy();
 }
}



설정을 구성한다는 뜻의 @Configuration

각 메서드 마다 @Bean 어노테이션을 부여함으로써 스프링 컨테이너가 해당 메서드를 빈으로 등록!



# 스프링 컨테이너



ApllicationContext <- 스프링 컨테이너



스프링 컨테이너는 @Configuration 이 붙은 클래스파일을 설정 정보로 사용하고

@Bean 이라 적힌 메서드를 모두 호출해서 반환된 객체를 컨테이너에 저장? 등록한다.

이렇게 스프링 컨테이너에 등록된 객체를 스프링 빈이라고 한다.

ㄴ @Bean(name="memberService2")  

해당 코드처럼 빈 이름을 직접 부여할수도 있음



스프링 빈은 applicationContext.getBean() 메서드를 사용하여 찾을수 있다.



//스프링 컨테이너 생성
ApplicationContext applicationContext = new AnnotationConfigApplicationContext(클래스 명);



스프링 컨테이너는 설정 정보를 참고해서 의존관계(Dependency Injection) 주입을 한다.



class ApplicationContextInfoTest {
 AnnotationConfigApplicationContext ac = new
AnnotationConfigApplicationContext(AppConfig.class);
 @Test
 @DisplayName("모든 빈 출력하기")
 void findAllBean() {
 String[] beanDefinitionNames = ac.getBeanDefinitionNames();
 for (String beanDefinitionName : beanDefinitionNames) {
 Object bean = ac.getBean(beanDefinitionName);
 System.out.println("name=" + beanDefinitionName + " object=" +
bean);
 }
 }
 @Test
 @DisplayName("애플리케이션 빈 출력하기")
 void findApplicationBean() {
 String[] beanDefinitionNames = ac.getBeanDefinitionNames();
 for (String beanDefinitionName : beanDefinitionNames) {
 BeanDefinition beanDefinition =
ac.getBeanDefinition(beanDefinitionName);
 //Role ROLE_APPLICATION: 직접 등록한 애플리케이션 빈
 //Role ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 빈
 if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
 Object bean = ac.getBean(beanDefinitionName);
 System.out.println("name=" + beanDefinitionName + " object=" +
bean);
​       }
   }
 }
}

ac.getBeanDefintionNames() : 스프링에 등록된 모든 빈 이름을 조회한다.

ac.getBean() : 빈 이름으로 빈 객체(인스턴스)를 조회한다.



## 애플리케이션 빈 출력하기



스프링이 내부에서 사용하는 빈은 제외하고, 내가 등록한 빈만 출력해보자.

스프링이 내부에서 사용하는 빈은 getRole() 로 구분할 수 있다.

ROLE_APPLICATION : 일반적으로 사용자가 정의한 빈

ROLE_INFRASTRUCTURE : 스프링이 내부에서 사용하는 빈



--스프링 빈은 부모타입으로 조회하면, 자식타입도 함께 조회된다.

그래서 모든 자바 객체 최고의 부모인 Object 타입으로 조회시, 모든 스프링 빈이 조회됨



## BeanFactory



스프링 컨테이너의 최상위 인터페이스이다.

스프링 빈을 관리하고 조회하는 역할을 담당한다.



getBean()을 제공





## ApplicationContext



BeanFactory 기능을 모두 상속받아서 제공하며 (BeanFactory 와 다른점은)



다른 부가기능을 제공함



메시지소스를 활용한 국제화 기능

예를 들어서 한국에서 들어오면 한국어로, 영어권에서 들어오면 영어로 출력

환경변수

로컬, 개발, 운영등을 구분해서 처리

애플리케이션 이벤트

이벤트를 발행하고 구독하는 모델을 편리하게 지원

편리한 리소스 조회

파일, 클래스패스, 외부 등에서 리소스를 편리하게 조회



정리

- ApplicationContext는 BeanFactory의 기능을 상속받는다.
- ApplicationContext는 빈 관리기능 + 편리한 부가 기능을 제공한다.
- BeanFactory를 직접 사용할 일은 거의 없다. 부가기능이 포함된 ApplicationContext를 사용한다.
- BeanFactory나 ApplicationContext를 스프링 컨테이너라 한다



java 코드로된 설정정보 이외에 xml 기반으로도 설정정보를 만들수 있음.

다양한 기반의 설정 정보를 만들수 있는 이유?



스프링 빈 설정 메타 정보 - BeanDefinition
스프링은 어떻게 이런 다양한 설정 형식을 지원하는 것일까? 그 중심에는 BeanDefinition 이라는 추상화
가 있다.

쉽게 이야기해서 역할과 구현을 개념적으로 나눈 것이다!
XML을 읽어서 BeanDefinition을 만들면 된다.
자바 코드를 읽어서 BeanDefinition을 만들면 된다.
스프링 컨테이너는 자바 코드인지, XML인지 몰라도 된다. 오직 BeanDefinition만 알면 된다.

BeanDefinition 을 빈 설정 메타정보라 한다.
@Bean ,  당 각각 하나씩 메타 정보가 생성된다.
스프링 컨테이너는 이 메타정보를 기반으로 스프링 빈을 생성한다.



# 싱글톤 패턴

싱글톤 패턴
클래스의 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴이다.
그래서 객체 인스턴스를 2개 이상 생성하지 못하도록 막아야 한다.
private 생성자를 사용해서 외부에서 임의로 new 키워드를 사용하지 못하도록 막아야 한다



스프링 없이 java 코드만으로 싱글톤 패턴을 구현할때의 문제점

- 싱글톤 패턴 문제점
- 싱글톤 패턴을 구현하는 코드 자체가 많이 들어간다.
- 의존관계상 클라이언트가 구체 클래스에 의존한다. DIP를 위반한다.
- 클라이언트가 구체 클래스에 의존해서 OCP 원칙을 위반할 가능성이 높다.
- 테스트하기 어렵다.
- 내부 속성을 변경하거나 초기화 하기 어렵다.
- private 생성자로 자식 클래스를 만들기 어렵다.
- 결론적으로 유연성이 떨어진다.
- 안티패턴으로 불리기도 한다.



싱글톤 방식의 주의점

싱글톤 패턴이든, 스프링 같은 싱글톤 컨테이너를 사용하든, 객체 인스턴스를 하나만 생성해서 공유하는 싱
글톤 방식은 여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문에 싱글톤 객체는 상태를 유지
(stateful)하게 설계하면 안된다.

- 무상태(stateless)로 설계해야 한다!


- 특정 클라이언트에 의존적인 필드가 있으면 안된다.
- 특정 클라이언트가 값을 변경할 수 있는 필드가 있으면 안된다!
- 가급적 읽기만 가능해야 한다.
- 필드 대신에 자바에서 공유되지 않는, 지역변수, 파라미터, ThreadLocal 등을 사용해야 한다.
- 스프링 빈의 필드에 공유 값을 설정하면 정말 큰 장애가 발생할 수 있다!!!





빈의 기본 타입?은 싱글톤 타입



@Configuration 을 적용하면

빈을 여러번 호출하더라도 이미 생성해놓은 객체를 가져다가 씀 (CGLIB 내부기술)



즉 

@Bean이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환하고, 스프링 빈이 없으면 생성
해서 스프링 빈으로 등록하고 반환하는 코드가 동적으로 만들어진다.

덕분에 싱글톤이 보장되는 것이다



@Configuration 을 적용하지않으면 싱글톤이 보장 되지 않음!!!!



@Bean 어노테이션만 사용해도 스프링 빈으로 등록이 되지만 싱글톤을 보장하지 않음.



수동적인 스프링 설정정보를 해야할떄는 항상 @Configuration 어노테이션을 사용하자..



# 컴포넌트 스캔



스프링 설정정보를 수으로 하게되면 일일이 등록해줘야 해서 귀찮고, 프로젝트 규모가 커지면 커질수록 , 누락하는 실수가 생기거나 , 관리가 어려워짐 ..



그래서 스프링에서는 설정 정보가 없어도 자동으로 스프링 빈을 등록하는 컴포넌트 스캔이라는 기능을 제공한다.



또 의존관계를 자동으로 주입해주는 @Autowired라는 기능도 제공해줌

컴포넌트 스캔을 사용하려면 먼저 @ComponentScan (@Service, @Repository 같은 어노테이션에도 컴포넌트 스캔 어노테이션이 들어가있음)



@ComponentScan(
 excludeFilters = @Filter(type = FilterType.ANNOTATION, classes =Configuration.class))



컴포넌트 스캔은 이름 그대로 @Component 애노테이션이 붙은 클래스를 스캔해서

 스프링 빈으로 등록한다.



@ComponentScan 은 @Component 가 붙은 모든 클래스를 스프링 빈으로 등록한다.
이때 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자를 사용한다.
빈 이름 기본 전략: MemberServiceImpl 클래스 memberServiceImpl
빈 이름 직접 지정: 만약 스프링 빈의 이름을 직접 지정하고 싶으면

@Component("memberService2") 이런식으로 이름을 부여하면 된다



basePackages : 탐색할 패키지의 시작 위치를 지정한다. 이 패키지를 포함해서 하위 패키지를 모두 탐색한
다.

basePackages = {"hello.core", "hello.service"} 이렇게 여러 시작 위치를 지정할 수도
있다.

basePackageClasses : 지정한 클래스의 패키지를 탐색 시작 위치로 지정한다.

만약 지정하지 않으면 @ComponentScan 이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다

참고로 스프링 부트를 사용하면 스프링 부트의 대표 시작 정보인 @SpringBootApplication 를 이 프로젝
트 시작 루트 위치에 두는 것이 관례이다. (그리고 이 설정안에 바로 @ComponentScan 이 들어있다!)



컴포넌트 스캔 기본 대상

컴포넌트 스캔은 @Component 뿐만 아니라 다음과 내용도 추가로 대상에 포함한다.

@Component : 컴포넌트 스캔에서 사용
@Controlller : 스프링 MVC 컨트롤러에서 사용
@Service : 스프링 비즈니스 로직에서 사용
@Repository : 스프링 데이터 접근 계층에서 사용
@Configuration : 스프링 설정 정보에서 사용



# 다양한 의존관계 주입 방법




의존관계 주입은 크게 4가지 방법이 있다.
생성자 주입
수정자 주입(setter 주입)
필드 주입
일반 메서드 주입



## 롬복을 이용한 의존관계 주입



생성자가 1개 있으면 Autowired 를 생략할수 있음

@RequiredArgsCounstructor



롬복 라이브러리가 제공하는 @RequiredArgsConstructor 기능을 사용하면 final이 붙은 필드를 모아서
생성자를 자동으로 만들어준다. 

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
 private final MemberRepository memberRepository;
 private final DiscountPolicy discountPolicy;
}



@Autowired 어노테이션은 타입 기반으로 동작하는데



구현체가 2개 이상일때 문제가 발생함.



조회 대상 빈이 2개 이상일 때 해결 방법
@Autowired 필드 명 매칭 ( @Autowried private DiscountPolicy rateDiscountPolicy)
@Qualifier @Qualifier끼리 매칭 빈 이름 매칭 (추가 구분자 지정)
@Primary 사용

ㄴ @Primary 는 우선순위를 정하는 방법이다. @Autowired 시에 여러 빈이 매칭되면 @Primary 가 우선권을
가진다



@Component
@Primary
public class RateDiscountPolicy implements DiscountPolicy {}

@Component
public class FixDiscountPolicy implements DiscountPolicy {}



# 빈 생명주기 콜백 

스프링 빈은 간단하게 다음과 같은 라이프사이클을 가진다.
객체 생성 의존관계 주입



스프링 빈의 이벤트 라이프사이클
스프링 컨테이너 생성 스프링 빈 생성 의존관계 주입 초기화 콜백 사용 소멸전 콜백 스프링
종료



초기화 콜백: 빈이 생성되고, 빈의 의존관계 주입이 완료된 후 호출
소멸전 콜백: 빈이 소멸되기 직전에 호출

스프링은 크게 3가지 방법으로 빈 생명주기 콜백을 지원한다.
인터페이스(InitializingBean, DisposableBean)
설정 정보에 초기화 메서드, 종료 메서드 지정
@PostConstruct, @PreDestory 애노테이션 지원



# 빈 스코프

스프링은 다음과 같은 다양한 스코프를 지원한다.
싱글톤: 기본 스코프, 스프링 컨테이너의 시작과 종료까지 유지되는 가장 넓은 범위의 스코프이다.

프로토타입: 스프링 컨테이너는 프로토타입 빈의 생성과 의존관계 주입까지만 관여하고 더는 관리하지 않는
매우 짧은 범위의 스코프이다.
웹 관련 스코프



request: 웹 요청이 들어오고 나갈때 까지 유지되는 스코프이다.
session: 웹 세션이 생성되고 종료될 때 까지 유지되는 스코프이다.
application: 웹의 서블릿 컨텍스와 같은 범위로 유지되는 스코프이다



request 는 HTTP 요청이 들어와야만 생성되므로 요청이 들어오기전에 호출시 오류가 발생한다.



해당 문제의 경우 Provider나 프록시를 통해 해결할수 있다.

ㄴ 객체 조회를 필요한 시점까지 지연처리 하는 기능



request scope 빈의 생성을 지연할 수 있다



@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)



스프링이 제공하는 편리하는 기능만큼 주의해야할점도 많다.

잘 사용하면 좋지만 제대로 사용하지 못한다면 오히려 독이 된다.

