import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;  
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class pengembalianbuku {
    public static void pengembalian_buku() {
        JFrame pengembalianBukuFrame = new JFrame("Pengembalian Buku");
        pengembalianBukuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon backgroundImage = new ImageIcon("src/img/cream.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 600, 350);

        JLabel labelKodeBuku = new JLabel("Kode Buku:");
        labelKodeBuku.setBounds(30, 15, 100, 30);

        JLabel labelIdAnggota = new JLabel("ID Anggota:");
        labelIdAnggota.setBounds(30, 55, 100, 30);

        JLabel labelTglKembali = new JLabel("Tgl Kembali (YYYY-MM-DD):");
        labelTglKembali.setBounds(30, 95, 200, 30);

        JTextField kodeBukuField = new JTextField();
        kodeBukuField.setBounds(200, 15, 150, 30);

        JTextField idAnggotaField = new JTextField();
        idAnggotaField.setBounds(200, 55, 150, 30);

        JTextField tglKembaliField = new JTextField();
        tglKembaliField.setBounds(200, 95, 150, 30);

        JButton submitButton = new JButton("Submit"); // Mengganti tombol "Hapus" menjadi "Submit"
        submitButton.setBounds(140, 135, 100, 30);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(250, 135, 100, 30);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String kdbuku = kodeBukuField.getText();
                String idanggota = idAnggotaField.getText();
                String tglkembali = tglKembaliField.getText();

                if (kdbuku.isEmpty() || idanggota.isEmpty() || tglkembali.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Semua kolom harus diisi.");
                    return;
                }

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date tglKembali = dateFormat.parse(tglkembali);

                    // Simpan data ke database
                    Connection connection = Koneksi.connect();
                    if (connection != null) {
                        try {
                            String insertQuery = "INSERT INTO pengembalian_buku (kdbuku, idanggota, tglkembali) VALUES (?, ?, ?)";
                            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                            preparedStatement.setString(1, kdbuku);
                            preparedStatement.setString(2, idanggota);
                            preparedStatement.setDate(3, new java.sql.Date(tglKembali.getTime()));
                            int rowsAffected = preparedStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                JOptionPane.showMessageDialog(pengembalianBukuFrame, "Data Pengembalian Buku berhasil ditambahkan.");
                            } else {
                                JOptionPane.showMessageDialog(pengembalianBukuFrame, "Gagal menambahkan data Pengembalian Buku.");
                            }
                            preparedStatement.close();
                            connection.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(pengembalianBukuFrame, "Terjadi kesalahan: " + ex.getMessage());
                        }
                    }
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Format tanggal kembali tidak valid (YYYY-MM-DD).");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pengembalianBukuFrame.dispose();
            }
        });

        pengembalianBukuFrame.add(labelKodeBuku);
        pengembalianBukuFrame.add(kodeBukuField);
        pengembalianBukuFrame.add(labelIdAnggota);
        pengembalianBukuFrame.add(idAnggotaField);
        pengembalianBukuFrame.add(labelTglKembali);
        pengembalianBukuFrame.add(tglKembaliField);
        pengembalianBukuFrame.add(submitButton);
        pengembalianBukuFrame.add(cancelButton);
        pengembalianBukuFrame.add(backgroundLabel);

        pengembalianBukuFrame.setSize(400, 220);
        pengembalianBukuFrame.setLayout(null);
        pengembalianBukuFrame.setVisible(true);
        pengembalianBukuFrame.setLocationRelativeTo(null);
    }
}
