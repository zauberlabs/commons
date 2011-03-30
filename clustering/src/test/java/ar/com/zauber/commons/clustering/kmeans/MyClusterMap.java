/**
 * Copyright (c) 2005-2011 Zauber S.A. <http://www.zaubersoftware.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

/** TODO document */
public class MyClusterMap extends JFrame {

    private static final long serialVersionUID = 1L;
    private KmeansClusterer clusterer;
    private JPanel jContentPane = null;
    private final Color[] stringsColors = {
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


    /** This is the default constructor */
    public MyClusterMap() {
        super();
        initialize();
    }

    /** This method initializes this */
    private void initialize() {
        //this.setSize(1000, 1000);
        this.setBounds(0, 0, 700, 700);
        this.setContentPane(getJContentPane());
        this.getContentPane().setBackground(Color.WHITE);
        this.setTitle("JFrame");
        final Random randomGenerator = new Random();

        Vector<Clusterable> data;

        data = new Vector<Clusterable>();

        for(int i = 0; i < 300; i++) {
            data.add(new MyPoint(randomGenerator.nextInt(500) + 100, 
                    randomGenerator.nextInt(500) + 100));
        }

        clusterer = new KmeansClusterer(data, new Integer(stringsColors.length));
        final Date inicio = new Date();
        System.out.println("Comienzo: " + inicio);
        clusterer.cluster();
        final Date fin = new Date();
        System.out.println("Fin: " + fin);
        System.out.println("Tardo: " 
                + (fin.getTime() - inicio.getTime()) + "millis");

    }

    /** TODO document */
    @Override
    public final void paint(final Graphics g) {
        // TODO Auto-generated method stub
        super.paint(g);
        int i = 0;


        final AttributedCharacterIterator draw;

        for (final Iterator clusterIter = 
            clusterer.iterator(); clusterIter.hasNext();) {
            final KmeansCluster element = (KmeansCluster) clusterIter.next();
//          g.drawString(stringsCentroids[i],
//               (int)((MyPoint)element.getCentroid()).getPoint().getX(),
//               (int)((MyPoint)element.getCentroid()).getPoint().getY());
            for(final Iterator pointIter = element.iterator(); 
                pointIter.hasNext();) {
                final MyPoint p = (MyPoint) pointIter.next();
                g.setColor(stringsColors[i]);
                g.drawString("*",
                        (int)p.getPoint().getX(),
                        (int)p.getPoint().getY());
            }
            i++;
        }

    }

    /** This method initializes jContentPane*/
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
        }
        return jContentPane;
    }
}
