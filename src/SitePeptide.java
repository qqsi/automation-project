
public class SitePeptide {
	private String sequence;
	private boolean truelabel;
	private Integer index;
	
	public String getSequence() {
		return sequence;
	}
	
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	
	public boolean isTruelabel() {
		return truelabel;
	}
	
	public void setTruelabel(boolean truelabel) {
		this.truelabel = truelabel;
	}
	
	public Integer getIndex() {
		return index;
	}
	
	public void setIndex(Integer index) {
		this.index = index;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result
				+ ((sequence == null) ? 0 : sequence.hashCode());
		result = prime * result + (truelabel ? 1231 : 1237);
		return result;
	}

	public SitePeptide(String sequence, boolean truelabel, Integer index) {
		super();
		this.sequence = sequence;
		this.truelabel = truelabel;
		this.index = index;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SitePeptide other = (SitePeptide) obj;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		if (sequence == null) {
			if (other.sequence != null)
				return false;
		} else if (!sequence.equals(other.sequence))
			return false;
		if (truelabel != other.truelabel)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Peptide [sequence=" + sequence + ", truelabel=" + truelabel
				+ ", index=" + index + "]";
	}
	
}
