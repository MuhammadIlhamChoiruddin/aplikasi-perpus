import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class peminjamanbuku {
    public static void peminjaman_buku() {
        JFrame peminjamanBukuFrame = new JFrame("Peminjaman Buku");
        ImageIcon backgroundImage = new ImageIcon("src/img/cream.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 650, 450);

        JLabel labelIdPeminjaman,labelKodeBuku, labelIdAnggota, labelTglPinjam, labelTglKembali;
        labelIdPeminjaman = new JLabel("Id Peminjaman:");
        labelIdPeminjaman.setBounds(30, 15, 100, 30);

        labelKodeBuku = new JLabel("Kode Buku:");
        labelKodeBuku.setBounds(30, 55, 100, 30);

        labelIdAnggota = new JLabel("ID Anggota:");
        labelIdAnggota.setBounds(30, 95, 100, 30);

        labelTglPinjam = new JLabel("Tgl Pinjam:");
        labelTglPinjam.setBounds(30, 135, 100, 30);

        labelTglKembali = new JLabel("Tgl Kembali:");
        labelTglKembali.setBounds(30, 175, 100, 30);

        JTextField idPeminjamanField, kdBukuField, idAnggotaField, tglPinjamField, tglKembaliField;
        idPeminjamanField = new JTextField();
        idPeminjamanField.setBounds(130, 15, 200, 30);

        kdBukuField = new JTextField();
        kdBukuField.setBounds(130, 55, 200, 30);

        idAnggotaField = new JTextField();
        idAnggotaField.setBounds(130, 95, 200, 30);

        tglPinjamField = new JTextField();
        tglPinjamField.setBounds(130, 135, 200, 30);

        tglKembaliField = new JTextField();
        tglKembaliField.setBounds(130, 175, 200, 30);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(130, 215, 100, 25);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idpeminjaman = idPeminjamanField.getText();
                String kdbuku = kdBukuField.getText();
                String idanggota = idAnggotaField.getText();
                String tglpinjam = tglPinjamField.getText();
                String tglkembali = tglKembaliField.getText();

                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        Statement stmt = connection.createStatement();
                        String insertQuery = "INSERT INTO peminjaman_buku (idpeminjaman, kdbuku, idanggota, tglpinjam, tglkembali) VALUES ('" + idpeminjaman + "' , '" + kdbuku + "', '" + idanggota + "', '" + tglpinjam + "', '" + tglkembali + "')";
                        int rowsAffected = stmt.executeUpdate(insertQuery);
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(peminjamanBukuFrame, "Data Peminjaman Buku berhasil ditambahkan.");
                        } else {
                            JOptionPane.showMessageDialog(peminjamanBukuFrame, "Gagal menambahkan data Peminjaman Buku.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(peminjamanBukuFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        peminjamanBukuFrame.add(labelIdPeminjaman);
        peminjamanBukuFrame.add(idPeminjamanField);
        peminjamanBukuFrame.add(labelKodeBuku);
        peminjamanBukuFrame.add(kdBukuField);
        peminjamanBukuFrame.add(labelIdAnggota);
        peminjamanBukuFrame.add(idAnggotaField);
        peminjamanBukuFrame.add(labelTglPinjam);
        peminjamanBukuFrame.add(tglPinjamField);
        peminjamanBukuFrame.add(labelTglKembali);
        peminjamanBukuFrame.add(tglKembaliField);
        peminjamanBukuFrame.add(submitButton);
        peminjamanBukuFrame.add(backgroundLabel);

        peminjamanBukuFrame.setSize(400, 300);
        peminjamanBukuFrame.setLayout(null);
        peminjamanBukuFrame.setVisible(true);
        peminjamanBukuFrame.setLocationRelativeTo(null);
    }
}
