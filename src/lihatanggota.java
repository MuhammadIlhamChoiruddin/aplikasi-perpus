import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class lihatanggota {

    public static void tampilkan_data_Anggota() {
        JFrame dataAnggotaFrame = new JFrame("Data Anggota");
        ImageIcon backgroundImage = new ImageIcon("src/img/cream.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 700, 350);

        // Tabel untuk menampilkan data anggota
        DefaultTableModel model = new DefaultTableModel();
        JTable dataTable = new JTable(model);
        model.addColumn("Id_Anggota");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("No_Hp");

        // Scroll pane agar bisa di-scroll jika data banyak
        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBounds(30, 30, 600, 200);

        // Tombol Refresh
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(30, 250, 80, 30);
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Mengambil data anggota terbaru dan menampilkannya di tabel
                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        Statement stmt = connection.createStatement();
                        String selectQuery = "SELECT * FROM anggota";
                        ResultSet rs = stmt.executeQuery(selectQuery);

                        // Clear existing rows in the table
                        model.setRowCount(0);

                        while (((ResultSet) rs).next()) {
                            model.addRow(new Object[]{
                                    rs.getString("idanggota"),
                                    rs.getString("nama"),
                                    rs.getString("alamat"),
                                    rs.getString("nohp")
                            });
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(dataAnggotaFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        // Tombol Hapus Anggota berdasarkan Id_Anggota
        JTextField idAnggotaField = new JTextField();
        idAnggotaField.setBounds(120, 250, 100, 30);
        JButton deleteButton = new JButton("Hapus Anggota");
        deleteButton.setBounds(230, 250, 120, 30);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Mengambil Id_Anggota yang diinputkan
                String idAnggota = idAnggotaField.getText();

                // Hapus anggota berdasarkan Id_Anggota
                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        Statement stmt = connection.createStatement();
                        String deleteQuery = "DELETE FROM anggota WHERE idanggota = '" + idAnggota + "'";
                        int rowsAffected = stmt.executeUpdate(deleteQuery);

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(dataAnggotaFrame, "Anggota dengan ID " + idAnggota + " berhasil dihapus.");
                            idAnggotaField.setText(""); // Reset field setelah penghapusan
                            refreshButton.doClick(); // Refresh tabel
                        } else {
                            JOptionPane.showMessageDialog(dataAnggotaFrame, "Anggota dengan ID " + idAnggota + " tidak ditemukan.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(dataAnggotaFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        // Tombol Edit Anggota
        JButton editButton = new JButton("Edit Anggota");
        editButton.setBounds(360, 250, 180, 30);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editDataAnggota();
            }
        });

        dataAnggotaFrame.add(scrollPane);
        dataAnggotaFrame.add(refreshButton);
        dataAnggotaFrame.add(idAnggotaField);
        dataAnggotaFrame.add(deleteButton);
        dataAnggotaFrame.add(editButton);
        dataAnggotaFrame.add(backgroundLabel);

        dataAnggotaFrame.setSize(700, 350);
        dataAnggotaFrame.setLayout(null);
        dataAnggotaFrame.setVisible(true);
        dataAnggotaFrame.setLocationRelativeTo (null);
        dataAnggotaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void editDataAnggota() {
        JFrame editAnggotaFrame = new JFrame("Edit Data Anggota");
        ImageIcon backgroundImage = new ImageIcon("src/img/cream.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        JLabel labelIdAnggota, labelNama, labelAlamat, labelNoHP;
        JTextField idAnggotaField, namaField, alamatField, noHPField;
        JButton simpanButton;

        labelIdAnggota = new JLabel("Id Anggota:");
        labelIdAnggota.setBounds(30, 30, 100, 30);

        labelNama = new JLabel("Nama:");
        labelNama.setBounds(30, 70, 100, 30);

        labelAlamat = new JLabel("Alamat:");
        labelAlamat.setBounds(30, 110, 100, 30);

        labelNoHP = new JLabel("No. HP:");
        labelNoHP.setBounds(30, 150, 100, 30);

        idAnggotaField = new JTextField();
        idAnggotaField.setBounds(150, 30, 200, 30);

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
                String idAnggota = idAnggotaField.getText();
                String nama = namaField.getText();
                String alamat = alamatField.getText();
                String noHP = noHPField.getText();

                // Simpan perubahan data anggota ke database
                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        String updateQuery = "UPDATE anggota SET nama=?, alamat=?, nohp=? WHERE idanggota=?";
                        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                        preparedStatement.setString(1, nama);
                        preparedStatement.setString(2, alamat);
                        preparedStatement.setString(3, noHP);
                        preparedStatement.setString(4, idAnggota);

                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(editAnggotaFrame, "Data Anggota berhasil diperbarui.");
                            editAnggotaFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(editAnggotaFrame, "Gagal memperbarui data Anggota.");
                        }
                        preparedStatement.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(editAnggotaFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        editAnggotaFrame.add(labelIdAnggota);
        editAnggotaFrame.add(labelNama);
        editAnggotaFrame.add(labelAlamat);
        editAnggotaFrame.add(labelNoHP);
        editAnggotaFrame.add(idAnggotaField);
        editAnggotaFrame.add(namaField);
        editAnggotaFrame.add(alamatField);
        editAnggotaFrame.add(noHPField);
        editAnggotaFrame.add(simpanButton);
        editAnggotaFrame.add(backgroundLabel);

        editAnggotaFrame.setSize(400, 250);
        editAnggotaFrame.setLayout(null);
        editAnggotaFrame.setVisible(true);
        editAnggotaFrame.setLocationRelativeTo(null);
    }
}
