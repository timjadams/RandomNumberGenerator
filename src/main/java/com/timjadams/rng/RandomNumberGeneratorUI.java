/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timjadams.rng;

import com.timjadams.rng.randomtypes.RandomType;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author Tim J. Adams
 */
public class RandomNumberGeneratorUI extends JFrame implements Runnable {
    private static final String LABEL_GENERATE_RANDOM = "Generate";
    private static final String LABEL_SELECT_RANDOM_TYPE = "Random:";
    private static final String PROPERTIES_FILE_NAME = "rng.properties";
    private static final String PROPERTY_WINDOW_LOCATION_X = "window.location.x";
    private static final String PROPERTY_WINDOW_LOCATION_Y = "window.location.y";

    private final JComboBox<String> _cbRandomType = new JComboBox<>();
    private final JTextField _tfGeneratedValue = new JTextField();
    private final Properties _properties = new Properties();

    protected RandomNumberGeneratorUI() {
        initWindow();
        initComponents();
        initProperties();
        generateRandomAndUpdateUI();
    }

    private void generateRandomAndUpdateUI() {
        final String randomTypeName = (String)_cbRandomType.getSelectedItem();
        final RandomTypeEnum randomType = RandomTypeEnum.getValueFromDescription(randomTypeName);
        final String randomValue = randomType.generateRandomValue();

        // Update the UI
        _tfGeneratedValue.setText(randomValue);

        // Copy the value to the clipboard
        StringSelection stringSelection = new StringSelection(randomValue);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;

        final JPanel randomTypePanel = new JPanel();
        randomTypePanel.add(new JLabel(LABEL_SELECT_RANDOM_TYPE));
        for (final RandomTypeEnum rte : RandomTypeEnum.values()) {
            _cbRandomType.addItem(rte.getDescription());
        }
        randomTypePanel.add(_cbRandomType);
        initComponentsAddToPane(randomTypePanel, gridBagConstraints, 0, 0, 2);

        final JButton btnGenerateRandom = new JButton(LABEL_GENERATE_RANDOM);
        btnGenerateRandom.addActionListener((final ActionEvent event) -> generateRandomAndUpdateUI());
        initComponentsAddToPane(btnGenerateRandom, gridBagConstraints, 2, 0, 1);

        _tfGeneratedValue.setEditable(false);
        initComponentsAddToPane(_tfGeneratedValue, gridBagConstraints, 0, 1, 3);

        pack();
    }

    private void initComponentsAddToPane(final JComponent c,
                                         final GridBagConstraints gbc,
                                         final int x,
                                         final int y,
                                         final int gridWidth) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = gridWidth;
        add(c, gbc);
    }

    private void initProperties() {
        final File propertiesFile = new File(PROPERTIES_FILE_NAME);
        if (propertiesFile.exists()) {
            try (final InputStream is = new FileInputStream(propertiesFile)) {
                _properties.load(is);
                is.close();
                final Point windowLocation = new Point();
                final String xString = _properties.getProperty(PROPERTY_WINDOW_LOCATION_X);
                final String yString = _properties.getProperty(PROPERTY_WINDOW_LOCATION_Y);
                final double x = Double.parseDouble(xString);
                final double y = Double.parseDouble(yString);
                windowLocation.setLocation(x, y);
                setLocation(windowLocation);
            } catch (final IOException e) {
                printException(e);
            }
        }
    }

    private void initWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(final ComponentEvent event) {
                formComponentMoved(event);
            }
        });
    }

    private void formComponentMoved(final ComponentEvent event) {
        final Point windowLocation = getLocationOnScreen();
        _properties.setProperty(PROPERTY_WINDOW_LOCATION_X, Double.toString(windowLocation.getX()));
        _properties.setProperty(PROPERTY_WINDOW_LOCATION_Y, Double.toString(windowLocation.getY()));
        saveProperties();
    }

    @Override
    public void run() {
        setVisible(true);
    }

    private void saveProperties() {
        final File f = new File(PROPERTIES_FILE_NAME);
        try (final OutputStream os = new FileOutputStream(f)) {
            final Instant now = Instant.now();
            final String nowString = DateTimeFormatter.ISO_INSTANT.format(now);
            _properties.store(os, nowString);
        } catch (final IOException e) {
            printException(e);
        }
    }

    private void printException(final Exception e) {
        System.err.println(e.getLocalizedMessage());
        e.printStackTrace(System.err);
    }
}
