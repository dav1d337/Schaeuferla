package sc.other;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sc.SCException;

/**
 * This class is investigating a given Exchange Rate File. It looks up the first
 * two line about meta-data (which currencys needs to be considered and in which
 * currency the result should be printed). For every Exchange Rate it generates
 * and saves a {code RateRelation} in {@code rateRelations}
 *
 * @author David Koch
 *
 */
public class ExchangeReader {

	private List<String> exchangeFile;

	private boolean considerAll = false;
	private String considerCurrencyCode;
	private String printedCurrencyCode;

	private List<RateRelation> rateRelations = new ArrayList<RateRelation>();

	public ExchangeReader(List<String> exchangeFile) throws SCException {
		this.exchangeFile = exchangeFile;

		checkFirstLine(exchangeFile.get(0));
		checkSecondLine(exchangeFile.get(1));
		for (int i = 2; i < exchangeFile.size(); i++) {
			checkRateLines(exchangeFile.get(i));
		}
	}

	/**
	 * Checks if all lines need to be considered or just one. Sets
	 * {@code considerAll} to true, if all are considered and sets
	 * {@code considerCurrencyCode} to the named code
	 *
	 * @param firstLine
	 *            First line of the file which needs to be "Consider all" or
	 *            "Consider only ABC"
	 * @throws SCException
	 *             when a format error occurs
	 */
	public void checkFirstLine(String firstLine) throws SCException {
		String start = "Consider ";
		if (firstLine.equals(start + "all")) {
			considerAll = true;
		} else if (firstLine.startsWith(start + "only ")) {
			String[] splitted = firstLine.split("only ");
			checkCurrencyCode(splitted[1]);
			setConsiderCurrencyCode(splitted[1]);
		}

		else {
			throw new SCException(
					"First line is invalid. Must be formatted as \"Consider all\" or as \"Consider only YOUR CURRENCY CODE\"");
		}
	}

	/**
	 * Checks in which currency the result be
	 *
	 * @param secondLine
	 *            The second line of the file needs to be equal to "Print in ABC"
	 *            where ABC is a ISO-4217 Currency Code
	 * @throws SCException
	 */
	public void checkSecondLine(String secondLine) throws SCException {
		if (!secondLine.startsWith("Print in ")) {
			throw new SCException("Second line is invalid.\"Print In YOUR CURRENCY CODE\"");
		}

		String[] splitted = secondLine.split("Print in ");

		checkCurrencyCode(splitted[1]);
		setPrintedCurrencyCode(splitted[1]);
	}

	/**
	 * Checks if a given String matches a ISO-4217 Currency Code
	 *
	 * @param string
	 * @throws SCException
	 *             If its not matches
	 */
	public void checkCurrencyCode(String string) throws SCException {
		String pattern = "/^AED|AFN|ALL|AMD|ANG|AOA|ARS|AUD|AWG|AZN|BAM|BBD|BDT|BGN|BHD|BIF|BMD|BND|BOB|BRL|BSD|BTN|BWP|BYR|BZD|CAD|CDF|CHF|CLP|CNY|COP|CRC|CUC|CUP|CVE|CZK|DJF|DKK|DOP|DZD|EGP|ERN|ETB|EUR|FJD|FKP|GBP|GEL|GGP|GHS|GIP|GMD|GNF|GTQ|GYD|HKD|HNL|HRK|HTG|HUF|IDR|ILS|IMP|INR|IQD|IRR|ISK|JEP|JMD|JOD|JPY|KES|KGS|KHR|KMF|KPW|KRW|KWD|KYD|KZT|LAK|LBP|LKR|LRD|LSL|LYD|MAD|MDL|MGA|MKD|MMK|MNT|MOP|MRO|MUR|MVR|MWK|MXN|MYR|MZN|NAD|NGN|NIO|NOK|NPR|NZD|OMR|PAB|PEN|PGK|PHP|PKR|PLN|PYG|QAR|RON|RSD|RUB|RWF|SAR|SBD|SCR|SDG|SEK|SGD|SHP|SLL|SOS|SPL|SRD|STD|SVC|SYP|SZL|THB|TJS|TMT|TND|TOP|TRY|TTD|TVD|TWD|TZS|UAH|UGX|USD|UYU|UZS|VEF|VND|VUV|WST|XAF|XCD|XDR|XOF|XPF|YER|ZAR|ZMW|ZWD$/";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(string);

		if (!m.matches()) {
			throw new SCException("Invalid Currency Code");
		}
	}

	/**
	 * Creates a {@code RateRelation} for every valid line
	 *
	 * @param line
	 * @throws SCException
	 *             If there is a formatting error
	 */
	public void checkRateLines(String line) throws SCException {

		if (!line.contains("->")) {
			throw new SCException("Wrong format. Need ->");
		}
		String[] splitted = line.split("->");
		if (splitted.length > 2) {
			throw new SCException("Wrong format");
		}
		String[] splitted2 = splitted[1].split(" ");
		if (splitted.length > 2) {
			throw new SCException("Wrong format");
		}

		String from = splitted[0];
		String to = splitted2[0];
		checkCurrencyCode(from);
		checkCurrencyCode(to);

		try {
			double result = Double.parseDouble(splitted2[1]);
		} catch (NumberFormatException e) {
			throw new SCException("invalid ratio");
		}

		BigDecimal rate = new BigDecimal(splitted2[1]);

		RateRelation relation = new RateRelation(from, to, rate);
		rateRelations.add(relation);
	}

	public boolean isConsiderAll() {
		return considerAll;
	}

	public void setConsiderAll(boolean considerAll) {
		this.considerAll = considerAll;
	}

	public String getConsiderCurrencyCode() {
		return considerCurrencyCode;
	}

	public void setConsiderCurrencyCode(String considerCurrencyCode) {
		this.considerCurrencyCode = considerCurrencyCode;
	}

	public String getPrintedCurrencyCode() {
		return printedCurrencyCode;
	}

	public void setPrintedCurrencyCode(String printedCurrencyCode) {
		this.printedCurrencyCode = printedCurrencyCode;
	}

	public List<RateRelation> getRateRelations() {
		return rateRelations;
	}

	public void setRateRelations(List<RateRelation> rateRelations) {
		this.rateRelations = rateRelations;
	}
}
