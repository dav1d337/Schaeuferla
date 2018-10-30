package sc.other;

import java.math.BigDecimal;
import java.util.ArrayList;

/**This class represents a Person
 * @author David Koch
 *
 */
public class Person {

	private String name;
	private int persMax;
	private ArrayList<BigDecimal> owes = new ArrayList<BigDecimal>(persMax);



	/**for each person involved, the owes shall be initialized with 0
	 *
	 * @param name the name of the person
	 * @param persMax the number of all involved persons in the data
	 */
	public Person(String name, int persMax) {
		this.name = name;
		this.persMax = persMax;
		for (int i = 0; i < persMax; i++) {
			owes.add(new BigDecimal("0.00"));
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<BigDecimal> getOwes() {
		return owes;
	}

	public void setOwes(ArrayList<BigDecimal> owes) {
		this.owes = owes;
	}

	public void setOwe(ArrayList<BigDecimal> owes, int index, BigDecimal amount) {
		owes.set(index, amount);
	}

	public BigDecimal getOwe(ArrayList<BigDecimal> owes, int index) {
		return owes.get(index);
	}

	public int getPersMax() {
		return persMax;
	}

	public void setPersMax(int persMax) {
		this.persMax = persMax;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * creates a CSV representation of the owes of a person
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getName());
		for (int i = 0; i < owes.size(); i++) {
		  BigDecimal num = owes.get(i);
		  sb.append("," + num);
		}
		String result = sb.toString();
		return result;

	}

}