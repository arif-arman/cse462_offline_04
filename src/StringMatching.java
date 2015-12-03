import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class StringMatching {

	String text;
	String pattern;
	int n;
	int m;
	PrintWriter out;
	
	public StringMatching(String text, String pattern) throws FileNotFoundException {
		// TODO Auto-generated constructor stub
		this.n = text.length();
		this.m = pattern.length();
		this.text = text;
		this.pattern = pattern;
		out = new PrintWriter(new File("output_string_matching.txt"));
		print();
		naive();
		kmpMatcher();
		multiHashing();
		out.close();
	}
	
	void print() {
		out.println("--- Text --- n: " + n);
		out.println(text);
		out.println("--- Pattern --- m: " + m);
		out.println(pattern);
	}
	
	void naive() {
		out.println("--- Naive ---");
		int counter = 0;
		for (int s=0;s<=n-m;s++) {
			boolean flag = true;
			for (int i=0;i<m;i++) {
				if (pattern.charAt(i) != text.charAt(s+i)) flag = false;
			}
			if (flag) {
				out.println(s);
				counter++;
			}
		}
		out.println("Total matches: " + counter);
	}
	
	void kmpMatcher() {
		out.println("--- KMP Matcher ---");
		int counter = 0;
		int [] pi = computePrefix();
		//for (int i=0;i<m;i++) System.out.print(pi[i] + " ");
		System.out.println();
		int q = 0;
		for (int i=0;i<n;i++) {
			while(q > 0 && pattern.charAt(q) != text.charAt(i)) q = pi[q-1];
			if (pattern.charAt(q) == text.charAt(i)) q++;
			if (q==m) {
				out.println(i-m+1);
				q = pi[q-1];
				counter++;
			}
		}
		out.println("Total matches: " + counter);
	}
	
	int [] computePrefix() {
		int [] pi = new int[m];
		pi[0] = 0;
		int k = 0;
		for (int q = 1;q<m;q++) {
			while(k > 0 && pattern.charAt(k) != pattern.charAt(q)) k = pi[k-1];
			if (pattern.charAt(k) == pattern.charAt(q)) k++;
			pi[q] = k;
		}
		return pi;
	}
	
	void multiHashing() {
		out.println("--- Multiple Hashing ---");
		int counter = 0;
		int d = 26;
		int r = 10;
		int [] qP = qPrimes(d,r);
		//for (int i=0;i<10;i++) System.out.print(qP[i] + " ");
		long [] P = new long[r];
		long [] T = new long[r];
		long [] H = new long[r];
		for (int k=0;k<r;k++) {
			H[k] = mod(((long) Math.pow(d, m-1)), qP[k]);
			//System.out.println(h);
			for (int i=0;i<m;i++) {
				P[k] = mod((d*P[k] + ((int)pattern.charAt(i) - 97)), qP[k]);
				T[k] = mod((d*T[k] + ((int)text.charAt(i) - 97)), qP[k]);
			}			
		}
		for (int s=0;s<=n-m;s++) {
			//System.out.println(p + " " + t);
			if (isEqual(P,T)) {
				out.println(s);
				counter++;
			}
			if (s < n-m) {
				for (int k=0;k<r;k++)
					T[k] = mod(d*(T[k] - ((int)text.charAt(s) - 97)*H[k]) + ((int)text.charAt(s+m) - 97), qP[k]);			
			}
				
		}
		out.println("Total matches: " + counter);
	}
	
	boolean isEqual(long [] P, long [] T) {
		int r = P.length;
		for (int i=0;i<r;i++) 
			if (P[i] != T[i]) return false;
		return true;
	}
	
	int [] qPrimes(int d, int r) {
		int [] qP = new int[10];
		int k = d;
		for (int i=0;i<10;i++) {
			for (int j=k;;j++) {
				if(isPrime(j)) {
					qP[i] = j;
					k = j+1;
					break;
				}
			}
		}
		return qP;
	}
	
	boolean isPrime(int n) {
		for (int i=2;i<Math.sqrt(n);i++) {
			if (n%i == 0) return false;
		}
		return true;
	}
	
	long mod(long x, int m) {
	    return (x%m + m)%m;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		InputGenerator ig = new InputGenerator();
		Scanner input = new Scanner(new File("input_string_matching.txt"));
		StringMatching sm = new StringMatching(input.nextLine(), input.nextLine());
		input.close();
	}

}
