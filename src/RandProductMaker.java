import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductMaker extends JFrame {
    private JTextField nameField, descField, idField, costField, countField;
    private RandomAccessFile randomAccessFile;
    private int recordCount;

    public RandProductMaker() {
        setTitle("Random Product Maker");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Description:"));
        descField = new JTextField();
        panel.add(descField);

        panel.add(new JLabel("ID:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("Cost:"));
        costField = new JTextField();
        panel.add(costField);

        panel.add(new JLabel("Record Count:"));
        countField = new JTextField();
        countField.setEditable(false);
        panel.add(countField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRecord();
            }
        });
        panel.add(addButton);

        add(panel);

        try {
            randomAccessFile = new RandomAccessFile("RandomProduct.dat", "rw");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addRecord() {
        try {
            if (nameField.getText().isEmpty() || descField.getText().isEmpty()
                    || idField.getText().isEmpty() || costField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled!");
                return;
            }

            // Pad fields to the correct lengths
            String paddedName = String.format("%-35s", nameField.getText());
            String paddedDesc = String.format("%-75s", descField.getText());
            String paddedId = String.format("%-6s", idField.getText());
            double cost = Double.parseDouble(costField.getText());

            randomAccessFile.seek(randomAccessFile.length());
            randomAccessFile.writeUTF(paddedName);
            randomAccessFile.writeUTF(paddedDesc);
            randomAccessFile.writeUTF(paddedId);
            randomAccessFile.writeDouble(cost);

            recordCount++;
            countField.setText(String.valueOf(recordCount));
            nameField.setText("");
            descField.setText("");
            idField.setText("");
            costField.setText("");

        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding record.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RandProductMaker().setVisible(true));
    }
}
