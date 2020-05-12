package com.xiazidong.hefei;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiazidong
 * @Date : 2020/5/12 20:28
 * <div style="width:200px;height:200px;background:red;"></div>
 */
public class QrcodeUtil {
    //设置两个常量用来标注颜色
    private static final int BLACK= Color.BLACK.getRGB();
    private static final int WHITE= Color.WHITE.getRGB();

    /**
     * 获取二维码图片
     *
     * @param width   宽度
     * @param height  高度
     * @param type    图片类型
     * @param content 二维码携带的内容
     * @param path    存放路径
     *                <p>
     *                集合，IO 文件操作，图片，常量
     */
    public static void getCode(int width, int height, String type, String content, String path) {
        //1.设置二维码信息(纠错等级，留白)
        Map map = new HashMap<>();
        //设置字符集
        map.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //留白
        map.put(EncodeHintType.MARGIN, 2);
        //设置容错率L(7%)M(15%)Q(25%)H(30%)
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        //二维码图片输出流
        MultiFormatWriter mu = new MultiFormatWriter();
        //二维矩阵类即二维数组
        try {
            BitMatrix bit = mu.encode(content, BarcodeFormat.QR_CODE, width, height, map);

            //画图 宽 高 颜色（选的黑色）
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            //双层for
            for(int i=0;i<width;i++){
                for (int j=0;j<height;j++){
                    //事先定义的二维码，取得这一点的值是true要划黑色，false要画白色
                     int rgb=bit.get(i,j)?BLACK:WHITE;
                    image.setRGB(i,j,rgb);
                }
            }

            File file=new File(path);
            //将图片写入到File文件中
             boolean flag = ImageIO.write(image, type, file);
             if (!flag){
                 System.out.println("二维码生成失败");
             }
            System.out.println("二维码生成成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        getCode(400,400,"png","我是小川妈妈","C:\\Users\\Administrator\\Desktop\\1.png");
    }
}
