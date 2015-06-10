package org.spaconference.rts;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.spaconference.rts.ExampleRunner.Way;


@RunWith(ExampleRunner.class)
public class F_FindingAndNull {

    static class Hat {
        public final String name;
        public final int stockSmall;
        public final int stockMedium;
        public final int stockLarge;

        public Hat(String name, int stockSmall, int stockMedium, int stockLarge) {
            this.name = name;
            this.stockSmall = stockSmall;
            this.stockMedium = stockMedium;
            this.stockLarge = stockLarge;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    final Hat deerstalker = new Hat("deerstalker", 0, 3, 1);
    final Hat bowler = new Hat("bowler", 1, 5, 2);
    final Hat topper = new Hat("topper", 1, 2, 4);
    final Hat trilby = new Hat("trilby", 0, 5, 3);
    final Hat panama = new Hat("panama", 3, 1, 4);
    final Hat stetson = new Hat("stetson", 0, 2, 6);
    final List<Hat> hats = asList(deerstalker, bowler, topper, trilby, panama, stetson);

    public static Hat findHatOldWay(List<Hat> hats, Predicate<? super Hat> criteria) {
        for (Hat hat : hats) {
            if (criteria.test(hat)) {
                return hat;
            }
        }

        return null;
    }

    @Way
    public static String oldWay(List<Hat> hats, Predicate<? super Hat> criteria) {
        Hat found = findHatOldWay(hats, criteria);
        return found != null ? found.name : "<nothing found>";
    }

    @Way
    public static String newWay(List<Hat> hats, Predicate<? super Hat> criteria) {
        return hats.stream().filter(criteria).findFirst().map(h->h.name).orElse("<nothing found>");
    }


    @Test
    public void findSmallHatInStock(BiFunction<List<Hat>,Predicate<? super Hat>,String> f) {
        String found = f.apply(hats, h->h.stockSmall > 0);
        assertThat(found, equalTo("bowler"));
    }

    @Test
    public void findHatWithOneMediumInStock(BiFunction<List<Hat>,Predicate<? super Hat>,String> f) {
        String found = f.apply(hats, h->h.stockMedium == 1);
        assertThat(found, equalTo("panama"));
    }

    @Test
    public void findHatWithExcessStock(BiFunction<List<Hat>,Predicate<? super Hat>,String> f) {
        Predicate<Hat> hasExcessStock = h -> h.stockSmall > 8 || h.stockMedium > 10 || h.stockLarge > 7;

        String found = f.apply(hats, hasExcessStock);
        assertThat(found, equalTo("<nothing found>"));
    }
}
