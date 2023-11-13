import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class lihatbuku {

    public static void tampilkan_data_buku() {
        JFrame dataBukuFrame = new JFrame("Data Buku");
        ImageIcon backgroundImage = new ImageIcon("src/img/cream.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 700, 350);

        // Tabel untuk menampilkan data buku
        DefaultTableModel model = new DefaultTableModel();
        JTable dataTable = new JTable(model);
        model.addColumn("Kode Buku");
        model.addColumn("Judul");
        model.addColumn("Pengarang");
        model.addColumn("Tahun Terbit");
        model.addColumn("Cetakan");

        // Scroll pane agar bisa di-scroll jika data banyak
        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBounds(30, 30, 600, 200);

        // Tombol Refresh
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(30, 250, 80, 30);
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Mengambil data buku terbaru dan menampilkannya di tabel
                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        Statement stmt = connection.createStatement();
                        String selectQuery = "SELECT * FROM buku";
                        ResultSet rs = stmt.executeQuery(selectQuery);

                        // Clear existing rows in the table
                        model.setRowCount(0);

                        while (rs.next()) {
                            model.addRow(new Object[]{
                                    rs.getString("kdbuku"),
                                    rs.getString("judul"),
                                    rs.getString("pengarang"),
                                    rs.getString("tterbit"),
                                    rs.getString("cetakan")
                            });
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(dataBukuFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        // Tombol Hapus Buku berdasarkan kode buku
        JLabel labelKodeHapus = new JLabel("Kode Buku:");
        labelKodeHapus.setBounds(130, 250, 100, 30);

        JTextField kodeHapusField = new JTextField();
        kodeHapusField.setBounds(230, 250, 100, 30);

        JButton hapusButton = new JButton("Hapus");
        hapusButton.setBounds(340, 250, 80, 30);
        hapusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String kodeBukuHapus = kodeHapusField.getText();
                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        Statement stmt = connection.createStatement();
                        String deleteQuery = "DELETE FROM buku WHERE kdbuku = '" + kodeBukuHapus + "'";
                        int rowsAffected = stmt.executeUpdate(deleteQuery);
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(dataBukuFrame, "Data Buku berhasil dihapus.");
                            // Refresh the table to show the updated data
                            refreshButton.doClick();
                        } else {
                            JOptionPane.showMessageDialog(dataBukuFrame, "Gagal menghapus data Buku.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(dataBukuFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        // Tombol Edit Buku
        JButton editButton = new JButton("Edit Buku");
        editButton.setBounds(430, 250, 150, 30);
        editButton.addActionListener(new ActionListener() {
            ImageIcon backgroundImage = new ImageIcon("src/img/cream.jpg");
            JLabel backgroundLabel = new JLabel(backgroundImage);
            public void actionPerformed(ActionEvent e) {
                editDataBuku();
            }
        });

        dataBukuFrame.add(scrollPane);
        dataBukuFrame.add(labelKodeHapus);
        dataBukuFrame.add(kodeHapusField);
        dataBukuFrame.add(hapusButton);
        dataBukuFrame.add(editButton);
        dataBukuFrame.add(refreshButton);
        dataBukuFrame.add(backgroundLabel);

        dataBukuFrame.setSize(700, 350);
        dataBukuFrame.setLayout(null);
        dataBukuFrame.setVisible(true);
        dataBukuFrame.setLocationRelativeTo(null);
    }

    public static void editDataBuku() {
        JFrame editBukuFrame = new JFrame("Edit Data Buku");
        ImageIcon backgroundImage = new ImageIcon("src/img/cream.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        JLabel labelKodeBuku, labelJudul, labelPengarang, labelTahunTerbit, labelCetakan;
        JTextField kodeBukuField, judulField, pengarangField, tahunTerbitField, cetakanField;
        JButton simpanButton;

        labelKodeBuku = new JLabel("Kode Buku:");
        labelKodeBuku.setBounds(30, 30, 100, 30);

        labelJudul = new JLabel("Judul:");
        labelJudul.setBounds(30, 70, 100, 30);

        labelPengarang = new JLabel("Pengarang:");
        labelPengarang.setBounds(30, 110, 100, 30);

        labelTahunTerbit = new JLabel("Tahun Terbit:");
        labelTahunTerbit.setBounds(30, 150, 100, 30);

        labelCetakan = new JLabel("Cetakan:");
        labelCetakan.setBounds(30, 190, 100, 30);

        kodeBukuField = new JTextField();
        kodeBukuField.setBounds(150, 30, 200, 30);

        judulField = new JTextField();
        judulField.setBounds(150, 70, 200, 30);

        pengarangField = new JTextField();
        pengarangField.setBounds(150, 110, 200, 30);

        tahunTerbitField = new JTextField();
        tahunTerbitField.setBounds(150, 150, 200, 30);

        cetakanField = new JTextField();
        cetakanField.setBounds(150, 190, 200, 30);

        simpanButton = new JButton("Simpan");
        simpanButton.setBounds(150, 230, 80, 30);
        simpanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String kodeBuku = kodeBukuField.getText();
                String judul = judulField.getText();
                String pengarang = pengarangField.getText();
                String tahunTerbit = tahunTerbitField.getText();
                String cetakan = cetakanField.getText();

                // Update data buku ke dalam database
                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        String updateQuery = "UPDATE buku SET judul=?, pengarang=?, tterbit=?, cetakan=? WHERE kdbuku=?";
                        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                        preparedStatement.setString(1, judul);
                        preparedStatement.setString(2, pengarang);
                        preparedStatement.setString(3, tahunTerbit);
                        preparedStatement.setString(4, cetakan);
                        preparedStatement.setString(5, kodeBuku);

                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(editBukuFrame, "Data Buku berhasil diperbarui.");
                            editBukuFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(editBukuFrame, "Gagal memperbarui data Buku.");
                        }
                        preparedStatement.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(editBukuFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        editBukuFrame.add(labelKodeBuku);
        editBukuFrame.add(labelJudul);
        editBukuFrame.add(labelPengarang);
        editBukuFrame.add(labelTahunTerbit);
        editBukuFrame.add(labelCetakan);
        editBukuFrame.add(kodeBukuField);
        editBukuFrame.add(judulField);
        editBukuFrame.add(pengarangField);
        editBukuFrame.add(tahunTerbitField);
        editBukuFrame.add(cetakanField);
        editBukuFrame.add(simpanButton);
        editBukuFrame.add(backgroundLabel);

        editBukuFrame.setSize(400, 300);
        editBukuFrame.setLayout(null);
        editBukuFrame.setVisible(true);
        editBukuFrame.setLocationRelativeTo(null);
    }
}
