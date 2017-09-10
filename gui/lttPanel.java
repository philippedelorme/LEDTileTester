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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.logging.*;
import java.util.ArrayList;
import java.util.List;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import utils.Utils;
import gui.lttButton;
import gui.lttPopupMenu;
import gui.lttSettings;


class lttPanel extends JPanel
{
    private static final Logger LOGGER = Logger.getLogger( lttPanel.class.getName() );

    public static final int DEFAULT_WIDTH = 10;
    public static final int DEFAULT_HEIGHT = 5;
    public static final int BORDER_WIDTH = 1;

    private final List<lttButton> list = new ArrayList<lttButton>();
    private JFrame frame;
    private lttSettings settings;
    private lttPopupMenu popup;

    private String currentText;
    private Color currentColorIn;
    private Color currentColorOut;

    public int width;
    public int height;
    public int button_width;
    public int button_heigth;


    private class Dispatcher implements KeyEventDispatcher
    {
        public boolean dispatchKeyEvent(KeyEvent e)
        {
            if (lttPanel.this.settings.inputHasFocus())
                return false;

            if (e.getID() == KeyEvent.KEY_PRESSED)
            {
                switch (e.getKeyCode())
                {
                    case KeyEvent.VK_Q:
                        LOGGER.info("Good bye!");
                        System.exit(0);
                        break;

                        // Add 1 tile
                    /*case KeyEvent.VK_PLUS:
                        lttPanel.this.width += 1;
                        lttPanel.this.refresh();
                        break;

                        // Remove 1 tile
                    case KeyEvent.VK_MINUS:
                        lttPanel.this.width -= 1;
                        lttPanel.this.refresh();
                        break;*/

                        // Toggle fullscreen
                    case KeyEvent.VK_F:
                        lttPanel.this.frame.setVisible(false);
                        lttPanel.this.frame.dispose();
                        if (frame.isUndecorated())
                        {
                            lttPanel.this.frame.setExtendedState(JFrame.NORMAL); 
                            lttPanel.this.frame.setUndecorated(false);
                        }
                        else
                        {
                            lttPanel.this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
                            lttPanel.this.frame.setUndecorated(true);
                        }
                        lttPanel.this.frame.setVisible(true);
                        break;

                        // Resize +1 pixel width
                    case KeyEvent.VK_RIGHT:
                        lttPanel.this.button_width += 1;
                        lttPanel.this.refreshButtonSize();
                        break;

                        // Resize -1 pixel width
                    case KeyEvent.VK_LEFT:
                        lttPanel.this.button_width -= 1;
                        lttPanel.this.refreshButtonSize();
                        break;

                        // Resize +1 pixel height
                    case KeyEvent.VK_UP:
                        lttPanel.this.button_heigth += 1;
                        lttPanel.this.refreshButtonSize();
                        break;

                        // Resize -1 pixel height
                    case KeyEvent.VK_DOWN:
                        lttPanel.this.button_heigth -= 1;
                        lttPanel.this.refreshButtonSize();
                        break;
                }
                settings.refreshValues();
                lttPanel.this.grabFocus();
            }
            return false;
        }
    }


    public lttPanel(int width, int height)
    {
        this.width = width;
        this.height = height;

        // current values
        this.currentText = null;
        this.currentColorIn = new Color(128, 128, 128);
        this.currentColorOut = Color.black;

        this.button_width = lttButton.WIDTH;
        this.button_heigth = lttButton.HEIGHT;
        this.refresh();

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new Dispatcher());
    }


    public void setSettings(final lttSettings settings)
    {
        this.settings = settings;
        this.popup = new lttPopupMenu(this.settings);
    }


    public void setFrame(final JFrame frame)
    {
        this.frame = frame;
    }


    // An inner class to check whether mouse events are the popup trigger
    class MousePopupListener extends MouseAdapter
    {
        private int row;
        private int col;

        MousePopupListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            checkPopup(e);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            checkPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            checkPopup(e);
        }

        private void checkPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                lttButton button = lttPanel.this.getGridButton(row, col);
                popup.show(lttPanel.this, button.getX(), button.getY());
            }
        }
    }


    private lttButton getGridButton(int r, int c)
    {
        int index = r * this.width + c;
        return list.get(index);
    }


    private lttButton createGridButton(final int row, final int col, String text)
    {
        if (text == null)
            text = row + "," + col;
        else
            // expand row and column
            text = text.replace("%r", Integer.toString(row)).replace("%c", Integer.toString(col));

        final lttButton button = new lttButton(text);

        button.setDefaultBackgroundColor(this.currentColorIn);
        button.setPressedBackgroundColor(Color.red);
        button.setBorder(BorderFactory.createLineBorder(this.currentColorOut, BORDER_WIDTH));
        button.addMouseListener(new MousePopupListener(row, col));

        return button;
    }


    public void setBorderColor(Color color)
    {
        currentColorOut = color;

        for (lttButton button: list)
        {
            button.setBorder(BorderFactory.createLineBorder(color, BORDER_WIDTH));
        }
    }


    public void setBackgroundColor(Color color)
    {
        currentColorIn = color;

        for (lttButton button: list)
        {
            button.setDefaultBackgroundColor(color);
        }
    }


    public void setText(String str)
    {
        currentText = str;

        LOGGER.info("Set text " + currentText);

        int w = width;
        int h = height;
        for (int i = 0; i < w * h; i++) {
            int row = i / w;
            int col = i % w;
            lttButton button = getGridButton(row, col);
            // expand row and column
            button.setText(str.replace("%r", Integer.toString(row)).replace("%c", Integer.toString(col)));
        }
    }


    public void refreshButtonSize()
    {
        for (lttButton button: list)
        {
            button.setPreferredSize(new Dimension(button_width, button_heigth));
        }
        setPreferredSize(new Dimension(width * button_width, height * button_heigth));
        revalidate();
        repaint();
    }


    public void refresh()
    {
        removeAll();
        list.clear();

        int w = width;
        int h = height;

        LOGGER.info("Rebuild panel with size " + w + "/" + h);

        // layout
        setLayout(new GridLayout(h, w));

        for (int i = 0; i < w * h; i++) {
            int row = i / w;
            int col = i % w;
            lttButton button = createGridButton(row, col, currentText);
            list.add(button);
            add(button);
        }

        setPreferredSize(new Dimension(width * button_width, height * button_heigth));
        revalidate();
        repaint();
        grabFocus();
    }
}


