import frame.karyawanViewFrame;
import helpers.Koneksi;

public class Main {
    public static void main(String[] args) {
        Koneksi.getConnection();
        karyawanViewFrame viewFrame = new karyawanViewFrame();
        viewFrame.setVisible(true);
    }
}
