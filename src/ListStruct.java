
public class ListStruct {
	
	private Node first;
	
	private class Node {
		Cell data;
		Node top, right, bottom, left;
		
		public Node (Cell cell) {
			this.data = cell;
		}
		
	}
		
	
}
