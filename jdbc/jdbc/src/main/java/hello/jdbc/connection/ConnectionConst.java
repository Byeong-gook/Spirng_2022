package hello.jdbc.connection;

//객체 생성을 못하게 하기위해 abstract로 선언
public abstract class ConnectionConst {
    public static final String URL = "jdbc:h2:tcp://localhost/~/test";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";

}
