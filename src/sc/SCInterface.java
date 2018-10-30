package sc;

import java.util.List;

/**
 * This interface encapsulates the final payments operation
 * {@link SCInterface#printFinalPayments(List, List)} of SumCost (SC).
 * 
 * @author Eugene Yip
 *
 */
public interface SCInterface {

	/**
	 * Method to process a list of expenses and return a CSV 
	 * representation of the final payments table
	 * 
	 * @param exchangeRateInput 
	 *            the exchange rates as a list of strings
	 * @param expensesInput
	 *            the expenses as a list of strings
	 * 
	 * @return a CSV representation of the final payments table
	 * 
	 * @throws SCException
	 *             thrown in case that the final payments could not be calculated
	 */
	public String printFinalPayments(List<String> exchangeRateInput, List<String> expensesInput) throws SCException;

}
