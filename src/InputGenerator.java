import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class InputGenerator {
	private String text;
	private String pattern;
	
	
	public InputGenerator() throws FileNotFoundException {
		// TODO Auto-generated constructor stub
		text = "";
		pattern = "";
		Random rand = new Random();
		int n = rand.nextInt(10000) + 100;
		int m = rand.nextInt(5) + 10;
		for (int i=0;i<m;i++) {
			pattern += (char)(rand.nextInt(25) + 97);
		}
		for (int i=0;i<n;i++) {
			if (rand.nextInt(100) < 1) {
				text += pattern;
				i += m;
			}
			else 
				text += (char)(rand.nextInt(25) + 97);
		}
		PrintWriter out = new PrintWriter(new File("input_string_matching.txt"));
		out.println(text);
		out.println(pattern);
		out.close();
		
	}
	
	public String getText() {
		return text;
	}
	
	public String getPattern() {
		return pattern;
	}
	
}
