import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductSearch extends JFrame {
    private JTextField searchField;
    private JTextArea resultArea;
    private RandomAccessFile randomAccessFile;

    public RandProductSearch() {
        setTitle("Random Product Search");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize GUI components
        JPanel panel = new JPanel(new GridLayout(2, 1));

        searchField = new JTextField();
        panel.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProducts();
            }
        });
        panel.add(searchButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Open the existing random access file
        try {
            randomAccessFile = new RandomAccessFile("RandomProduct.dat", "rw");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchProducts() {
        try {
            // Validate search field
            String searchName = searchField.getText();
            if (searchName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a product name to search.");
                return;
            }

            // Reset resultArea
            resultArea.setText("");

            // Search for products matching the given name and display results in the resultArea
            randomAccessFile.seek(0);
            while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
                String name = randomAccessFile.readUTF().trim();
                String desc = randomAccessFile.readUTF().trim();
                String id = randomAccessFile.readUTF().trim();
                double cost = randomAccessFile.readDouble();

                if (name.contains(searchName)) {
                    resultArea.append(String.format("Name: %s\nDescription: %s\nID: %s\nCost: %.2f\n\n",
                            name, desc, id, cost));
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching products.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RandProductSearch().setVisible(true));
    }
}
