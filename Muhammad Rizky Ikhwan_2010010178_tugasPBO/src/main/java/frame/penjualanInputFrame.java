package frame;

import helpers.ComboBoxitem;
import helpers.Koneksi;

import javax.swing.*;
import java.sql.*;

public class penjualanInputFrame extends JFrame {
    private JTextField idTextField;
    private JTextField namaTextField;
    private JButton simpanButton;
    private JButton batalButton;
    private JPanel buttonPanel;
    private JPanel mainPanel;
    private JComboBox barangcb;
    private JComboBox kasircb;

    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public penjualanInputFrame(){
        simpanButton.addActionListener(e -> {
            String nama_pelanggan = namaTextField.getText();
            ComboBoxitem kasir = (ComboBoxitem) kasircb.getSelectedItem();
            ComboBoxitem barang = (ComboBoxitem) barangcb.getSelectedItem();
            int id_kasir = kasir.getValue();
            int id_barang = barang.getValue();

            if(nama_pelanggan.equals("")){
                JOptionPane.showMessageDialog(
                        null,
                        "Lengkapi Nama Pelanggan",
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
                    String cekSQL = "SELECT * FROM penjualan WHERE nama_pelanggan = ?";
                    ps = c.prepareStatement(cekSQL);
                    ps.setString(1, nama_pelanggan);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()){
                        JOptionPane.showMessageDialog(
                                null,
                                "Penjualan Sudah Ada",
                                "Validasi data sama",
                                JOptionPane.WARNING_MESSAGE
                        );
                        return;
                    }
                    String insertSQL = "INSERT INTO penjualan SET nama_pelanggan = ?, id_kasir = ?, id_barang = ?";
                    ps = c.prepareStatement(insertSQL);
                    ps.setString(1, nama_pelanggan);
                    ps.setInt(2, id_kasir);
                    ps.setInt(3, id_barang);
                    ps.executeUpdate();
                    dispose();
                } else {
                    String cekSQL = "SELECT * FROM penjualan WHERE nama_pelanggan=? AND id!=?";
                    ps = c.prepareStatement(cekSQL);
                    ps.setString(1, nama_pelanggan);
                    ps.setInt(2, id);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()){
                        JOptionPane.showMessageDialog(
                                null,
                                "penjualan Sudah Ada",
                                "Validasi data sama",
                                JOptionPane.WARNING_MESSAGE
                        );
                        return;
                    }

                    String updateSQL = "UPDATE penjualan SET nama_pelanggan=?, id_kasir=?, id_barang=? WHERE id=?";
                    ps = c.prepareStatement(updateSQL);
                    ps.setString(1, nama_pelanggan);
                    ps.setInt(2, id_kasir);
                    ps.setInt(3, id_barang);
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
        kasircb();
        barangcb();
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

        String findSQL = "SELECT * FROM penjualan WHERE id = ?";

        Connection c = Koneksi.getConnection();
        PreparedStatement ps;
        try {
            ps = c.prepareStatement(findSQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                namaTextField.setText(rs.getString("nama_pelanggan"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void kasircb() {
        Connection c = Koneksi.getConnection();
        String selectSQL = "SELECT * FROM kasir ORDER by nama_kasir";

        java.sql.Statement s = null;
        try {
            s = c.createStatement();
            ResultSet rs = s.executeQuery(selectSQL);
            kasircb.addItem(new ComboBoxitem(0, "Pilih Kasir"));
            while (rs.next()){
                kasircb.addItem(new ComboBoxitem(
                        rs.getInt("id"),
                        rs.getString("nama_kasir")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void barangcb() {
        Connection c = Koneksi.getConnection();
        String selectSQL = "SELECT * FROM barang ORDER by nama_barang";

        Statement s = null;
        try {
            s = c.createStatement();
            ResultSet rs = s.executeQuery(selectSQL);
            barangcb.addItem(new ComboBoxitem(0, "Pilih Barang"));
            while (rs.next()){
                barangcb.addItem(new ComboBoxitem(
                        rs.getInt("id"),
                        rs.getString("nama_barang")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
