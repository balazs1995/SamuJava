import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Samu {

	public Samu() {
		mutex_ = new ReentrantLock();
		vi = new VisualImagery();
		nlp = new NLP();
		caregiver_name_ = new ArrayList<String>();
		caregiver_name_.add("caregiver1");
		caregiver_name_.add("caregiver2");
	}

	public void destroy() {
		run_ = false;
	}

	public boolean run() {
		return run_;
	}

	public void FamilyCaregiverShell() {
		String cmd_prefix = "___";

		  System.out.print(Caregiver() + "@Caregiver> ");

		  BufferedReader stdin_reader = new BufferedReader(new InputStreamReader(System.in));
		  try {
			for ( String line; (line = stdin_reader.readLine()) != null ; )
			    {
			      if (line.substring(0, cmd_prefix.length()).equals(cmd_prefix))
			        {
			          if ( line == cmd_prefix )
			            NextCaregiver();
			        }
			      else
			        {
			          ThisWasAnOperator(line);
			        }

				  System.out.print(Caregiver() + "@Caregiver> ");
			    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		    
		    run_ = false; 
	}

	public void terminal() {
		mutex_.lock();

		FamilyCaregiverShell();
		mutex_.unlock();
	}

	public void ThisWasAnOperator(String sentence) {
		vi.working(nlp.sentence2triplets(sentence));
	}

	public String Caregiver() {
		if (caregiver_name_.size() > 0)
			return caregiver_name_.get(caregiver_idx_);
		else
			return "Undefined";
	}

	public void NextCaregiver() {
		caregiver_idx_ = (caregiver_idx_ + 1) % caregiver_name_.size();
	}

	public double reward() {
		return vi.reward();
	}

	private boolean run_ = true;
	private Lock mutex_;
	private NLP nlp;
	private VisualImagery vi;
	private int caregiver_idx_ = 0;
	private List<String> caregiver_name_;

}
