package com.zxh.args.parser;

import com.zxh.args.parser.exception.ParserResultException;

import java.util.HashMap;
import java.util.Map;

/**
 * ParserResult:
 * @author zhangxuhai
 * @date 2019-07-01
*/
public class ParserResult {
    private Map<String, Object> flagValuePair = new HashMap<>();

    public void setFlagValue(String flag, Object value) {
        if (flag == null || value == null) {
            throw new ParserResultException("Flag or Value is null, which is not allowed!");
        }

        flagValuePair.put(flag, value);
    }

    public Object getValueByFlag(String flag) {
        if (flag == null) {
            throw new ParserResultException("Flag is null, which is not allowed!");
        }

        return flagValuePair.get(flag);
    }
}
