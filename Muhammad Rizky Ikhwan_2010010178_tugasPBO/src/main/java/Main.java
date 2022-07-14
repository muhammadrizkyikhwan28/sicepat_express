import frame.penjualanViewFrame;
import helpers.Koneksi;

public class Main {
    public static void main(String[] args) {
        Koneksi.getConnection();
        penjualanViewFrame viewFrame = new penjualanViewFrame();
        viewFrame.setVisible(true);
    }
}
