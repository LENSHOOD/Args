package zxh.demo.args.exception;

/**
 * ParserException:
 * @author zhangxuhai
 * @date 2019-07-10
*/
public class ParserException extends RuntimeException {
    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserException(String message) {
        super(message);
    }
}
