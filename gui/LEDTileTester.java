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


    private class Dispatcher implements KeyEventDispatcher
    {
        public boolean dispatchKeyEvent(KeyEvent e)
        {
            if (LEDTileTester.this.settings.inputHasFocus())
                return false;

            if (e.getID() == KeyEvent.KEY_PRESSED)
            {
                switch (e.getKeyCode())
                {
                    case KeyEvent.VK_Q:
                        LOGGER.info("Good bye!");
                        System.exit(0);
                        break;

                        // Toggle settings display
                    case KeyEvent.VK_S:
                        LEDTileTester.this.settings.setVisible(!LEDTileTester.this.settings.isVisible());
                        break;

                        // Toggle fullscreen
                    case KeyEvent.VK_F:
                        LEDTileTester.this.frame.setVisible(false);
                        LEDTileTester.this.frame.dispose();
                        if (frame.isUndecorated())
                        {
                            LEDTileTester.this.frame.setExtendedState(JFrame.NORMAL); 
                            LEDTileTester.this.frame.setUndecorated(false);
                        }
                        else
                        {
                            LEDTileTester.this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
                            LEDTileTester.this.frame.setUndecorated(true);
                        }
                        LEDTileTester.this.frame.setVisible(true);
                        break;

                        // Resize +1 pixel width
                    case KeyEvent.VK_RIGHT:
                        LEDTileTester.this.panel.button_width += 1;
                        LEDTileTester.this.panel.refreshButtonSize();
                        break;

                        // Resize -1 pixel width
                    case KeyEvent.VK_LEFT:
                        LEDTileTester.this.panel.button_width -= 1;
                        LEDTileTester.this.panel.refreshButtonSize();
                        break;

                        // Resize +1 pixel height
                    case KeyEvent.VK_UP:
                        LEDTileTester.this.panel.button_heigth += 1;
                        LEDTileTester.this.panel.refreshButtonSize();
                        break;

                        // Resize -1 pixel height
                    case KeyEvent.VK_DOWN:
                        LEDTileTester.this.panel.button_heigth -= 1;
                        LEDTileTester.this.panel.refreshButtonSize();
                        break;
                }
                settings.refreshValues();
                LEDTileTester.this.panel.grabFocus();
            }
            return false;
        }
    }


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

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new Dispatcher());

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

