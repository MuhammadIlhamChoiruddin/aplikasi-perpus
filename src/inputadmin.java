import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class inputadmin {
    public static void input_admin() {
        JFrame inputAdminFrame = new JFrame("Input Admin");
        ImageIcon backgroundImage = new ImageIcon("src/img/cream.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 600, 350);

        JLabel labelidadmin, labelnama, labelalamat, labelnohp;
        labelidadmin = new JLabel("Id_Admin:");
        labelidadmin.setBounds(30, 15, 100, 30);

        labelnama = new JLabel("Nama:");
        labelnama.setBounds(30, 55, 100, 30);

        labelalamat = new JLabel("Alamat:");
        labelalamat.setBounds(30, 95, 100, 30);

        labelnohp = new JLabel("No Hp:");
        labelnohp.setBounds(30, 135, 100, 30);

        JTextField idadminField, namaField, alamatField, nohpField;
        idadminField = new JTextField();
        idadminField.setBounds(130, 15, 200, 30);

        namaField = new JTextField();
        namaField.setBounds(130, 55, 200, 30);

        alamatField = new JTextField();
        alamatField.setBounds(130, 95, 200, 30);

        nohpField = new JTextField();
        nohpField.setBounds(130, 135, 200, 30);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(130, 175, 100, 25);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idadmin = idadminField.getText();
                String nama = namaField.getText();
                String alamat = alamatField.getText();
                String nohp = nohpField.getText();

                Connection connection = Koneksi.connect();
                if (connection != null) {
                    try {
                        Statement stmt = connection.createStatement();
                        String insertQuery = "INSERT INTO admin (idadmin , nama, alamat, nohp) VALUES ('" + idadmin
                                + "', '" + nama + "', '" + alamat + "', '" + nohp + "')";
                        int rowsAffected = stmt.executeUpdate(insertQuery);
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(inputAdminFrame, "Data Admin berhasil ditambahkan.");
                        } else {
                            JOptionPane.showMessageDialog(inputAdminFrame, "Gagal menambahkan data Admin.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(inputAdminFrame, "Terjadi kesalahan: " + ex.getMessage());
                    }
                }
            }
        });

        inputAdminFrame.add(labelidadmin);
        inputAdminFrame.add(idadminField);
        inputAdminFrame.add(labelnama);
        inputAdminFrame.add(namaField);
        inputAdminFrame.add(labelalamat);
        inputAdminFrame.add(alamatField);
        inputAdminFrame.add(labelnohp);
        inputAdminFrame.add(nohpField);
        inputAdminFrame.add(submitButton);
        inputAdminFrame.add(backgroundLabel);

        inputAdminFrame.setSize(400, 250);
        inputAdminFrame.setLayout(null);
        inputAdminFrame.setVisible(true);
        inputAdminFrame.setLocationRelativeTo(null);
    }
}
