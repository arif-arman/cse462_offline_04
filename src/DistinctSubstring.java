import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

import lib.QuickSort;

public class DistinctSubstring {

	String text;
	PrintWriter out;
	int n;
	int stop;
	int [][] P;
	int [] sa;
	
	public DistinctSubstring(String text) throws FileNotFoundException {
		// TODO Auto-generated constructor stub
		this.text = text;
		n = text.length();
		out = new PrintWriter(new File("output_distinct_substring.txt"));
		out.println("--- Text ---");
		out.println(text);
		naive();
		sa = suffixArray();
		distSubstr();
		out.close();
	}
	
	void naive() {
		out.println("--- Naive ---");
		SortedSet<String> set = new TreeSet<String>();
		for (int length=1;length<=n;length++) {
			for(int i=0; i < n - length + 1; i++) {
		        String sub = text.substring(i,length+i);
		        //out.println(sub);
		        set.add(sub);
		    }
		}
		
//	    for (String str : set) {
//	        out.println(str);
//	    }
	    out.println(set.size());
	    
	}
	
	
	int [] suffixArray() {
		int [] sa = new int[n];
		int MAXLG = 17;
		int [][] P = new int[MAXLG][n];
		Entry [] L = new Entry[n];
		for (int i=0;i<n;i++) L[i] = new Entry();
		int i, stop, count;
		for (i=0;i<n;i++) 
			P[0][i] = text.charAt(i) - 'a';
		for (stop = 1, count = 1; count>>1 < n; stop++, count <<= 1) {
			for (i=0;i<n;i++) {
				L[i].nr[0] = P[stop-1][i];
				L[i].nr[1] = i + count < n ? P[stop-1][i+count] : -1;
				L[i].p = i;
			}
			Arrays.sort(L, new CustomComparator());
			//System.out.println(count);
			//for (int k=0;k<n;k++) System.out.println(L[k]);

			for (i=0;i<n;i++) 
				P[stop][L[i].p] = i > 0 && L[i].nr[0] == L[i-1].nr[0] && L[i].nr[1] == L[i-1].nr[1] ?
						P[stop][L[i-1].p] : i;
//			for (i=0;i<n;i++) System.out.print(P[stop][i]);
//			System.out.println();
		}
		
//		for (i=0;i<stop;i++) {
//			for (int j=0;j<n;j++) {
//				System.out.print(P[i][j] + " ");
//			}
//			System.out.println();
//		}
		//System.out.println(stop);
		for (i=0;i<n;i++)
			sa[i] = P[stop-1][i];
//		for (int j=0;j<n;j++) {
//			System.out.print(sa[j] + " ");
//		}
		this.stop = stop;
		this.P = P;
		return sa;
	}
	
	int lcp(int x, int y) {
		int k, ret = 0;
		if (x==y) return n-x;
		for (k=stop-1; k>=0 && x<n && y<n; k--) {
			if (P[k][x] == P[k][y]) {
				x += 1<<k;
				y += 1<<k;
				ret += 1<<k;
			}
		}
		return ret;
	}
	
	void distSubstr() {
		out.println("--- Suffix Array & LCP ---");
		QuickSort qs = new QuickSort();
		qs.sort(sa);
		int [] sorted = qs.getIndex();
//		for (int i=0;i<n;i++) 
//			System.out.print(sa[i] + " ");
//		System.out.println();
//		for (int i=0;i<n;i++) 
//			System.out.print(sorted[i] + " ");
//		System.out.println();
		int sub = n;
		for (int i=0;i<n-1;i++) {
			sub += (n-sa[sorted[i+1]]) - lcp(sorted[i],sorted[i+1]);
		}
		out.println(sub);
		
	}
	
	
	
	class CustomComparator implements Comparator<Entry> {
		@Override
		public int compare(Entry o1, Entry o2) {
			// TODO Auto-generated method stub
			return o1.nr[0] == o2.nr[0] ? (o1.nr[1] < o2.nr[1] ? -1:1) : 
				(o1.nr[0] < o2.nr[0] ? -1:1);
		}
	}
	
	class Entry {
		int [] nr = new int[2];
		int p;
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			String s = nr[0] + " " + nr[1] + " " + p;
			return s;
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(new File("input_distinct_substring.txt"));
		DistinctSubstring ds = new DistinctSubstring(input.nextLine());
		input.close();
	}

}
