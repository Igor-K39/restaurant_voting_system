package ru.kopyshev.rvs.util;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;

import static com.sun.xml.fastinfoset.stax.events.Util.isEmptyString;

@UtilityClass
public class CollectionUtil {

    public static <T> List<T> getImmutableListIfNull(List<T> list) {
        return list == null ? List.of() : list;
    }

    public static <K, V> void addIfNotEmpty(V value, K key, Map<K, V> map) {
        if (value != null && !isEmptyString(value.toString())) {
            map.put(key, value);
        }
    }

    @SafeVarargs
    public static <K, V> void addAllIfNotEmpty(Map<K, V> source, Map<K, V> destination, K... keys) {
        for (K key : keys) {
            addIfNotEmpty(source.get(key), key, destination);
        }
    }
}
