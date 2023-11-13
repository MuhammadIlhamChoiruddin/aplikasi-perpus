import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class lihatpengembalian {

    public static void tampilkan_Data_Pengembalian() {
        JFrame pengembalianBukuFrame = new JFrame("Lihat Pengembalian Buku");
        ImageIcon backgroundImage = new ImageIcon("src/img/cream.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 600, 400);

        DefaultTableModel model = new DefaultTableModel();
        JTable dataTable = new JTable(model);
        model.addColumn("Kode Buku");
        model.addColumn("ID Anggota");
        model.addColumn("Tanggal Kembali");

        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBounds(30, 30, 550, 200);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(30, 240, 100, 30);
        refreshButton.addActionListener(e -> refreshTableData(model));

        JLabel kodeBukuLabel = new JLabel("Kode Buku:");
        kodeBukuLabel.setBounds(30, 280, 100, 30);

        JTextField kodeBukuField = new JTextField();
        kodeBukuField.setBounds(140, 280, 100, 30);

        JButton editButton = new JButton("Edit");
        editButton.setBounds(250, 280, 100, 30);
        editButton.addActionListener(e -> {
            String kdbuku = kodeBukuField.getText();
            if (!kdbuku.isEmpty()) {
                tampilkanFormEdit(kdbuku, model);
            } else {
                JOptionPane.showMessageDialog(null, "Masukkan Kode Buku untuk mengedit.");
            }
        });

        JButton deleteButton = new JButton("Hapus");
        deleteButton.setBounds(360, 280, 100, 30);
        deleteButton.addActionListener(e -> {
            String kdbuku = kodeBukuField.getText();
            if (!kdbuku.isEmpty()) {
                deleteDataByKodeBuku(kdbuku, model);
            } else {
                JOptionPane.showMessageDialog(null, "Masukkan Kode Buku untuk menghapus.");
            }
        });

        JButton searchButton = new JButton("Cari");
        searchButton.setBounds(470, 280, 100, 30);
        searchButton.addActionListener(e -> {
            String kdbuku = kodeBukuField.getText();
            if (!kdbuku.isEmpty()) {
                searchByKodeBuku(kdbuku, model);
            } else {
                JOptionPane.showMessageDialog(null, "Masukkan Kode Buku untuk mencari.");
            }
        });

        pengembalianBukuFrame.add(scrollPane);
        pengembalianBukuFrame.add(refreshButton);
        pengembalianBukuFrame.add(kodeBukuLabel);
        pengembalianBukuFrame.add(kodeBukuField);
        pengembalianBukuFrame.add(editButton);
        pengembalianBukuFrame.add(deleteButton);
        pengembalianBukuFrame.add(searchButton);
        pengembalianBukuFrame.add(backgroundLabel);

        pengembalianBukuFrame.setSize(615, 380);
        pengembalianBukuFrame.setLayout(null);
        pengembalianBukuFrame.setVisible(true);
        pengembalianBukuFrame.setLocationRelativeTo(null);
    }

    private static void refreshTableData(DefaultTableModel model) {
        Connection connection = Koneksi.connect();
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String selectQuery = "SELECT kdbuku, idanggota, tglkembali FROM pengembalian_buku";
                ResultSet rs = stmt.executeQuery(selectQuery);

                model.setRowCount(0);

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getString("kdbuku"),
                            rs.getString("idanggota"),
                            rs.getString("tglkembali")
                    });
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + ex.getMessage());
            }
        }
    }

    private static void tampilkanFormEdit(String kdbuku, DefaultTableModel model) {
        JFrame editFrame = new JFrame("Edit Data Pengembalian");
        editFrame.setSize(400, 300);
        editFrame.setLayout(null);

        JLabel kodeBukuLabel = new JLabel("Kode Buku:");
        kodeBukuLabel.setBounds(30, 30, 120, 30);

        JTextField kodeBukuField = new JTextField();
        kodeBukuField.setBounds(160, 30, 150, 30);
        kodeBukuField.setEditable(false);
        kodeBukuField.setText(kdbuku);

        JLabel idAnggotaLabel = new JLabel("ID Anggota:");
        idAnggotaLabel.setBounds(30, 70, 120, 30);

        JTextField idAnggotaField = new JTextField();
        idAnggotaField.setBounds(160, 70, 150, 30);

        JLabel tglKembaliLabel = new JLabel("Tanggal Kembali:");
        tglKembaliLabel.setBounds(30, 110, 120, 30);

        JTextField tglKembaliField = new JTextField();
        tglKembaliField.setBounds(160, 110, 150, 30);

        JButton simpanButton = new JButton("Simpan");
        simpanButton.setBounds(120, 200, 100, 30);
        simpanButton.addActionListener(e -> {
            String newIdAnggota = idAnggotaField.getText();
            String newTglKembali = tglKembaliField.getText();
            refreshTableData(model);
            editFrame.dispose();
        });

        editFrame.add(kodeBukuLabel);
        editFrame.add(kodeBukuField);
        editFrame.add(idAnggotaLabel);
        editFrame.add(idAnggotaField);
        editFrame.add(tglKembaliLabel);
        editFrame.add(tglKembaliField);
        editFrame.add(simpanButton);

        editFrame.setVisible(true);
        editFrame.setLocationRelativeTo(null);
    }


    private static void deleteDataByKodeBuku(String kdbuku, DefaultTableModel model) {
        Connection connection = Koneksi.connect();
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String deleteQuery = "DELETE FROM pengembalian_buku WHERE kdbuku = '" + kdbuku + "'";
                int rowsAffected = stmt.executeUpdate(deleteQuery);
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Data dengan Kode Buku " + kdbuku + " berhasil dihapus.");
                    refreshTableData(model);
                } else {
                    JOptionPane.showMessageDialog(null, "Data dengan Kode Buku " + kdbuku + " tidak ditemukan.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + ex.getMessage());
            }
        }
    }

    private static void searchByKodeBuku(String kdbuku, DefaultTableModel model) {
        Connection connection = Koneksi.connect();
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String searchQuery = "SELECT kdbuku, idanggota, tglkembali FROM pengembalian_buku WHERE kdbuku = '" + kdbuku + "'";
                ResultSet rs = stmt.executeQuery(searchQuery);

                model.setRowCount(0);

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getString("kdbuku"),
                            rs.getString("idanggota"),
                            rs.getString("tglkembali")
                    });
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + ex.getMessage());
            }
        }
    }
}
