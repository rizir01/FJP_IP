
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;


public class HelloRunner
{

	@SuppressWarnings("deprecation")
	public static void main(String[] args)
	{
		ANTLRInputStream input;
		
		try {
			Reader r = new FileReader(new File("test.txt"));
			input = new ANTLRInputStream(r);
			IPv4Lexer lexer = new IPv4Lexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			
			IPv4Parser parser = new IPv4Parser(tokens);
			ParseTree tree = parser.program(); // begin parsing at rule 'r'
			System.out.println(tree.toStringTree(parser)); // print LISP-style tree
			JFrame frame = new JFrame("Antlr AST");
			JPanel panel = new JPanel();
			TreeViewer viewr = new TreeViewer(Arrays.asList(
			        parser.getRuleNames()),tree);
			viewr.setScale(1.5);//scale a little
			panel.add(viewr);
			frame.add(panel);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(400,400);
			frame.setVisible(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
