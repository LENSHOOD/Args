package zxh.demo.args;

import java.util.HashMap;
import java.util.Map;

/**
 * ParserResult:
 * @author zhangxuhai
 * @date 2019-07-25
*/
public class ParserResult {
    Map<String, String> flagValueMap = new HashMap<>();

    void setFlagAndValue(String flag, String value) {
        flagValueMap.put(flag, value);
    }

    public String getFlag(String flag) {
        return flagValueMap.get(flag);
    }
}
