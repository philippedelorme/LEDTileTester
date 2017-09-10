/*vim: bg=dark et ai sw=4 ts=4 */

/**
 * 
 * Copyright 2017, Philippe DELORME
 * 
 * This file is part of LEDTileTester.
 * 
 * LEDTileTester is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package gui;

import java.util.logging.*;
import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class LEDTileTester
{
	private static final Logger LOGGER = Logger.getLogger( LEDTileTester.class.getName() );

    // main frame
    private JFrame frame;

    private lttSettings settings;
    private lttPanel panel;


    public LEDTileTester()
    {
        this.build();
    }


    private void build()
    {
		LOGGER.info("Building main app");

        this.frame = new JFrame("LEDTileTester");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.frame.setUndecorated(true);

        // panel and settings
        this.panel = new lttPanel(lttPanel.DEFAULT_WIDTH, lttPanel.DEFAULT_HEIGHT);
        this.settings = new lttSettings(this.frame, this.panel);
        this.panel.setSettings(this.settings);

        JPanel p = new JPanel();
        p.add(this.settings);
        p.add(this.panel);
        //p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        //this.frame.getContentPane().add(p, BorderLayout.NORTH);
        this.frame.getContentPane().add(p);

        this.frame.pack();
    }

    private void display()
    {
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LEDTileTester().display();
            }
        });
    }
}

