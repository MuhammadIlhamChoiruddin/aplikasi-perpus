import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class adminmenu {
    public static void admin_menu(){
        JFrame adminFrame = new JFrame("PERPUSTAKAAN");
        ImageIcon backgroundImage = new ImageIcon("src/img/112.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 900, 400);

        ImageIcon inputbookicon = new ImageIcon("src/img/books.png");
        ImageIcon inputadminicon = new ImageIcon("src/img/adminn.png");
        ImageIcon inputmembericon = new ImageIcon("src/img/memberr.png");
        ImageIcon inputborrowicon = new ImageIcon("src/img/borrow.png");
        ImageIcon inputreturnicon = new ImageIcon("src/img/return.png");
        
        JButton inputAdminButton = new JButton("Input Admin",inputadminicon);
        inputAdminButton.setBounds(30, 15, 160, 32);
        inputAdminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputadmin.input_admin();
            }
        });

        JButton inputAnggotaButton = new JButton("Input Anggota",inputmembericon);
        inputAnggotaButton.setBounds(30, 55, 160, 32);
        inputAnggotaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputanggota.input_anggota();
            }
        });

        JButton inputBukuButton = new JButton("Input Buku",inputbookicon);
        inputBukuButton.setBounds(30, 95, 160, 32);
        inputBukuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputbuku.input_buku();
            }
        });

        JButton lihatadminButton = new JButton("Melihat Admin");
        lihatadminButton.setBounds(250, 15,160, 32);
        lihatadminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { lihatadmin.tampilkan_Data_Admin(); }
        });

        JButton melihatAnggotaButton = new JButton("Melihat Anggota");
        melihatAnggotaButton.setBounds(250, 55, 160, 32);
        melihatAnggotaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lihatanggota.tampilkan_data_Anggota();
            }
        });

        JButton melihatBukuButton = new JButton("Melihat Buku");
        melihatBukuButton.setBounds(250, 95, 160, 32);
        melihatBukuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lihatbuku.tampilkan_data_buku();
            }
        });

        JButton peminjamanBukuButton = new JButton("Peminjaman Buku",inputborrowicon);
        peminjamanBukuButton.setBounds(30, 135, 160, 32);
        peminjamanBukuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                peminjamanbuku.peminjaman_buku();
            }
        });

        JButton pengembalianBukuButton = new JButton("Pengembalian Buku",inputreturnicon);
        pengembalianBukuButton.setBounds(30, 175,160, 32);
        pengembalianBukuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pengembalianbuku.pengembalian_buku();
            }
        });

        JButton lihatpeminjamanButton = new JButton("Lihat Peminjaman");
        lihatpeminjamanButton.setBounds(250, 135,160, 32);
        lihatpeminjamanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {lihatpeminjaman.tampilkan_Peminjaman_Buku();}
        });

        JButton lihatpengembalianButton = new JButton("Lihat Pengembalian");
        lihatpengembalianButton.setBounds(250, 175,160, 32);
        lihatpengembalianButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {lihatpengembalian.tampilkan_Data_Pengembalian();}
        });

        adminFrame.add(inputAdminButton);
        adminFrame.add(inputAnggotaButton);
        adminFrame.add(inputBukuButton);
        adminFrame.add(melihatBukuButton);
        adminFrame.add(melihatAnggotaButton);
        adminFrame.add(peminjamanBukuButton);
        adminFrame.add(pengembalianBukuButton);
        adminFrame.add(lihatadminButton);
        adminFrame.add(lihatpeminjamanButton);
        adminFrame.add(lihatpengembalianButton);
        adminFrame.add(backgroundLabel);

        adminFrame.setSize(900,400);
        adminFrame.setLayout(null);
        adminFrame.setVisible(true);
        adminFrame.setLocationRelativeTo(null);
    }
}
