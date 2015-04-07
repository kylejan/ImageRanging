package sysu.algorithm;




public class Node {
	
	public double x;
	public double y;
	private static final double NEARZERO = 0.00000001;
	
	public Node()
	{
		x = 0;
		y = 0;
	}
	
	public Node(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public boolean equals(Node another)
	{
		if (another == null)
			return false;
		else 
			return ((Math.abs(x-another.x) < NEARZERO) && 
					(Math.abs(y-another.y) < NEARZERO));
	}
}
