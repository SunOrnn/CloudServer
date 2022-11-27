package com.pay.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * 二维码
 * 爪哇笔记：https://blog.52itstyle.vip
 * @author 小柒2012
 */
public class ZxingUtils {

    static final String FORMAT = "png";

    static final int height = 256;

    /**
     * 生成二维码
     * @param qrCode
     * @param imgPath
     */
    public static void createQRCodeImage(String qrCode,String imgPath){
        HashMap hashMap = new HashMap();
        hashMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hashMap.put(EncodeHintType.MARGIN, 2);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(qrCode, BarcodeFormat.QR_CODE, height, height, hashMap);
            MatrixToImageWriter.writeToPath(bitMatrix, FORMAT, new File(imgPath).toPath());
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
