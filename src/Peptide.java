import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Peptide {
	private String name;
	private String sequence;
	private List<Integer> positivePositions;
	private List<Integer> negativePositions;
	private Integer length;

	public Peptide(String name, String sequence,
			List<Integer> positivePositions, List<Integer> negaivePositions) {
		super();
		this.name = name;
		this.sequence = sequence;
		this.positivePositions = positivePositions;
		this.negativePositions = negaivePositions;
		this.length = sequence.length();
	}
	
	public Peptide(String name, String sequence) {
		super();
		this.name = name;
		this.sequence = sequence;
		this.length = sequence.length();
		this.positivePositions = new ArrayList<Integer>();
		this.negativePositions = new ArrayList<Integer>();
	}
	
	public void addPositive(Integer position, char centerAA){
		if (sequence.charAt(position) == centerAA) {
			this.positivePositions.add(position);
			if (this.negativePositions.contains(position)) {
				negativePositions.remove(position);
			}
		}
	}
	
	public void generateNegativePositions(char centerAA) {
		for (int i = 0; i < length; i++){
			if (sequence.charAt(i) == centerAA && !positivePositions.contains(i)) {
				this.negativePositions.add(i);
			}
		}
	}

	private String repeatedString(char ch, int len) {
		char[] chars = new char[len];
		Arrays.fill(chars, ch);
		return new String(chars);
	}
	
	private SitePeptide generateSitePeptide(Integer upstreamLength, Integer downstreamLength, Integer position, boolean label) {
		String leftFill = "" , rightFill = "";
		int leftOffset = 0;
		int rightOffset = 0;
		if (position < upstreamLength) {
			leftOffset = upstreamLength - position;
			leftFill = repeatedString('*', leftOffset);
		}
		if (position + downstreamLength >= this.length) {
			rightOffset = position + downstreamLength - this.length + 1;
			rightFill = repeatedString('*', rightOffset);
		}
		String siteString = leftFill + this.sequence.substring(position - upstreamLength + leftOffset, position + downstreamLength - rightOffset + 1) + rightFill;
		return new SitePeptide(siteString, label, upstreamLength);
	}
	
	public List<SitePeptide> generateSites(Integer upstreamLength, Integer downstreamLength) {
		List<SitePeptide> siteList = new ArrayList<SitePeptide>();
		for (Integer position : positivePositions) {
			siteList.add(generateSitePeptide(upstreamLength, downstreamLength, position, true));
		}
		
		for (Integer position : negativePositions) {
			siteList.add(generateSitePeptide(upstreamLength, downstreamLength, position, false));
		}
		
		return siteList;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime
				* result
				+ ((negativePositions == null) ? 0 : negativePositions.hashCode());
		result = prime
				* result
				+ ((positivePositions == null) ? 0 : positivePositions
						.hashCode());
		result = prime * result
				+ ((sequence == null) ? 0 : sequence.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Peptide other = (Peptide) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (negativePositions == null) {
			if (other.negativePositions != null)
				return false;
		} else if (!negativePositions.equals(other.negativePositions))
			return false;
		if (positivePositions == null) {
			if (other.positivePositions != null)
				return false;
		} else if (!positivePositions.equals(other.positivePositions))
			return false;
		if (sequence == null) {
			if (other.sequence != null)
				return false;
		} else if (!sequence.equals(other.sequence))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SitePeptide [name=" + name + ", sequence=" + sequence
				+ ", positivePositions=" + positivePositions
				+ ", negaivePositions=" + negativePositions + ", length="
				+ length + "]";
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public List<Integer> getPositivePositions() {
		return positivePositions;
	}

	public void setPositivePositions(List<Integer> positivePositions) {
		this.positivePositions = positivePositions;
	}

	public List<Integer> getNegaivePositions() {
		return negativePositions;
	}

	public void setNegaivePositions(List<Integer> negaivePositions) {
		this.negativePositions = negaivePositions;
	}

	public Integer getLength() {
		return length;
	}

	public static void main(String[] args) {
		//merely a test case
		Peptide peptide = new Peptide("P41220", "MQSAMFLAVQHDCRPMDKSAGSGHKSEEKREKMKRTLLKDWKTRLSYFLQNSSTPGKPKTGKKSKQQAFIKPSPEEAQLWSEAFDELLASKYGLAAFRAFLKSEFCEENIEFWLACEDFKKTKSPQKLSSKARKIYTDFIEKEAPKEINIDFQTKTLIAQNIQEATSGCFTTAQKRVYSLMENNSYPRFLESEFYQDLCKKPQITTEPHAT");
		peptide.addPositive(106 - 1, 'C');
		peptide.addPositive(116 - 1, 'C');		
		peptide.addPositive(199 - 1, 'C');
		peptide.generateNegativePositions('C');
		System.out.println(peptide.generateSites(20, 20));
	}
	
	
}
