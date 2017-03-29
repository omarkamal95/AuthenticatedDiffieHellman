import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;

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
	public void generateDigitalSignature(File PublicKey) {
		try {

			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
			
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(1024, random);
			
			KeyPair pair = keyGen.generateKeyPair();
			PrivateKey priv = pair.getPrivate();
			PublicKey pub = pair.getPublic();
			
			Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
			dsa.initSign(priv);
			
			FileInputStream fis = new FileInputStream(PublicKey);
			BufferedInputStream bufin = new BufferedInputStream(fis);
			byte[] buffer = new byte[1024];
			int len;
			while ((len = bufin.read(buffer)) >= 0) {
			    dsa.update(buffer, 0, len);
			};
			bufin.close();
			byte[] realSig = dsa.sign();
			
			/* save the signature in a file */
			FileOutputStream sigfos = new FileOutputStream("sig");
			sigfos.write(realSig);
			sigfos.close();
			
			/* save the public key in a file */
			byte[] key = pub.getEncoded();
			FileOutputStream keyfos = new FileOutputStream("suepk");
			keyfos.write(key);
			keyfos.close();

	        } catch (Exception e) {
	            System.err.println("Caught exception " + e.toString());
	        }
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
