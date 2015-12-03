import java.util.HashSet;
import java.util.Set;

public class DistinctSubstring {

	String text;
	
	public DistinctSubstring() {
		// TODO Auto-generated constructor stub
		naive(5);
	}
	
	int naive(int length) {
		
	    Set<String> set = new HashSet<String>();
	    for(int i=0; i < text.length() - length + 1; i++) {
	        String sub = text.substring(i,length+i);
	        set.add(sub);
	    }
	    for (String str : set) {
	        System.out.println(str);
	    }
	    return set.size();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DistinctSubstring ds = new DistinctSubstring();
	}

}
