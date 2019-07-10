package zxh.demo.args;

import java.util.Map;
import java.util.Objects;

/**
 * ParserResult:
 * @author zhangxuhai
 * @date 2019-07-10
*/
public class ParserResult {

    private class ParserResultException extends RuntimeException {
        private ParserResultException(String message) {
            super(message);
        }
    }

    private Map<String, Object> flagValueMap;

    ParserResult(Map<String, Object> flagValueMap) {
        this.flagValueMap = flagValueMap;
    }

    void setValueWithFlag(String flag, Object value) {
        if (Objects.isNull(flag)) {
            throw new ParserResultException("Invalid flag: null");
        }
        flagValueMap.put(flag, value);
    }

    public Object getArgValueByFlag(String flag) {
        if (Objects.isNull(flag) || flag.trim().length() > 1) {
            throw new ParserResultException("Invalid flag: " + flag);
        }

        flag = flag.trim();
        if (!flagValueMap.containsKey(flag)) {
            throw new ParserResultException("Invalid flag: " + flag + ", not found!");
        }

        return flagValueMap.get(flag);
    }
}
