import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

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

	public boolean verifySignature(File yaSender, File sigYaSender, File pubSender) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, InvalidKeyException, SignatureException{
		FileInputStream keyfis = new FileInputStream(yaSender);
		byte[] encKey = new byte[keyfis.available()];  
		keyfis.read(encKey);

		keyfis.close();
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
		KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
		PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
		
		FileInputStream sigfis = new FileInputStream(sigYaSender);
		byte[] sigToVerify = new byte[sigfis.available()]; 
		sigfis.read(sigToVerify);
		sigfis.close();
		
		Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
		sig.initVerify(pubKey);
		
		FileInputStream datafis = new FileInputStream(pubSender);
		BufferedInputStream bufin = new BufferedInputStream(datafis);

		byte[] buffer = new byte[1024];
		int len;
		while (bufin.available() != 0) {
		    len = bufin.read(buffer);
		    sig.update(buffer, 0, len);
		};

		bufin.close();
		
		boolean verifies = sig.verify(sigToVerify);
		System.out.println("signature verifies: " + verifies);
		
		return verifies;


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
