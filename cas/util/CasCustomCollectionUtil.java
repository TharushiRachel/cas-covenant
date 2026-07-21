package com.itechro.cas.util;

import java.util.HashSet;
import java.util.Set;

public class CasCustomCollectionUtil {

    public static <T> Set<T> mergeSet(Set<T> a, Set<T> b) {

        return new HashSet<T>() {
            {
                addAll(a);
                addAll(b);
            }
        };
    }
}
