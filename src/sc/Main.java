package sc;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

/**
 * This class launches the specified SumCost implementation on the
 * files passed as command-line arguments.
 * 
 * @author David Koch
 * @author Eugene Yip
 * @author Jan Boockmann
 * @author Johannes Gareis
 *
 */
public class Main {
	private static String exchangeRateFileName;
	private static String expensesFileName;
	private static String result;
	
	public Main(String txt1, String txt2) {
		exchangeRateFileName = txt2;
		expensesFileName = txt1;
	}
	
	public static void main(String[] args) throws IOException {
		
		start();
	}
	
	public static void start() throws IOException {
		// create an instance of an SCInterface
		final SCInterface sc = new SCChangeRequestImpl();

		try {
			//String  exchangeRateFileName = "C:/Users/Dave/eclipse-workspace/SC_Koch_David2/resources/exchangeInputFiles/exchangeRate1.txt";
			
			// String expensesFileName = "C:/Users/Dave/eclipse-workspace/SC_Koch_David2/resources/inputFiles/expenses1.txt";
			
			// calculate the final payments for an expenses file, with currency information
			System.out.printf("Calculating final payments for the expenses file %s%n", expensesFileName);
			System.out.printf("with the exchange rate file %s%n", exchangeRateFileName);

			result = sc.printFinalPayments(FileReaderHelper.readFileToStringList(exchangeRateFileName), FileReaderHelper.readFileToStringList(expensesFileName));
			
			// print the final payments table to System.out
			System.out.println(result);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.printf("Check that all input files have been passed as command-line arguments: %s%n", e.getMessage());
		} catch (NoSuchFileException e) {
			System.err.printf("Could not open %s%n", e.getMessage());
		} catch (SCException e) {
			// in case of an exception, print the message to System.out
			System.err.println("An exception occured while calculating the final payments:");
			System.err.println(e.getMessage());
		}
	}
	
	public String print() {
		return result;
	}

}
