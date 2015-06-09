package org.spaconference.rts;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.spaconference.rts.ExampleRunner.Way;


@RunWith(ExampleRunner.class)
public class ExE_Grouping {
    static class Product {
        public final int id;
        public final String name;
        public final String category;

        public Product(int id, String name, String category) {
            this.id = id;
            this.name = name;
            this.category = category;
        }


        @Override
        public String toString() {
            return "Product{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", category='" + category + '\'' +
                    '}';
        }
    }

    public static Product converse = new Product(1, "converse", "shoes");
    public static Product bovverBoots = new Product(2, "bovver boots", "shoes");

    public static Product dunceHat = new Product(3, "dunce hat", "hats");
    public static Product deerstalker = new Product(4, "deerstalker", "hats");
    public static Product wizardsHat = new Product(5, "wizards hat", "hats");

    public static Product yFronts = new Product(6, "y-fronts", "pants");
    public static Product boxers = new Product(7, "y-fronts", "dogs");


    @Way
    public static Map<String,List<Product>> oldWay(List<Product> products) {
        SortedMap<String, List<Product>> categories = new TreeMap<>();

        for (Product p : products) {
            if (categories.containsKey(p.category)) {
                categories.get(p.category).add(p);
            }
            else {
                List<Product> categoryProducts= new ArrayList<>();
                categoryProducts.add(p);
                categories.put(p.category, categoryProducts);
            }
        }
        return categories;
    }

    @Way
    public static Map<String,List<Product>> newWay(List<Product> products) {
        return new HashMap<>();
    }


    @Test
    public void test(Function<List<Product>, Map<String,List<Product>>> f) {
        List<Product> products = asList(
            converse, bovverBoots, dunceHat, deerstalker, wizardsHat, yFronts, boxers);

        Map<String,List<Product>> categorised = ImmutableMap.of(
            "shoes", asList(converse, bovverBoots),
            "hats", asList(dunceHat, deerstalker, wizardsHat),
            "pants", asList(yFronts),
            "dogs", asList(boxers));

        assertThat(f.apply(products), equalTo(categorised));
    }
}
