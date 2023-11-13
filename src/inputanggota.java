import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class inputanggota {
    public static void input_anggota() {
        JFrame inputAnggotaFrame = new JFrame("Input Anggota");
        ImageIcon backgroundImage = new ImageIcon("src/img/cream.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 600, 350);

        JLabel labelIdAnggota, labelNama, labelAlamat, labelNoHp;
        labelIdAnggota = new JLabel("ID Anggota:");
        labelIdAnggota.setBounds(30, 15, 100, 30);

        labelNama = new JLabel("Nama:");
        labelNama.setBounds(30, 55, 100, 30);

        labelAlamat = new JLabel("Alamat:");
        labelAlamat.setBounds(30, 95, 100, 30);

        labelNoHp = new JLabel("No Hp:");
        labelNoHp.setBounds(30, 135, 100, 30);

        JTextField idAnggotaField, namaField, alamatField, noHpField;
        idAnggotaField = new JTextField();
        idAnggotaField.setBounds(130, 15, 200, 30);

        namaField = new JTextField();
        namaField.setBounds(130, 55, 200, 30);

        alamatField = new JTextField();
        alamatField.setBounds(130, 95, 200, 30);

        noHpField = new JTextField();
        noHpField.setBounds(130, 135, 200, 30);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(130, 175, 100, 25);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idAnggota = idAnggotaField.getText();
                String nama = namaField.getText();
                String alamat = alamatField.getText();
                String noHp = noHpField.getText();

                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        Statement stmt = connection.createStatement();
                        String insertQuery = "INSERT INTO anggota (idanggota, nama, alamat, nohp) VALUES ('" + idAnggota
                                + "', '" + nama + "', '" + alamat + "', '" + noHp + "')";
                        int rowsAffected = stmt.executeUpdate(insertQuery);
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(inputAnggotaFrame, "Data Anggota berhasil ditambahkan.");
                        } else {
                            JOptionPane.showMessageDialog(inputAnggotaFrame, "Gagal menambahkan data Anggota.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(inputAnggotaFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        inputAnggotaFrame.add(labelIdAnggota);
        inputAnggotaFrame.add(idAnggotaField);
        inputAnggotaFrame.add(labelNama);
        inputAnggotaFrame.add(namaField);
        inputAnggotaFrame.add(labelAlamat);
        inputAnggotaFrame.add(alamatField);
        inputAnggotaFrame.add(labelNoHp);
        inputAnggotaFrame.add(noHpField);
        inputAnggotaFrame.add(submitButton);
        inputAnggotaFrame.add(backgroundLabel);

        inputAnggotaFrame.setSize(400, 250);
        inputAnggotaFrame.setLayout(null);
        inputAnggotaFrame.setVisible(true);
        inputAnggotaFrame.setLocationRelativeTo(null);

    };
}
