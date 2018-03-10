package gdut.utils;

import gdut.pojo.BaseCodeEnum;

/**
 * 根据code返回对应的枚举值
 * Created by Jun on 2018/2/11.
 */
public class CodeEnumUtil {

    public static <E extends Enum<?> & BaseCodeEnum> E codeOf(Class<E> enumClass, int code) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}
