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

import java.awt.*;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;


class lttButton extends JButton
{
    private static final Logger LOGGER = Logger.getLogger( lttButton.class.getName() );

    public static int WIDTH = 75;
    public static int HEIGHT = 50;

    private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;
    private Color clickedBackgroundColor = Color.red;
    private Color defaultBackgroundColor;

    public lttButton(String text)
    {
        super(text);
        super.setContentAreaFilled(false);
        super.setPreferredSize(new Dimension(lttButton.WIDTH, lttButton.HEIGHT));

        super.setForeground(Color.white);
        super.setHorizontalTextPosition(SwingConstants.CENTER);
        super.setBorder(new LineBorder(Color.BLACK));
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        if (getModel().isPressed())
        {
            g.setColor(pressedBackgroundColor);
        }
        else if (getModel().isRollover())
        {
            g.setColor(hoverBackgroundColor);
        }
        else
        {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b) { }

    public void switchBackground()
    {
        if (getBackground() != clickedBackgroundColor)
        {
            setBackground(clickedBackgroundColor);
        }
        else
        {
            setBackground(defaultBackgroundColor);
        }
    }

    public Color getHoverBackgroundColor()
    {
        return hoverBackgroundColor;
    }

    public void setDefaultBackgroundColor(Color defaultBackgroundColor)
    {
        defaultBackgroundColor = defaultBackgroundColor;
        setBackground(defaultBackgroundColor);
    }

    public void setHoverBackgroundColor(Color hoverBackgroundColor)
    {
        hoverBackgroundColor = hoverBackgroundColor;
    }

    public Color getPressedBackgroundColor()
    {
        return pressedBackgroundColor;
    }

    public void setPressedBackgroundColor(Color pressedBackgroundColor)
    {
        pressedBackgroundColor = pressedBackgroundColor;
    }
}

