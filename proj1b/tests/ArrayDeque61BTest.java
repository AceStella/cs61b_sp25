import deque.ArrayDeque61B;

import deque.Deque61B;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

     @Test
     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }

    @Test
    /** In this test, we have three different assert statements that verify that addFirst works correctly. */
    public void addFirstTestBasic() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();

        ad1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(ad1.toList()).containsExactly("back").inOrder();

        ad1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(ad1.toList()).containsExactly("middle", "back").inOrder();

        ad1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(ad1.toList()).containsExactly("front", "middle", "back").inOrder();

        /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
           to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
           but not ["front", "middle", "back"].
         */
    }

    @Test
    /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
     *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
    public void addLastTestBasic() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();

        ad1.addLast("front"); // after this call we expect: ["front"]
        ad1.addLast("middle"); // after this call we expect: ["front", "middle"]
        ad1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(ad1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    /** This test performs interspersed addFirst and addLast calls. */
    public void addFirstAndAddLastTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();

        /* I've decided to add in comments the state after each call for the convenience of the
           person reading this test. Some programmers might consider this excessively verbose. */
        ad1.addLast(0);   // [0]
        ad1.addLast(1);   // [0, 1]
        ad1.addFirst(-1); // [-1, 0, 1]
        ad1.addLast(2);   // [-1, 0, 1, 2]
        ad1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(ad1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
    }

    // Below, you'll write your own tests for LinkedListDeque61B.

    @Test
    public void isEmptyTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();

        assertThat(ad1.isEmpty()).isTrue();
        ad1.addFirst(1);
        assertThat(ad1.isEmpty()).isFalse();
    }

    @Test
    public void sizeTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();

        assertThat(ad1.size()).isEqualTo(0);
        ad1.addFirst(1);
        ad1.addFirst(2);
        ad1.addFirst(3);
        assertThat(ad1.size()).isEqualTo(3);
    }

    @Test
    public void getTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();

        ad1.addFirst(3);
        ad1.addFirst(2);
        ad1.addFirst(1);
        assertThat(ad1.get(0)).isEqualTo(1);
        assertThat(ad1.get(-1)).isEqualTo(null);
        assertThat(ad1.get(3)).isEqualTo(null);
    }

    @Test
    public void removeFirstTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();

        ad1.addLast(0);   // [0]
        ad1.addLast(1);   // [0, 1]
        ad1.addFirst(-1); // [-1, 0, 1]
        ad1.addLast(2);   // [-1, 0, 1, 2]
        ad1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(ad1.removeFirst()).isEqualTo(-2);
        assertThat(ad1.toList()).containsExactly(-1, 0, 1, 2).inOrder();
    }

    @Test
    public void removeLastTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();

        ad1.addLast(0);   // [0]
        ad1.addLast(1);   // [0, 1]
        ad1.addFirst(-1); // [-1, 0, 1]
        ad1.addLast(2);   // [-1, 0, 1, 2]
        ad1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(ad1.removeLast()).isEqualTo(2);
        assertThat(ad1.toList()).containsExactly(-2, -1, 0, 1).inOrder();
    }

    @Test
    public void resizingUpTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();

        ad1.addLast(0);   // [0]
        ad1.addLast(1);   // [0, 1]
        ad1.addFirst(-1); // [-1, 0, 1]
        ad1.addLast(2);   // [-1, 0, 1, 2]
        ad1.addFirst(-2); // [-2, -1, 0, 1, 2]
        ad1.addLast(3);   // [-2, -1, 0, 1, 2, 3]
        ad1.addLast(4);   // [-2, -1, 0, 1, 2, 3, 4]
        ad1.addLast(5);   // [-2, -1, 0, 1, 2, 3, 4, 5]
        ad1.addLast(6);   // [-2, -1, 0, 1, 2, 3, 4, 5, 6]

        assertThat(ad1.size()).isEqualTo(9);
        assertThat(ad1.toList()).containsExactly(-2, -1, 0, 1, 2, 3, 4, 5, 6).inOrder();

        Deque61B<Integer> ad2 = new ArrayDeque61B<>();

        ad2.addLast(0);   // [0]
        ad2.addLast(1);   // [0, 1]
        ad2.addFirst(-1); // [-1, 0, 1]
        ad2.addLast(2);   // [-1, 0, 1, 2]
        ad2.addFirst(-2); // [-2, -1, 0, 1, 2]
        ad2.addLast(3);   // [-2, -1, 0, 1, 2, 3]
        ad2.addLast(4);   // [-2, -1, 0, 1, 2, 3, 4]
        ad2.addLast(5);   // [-2, -1, 0, 1, 2, 3, 4, 5]
        ad2.addFirst(-3); // [-3, -2, -1, 0, 1, 2, 3, 4, 5]

        assertThat(ad2.size()).isEqualTo(9);
        assertThat(ad2.toList()).containsExactly(-3, -2, -1, 0, 1, 2, 3, 4, 5).inOrder();
    }

    @Test
    public void resizingDownTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();

        for (int i = 0; i < 20; i++) {
            ad1.addLast(i);
        }

        for (int i = 0; i < 16; i++) {
            ad1.removeFirst();
        }

        assertThat(ad1.size()).isEqualTo(4);
        assertThat(ad1.toList()).containsExactly(16, 17, 18, 19).inOrder();
    }

    @Test
    public void iteratorTest() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        ad.addLast("middle");
        ad.addFirst("front");
        ad.addLast("back");
        ad.addFirst("very front");
        // Expected order: ["very front", "front", "middle", "back"]

        // Test using the enhanced for-loop (which relies on the iterator)
        List<String> iteratedItems = new ArrayList<>();
        for (String item : ad) {
            iteratedItems.add(item);
        }
        assertThat(iteratedItems).containsExactly("very front", "front", "middle", "back").inOrder();

        // Test iterator on an empty deque
        Deque61B<Integer> emptyDeque = new ArrayDeque61B<>();
        assertThat(emptyDeque.iterator().hasNext()).isFalse();
        // Also test the enhanced for-loop on an empty deque
        for (Integer item : emptyDeque) {
            assertWithMessage("Should not be able to iterate over an empty deque").fail();
        }
    }

    @Test
    public void equalsTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        ad1.addLast(10);
        ad1.addFirst(5);
        ad1.addLast(20); // Deque is [5, 10, 20]

        Deque61B<Integer> ad2 = new ArrayDeque61B<>();
        ad2.addLast(10);
        ad2.addFirst(5);
        ad2.addLast(20); // Deque is [5, 10, 20]

        // Test for equality with itself and an identical deque
        assertThat(ad1).isEqualTo(ad1);
        assertThat(ad1).isEqualTo(ad2);

        // Test for inequality: different size
        ad2.addLast(30); // ad2 is now [5, 10, 20, 30]
        assertThat(ad1).isNotEqualTo(ad2);

        // Test for inequality: same size, different elements
        Deque61B<Integer> ad3 = new ArrayDeque61B<>();
        ad3.addLast(10);
        ad3.addFirst(5);
        ad3.addLast(99); // Deque is [5, 10, 99]
        assertThat(ad1).isNotEqualTo(ad3);

        // Test for inequality: same elements, different order
        Deque61B<Integer> ad4 = new ArrayDeque61B<>();
        ad4.addFirst(10);
        ad4.addFirst(5);
        ad4.addLast(20); // Deque is [5, 10, 20] -> Oops, my comment was wrong. Let's fix.
        // ad4: addFirst(10) -> [10], addFirst(5) -> [5, 10], addLast(20) -> [5, 10, 20]
        // This is actually equal. Let's create a non-equal one.
        Deque61B<Integer> ad5 = new ArrayDeque61B<>();
        ad5.addLast(5);
        ad5.addLast(20);
        ad5.addLast(10); // Deque is [5, 20, 10]
        assertThat(ad1).isNotEqualTo(ad5);

        // Test against null and different object types
        assertThat(ad1).isNotEqualTo(null);
        List<Integer> arrayList = List.of(5, 10, 20);
        assertThat(ad1).isNotEqualTo(arrayList);

        // Test empty deques
        Deque61B<String> empty1 = new ArrayDeque61B<>();
        Deque61B<String> empty2 = new ArrayDeque61B<>();
        assertThat(empty1).isEqualTo(empty2);
    }

    @Test
    public void toStringTest() {
        // NOTE: These tests assume your desired format is "{item1, item2, ...}"
        // for non-empty deques and "{}" for empty ones. Your implementation
        // may produce something different, like "[item1, item2, ...]".
        // Adjust the expected strings if your format is different.

        // Test an empty deque
        Deque61B<Integer> emptyDeque = new ArrayDeque61B<>();
        assertThat(emptyDeque.toString()).isEqualTo("{}");

        // Test a deque with one element
        Deque61B<String> singleItemDeque = new ArrayDeque61B<>();
        singleItemDeque.addLast("lonely");
        assertThat(singleItemDeque.toString()).isEqualTo("{lonely}");

        // Test a deque with multiple elements
        Deque61B<Integer> multiItemDeque = new ArrayDeque61B<>();
        multiItemDeque.addLast(1);
        multiItemDeque.addLast(2);
        multiItemDeque.addLast(3);
        assertThat(multiItemDeque.toString()).isEqualTo("{1, 2, 3}");

        // Test after adds and removes to ensure correctness
        multiItemDeque.addFirst(0);
        multiItemDeque.removeLast(); // Deque is now [0, 1, 2]
        assertThat(multiItemDeque.toString()).isEqualTo("{0, 1, 2}");
    }
}
