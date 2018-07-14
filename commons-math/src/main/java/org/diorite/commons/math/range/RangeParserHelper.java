package org.diorite.commons.math.range;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

final class RangeParserHelper {
    static final String[] SPLITS = {" - ", " : ", " ; ", ", ", " ", ",", ";", ":", "-"};

    private RangeParserHelper() {}

    static <T extends NumericRange<N>, N extends Number> T parse(String string,
                                                                 BiFunction<? super N, ? super N, ? extends T> creator,
                                                                 Function<? super String, ? extends N> parser,
                                                                 BiPredicate<? super N, ? super N> maxGreaterThanMin) {
        if (string.length() < 3) {
            return null;
        }
        if ((string.charAt(0) == '<') && (string.charAt(string.length() - 1) == '>')) {
            string = string.substring(1, string.length() - 1);
            if (string.length() < 3) {
                return null;
            }
        }
        String[] nums = null;
        int i = 0;
        boolean firstMinus = string.charAt(0) == '-';
        if (firstMinus) {
            string = string.substring(1);
        }
        while ((i < SPLITS.length) && (nums == null)) {
            String separator = SPLITS[i++];
            int separatorIndex = string.indexOf(separator);
            if (separatorIndex == - 1) {
                continue;
            }
            nums = new String[]{string.substring(0, separatorIndex), string.substring(separatorIndex + separator.length())};
        }
        if (nums == null) {
            return null;
        }
        N min = parser.apply(firstMinus ? ("-" + nums[0]) : nums[0]);
        if (min == null) {
            return null;
        }
        N max = parser.apply(nums[1]);
        if ((max == null) || ! maxGreaterThanMin.test(max, min)) {
            return null;
        }
        return creator.apply(min, max);
    }
}
