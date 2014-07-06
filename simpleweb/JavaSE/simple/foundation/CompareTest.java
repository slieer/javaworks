package simple.foundation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author slieer Dec 13, 2008
 */

public class CompareTest {
	/**
	 * 定义Node型对象的第二种比较方式,可以针对Node对象定义多个比较器
	 */
	static Comparator<Node> comparator = new Comparator<Node>() {
		@Override
		public int compare(Node o1, Node o2) {
			return (o1.getX() + o1.getY()) - (o2.getX() + o2.getY());
		}
	};

	public static void main(String... args) {
		List<Node> list = new ArrayList<Node>();
		Node node0 = new Node();
		Node node1 = new Node();
		Node node2 = new Node();
		Node node3 = new Node();
		Node node4 = new Node();

		node0.setX(100);
		node0.setY(-102);
		node1.setX(7);
		node1.setY(2);
		node2.setX(2);
		node2.setY(2);

		node3.setX(0);
		node3.setY(2);
		node4.setX(1);
		node4.setY(2);

		list.add(node0);
		list.add(node1);
		list.add(node2);
		list.add(node3);
		list.add(node4);
		list.add(node0);
		
		//self-defined comparator.
		Collections.sort(list, comparator);
		for (Node n : list) {
			System.out.println(n);
		}
		
		//default comparator.
		Collections.sort(list);		
	}
}

class Node implements Comparable<Node>{
	private int x;
	private int y;
	private String info;

	public String toString() {
		return "[x=".concat(String.valueOf(x)).concat(" y=").concat(
				String.valueOf(y)).concat("]");
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public int compareTo(Node o) {
		return this.x - o.x;
	}
}
