package solver;

public class Point {
	public int x, y;
	
	public Point(byte B) {
		x = (B >> 4) & 0xf;
		y = B & 0xf;
	}
	
	@Override
	public int hashCode() {
		return x * Map.N + y;
	}
	
	@Override
	public boolean equals(Object obj) {
		return x == ((Point)obj).x && y == ((Point)obj).y;
	}
	
	public byte toByte() {
		return (byte)((x << 4) | y);
	}
	
	public Point() {
		x = y = -1;
	}
	
	public Point(Point P) {
		this.x = P.x;
		this.y = P.y;
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getPos() {
		return x * Map.N + y;
	}
	
	public void move(int dir) {
		switch(dir) {
		case 0: --x; break;
		case 1: ++y; break;
		case 2: ++x; break;
		case 3: --y; break;
		}
	}
	
	public static byte move(byte B, int dir) {
		Point P = new Point(B);
		P.move(dir);
		return P.toByte();
	}
	
	public boolean isValid(int N) {
		return x >= 0 && x < N && y >= 0 && y < N;
	}
	
	public static int getManDist(Point A, Point B) {
		return Math.abs(A.x - B.x) + Math.abs(A.y - B.y);
	}
}
