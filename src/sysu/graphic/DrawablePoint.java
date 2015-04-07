package sysu.graphic;

public class DrawablePoint {
	public float x;
	public float y;

	public DrawablePoint() {
		x = y = 0;
	}
	
	public DrawablePoint(float temx, float temy) {
		x = temx;
		y = temy;
	}

	public float dis(float x2, float y2) {
		return (float)Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
	}
}
