package org.spaconference.rts.solutions;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spaconference.rts.runner.ExampleRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.groupingBy;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.spaconference.rts.runner.ExampleRunner.Way;


@RunWith(ExampleRunner.class)
public class ExF_Grouping {

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
    public static Product duncesCap = new Product("dunce's cap", "hats");
    public static Product yFronts = new Product("y-fronts", "pants");
    public static Product boxers = new Product("boxers", "dogs");

    @Way
    public static Map<String, List<Product>> oldWay(List<Product> products) {
        SortedMap<String, List<Product>> categories = new TreeMap<>();

        for (Product p : products) {
            if (categories.containsKey(p.category)) {
                categories.get(p.category).add(p);
            } else {
                List<Product> categoryProducts = new ArrayList<>();
                categoryProducts.add(p);
                categories.put(p.category, categoryProducts);
            }
        }

        return categories;
    }

    @Way
    public static Map<String, List<Product>> newWay(List<Product> products) {
        return products.stream().collect(groupingBy(p -> p.category));
    }

    @Test
    public void test(Function<List<Product>, Map<String, Set<Product>>> f) {
        List<Product> products = asList(
                winklePickers, bovverBoots, fez, deerstalker, duncesCap, yFronts, boxers);

        // Bonus exercise - uncomment this line and group into sets
        //java.util.Collections.shuffle(products);

        Map<String, List<Product>> categorised = ImmutableMap.of(
                "shoes", asList(winklePickers, bovverBoots),
                "hats", asList(fez, deerstalker, duncesCap),
                "pants", asList(yFronts),
                "dogs", asList(boxers));

        assertThat(f.apply(products), equalTo(categorised));
    }
}
