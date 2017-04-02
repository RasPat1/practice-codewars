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