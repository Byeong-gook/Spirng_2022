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



## 타임리프 스프링 통합



스프링 통합으로 추가되는 기능들

**스프링의 SpringEL 문법 통합**

${@myBean.doSomething()} 처럼 스프링 빈 호출 지원
편리한 폼 관리를 위한 추가 속성 
th:object (기능 강화, 폼 커맨드 객체 선택)
th:field , th:errors , th:errorclass
폼 컴포넌트 기능
checkbox, radio button, List 등을 편리하게 사용할 수 있는 기능 지원
스프링의 메시지, 국제화 기능의 편리한 통합
스프링의 검증, 오류 처리 통합
스프링의 변환 서비스 통합(ConversionService



## 입력 폼 처리


th:object : 커맨드 객체를 지정한다.
*{...} : 선택 변수 식이라고 한다. th:object 에서 선택한 객체에 접근한다.

th:field
HTML 태그의 id , name , value 속성을 자동으로 처리해준다.
렌더링 전
<input type="text" th:field="*{itemName}" />
렌더링 후
<input type="text" id="itemName" name="itemName" th:value="*{itemName}" />





```
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <link th:href="@{/css/bootstrap.min.css}"
          href="../css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container {
            max-width: 560px;
        }
    </style>
</head>
<body>

<div class="container">

    <div class="py-5 text-center">
        <h2>상품 등록 폼</h2>
    </div>

    <form action="item.html" th:action th:object="${item}" method="post"> // from에 객체를 연결
        <div>
            <label for="itemName">상품명</label>
            <input type="text" id="itemName" th:field="*{itemName}" class="form-control"  placeholder="이름을 입력하세요"> // id , name 생략 가능
        </div>
        <div>
            <label for="price">가격</label>
            <input type="text" id="price"  th:field="*{price}" class="form-control" placeholder="가격을 입력하세요">
        </div>
        <div>
            <label for="quantity">수량</label>
            <input type="text" id="quantity" th:field="*{quantity}" class="form-control" placeholder="수량을 입력하세요">
        </div>

        <hr class="my-4">

        <div class="row">
            <div class="col">
                <button class="w-100 btn btn-primary btn-lg" type="submit">상품 등록</button>
            </div>
            <div class="col">
                <button class="w-100 btn btn-secondary btn-lg"
                        onclick="location.href='items.html'"
                        th:onclick="|location.href='@{/form/items}'|"
                        type="button">취소</button>
            </div>
        </div>

    </form>

</div> <!-- /container -->
</body>
</html>
```

th:object="${item}" : <form> 에서 사용할 객체를 지정한다. 선택 변수 식( *{...} )을 적용할 수
있다.
th:field="*{itemName}"

***{itemName}** 는 **선택 변수 식**을 사용했는데, **${item.itemName}** 과 같다. 앞서 th:object 로

item 을 선택했기 때문에 선택 변수 식을 적용할 수 있다.

**th:field 는 id , name , value** 속성을 모두 자동으로 만들어준다.

id : th:field 에서 지정한 변수 이름과 같다. id="itemName"
name : th:field 에서 지정한 변수 이름과 같다. name="itemName"
value : th:field 에서 지정한 변수의 값을 사용한다. value=""

참고로 해당 예제에서 id 속성을 제거해도 th:field 가 자동으로 만들어준다.



## 체크박스 - 단일 1



체크 박스를 체크하면 HTML Form에서 open=on 이라는 값이 넘어간다. 스프링은 on 이라는 문자를
true 타입으로 변환해준다. (스프링 타입 컨버터가 이 기능을 수행하는데, 뒤에서 설명한다.)
주의 - 체크 박스를 선택하지 않을 때
HTML에서 체크 박스를 선택하지 않고 폼을 전송하면 open 이라는 필드 자체가 서버로 전송되지 않는다.
HTTP 요청 메시지 로깅
HTTP 요청 메시지를 서버에서 보고 싶으면 다음 설정을 추가하면 된다.

application.properties
logging.level.org.apache.coyote.http11=debug

HTTP 메시지 바디를 보면 open 의 이름도 전송이 되지 않는 것을 확인할 수 있다.
itemName=itemA&price=10000&quantity=10

서버에서 Boolean 타입을 찍어보면 결과가 null 인 것을 확인할 수 있다.
log.info("item.open={}", item.getOpen());

HTML checkbox는 선택이 안되면 클라이언트에서 서버로 값 자체를 보내지 않는다. 수정의 경우에는
상황에 따라서 이 방식이 문제가 될 수 있다. 사용자가 의도적으로 체크되어 있던 값을 체크를 해제해도
저장시 아무 값도 넘어가지 않기 때문에, 서버 구현에 따라서 값이 오지 않은 것으로 판단해서 값을 변경하지
않을 수도 있다.

이런 문제를 해결하기 위해서 스프링 MVC는 약간의 트릭을 사용하는데, 히든 필드를 하나 만들어서,
_open 처럼 기존 체크 박스 이름 앞에 언더스코어( _ )를 붙여서 전송하면 체크를 해제했다고 인식할 수
있다. 히든 필드는 항상 전송된다. 따라서 체크를 해제한 경우 여기에서 open 은 전송되지 않고, _open 만
전송되는데, 이 경우 스프링 MVC는 체크를 해제했다고 판단한다.

체크 해제를 인식하기 위한 히든 필드

<input type="hidden" name="_open" value="on"/>



체크 박스 체크
open=on&_open=on
체크 박스를 체크하면 스프링 MVC가 open 에 값이 있는 것을 확인하고 사용한다. 이때 _open 은
무시한다.

체크 박스 미체크
_open=on

체크 박스를 체크하지 않으면 스프링 MVC가 _open 만 있는 것을 확인하고, open 의 값이 체크되지
않았다고 인식한다.

이 경우 서버에서 Boolean 타입을 찍어보면 결과가 null 이 아니라 false 인 것을 확인할 수 있다.
log.info("item.open={}", item.getOpen());



하지만 타임리프를 사용하게되면



```
<input type="checkbox" id="open" th:field="${item.open}" class="form-check-input">
```

타임리프를 사용하면 체크 박스의 히든 필드와 관련된 부분도 함께 해결해준다. HTML 생성 결과를 보면
히든 필드 부분이 자동으로 생성되어 있다



타임리프 체크 확인



체크 박스에서 판매 여부를 선택해서 저장하면, 조회시에 checked 속성이 추가된 것을 확인할 수 있다. 
이런 부분을 개발자가 직접 처리하려면 상당히 번거롭다. 타임리프의 th:field 를 사용하면, 값이 true
인 경우 체크를 자동으로 처리해준다.



## 체크박스 - 멀티

```
@ModelAttribute("regions")
public Map<String, String> regions(){
    Map<String, String> regions = new LinkedHashMap<>(); // 순서가 보장되는 LinkedHashMap
    regions.put("SEOUL", "서울");
    regions.put("BUSAN", "부산");
    regions.put("JEJU", "제주");
    return regions;
}

```

@ModelAttribute의 특별한 사용법
등록 폼, 상세화면, 수정 폼에서 모두 서울, 부산, 제주라는 체크 박스를 반복해서 보여주어야 한다. 이렇게
하려면 각각의 컨트롤러에서 model.addAttribute(...) 을 사용해서 체크 박스를 구성하는 데이터를
반복해서 넣어주어야 한다.
@ModelAttribute 는 이렇게 컨트롤러에 있는 별도의 메서드에 적용할 수 있다.
이렇게하면 해당 컨트롤러를 요청할 때 regions 에서 반환한 값이 자동으로 모델( model )에 담기게 된다.
물론 이렇게 사용하지 않고, 각각의 컨트롤러 메서드에서 모델에 직접 데이터를 담아서 처리해도 된다.



```
<div>
    <div>등록 지역</div>
    <div th:each="region : ${regions}" class="form-check form-check-inline">
        <input type="checkbox" th:field="*{item.regions}" th:value="${region.key}"
               class="form-check-input">
        <label th:for="${#ids.prev('regions')}"
               th:text="${region.value}" class="form-check-label">서울</label>
    </div>
</div>

```

th:for="${#ids.prev('regions')}"
멀티 체크박스는 같은 이름의 여러 체크박스를 만들 수 있다. 그런데 문제는 이렇게 반복해서 HTML 태그를
생성할 때, 생성된 HTML 태그 속성에서 name 은 같아도 되지만, id 는 모두 달라야 한다. 따라서
타임리프는 체크박스를 each 루프 안에서 반복해서 만들 때 임의로 1 , 2 , 3 숫자를 뒤에 붙여준다



each로 체크박스가 반복 생성된 결과 - id 뒤에 숫자가 추가

<input type="checkbox" value="SEOUL" class="form-check-input" id="regions1" 
name="regions">
<input type="checkbox" value="BUSAN" class="form-check-input" id="regions2" 
name="regions">
<input type="checkbox" value="JEJU" class="form-check-input" id="regions3" 
name="regions"> 



HTML의 id 가 타임리프에 의해 동적으로 만들어지기 때문에  으로 label 의
대상이 되는 id 값을 임의로 지정하는 것은 곤란하다. 타임리프는 ids.prev(...) , ids.next(...) 을
제공해서 동적으로 생성되는 id 값을 사용할 수 있도록 한다.



## 라디오 버튼



```
@ModelAttribute("itemTypes")
public ItemType[] itemTypes() {
    ItemType[] values = ItemType.values(); // ENUM의 경우 values() 를 사용하면 배열의 상태로 반환해줌
    return values;
}
```

itemTypes 를 등록 폼, 조회, 수정 폼에서 모두 사용하므로 @ModelAttribute 의 특별한 사용법을
적용하자.
ItemType.values() 를 사용하면 해당 ENUM의 모든 정보를 배열로 반환한다. 예) [BOOK, FOOD, 
ETC]



체크 박스는 수정시 체크를 해제하면 아무 값도 넘어가지 않기 때문에, 별도의 히든 필드로 이런 문제를
해결했다. 라디오 버튼은 이미 선택이 되어 있다면, 수정시에도 항상 하나를 선택하도록 되어 있으므로 체크
박스와 달리 별도의 히든 필드를 사용할 필요가 없다



```
<!-- radio button -->
<div>
    <div>상품 종류</div>
    <div th:each="type : ${itemTypes}" class="form-check form-check-inline">
        <input type="radio" th:field="*{itemType}" th:value="${type.name()}"
               class="form-check-input">
        <label th:for="${#ids.prev('itemType')}" th:text="${type.description}"
               class="form-check-label">
            BOOK
        </label>
    </div>
</div>
```



타임리프에서 ENUM 직접 접근

${T(hello.itemservice.domain.item.ItemType).values()} 스프링EL 문법으로 ENUM을 직접
사용할 수 있다. ENUM에 values() 를 호출하면 해당 ENUM의 모든 정보가 배열로 반환된다.
그런데 이렇게 사용하면 ENUM의 패키지 위치가 변경되거나 할때 자바 컴파일러가 타임리프까지 컴파일
오류를 잡을 수 없으므로 추천하지는 않는다 // 모델에 안담고 ENUM 접근



## 셀렉트 박스

@ModelAttribute("deliveryCodes")
public List deliveryCodes() {
 List deliveryCodes = new ArrayList<>();
 deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
 deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
 deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
 return deliveryCodes;
}



<!-- SELECT -->
<div>
 <div>배송 방식</div>
 <select th:field="*{deliveryCode}" class="form-select">
 <option value="">==배송 방식 선택==</option>
 <option th:each="deliveryCode : ${deliveryCodes}" th:value="$
{deliveryCode.code}"
 th:text="${deliveryCode.displayName}">FAST</option>
 </select>
</div>
<hr class="my-4">



## 메세지, 국제화

**메시지**

악덕? 기획자가 화면에 보이는 문구가 마음에 들지 않는다고, 상품명이라는 단어를 모두 상품이름으로
고쳐달라고 하면 어떻게 해야할까?
여러 화면에 보이는 상품명, 가격, 수량 등, label 에 있는 단어를 변경하려면 다음 화면들을 다 찾아가면서
모두 변경해야 한다. 지금처럼 화면 수가 적으면 문제가 되지 않지만 화면이 수십개 이상이라면 수십개의
파일을 모두 고쳐야 한다.

addForm.html , editForm.html , item.html , items.html

왜냐하면 해당 HTML 파일에 메시지가 하드코딩 되어 있기 때문이다.
이런 다양한 메시지를 한 곳에서 관리하도록 하는 기능을 메시지 기능이라 한다.
예를 들어서 messages.properteis 라는 메시지 관리용 파일을 만들고



item=상품
item.id=상품 ID
item.itemName=상품명
item.price=가격
item.quantity=수량



각 HTML들은 다음과 같이 해당 데이터를 key 값으로 불러서 사용하는 것이다.
addForm.html

editForm.html



**국제화**



메시지에서 한 발 더 나가보자.
메시지에서 설명한 메시지 파일( messages.properteis )을 각 나라별로 별도로 관리하면 서비스를
국제화 할 수 있다.
예를 들어서 다음과 같이 2개의 파일을 만들어서 분류한다.



**essages_en.propertis**
item=Item
item.id=Item ID
item.itemName=Item Name
item.price=price
item.quantity=quantity

**messages_ko.propertis**
item=상품
item.id=상품 ID
item.itemName=상품명
item.price=가격
item.quantity=수량



영어를 사용하는 사람이면 messages_en.propertis 를 사용하고,
한국어를 사용하는 사람이면 messages_ko.propertis 를 사용하게 개발하면 된다.
이렇게 하면 사이트를 국제화 할 수 있다.
한국에서 접근한 것인지 영어에서 접근한 것인지는 인식하는 방법은 HTTP accept-language 해더 값을
사용하거나 사용자가 직접 언어를 선택하도록 하고, 쿠키 등을 사용해서 처리하면 된다.
메시지와 국제화 기능을 직접 구현할 수도 있겠지만, 스프링은 기본적인 메시지와 국제화 기능을 모두
제공한다. 그리고 타임리프도 스프링이 제공하는 메시지와 국제화 기능을 편리하게 통합해서 제공한다.
지금부터 스프링이 제공하는 메시지와 국제화 기능을 알아보자



## 스프링 메시지 소스 설정

스프링은 기본적인 메시지 관리 기능을 제공한다.

메시지 관리 기능을 사용하려면 스프링이 제공하는 MessageSource 를 스프링 빈으로 등록하면 되는데,
MessageSource 는 인터페이스이다. 따라서 구현체인 ResourceBundleMessageSource 를 스프링 빈으로
등록하면 된다.



```
@Bean
public MessageSource messageSource() {
   ResourceBundleMessageSource messageSource = new
         ResourceBundleMessageSource();
   messageSource.setBasenames("messages", "errors");
   messageSource.setDefaultEncoding("utf-8");
   return messageSource;
}
```





basenames : 설정 파일의 이름을 지정한다.
messages 로 지정하면 messages.properties 파일을 읽어서 사용한다.
추가로 국제화 기능을 적용하려면 messages_en.properties , messages_ko.properties 와 같이
파일명 마지막에 언어 정보를 주면된다. 만약 찾을 수 있는 국제화 파일이 없으면
messages.properties (언어정보가 없는 파일명)를 기본으로 사용한다.
파일의 위치는 /resources/messages.properties 에 두면 된다.
여러 파일을 한번에 지정할 수 있다. 여기서는 messages , errors 둘을 지정했다.
defaultEncoding : 인코딩 정보를 지정한다. utf-8 을 사용하면 된다.

**스프링 부트**

스프링 부트를 사용하면 스프링 부트가 MessageSource 를 자동으로 스프링 빈으로 등록한다.

**스프링 부트 메시지 소스 설정**

스프링 부트를 사용하면 다음과 같이 메시지 소스를 설정할 수 있다.

**application.properties**


spring.messages.basename=messages,config.i18n.messages



스프링 부트 메시지 소스 기본 값

spring.messages.basename=messages

MessageSource 를 스프링 빈으로 등록하지 않고, 스프링 부트와 관련된 별도의 설정을 하지 않으면
messages 라는 이름으로 기본 등록된다. 따라서 messages_en.properties ,
messages_ko.properties , messages.properties 파일만 등록하면 자동으로 인식된다



**메시지 파일 만들기**
메시지 파일을 만들어보자. 국제화 테스트를 위해서 messages_en 파일도 추가하자.

messages.properties :기본 값으로 사용(한글)
messages_en.properties : 영어 국제화 사용

주의! 파일명은 massage가 아니라 messages다! 마지막 s에 주의하자
/resources/messages.properties



## 스프링 메시지 소스 사용

```
@Autowired
MessageSource ms;

@Test
void helloMessage() {
    String result = ms.getMessage("hello", null, null);
    Assertions.assertThat(result).isEqualTo("안녕");
    
}
```

ms.getMessage("hello", null, null)
code: hello
args: null
locale: null

가장 단순한 테스트는 메시지 코드로 hello 를 입력하고 나머지 값은 null 을 입력했다.
locale 정보가 없으면 basename 에서 설정한 기본 이름 메시지 파일을 조회한다. basename 으로
messages 를 지정 했으므로 messages.properties 파일에서 데이터 조회한다.



```
@Test
void notFoundMessageCode() {
    assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
            .isInstanceOf(NoSuchMessageException.class);
}
```

메시지가 없는 경우에는 NoSuchMessageException 이 발생한다.
메시지가 없어도 기본 메시지( defaultMessage )를 사용하면 기본 메시지가 반환된다.



```
@Test
void notFoundMessageCodeDefaultMesaage() {
    String result = ms.getMessage("no_code", null, "기본 메세지", null);
    assertThat(result).isEqualTo("기본 메세지");
}
```



다음 메시지의 {0} 부분은 매개변수를 전달해서 치환할 수 있다.
hello.name=안녕 {0} Spring 단어를 매개변수로 전달 안녕 Spring

```
@Test
void argumentMessage() {
    String message =  ms.getMessage("hello.name", new Object[]{"Spring"}, null);
    assertThat(message).isEqualTo("안녕 Spring!");

}
```



국제화 파일 선택
locale 정보를 기반으로 국제화 파일을 선택한다.
Locale이 en_US 의 경우 messages_en_US messages_en messages 순서로 찾는다.
Locale 에 맞추어 구체적인 것이 있으면 구체적인 것을 찾고, 없으면 디폴트를 찾는다고 이해하면 된다

```
@Test
void defaultLang() {
    assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕");
    assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
}
```

ms.getMessage("hello", null, null) : locale 정보가 없으므로 messages 를 사용
ms.getMessage("hello", null, Locale.KOREA) : locale 정보가 있지만, message_ko 가 없으므로
messages 를 사용

<div th:text="#{label.item}"></h2>

```
@Test
void enLang() {
    assertThat(ms.getMessage("hello", null,
            Locale.ENGLISH)).isEqualTo("hello");
}
```

ms.getMessage("hello", null, Locale.ENGLISH) : locale 정보가 Locale.ENGLISH 이므로
messages_en 을 찾아서 사용





## 웹어플리케이션에 메세지 적용하기



**타임리프 메시지 적용**

타임리프의 메시지 표현식 #{...} 를 사용하면 스프링의 메시지를 편리하게 조회할 수 있다.
예를 들어서 방금 등록한 상품이라는 이름을 조회하려면 #{label.item} 이라고 하면 된다



렌더링 전

<div th:text="#{label.item}"></h2>

렌더링 후

<div>상품</h2>





참고로 파라미터가 있는 메세지는 다음과 같이 사용할 수 있다.


hello.name=안녕 {0}
<p th:text="#{hello.name(${item.itemName})}"></p>



## 웹 어플리케이션에 국제화 적용하기

**웹으로 확인하기**
웹 브라우저의 언어 설정 값을 변경하면서 국제화 적용을 확인해보자.
크롬 브라우저 설정 언어를 검색하고, 우선 순위를 변경하면 된다.
우선순위를 영어로 변경하고 테스트해보자.
웹 브라우저의 언어 설정 값을 변경하면 요청시 Accept-Language 의 값이 변경된다.
Accept-Language 는 클라이언트가 서버에 기대하는 언어 정보를 담아서 요청하는 HTTP 요청 헤더이다.
(더 자세한 내용은 모든 개발자를 위한 HTTP 웹 기본지식 강의를 참고하자.)

**스프링의 국제화 메시지 선택**
앞서 MessageSource 테스트에서 보았듯이 메시지 기능은 Locale 정보를 알아야 언어를 선택할 수 있다.
결국 스프링도 Locale 정보를 알아야 언어를 선택할 수 있는데, 스프링은 언어 선택시 기본으로 AcceptLanguage 헤더의 값을 사용한다.

**LocaleResolver**
스프링은 **Locale 선택 방식을 변경**할 수 있도록 **LocaleResolver 라는 인터페이스를 제공**하는데, 
스프링 부트는 기본으로 Accept-Language 를 활용하는 **AcceptHeaderLocaleResolve**r 를 사용한다.



**LocaleResolver 인터페이스**

public interface LocaleResolver {
Locale resolveLocale(HttpServletRequest request);
void setLocale(HttpServletRequest request, @Nullable HttpServletResponse 
response, @Nullable Locale locale);
}



**LocaleResolver 변경**
만약 Locale 선택 방식을 변경하려면 LocaleResolver 의 구현체를 변경해서 쿠키나 세션 기반의
Locale 선택 기능을 사용할 수 있다. 예를 들어서 고객이 직접 Locale 을 선택하도록 하는 것이다. 
관련해서 LocaleResolver 를 검색하면 수 많은 예제가 나오니 필요한 분들은 참고하자.



## 검증 1 - Validation



**검증 요구사항**

상품 관리 시스템에 새로운 요구사항이 추가되었다.
요구사항: 검증 로직 추가

**타입 검증**

가격, 수량에 문자가 들어가면 검증 오류 처리

**필드 검증**


상품명: 필수, 공백X

가격: 1000원 이상, 1백만원 이하
수량: 최대 9999

**특정 필드의 범위를 넘어서는 검증**


가격 * 수량의 합은 10,000원 이상



지금까지 만든 웹 애플리케이션은 폼 입력시 숫자를 문자로 작성하거나해서 **검증 오류가 발생하면 오류**
**화면**으로 바로 이동한다. 이렇게 되면 사용자는 처음부터 해당 폼으로 다시 이동해서 입력을 해야 한다. 
아마도 이런 서비스라면 사용자는 금방 떠나버릴 것이다. **웹 서비스는 폼 입력시 오류가 발생하면, 고객이**
**입력한 데이터를 유지한 상태로 어떤 오류가 발생했는지 친절하게 알려주어야 한다.**

컨트롤러의 중요한 역할중 하나는 HTTP 요청이 정상인지 검증하는 것이다. 그리고 정상 로직보다 이런
검증 로직을 잘 개발하는 것이 어쩌면 더 어려울 수 있다.

**참고: 클라이언트 검증, 서버 검증**

클라이언트 검증(ex) 자바스크립트 검증)은 조작할 수 있으므로 **보안에 취약**하다.
서버만으로 검증하면, 즉각적인 **고****객 사용성이 부족**해진다.
둘을 적절히 섞어서 사용하되, 최종적으로 서버 검증은 필수
**API 방식을 사용**하면 **API 스펙을 잘 정의**해서 **검증 오류를 API 응답 결과에 잘 남겨주어야 함**
먼저 검증을 직접 구현해보고, 뒤에서 스프링과 타임리프가 제공하는 검증 기능을 활용해보자



## 검증 직접처리 - 소개



![fail](C:\Users\User\Desktop\Spring_2022\fail.JPG)

## 검증 직접처리 - 개발

```
@PostMapping("/add")
public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes, Model model) {

    //검증 오류 결과를 보관
    Map<String, String> errors = new HashMap<>();

    //검증 로직
    if(!StringUtils.hasText(item.getItemName())){
        errors.put("itemName", "상품 이름은 필수입니다.");
    }
    if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
        errors.put("price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.");
    }
    if (item.getQuantity() == null || item.getQuantity() >= 9999) {
        errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
    }

    // 특정 필드가 아닌 복합 룰 검증
    if (item.getPrice() != null && item.getQuantity() != null){
        int resultPrice = item.getPrice() * item.getQuantity();
        if(resultPrice < 10000) {
            errors.put("globalError", "가격 * 수량의 합은 10,000 원 이상이어야 합니다. 현재 값 = " + resultPrice);
        }
    }

    // 검증에 실패하면 다시 입력 폼으로
    if(!errors.isEmpty()) {
     model.addAttribute("errors", errors);
     return "validation/v1/addForm";
    }
    
    //성공로직
    Item savedItem = itemRepository.save(item);
    redirectAttributes.addAttribute("itemId", savedItem.getId());
    redirectAttributes.addAttribute("status", true);
    return "redirect:/validation/v1/items/{itemId}";
}
```

**검증 오류 보관**
Map errors = new HashMap<>();
만약 검증시 오류가 발생하면 어떤 검증에서 오류가 발생했는지 정보를 담아둔다.

**검증 로직**
if (!StringUtils.hasText(item.getItemName())) {
 errors.put("itemName", "상품 이름은 필수입니다.");
}

**import org.springframework.util.StringUtils; 추가 필요**

검증시 오류가 발생하면 errors 에 담아둔다. 이때 어떤 필드에서 오류가 발생했는지 구분하기 위해
오류가 발생한 필드명을 key 로 사용한다. 이후 뷰에서 이 데이터를 사용해서 고객에게 친절한 오류
메시지를 출력할 수 있다.



if (!errors.isEmpty()) {
 model.addAttribute("errors", errors);
 return "validation/v1/addForm";
}

만약 검증에서 오류 메시지가 하나라도 있으면 오류 메시지를 출력하기 위해 model errors 를 담고, 입력
폼이 있는 뷰 템플릿으로 보낸다



**Map을 활용한 오류 검증 표시 로직**

```
<div th:if="${errors?.containsKey('globalError')}">
    <p class="field-error" th:text="${errors['globalError']}">전체 오류 메세지</p>
</div>

```



![validation view display](C:\Users\User\Desktop\Spring_2022\validation view display.JPG)<div th:if="${errors?.containsKey('globalError')}"> // ?는 errors가 null 이면 무시하는 로직



```
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <link th:href="@{/css/bootstrap.min.css}"
          href="../css/bootstrap.min.css" rel="stylesheet">
    <style>
 .container {
 max-width: 560px;
 }
 .field-error {
 border-color: #dc3545;
 color: #dc3545;
 }
 </style>
</head>
<body>
<div class="container">
    <div class="py-5 text-center">
        <h2 th:text="#{page.addItem}">상품 등록</h2>
    </div>
    <form action="item.html" th:action th:object="${item}" method="post">
        <div th:if="${errors?.containsKey('globalError')}">
            <p class="field-error" th:text="${errors['globalError']}">전체 오류
                메시지</p>
        </div>
        <div>
            <label for="itemName" th:text="#{label.item.itemName}">상품명</
            label>
            <input type="text" id="itemName" th:field="*{itemName}"
                   th:class="${errors?.containsKey('itemName')} ? 'form-control field-error' : 'form-control'"
                   class="form-control" placeholder="이름을 입력하세요">
            <div class="field-error" th:if="${errors?.containsKey('itemName')}"
                 th:text="${errors['itemName']}">
                상품명 오류
            </div>
        </div>
        <div>
            <label for="price" th:text="#{label.item.price}">가격</label>
            <input type="text" id="price" th:field="*{price}"
                   th:class="${errors?.containsKey('price')} ? 'form-control field-error' : 'form-control'"
                   class="form-control" placeholder="가격을 입력하세요">
            <div class="field-error" th:if="${errors?.containsKey('price')}"
                 th:text="${errors['price']}">
                가격 오류
            </div>
        </div>
        <div>
            <label for="quantity" th:text="#{label.item.quantity}">수량</label>
            <input type="text" id="quantity" th:field="*{quantity}"
                   th:class="${errors?.containsKey('quantity')} ? 'form-control field-error' : 'form-control'"
                   class="form-control" placeholder="수량을 입력하세요">
            <div class="field-error" th:if="${errors?.containsKey('quantity')}"
                 th:text="${errors['quantity']}">
                수량 오류
            </div>
        </div>
        <hr class="my-4">
        <div class="row">
            <div class="col">
                <button class="w-100 btn btn-primary btn-lg" type="submit"
                        th:text="#{button.save}">저장</button>
            </div>
            <div class="col">
                <button class="w-100 btn btn-secondary btn-lg"
                        onclick="location.href='items.html'"
                        th:onclick="|location.href='@{/validation/v1/items}'|"
                        type="button" th:text="#{button.cancel}">취소</button>
            </div>
        </div>
    </form>
</div> <!-- /container -->
</body>
</html>
```

**글로벌 오류 메시지**

```
<div th:if="${errors?.containsKey('globalError')}">
    <p class="field-error" th:text="${errors['globalError']}">전체 오류 메시지</p>
</div>
```

오류 메시지는 errors 에 내용이 있을 때만 출력하면 된다. 타임리프의 th:if 를 사용하면 조건에 만족할
때만 해당 HTML 태그를 출력할 수 있다.





> 참고 Safe Navigation Operator
> 만약 여기에서 errors 가 null 이라면 어떻게 될까?
> 생각해보면 등록폼에 진입한 시점에는 errors 가 없다.
> 따라서 errors.containsKey() 를 호출하는 순간 NullPointerException 이 발생한다.
>
> errors?. 은 errors 가 null 일때 NullPointerException 이 발생하는 대신, null 을 반환하는
> 문법이다.
> th:if 에서 null 은 실패로 처리되므로 오류 메시지가 출력되지 않는다.
>
> 

**필드 오류 처리**

<input type="text" th:classappend="${errors?.containsKey('itemName')} ? 'fielderror' : _"
 class="form-control">+



classappend 를 사용해서 해당 필드에 오류가 있으면 field-error 라는 클래스 정보를 더해서 폼의
색깔을 빨간색으로 강조한다. 만약 값이 없으면 _ (No-Operation)을 사용해서 아무것도 하지 않는다



상품 수정의 검증은 더 효율적인 검증 처리 방법을 학습한 다음에 진행한다.

**정리**
만약 검증 오류가 발생하면 입력 폼을 다시 보여준다.
검증 오류들을 고객에게 친절하게 안내해서 다시 입력할 수 있게 한다.
검증 오류가 발생해도 고객이 입력한 데이터가 유지된다.

**남은 문제점**

뷰 템플릿에서 중복 처리가 많다. 뭔가 비슷하다.
**타입 오류 처리가 안된다.** **Item 의 price , quantity 같은 숫자 필드는 타입이 Integer 이므로 문자**
**타입으로 설정하는 것이 불가능**하다. **숫자 타입에 문자가 들어오면 오류가 발생**한다. 그런데 이러한 오류는
**스프링MVC에서 컨트롤러에 진입하기도 전에 예외가 발생**하기 때문에, **컨트롤러가 호출되지도 않고, 400** 
**예외가 발생하면서 오류 페이지를 띄워준다.**

**Item 의 price 에 문자를 입력하는 것 처럼 타입 오류가 발생해도 고객이 입력한 문자를 화면에 남겨야**
**한다.** 만약 컨트롤러가 호출된다고 가정해도 Item 의 price 는 Integer 이므로 문자를 보관할 수가 없다. 
결국 문자는 바인딩이 불가능하므로 고객이 입력한 문자가 사라지게 되고, 고객은 본인이 어떤 내용을
입력해서 오류가 발생했는지 이해하기 어렵다.

결국 고객이 입력한 값도 어딘가에 별도로 관리가 되어야 한다.
지금부터 스프링이 제공하는 검증 방법을 하나씩 알아보자.

