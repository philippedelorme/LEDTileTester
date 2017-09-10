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
import java.awt.event.*;
import javax.swing.*;
import javax.swing.colorchooser.*;
import javax.swing.text.Document;
import javax.swing.event.*;


class lttSettings extends JPanel
{
    private static final Logger LOGGER = Logger.getLogger( lttSettings.class.getName() );

    private JFrame frame;
    private lttPanel panel;
    private JPanel container;

    private JLabel textLabel;
    private JTextField textInput;

    private JColorChooser tileIn;
    private JColorChooser tileOut;

    private JSpinner nbTileX, nbTileY;
    private JSpinner sizeTileX, sizeTileY;

    private ChangeListener pickerListener, nbTileListener, sizeTileListener;


    public lttSettings(JFrame frame, lttPanel panel)
    {
        this.frame = frame;
        this.panel = panel;

        setBorder(BorderFactory.createTitledBorder("Settings"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        build();
    }


    private void build()
    {
        // This is the mani container for everything but the text part
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        //
        // Text setter
        //
        textLabel = new JLabel("Text: ");
        textInput = new JTextField(50);
        textInput.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                String text = lttSettings.this.textInput.getText();
                lttSettings.this.panel.setText(text);
            }
        });
        JPanel p = new JPanel();
        p.add(textLabel);
        p.add(textInput);

        add(p);


        //
        // Color selection
        //

        // actions
        pickerListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                Color newColor;

                newColor = lttSettings.this.tileIn.getColor();
                LOGGER.info("Get tile color: " + newColor.toString());
                if (newColor != null)
                    lttSettings.this.panel.setBackgroundColor(newColor);

                newColor = lttSettings.this.tileOut.getColor();
                LOGGER.info("Get border color: " + newColor.toString());
                if (newColor != null)
                    lttSettings.this.panel.setBorderColor(newColor);
            }
        };

        tileIn = newColorChooser("Tile color", Color.blue);
        tileOut = newColorChooser("Border color", Color.black);

        container.add(this.tileIn, BorderLayout.PAGE_END);
        container.add(this.tileOut, BorderLayout.PAGE_END);


        //
        // Spinner
        //

        // actions
        nbTileListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                lttSettings.this.panel.width = (int) lttSettings.this.nbTileX.getValue();
                lttSettings.this.panel.height = (int) lttSettings.this.nbTileY.getValue();
                lttSettings.this.panel.refresh();
            }
        };

        sizeTileListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                lttSettings.this.panel.button_width = (int) lttSettings.this.sizeTileX.getValue();
                lttSettings.this.panel.button_heigth = (int) lttSettings.this.sizeTileY.getValue();
                lttSettings.this.panel.refreshButtonSize();
            }
        };

        JPanel c = new JPanel(new GridLayout(0,2));
        c.setBorder(BorderFactory.createTitledBorder("Number and size of tiles"));

        nbTileX = this.newSpinner(c, "Tile # - horizontal", lttPanel.DEFAULT_WIDTH, 1, 10000, 1);
        nbTileY = this.newSpinner(c, "Tile # - vertical", lttPanel.DEFAULT_HEIGHT, 1, 10000, 1);
        nbTileX.addChangeListener(nbTileListener);
        nbTileY.addChangeListener(nbTileListener);

        sizeTileX = this.newSpinner(c, "Tile size - horizontal", lttButton.WIDTH, 25, 10000, 1);
        sizeTileY = this.newSpinner(c, "Tile size - vertical", lttButton.HEIGHT, 25, 10000, 1);
        sizeTileX.addChangeListener(sizeTileListener);
        sizeTileY.addChangeListener(sizeTileListener);

        container.add(c);

        add(container);
    }


    private JColorChooser newColorChooser(String title, Color color)
    {
        JColorChooser chooser = new JColorChooser(color);

        // only keep the first tab of the color chooser
        AbstractColorChooserPanel defaultPanels[] = chooser.getChooserPanels();
        if (defaultPanels.length > 0)
            defaultPanels = new AbstractColorChooserPanel[]{defaultPanels[0]};
        chooser.setChooserPanels(defaultPanels);

        // remove the preview panel
        chooser.setPreviewPanel(new JPanel());

        // events and title
        chooser.getSelectionModel().addChangeListener(this.pickerListener);
        chooser.setBorder(BorderFactory.createTitledBorder(title));

        return chooser;
    }



    /**
     * Return the formatted text field used by the editor, or
     * null if the editor doesn't descend from JSpinner.DefaultEditor.
     */
    public JFormattedTextField getTextField(JSpinner spinner) {
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            return ((JSpinner.DefaultEditor)editor).getTextField();
        } else {
            LOGGER.warning("Unexpected editor type: "
                    + spinner.getEditor().getClass()
                    + " isn't a descendant of DefaultEditor");
            return null;
        }
    }


    private JSpinner newSpinner(JPanel c, String label, int init, int min, int max, int step)
    {
        JLabel jl = new JLabel(label);
        c.add(jl);

        SpinnerModel model = new SpinnerNumberModel(init, min, max, step); 

        JSpinner spinner = new JSpinner(model);
        jl.setLabelFor(spinner);
        c.add(spinner);

        JFormattedTextField text = this.getTextField(spinner);
        if (text != null ) {
            text.setColumns(8);
            text.setHorizontalAlignment(JTextField.RIGHT);
        }

        return spinner;
    }
}


