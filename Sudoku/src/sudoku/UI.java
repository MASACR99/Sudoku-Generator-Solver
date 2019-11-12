/*
 *  Code created by Joan Gil Rigo aka MASACR
 */
package sudoku;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Will open a window with a 9x9 square separete the 3x3 from each other wait
 * for the user to choose with one button or another which method (new sudoku
 * from 0 or place one)
 *
 * @author MASACR
 */
public class UI {

    int sudoku[][] = new int[9][9];

    public UI() {
        JFrame frame = new JFrame("Sudoku solver9000");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setResizable(true);
        JButton new_sudoku = new JButton("Generate random sudoku");
        JButton solve_sudoku = new JButton("Solve the sudoku on-screen");
        JPanel sudoku_panel = new JPanel(new GridLayout(3, 3));
        JPanel auxiliar_panel[] = new JPanel[9];
        JPanel button_panel = new JPanel(new GridLayout(1, 2));
        JTextField panes[][] = new JTextField[9][9];
        button_panel.add(new_sudoku);
        button_panel.add(solve_sudoku);
        int z = 0;

        new_sudoku.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sudoku = Sudoku.sudokuGenerator();
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (sudoku[i][j] == 0) {
                            panes[i][j].setText(null);
                        } else {
                            panes[i][j].setText(String.valueOf(sudoku[i][j]));
                        }
                    }
                }
            }
        });

        solve_sudoku.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sudoku.sudokuSolver9000(sudoku, 0, 0);
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (sudoku[i][j] == 0) {
                            panes[i][j].setText(null);
                        } else {
                            panes[i][j].setText(String.valueOf(sudoku[i][j]));
                        }
                    }
                }
            }
        });

        for (int i = 0; i < 9; i++) {
            auxiliar_panel[i] = new JPanel(new GridLayout(3, 3));
            auxiliar_panel[i].setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        }
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                panes[i][j] = new JTextField();
                panes[i][j].setPreferredSize(new Dimension(40, 40));
                panes[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JTextField source = (JTextField) e.getSource();
                        int row = -1;
                        int col = -1;
                        boolean found = false;
                        for (int i = 0; i < 9 && found == false; i++) {
                            for (int j = 0; j < 9 && found == false; j++) {
                                if (panes[i][j] == source) {
                                    found = true;
                                    row = i;
                                    col = j;
                                }
                            }
                        }
                        if (panes[row][col].getText().matches("[0-9]?\\d") || panes[row][col].getText().matches(null)) {
                            if (Sudoku.checker(row, col, Integer.parseInt(panes[row][col].getText()), sudoku)) {
                                sudoku[row][col] = Integer.parseInt(panes[row][col].getText());
                            } else {
                                JOptionPane.showMessageDialog(frame, "The number you tried to use isn't correct");
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "You should just use numbers from 0 to 9!");
                        }
                    }
                });
            }
        }
        for (int i = 0; i < 9; i++) {
            if (i == 3) {
                z = z + 3;
            } else if (i == 6) {
                z = z + 3;
            }
            for (int j = 0; j < 9; j++) {
                if (j == 3) {
                    z++;
                } else if (j == 6) {
                    z++;
                }
                auxiliar_panel[z].add(panes[i][j]);
            }
            z = z - 2;
        }
        for(int i = 0; i < 9; i++){
            sudoku_panel.add(auxiliar_panel[i]);
        }
        frame.add(sudoku_panel, BorderLayout.WEST);
        frame.add(button_panel, BorderLayout.EAST);
        frame.pack();
        frame.setVisible(true);
    }
}
