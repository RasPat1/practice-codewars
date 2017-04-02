import java.util.*;

public class FunWithTrees {
  static ListNode flatten(TreeNode root) {
    if (root == null) {
      return null;
    }

    TreeSet<ListNode> set = new TreeSet<>(new SortedListNode());
    flatten(root, set);

    // iterate through the set
    // set each element to connect to the next
    ListNode previousNode = null;
    for (ListNode ln : set) {
      if (previousNode == null) {
        previousNode = ln;
      } else {
        previousNode.next = ln;
        previousNode = ln;
      }
    }

    // make sure last element does't point to anything
    set.last().next = null;

    return set.first();
  }

  static void flatten(TreeNode root, TreeSet set) {
    if (root != null) {
      visit(root.value, set);
      flatten(root.left, set);
      flatten(root.right, set);
    }
  }

  public static void visit(ListNode node, TreeSet set) {
    while (node != null) {
      set.add(node);
      node = node.next;
    }
  }

  static class SortedListNode implements Comparator<ListNode> {
    @Override
    public int compare(ListNode ln1, ListNode ln2) {
      Integer i1 = new Integer(ln1.data);
      Integer i2 = new Integer(ln2.data);
      return i1.compareTo(i2);
    }
  }

}


/*
class TreeNode {
  public TreeNode left;
  public TreeNode right;
  public ListNode value;

  TreeNode(ListNode value, TreeNode left, TreeNode right) {
    this.value = value;
    this.left = left;
    this.right = right;
  }

  TreeNode(ListNode value) {
    this(value, null, null);
  }
}
*/

/*
class ListNode {
  public int data;
  public ListNode next;

  ListNode(int data, ListNode next) {
    this.data = data;
    this.next = next;
  }

  ListNode(int data) {
    this(data, null);
  }
}
*/