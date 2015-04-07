Traffic
=======

about how to use pictures to gain information.

周荣笔记：
1、所有画出的边都没有判别方向，以后改善；

Algorithm API
============

1.计算平面的线段长度
--------------------
```
public static double getRealDist(Node E, Node M, Node rectPos[], double rectLen[])  
```
E,M 为所求线段两个端点，顺序无关  
rectPos 为矩形坐标数组，先生成数组 Node rectPos[] = {A, B, C, D} 坐标顺序有关，再传入  
rectLen 为矩形长宽数组，先生成数组 double rectLen] = {AB, AD} 顺序有关，再传入  
调用  
```
double EM = Function.getRealDist(E, M, rectPos, rectLen);  
```
2.计算平面的角度
----------------
```
public static double getRealAngle(Node G, Node E, Node M, Node rectPos[], double rectLen[])  
```
G，E，M为角度三点坐标，按角度命名的顺序传入  
rectPos和rectLen和上面一样  
调用，返回角度度数，非弧度
```
double angleGEM = Function.getRealAngle(G, E, M, rectPos, rectLen);  
```
3.计算高度
----------
```
public static double getRealHeight(Node M, Node N, Node E, Node F, Node G, Node H, double h, Node rectPos[]) 
```
M,N为待求高度的线段两点,M为与平面相交一点
(E,F) (G,H)分别为已知高度的两条竖直线段的两点E、G为垂足，线段高度为h
rectPos为已知平面矩形的数据
调用
```
double Hmn = Function.getRealHeight(M, N, E, F, G, H, EF, rectPos);
```




