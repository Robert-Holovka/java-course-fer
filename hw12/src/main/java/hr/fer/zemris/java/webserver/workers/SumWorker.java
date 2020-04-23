package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.common.Utils;

/**
 * Takes 2 parameters: "a" & "b" from a query String. If they do not exist this
 * class will work with default parameters instead. Writes a & b to the context
 * temporary parameters. Sums a and b, writes sum to the temporary parameters of
 * a context. If sum was odd "picture1.png" will be written to the context
 * parameters, otherwise it will be "picture2.png".
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class SumWorker implements IWebWorker {
	@Override
	public void processRequest(RequestContext context) {
		String first = context.getParameter("a");
		String second = context.getParameter("b");
		int a = 1;
		if (first != null && Utils.isInteger(first)) {
			a = Integer.parseInt(first);
		}
		int b = 2;
		if (second != null && Utils.isInteger(second)) {
			b = Integer.parseInt(second);
		}

		int sum = a + b;
		context.setTemporaryParameter("zbroj", String.valueOf(sum));
		context.setTemporaryParameter("varA", String.valueOf(a));
		context.setTemporaryParameter("varB", String.valueOf(b));
		String pictures = "/images/";
		context.setTemporaryParameter("imgName",
				(sum % 2 == 0) ? pictures + "picture1.png" : pictures + "picture2.png");

		try {
			context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
		} catch (Exception e) {
		}
	}
}