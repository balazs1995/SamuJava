import java.util.ArrayList;
import java.util.List;

import org.linkgrammar.LinkGrammar;
import org.linkgrammar.Linkage;

public class NLP {

	public NLP() {

		LinkGrammar.init();
	}

	public List<SPOTriplet> sentence2triplets(String s) {
		List<SPOTriplet> triplets = new ArrayList<SPOTriplet>();

		LinkGrammar.parse(s);
		int linkageSize = LinkGrammar.getNumLinkages();
		SPOTriplet triplet = new SPOTriplet();
		String alter_p = new String();
		boolean ready = false;

		for (int l = 0; l < linkageSize; ++l) {
			LinkGrammar.makeLinkage(l);
			Linkage linkage = new Linkage();
			linkage.setDisjunctCost(LinkGrammar.getLinkageDisjunctCost());
			linkage.setLinkCost(LinkGrammar.getLinkageLinkCost());
			linkage.setLinkedWordCount(LinkGrammar.getNumWords());
			linkage.setNumViolations(LinkGrammar.getLinkageNumViolations());
			String[] disjuncts = new String[LinkGrammar.getNumWords()];
			String[] words = new String[LinkGrammar.getNumWords()];
			for (int i = 0; i < words.length; i++) {
				disjuncts[i] = LinkGrammar.getLinkageDisjunct(i);
				words[i] = LinkGrammar.getLinkageWord(i);
			}

			int numLinks = LinkGrammar.getNumLinks();
			for (int i = 0; i < numLinks && !ready; i++) {
				String c = LinkGrammar.getLinkLabel(i);
				if (c.charAt(0) == 'S') {
					triplet.p = LinkGrammar.getLinkageWord(i);
					alter_p = words[LinkGrammar.getLinkRWord(i)];
					triplet.s = words[LinkGrammar.getLinkLWord(i)];
				}
				if (c.charAt(0) == 'O') {
					triplet.o = words[LinkGrammar.getLinkRWord(i)];
					if (triplet.p.equals(words[LinkGrammar.getLinkLWord(i)])) {
						triplet.cut();
						triplets.add(triplet);
						ready = true;
						break;
					} else if (alter_p.equals(words[LinkGrammar.getLinkLWord(i)])) {
						triplet.p = alter_p;
						triplet.cut();
						triplets.add(triplet);
						ready = true;
						break;

					}
				}
			}

		}

		return triplets;
	}

}
