package ru.kopyshev.rvs;

import lombok.experimental.UtilityClass;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.kopyshev.rvs.util.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;

@UtilityClass
public class MatcherFactory {

    public static <T> Matcher<T> usingAssertions(Class<T> tClass, BiConsumer<T, T> assertion,
                                                 BiConsumer<Iterable<T>, Iterable<T>> iterableAssertion) {
        return new Matcher<>(tClass, assertion, iterableAssertion);
    }

    public static <T> Matcher<T> usingIgnoreFieldComparator(Class<T> tClass, String... fieldsToIgnore) {
        return usingAssertions(tClass,
                (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields(fieldsToIgnore).isEqualTo(e),
                (a, e) -> assertThat(a).usingRecursiveFieldByFieldElementComparatorIgnoringFields(fieldsToIgnore).isEqualTo(e));
    }

    public static class Matcher<T> {
        private final Class<T> tClass;
        private final BiConsumer<T, T> assertion;
        private final BiConsumer<Iterable<T>, Iterable<T>> iterableAssertion;

        private Matcher(Class<T> tClass, BiConsumer<T, T> assertion, BiConsumer<Iterable<T>, Iterable<T>> iterableAssertion) {
            this.tClass = tClass;
            this.assertion = assertion;
            this.iterableAssertion = iterableAssertion;
        }

        public void assertMatch(T actual, T expected) {
            assertion.accept(actual, expected);
        }

        public void assertMatch(Iterable<T> actual, Iterable<T> expected) {
            iterableAssertion.accept(actual, expected);
        }

        @SafeVarargs
        public final void assertMatch(Iterable<T> actual, T... expected) {
            var listExpected = List.of(expected);
            iterableAssertion.accept(actual, listExpected);
        }

        public ResultMatcher contentJson(T tExpected) {
            return result -> {
                T tActual = JsonUtil.readValue(getContent(result), tClass);
                assertMatch(tActual, tExpected);
            };
        }

        public ResultMatcher contentJson(Iterable<T> tExpected) {
            return result -> {
                List<T> tActual = JsonUtil.readValues(getContent(result), tClass);
                assertMatch(tActual, tExpected);
            };
        }

        @SafeVarargs
        public final ResultMatcher contentJson(T... tExpected) {
            return contentJson(Arrays.asList(tExpected));
        }

        public T readFromJson(ResultActions actions) throws UnsupportedEncodingException {
            String content = getContent(actions.andReturn());
            return JsonUtil.readValue(content, tClass);
        }

        public String getContent(MvcResult result) throws UnsupportedEncodingException {
            return result.getResponse().getContentAsString();
        }
    }
}
