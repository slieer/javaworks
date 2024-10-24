package treeSet;

/**
 * An item with a description and a part number.
 */
public record Item(String description, int partNumber) implements Comparable<Item>
{
   public int compareTo(Item other)
   {
      int diff = Integer.compare(partNumber, other.partNumber);
      return diff != 0 ? diff : description.compareTo(other.description);
   }
}
