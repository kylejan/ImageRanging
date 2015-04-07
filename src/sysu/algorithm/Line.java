package sysu.algorithm;



/*
  use ax+by+c=0 to express a line
*/
public class Line {
	
	public double a;
	public double b;
	public double c;
	private static final double NEARZERO = 0.00000001;
	
	public Line ()
	{
		a = b = c =0;
	}
	
	/*use a,b,c to define a line*/
	public Line (double a, double b, double c)
	{
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	/*define a line using two points, n1 and n2*/
	public Line (Node n1, Node n2)
	{
		//if the line parallels the y-axis 
		if(n1.x == n2.x)
		{
			a = 1;
			b = 0;
			c = -n1.x;
		}
		//or x-axis
		else if(n1.y == n2.y)
		{
			a = 0;
			b = 1;
			c = -n1.y;
		}
		//otherwise
		else
		{
			double k,z;
			k = (n2.y - n1.y) / (n2.x - n1.x);
			z = n1.y - k*n1.x; // b = y2 - k*x2;

			a = k;
			b = -1;
			c = z;
		}
	}
	/*override the equals method*/
	public boolean equals (Line l)
	{
		if (l == null)
			return false;
		else
			return (Math.abs(a - l.a) < NEARZERO
					&& Math.abs(b - l.b) < NEARZERO
					&& Math.abs(c - l.c) < NEARZERO);
	}
	
}
