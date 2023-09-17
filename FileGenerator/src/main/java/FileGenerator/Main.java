package FileGenerator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;

import static FileGenerator.Generators.ProjectStructureBuilder.buildProjectStructure;

public class Main {

    private static String wybranaSciezka = null;
    public static void main(String[] args) {

        JFrame frame = new JFrame("Wybieranie ścieżki");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel();
        frame.add(panel);

        JButton button = new JButton("Wybierz ścieżkę");
        panel.add(button);

        JTextField textField = new JTextField(20);
        panel.add(textField);

        JLabel label = new JLabel("Nazwa Programu:");
        panel.add(label);

        JTextField nazwaProgramuField = new JTextField(20);
        panel.add(nazwaProgramuField);

        JButton generujButton = new JButton("Wygeneruj Projekt");
        panel.add(generujButton);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    wybranaSciezka = selectedFile.getAbsolutePath();
                    textField.setText(wybranaSciezka);
                }
            }
        });

        generujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nazwaProgramu = nazwaProgramuField.getText();
                if (wybranaSciezka != null && !nazwaProgramu.isEmpty()) {
                    String sciezkaZNazwa = wybranaSciezka + File.separator + nazwaProgramu;

                    buildProjectStructure(sciezkaZNazwa);

                    JOptionPane.showMessageDialog(null, "Projekt zostanie wygenerowany w ścieżce: " + sciezkaZNazwa);
                } else {
                    JOptionPane.showMessageDialog(null, "Proszę najpierw wybrać ścieżkę i podać nazwę programu.");
                }
            }
        });

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}
