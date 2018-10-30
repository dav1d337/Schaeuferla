package sc;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
// import java.util.Enumeration;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(urlPatterns = { "/sumCost" })
public class HtmlReader extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

		Path p1 = Paths.get("inputExpenses.txt");
		Path p2 = Paths.get("inputRates.txt");
		PrintWriter out = res.getWriter();
//		Enumeration<String> fields = req.getParameterNames();
		
		try (BufferedWriter writer = Files.newBufferedWriter(p1, Charset.forName("UTF-8"))) {

//			while (fields.hasMoreElements()) {
//				String param = fields.nextElement();
//				String line = req.getParameter(param);
//				out.println(line);
//				
//				writer.write(line);
//			}
			
			writer.write(req.getParameter("t1"));
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		try (BufferedWriter writer = Files.newBufferedWriter(p2, Charset.forName("UTF-8"))) {

//			while (fields.hasMoreElements()) {
//				String param = fields.nextElement();
//				String line = req.getParameter(param);
//				out.println(line);
//				
//				writer.write(line);
//			}
			writer.write(req.getParameter("t2"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		Main sc = new Main(p1.toString(), p2.toString());
		Main.start();
		out.println(sc.print());

	}

}
