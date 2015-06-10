package org.spaconference.rts;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spaconference.rts.runner.ExampleRunner;

import java.util.*;
import java.util.function.Function;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static java.util.Collections.shuffle;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.spaconference.rts.runner.ExampleRunner.Way;


@RunWith(ExampleRunner.class)
public class ExE_Grouping {
    static class Product {
        public final String name;
        public final String category;

        public Product(String name, String category) {
            this.name = name;
            this.category = category;
        }

        @Override
        public String toString() {
            return "Product{name='" + name + '\'' + ", category='" + category + '\'' + '}';
        }
    }

    public static Product winklePickers = new Product("winkle pickers", "shoes");
    public static Product bovverBoots = new Product("bovver boots", "shoes");
    public static Product fez = new Product("fez", "hats");
    public static Product deerstalker = new Product("deerstalker", "hats");
    public static Product duncesCap = new Product("dunces's cap", "hats");
    public static Product yFronts = new Product("y-fronts", "pants");
    public static Product boxers = new Product("boxers", "dogs");

    @Way
    public static Map<String,Set<Product>> oldWay(List<Product> products) {
        SortedMap<String, Set<Product>> categories = new TreeMap<>();

        for (Product p : products) {
            if (categories.containsKey(p.category)) {
                categories.get(p.category).add(p);
            }
            else {
                Set<Product> categoryProducts= new HashSet<>();
                categoryProducts.add(p);
                categories.put(p.category, categoryProducts);
            }
        }

        return categories;
    }

    @Way
    public static Map<String,Set<Product>> newWay(List<Product> products) {
        return new HashMap<>();
    }


    @Test
    public void test(Function<List<Product>,Map<String,Set<Product>>> f) {
        List<Product> products = asList(
                winklePickers, bovverBoots, fez, deerstalker, duncesCap, yFronts, boxers);
        shuffle(products);

        Map<String,Set<Product>> categorised = ImmutableMap.<String,Set<Product>>of(
            "shoes", newHashSet(winklePickers, bovverBoots),
            "hats", newHashSet(fez, deerstalker, duncesCap),
            "pants", newHashSet(yFronts),
            "dogs", newHashSet(boxers));

        assertThat(f.apply(products), equalTo(categorised));
    }
}
