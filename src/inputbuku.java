import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class inputbuku {
    public static void input_buku() {
        JFrame inputBukuFrame = new JFrame("Input Buku");
        ImageIcon backgroundImage = new ImageIcon("src/img/cream.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 600, 350);

        JLabel labelKodeBuku, labelJudul, labelPengarang, labelTahunTerbit, labelCetakan;
        labelKodeBuku = new JLabel("Kode Buku:");
        labelKodeBuku.setBounds(30, 15, 100, 30);

        labelJudul = new JLabel("Judul:");
        labelJudul.setBounds(30, 55, 100, 30);

        labelPengarang = new JLabel("Pengarang:");
        labelPengarang.setBounds(30, 95, 100, 30);

        labelTahunTerbit = new JLabel("Tahun Terbit:");
        labelTahunTerbit.setBounds(30, 135, 100, 30);

        labelCetakan = new JLabel("Cetakan:");
        labelCetakan.setBounds(30, 175, 100, 30);

        JTextField kodeBukuField, judulField, pengarangField, tahunTerbitField, cetakanField;
        kodeBukuField = new JTextField();
        kodeBukuField.setBounds(130, 15, 200, 30);

        judulField = new JTextField();
        judulField.setBounds(130, 55, 200, 30);

        pengarangField = new JTextField();
        pengarangField.setBounds(130, 95, 200, 30);

        tahunTerbitField = new JTextField();
        tahunTerbitField.setBounds(130, 135, 200, 30);

        cetakanField = new JTextField();
        cetakanField.setBounds(130, 175, 200, 30);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(130, 215, 100, 25);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String kodeBuku = kodeBukuField.getText();
                String judul = judulField.getText();
                String pengarang = pengarangField.getText();
                String tahunTerbit = tahunTerbitField.getText();
                String cetakan = cetakanField.getText();

                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        Statement stmt = connection.createStatement();
                        String insertQuery = "INSERT INTO buku (kdbuku, judul, pengarang, tterbit, cetakan) VALUES ('" + kodeBuku
                                + "', '" + judul + "', '" + pengarang + "', '" + tahunTerbit + "', '" + cetakan + "')";
                        int rowsAffected = stmt.executeUpdate(insertQuery);
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(inputBukuFrame, "Data Buku berhasil ditambahkan.");
                        } else {
                            JOptionPane.showMessageDialog(inputBukuFrame, "Gagal menambahkan data Buku.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(inputBukuFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        inputBukuFrame.add(labelKodeBuku);
        inputBukuFrame.add(kodeBukuField);
        inputBukuFrame.add(labelJudul);
        inputBukuFrame.add(judulField);
        inputBukuFrame.add(labelPengarang);
        inputBukuFrame.add(pengarangField);
        inputBukuFrame.add(labelTahunTerbit);
        inputBukuFrame.add(tahunTerbitField);
        inputBukuFrame.add(labelCetakan);
        inputBukuFrame.add(cetakanField);
        inputBukuFrame.add(submitButton);
        inputBukuFrame.add(backgroundLabel);

        inputBukuFrame.setSize(400, 300);
        inputBukuFrame.setLayout(null);
        inputBukuFrame.setVisible(true);
        inputBukuFrame.setLocationRelativeTo(null);
    };
}
