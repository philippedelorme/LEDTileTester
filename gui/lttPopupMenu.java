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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import gui.lttSettings;


class lttPopupMenu extends JPopupMenu
{
    private static final Logger LOGGER = Logger.getLogger( lttPopupMenu.class.getName() );

    private lttSettings settings;


    public lttPopupMenu(lttSettings settings)
    {
        JMenuItem item;
        this.settings = settings;

        ActionListener menuListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                String evt = event.getActionCommand();

                if (evt == "Exit")
                {
                    LOGGER.info("Good bye!");
                    System.exit(0);
                }
                else if (evt == "Hide/Show settings")
                {
                    if (lttPopupMenu.this.settings.isVisible())
                    {
                        LOGGER.info("Hide settings");
                        lttPopupMenu.this.settings.setVisible(false);
                    }
                    else
                    {
                        LOGGER.info("Show settings");
                        lttPopupMenu.this.settings.setVisible(true);
                    }
                }
            }
        };

        item = new JMenuItem("Hide/Show settings");
        item.addActionListener(menuListener);
        add(item);

        addSeparator();

        item = new JMenuItem("Exit");
        item.addActionListener(menuListener);
        add(item);
    }
}

