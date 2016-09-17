package com.lucidcoders.coolundertwenty.util;

import java.util.List;

public class Util {

    public static boolean checkString(String string) {
        return string != null && !string.isEmpty();
    }

    public static boolean checkList(List<?> list) {
        return list != null && list.size() > 0;
    }
}
