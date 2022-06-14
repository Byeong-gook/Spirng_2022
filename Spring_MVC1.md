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