/*
 * Copyright (c) 2005-2008 Zauber -- All rights reserved
 */
package ar.com.zauber.commons.clustering.kmeans;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.text.AttributedCharacterIterator;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ar.com.zauber.commons.clustering.Clusterable;
import ar.com.zauber.commons.clustering.MyPoint;

public class MyClusterMap extends JFrame {

	private static final long serialVersionUID = 1L;
	private KmeansClusterer clusterer;
	private JPanel jContentPane = null;
	private Color[] stringsColors = {
			Color.BLACK,
			Color.BLUE,
			Color.CYAN,
			Color.GRAY,
			Color.GREEN,
			Color.LIGHT_GRAY,
			Color.MAGENTA,
			Color.ORANGE,
			Color.DARK_GRAY,
			Color.PINK,
			Color.RED,
			Color.YELLOW			
	};
	
	
	/**
	 * This is the default constructor
	 */
	public MyClusterMap() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		//this.setSize(1000, 1000);
		this.setBounds(0, 0, 700, 700);
		this.setContentPane(getJContentPane());
		this.getContentPane().setBackground(Color.WHITE);
		this.setTitle("JFrame");
        Random randomGenerator = new Random();
        
        Vector<Clusterable> data;
        
        data = new Vector<Clusterable>(); 
        
        for(int i = 0; i < 300; i++) {
            data.add(new MyPoint(randomGenerator.nextInt(500)+100, randomGenerator.nextInt(500)+100));
        }
        
        clusterer = new KmeansClusterer(data, new Integer(stringsColors.length));
        Date inicio = new Date();
        System.out.println("Comienzo: " + inicio);
        clusterer.cluster();
        Date fin = new Date();
        System.out.println("Fin: " + fin);
        System.out.println("Tardo: " + (fin.getTime() - inicio.getTime()) + "millis");
        
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
        int i = 0;
        
        
        AttributedCharacterIterator draw;
        
        for (Iterator clusterIter = clusterer.iterator(); clusterIter.hasNext();) {
			KmeansCluster element = (KmeansCluster) clusterIter.next();
//			g.drawString(
//					stringsCentroids[i],
//					(int)((MyPoint)element.getCentroid()).getPoint().getX(),
//					(int)((MyPoint)element.getCentroid()).getPoint().getY());
			for (Iterator pointIter = element.iterator(); pointIter.hasNext();) {
				MyPoint p = (MyPoint) pointIter.next();
				g.setColor(stringsColors[i]);
				g.drawString(
					"*",
					(int)p.getPoint().getX(),
					(int)p.getPoint().getY());
			}
			i++;
		}

	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
		}
		return jContentPane;
	}

}
