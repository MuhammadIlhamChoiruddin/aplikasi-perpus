import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class lihatadmin {

    public static void tampilkan_Data_Admin() {
        JFrame dataAdminFrame = new JFrame("Data Admin");
        ImageIcon backgroundImage = new ImageIcon("src/img/cream.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 700, 350);

        // Tabel untuk menampilkan data admin
        DefaultTableModel model = new DefaultTableModel();
        JTable dataTable = new JTable(model);
        model.addColumn("ID Admin");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("No. HP");

        // Scroll pane agar bisa di-scroll jika data banyak
        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBounds(30, 30, 600, 200);

        // Tombol Refresh
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(30, 250, 80, 30);
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Mengambil data admin terbaru dan menampilkannya di tabel
                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        Statement stmt = connection.createStatement();
                        String selectQuery = "SELECT * FROM admin";
                        ResultSet rs = stmt.executeQuery(selectQuery);

                        // Clear existing rows in the table
                        model.setRowCount(0);

                        while (rs.next()) {
                            model.addRow(new Object[]{
                                    rs.getString("idadmin"),
                                    rs.getString("nama"),
                                    rs.getString("alamat"),
                                    rs.getString("nohp")
                            });
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(dataAdminFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        // Tombol Hapus Admin berdasarkan ID
        JTextField idAdminField = new JTextField();
        idAdminField.setBounds(120, 250, 100, 30);
        JButton deleteButton = new JButton("Hapus Admin");
        deleteButton.setBounds(230, 250, 120, 30);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Mengambil ID Admin yang diinputkan
                String idAdmin = idAdminField.getText();

                // Hapus admin berdasarkan ID Admin
                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        Statement stmt = connection.createStatement();
                        String deleteQuery = "DELETE FROM admin WHERE idadmin = '" + idAdmin + "'";
                        int rowsAffected = stmt.executeUpdate(deleteQuery);

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(dataAdminFrame, "Admin dengan ID " + idAdmin + " berhasil dihapus.");
                            idAdminField.setText(""); // Reset field setelah penghapusan
                            refreshButton.doClick(); // Refresh tabel
                        } else {
                            JOptionPane.showMessageDialog(dataAdminFrame, "Admin dengan ID " + idAdmin + " tidak ditemukan.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(dataAdminFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        // Tombol Edit Admin
        JButton editButton = new JButton("Edit Admin");
        editButton.setBounds(360, 250, 180, 30);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editDataAdmin();
            }
        });

        dataAdminFrame.add(scrollPane);
        dataAdminFrame.add(refreshButton);
        dataAdminFrame.add(idAdminField);
        dataAdminFrame.add(deleteButton);
        dataAdminFrame.add(editButton);
        dataAdminFrame.add(backgroundLabel);

        dataAdminFrame.setSize(700, 350);
        dataAdminFrame.setLayout(null);
        dataAdminFrame.setVisible(true);
        dataAdminFrame.setLocationRelativeTo(null);
        dataAdminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void editDataAdmin() {
        JFrame editAdminFrame = new JFrame("Edit Data Admin");
        ImageIcon backgroundImage = new ImageIcon("src/img/cream.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        JLabel labelIDAdmin, labelNama, labelAlamat, labelNoHP;
        JTextField idAdminField, namaField, alamatField, noHPField;
        JButton simpanButton;

        labelIDAdmin = new JLabel("ID Admin:");
        labelIDAdmin.setBounds(30, 30, 100, 30);

        labelNama = new JLabel("Nama:");
        labelNama.setBounds(30, 70, 100, 30);

        labelAlamat = new JLabel("Alamat:");
        labelAlamat.setBounds(30, 110, 100, 30);

        labelNoHP = new JLabel("No. HP:");
        labelNoHP.setBounds(30, 150, 100, 30);

        idAdminField = new JTextField();
        idAdminField.setBounds(150, 30, 200, 30);

        namaField = new JTextField();
        namaField.setBounds(150, 70, 200, 30);

        alamatField = new JTextField();
        alamatField.setBounds(150, 110, 200, 30);

        noHPField = new JTextField();
        noHPField.setBounds(150, 150, 200, 30);

        simpanButton = new JButton("Simpan");
        simpanButton.setBounds(150, 190, 80, 30);
        simpanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idAdmin = idAdminField.getText();
                String nama = namaField.getText();
                String alamat = alamatField.getText();
                String noHP = noHPField.getText();

                // Simpan perubahan data admin ke database
                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        String updateQuery = "UPDATE admin SET nama=?, alamat=?, nohp=? WHERE idadmin=?";
                        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                        preparedStatement.setString(1, nama);
                        preparedStatement.setString(2, alamat);
                        preparedStatement.setString(3, noHP);
                        preparedStatement.setString(4, idAdmin);

                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(editAdminFrame, "Data Admin berhasil diperbarui.");
                            editAdminFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(editAdminFrame, "Gagal memperbarui data Admin.");
                        }
                        preparedStatement.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(editAdminFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        editAdminFrame.add(labelIDAdmin);
        editAdminFrame.add(labelNama);
        editAdminFrame.add(labelAlamat);
        editAdminFrame.add(labelNoHP);
        editAdminFrame.add(idAdminField);
        editAdminFrame.add(namaField);
        editAdminFrame.add(alamatField);
        editAdminFrame.add(noHPField);
        editAdminFrame.add(simpanButton);
        editAdminFrame.add(backgroundLabel);

        editAdminFrame.setSize(400, 250);
        editAdminFrame.setLayout(null);
        editAdminFrame.setVisible(true);
        editAdminFrame.setLocationRelativeTo(null);
    }
}
