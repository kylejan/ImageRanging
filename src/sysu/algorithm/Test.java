package sysu.algorithm;


public class Test {
	
	public static void main(String args[])
	{
		/*文档1数据*/
		Node A = new Node(1397.0,668.0);
		Node B = new Node(2351.0,664.0);
		Node C = new Node(2607.0,1931.0);
		Node D = new Node(969.0,1918.0);
		Node E = new Node(1563.0,2009.0);
		Node M = new Node(2195.0,1070.0);
		Node G = new Node(1272.0,2152.0);
		Node F = new Node(1547.0,1604.0);
		Node H = new Node(1233.0,1750.0);
		Node N = new Node(2195.0,785.0);
		double AB = 88.0;
		double DC = 88.0;
		double AD = 176.0;
		double BC = 176.0;
		double EF = 27.0;
		double GH = 27.0;

		/*文档2数据
		Node A = new Node(336.0,730.0);
		Node B = new Node(556.0,736.0);
		Node C = new Node(705.0,864.0);
		Node D = new Node(388.0,866.0);
		Node E = new Node(826.0,876.0);
		Node M = new Node(267.0,811.0);
		Node G = new Node(665,808);
		Node F = new Node(836.0,735.0);
		Node H = new Node(671.0,690.0);
		Node N = new Node(266.0,735.0);
		double AB = 7.5;
		double DC = 7.5;
		double AD = 15.0;
		double BC = 15.0;
		
		double EF = 3.3;
		double GH = 3.3;
		*/

		Node rectPos[] = {A, B, C, D};
		double rectLen[] = {AB, AD};
		double EM = Function.getRealDist(M, E, rectPos, rectLen);
		double EG = Function.getRealDist(G, E, rectPos, rectLen);
		double angleGEM = Function.getRealAngle(G, E, M, rectPos, rectLen);
		double Hmn = Function.getRealHeight(M, N, E, F, G, H, EF, rectPos);
		//System.out.println(EM);
		//System.out.println(EG);
		//System.out.println(angleGEM);
		//System.out.println(Hmn);
	}
}