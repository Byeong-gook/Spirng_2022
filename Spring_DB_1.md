**H2 데이터베이스 설정**

H2 데이터베이스는 개발이나 테스트 용도로 사용하기 좋은 가볍고 편리한 DB이다. 그리고 SQL을 실행할
수 있는 웹 화면을 제공한다



![External Libraries](C:\Users\User\Desktop\Spring_2022\External Libraries.JPG)



h2 데이터베이스 버전은 스프링 부트 버전에 맞춘다. - 현재 1.4.200 버전을 다운로드 받으면 된다.
다음 링크에 가면 다양한 H2 다운로드 버전을 확인할 수 있다.

https://www.h2database.com/html/download-archive.html

**MAC, 리눅스 사용자**

권한 주기: chmod 755 h2.sh
실행: ./h2.sh

**윈도우 사용자**

실행: h2.bat



**데이터베이스 파일 생성 방법**

사용자명은 sa 입력
JDBC URL에 다음 입력, 

jdbc:h2:~/test (최초 한번) 이 경우 연결 시험 을 호출하면 오류가 발생한다. 연결 을 직접
눌러주어야 한다.

~/test.mv.db 파일 생성 확인
이후부터는 jdbc:h2:tcp://localhost/~/test 이렇게 접속



create table member (
 member_id varchar(10),
 money integer not null default 0,
 primary key (member_id)
);

insert into member(member_id, money) values ('hi1',10000);
insert into member(member_id, money) values ('hi2',20000)



## JDBC 이해

![Database](C:\Users\User\Desktop\Spring_2022\Database.JPG)

1. 커넥션 연결: 주로 TCP/IP를 사용해서 커넥션을 연결한다.
2. SQL 전달: 애플리케이션 서버는 DB가 이해할 수 있는 SQL을 연결된 커넥션을 통해 DB에 전달한다.
3. 결과 응답: DB는 전달된 SQL을 수행하고 그 결과를 응답한다. 애플리케이션 서버는 응답 결과를 활용한다



애플리케이션 서버와 DB - DB 변경![database connection](C:\Users\User\Desktop\Spring_2022\database connection.JPG)문제는 각각의 데이터베이스마다 커넥션을 연결하는 방법, SQL을 전달하는 방법, 그리고 결과를 응답 받는
방법이 모두 다르다는 점이다.
참고로 관계형 데이터베이스는 수십개가 있다.
여기에는 2가지 큰 문제가 있다.

1. 데이터베이스를 다른 종류의 데이터베이스로 변경하면 애플리케이션 서버에 개발된 데이터베이스 사용
  코드도 함께 변경해야 한다.
2. 개발자가 각각의 데이터베이스마다 커넥션 연결, SQL 전달, 그리고 그 결과를 응답 받는 방법을 새로
  학습해야 한다.
  이런 문제를 해결하기 위해 JDBC라는 자바 표준이 등장한다





## JDBC 표준 인터페이스

JDBC(Java Database Connectivity)는 자바에서 데이터베이스에 접속할 수 있도록 하는 자바 API다. 
JDBC는 데이터베이스에서 자료를 쿼리하거나 업데이트하는 방법을 제공한다.

![JDBC BASIC](C:\Users\User\Desktop\Spring_2022\JDBC BASIC.JPG)



대표적으로 다음 3가지 기능을 표준 인터페이스로 정의해서 제공한다.
java.sql.Connection - 연결
java.sql.Statement - SQL을 담은 내용
java.sql.ResultSet - SQL 요청 응답
자바는 이렇게 표준 인터페이스를 정의해두었다. 이제부터 개발자는 이 표준 인터페이스만 사용해서
개발하면 된다.

그런데 인터페이스만 있다고해서 기능이 동작하지는 않는다. 이 JDBC 인터페이스를 각각의 DB 벤더
(회사)에서 자신의 DB에 맞도록 구현해서 라이브러리로 제공하는데, 이것을 JDBC 드라이버라 한다. 예를
들어서 MySQL DB에 접근할 수 있는 것은 MySQL JDBC 드라이버라 하고, Oracle DB에 접근할 수
있는 것은 Oracle JDBC 드라이버라 한다



JDBC의 등장으로 다음 2가지 문제가 해결되었다.

1. 데이터베이스를 다른 종류의 데이터베이스로 변경하면 애플리케이션 서버의 데이터베이스 사용 코드도
  함께 변경해야하는 문제
  애플리케이션 로직은 이제 JDBC 표준 인터페이스에만 의존한다. 따라서 데이터베이스를 다른 종류의
  데이터베이스로 변경하고 싶으면 JDBC 구현 라이브러리만 변경하면 된다. 따라서 다른 종류의
  데이터베이스로 변경해도 애플리케이션 서버의 사용 코드를 그대로 유지할 수 있다.
2. 개발자가 각각의 데이터베이스마다 커넥션 연결, SQL 전달, 그리고 그 결과를 응답 받는 방법을 새로
  학습해야하는 문제
  개발자는 JDBC 표준 인터페이스 사용법만 학습하면 된다. 한번 배워두면 수십개의 데이터베이스에
  모두 동일하게 적용할 수 있다.
  참고 - 표준화의 한계
> JDBC의 등장으로 많은 것이 편리해졌지만, 각각의 데이터베이스마다 SQL, 데이터타입 등의 일부 사용법
> 다르다. ANSI SQL이라는 표준이 있기는 하지만 일반적인 부분만 공통화했기 때문에 한계가 있다. 
> 대표적으로 실무에서 기본으로 사용하는 페이징 SQL은 각각의 데이터베이스마다 사용법이 다르다.
> 결국 데이터베이스를 변경하면 JDBC 코드는 변경하지 않아도 되지만 SQL은 해당 데이터베이스에 맞도록
> 변경해야한다.
> 참고로 JPA(Java Persistence API)를 사용하면 이렇게 각각의 데이터베이스마다 다른 SQL을 정의해야
> 하는 문제도 많은 부분 해결할 수 있다.



## JDBC와 최신 데이터 접근 기술



JDBC는 1997년에 출시될 정도로 오래된 기술이고, 사용하는 방법도 복잡하다. 그래서 최근에는 JDBC를
직접 사용하기 보다는 JDBC를 편리하게 사용하는 다양한 기술이 존재한다. 

대표적으로 **SQL Mapper**와 **ORM** 기술로 나눌 수 있다.



![SQL Mapper](C:\Users\User\Desktop\Spring_2022\SQL Mapper.JPG)**SQL Mapper**

장점: JDBC를 편리하게 사용하도록 도와준다.
SQL 응답 결과를 객체로 편리하게 변환해준다.
JDBC의 반복 코드를 제거해준다.

단점: 개발자가 SQL을 직접 작성해야한다.

**대표 기술: 스프링 JdbcTemplate, MyBatis**



**ORM 기술**

![ORM](C:\Users\User\Desktop\Spring_2022\ORM.JPG)



ORM 기술
**ORM은 객체를 관계형 데이터베이스 테이블과 매핑해주는 기술**이다. 이 기술 덕분에 개발자는
반복적인 SQL을 직접 작성하지 않고, **ORM 기술이 개발자 대신에 SQL을 동적으로 만들어**
실행해준다. 추가로 각각의 데이터베이스마다 **다른 SQL을 사용하는 문제도 중간에서 해결**해준다.

**대표 기술: JPA, 하이버네이트, 이클립스링크**

JPA는 자바 진영의 ORM 표준 인터페이스이고, 이것을 구현한 것으로 하이버네이트와 이클립스 링크
등의 구현 기술이 있다.



**SQL Mapper vs ORM 기술**
SQL Mapper와 ORM 기술 둘다 각각 장단점이 있다.

쉽게 설명하자면 SQL Mapper는 SQL만 직접 작성하면 나머지 번거로운 일은 SQL Mapper가 대신
해결해준다.

 SQL Mapper는 SQL만 작성할 줄 알면 금방 배워서 사용할 수 있다.

ORM기술은 SQL 자체를 작성하지 않아도 되어서 개발 생산성이 매우 높아진다. 

편리한 반면에 쉬운
기술은 아니므로 실무에서 사용하려면 깊이있게 학습해야 한다.

강의 뒷 부분에서 다양한 데이터 접근 기술을 설명하는데, 그때 SQL Mapper인 JdbcTemplate과
MyBatis를 학습하고 코드로 활용해본다. 

그리고 ORM의 대표 기술인 JPA도 학습하고 코드로 활용해본다. 이 과정을 통해서 각각의
기술들의 장단점을 파악하고, 어떤 기술을 언제 사용해야 하는지 자연스럽게 이해하게 될 것이다.

> 중요
> 이런 기술들도 내부에서는 모두 JDBC를 사용한다. 따라서 JDBC를 직접 사용하지는 않더라도, JDBC가
> 어떻게 동작하는지 기본 원리를 알아두어야 한다. 그래야 해당 기술들을 더 깊이있게 이해할 수 있고, 
> 무엇보다 문제가 발생했을 때 근본적인 문제를 찾아서 해결할 수 있다 JDBC는 자바 개발자라면 꼭
> 알아두어야 하는 필수 기본 기술이다.



## 데이터베이스 연결

```
package hello.jdbc.connection;

//객체 생성을 못하게 하기위해 abstract로 선언
public abstract class ConnectionConst {
    public static final String URL = "jdbc:h2:tcp://localhost/~/test";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";

}
```

```
@Slf4j
public class DBConnectionUtil {

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(ConnectionConst.URL, ConnectionConst.USERNAME, ConnectionConst.PASSWORD);
            log.info("get connection={}, class={}", connection, connection.getClass());
            return connection;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
```

데이터베이스에 연결하려면 JDBC가 제공하는 DriverManager.getConnection(..) 를 사용하면 된다. 
이렇게 하면 라이브러리에 있는 데이터베이스 드라이버를 찾아서 해당 드라이버가 제공하는 커넥션을
반환해준다.

 여기서는 H2 데이터베이스 드라이버가 작동해서 실제 데이터베이스와 커넥션을 맺고 그
결과를 반환해준다****

**테스트 코드 작성**

```
@Test
void connection() {
    Connection connection = DBConnectionUtil.getConnection();

    Assertions.assertThat(connection).isNotNull();
    
}
```

DBConnectionUtil - get connection=conn0: url=jdbc:h2:tcp://localhost/~/test 
user=SA, class=class org.h2.jdbc.JdbcConnection



실행 결과를 보면 class=class org.h2.jdbc.JdbcConnection 부분을 확인할 수 있다. 이것이 바로
H2 데이터베이스 드라이버가 제공하는 H2 전용 커넥션이다. 물론 이 커넥션은 JDBC 표준 커넥션
인터페이스인 java.sql.Connection 인터페이스를 구현하고 있다



JDBC는 java.sql.Connection 표준 커넥션 인터페이스를 정의한다.
H2 데이터베이스 드라이버는 JDBC Connection 인터페이스를 구현한다.



JDBC가 제공하는 DriverManager 는 라이브러리에 등록된 DB 드라이버들을 관리하고, 커넥션을
획득하는 기능을 제공한다.

1. 애플리케이션 로직에서 커넥션이 필요하면 DriverManager.getConnection() 을 호출한다.
2. DriverManager 는 라이브러리에 등록된 드라이버 목록을 자동으로 인식한다. 이 드라이버들에게
  순서대로 다음 정보를 넘겨서 커넥션을 획득할 수 있는지 확인한다.
  URL: 예) jdbc:h2:tcp://localhost/~/test
  이름, 비밀번호 등 접속에 필요한 추가 정보
  여기서 각각의 드라이버는 URL 정보를 체크해서 본인이 처리할 수 있는 요청인지 확인한다. 예를
  들어서 URL이 jdbc:h2 로 시작하면 이것은 h2 데이터베이스에 접근하기 위한 규칙이다. 따라서 H2 
  드라이버는 본인이 처리할 수 있으므로 실제 데이터베이스에 연결해서 커넥션을 획득하고 이 커넥션을
  클라이언트에 반환한다. 반면에 URL이 jdbc:h2 로 시작했는데 MySQL 드라이버가 먼저 실행되면
  이 경우 본인이 처리할 수 없다는 결과를 반환하게 되고, 다음 드라이버에게 순서가 넘어간다.
3. 이렇게 찾은 커넥션 구현체가 클라이언트에 반환된다.
  우리는 H2 데이터베이스 드라이버만 라이브러리에 등록했기 때문에 H2 드라이버가 제공하는 H2 
  커넥션을 제공받는다. 물론 이 H2 커넥션은 JDBC가 제공하는 java.sql.Connection 인터페이스를
  구현하고 있다



H2 데이터베이스 드라이버 라이브러리
runtimeOnly 'com.h2database:h2' //h2-x.x.xxx.jar



## JDBC 개발 - 등록

create table member (
 member_id varchar(10),
 money integer not null default 0,
 primary key (member_id)
);



```
@Slf4j
public class MemberRepositoryV0 {

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values (?, ?)";

        Connection con = null; // 연결
        PreparedStatement pstmt = null; // 쿼리 날리기

        try {
            con = getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate(); Statement를 통해 준비된 SQL을 커넥션을 통해 실제 데이터베이스에 전달한다.
            return member;

        } catch (SQLException e){
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, null)
        }
    }


    private void close (Connection con, Statement stmt, ResultSet rs){
        //그냥 Statement는 SQL을 바로 던지는것이고 PreparedStatement는 파라미터를 바인딩해서 SQL을 던질수 있음
        if(stmt != null){
            try {
                stmt.close(); //Exception
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
        if(con != null) {

            try {
                con.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
    }
    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
```

**커넥션 획득**
getConnection() : 이전에 만들어둔 DBConnectionUtil 를 통해서 데이터베이스 커넥션을 획득한다.

save() - SQL 전달

sql : 데이터베이스에 전달할 SQL을 정의한다. 여기서는 데이터를 등록해야 하므로 insert sql 을
준비했다.
con.prepareStatement(sql) : 데이터베이스에 전달할 SQL과 파라미터로 전달할 데이터들을
준비한다.
sql : insert into member(member_id, money) values(?, ?)"
pstmt.setString(1, member.getMemberId()) : SQL의 첫번째 ? 에 값을 지정한다. 문자이므로
setString 을 사용한다.
pstmt.setInt(2, member.getMoney()) : SQL의 두번째 ? 에 값을 지정한다. Int 형 숫자이므로
setInt 를 지정한다.

pstmt.executeUpdate() : Statement 를 통해 준비된 SQL을 커넥션을 통해 실제 데이터베이스에
전달한다. 참고로 executeUpdate() 은 int 를 반환하는데 **영****향받은 DB row 수를 반환**한다. 여기서는
하나의 row를 등록했으므로 1을 반환한다.



**리소스 정리**
쿼리를 실행하고 나면 리소스를 정리해야 한다. 여기서는 Connection , PreparedStatement 를
사용했다. 리소스를 정리할 때는 항상 역순으로 해야한다. Connection 을 먼저 획득하고 Connection 을
통해 PreparedStatement 를 만들었기 때문에 리소스를 반환할 때는 PreparedStatement 를 먼저
종료하고, 그 다음에 Connection 을 종료하면 된다. 참고로 여기서 사용하지 않은 ResultSet 은 결과를
조회할 때 사용한다. 조금 뒤에 조회 부분에서 알아보자.





> 주의
> 리소스 정리는 꼭! 해주어야 한다. 따라서 예외가 발생하든, 하지 않든 항상 수행되어야 하므로 finally
> 구문에 주의해서 작성해야한다. 만약 이 부분을 놓치게 되면 커넥션이 끊어지지 않고 계속 유지되는 문제가
> 발생할 수 있다. 이런 것을 리소스 누수라고 하는데, 결과적으로 커넥션 부족으로 장애가 발생할 수 있다.
> 참고
> PreparedStatement 는 Statement 의 자식 타입인데, ? 를 통한 파라미터 바인딩을 가능하게 해준다.
> 참고로 SQL Injection 공격을 예방하려면 PreparedStatement 를 통한 파라미터 바인딩 방식을
> 사용해야 한다.



## JDBC 개발 - 조회

```
public Member findById(String memberId) throws SQLException {
    String sql = "select * from member where member_id = ?";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
        con = getConnection();
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1, memberId);
        rs = pstmt.executeQuery();
        if (rs.next()) {
            Member member = new Member();
            member.setMemberId(rs.getString("member_id"));
            member.setMoney(rs.getInt("money"));
            return member;
        } else {
            throw new NoSuchElementException("member not found memberId=" +
                    memberId);
        }
    } catch (SQLException e) {
        log.error("db error", e);
        throw e;
    } finally {
        close(con, pstmt, rs);
    }
}
```

**findById() - 쿼리 실행**
sql : 데이터 조회를 위한 select SQL을 준비한다.
rs = pstmt.executeQuery() 데이터를 변경할 때는 executeUpdate() 를 사용하지만, 데이터를
조회할 때는 executeQuery() 를 사용한다. executeQuery() 는 결과를 ResultSet 에 담아서 반환한다.



**ResultSet**
ResultSet 은 다음과 같이 생긴 데이터 구조이다. 보통 select 쿼리의 결과가 순서대로 들어간다. 
예를 들어서 select member_id, money 라고 지정하면 member_id , money 라는 이름으로
데이터가 저장된다.
참고로 select * 을 사용하면 테이블의 모든 컬럼을 다 지정한다.
ResultSet 내부에 있는 **커서( cursor )를 이동**해서 다음 데이터를 조회할 수 있다.
**rs.next()** : 이것을 호출하면 커서가 다음으로 이동한다. 참고로 최초의 커서는 데이터를 가리키고 있지
않기 때문에 **rs.next() 를 최초 한번은 호출해야 데이터를 조회**할 수 있다.
rs.next() 의 결과가 **true 면 커서의 이동 결과 데이터가 있다는 뜻**이다.
rs.next() 의 결과가 **false 면 더이상 커서가 가리키는 데이터가 없다는 뜻**이다.
rs.getString("member_id") : 현재 커서가 가리키고 있는 위치의 member_id 데이터를 String
타입으로 반환한다.
rs.getInt("money") : 현재 커서가 가리키고 있는 위치의 money 데이터를 int 타입으로 반환한다.



![ResultSet](C:\Users\User\Desktop\Spring_2022\ResultSet.JPG)



참고로 이 ResultSet 의 결과 예시는 회원이 2명 조회되는 경우이다.

1-1 에서 rs.next() 를 호출한다.
1-2 의 결과로 cursor 가 다음으로 이동한다. 이 경우 cursor 가 가리키는 데이터가 있으므로 true 를
반환한다.
2-1 에서 rs.next() 를 호출한다.
2-2 의 결과로 cursor 가 다음으로 이동한다. 이 경우 cursor 가 가리키는 데이터가 있으므로 true 를
반환한다.
3-1 에서 rs.next() 를 호출한다.
3-2 의 결과로 cursor 가 다음으로 이동한다. 이 경우 cursor 가 가리키는 데이터가 없으므로 false 를
반환한다.

findById() 에서는 회원 하나를 조회하는 것이 목적이다. 따라서 **조회 결과가 항상 1건이므로 while**
**대신에 if 를 사용**한다. 다음 SQL을 보면 PK인 member_id 를 항상 지정하는 것을 확인할 수 있다.
SQL: select * from member where member_id = ?



## JDBC 개발 - 수정, 삭제

```
public void update(String memberId, int money) throws SQLException {
    String sql = "update member set money=? where member_id=?";

    Connection con = null;
    PreparedStatement pstmt = null;

    try {
        con = getConnection();
        pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, money);
        pstmt.setString(2, memberId);
        int resultSize = pstmt.executeUpdate();
        log.info("resultSize={}", resultSize);
    } catch (SQLException e) {
        log.error("db error", e);
        throw e;
    } finally {
        close(con, pstmt, null);
    }
}
```

```
//delete
repository.delete(member.getMemberId());
assertThatThrownBy(() -> repository.findById(member.getMemberId()))
        .isInstanceOf(NoSuchElementException.class);
```

회원을 삭제한 다음 findById() 를 통해서 조회한다. 회원이 없기 때문에 NoSuchElementException 이
발생한다. assertThatThrownBy 는 해당 예외가 발생해야 검증에 성공한다.

> 참고
> 마지막에 회원을 삭제하기 때문에 테스트가 정상 수행되면, 이제부터는 같은 테스트를 반복해서 실행할 수
> 있다. 물론 테스트 중간에 오류가 발생해서 삭제 로직을 수행할 수 없다면 테스트를 반복해서 실행할 수
> 없다.
> 트랜잭션을 활용하면 이 문제를 깔끔하게 해결할 수 있는데, 자세한 내용은 뒤에서 설명한다



## 커넥션 풀 이해

**데이터베이스 커넥션을 매번 획득**

![ConnectionPool](C:\Users\User\Desktop\Spring_2022\ConnectionPool.JPG)

1. 애플리케이션 로직은 DB 드라이버를 통해 커넥션을 조회한다.
2. DB 드라이버는 DB와 TCP/IP 커넥션을 연결한다. 물론 이 과정에서 3 way handshake 같은 TCP/IP
  연결을 위한 네트워크 동작이 발생한다.
3. DB 드라이버는 TCP/IP 커넥션이 연결되면 ID, PW와 기타 부가정보를 DB에 전달한다.
4. DB는 ID, PW를 통해 내부 인증을 완료하고, 내부에 DB 세션을 생성한다.
5. DB는 커넥션 생성이 완료되었다는 응답을 보낸다.
6. DB 드라이버는 커넥션 객체를 생성해서 클라이언트에 반환한다





이렇게 커넥션을 새로 만드는 것은 과정도 복잡하고 시간도 많이 많이 소모되는 일이다.
DB는 물론이고 애플리케이션 서버에서도 TCP/IP 커넥션을 새로 생성하기 위한 리소스를 매번 사용해야
한다.
진짜 문제는 고객이 애플리케이션을 사용할 때, SQL을 실행하는 시간 뿐만 아니라 커넥션을 새로 만드는
시간이 추가되기 때문에 결과적으로 응답 속도에 영향을 준다. 이것은 사용자에게 좋지 않은 경험을 줄 수
있다.





> 참고: 데이터베이스마다 커넥션을 생성하는 시간은 다르다. 시스템 상황마다 다르지만 MySQL 계열은 수
> ms(밀리초) 정도로 매우 빨리 커넥션을 확보할 수 있다. 반면에 수십 밀리초 이상 걸리는 데이터베이스들도
> 있다.
>
> 
>
> 이런 문제를 한번에 해결하는 아이디어가 바로 커넥션을 미리 생성해두고 사용하는 커넥션 풀이라는
> 방법이다.
> 커넥션 풀은 이름 그대로 커넥션을 관리하는 풀(수영장 풀을 상상하면 된다.)이다
>
> 



애플리케이션을 시작하는 시점에 커넥션 풀은 필요한 만큼 커넥션을 미리 확보해서 풀에 보관한다. 보통
얼마나 보관할 지는 서비스의 특징과 서버 스펙에 따라 다르지만 기본값은 보통 10개이다.



커넥션 풀에 들어 있는 커넥션은 TCP/IP로 DB와 커넥션이 연결되어 있는 상태이기 때문에 언제든지 즉시
SQL을 DB에 전달할 수 있다



애플리케이션 로직은 커넥션 풀에서 받은 커넥션을 사용해서 SQL을 데이터베이스에 전달하고 그 결과를
받아서 처리한다.

**커넥션을 모두 사용하고 나면 이제는 커넥션을 종료**하는 것이 아니라, 다음에 **다시 사용할 수 있도록 해당**
**커넥션을 그대로 커넥션 풀에 반환**하면 된다. 여기서 주의할 점은 커넥션을 종료하는 것이 아니라 커넥션이
살아있는 상태로 커넥션 풀에 반환해야 한다는 것이다.



**정리**
적절한 커넥션 풀 숫자는 서비스의 특징과 애플리케이션 서버 스펙, DB 서버 스펙에 따라 다르기 때문에
성능 테스트를 통해서 정해야 한다. (보통 기본값은 10 정도)

커넥션 풀은 서버당 최대 커넥션 수를 제한할 수 있다. 따라서 DB에 무한정 연결이 생성되는 것을
막아주어서 DB를 보호하는 효과도 있다.

이런 커넥션 풀은 얻는 이점이 매우 크기 때문에 실무에서는 항상 기본으로 사용한다.

커넥션 풀은 개념적으로 단순해서 직접 구현할 수도 있지만, 사용도 편리하고 성능도 뛰어난 오픈소스
커넥션 풀이 많기 때문에 오픈소스를 사용하는 것이 좋다.

대표적인 커넥션 풀 오픈소스는 **commons-dbcp2 , tomcat-jdbc pool , HikariCP** 등이 있다.

성능과 사용의 편리함 측면에서 최근에는 **hikariCP** 를 주로 사용한다. 스프링 부트 2.0 부터는 기본
커넥션 풀로 hikariCP 를 제공한다. 성능, 사용의 편리함, 안전성 측면에서 이미 검증이 되었기 때문에
커넥션 풀을 사용할 때는 고민할 것 없이 hikariCP 를 사용하면 된다. 실무에서도 레거시 프로젝트가 아닌
이상 대부분 hikariCP 를 사용한다.



## DataSource 이해



커넥션을 얻는 방법은 앞서 학습한 JDBC DriverManager 를 직접 사용하거나, 커넥션 풀을 사용하는 등
다양한 방법이 존재한다



우리가 앞서 JDBC로 개발한 애플리케이션 처럼 DriverManager 를 통해서 커넥션을 획득하다가, 커넥션
풀을 사용하는 방법으로 변경하려면 어떻게 해야할까



예를 들어서 애플리케이션 로직에서 DriverManager 를 사용해서 커넥션을 획득하다가 HikariCP 같은
**커넥션 풀을 사용하도록 변경**하면 커넥션을 획득하는 **애플리케이션 코드도 함께 변경**해야 한다. 의존관계가
DriverManager 에서 HikariCP 로 변경되기 때문이다. 물론 둘의 사용법도 조금씩 다를 것이다



**커넥션을 획득하는 방법을 추상화**



![DataSource(Connection Abstract)](C:\Users\User\Desktop\Spring_2022\DataSource(Connection Abstract).JPG)



자바에서는 이런 문제를 해결하기 위해 javax.sql.DataSource 라는 인터페이스를 제공한다.
DataSource 는 **커넥션을 획득하는 방법을 추상화 하는 인터페이스**이다.
이 인터페이스의 핵심 기능은 커넥션 조회 하나이다. (다른 일부 기능도 있지만 크게 중요하지 않다.



**DataSource 핵심 기능만 축약**

```
public interface DataSource {
    Connection getConnection() throws SQLException;
}
```

대부분의 커넥션 풀은 DataSource 인터페이스를 이미 구현해두었다. 따라서 개발자는 DBCP2 커넥션 풀 ,
HikariCP 커넥션 풀 의 코드를 직접 의존하는 것이 아니라 DataSource 인터페이스에만 의존하도록
애플리케이션 로직을 작성하면 된다. 

커넥션 풀 구현 기술을 변경하고 싶으면 해당 **구현체로 갈아끼우기만 하면 된다**.

**DriverManager 는 DataSource 인터페이스를 사용하지 않는다.** 따라서 DriverManager 는 직접
사용해야 한다. 따라서 DriverManager 를 사용하다가 DataSource 기반의 커넥션 풀을 사용하도록
변경하면 관련 코드를 다 고쳐야 한다. 이런 문제를 해결하기 위해 스프링은 DriverManager 도
DataSource 를 통해서 사용할 수 있도록 **DriverManagerDataSource 라는 DataSource 를 구현한**
**클래스를 제공**한다.

자바는 DataSource 를 통해 커넥션을 획득하는 방법을 추상화했다. 이제 애플리케이션 로직은
DataSource 인터페이스에만 의존하면 된다. 덕분에 DriverManagerDataSource 를 통해서
DriverManager 를 사용하다가 커넥션 풀을 사용하도록 코드를 변경해도 애플리케이션 로직은 변경하지
않아도 된다.



## DataSource 예제 - DriverManager

먼저 기존에 개발했던 DriverManager 를 통해서 커넥션을 획득하는 방법을 확인해보자.



```
@Test
void dataSourceDriverManager() throws SQLException {
    //DriverManagerDataSource - 항상 새로운 커넥션을 획득
    DriverManagerDataSource dataSource = new DriverManagerDataSource(ConnectionConst.URL, ConnectionConst.USERNAME, ConnectionConst.PASSWORD);
    useDataSource(dataSource);
}

private void useDataSource(DataSource dataSource) throws SQLException {
    Connection con1 = dataSource.getConnection();
    Connection con2 = dataSource.getConnection();

    log.info("connection={}, class={}", con1, con1.getClass());
    log.info("connection={}, class={}", con2, con2.getClass());
}
```



**파라미터 차이**
기존 DriverManager 를 통해서 커넥션을 획득하는 방법과 DataSource 를 통해서 커넥션을 획득하는
방법에는 큰 차이가 있다.

```
DriverManager
        DriverManager.getConnection(URL, USERNAME, PASSWORD)
        DriverManager.getConnection(URL, USERNAME, PASSWORD)
        DataSource
        void dataSourceDriverManager() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL,
        USERNAME, PASSWORD);
        useDataSource(dataSource);
        }
private void useDataSource(DataSource dataSource) throws SQLException {
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();
        log.info("connection={}, class={}", con1, con1.getClass());
        log.info("connection={}, class={}", con2, con2.getClass());
        }
```



DriverManager 는 커넥션을 획득할 때 마다 URL , USERNAME , PASSWORD 같은 파라미터를 계속
전달해야 한다. 반면에 DataSource 를 사용하는 방식은 처음 객체를 생성할 때만 필요한 파리미터를
넘겨두고, 커넥션을 획득할 때는 단순히 dataSource.getConnection() 만 호출하면 된다.

**설정과 사용의 분리**
설정: DataSource 를 만들고 필요한 속성들을 사용해서 URL , USERNAME , PASSWORD 같은 부분을
입력하는 것을 말한다. 이렇게 설정과 관련된 속성들은 한 곳에 있는 것이 향후 변경에 더 유연하게 대처할
수 있다.

사용: 설정은 신경쓰지 않고, DataSource 의 getConnection() 만 호출해서 사용하면 된다.

**설정과 사용의 분리 설명**

이 부분이 작아보이지만 큰 차이를 만들어내는데, 필요한 데이터를 DataSource 가 만들어지는 시점에
미리 다 넣어두게 되면, DataSource 를 사용하는 곳에서는 dataSource.getConnection() 만 호출하면
되므로, URL , USERNAME , PASSWORD 같은 속성들에 의존하지 않아도 된다. 그냥 DataSource 만
주입받아서 getConnection() 만 호출하면 된다.
쉽게 이야기해서 리포지토리(Repository)는 DataSource 만 의존하고, 이런 속성을 몰라도 된다.
애플리케이션을 개발해보면 보통 설정은 한 곳에서 하지만, 사용은 수 많은 곳에서 하게 된다.
덕분에 객체를 설정하는 부분과, 사용하는 부분을 좀 더 명확하게 분리할 수 있다.



## DataSource 에제2 - 커넥션 풀

```
@Test
void dataSourceConnectionPool() throws SQLException, InterruptedException {
    //커넥션 풀링
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(ConnectionConst.URL);
    dataSource.setUsername(ConnectionConst.USERNAME);
    dataSource.setPassword(ConnectionConst.PASSWORD);
    dataSource.setMaximumPoolSize(10);
    dataSource.setPoolName("MyPool");

    useDataSource(dataSource);
    Thread.sleep(1000);
}

```

HikariCP 커넥션 풀을 사용한다. HikariDataSource 는 DataSource 인터페이스를 구현하고 있다.
커넥션 풀 최대 사이즈를 10으로 지정하고, 풀의 이름을 MyPool 이라고 지정했다.

커넥션 풀에서 커넥션을 생성하는 작업은 애플리케이션 실행 속도에 영향을 주지 않기 위해 별도의
쓰레드에서 작동한다. 별도의 쓰레드에서 동작하기 때문에 테스트가 먼저 종료되어 버린다. 예제처럼
Thread.sleep 을 통해 대기 시간을 주어야 쓰레드 풀에 커넥션이 생성되는 로그를 확인할 수 있다.



```
#커넥션 풀 초기화 정보 출력
        HikariConfig - MyPool - configuration:
        HikariConfig - maximumPoolSize................................10
        HikariConfig - poolName................................"MyPool"
        #커넥션 풀 전용 쓰레드가 커넥션 풀에 커넥션을 10개 채움
        [MyPool connection adder] MyPool - Added connection conn0: url=jdbc:h2:..
        user=SA
        [MyPool connection adder] MyPool - Added connection conn1: url=jdbc:h2:..
        user=SA
        [MyPool connection adder] MyPool - Added connection conn2: url=jdbc:h2:..
        user=SA
        [MyPool connection adder] MyPool - Added connection conn3: url=jdbc:h2:..
        user=SA
        [MyPool connection adder] MyPool - Added connection conn4: url=jdbc:h2:..
        user=SA
        ...
        [MyPool connection adder] MyPool - Added connection conn9: url=jdbc:h2:..
        user=SA
        #커넥션 풀에서 커넥션 획득1
        ConnectionTest - connection=HikariProxyConnection@446445803 wrapping conn0:
        url=jdbc:h2:tcp://localhost/~/test user=SA, class=class 
        com.zaxxer.hikari.pool.HikariProxyConnection
        #커넥션 풀에서 커넥션 획득2
        ConnectionTest - connection=HikariProxyConnection@832292933 wrapping conn1:
        url=jdbc:h2:tcp://localhost/~/test user=SA, class=class 
        com.zaxxer.hikari.pool.HikariProxyConnection
        MyPool - After adding stats (total=10, active=2, idle=8, waiting=0)
```

실행 결과를 분석해보자.
**HikariConfig**

HikariCP 관련 설정을 확인할 수 있다. 풀의 이름( MyPool )과 최대 풀 수( 10 )을 확인할 수 있다.

**MyPool connection adder**

별도의 쓰레드 사용해서 커넥션 풀에 커넥션을 채우고 있는 것을 확인할 수 있다. 이 쓰레드는 커넥션 풀에
커넥션을 최대 풀 수( 10 )까지 채운다.

그렇다면 왜 별도의 쓰레드를 사용해서 커넥션 풀에 커넥션을 채우는 것일까?

커넥션 풀에 커넥션을 채우는 것은 상대적으로 오래 걸리는 일이다. 애플리케이션을 실행할 때 커넥션 풀을
채울 때 까지 마냥 대기하고 있다면 애플리케이션 실행 시간이 늦어진다. 따라서 이렇게 별도의 쓰레드를
사용해서 커넥션 풀을 채워야 애플리케이션 **실행 시간에 영향을 주지 않는다.**



**커넥션 풀에서 커넥션 획득**
커넥션 풀에서 커넥션을 획득하고 그 결과를 출력했다. 여기서는 커넥션 풀에서 커넥션을 2개 획득하고
반환하지는 않았다. 따라서 풀에 있는 10개의 커넥션 중에 2개를 가지고 있는 상태이다. 그래서 마지막
로그를 보면 사용중인 커넥션 active=2 , 풀에서 대기 상태인 커넥션 idle=8 을 확인할 수 있다.
MyPool - After adding stats (total=10, active=2, idle=8, waiting=0)



커넥션 기본 개수가 10개로 설정되어있는데 10개를 넘겨서까지 초과해서 사용하면

풀이 종료될때까지 Block이 됨 (waiting상태) 30초 지나면 연결이 끊김

## DataSource 적용

```
/**
 * JDBC - DataSource 사용, JdbcUtils 사용
 */
@Slf4j
public class MemberRepositoryV1 {
    private final DataSource dataSource;
    public MemberRepositoryV1(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    //save()...
    //findById()...
    //update()....
    //delete()....
    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }
    private Connection getConnection() throws SQLException {
        Connection con = dataSource.getConnection();
        log.info("get connection={}, class={}", con, con.getClass());
        return con;
    }
}
```

DataSource 의존관계 주입

외부에서 DataSource 를 주입 받아서 사용한다. 이제 직접 만든 DBConnectionUtil 을 사용하지

않아도 된다.

DataSource 는 표준 인터페이스 이기 때문에 DriverManagerDataSource 에서
HikariDataSource 로 변경되어도 해당 코드를 변경하지 않아도 된다.

JdbcUtils 편의 메서드

스프링은 JDBC를 편리하게 다룰 수 있는 JdbcUtils 라는 편의 메서드를 제공한다.
JdbcUtils 을 사용하면 커넥션을 좀 더 편리하게 닫을 수 있다



```
MemberRepositoryV1 repository;

        @BeforeEach
        void beforeEach() {
            //기본 DriverManager - 항상 새로운 커넥션을 획득
            //DriverManagerDataSource dataSource = new DriverManagerDataSource(ConnectionConst.URL, ConnectionConst.USERNAME, ConnectionConst.PASSWORD);

            //커넥션 풀링
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(ConnectionConst.URL);
            dataSource.setUsername(ConnectionConst.USERNAME);
            dataSource.setPassword(ConnectionConst.PASSWORD);

            repository = new MemberRepositoryV1(dataSource);
        }
```

커넥션 풀 사용시 conn0 커넥션이 재사용 된 것을 확인할 수 있다.
테스트는 순서대로 실행되기 때문에 커넥션을 사용하고 다시 돌려주는 것을 반복한다. 따라서 conn0 만
사용된다.
웹 애플리케이션에 동시에 여러 요청이 들어오면 여러 쓰레드에서 커넥션 풀의 커넥션을 다양하게 가져가는
상황을 확인할 수 있다.

**DI**
**DriverManagerDataSource**를  **HikariDataSource** 로 변경해도 MemberRepositoryV1 의 코드는 전혀
변경하지 않아도 된다. MemberRepositoryV1 는 **DataSource 인터페이스에만 의존**하기 때문이다. 
이것이 DataSource 를 사용하는 장점이다.(DI + OCP)



정리 



**커넥션 풀에서 가져온 커넥션을 사용이 끝난뒤 종료하더라도 그 커넥션을 종료하는것이아닌 커넥션 풀에 다시 반환하는것이다.**



**DriverManagerDataSource를 커넥션을 사용할때마다 생성하지만**

**HikariDataSource를 사용하면 커넥션을 미리만들어놓은것을 재사용한다.**



## 트랜잭션 - 개념 이해


데이터를 저장할 때 단순히 파일에 저장해도 되는데, 데이터베이스에 저장하는 이유는 무엇일까?
여러가지 이유가 있지만, 가장 대표적인 이유는 바로 데이터베이스는 트랜잭션이라는 개념을 지원하기
때문이다.

트랜잭션을 이름 그대로 번역하면 거래라는 뜻이다. 이것을 쉽게 풀어서 이야기하면, 데이터베이스에서
트랜잭션은 하나의 거래를 안전하게 처리하도록 보장해주는 것을 뜻한다. 그런데 하나의 거래를 안전하게
처리하려면 생각보다 고려해야 할 점이 많다. 예를 들어서 A의 5000원을 B에게 계좌이체한다고
생각해보자. A의 잔고를 5000원 감소하고, B의 잔고를 5000원 증가해야한다.

5000원 계좌이체

1. A의 잔고를 5000원 감소
2. B의 잔고를 5000원 증가

계좌이체라는 거래는 이렇게 2가지 작업이 합쳐져서 하나의 작업처럼 동작해야 한다. 만약 1번은
성공했는데 2번에서 시스템에 문제가 발생하면 계좌이체는 실패하고, A의 잔고만 5000원 감소하는 심각한
문제가 발생한다. 

데이터베이스가 제공하는 트랜잭션 기능을 사용하면 1,2 둘다 함께 성공해야 저장하고, 중간에 하나라도
실패하면 거래 전의 상태로 돌아갈 수 있다. 만약 1번은 성공했는데 2번에서 시스템에 문제가 발생하면
계좌이체는 실패하고, 거래 전의 상태로 완전히 돌아갈 수 있다. 결과적으로 A의 잔고가 감소하지 않는다.

모든 작업이 성공해서 데이터베이스에 정상 반영하는 것을 **커밋( Commit )**이라 하고, 작업 중 하나라도
실패해서 거래 이전으로 되돌리는 것을 **롤백( Rollback** )이라 한다.



**트랜잭션 ACID**

트랜잭션은 ACID(http://en.wikipedia.org/wiki/ACID)라 하는 원자성(Atomicity), 일관성
(Consistency), 격리성(Isolation), 지속성(Durability)을 보장해야 한다.



원자성: 트랜잭션 내에서 실행한 작업들은 마치 하나의 작업인 것처럼 모두 성공 하거나 모두 실패해야
한다.

일관성: 모든 트랜잭션은 일관성 있는 데이터베이스 상태를 유지해야 한다. 예를 들어 데이터베이스에서
정한 무결성 제약 조건을 항상 만족해야 한다. 

격리성: 동시에 실행되는 트랜잭션들이 서로에게 영향을 미치지 않도록 격리한다. 예를 들어 동시에 같은
데이터를 수정하지 못하도록 해야 한다. 격리성은 동시성과 관련된 성능 이슈로 인해 트랜잭션 격리 수준
(Isolation level)을 선택할 수 있다. 

지속성: 트랜잭션을 성공적으로 끝내면 그 결과가 항상 기록되어야 한다. 중간에 시스템에 문제가 발생해도
데이터베이스 로그 등을 사용해서 성공한 트랜잭션 내용을 복구해야 한다.



트랜잭션은 원자성, 일관성, 지속성을 보장한다. **문제는 격리성**인데 트랜잭션 간에 **격리성을 완벽히**
**보장하려면 트랜잭션을 거의 순서대로 실행**해야 한다. 이렇게 하면 동시 처리 성능이 매우 나빠진다. 이런
문제로 인해 ANSI 표준은 트랜잭션의 격리 수준을 4단계로 나누어 정의했다.



트랜잭션 격리 수준 - Isolation level
READ UNCOMMITED(커밋되지 않은 읽기) 
READ COMMITTED(커밋된 읽기) 
REPEATABLE READ(반복 가능한 읽기) 
SERIALIZABLE(직렬화 가능) 

-

> 참고: 강의에서는 일반적으로 많이 사용하는 READ COMMITTED(커밋된 읽기) 트랜잭션 격리 수준을
> 기준으로 설명한다.
> 트랜잭션 격리 수준은 데이터베이스에 자체에 관한 부분이어서 이 강의 내용을 넘어선다. 트랜잭션 격리
> 수준에 대한 더 자세한 내용은 데이터베이스 메뉴얼이나, JPA 책 16.1 트랜잭션과 락을 참고하자



## 데이터베이스 연결 구조와 DB 세션

![DataBase Session](C:\Users\User\Desktop\Spring_2022\DataBase Session.JPG)사용자는 웹 애플리케이션 서버(WAS)나 DB 접근 툴 같은 클라이언트를 사용해서 데이터베이스 서버에
접근할 수 있다. 클라이언트는 데이터베이스 서버에 연결을 요청하고 커넥션을 맺게 된다. 이때
데이터베이스 서버는 내부에 세션이라는 것을 만든다. 그리고 앞으로 해당 커넥션을 통한 모든 요청은 이
세션을 통해서 실행하게 된다.
쉽게 이야기해서 개발자가 클라이언트를 통해 SQL을 전달하면 현재 커넥션에 연결된 세션이 SQL을
실행한다.
세션은 트랜잭션을 시작하고, 커밋 또는 롤백을 통해 트랜잭션을 종료한다. 그리고 이후에 새로운
트랜잭션을 다시 시작할 수 있다.
사용자가 커넥션을 닫거나, 또는 DBA(DB 관리자)가 세션을 강제로 종료하면 세션은 종료된다.





**커넥션 풀이 10개의 커넥션을 생성하면, 세션도 10개 만들어진다.**



## 트랜잭션 - DB (개념이해) 

**트랜잭션 사용법**
데이터 변경 쿼리를 실행하고 데이터베이스에 그 결과를 반영하려면 커밋 명령어인 commit 을 호출하고, 
결과를 반영하고 싶지 않으면 롤백 명령어인 rollback 을 호출하면 된다.

**커밋을 호출하기 전까지는 임시로 데이터를 저장**하는 것이다. 따라서 **해당 트랜잭션을 시작한 세션(사용자)**
**에게만 변경 데이터가 보이고 다른 세션(사용자)에게는 변경 데이터가 보이지 않는다.**
등록, 수정, 삭제 모두 같은 원리로 동작한다. 앞으로는 등록, 수정, 삭제를 간단히 변경이라는 단어로
표현하겠다.![transaction session](C:\Users\User\Desktop\Spring_2022\transaction session.JPG)

세션1은 트랜잭션을 시작하고 **신규 회원1, 신규 회원2를 DB에 추가**했다. 아직 커밋은 하지 않은 상태이다.
새로운 데이터는 임시 상태로 저장된다.

세션1은 select 쿼리를 실행해서 본인이 입력한 **신규 회원1, 신규 회원2를 조회**할 수 있다.
세션2는 select 쿼리를 실행해도 **신규 회원들을 조회할 수 없다**. 왜냐하면 세션1이 아직 커밋을 하지
않았기 때문이다



커밋하지 않은 데이터를 다른 곳에서 조회할 수 있으면 어떤 문제가 발생할까?
예를 들어서 커밋하지 않는 데이터가 보인다면, 세션2는 데이터를 조회했을 때 신규 회원1, 2가 보일
것이다. 

따라서 **신규 회원1, 신규 회원2가 있다고 가정하고 어떤 로직을 수행**할 수 있다. 그런데 세션1이
**롤백을 수행하면 신규 회원1, 신규 회원2의 데이터가 사라지게 된다.** 따라서 **데이터 정합성에 큰 문제가**
**발생**한다.
세션2에서 세션1이 아직 커밋하지 않은 변경 데이터가 보이다면, 세션1이 롤백 했을 때 심각한 문제가
발생할 수 있다. 



**따라서 커밋 전의 데이터는 다른 세션에서 보이지 않는다.**

![transaction commit](C:\Users\User\Desktop\Spring_2022\transaction commit.JPG)



세션1이 신규 데이터를 추가한 후에 commit 을 호출했다.

commit 으로 새로운 데이터가 실제 데이터베이스에 반영된다. 데이터의 상태도 임시 완료로
변경되었다.

이제 다른 세션에서도 회원 테이블을 조회하면 신규 회원들을 확인할 수 있다.



**세션1 신규 데이터 추가 후 rollback**



세션1이 신규 데이터를 추가한 후에 commit 대신에 rollback 을 호출했다.

세션1이 데이터베이스에 반영한 모든 데이터가 처음 상태로 복구된다.
수정하거나 삭제한 데이터도 rollback 을 호출하면 모두 트랜잭션을 시작하기 직전의 상태로 복구된다.



## 트랜잭션 (자동커밋 , 수동커밋)

**자동커밋**

트랜잭션을 사용하려면 먼저 자동 커밋과 수동 커밋을 이해해야 한다.
자동 커밋으로 설정하면 각각의 쿼리 실행 직후에 자동으로 커밋을 호출한다. 따라서 커밋이나 롤백을 직접
호출하지 않아도 되는 편리함이 있다. 하지만 쿼리를 하나하나 실행할 때 마다 자동으로 커밋이 되어버리기
때문에 우리가 원하는 트랜잭션 기능을 제대로 사용할 수 없다.



set autocommit true; //**자동 커밋 모드 설정**
insert into member(member_id, money) values ('data1',10000); //자동 커밋
insert into member(member_id, money) values ('data2',10000); //자동 커밋



따라서 commit , rollback 을 직접 호출하면서 트랜잭션 기능을 제대로 수행하려면 자동 커밋을 끄고
수동 커밋을 사용해야 한다



set autocommit false; //**수동 커밋 모드 설정**
insert into member(member_id, money) values ('data3',10000);
insert into member(member_id, money) values ('data4',10000);
commit; //수동 커밋



보통 자동 커밋 모드가 기본으로 설정된 경우가 많기 때문에, **수동 커밋 모드로 설정하는 것을 트랜잭션을**
**시작**한다고 표현할 수 있다.

수동 커밋 설정을 하면 이후에 꼭 commit , rollback 을 호출해야 한다.

참고로 수동 커밋 모드나 자동 커밋 모드는 한번 설정하면 해당 세션에서는 계속 유지된다. 

중간에 변경하는것은 가능하다



## 트랜잭션 (실습)



1. 기본 데이터 입력
  지금까지 설명한 예시를 직접 확인해보자.
  먼저 H2 데이터베이스 웹 콘솔 창을 2개 열어두자.
> 주의!
> H2 데이터베이스 웹 콘솔 창을 2개 열때 기존 URL을 복사하면 안된다. 꼭 http://localhost:8082 를
> 직접 입력해서 완전히 새로운 세션에서 연결하도록 하자. URL을 복사하면 같은 세션( jsessionId )에서
> 실행되어서 원하는 결과가 나오지 않을 수 있다



데이터 초기화 SQL
//데이터 초기화
set autocommit true;
delete from member;
insert into member(member_id, money) values ('oldId',10000);

자동 커밋상태에서는 다른 h2데이터베이스 세션에서 값을 조회하더라도 같은 데이터가 들어있음을 확인할 수 있음



**신규 데이터 추가 - 커밋 전**

//트랜잭션 시작
set autocommit false; //수동 커밋 모드
insert into member(member_id, money) values ('newId1',10000);
insert into member(member_id, money) values ('newId2',10000);

세션1, 세션2에서 다음 쿼리를 실행해서 결과를 확인하자.
select * from member;
결과를 이미지와 비교해보자. 아직 세션1이 커밋을 하지 않은 상태이기 때문에 세션1에서는 입력한
데이터가 보이지만, 세션2에서는 입력한 데이터가 보이지 않는 것을 확인할 수 있다



세션1에서 커밋을 호출해보자.
commit; //데이터베이스에 반영
세션1, 세션2에서 다음 쿼리를 실행해서 결과를 확인하자.

select * from member;
결과를 이미지와 비교해보자. 세션1이 트랜잭션을 커밋했기 때문에 데이터베이스에 실제 데이터가
반영된다. 커밋 이후에는 모든 세션에서 데이터를 조회할 수 있다.



**롤백 - rollback**



//데이터 초기화
set autocommit true;
delete from member;
insert into member(member_id, money) values ('oldId',10000);

//트랜잭션 시작
set autocommit false; //수동 커밋 모드
insert into member(member_id, money) values ('newId1',10000);
insert into member(member_id, money) values ('newId2',10000)



select * from member;



rollack;



세션1, 세션2에서 다음 쿼리를 실행해서 결과를 확인하자.
select * from member;
결과를 이미지와 비교해보자. 롤백으로 데이터가 DB에 반영되지 않은 것을 확인할 수 있다.



## 트랜잭션 - (계좌이체)



이번에는 계좌이체 예제를 통해 트랜잭션이 어떻게 사용되는지 조금 더 자세히 알아보자.
다음 3가지 상황을 준비했다.

계좌이체 정상
계좌이체 문제 상황 - 커밋
계좌이체 문제 상황 - 롤백



다음 기본 데이터를 준비했다.
memberA 10000원
memberB 10000원
이제 계좌이체를 실행해보자.



memberA 의 돈을 memberB 에게 2000원 계좌이체하는 트랜잭션을 실행해보자. 다음과 같은 2번의
update 쿼리가 수행되어야 한다.
set autocommit false 로 설정한다.
아직 커밋하지 않았으므로 다른 세션에는 기존 데이터가 조회된다.

계좌이체 실행 SQL - 성공
set autocommit false;
update member set money=10000 - 2000 where member_id = 'memberA';
update member set money=10000 + 2000 where member_id = 'memberB'



commit 명령어를 실행하면 데이터베이스에 결과가 반영된다.
다른 세션에서도 memberA 의 금액이 8000원으로 줄어들고, memberB 의 금액이 12000원으로 증가한
것을 확인할 수 있다.

세션1 커밋
**commit;**

확인 쿼리

**select * from member;**



**계좌이체 문제 상황 - 커밋**



계좌이체를 실행하는 도중에 SQL에 문제가 발생한다. 그래서 memberA 의 돈을 2000원 줄이는 것에는
성공했지만, memberB 의 돈을 2000원 증가시키는 것에 실패한다.
두 번째 SQL은 member_iddd 라는 필드에 오타가 있다. 두 번째 update 쿼리를 실행하면 SQL 오류가
발생하는 것을 확인할 수 있다.

**계좌이체 실행 SQL - 오류**

set autocommit false;
update member set money=10000 - 2000 where member_id = 'memberA'; //성공
update member set money=10000 + 2000 where member_iddd = 'memberB'; //쿼리 예외

발생



두 번째 SQL 실행시 발생하는 오류 메시지
Column "MEMBER_IDDD" not found; SQL statement:
update member set money=10000 + 2000 where member_iddd = 'memberB' [42122-200] 
42S22/42122
여기서 문제는 memberA 의 돈은 2000원 줄어들었지만, memberB 의 돈은 2000원 증가하지 않았다는
점이다. 결과적으로 계좌이체는 실패하고 memberA 의 돈만 2000원 줄어든 상황이다



만약 이 상황에서 강제로 commit 을 호출하면 어떻게 될까?
계좌이체는 실패하고 memberA 의 돈만 2000원 줄어드는 아주 심각한 문제가 발생한다.
세션1 커밋
commit;

확인 쿼리
select * from member;

이렇게 중간에 문제가 발생했을 때는 커밋을 호출하면 안된다. 롤백을 호출해서 데이터를 트랜잭션 시작
시점으로 원복해야 한다.



계좌이체 문제 상황 - 롤백
중간에 문제가 발생했을 때 롤백을 호출해서 트랜잭션 시작 시점으로 데이터를 원복해보자.
기본 데이터 입력
먼저 다음 SQL로 기본 데이터를 설정하자.
기본 데이터 입력 - SQL

set autocommit true;
delete from member;
insert into member(member_id, money) values ('memberA',10000);
insert into member(member_id, money) values ('memberB',10000);



계좌이체를 실행하는 도중에 SQL에 문제가 발생한다. 그래서 memberA 의 돈을 2000원 줄이는 것에는
성공했지만, memberB 의 돈을 2000원 증가시키는 것에 실패한다. 두 번째 update 쿼리를 실행하면 SQL 
오류가 발생하는 것을 확인할 수 있다.
계좌이체 실행 SQL - 오류
set autocommit false;
update member set money=10000 - 2000 where member_id = 'memberA'; //성공
update member set money=10000 + 2000 where member_iddd = 'memberB'; //쿼리 예외
발생
두 번째 SQL 실행시 발생하는 오류 메시지
Column "MEMBER_IDDD" not found; SQL statement:
update member set money=10000 + 2000 where member_iddd = 'memberB' [42122-200] 
42S22/42122
여기서 문제는 memberA 의 돈은 2000원 줄어들었지만, memberB 의 돈은 2000원 증가하지 않았다는
점이다. 결과적으로 계좌이체는 실패하고 memberA 의 돈만 2000원 줄어든 상황이다.
롤백
이럴 때는 롤백을 호출해서 트랜잭션을 시작하기 전 단계로 데이터를 복구해야 한다.
롤백을 사용한 덕분에 계좌이체를 실행하기 전 상태로 돌아왔다. memberA 의 돈도 이전 상태인 10000
원으로 돌아오고, memberB 의 돈도 10000 원으로 유지되는 것을 확인할 수 있다.
세션1 - 롤백

rollback;

확인 쿼리
select * from member;



정리
원자성: 트랜잭션 내에서 실행한 작업들은 마치 하나의 작업인 것처럼 모두 성공 하거나 모두 실패해야
한다. 



트랜잭션의 원자성 덕분에 여러 SQL 명령어를 마치 하나의 작업인 것 처럼 처리할 수 있었다. 성공하면
한번에 반영하고, 중간에 실패해도 마치 하나의 작업을 되돌리는 것 처럼 간단히 되돌릴 수 있다.

**오토 커밋**
만약 오토 커밋 모드로 동작하는데, 계좌이체 중간에 실패하면 어떻게 될까? 쿼리를 하나 실행할 때 마다
바로바로 커밋이 되어버리기 때문에 memberA 의 돈만 2000원 줄어드는 심각한 문제가 발생한다.

**트랜잭션 시작**
따라서 이런 종류의 작업은 꼭 수동 커밋 모드를 사용해서 수동으로 커밋, 롤백 할 수 있도록 해야 한다. 보통
이렇게 자동 커밋 모드에서 수동 커밋 모드로 전환 하는 것을 트랜잭션을 시작한다고 표현한다.



## DB 락 - 개념 이해

세션1이 트랜잭션을 시작하고 데이터를 수정하는 동안 아직 커밋을 수행하지 않았는데, 세션2에서 동시에
같은 데이터를 수정하게 되면 여러가지 문제가 발생한다. 바로 트랜잭션의 원자성이 깨지는 것이다. 여기에
더해서 세션1이 중간에 롤백을 하게 되면 세션2는 잘못된 데이터를 수정하는 문제가 발생한다.

이런 문제를 방지하려면, 세션이 트랜잭션을 시작하고 데이터를 수정하는 동안에는 커밋이나 롤백 전까지
다른 세션에서 해당 데이터를 수정할 수 없게 막아야 한다.



세션1은 memberA 의 금액을 500원으로 변경하고 싶고, 세션2는 같은 memberA 의 금액을 1000원으로
변경하고 싶다.

데이터베이스는 이런 문제를 해결하기 위해 락(Lock)이라는 개념을 제공한다.
다음 예시를 통해 동시에 데이터를 수정하는 문제를 락으로 어떻게 해결하는지 자세히 알아보자.



1. 세션1은 트랜잭션을 시작한다.
2. 세션1은 memberA 의 money 를 500으로 변경을 시도한다. 이때 해당 로우의 락을 먼저 획득해야 한다. 
  락이 남아 있으므로 세션1은 락을 획득한다. (세션1이 세션2보다 조금 더 빨리 요청했다.)
3. 세션1은 락을 획득했으므로 해당 로우에 update sql을 수행한다.



4. 세션2는 트랜잭션을 시작한다.
5. 세션2도 memberA 의 money 데이터를 변경하려고 시도한다. 이때 해당 로우의 락을 먼저 획득해야 한다. 
  락이 없으므로 락이 돌아올 때 까지 대기한다.
  참고로 세션2가 락을 무한정 대기하는 것은 아니다. 락 대기 시간을 넘어가면 락 타임아웃 오류가 발생한다. 
  락 대기 시간은 설정할 수 있다.


6. 세션1은 커밋을 수행한다. 커밋으로 트랜잭션이 종료되었으므로 락도 반납한다
7. 락을 획득하기 위해 대기하던 세션2가 락을 획득한다
8. 세션2는 update sql을 수행한다
9. 세션2는 커밋을 수행하고 트랜잭션이 종료되었으므로 락을 반납한다.



**DB 락 - 변경**



기본 데이터 입력 - SQL
set autocommit true;
delete from member;
insert into member(member_id, money) values ('memberA',10000);



set autocommit false;
update member set money=500 where member_id = 'memberA';





SET LOCK_TIMEOUT 60000;
set autocommit false;
update member set money=1000 where member_id = 'memberA';

세션2는 memberA 의 데이터를 1000원으로 수정하려 한다.
세션1이 트랜잭션을 커밋하거나 롤백해서 종료하지 않았으므로 아직 세션1이 락을 가지고 있다. 따라서
**세션2는 락을 획득하지 못하기 때문에 데이터를 수정할 수 없다.** 세션2는 락이 돌아올 때 까지 대기하게
된다.
SET LOCK_TIMEOUT 60000 : 락 획득 시간을 60초로 설정한다. 60초 안에 락을 얻지 못하면 예외가
발생한다.
참고로 H2 데이터베이스에서는 딱 60초에 예외가 발생하지는 않고, 시간이 조금 더 걸릴 수 있다.

세션2 락 획득

세션1을 커밋하면 세션1이 커밋되면서 락을 반납한다. 이후에 대기하던 세션2가 락을 획득하게 된다. 
따라서 락을 획득한 세션2의 업데이트가 반영되는 것을 확인할 수 있다. 물론 이후에 세션2도 커밋을
호출해서 락을 반납해야 한다



세션1
commit;
세션1이 커밋하면 이후에 락을 반납하고 다음 시나리오가 이어진다

세션2 락 타임아웃
SET LOCK_TIMEOUT  : 락 타임아웃 시간을 설정한다.
예) SET LOCK_TIMEOUT 10000 10초, 세션2에 설정하면 세션2가 10초 동안 대기해도 락을 얻지 못하면
락 타임아웃 오류가 발생한다.

위 시나리오 중간에 락을 오랜기간 대기하면 어떻게 되는지 알아보자.
10초 정도 기다리면 세션2에서는 다음과 같은 락 타임아웃 오류가 발생한다.



세션2의 실행 결과
Timeout trying to lock table {0}; SQL statement:
update member set money=10000 - 2000 where member_id = 'memberA' [50200-200] 
HYT00/50200



세션1이 memberA 의 데이터를 변경하고, 트랜잭션을 아직 커밋하지 않았다. 따라서 세션2는 세션1이
트랜잭션을 커밋하거나 롤백할 때 까지 대기해야 한다. 기다리면 락 타임아웃 오류가 발생하는 것을 확인할
수 있다.

> 주의!
> 테스트 도중 락이 꼬이는 문제가 발생할 수 있다. 이럴 때는 H2 서버를 내렸다가 다시 올리자. 여기서 H2 
> 서버를 내린다는 뜻은 웹 브라우저를 종료하는 것이 아니라 h2.sh , h2.bat 를 종료하고 다시 실행하는
> 것을 뜻한다



##  DB 락 - 조회

일반적인 조회는 락을 사용하지 않는다
데이터베이스마다 다르지만, 보통 데이터를 조회할 때는 락을 획득하지 않고 바로 데이터를 조회할 수 있다. 
예를 들어서 세션1이 락을 획득하고 데이터를 변경하고 있어도, 세션2에서 데이터를 조회는 할 수 있다. 
물론 세션2에서 조회가 아니라 데이터를 변경하려면 락이 필요하기 때문에 락이 돌아올 때 까지 대기해야
한다.

**조회와 락**

데이터를 조회할 때도 락을 획득하고 싶을 때가 있다. 이럴 때는 **select for update** 구문을 사용하면
된다.
이렇게 하면 세션1이 **조회 시점에 락을 가져가버리기 때문에 다른 세션에서 해당 데이터를 변경할 수 없다.**
물론 이 경우도 **트****랜잭션을 커밋하면 락을 반납**한다.

**조회 시점에 락이 필요한 경우는 언제일까?**

**트랜잭션 종료 시점까지 해당 데이터를 다른 곳에서 변경하지 못하도록 강제로 막아야 할 때 사용한다**.
예를 들어서 애플리케이션 로직에서 memberA 의 금액을 조회한 다음에 이 금액 정보로 애플리케이션에서
어떤 계산을 수행한다. 그런데 이 계산이 돈과 관련된 매우 중요한 계산이어서 계산을 완료할 때 까지
memberA 의 금액을 다른곳에서 변경하면 안된다. 이럴 때 조회 시점에 락을 획득하면 된다.



기본 데이터 입력 - SQL
set autocommit true;
delete from member;
insert into member(member_id, money) values ('memberA',10000);

**세션1**
set autocommit false;
select * from member where member_id='memberA' for update;

**select for update 구문을 사용하면 조회를 하면서 동시에 선택한 로우의 락도 획득**한다.
물론 락이 없다면 락을 획득할 때 까지 대기해야 한다.

**세션1은 트랜잭션을 종료할 때 까지 memberA** 의 **로우의 락을 보유**한다.



set autocommit false;
update member set money=500 where member_id = 'memberA';

**세션2는 데이터를 변경**하고 싶다. **데이터를 변경하려면 락이 필요**하다.
**세션1이 memberA 로우의 락을 획득**했기 때문에 **세션2는 락을 획득할 때 까지 대기**한다.
이후에 세션1이 커밋을 수행하면 세션2가 락을 획득하고 데이터를 변경한다. 만약 락 타임아웃 시간이
지나면 락 타임아웃 예외가 발생한다.



세션1 커밋
commit;
세션2도 커밋해서 데이터를 반영해준다.

세션2 커밋
commit;

정리
트랜잭션과 락은 데이터베이스마다 실제 동작하는 방식이 조금씩 다르기 때문에, 해당 데이터베이스
메뉴얼을 확인해보고, 의도한대로 동작하는지 테스트한 이후에 사용하자.



## 트랜잭션 - 적용 1

실제 애플리케이션에서 DB 트랜잭션을 사용해서 계좌이체 같이 원자성이 중요한 비즈니스 로직을 어떻게
구현하는지 알아보자.

먼저 트랜잭션 없이 단순하게 계좌이체 비즈니스 로직만 구현해보자



```
package hello.jdbc.service;


import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV1 {

    private final MemberRepositoryV1 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId); // 보내는 사람
        Member toMember = memberRepository.findById(toId); // 누구에게 보낼지

        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);

        memberRepository.update(toId, toMember.getMoney() + money);

    }

    private void validation(Member toMember) {
        if(toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}

```

formId 의 회원을 조회해서 toId 의 회원에게 money 만큼의 돈을 계좌이체 하는 로직이다.

fromId 회원의 돈을 money 만큼 감소한다. UPDATE SQL 실행
toId 회원의 돈을 money 만큼 증가한다. UPDATE SQL 실행
예외 상황을 테스트해보기 위해 toId 가 "ex" 인 경우 예외를 발생한다



```
package hello.jdbc.service;

import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV0;
import hello.jdbc.repository.MemberRepositoryV1;
import lombok.SneakyThrows;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 기본 동작, 트랜잭션이 없어서 문제 발생
 * */
public class MemberServiceV1Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    private MemberRepositoryV1 memberRepository;
    private MemberServiceV1 memberService;


    @BeforeEach
    void before() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepository = new MemberRepositoryV1(dataSource);
        memberService = new MemberServiceV1(memberRepository);
    }

    @SneakyThrows
    @Test
    @DisplayName("정상 이체")
    void accountTransfer() throws SQLException {
        //given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_B, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        //when
        memberService.accountTransfer(memberA.getMemberId(),memberB.getMemberId(), 2000);


        //then
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberB = memberRepository.findById(memberB.getMemberId());
        assertThat(findMemberA.getMoney()).isEqualTo(8000);
        assertThat(findMemberB.getMoney()).isEqualTo(12000);
    }

    @Test
    @DisplayName("이체중 예외 발생")
    void accountTransferEx() throws SQLException {
        //given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberEx = new Member(MEMBER_EX, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberEx);
        //when
        assertThatThrownBy(() ->
                memberService.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(),
                        2000))
                .isInstanceOf(IllegalStateException.class);
        //then
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberEx =
                memberRepository.findById(memberEx.getMemberId());
        //memberA의 돈만 2000원 줄었고, ex의 돈은 10000원 그대로이다.
        assertThat(findMemberA.getMoney()).isEqualTo(8000);
        assertThat(findMemberEx.getMoney()).isEqualTo(10000);
    }

}

```

**정상이체 - accountTransfer()**
**give**n: 다음 데이터를 저장해서 테스트를 준비한다.
**memberA** 10000원
memberB 10000원

when: 계좌이체 로직을 실행한다.
memberService.accountTransfer() 를 실행한다.
memberA memberB 로 2000원 계좌이체 한다.
memberA 의 금액이 2000원 감소한다.
memberB 의 금액이 2000원 증가한다.
then: 계좌이체가 정상 수행되었는지 검증한다.
memberA 8000원 - 2000원 감소
memberB 12000원 - 2000원 증가
정상이체 로직이 정상 수행되는 것을 확인할 수 있다.

**테스트 데이터 제거**
테스트가 끝나면 다음 테스트에 영향을 주지 않기 위해 @AfterEach 에서 테스트에 사용한 데이터를 모두
삭제한다.
@BeforeEach : 각각의 테스트가 수행되기 전에 실행된다.
@AfterEach : 각각의 테스트가 실행되고 난 이후에 실행된다



## 트랜잭션 - 적용 2

애플리케이션에서 트랜잭션을 어떤 계층에 걸어야 할까? 쉽게 이야기해서 트랜잭션을 어디에서 시작하고, 
어디에서 커밋해야할까?



**비즈니스 로직과 트랜잭션**



트랜잭션은 비즈니스 로직이 있는 서비스 계층에서 시작해야 한다. 비즈니스 로직이 잘못되면 해당
비즈니스 로직으로 인해 문제가 되는 부분을 함께 롤백해야 하기 때문이다.

그런데 트랜잭션을 시작하려면 커넥션이 필요하다. 결국 서비스 계층에서 커넥션을 만들고, 트랜잭션 커밋
이후에 커넥션을 종료해야 한다.

애플리케이션에서 DB 트랜잭션을 사용하려면 **트랜잭션을 사용하는 동안 같은 커넥션을 유지**해야한다. 
그래야 같은 세션을 사용할 수 있다.



애플리케이션에서 같은 커넥션을 유지하려면 어떻게 해야할까? 가장 단순한 방법은 커넥션을 파라미터로
전달해서 같은 커넥션이 사용되도록 유지하는 것이다.

먼저 리포지토리가 파라미터를 통해 같은 커넥션을 유지할 수 있도록 파라미터를 추가하자.

코드 유지를 위해 MemberRepositoryV1 은 남겨두고 MemberRepositoryV2 를 만들자.



```
package hello.jdbc.repository;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;
import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;
/**
 * JDBC - ConnectionParam
 */
@Slf4j
public class MemberRepositoryV2 {
    private final DataSource dataSource;
    public MemberRepositoryV2(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values(?, ?)";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }
    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberId=" +
                        memberId);
            }
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, rs);
        }
    }
    public Member findById(Connection con, String memberId) throws SQLException
    {
        String sql = "select * from member where member_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberId=" +
                        memberId);
            }
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            //connection은 여기서 닫지 않는다.
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(pstmt);
        }
    }
    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money=? where member_id=?";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }
    public void update(Connection con, String memberId, int money) throws
            SQLException {
        String sql = "update member set money=? where member_id=?";
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            //connection은 여기서 닫지 않는다.
            JdbcUtils.closeStatement(pstmt);
        }
    }
    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id=?";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }
    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }
    private Connection getConnection() throws SQLException {
        Connection con = dataSource.getConnection();
        log.info("get connection={} class={}", con, con.getClass());
        return con;
    }
}
```

MemberRepositoryV2 는 기존 코드와 같고 커넥션 유지가 필요한 다음 두 메서드가 추가되었다. 참고로
다음 두 메서드는 계좌이체 서비스 로직에서 호출하는 메서드이다.
findById(Connection con, String memberId)
update(Connection con, String memberId, int money)
주의 - 코드에서 다음 부분을 주의해서 보자!



1. 커넥션 유지가 필요한 두 메서드는 파라미터로 넘어온 커넥션을 사용해야 한다. 따라서 con = 
  getConnection() 코드가 있으면 안된다.

  ​

2. 커넥션 유지가 필요한 두 메서드는 리포지토리에서 커넥션을 닫으면 안된다. 커넥션을 전달 받은
  리포지토리 뿐만 아니라 이후에도 커넥션을 계속 이어서 사용하기 때문이다. 이후 서비스 로직이 끝날 때
  트랜잭션을 종료하고 닫아야 한다.
  이제 가장 중요한 트랜잭션 연동 로직을 작성해보자.
  기존 MemberServiceV1 을 복사해서 새로운 MemberServiceV2 를 만들고 수정하자.

```
package hello.jdbc.service;
import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
/**
 * 트랜잭션 - 파라미터 연동, 풀을 고려한 종료
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {
    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;
    public void accountTransfer(String fromId, String toId, int money) throws
            SQLException {
        Connection con = dataSource.getConnection();
        try {
            con.setAutoCommit(false); //트랜잭션 시작
            //비즈니스 로직
            bizLogic(con, fromId, toId, money);
            con.commit(); //성공시 커밋
        } catch (Exception e) {
            con.rollback(); //실패시 롤백
            throw new IllegalStateException(e);
        } finally {
            release(con);
        }
    }
    private void bizLogic(Connection con, String fromId, String toId, int
            money) throws SQLException {
        Member fromMember = memberRepository.findById(con, fromId);
        Member toMember = memberRepository.findById(con, toId);
        memberRepository.update(con, fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(con, toId, toMember.getMoney() + money);
    }
    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
    private void release(Connection con) {
        if (con != null) {
            try {
                con.setAutoCommit(true); //커넥션 풀 고려 (커넥션 사용하고나서 커넥션 풀에 돌아갈때 고려해서 커넥션을 반환하기전에 수동커밋상태를 자동커밋으로 바꿔준다.)
                con.close();
            } catch (Exception e) {
                log.info("error", e);
            }
        }
    }
}
```

Connection con = dataSource.getConnection();
트랜잭션을 시작하려면 커넥션이 필요하다.

con.setAutoCommit(false); //트랜잭션 시작

트랜잭션을 시작하려면 자동 커밋 모드를 꺼야한다. 이렇게 하면 커넥션을 통해 세션에 set 
autocommit false 가 전달되고, 이후부터는 수동 커밋 모드로 동작한다. 이렇게 자동 커밋 모드를

수동 커밋 모드로 변경하는 것을 트랜잭션을 시작한다고 보통 표현한다.



bizLogic(con, fromId, toId, money);
트랜잭션이 시작된 커넥션을 전달하면서 비즈니스 로직을 수행한다.

이렇게 분리한 이유는 트랜잭션을 관리하는 로직과 실제 비즈니스 로직을 구분하기 위함이다.

memberRepository.update(con..) : 비즈니스 로직을 보면 리포지토리를 호출할 때 커넥션을
전달하는 것을 확인할 수 있다.

con.commit(); //성공시 커밋
비즈니스 로직이 정상 수행되면 트랜잭션을 커밋한다.

con.rollback(); //실패시 롤백
catch(Ex){..} 를 사용해서 비즈니스 로직 수행 도중에 예외가 발생하면 트랜잭션을 롤백한다.
release(con);

finally {..} 를 사용해서 커넥션을 모두 사용하고 나면 안전하게 종료한다. 



그런데 커넥션 풀을
사용하면 con.close() 를 호출 했을 때 커넥션이 종료되는 것이 아니라 풀에 반납된다. 현재 수동
커밋 모드로 동작하기 때문에 풀에 돌려주기 전에 기본 값인 자동 커밋 모드로 변경하는 것이
안전하다



```
package hello.jdbc.service;
import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import java.sql.SQLException;
import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
/**
 * 트랜잭션 - 커넥션 파라미터 전달 방식 동기화
 */
class MemberServiceV2Test {
    private MemberRepositoryV2 memberRepository;
    private MemberServiceV2 memberService;
    @BeforeEach
    void before() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL,
                USERNAME, PASSWORD);
        memberRepository = new MemberRepositoryV2(dataSource);
        memberService = new MemberServiceV2(dataSource, memberRepository);
    }
    @AfterEach
    void after() throws SQLException {
        memberRepository.delete("memberA");
        memberRepository.delete("memberB");
        memberRepository.delete("ex");
    }
    @Test
    @DisplayName("정상 이체")
    void accountTransfer() throws SQLException {
        //given
        Member memberA = new Member("memberA", 10000);
        Member memberB = new Member("memberB", 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberB);
        //when
        memberService.accountTransfer(memberA.getMemberId(),
                memberB.getMemberId(), 2000);
        //then
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberB = memberRepository.findById(memberB.getMemberId());
        assertThat(findMemberA.getMoney()).isEqualTo(8000);
        assertThat(findMemberB.getMoney()).isEqualTo(12000);
    }
    @Test
    @DisplayName("이체중 예외 발생")
    void accountTransferEx() throws SQLException {
        //given
        Member memberA = new Member("memberA", 10000);
        Member memberEx = new Member("ex", 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberEx);
        //when
        assertThatThrownBy(() ->
                memberService.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(),
                        2000))
                .isInstanceOf(IllegalStateException.class);
        //then
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberEx =
                memberRepository.findById(memberEx.getMemberId());
        //memberA의 돈이 롤백 되어야함
        assertThat(findMemberA.getMoney()).isEqualTo(10000);
        assertThat(findMemberEx.getMoney()).isEqualTo(10000);
    }
}
```

**정상이체 - accountTransfer()**
기존 로직과 같아서 생략한다.

**이체중 예외 발생 - accountTransferEx()**
다음 데이터를 저장해서 테스트를 준비한다.
memberA 10000원
memberEx 10000원
계좌이체 로직을 실행한다.
memberService.accountTransfer() 를 실행한다.
커넥션을 생성하고 트랜잭션을 시작한다.
memberA memberEx 로 2000원 계좌이체 한다.
memberA 의 금액이 2000원 감소한다.
memberEx 회원의 ID는 ex 이므로 중간에 예외가 발생한다.
예외가 발생했으므로 트랜잭션을 롤백한다.
계좌이체는 실패했다. 롤백을 수행해서 memberA 의 돈이 기존 10000원으로 복구되었다.
memberA 10000원 - 트랜잭션 롤백으로 복구된다.
memberB 10000원 - 중간에 실패로 로직이 수행되지 않았다. 따라서 그대로 10000원으로 남아있게
된다.

트랜잭션 덕분에 계좌이체가 실패할 때 롤백을 수행해서 모든 데이터를 정상적으로 초기화 할 수 있게
되었다. 결과적으로 계좌이체를 수행하기 직전으로 돌아가게 된다.

**남은 문제**
애플리케이션에서 DB 트랜잭션을 적용하려면 서비스 계층이 매우 지저분해지고, 생각보다 매우 복잡한
코드를 요구한다. 추가로 커넥션을 유지하도록 코드를 변경하는 것도 쉬운 일은 아니다. 다음 시간에는
스프링을 사용해서 이런 문제들을 하나씩 해결해보자.



**트랜잭션을 유지할려면 같은 커넥션을 사용해야한다!**