package zxh.demo.args;

import java.util.HashMap;
import java.util.Map;

/**
 * ParserResult:
 * @author zhangxuhai
 * @date 2019-07-25
*/
public class ParserResult {
    private Map<String, Object> flagValueMap = new HashMap<>();

    void setFlagAndValue(String flag, Object value) {
        flagValueMap.put(flag, value);
    }

    public Object getFlag(String flag) {
        return flagValueMap.get(flag);
    }
}
