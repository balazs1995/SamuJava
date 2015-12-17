import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.imageio.ImageIO;

public class VisualImagery {

	public VisualImagery() {
		program = new LinkedList<SPOTriplet>();
		ql = new QL();
	}

	public double reward() {
		return ql.reward();
	}

	public void working(List<SPOTriplet> triplets) {
		if (triplets.size() == 0)
			return;
		for (SPOTriplet triplet : triplets) {
			if (program.size() >= stmt_max)
				program.poll();

			program.offer(triplet);
		}
		try {
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			File outputfile = new File("samu_vi_" + timeInName() + ".png");

			Graphics graphics = bi.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, bi.getWidth(), bi.getHeight());
			graphics.setColor(Color.BLACK);
			try {
				graphics.setFont(Font.createFont(Font.TRUETYPE_FONT,
						new File("/usr/share/fonts/truetype/dejavu/DejaVuSansMono-Bold.ttf")).deriveFont(18.0f));
			} catch (FontFormatException e) {
				graphics.setFont(new Font("Arial", Font.BOLD, 14));
			}

			Queue<SPOTriplet> run = program;

			String prg = "";
			stmt_counter = 0;

			while (run.peek() != null) {
				SPOTriplet triplet = run.peek();
				prg += triplet.s;
				prg += triplet.p;
				prg += triplet.o;

				graphics.drawString("" + triplet.s + "." + triplet.p + "(" + triplet.o + ")", 5,
						256 - (++stmt_counter) * 28);
				run.poll();
			}

			ImageIO.write(bi, "png", outputfile);

			double img_input[] = new double[height * width];

			for (int i = 0; i < height; ++i)
				for (int j = 0; j < width; ++j) {
					img_input[i * 256 + j] = dread(i, j, bi);
				}
			long start = System.nanoTime();

			System.out.println("QL start... ");

			System.out.println(ql.ThisWasAnOperator(triplets.get(0), prg, img_input));

			System.out.println("" + (System.nanoTime() - start) + " ms ");

		} catch (IOException e) {
			System.err.println("WARNING: " + e.toString());
		}
	}

	private double dread(int x, int y, BufferedImage im) {
		return im.getRGB(x, y) / 65535.0;
	}

	private String timeInName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS");
		Date now = new Date();
		String strDate = sdf.format(now);
		return strDate;
	}

	private QL ql;
	Queue<SPOTriplet> program;
	private int stmt_counter = 0;
	private static final int stmt_max = 7;
	private static final int height = 512;
	private static final int width = 512;

}
