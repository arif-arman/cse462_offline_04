import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class DistinctSubstring {

	String text;
	PrintWriter out;
	int n;
	
	public DistinctSubstring(String text) throws FileNotFoundException {
		// TODO Auto-generated constructor stub
		this.text = text;
		n = text.length();
		out = new PrintWriter(new File("output_distinct_substring.txt"));
		out.println("--- Text ---");
		out.println(text);
		naive();
		suffixArray();
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
		
	    for (String str : set) {
	        out.println(str);
	    }
	    out.println("Total Distinct Substrings: " + set.size());
	    
	}
	
	
	int [] suffixArray() {
		int [] sa = new int[n];
		int MAXLG = 17;
		int [][] P = new int[MAXLG][n];
		Entry [] L = new Entry[n];
		for (int i=0;i<n;i++) L[i] = new Entry();
		int N, i, stop, count;
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
		}
		for (i=0;i<MAXLG;i++) {
			for (int j=0;j<n;j++) {
				System.out.print(P[i][j] + " ");
			}
			System.out.println();
		}
		return sa;
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
		Scanner input = new Scanner(new File("input_string_matching.txt"));
		DistinctSubstring ds = new DistinctSubstring(input.nextLine());
		input.close();
	}

}
