package com.xiazidong.hefei;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiazidong
 * @Date : 2020/5/12 21:37
 */
public class ZXingCodeUtil {
    /**
     * 二维码图片加入Logo
     *
     * @param bim 图片流
     * @param logoPic Logo图片物理位置
     * @param logoConfig Logo图片设置參数
     * @throws Exception 异常上抛
     */
    private void addLogo_QRCode(BufferedImage bim, File logoPic, LogoConfig logoConfig) throws Exception {
        try {
            // 对象流传输
            BufferedImage image = bim;
            Graphics2D g = image.createGraphics();

            // 读取Logo图片
            BufferedImage logo = ImageIO.read(logoPic);

            // 设置logo的大小,本人设置为二维码图片的20%,由于过大会盖掉二维码
            int widthLogo = logo.getWidth(null) > image.getWidth() * 2 / 10 ? (image.getWidth() * 2 / 10) : logo.getWidth(null), heightLogo = logo
                    .getHeight(null) > image.getHeight() * 2 / 10 ?
                    (image.getHeight() * 2 / 10) : logo.getWidth(null);

            // 计算图片放置位置
            // logo放在中心
            int x = (image.getWidth() - widthLogo) / 2;
            int y = (image.getHeight() - heightLogo) / 2;
            // 開始绘制图片
            g.drawImage(logo, x, y, widthLogo, heightLogo, null);
            g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
            g.setStroke(new BasicStroke(logoConfig.getBorder()));
            g.setColor(logoConfig.getBorderColor());
            g.drawRect(x, y, widthLogo, heightLogo);

            g.dispose();
            logo.flush();
            image.flush();

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 二维码的解析
     *
     * @param image  图片文件流
     * @return 解析后的Result结果集
     * @throws Exception 错误异常上抛
     */
    @SuppressWarnings("unchecked")
    public Result parseQR_CODEImage(BufferedImage image) throws Exception {
        // 设置解析
        Result result = null;
        try {
            MultiFormatReader formatReader = new MultiFormatReader();

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

            @SuppressWarnings("rawtypes")
            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            result = formatReader.decode(binaryBitmap, hints);

            System.out.println("resultFormat = " + result.getBarcodeFormat());
            System.out.println("resultText = " + result.getText());
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    /**
     * 生成二维码bufferedImage图片
     * @param zxingconfig 二维码配置信息
     * @return 生成后的 BufferedImage
     * @throws Exception 异常上抛
     */
    public BufferedImage getQR_CODEBufferedImage(ZXingConfig zxingconfig) throws Exception {
        // Google配置文件
        MultiFormatWriter multiFormatWriter = null;
        BitMatrix bm = null;
        BufferedImage image = null;
        try {
            multiFormatWriter = new MultiFormatWriter();

            // 參数顺序分别为：编码内容。编码类型，生成图片宽度，生成图片高度，设置參数
            bm = multiFormatWriter.encode(zxingconfig.getContent(), zxingconfig.getBarcodeformat(), zxingconfig.getWidth(), zxingconfig.getHeight(),
                    zxingconfig.getHints());

            int w = bm.getWidth();
            int h = bm.getHeight();
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            // 開始利用二维码数据创建Bitmap图片，分别设为黑白两色
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    image.setRGB(x, y, bm.get(x, y) ?

                            Color.BLACK.getRGB() : Color.WHITE.getRGB());
                }
            }

            // 是否设置Logo图片
            if (zxingconfig.isLogoFlg()) {
                this.addLogo_QRCode(image, new File(zxingconfig.getLogoPath()), zxingconfig.getLogoConfig());
            }
        } catch (WriterException e) {
            throw e;
        }
        return image;
    }

    /**
     * 设置二维码的格式參数
     *
     * @return
     */
    public Map<EncodeHintType, Object> getDecodeHintType() {
        // 用于设置QR二维码參数
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // 设置QR二维码的纠错级别（H为最高级别）详细级别信息
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置编码方式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.MAX_SIZE, 350);
        hints.put(EncodeHintType.MIN_SIZE, 100);

        return hints;
    }
    /***
     * 二维码嵌套背景图的方法
     *@param bigPath 背景图 - 可传网络地址
     *@param smallPath 二维码地址 - 可传网络地址
     *@param newFilePath 生成新图片的地址
     * @param  x 二维码x坐标
     *  @param  y 二维码y坐标
     * */
    public static void mergeImage(String bigPath, String smallPath, String newFilePath,String x, String y) throws IOException {

        try {
            BufferedImage small;
            BufferedImage big;
            if (bigPath.contains("http://") || bigPath.contains("https://")) {
                URL url = new URL(bigPath);
                big = ImageIO.read(url);
            } else {
                big = ImageIO.read(new File(bigPath));
            }


            if (smallPath.contains("http://") || smallPath.contains("https://")) {

                URL url = new URL(smallPath);
                small = ImageIO.read(url);
            } else {
                small = ImageIO.read(new File(smallPath));
            }

            Graphics2D g = big.createGraphics();

            float fx = Float.parseFloat(x);
            float fy = Float.parseFloat(y);
            int x_i = (int) fx;
            int y_i = (int) fy;
            g.drawImage(small, x_i, y_i, small.getWidth(), small.getHeight(), null);
            g.dispose();
            ImageIO.write(big, "png", new File(newFilePath));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 这个工具类 ZXingCodeUtil 是提供二维码图片生成的工具 首先使用的时候须要实例化
     * ZXingCodeUtil 然后实例化參数 ZXingConfig 和 LogoConfig 通过以下的演示能够详细看參数是依照什么循序进行设置 最后调用
     * ZXingCodeUtil 中方法 getQR_CODEBufferedImage来生成二维码
     */
    public static void main(String[] args) throws WriterException {
        String content = "http://www.baidu.com";
        System.out.println("inputParam:" + content);
        try {
            // 生成二维码
            File file = new File("D:\\Michael_QRCode.png");
            ZXingCodeUtil zp = new ZXingCodeUtil(); // 实例化二维码工具
            ZXingConfig zxingconfig = new ZXingConfig();    // 实例化二维码配置參数
            zxingconfig.setHints(zp.getDecodeHintType());   // 设置二维码的格式參数
            zxingconfig.setContent(content);// 设置二维码生成内容
            zxingconfig.setLogoPath("D:\\毕业证书.png"); // 设置Logo图片
            zxingconfig.setLogoConfig(new LogoConfig());    // Logo图片參数设置
            zxingconfig.setLogoFlg(true);   // 设置生成Logo图片
            BufferedImage bim = zp.getQR_CODEBufferedImage(zxingconfig);// 生成二维码
            ImageIO.write(bim, "png", file);    // 图片写出
            Thread.sleep(500);  // 缓冲

            zp.parseQR_CODEImage(bim);  // 解析调用


            mergeImage("D:\\baobao.jpg", "D:\\Michael_QRCode.png", "D:\\beijing.png","63", "163");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
