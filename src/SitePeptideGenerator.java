import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class SitePeptideGenerator {
	
	public List<SitePeptide> generateSitePeptides(String filename, Integer upstreamLength, Integer downstreamLength) {
		//readline
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = br.readLine();
			
			Map<String, Peptide> peptideMap = new HashMap<String, Peptide>();
			while ((line = br.readLine()) != null) {
				String[] elements = line.split("\\s+");
				if (peptideMap.containsKey(elements[0])) {
					Peptide peptide = peptideMap.get(elements[0]);
					//minus one here is very important
					peptide.addPositive(Integer.parseInt(elements[2]) -1, elements[3].charAt(0));
				} else {
					Peptide peptide = new Peptide(elements[0], elements[1]);
					//minus one here is very important
					peptide.addPositive(Integer.parseInt(elements[2]) - 1, elements[3].charAt(0));
					peptideMap.put(elements[0], peptide);
				}
			}
			br.close();
			
			PrintWriter writer = new PrintWriter("/Users/Cambi/All_palmitoylation_site_peptide.txt", "UTF-8");
			
			List<SitePeptide> sitePeptides = new ArrayList<SitePeptide>();
			for (Peptide peptide : peptideMap.values()){
				peptide.generateNegativePositions('C');
				//seems not elegant here...
				sitePeptides.addAll(peptide.generateSites(upstreamLength, downstreamLength));
			}

			int numtrue = 0;
			int numfalse = 0;
			for (SitePeptide sitePeptide : sitePeptides) {
				if (sitePeptide.isTruelabel()) {
					numtrue ++;
				} else {
					numfalse ++;
				}
				writer.println(sitePeptide.getSequence() + " " + sitePeptide.isTruelabel());
			}
			writer.close();
			System.out.println("numtrue: " + numtrue + " numfalse: " + numfalse);
			return sitePeptides;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		new SitePeptideGenerator().generateSitePeptides("/Users/Cambi/All_palmitoylation sites@20130911_ZYY.elm", 10, 9);
	}
	
}
