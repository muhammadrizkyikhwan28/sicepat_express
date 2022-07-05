package frame;

import helpers.ComboBoxitem;
import helpers.Koneksi;

import javax.swing.*;
import java.sql.*;

public class karyawanInputFrame extends JFrame {
    private JTextField idTextField;
    private JTextField namaTextField;
    private JButton simpanButton;
    private JButton batalButton;
    private JPanel buttonPanel;
    private JPanel mainPanel;
    private JComboBox shiffcb;
    private JComboBox jabatancb;

    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public karyawanInputFrame(){
        simpanButton.addActionListener(e -> {
            String nama = namaTextField.getText();
            ComboBoxitem jabatan = (ComboBoxitem) jabatancb.getSelectedItem();
            ComboBoxitem shiff = (ComboBoxitem) shiffcb.getSelectedItem();
            int id_jabatan = jabatan.getValue();
            int id_shiff = shiff.getValue();
//            String cls = jeniscb.getSelectedItem().toString();
//            String mrk = shiffcb.getSelectedItem().toString();
//              String jabtan = jabatancb.getSelectedItem().toString();
//            int id_jenis_kelamain;
//            int id_shiff;
//            int id_jabatan;
//
//            if(mrk == "Asus"){
//                id_merk = 1;
//            } else if(mrk == "Acer"){
//                id_merk = 2;
//            } else if(mrk == "Lenovo"){
//                id_merk = 3;
//            } else if(mrk == "HP"){
//                id_merk = 4;
//            } else {
//                id_merk = 5;
//            }
//            if(cls == "Ultrabook"){
//                id_class = 1;
//            } else if(cls == "Gaming notebook"){
//                id_class = 2;
//            } else if(cls == "Notebook Mainstream"){
//                id_class = 3;
//            } else {
//                id_class = 4;
//            }
            if(nama.equals("")){
                JOptionPane.showMessageDialog(
                        null,
                        "Lengkapi Nama karyawan",
                        "Validasi data kosong",
                        JOptionPane.WARNING_MESSAGE
                );
                namaTextField.requestFocus();
                return;
            }

            Connection c = Koneksi.getConnection();
            PreparedStatement ps;
            try {
                if (this.id == 0) {
                    String cekSQL = "SELECT * FROM karyawan WHERE nama = ?";
                    ps = c.prepareStatement(cekSQL);
                    ps.setString(1, nama);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()){
                        JOptionPane.showMessageDialog(
                                null,
                                "karyawan Sudah Ada",
                                "Validasi data sama",
                                JOptionPane.WARNING_MESSAGE
                        );
                        return;
                    }
                    String insertSQL = "INSERT INTO karyawan SET nama = ?, id_jabatan = ?, id_shiff = ?";
                    ps = c.prepareStatement(insertSQL);
                    ps.setString(1, nama);
                    ps.setInt(2, id_jabatan);
                    ps.setInt(3, id_shiff);
                    ps.executeUpdate();
                    dispose();
                } else {
                    String cekSQL = "SELECT * FROM karyawan WHERE nama=? AND id!=?";
                    ps = c.prepareStatement(cekSQL);
                    ps.setString(1, nama);
                    ps.setInt(2, id);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()){
                        JOptionPane.showMessageDialog(
                                null,
                                "karyawan Sudah Ada",
                                "Validasi data sama",
                                JOptionPane.WARNING_MESSAGE
                        );
                        return;
                    }

                    String updateSQL = "UPDATE karyawan SET nama=?, id_jabatan=?, id_shiff=? WHERE id=?";
                    ps = c.prepareStatement(updateSQL);
                    ps.setString(1, nama);
                    ps.setInt(2, id_jabatan);
                    ps.setInt(3, id_shiff);
                    ps.setInt(4, id);
                    ps.executeUpdate();
                    dispose();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });
        batalButton.addActionListener(e -> {
            dispose();
        });
        jabatancb();
        shiffcb();
        init();
    }

    public void init() {
        setTitle("Input Data");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
    }

    public void isiKomponen() {
        idTextField.setText(String.valueOf(this.id));

        String findSQL = "SELECT * FROM karyawan WHERE id = ?";

        Connection c = Koneksi.getConnection();
        PreparedStatement ps;
        try {
            ps = c.prepareStatement(findSQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                namaTextField.setText(rs.getString("nama"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void jabatancb() {
        Connection c = Koneksi.getConnection();
        String selectSQL = "SELECT * FROM jabatan ORDER by jabatan";

        java.sql.Statement s = null;
        try {
            s = c.createStatement();
            ResultSet rs = s.executeQuery(selectSQL);
            jabatancb.addItem(new ComboBoxitem(0, "Pilih Jabatan"));
            while (rs.next()){
                jabatancb.addItem(new ComboBoxitem(
                        rs.getInt("id"),
                        rs.getString("jabatan")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void shiffcb() {
        Connection c = Koneksi.getConnection();
        String selectSQL = "SELECT * FROM shiff ORDER by shiff";

        Statement s = null;
        try {
            s = c.createStatement();
            ResultSet rs = s.executeQuery(selectSQL);
            shiffcb.addItem(new ComboBoxitem(0, "Pilih Shiff"));
            while (rs.next()){
                shiffcb.addItem(new ComboBoxitem(
                        rs.getInt("id"),
                        rs.getString("shiff")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
