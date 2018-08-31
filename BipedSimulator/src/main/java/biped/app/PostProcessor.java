
package biped.app;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartType;
import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.chart.HybridContentRenderer;
import edu.ucsc.cross.hse.core.chart.RendererConfiguration;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.file.FileBrowser;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;

/**
 * A post processor
 */
public class PostProcessor {

	public static void main(String args[]) {

		System.out.println("Select environment to process");
		HSEnvironment environment = HSEnvironment.loadFromFile(FileBrowser.load());
		processEnvironmentData(environment, true);
	}

	/**
	 * Executes all processing tasks. This method is called by the main applications
	 */
	public static void processEnvironmentData(HSEnvironment environment, boolean save) {

		Figure[] figures = { generateBipedStateFigure(environment.getTrajectories()),
				generateLimbAngleFigure(environment.getTrajectories()) };
		for (Figure fig : figures) {
			if (save) {
				fig.exportToFile();
			} else {
				fig.display();
			}
		}
	}

	/**
	 * Generate a figure
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying state values
	 */
	public static Figure generateLimbAngleFigure(TrajectorySet solution) {

		// Create figure
		Figure figure = new Figure(1000, 800, "Biped Limb Angles");
		// Add charts
		figure.add(0, 0, solution, HybridTime.TIME, "plantedLegAngle", "Time (sec)", "Planted Leg Angle (rad)", null,
				false);
		figure.add(1, 0, solution, HybridTime.TIME, "swingLegAngle", "Time (sec)", "Swing Leg Angle (rad)", null,
				false);
		figure.add(2, 0, solution, HybridTime.TIME, "torsoAngle", "Time (sec)", "Torso Angle (rad)", null, true);
		// Export the figure as a pdf
		for (Component comp : figure.getContentPanel().getComponents()) {
			ChartPanel panel = (ChartPanel) comp;
			panel.getChart().getXYPlot().setRenderer(new HybridContentRenderer(ChartType.LINE, solution,
					getDefaultBipedRenderer(), panel.getChart().getXYPlot().getDataset()));
			panel.getChart().getLegend().setVisible(false);
		}
		return figure;
	}

	public static Figure generateBipedStateFigure(TrajectorySet solution) {

		Figure figure = new Figure(1000, 800);
		String[] axis = { "Planted Leg Angle", "Swing Leg Angle", "Torso Angle", "Planted Leg Velocity",
				"Swing Leg Velocity", "Torso Velocity" };

		ChartPanel ch = ChartUtils.createPanel(solution, HybridTime.TIME, "plantedLegAngle", ChartType.LINE,
				getDefaultBipedRenderer(), null);
		ch.getChart().setTitle("Title");
		figure.add(0, 0, ChartUtils.createPanel(solution, HybridTime.TIME, "plantedLegAngle", ChartType.LINE,
				getDefaultBipedRenderer(), ch.getChart()));
		figure.add(0, 1, ChartUtils.createPanel(solution, HybridTime.TIME, "swingLegAngle", ChartType.LINE,
				getDefaultBipedRenderer(), null));
		figure.add(0, 2, ChartUtils.createPanel(solution, HybridTime.TIME, "torsoAngle", ChartType.LINE,
				getDefaultBipedRenderer(), null));
		figure.add(1, 0, ChartUtils.createPanel(solution, HybridTime.TIME, "plantedLegVelocity", ChartType.LINE,
				getDefaultBipedRenderer(), null));
		figure.add(1, 1, ChartUtils.createPanel(solution, HybridTime.TIME, "swingLegVelocity", ChartType.LINE,
				getDefaultBipedRenderer(), null));
		figure.add(1, 2, ChartUtils.createPanel(solution, HybridTime.TIME, "torsoVelocity", ChartType.LINE,
				getDefaultBipedRenderer(), null));
		int i = 0;
		for (Component comp : figure.getContentPanel().getComponents()) {
			ChartPanel panel = (ChartPanel) comp;
			panel.getChart().setTitle(axis[i++]);
			panel.getChart().getLegend().setVisible(false);
		}
		return figure;
	}

	public static RendererConfiguration getDefaultBipedRenderer() {

		RendererConfiguration bipedRend = new RendererConfiguration();
		bipedRend.assignSeriesColor("Physical", Color.BLUE);
		bipedRend.assignSeriesColor("Virtual", Color.GREEN);
		bipedRend.assignSeriesColor("Reference", Color.RED);
		bipedRend.assignSeriesColor("Perturbation", Color.MAGENTA);
		float dash[] = { 10.0f };
		BasicStroke virtual = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
		bipedRend.assignSeriesStroke("Virtual", virtual);
		float[] dashingPattern3 = { 10f, 10f, 1f, 10f };
		BasicStroke stroke3 = new BasicStroke(3.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 1.0f,
				dashingPattern3, 0.0f);
		bipedRend.assignSeriesStroke("Reference", stroke3);
		return bipedRend;
	}
}
