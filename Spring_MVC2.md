## 타임리프 - 기본기능

**서버 사이드 HTML 렌더링 (SSR)**
타임리프는 백엔드 서버에서 HTML을 동적으로 렌더링 하는 용도로 사용된다.

**네츄럴 템플릿**
타임리프는 순수 HTML을 최대한 유지하는 특징이 있다.
타임리프로 작성한 파일은 HTML을 유지하기 때문에 웹 브라우저에서 파일을 직접 열어도 내용을 확인할
수 있고, 서버를 통해 뷰 템플릿을 거치면 동적으로 변경된 결과를 확인할 수 있다. 
JSP를 포함한 다른 뷰 템플릿들은 해당 파일을 열면, 예를 들어서 JSP 파일 자체를 그대로 웹 브라우저에서
열어보면 JSP 소스코드와 HTML이 뒤죽박죽 섞여서 웹 브라우저에서 정상적인 HTML 결과를 확인할 수
없다. 오직 서버를 통해서 JSP가 렌더링 되고 HTML 응답 결과를 받아야 화면을 확인할 수 있다.
반면에 타임리프로 작성된 파일은 해당 파일을 그대로 웹 브라우저에서 열어도 정상적인 HTML 결과를
확인할 수 있다. 물론 이 경우 동적으로 결과가 렌더링 되지는 않는다. 하지만 HTML 마크업 결과가 어떻게
되는지 파일만 열어도 바로 확인할 수 있다.
이렇게 순수 HTML을 그대로 유지하면서 뷰 템플릿도 사용할 수 있는 타임리프의 특징을 네츄럴 템플릿
(natural templates)이라 한다.

**스프링 통합 지원**
타임리프는 스프링과 자연스럽게 통합되고, 스프링의 다양한 기능을 편리하게 사용할 수 있게 지원한다. 이
부분은 스프링 통합과 폼 장에서 자세히 알아보겠다.

**타임리프 사용 선언**

<html xmlns:th="http://www.thymeleaf.org">



## 텍스트 - text, utext

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>컨텐츠에 데이터 출력하기</h1>
<ul>
    <li>th:text 사용 <span th:text="${data}"></span></li>
    <li>컨텐츠 안에서 직접 출력하기 = [[${data}]]</li> 
</ul>
</body>
</html>
```

@Controller
@RequestMapping("/basic")
public class BasicController {
 @GetMapping("/text-basic")
 public String textBasic(Model model) {
 model.addAttribute("data", "Hello Spring!");
 return "basic/text-basic";
 }
}



**Escape**

HTML 문서는 < , > 같은 특수 문자를 기반으로 정의된다. 따라서 뷰 템플릿으로 HTML 화면을 생성할
때는 출력하는 데이터에 이러한 특수 문자가 있는 것을 주의해서 사용해야 한다.
앞에서 만든 예제의 데이터를 다음과 같이 변경해서 실행해보자.

변경 전
"Hello Spring!"

변경 후
"Hello **Spring!**"

** 테그를 사용해서 Spring!이라는 단어가 진하게 나오도록 해보자.
웹 브라우저에서 실행결과를 보자.
웹 브라우저: Hello Spring!
소스보기: Hello <b>Spring!</b>
개발자가 의도한 것은  가 있으면 해당 부분을 강조하는 것이 목적이었다. 그런데  테그가 그대로
나온다.
소스보기를 하면 < 부분이 < 로 변경된 것을 확인할 수 있다

**







HTML 엔티티
웹 브라우저는 < 를 HTML 테그의 시작으로 인식한다. 따라서 < 를 테그의 시작이 아니라 문자로 표현할 수
있는 방법이 필요한데, 이것을 HTML 엔티티라 한다. 그리고 이렇게 HTML에서 사용하는 특수 문자를
HTML 엔티티로 변경하는 것을 이스케이프(escape)라 한다. 그리고 타임리프가 제공하는 th:text ,
[[...]] 는 기본적으로 이스케이스(escape)를 제공한다.



> > 기타 수 많은 HTML 엔티티가 있다.
> > 참고
> > HTML 엔티티와 관련해서 더 자세한 내용은 HTML 엔티티로 검색해보자

Unescape
이스케이프 기능을 사용하지 않으려면 어떻게 해야할까?
타임리프는 다음 두 기능을 제공한다.

th:text th:utext
[[...]]/////[(...)]





> 주의!
> 실제 서비스를 개발하다 보면 escape를 사용하지 않아서 HTML이 정상 렌더링 되지 않는 수 많은 문제가
> 발생한다. escape를 기본으로 하고, 꼭 필요한 때만 unescape를 사용하자.

# 변수 Spring EL

변수 - SpringEL
타임리프에서 변수를 사용할 때는 변수 표현식을 사용한다.

변수 표현식 : ${...}
그리고 이 변수 표현식에는 스프링 EL이라는 스프링이 제공하는 표현식을 사용할 수 있다



@GetMapping("/variable")
public String variable(Model model) {
 User userA = new User("userA", 10);
 User userB = new User("userB", 20);
 List list = new ArrayList<>();
 list.add(userA);
 list.add(userB);
 Map map = new HashMap<>();
 map.put("userA", userA);
 map.put("userB", userB);
 model.addAttribute("user", userA);
 model.addAttribute("users", list);
 model.addAttribute("userMap", map);
 return "basic/variable";
}

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>SpringEL 표현식</h1>
<ul>Object
    <li>${user.username} = <span th:text="${user.username}"></span></li>
    <li>${user['username']} = <span th:text="${user['username']}"></span></li>
    <li>${user.getUsername()} = <span th:text="${user.getUsername()}"></span></li>
</ul>
<ul>List
    <li>${users[0].username} = <span th:text="${users[0].username}"></span></li>
    <li>${users[0]['username']} = <span th:text="${users[0]['username']}"></span></li>
    <li>${users[0].getUsername()} = <span th:text="${users[0].getUsername()}"></span></li>
</ul>
<ul>Map
    <li>${userMap['userA'].username} = <span th:text="${userMap['userA'].username}"></span></li>
    <li>${userMap['userA']['username']} = <span th:text="${userMap['userA']['username']}"></span></li>
    <li>${userMap['userA'].getUsername()} = <span th:text="${userMap['userA'].getUsername()}"></span></li>
</ul>
</body>
</html>
```

SpringEL 다양한 표현식 사용

Object
user.username : user의 username을 프로퍼티 접근 user.getUsername()
user['username'] : 위와 같음 user.getUsername()
user.getUsername() : user의 getUsername() 을 직접 호출

List

users[0].username : List에서 첫 번째 회원을 찾고 username 프로퍼티 접근
list.get(0).getUsername()
users[0]['username'] : 위와 같음
users[0].getUsername() : List에서 첫 번째 회원을 찾고 메서드 직접 호출

Map

userMap['userA'].username : Map에서 userA를 찾고, username 프로퍼티 접근



지역 변수 선언
th:with 를 사용하면 지역 변수를 선언해서 사용할 수 있다. 지역 변수는 선언한 테그 안에서만 사용할 수
있다



```
<h1>지역 변수 - (th:with)</h1>
<div th:with="first=${users[0]}">
    <p>처음 사람의 이름은 <span th:text="${first.username}"></span></p>
</div>
```



## 기본 객체들



기본 객체들
타임리프는 기본 객체들을 제공한다.
${#request}
${#response}
${#session}
${#servletContext}
${#locale}

그런데 #request 는 HttpServletRequest 객체가 그대로 제공되기 때문에 데이터를 조회하려면
request.getParameter("data") 처럼 불편하게 접근해야 한다.
이런 점을 해결하기 위해 편의 객체도 제공한다.

//http://localhost:8080/basic/basic-objects?paramData=HelloParam

HTTP 요청 파라미터 접근: param
예) ${param.paramData}

HTTP 세션 접근: session
예) ${session.sessionData}

스프링 빈 접근: @
예) ${@helloBean.hello('Spring!')}



```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>식 기본 객체 (Expression Basic Objects)</h1>
<ul>
    <li>request = <span th:text="${#request}"></span></li>
    <li>response = <span th:text="${#response}"></span></li>
    <li>session = <span th:text="${#session}"></span></li>
    <li>servletContext = <span th:text="${#servletContext}"></span></li>
    <li>locale = <span th:text="${#locale}"></span></li>
</ul>
<h1>편의 객체</h1>
<ul>
    <li>Request Parameter = <span th:text="${param.paramData}"></span></li>
    <li>session = <span th:text="${session.sessionData}"></span></li>
    <li>spring bean = <span th:text="${@helloBean.hello('Spring!')}"></span></
    li>
</ul>
</body>
</html>
```



## 유틸리티 객체와 날짜



타임리프는 문자, 숫자, 날짜, URI등을 편리하게 다루는 다양한 유틸리티 객체들을 제공한다.
타임리프 유틸리티 객체들

message : 메시지, 국제화 처리

uris : URI 이스케이프 지원

dates : java.util.Date 서식 지원

calendars : java.util.Calendar 서식 지원

temporals : 자바8 날짜 서식 지원

numbers : 숫자 서식 지원

strings : 문자 관련 편의 기능

objects : 객체 관련 기능 제공

bools : boolean 관련 기능 제공

arrays : 배열 관련 기능 제공

lists , #sets , #maps : 컬렉션 관련 기능 제공

ids : 아이디 처리 관련 기능 제공, 뒤에서 설명





자바8 날짜
타임리프에서 자바8 날짜인 LocalDate , LocalDateTime , Instant 를 사용하려면 추가 라이브러리가
필요하다. 스프링 부트 타임리프를 사용하면 해당 라이브러리가 자동으로 추가되고 통합된다.
타임리프 자바8 날짜 지원 라이브러리
thymeleaf-extras-java8time
자바8 날짜용 유틸리티 객체

temporals



```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>LocalDateTime</h1>
<ul>
    <li>default = <span th:text="${localDateTime}"></span></li>
    <li>yyyy-MM-dd HH:mm:ss = <span th:text="${#temporals.format(localDateTime,
'yyyy-MM-dd HH:mm:ss')}"></span></li>
</ul>
<h1>LocalDateTime - Utils</h1>
<ul>
    <li>${#temporals.day(localDateTime)} = <span th:text="${#temporals.day(localDateTime)}"></span></li>
    <li>${#temporals.month(localDateTime)} = <span th:text="${#temporals.month(localDateTime)}"></span></li>
    <li>${#temporals.monthName(localDateTime)} = <span th:text="${#temporals.monthName(localDateTime)}"></span></li>
    <li>${#temporals.monthNameShort(localDateTime)} = <span th:text="${#temporals.monthNameShort(localDateTime)}"></span></li>
    <li>${#temporals.year(localDateTime)} = <span th:text="${#temporals.year(localDateTime)}"></span></li>
    <li>${#temporals.dayOfWeek(localDateTime)} = <span th:text="$
{#temporals.dayOfWeek(localDateTime)}"></span></li>
    <li>${#temporals.dayOfWeekName(localDateTime)} = <span th:text="$
{#temporals.dayOfWeekName(localDateTime)}"></span></li>
    <li>${#temporals.dayOfWeekNameShort(localDateTime)} = <span th:text="${#temporals.dayOfWeekNameShort(localDateTime)}"></span></li>
    <li>${#temporals.hour(localDateTime)} = <span th:text="${#temporals.hour(localDateTime)}"></span></li>
    <li>${#temporals.minute(localDateTime)} = <span th:text="${#temporals.minute(localDateTime)}"></span></li>
    <li>${#temporals.second(localDateTime)} = <span th:text="${#temporals.second(localDateTime)}"></span></li>
    <li>${#temporals.nanosecond(localDateTime)} = <span th:text="${#temporals.nanosecond(localDateTime)}"></span></li>
</ul>
</body>
</html>
```





## URL 링크



```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>URL 링크</h1>
<ul>
    <li><a th:href="@{/hello}">basic url</a></li>
    <li><a th:href="@{/hello(param1=${param1}, param2=${param2})}">hello query
        param</a></li>
    <li><a th:href="@{/hello/{param1}/{param2}(param1=${param1}, param2=$
{param2})}">path variable</a></li>
    <li><a th:href="@{/hello/{param1}(param1=${param1}, param2=$
{param2})}">path variable + query parameter</a></li>
</ul>
</body>
</html>
```

**단순한 URL**
@{/hello} /hello

**쿼리 파라미터**
@{/hello(param1=${param1}, param2=${param2})}
/hello?param1=data1&param2=data2

() 에 있는 부분은 쿼리 파라미터로 처리된다.

**경로 변수**
@{/hello/{param1}/{param2}(param1=${param1}, param2=${param2})}
/hello/data1/data2
URL 경로상에 변수가 있으면 () 부분은 경로 변수로 처리된다.

**경로 변수 + 쿼리 파라미터**
@{/hello/{param1}(param1=${param1}, param2=${param2})}
/hello/data1?param2=data2

경로 변수와 쿼리 파라미터를 함께 사용할 수 있다.





상대경로, 절대경로, 프로토콜 기준을 표현할 수 도 있다.
/hello : 절대 경로
hello : 상대 경로



## 리터럴



**Literals**

리터럴은 소스 코드상에 고정된 값을 말하는 용어이다.
예를 들어서 다음 코드에서 "Hello" 는 문자 리터럴, 10 , 20 는 숫자 리터럴이다.

> 참고
> 이 내용이 쉬워 보이지만 처음 타임리프를 사용하면 많이 실수하니 잘 보아두자.
> 타임리프는 다음과 같은 리터럴이 있다.
> 문자: 'hello'
> 숫자: 10
> 불린: true , false
> null: null
> 타임리프에서 문자 리터럴은 항상 ' (작은 따옴표)로 감싸야 한다.
>
> 

그런데 문자를 항상 ' 로 감싸는 것은 너무 귀찮은 일이다. 공백 없이 쭉 이어진다면 하나의 의미있는
토큰으로 인지해서 다음과 같이 작은 따옴표를 생략할 수 있다. 
룰: A-Z , a-z , 0-9 , [] , . , - , _

<span th:text="hello">



오류

<span th:text="hello world!"></span

문자 리터럴은 원칙상 ' 로 감싸야 한다. 중간에 공백이 있어서 하나의 의미있는 토큰으로도 인식되지
않는다.
수정

이렇게 ' 로 감싸면 정상 동작한다



```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>리터럴</h1>
<ul>
    <!--주의! 다음 주석을 풀면 예외가 발생함-->
    <!-- <li>"hello world!" = <span th:text="hello world!"></span></li>-->
    <li>'hello' + ' world!' = <span th:text="'hello' + ' world!'"></span></li>
    <li>'hello world!' = <span th:text="'hello world!'"></span></li>
    <li>'hello ' + ${data} = <span th:text="'hello ' + ${data}"></span></li>
    <li>리터럴 대체 |hello ${data}| = <span th:text="|hello ${data}|"></span></li>
</ul>
</body>
</html>
```



## 연산

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<ul>
    <li>산술 연산
        <ul>
            <li>10 + 2 = <span th:text="10 + 2"></span></li>
            <li>10 % 2 == 0 = <span th:text="10 % 2 == 0"></span></li>
        </ul>
    </li>
    <li>비교 연산
        <ul>
            <li>1 > 10 = <span th:text="1 &gt; 10"></span></li>
            <li>1 gt 10 = <span th:text="1 gt 10"></span></li>
            <li>1 >= 10 = <span th:text="1 >= 10"></span></li>
            <li>1 ge 10 = <span th:text="1 ge 10"></span></li>
            <li>1 == 10 = <span th:text="1 == 10"></span></li>
            <li>1 != 10 = <span th:text="1 != 10"></span></li>
        </ul>
    </li>
    <li>조건식
        <ul>
            <li>(10 % 2 == 0)? '짝수':'홀수' = <span th:text="(10 % 2 == 0)?
'짝수':'홀수'"></span></li>
        </ul>
    </li>
    <li>Elvis 연산자
        <ul>
            <li>${data}?: '데이터가 없습니다.' = <span th:text="${data}?: '데이터가
없습니다.'"></span></li>
            <li>${nullData}?: '데이터가 없습니다.' = <span th:text="${nullData}?:
'데이터가 없습니다.'"></span></li>
        </ul>
    </li>
    <li>No-Operation
        <ul>
            <li>${data}?: _ = <span th:text="${data}?: _">데이터가 없습니다.</
                span></li>
            <li>${nullData}?: _ = <span th:text="${nullData}?: _">데이터가
없습니다.</span></li>
        </ul>
    </li>
</ul>
</body>
</html>
```





비교연산: HTML 엔티티를 사용해야 하는 부분을 주의하자, 

> .> (gt), < (lt), >= (ge), <= (le), ! (not), == (eq), != (neq, ne)
>
> 조건식: 자바의 조건식과 유사하다.
>
> Elvis 연산자: 조건식의 편의 버전
>
> No-Operation: _ 인 경우 마치 타임리프가 실행되지 않는 것 처럼 동작한다. 이것을 잘 사용하면 HTML
> 의 내용 그대로 활용할 수 있다. 마지막 예를 보면 데이터가 없습니다. 부분이 그대로 출력된다