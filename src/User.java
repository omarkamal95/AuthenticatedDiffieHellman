
public class User {
	long q;
	long a;
	long X;
	long Y;
	
	public User(long Qinit, long Ainit){
		this.q = Qinit;
		this.a = Ainit;
	}
	public void generateRandomX(){
		this.X = (long)((q-1) * Math.random());
	}
	public void generateY(){
		this.Y = (a^this.X)%q;
	}
	public long getQ() {
		return q;
	}
	public void setQ(long q) {
		this.q = q;
	}
	public long getA() {
		return a;
	}
	public void setA(long a) {
		this.a = a;
	}
	public long getX() {
		return X;
	}
	public void setX(long x) {
		X = x;
	}
	public long getY() {
		return Y;
	}
	public void setY(long y) {
		Y = y;
	}
	
	
}
