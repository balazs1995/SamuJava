import java.io.PrintStream;

public class SPOTriplet {
	public String s;
	public String p;
	public String o;

	public SPOTriplet() {
		s = new String();
		p = new String();
		o = new String();
	}

	public SPOTriplet(String S, String P, String O) {
		s = S;
		p = P;
		o = O;
	}

	public void print(PrintStream out) {
		out.print(s + " " + p + " " + o);
	}

	public boolean equal(SPOTriplet other) {
		return (s.equals(other.s) && p.equals(other.p) && o.equals(other.o));
	}

	public boolean lessThan(SPOTriplet other) {
		String a = s + p + o;
		String b = other.s + other.p + other.o;
		return a.compareTo(b) < 0;
	}

	public double cmp(SPOTriplet other) {
		if (this == other || other == null)
			return 1.0;
		else if ((s.equals(other.s) && p.equals(other.p)) || (s.equals(other.s) && o.equals(other.o))
				|| (o.equals(other.o) && p.equals(other.p)))
			return 2.0 / 3.0;
		else if (equal(other))
			return 1.0 / 3.0;

		return 0.0;
	}

	public void cut() {
		/*
		s = s.replace(".", "");
		s = s.replace("[", "");
		p = p.replace(".", "");
		p = p.replace("[", "");
		o = o.replace(".", "");
		o = o.replace("[", "");*/
		try{
			s = s.substring(0, s.indexOf("."));
		}catch(Exception e){
		}
		try{
		s = s.substring(0, s.indexOf("["));
		}catch(Exception e){
		}
		try{
		p = p.substring(0, p.indexOf("."));
		}catch(Exception e){
		}
		try{
		p = p.substring(0, p.indexOf("["));
		}catch(Exception e){
		}
		try{
		o = o.substring(0, o.indexOf("."));
		}catch(Exception e){
		}
		try{
		o = o.substring(0, o.indexOf("["));
		}catch(Exception e){
		}
	}
	
	//we are using it as a key
	@Override
    public boolean equals(Object obj){
		return equal((SPOTriplet) obj);
	}
	@Override
    public int hashCode(){
		return s.hashCode()+p.hashCode()+o.hashCode();
		
	}
}
