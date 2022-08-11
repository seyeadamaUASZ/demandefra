package sn.gainde2000.pi.core.tools;

import java.awt.image.BufferedImage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.UPCAWriter;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class Qrcode {

    public static BufferedImage generateUPCABarcodeImage(String barcodeText) throws Exception {
        UPCAWriter barcodeWriter = new UPCAWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.UPC_A, 300, 150);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public static BufferedImage generateEAN13BarcodeImage(String barcodeText) throws Exception {
        EAN13Writer barcodeWriter = new EAN13Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.EAN_13, 300, 150);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public static BufferedImage generateCode128BarcodeImage(String barcodeText) throws Exception {
        Code128Writer barcodeWriter = new Code128Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.CODE_128, 300, 150);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public static BufferedImage generatePDF417BarcodeImage(String barcodeText) throws Exception {
        PDF417Writer barcodeWriter = new PDF417Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.PDF_417, 700, 700);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public static BufferedImage generateQRCodeImage(String barcodeText, long couleur, byte[] imge) throws Exception {
        Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();

        int color = (int) Long.parseLong(Long.toHexString(couleur), 16);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 400, 400, hints);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        MatrixToImageConfig conf = new MatrixToImageConfig(color, -1);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix, conf);
        InputStream is = new ByteArrayInputStream(imge);
        BufferedImage logo = ImageIO.read(is);
        BufferedImage scaled = getScaledInstance(logo, 80, 80, RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
        int deltaHeight = image.getHeight() - scaled.getHeight();
        int deltaWidth = image.getWidth() - scaled.getWidth();
        BufferedImage combined = new BufferedImage(bitMatrix.getHeight(), bitMatrix.getWidth(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) combined.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g.drawImage(scaled, (int) Math.round(deltaWidth / 2), (int) Math.round(deltaHeight / 2), null);
        ImageIO.write(combined, "png", os);
        return combined;
    }

    public static BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight, Object hint, boolean higherQuality) {
        int type
                = (img.getTransparency() == Transparency.OPAQUE)
                ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage) img;
        int w, h;
        if (higherQuality) {
            w = img.getWidth();
            h = img.getHeight();
        } else {
            w = targetWidth;
            h = targetHeight;
        }

        do {
            if (higherQuality && w > targetWidth) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }

            if (higherQuality && h > targetHeight) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }

            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while (w != targetWidth || h != targetHeight);

        return ret;
    }

    public static BufferedImage generateQRCodeImage1(String barcodeText) throws Exception {
        Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 400, 400, hints);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        MatrixToImageConfig conf = new MatrixToImageConfig(0xFF000000, -1);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix, conf);

        return image;
    }

    public static BufferedImage generateQRCodeImageFichier(String barcodeText, String logo1) throws Exception {
        Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 400, 400, hints);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        MatrixToImageConfig conf = new MatrixToImageConfig(0xFF40BAD0, -1);

        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix, conf);

        BufferedImage logo = ImageIO.read(new File(logo1));
        BufferedImage scaled = getScaledInstance(logo, 100, 100, RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
        //Calculate the delta height and width
        int deltaHeight = image.getHeight() - scaled.getHeight();
        int deltaWidth = image.getWidth() - scaled.getWidth();

        // Initialize combined image
        BufferedImage combined = new BufferedImage(bitMatrix.getHeight(), bitMatrix.getWidth(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) combined.getGraphics();

        // Write QR code to new image at position 0/0
        g.drawImage(image, 0, 0, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        g.drawImage(scaled, (int) Math.round(deltaWidth / 2), (int) Math.round(deltaHeight / 2), null);

        ImageIO.write(combined, "png", os);
        return combined;
    }

}
