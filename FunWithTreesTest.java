import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FunWithTreesTest {
  @Test
  public void nullTree() {
    testFlatten(null, null);
  }

  @Test
  public void exampleTree() {
    ListNode l1 = new ListNode(17, new ListNode(1));
    ListNode l2 = new ListNode(3);
    ListNode l3 = new ListNode(1);
    ListNode l4 = new ListNode(2);
    ListNode l5 = new ListNode(16);
    ListNode l6 = new ListNode(7, new ListNode(3));
    TreeNode root = new TreeNode(l1, new TreeNode(l2, new TreeNode(l4), null), new TreeNode(l3, new TreeNode(l5), new TreeNode(l6)));

    ListNode expected = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(7, new ListNode(16, new ListNode(17))))));
    testFlatten(root, expected);
  }

  public static void testFlatten(TreeNode input, ListNode expected) {
    assertTrue(isValid(FunWithTrees.flatten(input), expected));
  }

  // assumes no cycles
  public static Boolean isValid(ListNode ln1, ListNode ln2) {
    while (ln1 != null && ln2 != null && ln1.data == ln2.data) {
      ln1 = ln1.next;
      ln2 = ln2.next;
    }

    return ln1 == ln2;
  }

}
