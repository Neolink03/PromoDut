package vue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import modele.Etudiant;
import modele.Promotion;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleInsets;

public class VueCamembert extends JInternalFrame implements Observateur {
	
	private Camembert cam;
	private Promotion promo;
	
	public VueCamembert(Promotion promo) {

		this.setTitle("Dpts Origine");
		this.promo = promo;
		
		init();
		
		promo.addObservateur(this);
	}
	
	public void init() {
		
		// On supprime tous les éléments du précédent panneau
		this.getContentPane().removeAll();
		JPanel pano = new JPanel();
		
		cam = new Camembert(promo);
		pano.add(cam);

		this.setContentPane(pano);
		this.pack();
	}
	
	@Override
	public void update() {
		
		init();
	}
	
	private class Camembert extends ChartPanel{
			
		private Promotion promo;
			
		private ChartPanel chartpano;
		private JFreeChart piechart ;
		public DefaultPieDataset pieDataset;
		
		public Camembert(Promotion promo) {

			super(null);
			this.promo = promo;
			genereData();
			init();
		}
		
		public void init(){
			
			piechart = ChartFactory.createPieChart3D(
		            "Répartition géographique",  // chart title
		            pieDataset,                // data
		            true,                   // include legend
		            true,
		            false
	        );
			
			((PiePlot3D) (piechart.getPlot() ) ).setForegroundAlpha(0.5f);
			((PiePlot3D) (piechart.getPlot() ) ).setBackgroundPaint(Color.darkGray);
			
			// On créer le panneau
			//chartpano = new ChartPanel(piechart);
	        
			this.setChart(piechart);
		}
		
		public void genereData() {
			
			pieDataset = new DefaultPieDataset();
			
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			ArrayList<Etudiant> list = promo.getListeEtudiants();
			
			// On récup le nombre d'étudiants par département
			for( int i = 0 ; i < list.size() ; i++ ) {
				
				// Si le département a déjà eu au - 1 étu rajouter +1
				if ( map.get(list.get(i).getDpt()) != null) {
					
					map.put(list.get(i).getDpt(), map.get(list.get(i).getDpt()) + 1);
				}
				
				// Sinon créer dans la map de le dpt avec l'étu (le seul à ce moment là)
				else {
					
					map.put(list.get(i).getDpt(), 1);
				}
				
			}
			
			Object t[] = map.keySet().toArray();
			Arrays.sort(t);
			
			for( int i = 0 ; i < t.length ; i++ ) {
				
				pieDataset.setValue((String) t[i], map.get((String) t[i]));
			}
		}
	
	}

}
