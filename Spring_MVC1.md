##  웹서버, 웹 어플리케이션 서버

웹서버가 하는일을  웹 어플리케이션 서버에서도 할 수 있지만.



웹서버(Nginx Apache)는 정적 [ HTML CSS JS ] 와스(Tomcat)는 동적 처리 [웹 어플리케이션 로직 , JSP ]를 주로 담당한다.



# 서블릿

서블릿은 HTTP 요청 ~ 응답  발생하는 많은 과정들을 담당해서 해준다.



해당 url로 들어올 요청을 처리할 클래스를 만들고 extends HttpServlet 을 상속 받으면되고

// 요청이 들어왔을때 처리해줄 애플리케이션 로직만 작성해주면된다.



@WebServlet(name = "helloServlet", urlPatterns = "/hello") 
public class HelloServlet extends HttpServlet { 
 @Override 
 protected void service(HttpServletRequest request, HttpServletResponse response){ 
 //애플리케이션 로직
​    } 
}



HTTP 요청 정보를 편리하게 사용할 수 있는 HttpServletRequest

HTTP 응답 정보를 편리하게 제공할 수 있는 HttpServletResponse



HTTP 요청시

• WAS는 Request, Response 객체를 새로 만들어서 서블릿 객체 호출
• 개발자는 Request 객체에서 HTTP 요청 정보를 편리하게 꺼내서 사용
• 개발자는 Response 객체에 HTTP 응답 정보를 편리하게 입력
• WAS는 Response 객체에 담겨있는 내용으로 HTTP 응답 정보를 생성



톰캣처럼 서블릿을 지원하는 WAS를 서블릿 컨테이너라고 함

• 서블릿 컨테이너는 서블릿 객체를 생성, 초기화, 호출, 종료하는 생명주기 관리

• 서블릿 **객체는 싱글톤으로 관리**

​	• 고객의 요청이 올 때 마다 계속 객체를 생성하는 것은 비효율

​	• 최초 로딩 시점에 서블릿 객체를 미리 만들어두고 재활용

​	• 모든 고객 요청은 동일한 서블릿 객체 인스턴스에 접근

​	**• 공유 변수 사용 주의**

​	• 서블릿 컨테이너 종료시 함께 종료

• JSP도 서블릿으로 변환 되어서 사용

• 동시 요청을 위한 멀티 쓰레드 처리 지원





# 동시요청 멀티 쓰레드

서블릿은 쓰레드가 호출한다.





쓰레드
• 애플리케이션 코드를 하나하나 순차적으로 실행하는 것은 쓰레드
• 자바 메인 메서드를 처음 실행하면 main이라는 이름의 쓰레드가 실행
• 쓰레드가 없다면 자바 애플리케이션 실행이 불가능
• 쓰레드는 한번에 하나의 코드 라인만 수행
• 동시 처리가 필요하면 쓰레드를 추가로 생성



HTTP request 요청 -> 쓰레드 할당 -> 쓰레드의 서블릿 호출  -> 서블릿 응답 -> Http Response

단일 쓰레드일경우 처리가 지연되는 경우 문제가 발생한다.

요청마다 쓰레드를 생성하면 해당 문제를 해결 가능



요청 마다 쓰레드 생성

장단점



• 장점

• 동시 요청을 처리할 수 있다.
• 리소스(CPU, 메모리)가 허용할 때 까지 처리가능
• 하나의 쓰레드가 지연 되어도, 나머지 쓰레드는 정상 동작한다.



• 단점



• 쓰레드는 생성 비용은 매우 비싸다.
• 고객의 요청이 올 때 마다 쓰레드를 생성하면, 응답 속도가 늦어진다.
• 쓰레드는 컨텍스트 스위칭 비용이 발생한다.
• 쓰레드 생성에 제한이 없다.
• 고객 요청이 너무 많이 오면, CPU, 메모리 임계점을 넘어서 서버가 죽을 수 있다.



**컨텍스트 스위칭이란?**

**CPU에서 실행 중이던 프로세스/스레드가 다른 프로세스/스레드로 교체되는 것**을 의미합니다.

단일 CPU서는 한 번에 하나의 스레드나 프로세스만 실행 가능합니다. 그래서 동시에 여러 스레드나 프로세스를 단일 CPU에서 실행하기 위해서는 서로 번갈아 가면서 실행이 돼야 하고 이때 CPU에서 실행 중이던 프로세스나 스레드에서 다른 프로세스나 스레드로 교체되는 것을 컨텍스트 스위칭이라고 합니다.

쓰레드 생성에 제한이 없다면 ( cpu 메모리가 임계점을 넘어서 서버가 죽을 수 있음)

ㄴ ex) 수강신청,, 



# 쓰레드 풀



수영장 안에 쓰레드들이 뛰어놀고 있다고 생각



쓰레드를 풀 안에 미리 만들어서 생성해놓고 가져다 쓰는 느낌

만약 서버의 쓰레드 풀을 200개로 설정해놓으면 동시요청이 많이와서 200개를 다쓰고 있다면

쓰레드를 대기하거나 거절 시킴



요청 마다 쓰레드 생성의 단점 보완

• 특징
• 필요한 쓰레드를 쓰레드 풀에 보관하고 관리한다.
• 쓰레드 풀에 생성 가능한 쓰레드의 최대치를 관리한다. 톰캣은 최대 200개 기본 설정 (변경 가능)



• 사용

• 쓰레드가 필요하면, 이미 생성되어 있는 쓰레드를 쓰레드 풀에서 꺼내서 사용한다.
• 사용을 종료하면 쓰레드 풀에 해당 쓰레드를 반납한다.
• 최대 쓰레드가 모두 사용중이어서 쓰레드 풀에 쓰레드가 없으면?
• 기다리는 요청은 거절하거나 특정 숫자만큼만 대기하도록 설정할 수 있다.



• 장점
• 쓰레드가 미리 생성되어 있으므로, 쓰레드를 생성하고 종료하는 비용(CPU)이 절약되고, 응답 시간이 빠르다.
• 생성 가능한 쓰레드의 최대치가 있으므로 너무 많은 요청이 들어와도 기존 요청은 안전하게 처리할 수 있다.



쓰레드 풀

실무 팁
• WAS의 주요 튜닝 포인트는 최대 쓰레드(max thread) 수이다.

• 이 값을 너무 낮게 설정하면?
• 동시 요청이 많으면, 서버 리소스는 여유롭지만, 클라이언트는 금방 응답 지연

• 이 값을 너무 높게 설정하면?
• 동시 요청이 많으면, CPU, 메모리 리소스 임계점 초과로 서버 다운

• 장애 발생시?
• 클라우드면 일단 서버부터 늘리고, 이후에 튜닝
• 클라우드가 아니면 열심히 튜닝



쓰레드 풀
쓰레드 풀의 적정 숫자

• 적정 숫자는 어떻게 찾나요?

• 애플리케이션 로직의 복잡도, CPU, 메모리, IO 리소스 상황에 따라 모두 다름



• 성능 테스트

• 최대한 실제 서비스와 유사하게 성능 테스트 시도
• 툴: 아파치 ab, 제이미터, nGrinder



테스트툴을 통해 부하테스트를 해보는것이 적절하다..



WAS의 멀티 쓰레드 지원

핵심

• 멀티 쓰레드에 대한 부분은 WAS가 처리
• 개발자가 멀티 쓰레드 관련 코드를 신경쓰지 않아도 됨
• 개발자는 마치 싱글 쓰레드 프로그래밍을 하듯이 편리하게 소스 코드를 개발
• 멀티 쓰레드 환경이므로 싱글톤 객체(서블릿, 스프링 빈)는 주의해서 사용

ㄴ 싱글톤 같은경우 객체를 하나 생성해서 클라이언트가 돌려쓰는느낌이기에 조심해서 사용해야한다! (공유변수, 맴버변수 같은것들!)



# 서버 사이드 랜더링 , 클라이언트 사이드 랜더링

• SSR - 서버 사이드 렌더링
• HTML 최종 결과를 서버에서 만들어서 웹 브라우저에 전달
• 주로 정적인 화면에 사용
• 관련기술: JSP, 타임리프 -> 백엔드 개발자



• CSR - 클라이언트 사이드 렌더링
• HTML 결과를 자바스크립트를 사용해 웹 브라우저에서 동적으로 생성해서 적용
• 주로 동적인 화면에 사용, 웹 환경을 마치 앱 처럼 필요한 부분부분 변경할 수 있음
• 예) 구글 지도, Gmail, 구글 캘린더
• 관련기술: React, Vue.js -> 웹 프론트엔드 개발자
• 참고
• React, Vue.js를 CSR + SSR 동시에 지원하는 웹 프레임워크도 있음
• SSR을 사용하더라도, 자바스크립트를 사용해서 화면 일부를 동적으로 변경 가능



# 자바 웹 기술 역사

자바 웹 기술 역사

과거 기술
• 서블릿 - 1997
• HTML 생성이 어려움
• JSP - 1999
• HTML 생성은 편리하지만, 비즈니스 로직까지 너무 많은 역할 담당
• 서블릿, JSP 조합 MVC 패턴 사용
• 모델, 뷰 컨트롤러로 역할을 나누어 개발
• MVC 프레임워크 춘추 전국 시대 - 2000년 초 ~ 2010년 초
• MVC 패턴 자동화, 복잡한 웹 기술을 편리하게 사용할 수 있는 다양한 기능 지원
• 스트럿츠, 웹워크, 스프링 MVC(과거 버전)



자바 웹 기술 역사

현재 사용 기술
• 애노테이션 기반의 스프링 MVC 등장
• @Controller
• MVC 프레임워크의 춘추 전국 시대 마무리
• 스프링 부트의 등장
• 스프링 부트는 서버를 내장
• 과거에는 서버에 WAS를 직접 설치하고, 소스는 War 파일을 만들어서 설치한 WAS에 배포
• 스프링 부트는 빌드 결과(Jar)에 WAS 서버 포함 -> 빌드 배포 단순화



최신 기술 - 스프링 웹 플럭스(WebFlux)



• 특징
• 비동기 넌 블러킹 처리
• 최소 쓰레드로 최대 성능 - 쓰레드 컨텍스트 스위칭 비용 효율화
• 함수형 스타일로 개발 - 동시처리 코드 효율화
• 서블릿 기술 사용X



그런데

• 웹 플럭스는 기술적 난이도 매우 높음
• 아직은 RDB 지원 부족
• 일반 MVC의 쓰레드 모델도 충분히 빠르다.
• 실무에서 아직 많이 사용하지는 않음 (전체 1% 이하)



## 자바 뷰 템플릿 역사

HTML을 편리하게 생성하는 뷰 기능

@  JSP

• 속도 느림, 기능 부족



@ 프리마커(Freemarker), Velocity(벨로시티)

• 속도 문제 해결, 다양한 기능



@  타임리프(Thymeleaf)

• 내추럴 템플릿: HTML의 모양을 유지하면서 뷰 템플릿 적용 가능
• 스프링 MVC와 강력한 기능 통합
• 최선의 선택, 단 성능은 프리마커, 벨로시티가 더 빠름



# 서블릿

@ServletComponentScan
스프링 부트는 서블릿을 직접 등록해서 사용할 수 있도록 @ServletComponentScan 을 지원한다. 



WebServlet 서블릿 애노테이션
name: 서블릿 이름
urlPatterns: URL 매핑



```
public class HelloServlet extends HttpServlet
```

서블릿을 사용하기위해서는 HttpServlet을 상속해줘야 한다.



HTTP 요청을 통해 매핑된 URL이 호출되면 서블릿 컨테이너는 다음 메서드를 실행한다.
protected void service(HttpServletRequest request, HttpServletResponse response)



welcome 페이지 추가
지금부터 개발할 내용을 편리하게 참고할 수 있도록 welcome 페이지를 만들어두자.
webapp 경로에 index.html 을 두면 http://localhost:8080 호출시 index.html 페이지가 열린다

임시 저장소 기능
해당 HTTP 요청이 시작부터 끝날 때 까지 유지되는 임시 저장소 기능

저장: request.setAttribute(name, value)

조회: request.getAttribute(name)



세션 관리 기능
request.getSession(create: true)



## start-line 정보



private void printStartLine(HttpServletRequest request) {
 System.out.println("--- REQUEST-LINE - start ---");
 System.out.println("request.getMethod() = " + request.getMethod()); //GET
 System.out.println("request.getProtocal() = " + request.getProtocol()); //
HTTP/1.1
 System.out.println("request.getScheme() = " + request.getScheme()); //http
 // http://localhost:8080/request-header
 System.out.println("request.getRequestURL() = " + request.getRequestURL());
 // /request-test
 System.out.println("request.getRequestURI() = " + request.getRequestURI());
 //username=hi
 System.out.println("request.getQueryString() = " +
request.getQueryString());
 System.out.println("request.isSecure() = " + request.isSecure()); //https 
사용 유무
 System.out.println("--- REQUEST-LINE - end ---");
 System.out.println();
}



--- REQUEST-LINE - start ---
request.getMethod() = GET
request.getProtocal() = HTTP/1.1
request.getScheme() = http
request.getRequestURL() = http://localhost:8080/request-header
request.getRequestURI() = /request-header
request.getQueryString() = username=hello
request.isSecure() = false
--- REQUEST-LINE - end --



## heaeder 정보





//Header 모든 정보
private void printHeaders(HttpServletRequest request) {
 System.out.println("--- Headers - start ---");
/*
 Enumeration headerNames = request.getHeaderNames();
 while (headerNames.hasMoreElements()) {
 String headerName = headerNames.nextElement();
 System.out.println(headerName + ": " + request.getHeader(headerName));
 }
*/
 request.getHeaderNames().asIterator()
 .forEachRemaining(headerName -> System.out.println(headerName + ": 
" + request.getHeader(headerName)));
 System.out.println("--- Headers - end ---");
 System.out.println();
}





--- Headers - start ---
host: localhost:8080
connection: keep-alive
cache-control: max-age=0
sec-ch-ua: "Chromium";v="88", "Google Chrome";v="88", ";Not A Brand";v="99"
sec-ch-ua-mobile: ?0
upgrade-insecure-requests: 1
user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_0) AppleWebKit/537.36 
(KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36
accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/
webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
sec-fetch-site: none
sec-fetch-mode: navigate
sec-fetch-user: ?1
sec-fetch-dest: document
accept-encoding: gzip, deflate, br
accept-language: ko,en-US;q=0.9,en;q=0.8,ko-KR;q=0.7
--- Headers - end ---



//Header 편리한 조회
private void printHeaderUtils(HttpServletRequest request) {
 System.out.println("--- Header 편의 조회 start ---");
 System.out.println("[Host 편의 조회]");
 System.out.println("request.getServerName() = " +
request.getServerName()); //Host 헤더
 System.out.println("request.getServerPort() = " +
request.getServerPort()); //Host 헤더
 System.out.println();
 System.out.println("[Accept-Language 편의 조회]");
 request.getLocales().asIterator()
 .forEachRemaining(locale -> System.out.println("locale = " +
locale));
 System.out.println("request.getLocale() = " + request.getLocale());
 System.out.println();
 System.out.println("[cookie 편의 조회]");
 if (request.getCookies() != null) {
 for (Cookie cookie : request.getCookies()) {
 System.out.println(cookie.getName() + ": " + cookie.getValue());
 }
 }
 System.out.println();
 System.out.println("[Content 편의 조회]");
 System.out.println("request.getContentType() = " +
request.getContentType());
 System.out.println("request.getContentLength() = " +
request.getContentLength());
 System.out.println("request.getCharacterEncoding() = " +
request.getCharacterEncoding());
 System.out.println("--- Header 편의 조회 end ---");
 System.out.println();
}





--- Header 편의 조회 start ---
[Host 편의 조회]
request.getServerName() = localhost
request.getServerPort() = 8080
[Accept-Language 편의 조회]
locale = ko
locale = en_US
locale = en
locale = ko_KR
request.getLocale() = ko
[cookie 편의 조회]
[Content 편의 조회]
request.getContentType() = null
request.getContentLength() = -1
request.getCharacterEncoding() = UTF-8
--- Header 편의 조회 end ---



## 기타 정보



//기타 정보
private void printEtc(HttpServletRequest request) {
 System.out.println("--- 기타 조회 start ---");
 System.out.println("[Remote 정보]");
 System.out.println("request.getRemoteHost() = " +
request.getRemoteHost()); //
 System.out.println("request.getRemoteAddr() = " +
request.getRemoteAddr()); //
 System.out.println("request.getRemotePort() = " +
request.getRemotePort()); //
 System.out.println();
 System.out.println("[Local 정보]");
 System.out.println("request.getLocalName() = " +
request.getLocalName()); //
 System.out.println("request.getLocalAddr() = " +
request.getLocalAddr()); //
 System.out.println("request.getLocalPort() = " +
request.getLocalPort()); //
 System.out.println("--- 기타 조회 end ---");
 System.out.println();
}

--- 기타 조회 start ---
[Remote 정보]
request.getRemoteHost() = 0:0:0:0:0:0:0:1
request.getRemoteAddr() = 0:0:0:0:0:0:0:1
request.getRemotePort() = 54305
[Local 정보]
request.getLocalName() = localhost
request.getLocalAddr() = 0:0:0:0:0:0:0:1
request.getLocalPort() = 8080
--- 기타 조회 end ---  



# HTTP 요청 데이터 

HTTP 요청 메시지를 통해 클라이언트에서 서버로 데이터를 전달하는 방법을 알아보자.
주로 다음 3가지 방법을 사용한다.

**GET - 쿼리 파라미터**
/url?username=hello&age=20



메시지 바디 없이, URL의 쿼리 파라미터에 데이터를 포함해서 전달

예) 검색, 필터, 페이징등에서 많이 사용하는 방식



String username = request.getParameter("username"); //단일 파라미터 조회
Enumeration parameterNames = request.getParameterNames(); //파라미터 이름들
모두 조회
Map parameterMap = request.getParameterMap(); //파라미터를 Map
으로 조회
String[] usernames = request.getParameterValues("username"); //복수 파라미터 조회



**전체 파라미터 조회**

 request.getParameterNames().asIterator()
 .forEachRemaining(paramName -> System.out.println(paramName +
"=" + request.getParameter(paramName)));

**단일 파라미터 조회**

String username = request.getParameter("username")

**복수 파라미터 조회**

String[] usernames = request.getParameterValues("username");
 for (String name : usernames) {
 System.out.println("username=" + name);
 }



<form action="/request-param" method="post">
 username: <input type="text" name="username" />
 age: <input type="text" name="age" />
 <button type="submit">전송</button>
</form>



@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {





**POST - HTML Form**
content-type: application/x-www-form-urlencoded



메시지 바디에 쿼리 파리미터 형식으로 전달 username=hello&age=20
예) 회원 가입, 상품 주문, HTML Form 사용



application/x-www-form-urlencoded 형식은 앞서 GET에서 살펴본 쿼리 파라미터 형식과 같다. 따라서 쿼리 파라미터 조회 메서드를 그대로 사용하면 된다.

클라이언트(웹 브라우저) 입장에서는 두 방식에 차이가 있지만, 서버 입장에서는 둘의 형식이 동일하므로, 

request.getParameter() 로 편리하게 구분없이 조회할 수 있다.



**HTTP message body**에 데이터를 직접 담아서 요청

HTTP API에서 주로 사용, JSON, XML, TEXT

데이터 형식은 주로 JSON 사용

POST, PUT, PATCH



## HTTP 요청 데이터 - API 메시지 바디 - 단순 텍스트

먼저 가장 단순한 텍스트 메시지를 HTTP 메시지 바디에 담아서 전송하고, 읽어보자.
HTTP 메시지 바디의 데이터를 InputStream을 사용해서 직접 읽을 수 있다.



// 메시지 바디에 있는 내용을 바이트코드로 얻을수있음

ServletInputStream inputStream = request.getInputStream();



//바이트를 문자로 변환하는 작업 (어떤 인코딩인지 명시 필요)

 String messageBody = StreamUtils.copyToString(inputStream,
StandardCharsets.UTF_8);



## HTTP 요청 데이터 - API 메시지 바디 - JSON

content-type: application/json



json도 문자데이터라 text/plain 과 방식은 동일



ServletInputStream inputStream = request.getInputStream();
 String messageBody = StreamUtils.copyToString(inputStream,
StandardCharsets.UTF_8);
 System.out.println("messageBody = " + messageBody);



**읽어온 json 데이타를 객체형식으로 변환하는 방법**

HelloData helloData = objectMapper.readValue(messageBody,
HelloData.class);
 System.out.println("helloData.username = " + helloData.getUsername());
 System.out.println("helloData.age = " + helloData.getAge());





# HttpServletResponse - 기본 사용법



**HttpServletResponse 역할**
HTTP 응답 메시지 생성
HTTP 응답코드 지정
헤더 생성
바디 생성



**편의 기능 제공**
Content-Type, 쿠키, Redirect



//[status-line]
 response.setStatus(HttpServletResponse.SC_OK); //200 응답코드 지정

//[response-headers]
 response.setHeader("Content-Type", "text/plain;charset=utf-8");
 response.setHeader("Cache-Control", "no-cache, no-store, mustrevalidate"); // 캐시무효화
 response.setHeader("Pragma", "no-cache"); // 과거버전 캐시 무효화
 response.setHeader("my-header","hello");



 //[Header 편의 메서드]
 content(response);
 cookie(response);
 redirect(response);



//[message body]
 PrintWriter writer = response.getWriter();
 writer.println("ok");



//content 설정

response.setContentType("text/plain");
 response.setCharacterEncoding("utf-8")



// 쿠키 설정

Cookie cookie = new Cookie("myCookie", "good");
 cookie.setMaxAge(600); //600초
 response.addCookie(cookie);



//리다이렉트

response.sendRedirect("/basic/hello-form.html");



## HTTP 응답 데이터 - HTML

단순 텍스트 응답





response.setContentType("text/html");
 response.setCharacterEncoding("utf-8");
 PrintWriter writer = response.getWriter();
 writer.println("<html>");
 writer.println("<body>");
 writer.println(" <div>안녕?</div>");
 writer.println("</body>");
 writer.println("</html>")



HTTP 응답으로 HTML을 반환할 때는 content-type을 text/html 로 지정해야 한다.



## HTTP 응답 데이터 - JSON



response.setHeader("content-type", "application/json");
 response.setCharacterEncoding("utf-8");

 HelloData data = new HelloData();
 data.setUsername("kim");
 data.setAge(20);



 //{"username":"kim","age":20} 객체를 JSON 형식으로 바꾸기

 **String result = objectMapper.writeValueAsString(data);**
 response.getWriter().write(result);



HTTP 응답으로 JSON을 반환할 때는 content-type을 application/json 로 지정해야 한다.
Jackson 라이브러리가 제공하는 **objectMapper.writeValueAsString()** 를 사용하면 객체를 JSON 
문자로 변경할 수 있다



application/json 은 스펙상 utf-8 형식을 사용하도록 정의되어 있다. 그래서 스펙에서 charset=utf-8 
과 같은 추가 파라미터를 지원하지 않는다. 따라서 application/json 이라고만 사용해야지

> application/json;charset=utf-8 이라고 전달하는 것은 의미 없는 파라미터를 추가한 것이 된다.
> response.getWriter()를 사용하면 추가 파라미터를 자동으로 추가해버린다. 이때는
> response.getOutputStream()으로 출력하면 그런 문제가 없다.



## 서블릿으로 동적 HTML 코드 작성



PrintWriter w = response.getWriter();

w.write("<HTML>");

//자바 코드로 HTML을 제공해야 함 

// 이런식으로 html 코드를 작성하면 너무 귀찮고 번거로워짐 그래서 제공하는게 html 템플릿엔진 

// 대표적으로 JSP , 타임리프 , Freemarker , Velocity가 존재



## JSP



**JSP 라이브러리 추가**



//JSP 추가 시작
implementation 'org.apache.tomcat.embed:tomcat-embed-jasper'
implementation 'javax.servlet:jstl'
//JSP 추가 끝



**jsp 는 자바 파일이아니라 webapp 폴더 밑에 생성★** 



****// JSP 파일 명시

<%@ page contentType="text/html;charset=UTF-8" language="java" %>



// java 코드를 넣을때는

<% ~  %>

// java 코드 출력

<%= %>



// 패키지 임포트

<%@ page import="hello.servlet.domain.member.MemberRepository" %>
<%@ page import="hello.servlet.domain.member.Member" %>



**jsp 파일 에서도 서블릿의 request resposne 가 사용 가능하다.**



- id=<%=member.getId()%>
- username=<%=member.getUsername()%>
- age=<%=member.getAge()%>



// 루프 사용

<%
 for (Member member : members) {
 out.write(" ");
 out.write(" " + member.getId() + "");
 out.write(" " + member.getUsername() + "");
 out.write(" " + member.getAge() + "");
 out.write(" ");
 }
%>



**서블릿을 개발할때는 HTML을 만드는 작업이 지저분 하고 복잡**



**JSP 사용해서 HTML 생성하는부분은 깔끔해졌지만**

**JAVA 코드 및 데이터조회하는 다양한 코드가 JSP에 노출되어있고 JSP에 너무 많은 역할이 들어가있다.**



위 단점을 해결하기 위해



**MVC 패턴의 등장**


**비즈니스 로직은 서블릿 처럼 다른곳에서 처리하고, JSP는 목적에 맞게 HTML로 화면(View)을 그리는**
**일에 집중하도록 하자. 과거 개발자들도 모두 비슷한 고민이 있었고, 그래서 MVC 패턴이 등장했다. 우리도**
**직접 MVC 패턴을 적용해서 프로젝트를 리팩터링 해보**자



**하나의 서블릿이나 JSP만으로 비즈니스 로직과 뷰 렌더링까지 모두 처리하게 되면, 너무 많은 역할을**
**하게되고, 결과적으로 유지보수가 어려워진다.**



## Model , View , Controller



MVC 패턴은 지금까지 학습한 것 처럼 하나의 서블릿이나, JSP로 처리하던 것을 컨트롤러(Controller)와

뷰(View)라는 영역으로 서로 역할을 나눈 것을 말한다. 



웹 애플리케이션은 보통 이 MVC 패턴을 사용한다.

컨트롤러: HTTP 요청을 받아서 파라미터를 검증하고, 비즈니스 로직을 실행한다. 그리고 뷰에 전달할 결과
데이터를 조회해서 모델에 담는다.



모델: 뷰에 출력할 데이터를 담아둔다. 뷰가 필요한 데이터를 모두 모델에 담아서 전달해주는 덕분에 뷰는
비즈니스 로직이나 데이터 접근을 몰라도 되고, 화면을 렌더링 하는 일에 집중할 수 있다.

뷰: 모델에 담겨있는 데이터를 사용해서 화면을 그리는 일에 집중한다. 여기서는 HTML을 생성하는 부분을
말한다.





![MVC BEFORE AFTER](C:\Users\User\Desktop\Spring_2022\MVC BEFORE AFTER.png)

![MVC BEFORE AFTER](C:\Users\User\Desktop\Spring_2022\mvc pattern_2.JPG)

> 참고
> 컨트롤러에 비즈니스 로직을 둘 수도 있지만, 이렇게 되면 컨트롤러가 너무 많은 역할을 담당한다. 그래서
> 일반적으로 비즈니스 로직은 서비스(Service)라는 계층을 별도로 만들어서 처리한다. 그리고 컨트롤러는
> 비즈니스 로직이 있는 서비스를 호출하는 담당한다. 참고로 비즈니스 로직을 변경하면 비즈니스 로직을
> 호출하는 컨트롤러의 코드도 변경될 수 있다. 앞에서는 이해를 돕기 위해 비즈니스 로직을 호출한다는 표현
> 보다는, 비즈니스 로직이라 설명했다



**MVC 패턴 - 적용**



String viewPath = "/WEB-INF/views/new-form.jsp";
 RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
 dispatcher.forward(request, response)



**서블릿에서 jsp 호출하기**



<!-- 상대경로 사용, [현재 URL이 속한 계층 경로 + /save] -->
<form action="save" method="post">
 username: <input type="text" name="username" />
 age: <input type="text" name="age" />
 <button type="submit">전송</button>
</form>



localhost:8080/servlet-mvc/members/new-form -> localhost:8080/servlet-mvc/members/save



**dispatcher.forward()** : 다른 서블릿이나 JSP로 이동할 수 있는 기능이다. 서버 내부에서 다시 호출이
발생한



## 프론트 컨트롤러 패턴

![front Controller](C:\Users\User\Desktop\Spring_2022\front Controller.JPG)

**FrontController 패턴 특징**



프론트 컨트롤러 서블릿 하나로 클라이언트의 요청을 받음
프론트 컨트롤러가 요청에 맞는 컨트롤러를 찾아서 호출
입구를 하나로!
공통 처리 가능
프론트 컨트롤러를 제외한 나머지 컨트롤러는 서블릿을 사용하지 않아도 됨



**스프링 웹 MVC와 프론트 컨트롤러**

스프링 웹 MVC의 핵심도 바로 FrontController
스프링 웹 MVC의 DispatcherServlet이 FrontController 패턴으로 구현되어 있음





@WebServlet(name = "frontControllerServletV1", urlPatterns = "/frontcontroller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {
 private Map controllerMap = new HashMap<>();
 public FrontControllerServletV1() {

// 생성자에서 Map을이용한 객체 생성

 controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());

controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());

 controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
 }



@Override
 protected void service(HttpServletRequest request, HttpServletResponse 
response)
 throws ServletException, IOException {
 System.out.println("FrontControllerServletV1.service");
 String requestURI = request.getRequestURI(); // 요청에 사용된 URI 
 ControllerV1 controller = controllerMap.get(requestURI);
 if (controller == null) { // 빈 URI 요청시
 response.setStatus(HttpServletResponse.SC_NOT_FOUND);
 return;
 }
 controller.process(request, response);
 }
}





**프론트 컨트롤러 분석**

urlPatterns
urlPatterns = "/front-controller/v1/*" : /front-controller/v1 를 포함한 하위 모든 요청은
이 서블릿에서 받아들인다.
예) /front-controller/v1 , /front-controller/v1/a , /front-controller/v1/a/b





//request에 담겨있는 파라미터를 Map에 담는 방식

private Map createParamMap(HttpServletRequest request) {
 Map paramMap = new HashMap<>();
 request.getParameterNames().asIterator()
 .forEachRemaining(paramName -> paramMap.put(paramName,
request.getParameter(paramName)));
 return paramMap;
 }



## 어댑터 패턴



![adapter pattern](C:\Users\User\Desktop\Spring_2022\adapter pattern.JPG)



## 스프링 MVC 



![SPRING ](C:\Users\User\Desktop\Spring_2022\SPRING .JPG)



스프링 MVC의 프론트 컨트롤러가 바로 디스패처 서블릿(DispatcherServlet)이다.



DispacherServlet 도 부모 클래스에서 HttpServlet 을 상속 받아서 사용하고, 서블릿으로 동작한다.
DispatcherServlet FrameworkServlet HttpServletBean HttpServlet
스프링 부트는 DispacherServlet 을 서블릿으로 자동으로 등록하면서 모든 경로( urlPatterns="/" )에
대해서 매핑한다.

참고: 더 자세한 경로가 우선순위가 높다. 그래서 기존에 등록한 서블릿도 함께 동작한다



**요청 흐름**

**서블릿이 호출되면 HttpServlet 이 제공하는 serivce() 가 호출된다.**
**스프링 MVC는 DispatcherServlet 의 부모인 FrameworkServlet 에서 service() 를 오버라이드**
**해두었다.**





**FrameworkServlet.service() 를 시작으로 여러 메서드가 호출되면서**
**"DispacherServlet.doDispatch()**"가 호출된다.



**지금부터 DispacherServlet 의 핵심인 doDispatch() 코드를 분석해보자. 최대한 간단히 설명하기**
**위해 예외처리, 인터셉터 기능은 제외했다**



protected void doDispatch(HttpServletRequest request, HttpServletResponse 
response) throws Exception {
HttpServletRequest processedRequest = request;
HandlerExecutionChain mappedHandler = null;
ModelAndView mv = null;
// 1. 핸들러 조회
mappedHandler = getHandler(processedRequest);
if (mappedHandler == null) {
noHandlerFound(processedRequest, response);
return;
}
// 2. 핸들러 어댑터 조회 - 핸들러를 처리할 수 있는 어댑터
HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());

// 3. 핸들러 어댑터 실행 -> 4. 핸들러 어댑터를 통해 핸들러 실행 -> 5. ModelAndView 반환
mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
processDispatchResult(processedRequest, response, mappedHandler, mv,
dispatchException);
}
private void processDispatchResult(HttpServletRequest request,
HttpServletResponse response, HandlerExecutionChain mappedHandler, ModelAndView 
mv, Exception exception) throws Exception {

// 뷰 렌더링 호출
render(mv, request, response);
}
protected void render(ModelAndView mv, HttpServletRequest request,
HttpServletResponse response) throws Exception {
View view;
String viewName = mv.getViewName();
// 6. 뷰 리졸버를 통해서 뷰 찾기, 7. View 반환

view = resolveViewName(viewName, mv.getModelInternal(), locale, request);

// 8. 뷰 렌더링
view.render(mv.getModelInternal(), request, response);
}



동작 순서

1. 핸들러 조회: 핸들러 매핑을 통해 요청 URL에 매핑된 핸들러(컨트롤러)를 조회한다.

2. 핸들러 어댑터 조회: 핸들러를 실행할 수 있는 핸들러 어댑터를 조회한다.

3. 핸들러 어댑터 실행: 핸들러 어댑터를 실행한다.

4. 핸들러 실행: 핸들러 어댑터가 실제 핸들러를 실행한다.

5. ModelAndView 반환: 핸들러 어댑터는 핸들러가 반환하는 정보를 ModelAndView로 변환해서
  반환한다.

6. viewResolver 호출: 뷰 리졸버를 찾고 실행한다.
  JSP의 경우: InternalResourceViewResolver 가 자동 등록되고, 사용된다.

7. View 반환: 뷰 리졸버는 뷰의 논리 이름을 물리 이름으로 바꾸고, 렌더링 역할을 담당하는 뷰 객체를
  반환한다.
  JSP의 경우 InternalResourceView(JstlView) 를 반환하는데, 내부에 forward() 로직이 있다.

8. 뷰 렌더링: 뷰를 통해서 뷰를 렌더링 한다.
  인터페이스 살펴보기
  스프링 MVC의 큰 강점은 DispatcherServlet 코드의 변경 없이, 원하는 기능을 변경하거나 확장할 수
  있다는 점이다. 지금까지 설명한 대부분을 확장 가능할 수 있게 인터페이스로 제공한다.
  이 인터페이스들만 구현해서 DispatcherServlet 에 등록하면 여러분만의 컨트롤러를 만들 수도 있다.

  ​

  **주요 인터페이스 목록**

  ​

  핸들러 매핑: org.springframework.web.servlet.HandlerMapping
  핸들러 어댑터: org.springframework.web.servlet.HandlerAdapter
  뷰 리졸버: org.springframework.web.servlet.ViewResolver
  뷰: org.springframework.web.servlet.View



## 핸들러 매핑과 핸들러 어댑터

**핸들러 매핑과 핸들러 어댑터**

핸들러 매핑과 핸들러 어댑터가 어떤 것들이 어떻게 사용되는지 알아보자.
지금은 전혀 사용하지 않지만, 과거에 주로 사용했던 스프링이 제공하는 간단한 컨트롤러로 핸들러 매핑과
어댑터를 이해해보자



**과거 버전의 스프링 컨트롤러**

public interface Controller {
ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse 
response) throws Exception;
}



Controller 인터페이스는 @Controller 애노테이션과는 전혀 다르다.



@Component("/springmvc/old-controller")



@Component : 이 컨트롤러는 /springmvc/old-controller 라는 이름의 스프링 빈으로 등록되었다.
**빈의 이름으로 URL을 매핑할 것이다**![HandlerMapping + HandlerAdapter](C:\Users\User\Desktop\Spring_2022\HandlerMapping + HandlerAdapter.JPG). 핸들러 매핑으로 핸들러 조회

1. HandlerMapping 을 순서대로 실행해서, 핸들러를 찾는다.
2. 이 경우 빈 이름으로 핸들러를 찾아야 하기 때문에 이름 그대로 빈 이름으로 핸들러를 찾아주는
  BeanNameUrlHandlerMapping 가 실행에 성공하고 핸들러인 OldController 를 반환한다.
3. 핸들러 어댑터 조회
4. HandlerAdapter 의 supports() 를 순서대로 호출한다.
5. SimpleControllerHandlerAdapter 가 Controller 인터페이스를 지원하므로 대상이 된다


3. 핸들러 어댑터 실행

4. 디스패처 서블릿이 조회한 SimpleControllerHandlerAdapter 를 실행하면서 핸들러 정보도 함께
  넘겨준다.

5. SimpleControllerHandlerAdapter 는 핸들러인 OldController 를 내부에서 실행하고, 그 결과를
  반환한다.

  ​

  정리 - OldController 핸들러매핑, 어댑터

  OldController 를 실행하면서 사용된 객체는 다음과 같다.
  HandlerMapping = BeanNameUrlHandlerMapping
  HandlerAdapter = SimpleControllerHandlerAdapter



3. spring.mvc.view.prefix=/WEB-INF/views/
   spring.mvc.view.suffix=.jsp





뷰 리졸버 - InternalResourceViewResolver (내부 자원 이동)



스프링 부트는 InternalResourceViewResolver 라는 뷰 리졸버를 자동으로 등록하는데, 이때
application.properties 에 등록한 spring.mvc.view.prefix , spring.mvc.view.suffix 설정
정보를 사용해서 등록한다.
참고로 권장하지는 않지만 설정 없이 다음과 같이 전체 경로를 주어도 동작하기는 한다.
return new ModelAndView("/WEB-INF/views/new-form.jsp");



1 = BeanNameViewResolver : 빈 이름으로 뷰를 찾아서 반환한다. (예: 엑셀 파일 생성
기능에 사용)
2 = InternalResourceViewResolver : JSP를 처리할 수 있는 뷰를 반환한다.



## 스프링 MVC



@RequestMapping

스프링은 애노테이션을 활용한 매우 유연하고, 실용적인 컨트롤러를 만들었는데 이것이 바로
@RequestMapping 애노테이션을 사용하는 컨트롤러이다. 다들 한번쯤 사용해보았을 것이다.
여담이지만 과거에는 스프링 프레임워크가 MVC 부분이 약해서 스프링을 사용하더라도 MVC 웹 기술은
스트럿츠 같은 다른 프레임워크를 사용했었다. 그런데 @RequestMapping 기반의 애노테이션 컨트롤러가
등장하면서, MVC 부분도 스프링의 완승으로 끝이 났다.





@Controller : 
스프링이 자동으로 스프링 빈으로 등록한다. (내부에 @Component 애노테이션이 있어서 컴포넌트
스캔의 대상이 됨)
스프링 MVC에서 애노테이션 기반 컨트롤러로 인식한다.

@RequestMapping : 요청 정보를 매핑한다. 해당 URL이 호출되면 이 메서드가 호출된다. 애노테이션을
기반으로 동작하기 때문에, 메서드의 이름은 임의로 지으면 된다.

ModelAndView : 모델과 뷰 정보를 담아서 반환하면 된다





@Controller
@RequestMapping("/springmvc/v2/members")
public class SpringMemberControllerV2 {
 private MemberRepository memberRepository = MemberRepository.getInstance();



 @RequestMapping("/new-form")
 public ModelAndView newForm() {
 return new ModelAndView("new-form");
 }
 @RequestMapping("/save")
 public ModelAndView save(HttpServletRequest request, HttpServletResponse 
response) {
 String username = request.getParameter("username");
 int age = Integer.parseInt(request.getParameter("age"));
 Member member = new Member(username, age);
 memberRepository.save(member);
 ModelAndView mav = new ModelAndView("save-result");
 mav.addObject("member", member);
 return mav;
 }

 @RequestMapping // /springmvc/v2/members 호출시 해당 메서드가 호출됨
 public ModelAndView members() {
 List members = memberRepository.findAll();
 ModelAndView mav = new ModelAndView("members");
 mav.addObject("members", members);
 return mav;
 }
}



## 실용적인 방식의 MVC



@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {
 private MemberRepository memberRepository = MemberRepository.getInstance();
 @GetMapping("/new-form")
 public String newForm() {
 return "new-form";
 }
 @PostMapping("/save")
 public String save(
 @RequestParam("username") String username,
 @RequestParam("age") int age,
 Model model) {
 Member member = new Member(username, age);
 memberRepository.save(member);
 model.addAttribute("member", member);
 return "save-result";
 }
 @GetMapping
 public String members(Model model) {
 List members = memberRepository.findAll();
 model.addAttribute("members", members);
 return "members";
 }
}



@RequestParam 사용
스프링은 HTTP 요청 파라미터를 @RequestParam 으로 받을 수 있다.
@RequestParam("username") 은 request.getParameter("username") 와 거의 같은 코드라
생각하면 된다.
물론 GET 쿼리 파라미터, POST Form 방식을 모두 지원한다.



@RequestMapping @GetMapping, @PostMapping
@RequestMapping 은 URL만 매칭하는 것이 아니라, HTTP Method도 함께 구분할 수 있다.
 예를 들어서 URL이 /new-form 이고, HTTP Method가 GET인 경우를 모두 만족하는 매핑을 하려면
다음과 같이 처리하면 된다.

**@RequestMapping(value = "/new-form", method = RequestMethod.GET)**



이것을 @GetMapping , @PostMapping 으로 더 편리하게 사용할 수 있다.



참고로 Get, Post, Put, Delete, Patch 모두 애노테이션이 준비되어 있다.
@GetMapping 코드를 열어서 @RequestMapping 애노테이션을 내부에 가지고 있는 모습을 확인하자.



> 주의!
> Packaging는 War가 아니라 Jar를 선택해주세요. JSP를 사용하지 않기 때문에 Jar를 사용하는 것이
> 좋습니다. 앞으로 스프링 부트를 사용하면 이 방식을 주로 사용하게 됩니다.
> Jar를 사용하면 항상 내장 서버(톰캣등)을 사용하고, webapp 경로도 사용하지 않습니다. 내장 서버 사용에
> 최적화 되어 있는 기능입니다. 최근에는 주로 이 방식을 사용합니다.
> War를 사용하면 내장 서버도 사용가능 하지만, 주로 외부 서버에 배포하는 목적으로 사용합니다


스프링 부트에 Jar 를 사용하면 /resources/static/index.hml 위치에 index.html 파일을 두면
Welcome 페이지로 처리해준다. (스프링 부트가 지원하는 정적 컨텐츠 위치에 /index.html 이 있으면
된다



## SLF4J 라이브러리를 활용한 로그 사용 



**로그 선언**
private Logger log = LoggerFactory.getLogger(getClass());
private static final Logger log = LoggerFactory.getLogger(Xxx.class)
@Slf4j : 롬복 사용 가능

**로그 호출**
log.info("hello")
System.out.println("hello")
시스템 콘솔로 직접 출력하는 것 보다 로그를 사용하면 다음과 같은 장점이 있다. 실무에서는 항상 로그를
사용해야 한다





//@Slf4j
@RestController
public class LogTestController {
 private final Logger log = LoggerFactory.getLogger(getClass());
 @RequestMapping("/log-test")
 public String logTest() {
 String name = "Spring";

 log.trace("trace log={}", name);
 log.debug("debug log={}", name);
 log.info(" info log={}", name);
 log.warn(" warn log={}", name);
 log.error("error log={}", name);
 //로그를 사용하지 않아도 a+b 계산 로직이 먼저 실행됨, 이런 방식으로 사용하면 X
 log.debug("String concat log=" + name);
 return "ok";
 }
}



**@RestController**
**@Controller 는 반환 값이 String 이면 뷰 이름으로 인식된다. 그래서 뷰를 찾고 뷰가 랜더링 된다.**
@RestController 는 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력한다. 
**따라서 실행 결과로 ok 메세지를 받을 수 있다. @ResponseBody 와 관련이 있는데, 뒤에서 더 자세히**
**설명한다**



테스트
로그가 출력되는 포멧 확인
시간, 로그 레벨, 프로세스 ID, 쓰레드 명, 클래스명, 로그 메시지
로그 레벨 설정을 변경해서 출력 결과를 보자.
LEVEL: TRACE > DEBUG > INFO > WARN > ERROR

개발 서버는 debug 출력
운영 서버는 info 출력
@Slf4j 로 변경 //  private final Logger log = LoggerFactory.getLogger(getClass()); 해당 코드 생략 가능



```
#전체 로그 레벨 설정(기본 info)
logging.level.root=info

#hello.springmvc 패키지와 그 하위 로그 레벨 설정
logging.level.hello.springmvc=debug

```



올바른 로그 사용법
log.debug("data="+data)
로그 출력 레벨을 info로 설정해도 해당 코드에 있는 "data="+data가 실제 실행이 되어 버린다. 
결과적으로 문자 더하기 연산이 발생한다.

log.debug("data={}", data)
로그 출력 레벨을 info로 설정하면 아무일도 발생하지 않는다. 따라서 앞과 같은 의미없는 연산이
발생하지 않는다



로그 사용시 장점
쓰레드 정보, 클래스 이름 같은 부가 정보를 함께 볼 수 있고, 출력 모양을 조정할 수 있다.
로그 레벨에 따라 개발 서버에서는 모든 로그를 출력하고, 운영서버에서는 출력하지 않는 등 로그를 상황에
맞게 조절할 수 있다.



시스템 아웃 콘솔에만 출력하는 것이 아니라, 파일이나 네트워크 등, 로그를 별도의 위치에 남길 수 있다. 
특히 파일로 남길 때는 일별, 특정 용량에 따라 로그를 분할하는 것도 가능하다.
성능도 일반 System.out보다 좋다. (내부 버퍼링, 멀티 쓰레드 등등) 그래서 실무에서는 꼭 로그를
사용해야 한다

## 요청 매핑

매핑 정보(한번 더)
@RestController
@Controller 는 반환 값이 String 이면 뷰 이름으로 인식된다. 그래서 뷰를 찾고 뷰가 랜더링 된다.
@RestController 는 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력한다. 
따라서 실행 결과로 ok 메세지를 받을 수 있다. @ResponseBody 와 관련이 있는데, 뒤에서 더 자세히
설명한다



@RequestMapping("/hello-basic")
/hello-basic URL 호출이 오면 이 메서드가 실행되도록 매핑한다.
대부분의 속성을 배열[] 로 제공하므로 다중 설정이 가능하다. {"/hello-basic", "/hello-go"}



둘다 허용
다음 두가지 요청은 다른 URL이지만, 스프링은 다음 URL 요청들을 같은 요청으로 매핑한다.
매핑: /hello-basic
URL 요청: /hello-basic , /hello-basic/
HTTP 메서드
@RequestMapping 에 method 속성으로 HTTP 메서드를 지정하지 않으면 HTTP 메서드와 무관하게
호출된다.
모두 허용 GET, HEAD, POST, PUT, PATCH, DELETE



특정 메서드만 지정하고 호출하면 HTTP 405 상태코드(Method Not Allowed) 반환



**PathVariable**



PathVariable(경로 변수) 사용
/**

 * PathVariable 사용
 * 변수명이 같으면 생략 가능
 * @PathVariable("userId") String userId -> @PathVariable userId
    */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data) {
     log.info("mappingPath userId={}", data);
     return "ok";
    }





최근 HTTP API는 다음과 같이 리소스 경로에 식별자를 넣는 스타일을 선호한다.
/mapping/userA
/users/1
@RequestMapping 은 URL 경로를 템플릿화 할 수 있는데, @PathVariable 을 사용하면 매칭 되는 부분을
편리하게 조회할 수 있다.
@PathVariable 의 이름과 파라미터 이름이 같으면 생략할 수 있다



**PathVariable** 다중



특정 파라미터 조건 매핑
/**

 * 파라미터로 추가 매핑
 * params="mode",
 * params="!mode"
 * params="mode=debug"
 * params="mode!=debug" (! = )
 * params = {"mode=debug","data=good"}
    */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
     log.info("mappingParam");
     return "ok";
    }



**특정 헤더 조건 매핑**



특정 헤더 조건 매핑
/**

 * 특정 헤더로 추가 매핑
 * headers="mode",
 * headers="!mode"
 * headers="mode=debug"
 * headers="mode!=debug" (! = )
    */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
     log.info("mappingHeader");
     return "ok";
    }





미디어 타입 조건 매핑 - HTTP 요청 Content-Type, consume
/**

 * Content-Type 헤더 기반 추가 매핑 Media Type
 * consumes="application/json"
 * consumes="!application/json"
 * consumes="application/*"
 * consumes="*\/*"
 * MediaType.APPLICATION_JSON_VALUE
    */
    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    public String mappingConsumes() {
     log.info("mappingConsumes");
     return "ok";
    }



미디어 타입 조건 매핑 - HTTP 요청 Accept, produce
/**

 * Accept 헤더 기반 Media Type
 * produces = "text/html"
 * produces = "!text/html"
 * produces = "text/*"
 * produces = "*\/*"
    */
    @PostMapping(value = "/mapping-produce", produces = "text/html")
    public String mappingProduces() {
     log.info("mappingProduces");
     return "ok";
    }




HTTP 요청의 Accept 헤더를 기반으로 미디어 타입으로 매핑한다.
만약 맞지 않으면 HTTP 406 상태코드(Not Acceptable)을 반환한다.
예시)
produces = "text/plain"
produces = {"text/plain", "application/*"}
produces = MediaType.TEXT_PLAIN_VALUE

produces = "text/plain;charset=UTF-8"







회원 관리 API
회원 목록 조회: GET /users
회원 등록: POST /users
회원 조회: GET /users/{userId}
회원 수정: PATCH /users/{userId}
회원 삭제: DELETE /users/{userId}
MappingClassController
package hello.springmvc.basic.requestmapping;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {
 /**

 * GET /mapping/users
    */
     @GetMapping
     public String users() {
     return "get users";
     }
     /**
 * POST /mapping/users
    */
     @PostMapping
     public String addUser() {
     return "post user";
     }
     /**
 * GET /mapping/users/{userId}
    */
     @GetMapping("/{userId}")
     public String findUser(@PathVariable String userId) {
     return "get userId=" + userId;
     }
     /**
 * PATCH /mapping/users/{userId}
    */
     @PatchMapping("/{userId}")
     public String updateUser(@PathVariable String userId) {
     return "update userId=" + userId;
     }
     /**
 * DELETE /mapping/users/{userId}
    */
     @DeleteMapping("/{userId}")
     public String deleteUser(@PathVariable String userId) {
     return "delete userId=" + userId;
     }
    }
    /mapping :는 강의의 다른 예제들과 구분하기 위해 사용했다.
    @RequestMapping("/mapping/users")
    클래스 레벨에 매핑 정보를 두면 메서드 레벨에서 해당 정보를 조합해서 사용한다



## HTTP 기본, 헤더 조회

@Slf4j
@RestController
public class RequestHeaderController {
 @RequestMapping("/headers")
 public String headers(HttpServletRequest request,
 HttpServletResponse response,
 HttpMethod httpMethod,
 Locale locale,
 @RequestHeader MultiValueMap
headerMap,
 @RequestHeader("host") String host,
 @CookieValue(value = "myCookie", required = false)
String cookie
 ) {
 log.info("request={}", request);
 log.info("response={}", response);
 log.info("httpMethod={}", httpMethod); //GET , POST
 log.info("locale={}", locale); //ko_kr
 log.info("headerMap={}", headerMap); //key value 형태로 헤더값 담김
  log.info("header host={}", host); // localohst:89080
 log.info("myCookie={}", cookie); // null
 return "ok";
 }
}



HttpServletRequest
HttpServletResponse
HttpMethod : HTTP 메서드를 조회한다. org.springframework.http.HttpMethod
Locale : Locale 정보를 조회한다.
@RequestHeader MultiValueMap headerMap
모든 HTTP 헤더를 MultiValueMap 형식으로 조회한다.
@RequestHeader("host") String host
특정 HTTP 헤더를 조회한다.
속성
필수 값 여부: required
기본 값 속성: defaultValue
@CookieValue(value = "myCookie", required = false) String cookie
특정 쿠키를 조회한다.
속성
필수 값 여부: required
기본 값: defaultValu



MultiValueMap
MAP과 유사한데, 하나의 키에 여러 값을 받을 수 있다.
HTTP header, HTTP 쿼리 파라미터와 같이 하나의 키에 여러 값을 받을 때 사용한다. 
keyA=value1&keyA=value2



MultiValueMap map = new LinkedMultiValueMap();
map.add("keyA", "value1");
map.add("keyA", "value2");

//[value1,value2]
List values = map.get("keyA")



@ResponseBody
@RequestMapping("/request-param-map")
public String requestParamMap(@RequestParam Map paramMap) {
 log.info("username={}, age={}", paramMap.get("username"),
paramMap.get("age"));
 return "ok";
}



@RequestParam(required = false) Integer age

@RequestParam.required
파라미터 필수 여부
기본값이 파라미터 필수( true )이다.



@RequestParam(required = false, defaultValue = "-1") int age).



파라미터에 값이 없는 경우 defaultValue 를 사용하면 기본 값을 적용할 수 있다.
이미 기본 값이 있기 때문에 required 는 의미가 없다.
defaultValue 는 빈 문자의 경우에도 설정한 기본 값이 적용된다.


파라미터를 Map, MultiValueMap으로 조회할 수 있다.
@RequestParam Map , 
Map(key=value)
@RequestParam MultiValueMap
MultiValueMap(key=[value1, value2, ...] ex) (key=userIds, value=[id1, id2])
파라미터의 값이 1개가 확실하다면 Map 을 사용해도 되지만, 그렇지 않다면 MultiValueMap 을 사용하자.



## @ModelAttribute



HTTP 요청 파라미터 - @ModelAttribute
실제 개발을 하면 요청 파라미터를 받아서 필요한 객체를 만들고 그 객체에 값을 넣어주어야 한다. 보통
다음과 같이 코드를 작성할 것이다.
@RequestParam String username;
@RequestParam int age;
HelloData data = new HelloData();
data.setUsername(username);
data.setAge(age);
스프링은 이 과정을 완전히 자동화해주는 @ModelAttribute 기능을 제공한다



@ResponseBody
@RequestMapping("/model-attribute-v1")
public String modelAttributeV1(@ModelAttribute HelloData helloData) {
 log.info("username={}, age={}", helloData.getUsername(),
helloData.getAge());
 return "ok";
}



마치 마법처럼 HelloData 객체가 생성되고, 요청 파라미터의 값도 모두 들어가 있다.
스프링MVC는 @ModelAttribute 가 있으면 다음을 실행한다.
HelloData 객체를 생성한다.
요청 파라미터의 이름으로 HelloData 객체의 프로퍼티를 찾는다. 그리고 해당 프로퍼티의 setter를
호출해서 파라미터의 값을 입력(바인딩) 한다.
예) 파라미터 이름이 username 이면 setUsername() 메서드를 찾아서 호출하면서 값을 입력한다.
프로퍼티
객체에 getUsername() , setUsername() 메서드가 있으면, 이 객체는 username 이라는 프로퍼티를
가지고 있다.
username 프로퍼티의 값을 변경하면 setUsername() 이 호출되고, 조회하면 getUsername() 이
호출된다..



class HelloData {
 getUsername();
 setUsername();
}
바인딩 오류
age=abc 처럼 숫자가 들어가야 할 곳에 문자를 넣으면 BindException 이 발생한다. 이런 바인딩 오류를
처리하는 방법은 검증 부분에서 다룬다



@ModelAttribute 는 생략할 수 있다.
그런데 @RequestParam 도 생략할 수 있으니 혼란이 발생할 수 있다.
스프링은 해당 생략시 다음과 같은 규칙을 적용한다.
String , int , Integer 같은 단순 타입 = @RequestParam
나머지 = @ModelAttribute (argument resolver 로 지정해둔 타입 외)

즉 내가 만든 객체 같은것들은 생략할수있음



## HTTP 요청 메시지 - 단순 텍스트

먼저 가장 단순한 텍스트 메시지를 HTTP 메시지 바디에 담아서 전송하고, 읽어보자.
HTTP 메시지 바디의 데이터를 InputStream 을 사용해서 직접 읽을 수 있다



@Slf4j
@Controller
public class RequestBodyStringController {
 @PostMapping("/request-body-string-v1")
 public void requestBodyString(HttpServletRequest request,
HttpServletResponse response) throws IOException {
 ServletInputStream inputStream = request.getInputStream();
 String messageBody = StreamUtils.copyToString(inputStream,
StandardCharsets.UTF_8);
 log.info("messageBody={}", messageBody);
 response.getWriter().write("ok");
 }
}



/**

 * InputStream(Reader): HTTP 요청 메시지 바디의 내용을 직접 조회
 * OutputStream(Writer): HTTP 응답 메시지의 바디에 직접 결과 출력
    */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter)
    throws IOException {
     String messageBody = StreamUtils.copyToString(inputStream,
    StandardCharsets.UTF_8);
     log.info("messageBody={}", messageBody);
     responseWriter.write("ok");
    }
 * ​

**HTTP Entity 메세지 **컨버터**



/**

 * HttpEntity: HTTP header, body 정보를 편라하게 조회
 * - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
 * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
    *
 * 응답에서도 HttpEntity 사용 가능
 * - 메시지 바디 정보 직접 반환(view 조회X)
 * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
    */
     @PostMapping("/request-body-string-v3")
     public HttpEntity requestBodyStringV3(HttpEntity httpEntity) {
      String messageBody = httpEntity.getBody();
      log.info("messageBody={}", messageBody);
      return new HttpEntity<>("ok");
     }

**



스프링 MVC는 다음 파라미터를 지원한다.



**HttpEntity: HTTP header, body 정보를 편리하게 조회**
**메시지 바디 정보를 직접 조회**
요청 파라미터를 조회하는 기능과 관계 없음 @RequestParam X, @ModelAttribute X

**HttpEntity는 응답에도 사용 가능** // return new HttpEntity<>("ok")

메시지 바디 정보 직접 반환

헤더 정보 포함 가능

view 조회X

HttpEntity 를 상속받은 다음 객체들도 같은 기능을 제공한다.





RequestEntity
HttpMethod, url 정보가 추가, 요청에서 사용

ResponseEntity
**HTTP 상태 코드 설정 가능**, 응답에서 사용

return new ResponseEntity("Hello World", responseHeaders, HttpStatus.CREATED)



**RequestBody** **, ResponseBody**



@RequestBody - requestBodyStringV4
/**

 * @RequestBody
 * - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
 * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
    *
 * @ResponseBody
 * - 메시지 바디 정보 직접 반환(view 조회X)
 * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
    */
     @ResponseBody
     @PostMapping("/request-body-string-v4")
     public String requestBodyStringV4(@RequestBody String messageBody) {
      log.info("messageBody={}", messageBody);
      return "ok";
     }




**@RequestBody**

@RequestBody 를 사용하면 HTTP 메시지 바디 정보를 편리하게 조회할 수 있다. 참고로 헤더 정보가
필요하다면 HttpEntity 를 사용하거나 @RequestHeader 를 사용하면 된다.



이렇게 메시지 바디를 직접 조회하는 기능은 요청 파라미터를 조회하는 @RequestParam ,@ModelAttribute 와는 전혀 관계가 없다.

요청 파라미터 vs HTTP 메시지 바디



요청 파라미터를 조회하는 기능: @RequestParam , @ModelAttribute

HTTP 메시지 바디를 직접 조회하는 기능: @RequestBody





**@ResponseBody**

@ResponseBody 를 사용하면 응답 결과를 HTTP 메시지 바디에 직접 담아서 전달할 수 있다.
물론 이 경우에도 view를 사용하지 않는다



# HTTP 요청 메시지 - JSON



/**

 * {"username":"hello", "age":20}
 * content-type: application/json
    */
    @Slf4j
    @Controller
    public class RequestBodyJsonController {
     private ObjectMapper objectMapper = new ObjectMapper();
     @PostMapping("/request-body-json-v1")
     public void requestBodyJsonV1(HttpServletRequest request,
    HttpServletResponse response) throws IOException {
     ServletInputStream inputStream = request.getInputStream();
     String messageBody = StreamUtils.copyToString(inputStream,
    StandardCharsets.UTF_8);
     log.info("messageBody={}", messageBody);
     HelloData data = objectMapper.readValue(messageBody, HelloData.class);
     log.info("username={}, age={}", data.getUsername(), data.getAge());
     response.getWriter().write("ok");
     }
    }

@ResponseBody
@PostMapping("/request-body-json-v2")
public String requestBodyJsonV2(@RequestBody String messageBody) throws
IOException {
 HelloData data = objectMapper.readValue(messageBody, HelloData.class);
 log.info("username={}, age={}", data.getUsername(), data.getAge());
 return "ok";
}



이전에 학습했던 @RequestBody 를 사용해서 HTTP 메시지에서 데이터를 꺼내고 messageBody에
저장한다.

문자로 된 JSON 데이터인 messageBody 를 objectMapper 를 통해서 자바 객체로 변환한다.
문자로 변환하고 다시 json으로 변환하는 과정이 불편하다. @ModelAttribute처럼 한번에 객체로
변환할 수는 없을까?



@ResponseBody
@PostMapping("/request-body-json-v3")
public String requestBodyJsonV3(@RequestBody HelloData data) {
 log.info("username={}, age={}", data.getUsername(), data.getAge());
 return "ok";
}



@RequestBody 객체 파라미터
@RequestBody HelloData data
@RequestBody 에 직접 만든 객체를 지정할 수 있다.
HttpEntity , @RequestBody 를 사용하면 HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을 우리가
원하는 문자나 객체 등으로 변환해준다.
HTTP 메시지 컨버터는 문자 뿐만 아니라 JSON도 객체로 변환해주는데, 우리가 방금 V2에서 했던 작업을
대신 처리해준다.
자세한 내용은 뒤에 HTTP 메시지 컨버터에서 다룬다.
@RequestBody는 생략 불가능
@ModelAttribute 에서 학습한 내용을 떠올려보자.
스프링은 @ModelAttribute , @RequestParam 해당 생략시 다음과 같은 규칙을 적용한다.
String , int , Integer 같은 단순 타입 = @RequestParam
나머지 = @ModelAttribute (argument resolver 로 지정해둔 타입 외)
따라서 이 경우 HelloData에 @RequestBody 를 생략하면 @ModelAttribute 가 적용되어버린다.
HelloData data @ModelAttribute HelloData data
따라서 생략하면 HTTP 메시지 바디가 아니라 요청 파라미터를 처리하게 된다.





#HttpEntity 사용

@ResponseBody
@PostMapping("/request-body-json-v4")
public String requestBodyJsonV4(HttpEntity httpEntity) {
 HelloData data = httpEntity.getBody();
 log.info("username={}, age={}", data.getUsername(), data.getAge());
 return "ok";
}





/**

 * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림)
 * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (contenttype: application/json)
    *
 * @ResponseBody 적용
 * - 메시지 바디 정보 직접 반환(view 조회X)
 * - HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용
    (Accept: application/json)
     */

  ​



@ResponseBody
응답의 경우에도 @ResponseBody 를 사용하면 해당 객체를 HTTP 메시지 바디에 직접 넣어줄 수 있다.
물론 이 경우에도 HttpEntity 를 사용해도 된다.

@RequestBody 요청
JSON 요청 HTTP 메시지 컨버터 객체

@ResponseBody 응답
객체 HTTP 메시지 컨버터 JSON 응답





## HTTP 응답- 정적 리소스 , 뷰 템플릿







응답 데이터는 이미 앞에서 일부 다룬 내용들이지만, 응답 부분에 초점을 맞추어서 정리해보자.
스프링(서버)에서 응답 데이터를 만드는 방법은 크게 3가지이다.

정적 리소스
예) 웹 브라우저에 정적인 HTML, css, js을 제공할 때는, 정적 리소스를 사용한다.
뷰 템플릿 사용
예) 웹 브라우저에 동적인 HTML을 제공할 때는 뷰 템플릿을 사용한다.
HTTP 메시지 사용
HTTP API를 제공하는 경우에는 HTML이 아니라 데이터를 전달해야 하므로, HTTP 메시지 바디에
JSON 같은 형식으로 데이터를 실어 보낸다.



**정적 리소스**
스프링 부트는 클래스패스의 다음 디렉토리에 있는 정적 리소스를 제공한다.
/static , /public , /resources , /META-INF/resources

src/main/resources 는 리소스를 보관하는 곳이고, 또 클래스패스의 시작 경로이다.
따라서 다음 디렉토리에 리소스를 넣어두면 스프링 부트가 정적 리소스로 서비스를 제공한다.

**정적 리소스 경로**
src/main/resources/static

다음 경로에 파일이 들어있으면
src/main/resources/static/basic/hello-form.html

웹 브라우저에서 다음과 같이 실행하면 된다.
http://localhost:8080/basic/hello-form.html
정적 리소스는 해당 파일을 변경 없이 그대로 서비스하는 것이다



**뷰 템플릿**

뷰 템플릿을 거쳐서 HTML이 생성되고, 뷰가 응답을 만들어서 전달한다.
일반적으로 HTML을 동적으로 생성하는 용도로 사용하지만, 다른 것들도 가능하다. 뷰 템플릿이 만들 수
있는 것이라면 뭐든지 가능하다.
스프링 부트는 기본 뷰 템플릿 경로를 제공한다.
뷰 템플릿 경로
src/main/resources/templates



<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"> // 타임리프 선언
<head>
 <meta charset="UTF-8">
 <title>Title</title>
</head>
<body>
<p th:text="${data}">empty</p> // data 안에 든 값을 empty 부분과 치환!
</body>
</html>



@Controller
public class ResponseViewController {
 @RequestMapping("/response-view-v1")
 public ModelAndView responseViewV1() {
 ModelAndView mav = new ModelAndView("response/hello")
 .addObject("data", "hello!");
 return mav;
 }
 @RequestMapping("/response-view-v2")
 public String responseViewV2(Model model) {
 model.addAttribute("data", "hello!!");
 return "response/hello";
 }

 @RequestMapping("/response/hello")
 public void responseViewV3(Model model) {
 model.addAttribute("data", "hello!!");
 }
}



**String을 반환하는 경우 - View or HTTP 메시지**
@ResponseBody 가 없으면 response/hello 로 뷰 리졸버가 실행되어서 뷰를 찾고, 렌더링 한다.
@ResponseBody 가 있으면 뷰 리졸버를 실행하지 않고, HTTP 메시지 바디에 직접 response/hello 라는
문자가 입력된다.
여기서는 뷰의 논리 이름인 response/hello 를 반환하면 다음 경로의 뷰 템플릿이 렌더링 되는 것을
확인할 수 있다.
실행: templates/response/hello.html

**Void를 반환하는 경우**
@Controller 를 사용하고, HttpServletResponse , OutputStream(Writer) 같은 HTTP 메시지
바디를 처리하는 파라미터가 없으면 요청 URL을 참고해서 논리 뷰 이름으로 사용
요청 URL: /response/hello
실행: templates/response/hello.html
참고로 이 방식은 명시성이 너무 떨어지고 이렇게 딱 맞는 경우도 많이 없어서, 권장하지 않는다.



build.gradle
`implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'`
스프링 부트가 자동으로 ThymeleafViewResolver 와 필요한 스프링 빈들을 등록한다. 그리고 다음
설정도 사용한다. 이 설정은 기본 값 이기 때문에 변경이 필요할 때만 설정하면 된다.
application.properties
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html



## HTTP 응답 - HTTP API, 메시지 바디에 직접 입력

@Slf4j
@Controller
//@RestController
public class ResponseBodyController {
 @GetMapping("/response-body-string-v1")
 public void responseBodyV1(HttpServletResponse response) throws IOException 
{
 response.getWriter().write("ok");
 }
 /**

 * HttpEntity, ResponseEntity(Http Status 추가)
 * @return
    */
     @GetMapping("/response-body-string-v2")
     public ResponseEntity responseBodyV2() {
     return new ResponseEntity<>("ok", HttpStatus.OK);
     }

     @ResponseBody
     @GetMapping("/response-body-string-v3")
     public String responseBodyV3() {
     return "ok";
     }

     @GetMapping("/response-body-json-v1")
     public ResponseEntity responseBodyJsonV1() {
     HelloData helloData = new HelloData();
     helloData.setUsername("userA");
     helloData.setAge(20);
     return new ResponseEntity<>(helloData, HttpStatus.OK);
     }

     @ResponseStatus(HttpStatus.OK) // HTTP 응답코드 지정!
     @ResponseBody
     @GetMapping("/response-body-json-v2")
     public HelloData responseBodyJsonV2() {
     HelloData helloData = new HelloData();
     helloData.setUsername("userA");
     helloData.setAge(20);
     return helloData;
     }
    }



## ArgumentResolver



![RequestMappingHandlerAdapter](C:\Users\User\Desktop\Spring_2022\RequestMappingHandlerAdapter.JPG)생각해보면, 애노테이션 기반의 컨트롤러는 매우 다양한 파라미터를 사용할 수 있었다.
HttpServletRequest , Model 은 물론이고, @RequestParam , @ModelAttribute 같은 애노테이션
그리고 @RequestBody , HttpEntity 같은 HTTP 메시지를 처리하는 부분까지 매우 큰 유연함을
보여주었다.
이렇게 파라미터를 유연하게 처리할 수 있는 이유가 바로 ArgumentResolver 덕분이다.
애노테이션 기반 컨트롤러를 처리하는 **RequestMappingHandlerAdaptor** 는 바로 이 

**ArgumentResolver 를 호출**해서 컨트롤러(핸들러)가 필요로 하는 **다양한 파라미터의 값(객체)을 생성**한다. 
그리고 이렇게 파리미터의 값이 모두 준비되면 컨트롤러를 호출하면서 값을 넘겨준다.
스프링은 30개가 넘는 ArgumentResolver 를 기본으로 제공한다.
어떤 종류들이 있는지 살짝 코드로 확인만 해보자.



//1. 클라이언트가 RequestMapping에 명시되어있는 url 호출

//2. Argument Resolver에게 핸들러가필요로 하는 파라미터 지원되는지 물어봄

//3. 지원된다면 해당되는 파라미터로 넘겨줄데이터를 ArgumentResolver가 다 생성 준비

//4. 그때 핸들러어댑터가 컨트롤러를 호출 Argument를 통해 생성된 파라미터에 데이터 주입

동작 방식
ArgumentResolver 의 supportsParameter() 를 호출해서 해당 파라미터를 지원하는지 체크하고, 
지원하면 resolveArgument() 를 호출해서 실제 객체를 생성한다. 그리고 이렇게 생성된 객체가 컨트롤러
호출시 넘어가는 것이다.
그리고 원한다면 여러분이 직접 이 인터페이스를 **확장**해서 원하는 **ArgumentResolver** 를 만들 수도 있다. 
실제 확장하는 예제는 향후 로그인 처리에서 진행하겠다



**ReturnValueHandler**



HandlerMethodReturnValueHandler 를 줄여서 ReturnValueHandle 라 부른다.
ArgumentResolver 와 비슷한데, 이것은 응답 값을 변환하고 처리한다.
컨트롤러에서 String으로 뷰 이름을 반환해도, 동작하는 이유가 바로 ReturnValueHandler 덕분이다.
어떤 종류들이 있는지 살짝 코드로 확인만 해보자.
스프링은 10여개가 넘는 ReturnValueHandler 를 지원한다.
예) ModelAndView , @ResponseBody , HttpEntity , String



요청의 경우 @RequestBody 를 처리하는 ArgumentResolver 가 있고, HttpEntity 를 처리하는
ArgumentResolver 가 있다. 이 ArgumentResolver 들이 HTTP 메시지 컨버터를 사용해서 필요한
객체를 생성하는 것이다. (어떤 종류가 있는지 코드로 살짝 확인해보자)



응답의 경우 @ResponseBody 와 HttpEntity 를 처리하는 ReturnValueHandler 가 있다. 그리고
여기에서 HTTP 메시지 컨버터를 호출해서 응답 결과를 만든다.
스프링 MVC는 @RequestBody @ResponseBody 가 있으면

RequestResponseBodyMethodProcessor (ArgumentResolver)
HttpEntity 가 있으면 HttpEntityMethodProcessor (ArgumentResolver)를 사용한다

확장
스프링은 다음을 모두 인터페이스로 제공한다. 따라서 필요하면 언제든지 기능을 확장할 수 있다.
HandlerMethodArgumentResolver
HandlerMethodReturnValueHandler
HttpMessageConverter

스프링이 필요한 대부분의 기능을 제공하기 때문에 실제 기능을 확장할 일이 많지는 않다. 기능 확장은
**WebMvcConfigurer** 를 상속 받아서 스프링 빈으로 등록하면 된다. 실제 자주 사용하지는 않으니 실제 기능
확장이 필요할 때 **WebMvcConfigurer** 를 검색해보자.



@Bean
public WebMvcConfigurer webMvcConfigurer() {
 return new WebMvcConfigurer() {
 @Override
 public void addArgumentResolvers(List
resolvers) {
 //...
 }
 @Override
 public void extendMessageConverters(List>
converters) {
 //...
 }
 };
}