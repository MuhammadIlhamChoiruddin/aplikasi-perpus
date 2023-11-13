import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class lihatpeminjaman {

    public static void tampilkan_Peminjaman_Buku() {
        JFrame peminjamanBukuFrame = new JFrame("Lihat Peminjaman Buku");
        ImageIcon backgroundImage = new ImageIcon("src/img/cream.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 600, 400);

        DefaultTableModel model = new DefaultTableModel();
        JTable dataTable = new JTable(model);
        model.addColumn("ID Peminjaman");
        model.addColumn("Kode Buku");
        model.addColumn("ID Anggota");
        model.addColumn("Tanggal Pinjam");
        model.addColumn("Tanggal Kembali");

        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBounds(30, 30, 550, 200);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(30, 240, 100, 30);
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshTableData(model);
            }
        });

        JLabel idLabel = new JLabel("ID Peminjaman:");
        idLabel.setBounds(30, 280, 100, 30);

        JTextField idTextField = new JTextField();
        idTextField.setBounds(140, 280, 100, 30);

        JButton editButton = new JButton("Edit");
        editButton.setBounds(250, 280, 100, 30);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idPeminjaman = idTextField.getText();
                if (!idPeminjaman.isEmpty()) {
                    tampilkanFormEdit(idPeminjaman, model);
                } else {
                    JOptionPane.showMessageDialog(null, "Masukkan ID Peminjaman untuk mengedit.");
                }
            }
        });

        JButton deleteButton = new JButton("Hapus");
        deleteButton.setBounds(360, 280, 100, 30);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idPeminjaman = idTextField.getText();
                if (!idPeminjaman.isEmpty()) {
                    deletePeminjamanByID(idPeminjaman, model);
                } else {
                    JOptionPane.showMessageDialog(null, "Masukkan ID Peminjaman untuk menghapus.");
                }
            }
        });

        peminjamanBukuFrame.add(scrollPane);
        peminjamanBukuFrame.add(refreshButton);
        peminjamanBukuFrame.add(idLabel);
        peminjamanBukuFrame.add(idTextField);
        peminjamanBukuFrame.add(editButton);
        peminjamanBukuFrame.add(deleteButton);
        peminjamanBukuFrame.add(backgroundLabel);

        peminjamanBukuFrame.setSize(615, 380);
        peminjamanBukuFrame.setLayout(null);
        peminjamanBukuFrame.setVisible(true);
        peminjamanBukuFrame.setLocationRelativeTo(null);
    }

    private static void tampilkanFormEdit(String idPeminjaman, DefaultTableModel model) {
        JFrame editFrame = new JFrame("Edit Data Peminjaman");
        editFrame.setSize(400, 300);
        editFrame.setLayout(null);

        JLabel idLabel = new JLabel("ID Peminjaman:");
        idLabel.setBounds(30, 30, 120, 30);

        JTextField idField = new JTextField();
        idField.setBounds(160, 30, 150, 30);
        idField.setEditable(false);
        idField.setText(idPeminjaman);

        // Other fields for editing, for example:
        JLabel kodeBukuLabel = new JLabel("Kode Buku:");
        kodeBukuLabel.setBounds(30, 70, 120, 30);

        JTextField kodeBukuField = new JTextField();
        kodeBukuField.setBounds(160, 70, 150, 30);

        JLabel idAnggotaLabel = new JLabel("ID Anggota:");
        idAnggotaLabel.setBounds(30, 110, 120, 30);

        JTextField idAnggotaField = new JTextField();
        idAnggotaField.setBounds(160, 110, 150, 30);

        // Similarly, add other fields as required for editing

        JButton simpanButton = new JButton("Simpan");
        simpanButton.setBounds(120, 200, 100, 30);
        simpanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get updated data from text fields
                String newKodeBuku = kodeBukuField.getText();
                String newIdAnggota = idAnggotaField.getText();

                // Call a method to update data in the database
                updatePeminjamanData(idPeminjaman, newKodeBuku, newIdAnggota);

                // Refresh the main table data after editing
                refreshTableData(model);

                // Close the edit frame after saving changes
                editFrame.dispose();
            }
        });

        editFrame.add(idLabel);
        editFrame.add(idField);
        editFrame.add(kodeBukuLabel);
        editFrame.add(kodeBukuField);
        editFrame.add(idAnggotaLabel);
        editFrame.add(idAnggotaField);
        // Add other fields for editing

        editFrame.add(simpanButton);

        editFrame.setVisible(true);
        editFrame.setLocationRelativeTo(null);
    }

    private static void updatePeminjamanData(String idPeminjaman, String newKodeBuku, String newIdAnggota) {
        Connection connection = Koneksi.connect();
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String updateQuery = "UPDATE peminjaman_buku SET kdbuku = '" + newKodeBuku + "', idanggota = '" + newIdAnggota + "' WHERE idpeminjaman = '" + idPeminjaman + "'";
                int rowsAffected = stmt.executeUpdate(updateQuery);
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Data dengan ID " + idPeminjaman + " berhasil diperbarui.");
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal memperbarui data dengan ID " + idPeminjaman);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + ex.getMessage());
            }
        }
    }

    private static void deletePeminjamanByID(String idPeminjaman, DefaultTableModel model) {
        Connection connection = Koneksi.connect();
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String deleteQuery = "DELETE FROM peminjaman_buku WHERE idpeminjaman = '" + idPeminjaman + "'";
                int rowsAffected = stmt.executeUpdate(deleteQuery);
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Data dengan ID " + idPeminjaman + " berhasil dihapus.");
                } else {
                    JOptionPane.showMessageDialog(null, "Data dengan ID " + idPeminjaman + " tidak ditemukan.");
                }
                refreshTableData(model);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + ex.getMessage());
            }
        }
    }

    private static void refreshTableData(DefaultTableModel model) {
        Connection connection = Koneksi.connect();
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String selectQuery = "SELECT * FROM peminjaman_buku";
                ResultSet rs = stmt.executeQuery(selectQuery);

                model.setRowCount(0); // Clear existing rows in the table

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getString("idpeminjaman"),
                            rs.getString("kdbuku"),
                            rs.getString("idanggota"),
                            rs.getString("tglpinjam"),
                            rs.getString("tglkembali")
                    });
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + ex.getMessage());
            }
        }
    }

    // Koneksi.connect() method should exist in your Koneksi class for database connection
}
