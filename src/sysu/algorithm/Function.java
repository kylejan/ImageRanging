package sysu.algorithm;

public class Function {
	
	/*计算两条直线的交点并返回，平行无交点返回null*/
	private static Node intersectionPoint(Line l1, Line l2)
	{
		Node newPoint = new Node();
		double x = 0, y = 0;
		
		if ((l1.a == 0 && l2.a == 0) || (l1.a == 0 && l1.b == 0))
			return null;
		else if ((l1.b == 0 && l2.b == 0) || (l2.a == 0 && l2.b == 0))
			return null;
		else if (l1.a == 0 && l2.a != 0)
		{
			y = -l1.c / l1.b;
			x = -(l2.c + l2.b*y) / l2.a;
		}
		else if(l1.a != 0 && l2.a == 0)
		{
			y = -l2.c / l2.b;
			x = -(l1.c + l1.b*y) / l1.a;
		}
		else if(l1.a != 0 && l2.a != 0)
		{
			double temp = -l1.a / l2.a;
			y = -(l2.c*temp + l1.c) / (l2.b*temp + l1.b);
			x = -(l1.c + l1.b*y) / l1.a;
		}

		newPoint.x = x;
		newPoint.y = y;

		return newPoint;
	}

	/*返回两个点的距离*/
	private static double dist(Node n1, Node n2)
	{
		return Math.sqrt((n1.x - n2.x) * (n1.x - n2.x) + (n1.y - n2.y) * (n1.y - n2.y));
	}
	
	/*返回四个点的CR值*/
	private static double CR(Node n1, Node n2, Node n3, Node v)
	{
		return dist(n1, n2) * dist(n3, v) / (dist(n1, n3) * dist(n2, v));
	}
	
	private static double calDistanceOverSameLine(Node v, Node n1, Node n2, Node n3, Node n4, double d)
	{
	    if( n1.equals(n3) && n2.equals(n4) )
	        return d;
	    if(n1.equals(n3))
	    {
	        Node temp = n3;
	        n3 = n4;
	        n4 = temp;
	    }
		double cr1 = CR(n1,n2,n3,v);
		double cr2 = CR(n3,n4,n1,v);
		return (d/cr1)*cr2;
	}
	
	private static double calDistanceByParallelLine(Line vl, Node p1, Node p2, Node a1, Node a2, double d)
	{
		//必须的像素点信息是a, b, e, m=>p1, p2, a1, a2
		//必须的距离d = p1p2
		Line l1 = new Line(p1, p2);
		Line l2 = new Line(a1, a2);

		//p1p2 与 a1a2的灭点（交点）v1
		Node v1 = intersectionPoint(l1, l2);

		//求出p1a1与vl的交点vt
		Line l3 = new Line(p1, a1);
		Node vt = intersectionPoint(l3, vl);

		//求出vt与p2所形成的直线与直线a1a2的交点ax
		Line l4 = new Line(vt, p2);
		Node ax = intersectionPoint(l4, l2);

		//a1ax对应的空间距离则为d

		//求出 d(e,ax)*d(m,v1) / d(e,m)*d(ax,v1)，记为cr1
		double cr1 = CR(a1, ax, a2, v1);
	    //求得空间距离EM = d/cr1
		double D = d / cr1;
		return D;
	}
	
	public static double getRealDist(Node E, Node M, Node rectPos[], double rectLen[])
	{
		//解析数据
		Node A = rectPos[0];
		Node B = rectPos[1];
		Node C = rectPos[2];
		Node D = rectPos[3];
		double AB = rectLen[0];
		double AD = rectLen[1];
		double DC = AB;
		double BC = AD;
		
		//求直线ab，cd的方程及其交点
		Line lab = new Line(A, B);
		Line lcd = new Line(C, D);
		Node v1 = intersectionPoint(lab, lcd);
		
		//求直线ad，bc的方程及其交点
		Line lad = new Line(A, D);
		Line lbc = new Line(B, C);
		Node v2 = intersectionPoint(lad, lbc);
		
		//当矩形的两组边都平行时，直接用勾股定理算出距离并返回
		if (v1 == null && v2 == null)
			return AB / dist(A, B) * dist(E, M);
		//AB平行于CD时，设其灭点v1在v2平行于X轴的直线上，目的上得到与X轴平行且过v2的灭线
		else if (v1 == null)
			v1 = new Node(v2.x + 10, v2.y);
		//AD平行与BC时，情况与上面类似
		else if (v2 == null)
			v2 = new Node(v1.x + 10, v1.y);
		
		//求出灭线vl,em及其交点
		Line vl = new Line(v1, v2);
		Line lem = new Line(E, M);
		Node v3 = intersectionPoint(lem, vl);
		
		double dist = 0;
		/*
		 * 断灭点v3是否与灭点v1和v2重合
		 * 如果重合
		 * 判断待求线段em是否在其中一条边所在的直线上
		 * 如果在，调用calDistanceOverSameLine计算EM
		 * 如果不在，调用计calDistanceByParallelLine计算EM
		 */
		if (v3.equals(v1) || v3.equals(v2))
		{
			if (lem.equals(lab))
				dist = calDistanceOverSameLine(v1, A, B, E, M, AB);
			else if (lem.equals(lcd))
				dist = calDistanceOverSameLine(v1, D, C, E, M, DC);
			else if (lem.equals(lad))
				dist = calDistanceOverSameLine(v2, A, D, E, M, AD);
			else if (lem.equals(lbc))
				dist = calDistanceOverSameLine(v2, B, C, E, M, BC);
			else if (v3.equals(v1))
				dist = calDistanceByParallelLine(vl, A, B, E, M, AB);
			else if (v3.equals(v2))
				dist = calDistanceByParallelLine(vl, A, D, E, M, AD);
		}
		/*
		       如果不重合，求出待求线段em所在直线与矩形四条边的交点
		       与AB交点：z1 ，与CD交点：z2，与BC交点：z3，与AD交点：z4
		       判断待求线段em所在直线与矩形四条边的交点是否重合于c
		       如果重合
		            调用calDistanceOverSameLine计算bz1所对应的实际长度
			根据几何关系，可获得直角三角形BCZ1的斜边CZ1所对应的实际长度
			再调用calDistanceOverSameLine计算em所对应的实际长度EM，算法结束
		        如果不重合
			调用calDistanceOverSameLine计算cz2、cz3所对应的实际长度CZ2、CZ3
			然后根据勾股定理，求出z2z3所对应的实际长度Z2Z3
			再调用calDistanceOverSameLine计算出em所对应的实际长度EM，算法结束
		 */
		else 
		{
			if (!intersectionPoint(lcd, lem).equals(C))
			{
				Node z1 = intersectionPoint(lem, lab);
				Node z2 = intersectionPoint(lem, lcd);
				Node z3 = intersectionPoint(lem, lbc);
				Node z4 = intersectionPoint(lem, lad);
				
				double Cz2 = calDistanceOverSameLine(v1, D, C, C, z2, DC);
				double Cz3 = calDistanceOverSameLine(v2, B, C, C, z3, BC);
				double z2z3 = Math.sqrt(Cz2 * Cz2 + Cz3 * Cz3);
				dist = calDistanceOverSameLine(v3, z2, z3, E, M, z2z3);
			}
			else
			{
				Node B1 = intersectionPoint(lab, lem);
				double upperLen = calDistanceOverSameLine(v1, A, B, B, B1, AB);
				dist = Math.sqrt(upperLen * upperLen + BC * BC);
			}
		}
		return dist;
	}
	
	/*
	 * 计算角GEM在世界坐标中的角度
	 */
	public static double getRealAngle(Node G, Node E, Node M, Node rectPos[], double rectLen[])
	{		
		//首先计算出三条边的实际边长，再利用余弦定理求出所要求的角度
		double GE = getRealDist(G, E, rectPos, rectLen);
		double EM = getRealDist(E, M, rectPos, rectLen);
		double GM = getRealDist(G, M, rectPos, rectLen);
		
		double cosAngle = (Math.pow(EM, 2) + Math.pow(GE, 2) - Math.pow(GM, 2)) / (2 * EM * GE);
		
		return Math.acos(cosAngle) / Math.PI * 180;
	}
	
	/*计算矩阵的二范数*/
	private static double Norm(double[][] m)
	{
		double sum = 0;
		for (int i = 0; i < m.length; i++)
			for (int j = 0; j < m[i].length; j++)
				sum += m[i][j] * m[i][j];
		
		return Math.sqrt(sum);
	}
	
	/*计算两个矩阵的叉乘,确保传入的两个矩阵是可乘的，即满足m*n和n*r的条件*/
	private static double[][] crossMultiply(double[][] a, double[][] b) 
	{
		
		//生成新的矩阵并初始化，用于存放运算结果
		double[][] c = {{a[1][0] * b[2][0] - b[1][0] * a[2][0]}, 
						{a[2][0] * b[0][0] - a[0][0] * b[2][0]}, 
						{a[0][0] * b[1][0] - a[1][0] * b[0][0]}};
		return c;
	}
	
	/*计算两个矩阵的点乘*/
	private static double dotMultiply(double[][]a, double[][]b)
	{
		int m = a.length;
		int n = a[0].length;
		double sum = 0;
		
		for (int i = 0; i < m; i++) 
			for (int j = 0; j < n; j++)
				sum += a[i][j] * b[i][j];
		
		return sum;
	}
	/*
	 * M,N为待求高度的线段两点,M为与平面相交一点
	 * (E,F) (G,H)分别为已知高度的两条竖直线段的两点E、G为垂足，线段高度为h
	 * rectPos为已知平面矩形的数据
	 */
	public static double getRealHeight(Node M, Node N, Node E, Node F, Node G, Node H, double h, Node rectPos[]) 
	{
		//比例系数
		double k;
		
		//解析数据
		Node A = rectPos[0];
		Node B = rectPos[1];
		Node C = rectPos[2];
		Node D = rectPos[3];
		
		//求直线ab，cd的方程及其交点
		Line lab = new Line(A, B);
		Line lcd = new Line(C, D);
		Node v1 = intersectionPoint(lab, lcd);
		
		//求直线ad，bc的方程及其交点
		Line lad = new Line(A, D);
		Line lbc = new Line(B, C);
		Node v2 = intersectionPoint(lad, lbc);
		
		//求出灭线vl
		Line vl = new Line(v1, v2);
		
		//令 Mvl = Mvl / norm(Mvl)
		double[][] Mvl = {{vl.a}, {vl.b}, {vl.c}};
		double divisor = Norm(Mvl);
		
		for (int i = 0; i < Mvl.length; i++)
			for (int j = 0; j < Mvl[i].length; j++) 
				Mvl[i][j] /= divisor;
		
		//求出两条高度线段所在直线的灭点vz
		Line l1 = new Line(E, F);
		Line l2 = new Line(G, H);
		
		Node vz = intersectionPoint(l1, l2);
		//System.out.println(vz.x + " " + vz.y);
		
		double[][] Mvz = {{vz.x}, {vz.y}, {1}};
		double[][] ME = {{E.x}, {E.y}, {1}};
		double[][] MF = {{F.x}, {F.y}, {1}};
		
		
		//利用公式和一直高度h求出系数k
		k = (-Norm(crossMultiply(ME, MF)) / (dotMultiply(Mvl, ME) * Norm(crossMultiply(Mvz, MF)))) / h;
		
		double[][] MM = {{M.x}, {M.y}, {1}};
		double[][] MN = {{N.x}, {N.y}, {1}};
		
		double ans =  (-Norm(crossMultiply(MM, MN)) / (dotMultiply(Mvl, MM) * Norm(crossMultiply(Mvz, MN)))) / k;
		
		return ans;
		
	}
}
