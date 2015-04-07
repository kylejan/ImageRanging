package sysu.interaction;
import sysu.algorithm.*;
import android.R.string;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import android.os.Environment;  
@TargetApi(8)
public class ParseAndSaveXML {
	
	public static boolean process(String fromPath, String toPath) {
		double[] result = parseXML(fromPath);
		try {
			createXML(result[0], result[1], result[2], toPath);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@SuppressLint({ "NewApi", "NewApi", "NewApi" })
	public static double[] parseXML(String path) 
	{
		double[] result = new double[3];
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File(path));
			Element root = (Element) document.getDocumentElement();
			
			//矩形定点顺序： 从矩形左上角的定点开始，顺时针罗列
			String[] pointNames = { "first_point", "second_point",
					"third_point", "fourth_point" };
			
			// 获取标定矩形信息
			Element planeInfo = (Element) root.getElementsByTagName(
					"plane_calibration").item(0);

			Element rectNode = (Element) planeInfo.getElementsByTagName(
					"rectangular_points").item(0);
			
			//矩形四点坐标
			Node[] rectPos = new Node[4];
			for (int i = 0; i < 4; i++) {
				String[] s = rectNode.getElementsByTagName(pointNames[i])
						.item(0).getTextContent().split(",");
				Node point = new Node(Integer.parseInt(s[0]),
						Integer.parseInt(s[1]));
				rectPos[i] = point;
			}
			
			//矩形边长信息
			Element width = (Element) planeInfo.getElementsByTagName(
					"rectangular_lengths").item(0);
			double[] rectLen = new double[2];
			rectLen[0] = Double.parseDouble(width.getElementsByTagName("width1")
					.item(0).getTextContent());
			rectLen[1] = Double.parseDouble(width.getElementsByTagName("width2")
					.item(0).getTextContent());

			// 获取标定高度信息
			Element heightInfo = (Element) root.getElementsByTagName(
					"height_calibration").item(0);
			Element verticalPoint = (Element) heightInfo.getElementsByTagName(
					"vertical_points").item(0);
			Node[] vPos = new Node[4];
			for (int i = 0; i < 4; i++) {
				String[] s = verticalPoint.getElementsByTagName(pointNames[i])
						.item(0).getTextContent().split(",");
				Node point = new Node(Integer.parseInt(s[0]),
						Integer.parseInt(s[1]));
				vPos[i] = point;
			}
			Element hei = (Element) heightInfo.getElementsByTagName(
					"vertical_heights").item(0);
			double[] H = new double[2];
			H[0] = Double.parseDouble(hei.getTextContent().split(";")[0]);
			H[1] = Double.parseDouble(hei.getTextContent().split(";")[1]);

			// 获取待测信息
			// 两点距离坐标
			Element len = (Element) root.getElementsByTagName(
					"plane_length_test").item(0);

			String[] s1 = len.getElementsByTagName(pointNames[0]).item(0).getTextContent().split(",");
			Node E = new Node(Integer.parseInt(s1[0]), Integer.parseInt(s1[1]));
			String[] s2 = len.getElementsByTagName(pointNames[1]).item(0)
					.getTextContent().split(",");
			Node M = new Node(Integer.parseInt(s2[0]), Integer.parseInt(s2[1]));

			// 角度三点坐标， 顺序要求角顶点坐标必须为第二个点的坐标
			Element angle = (Element) root.getElementsByTagName(
					"plane_angel_test").item(0);
			Node[] angleNodes = new Node[3];
			for (int i = 0; i < 3; i++) {
				String[] s = angle.getElementsByTagName(pointNames[i]).item(0)
						.getTextContent().split(",");
				Node point = new Node(Integer.parseInt(s[0]),
						Integer.parseInt(s[1]));
				angleNodes[i] = point;
			}

			// 高度两点坐标
			Element height = (Element) root.getElementsByTagName(
					"height_calc_coordinate").item(0);
			Node[] hNodes = new Node[2];
			for (int i = 0; i < 2; i++) {
				String[] s = height.getElementsByTagName(pointNames[i]).item(0)
						.getTextContent().split(",");
				Node point = new Node(Integer.parseInt(s[0]),
						Integer.parseInt(s[1]));
				hNodes[i] = point;
			}

			double EM = Function.getRealDist(E, M, rectPos, rectLen);
			double angleGEM = Function.getRealAngle(angleNodes[0],
					angleNodes[1], angleNodes[2], rectPos, rectLen);
			double hMN = Function.getRealHeight(hNodes[0], hNodes[1], vPos[0],
					vPos[1], vPos[2], vPos[3], H[0], rectPos);

			result[0] = EM;
			result[1] = angleGEM;
			result[2] = hMN;
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		//以数组形式放回所有计算结果
		return result;
	}

	public static void createXML(double EM, double angleGEM, double hMN,
			String fileName) throws ParserConfigurationException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();

		Element root = doc.createElement("result");
		doc.appendChild(root);

		Element len = doc.createElement("test_length");
		len.setTextContent(String.valueOf(EM));
		root.appendChild(len);

		Element angle = doc.createElement("test_angle");
		angle.setTextContent(String.valueOf(angleGEM));
		root.appendChild(angle);

		Element height = doc.createElement("test_height");
		height.setTextContent(String.valueOf(hMN));
		root.appendChild(height);

		TransformerFactory tf = TransformerFactory.newInstance();

		try {
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(doc);
			transformer.setOutputProperty(OutputKeys.ENCODING, "gb2312");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
			StreamResult result = new StreamResult(pw);
			transformer.transform(source, result);
			
		} catch (TransformerConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (TransformerException e) {
			System.out.println(e.getMessage());
		}

	}
}
