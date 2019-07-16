package zxh.demo.args;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ParserResult:
 * @author zhangxuhai
 * @date 2019-07-16
*/
public class ParserResult {
    public class ParserResultException extends RuntimeException {
        private ParserResultException(String message) {
            super(message);
        }
    }

    private Map<String, Object> flagValueMap = new HashMap<>();

    public void setFlagAndValue(String flag, Object value) {
        validEmpty(flag);
        flagValueMap.put(flag, value);
    }

    public Object getValueByFlag(String flag) {
        validEmpty(flag);

        if (!flagValueMap.containsKey(flag)) {
            throw new ParserResultException(String.format("Invalid flag: %s, no value matched!", flag));
        }

        return flagValueMap.get(flag);
    }

    private void validEmpty(String flag) {
        if (Objects.isNull(flag) || flag.trim().isEmpty()) {
            throw new ParserResultException("Invalid flag: empty flag!");
        }
    }
}
