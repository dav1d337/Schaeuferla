package sc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import sc.other.ExchangeReader;
import sc.other.Person;
import sc.other.RateRelation;

/**
 * This class implements the {@link SCInterface} according to the customer's
 * change request.
 *
 * @author enter-your-name-here
 *
 */
public class SCChangeRequestImpl implements SCInterface {

	private List<RateRelation> rateRelations = new ArrayList<RateRelation>();
	private ExchangeReader exchangeReader;

	/**
	 * Finds a {@code RateRelation} based on a given String {@code to} and {@code from}
	 * @param from
	 * @param to
	 * @return null if no relation was found
	 * @throws SCException
	 */
	public RateRelation findRelation(String from, String to) throws SCException {

		rateRelations = exchangeReader.getRateRelations();

		for (RateRelation relation : rateRelations) {
			if (relation.getTo().equals(to) && relation.getFrom().equals(from)) {
				return relation;
			}
		}

		return null;
	}

	/**
	 * @param persons
	 * @return how many persons owe X money?
	 */
	public int howMany(String persons) {
		return persons.replace(",", "").length() + 1; // +1 = person who interpret the money
	}

	/**
	 * @param input
	 *            The string to count the unique letters from
	 * @return number of Letters (=different number of persons) in the String
	 */
	public static int countLetters(String input) {
		boolean[] isItThere = new boolean[Character.MAX_VALUE];
		for (int i = 0; i < input.length(); i++) {
			if (Character.isLetter(input.charAt(i))) {
				isItThere[input.charAt(i)] = true;
			}
		}

		int count = 0;
		for (int i = 0; i < isItThere.length; i++) {
			if (isItThere[i] == true) {
				count++;
			}
		}
		return count;
	}

	/**
	 * @param ower
	 *            The person to convert into a int representation
	 * @return a integer representation of a person, where A=0, B=1, C=2.. etc.
	 *         return -1 for invalid input
	 *
	 */
	public int charToInt(String ower) {

		String[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");

		for (int i = 0; i < alphabet.length; i++) {
			if (ower.equals(alphabet[i])) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * This method does a first check, if the input data is formatted correctly
	 * Terminatey if there is an error.
	 *
	 * @param input
	 *            The input data to check
	 * @throws SCException
	 */
	public void firstValidateExpenses(List<String> input) throws SCException {
		for (int i = 0; i < input.size(); i++) {
			if (input.get(i).length() < 10 || !input.get(i).contains("}") || !input.get(i).contains("{")) {
				throw new SCException("Invalid input on line number" + (i + 1));
			}
		}
	}

	/**
	 * The final validation for checking, if the input is formatted correctly.
	 * Terminatey if there is an error.
	 *
	 * @param inputSplitted
	 *            The input data to check
	 * @throws SCException
	 */
	public void finalValidate(ArrayList<String[]> inputSplitted, ArrayList<String[]> splitConcurry) throws SCException {
		for (int i = 0; i < inputSplitted.size(); i++) {
			if (charToInt(inputSplitted.get(i)[0]) == -1) {
				throw new SCException(
						"Error, just one person can interprete money - incorrect format on line " + (i + 1));

			}

			if (inputSplitted.get(i)[1].length() > 1 && !inputSplitted.get(i)[1].contains(",")) {
				throw new SCException("Error, invalid input on line " + (i + 1));
			}

			if (inputSplitted.get(i)[1].length() < 1) {
				throw new SCException("Error, list of owers is never empty. line: " + (i + 1));
			}

			if (inputSplitted.get(i)[1].contains("0") || inputSplitted.get(i)[1].contains("1")
					|| inputSplitted.get(i)[1].contains("2") || inputSplitted.get(i)[1].contains("3")
					|| inputSplitted.get(i)[1].contains("4") || inputSplitted.get(i)[1].contains("5")
					|| inputSplitted.get(i)[1].contains("6") || inputSplitted.get(i)[1].contains("7")
					|| inputSplitted.get(i)[1].contains("8") || inputSplitted.get(i)[1].contains("9")) {
				throw new SCException("Error, invalid input on line " + (i + 1));
			}

			if (inputSplitted.get(i)[1].contains(inputSplitted.get(i)[0])) {
				throw new SCException("Error, a payer is never an ower of an expense, check line: " + (i + 1));
			}

			if (parseStrToInt(splitConcurry.get(i)[0]) == -1 || Integer.valueOf(splitConcurry.get(i)[0]) < 0) {
				throw new SCException("Error, invalid or negative amount on line " + (i + 1));
			}

			if (splitConcurry.get(i)[1].length() < 3|| splitConcurry.get(i)[1].length() > 3) {
				throw new SCException("Invalid Currency Code");
			}
		}
	}

	/**
	 * This method should check if a String contains a legit Integer
	 *
	 * @param str
	 *            The string to check
	 * @return -1 if the String is not a valid Integer
	 */
	public static int parseStrToInt(String str) {
		if (str.matches("\\d+")) {
			return Integer.parseInt(str);
		} else {
			return -1;
		}
	}

	/**
	 * Function to process a list of expenses and return a CSV representation of the
	 * final payments table
	 *
	 * @param input
	 *            the expenses as a string array list
	 * @return a CSV representation of the final payments table
	 */
	@Override
	public String printFinalPayments(List<String> exchangeRateInput, List<String> expensesInput) throws SCException {

		exchangeReader = new ExchangeReader(exchangeRateInput);
		rateRelations = exchangeReader.getRateRelations();

		firstValidateExpenses(expensesInput);


		// remove all lines which are not to be considered
		if (!exchangeReader.isConsiderAll()) {
			for (Iterator<String> iter = expensesInput.listIterator(); iter.hasNext(); ) {
			    String line = iter.next();
			    if (!line.contains(exchangeReader.getConsiderCurrencyCode())) {
			        iter.remove();
			    }
			}
		}



		// toUpper
		for (int i = 0; i < expensesInput.size(); i++) {
			String upper = expensesInput.get(i).toUpperCase();
			expensesInput.set(i, upper);
		}

		// splitting the data into StringArrays in an ArrayList, where every element
		// stands for one input line, and the StringArrays contain 3 elements, where the
		// first element is the person who paid the expense, the second element are the
		// persons who owe him money now, and the third element is the amount of the
		// expense

		ArrayList<String[]> inputSplitted = new ArrayList<String[]>();

		for (int i = 0; i < expensesInput.size(); i++) {
			inputSplitted.add(expensesInput.get(i).split("\\{|\\}"));
		}

		ArrayList<String[]> splitConcurry = new ArrayList<String[]>();
		for (int i = 0; i < inputSplitted.size(); i++) {
			String[] splitConcurryElem = inputSplitted.get(i)[2].split(","); // array-position 0 = Amount; 1 = Concurry
			splitConcurry.add(splitConcurryElem);
		}

		// count the people how are involved in the shared expenses
		int persMax = 0;
		StringBuilder personsInOneString = new StringBuilder();
		for (int i = 0; i < inputSplitted.size(); i++) {
			personsInOneString.append(inputSplitted.get(i)[0] + inputSplitted.get(i)[1]);
		}

		String personsInOneStringAsString = personsInOneString.toString();
		String dataUpper = personsInOneStringAsString.toUpperCase();
		persMax = countLetters(dataUpper);

		// splitting the second element of the Strings[], so we can work with the
		// persons

		ArrayList<String[]> owersSplit = new ArrayList<String[]>();
		for (int i = 0; i < inputSplitted.size(); i++) {
			owersSplit.add(inputSplitted.get(i)[1].split("\\,"));
		}

		// create persons (assumption: always alphabetical - input needs to be
		// correct!!)

		ArrayList<Person> persons = new ArrayList<Person>();

		String[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ".split("");

		for (int i = 0; i < persMax; i++) {
			persons.add(new Person(alphabet[i], persMax));
		}

		// format-checking
		String[] alphabetWithOutUsedChars = Arrays.copyOfRange(alphabet, persMax, alphabet.length);

		for (int i = 0; i < inputSplitted.size(); i++) {
			for (int j = 0; j < alphabetWithOutUsedChars.length; j++) {
				if (inputSplitted.get(i)[1].contains(alphabetWithOutUsedChars[j])) {
					throw new SCException("Invalid input - sort your names alphabetically - line: " + (i + 1));
				}
			}

		}

		finalValidate(inputSplitted, splitConcurry);

		// additional formatcheck
		int counter = 0;
		for (Object[] data : inputSplitted) {
			for (int i = 0; i < data.length; i++) {
				counter++;
			}
		}
		// every StringArray element in the data must contain 3 elements (see above)
		if (counter % 3 != 0) {
			throw new SCException("Error, invalid input, check your whole input");
		}

		// all owes in the stringArrays are added to an integer Array so we can do math
		// with it

		ArrayList<BigDecimal> ints = new ArrayList<BigDecimal>();
		for (int i = 0; i < expensesInput.size(); i++) {

			RateRelation relation;
			relation = findRelation(splitConcurry.get(i)[1], exchangeReader.getPrintedCurrencyCode());
			if (relation == null) {
				relation = new RateRelation("dummy", "dummy", new BigDecimal(1));
			}
			BigDecimal toAdd = new BigDecimal(Integer.parseInt(splitConcurry.get(i)[0]));
			toAdd = toAdd.multiply(relation.getAmount());
			ints.add(toAdd);

		}

		// doing the calculations

		for (int i = 0; i < alphabet.length; i++) {

			for (int j = 0; j < expensesInput.size(); j++) {

				String[] ower = owersSplit.get(j);

				if (inputSplitted.get(j)[0].equals(alphabet[i])) {

					for (int k = 0; k < ower.length; k++) {
						String s = ower[k];

						int howmany = howMany(inputSplitted.get(j)[1]);
						BigDecimal personcounter = new BigDecimal(howmany);

						// setting for every person in the second element the owe to the person in the
						// first element the owe to the amount in the third element divided by all the
						// involved persons

						persons.get(charToInt(s)).setOwe(persons.get(charToInt(s)).getOwes(), i,
								persons.get(charToInt(s)).getOwe(persons.get(charToInt(s)).getOwes(), i)
										.add(ints.get(j)).divide(personcounter));

					}
				}
			}
		}

		// round them up
		for (int i = 0; i < persMax; i++) {
			for (int j = 0; j < persMax; j++) {
				BigDecimal result = new BigDecimal(0);
				if (persons.get(i).getOwe(persons.get(i).getOwes(), j).compareTo(BigDecimal.ZERO) > 0
						&& persons.get(j).getOwe(persons.get(j).getOwes(), i).compareTo(BigDecimal.ZERO) > 0) {

					if (persons.get(i).getOwe(persons.get(i).getOwes(), j)
							.compareTo(persons.get(j).getOwe(persons.get(j).getOwes(), i)) > 0) {

						result = persons.get(i).getOwe(persons.get(i).getOwes(), j)
								.subtract(persons.get(j).getOwe(persons.get(j).getOwes(), i));

						persons.get(j).setOwe(persons.get(j).getOwes(), i, new BigDecimal("0.00"));

						persons.get(i).setOwe(persons.get(i).getOwes(), j, result);

					} else {

						result = persons.get(j).getOwe(persons.get(j).getOwes(), i)
								.subtract(persons.get(i).getOwe(persons.get(i).getOwes(), j));

						persons.get(i).setOwe(persons.get(i).getOwes(), j, new BigDecimal("0.00"));

						persons.get(j).setOwe(persons.get(j).getOwes(), i, result);

					}
				}
			}
		}

		// add the CSV representation of all owes of a person into a ArrayList of
		// Strings, so its fit to the return value of this method and the requirement of
		// the project

		ArrayList<String> output = new ArrayList<String>();

		// adding a header to the output (e.g. ,A,B,C)
		StringBuilder header = new StringBuilder();
		for (int i = 0; i < persons.size(); i++) {
			header.append("," + alphabet[i]);
		}
		output.add(header.toString());


		for (int i = 0; i < persMax; i++) {
			output.add(persons.get(i).toString());
		}

		return String.join("\r\n", output);

	}

}
