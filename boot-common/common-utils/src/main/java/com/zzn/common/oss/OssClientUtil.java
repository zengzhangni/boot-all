package com.zzn.common.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Author: pkl
 * @Date: 2019/3/28 11:46
 */
@Component("ossClientUtil")
public class OssClientUtil {

    public static final Logger logger = LoggerFactory.getLogger(OssClientUtil.class);
    @Value(value = "${aliyun.oss.endpoint}")
    private String endpoint;
    @Value(value = "${aliyun.oss.network}")
    private String network;
    @Value(value = "${aliyun.oss.accessKeyId}")
    private String accessKeyId;
    @Value(value = "${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value(value = "${aliyun.oss.bucketName}")
    private String bucketName;
    private String filedir = this.getCurrentYearMoth();

    private OSSClient ossClient;

//    @Autowired(required = false)
//    BaseAttachmentFeign api;

    @PostConstruct
    public void ossClientUtil() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 初始化
     */
    public void init() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 销毁
     */
    @PreDestroy
    public void destory() {
        ossClient.shutdown();
    }

    public String uploadImg2Oss(MultipartFile file){
        //获得原始文件名
        String originalFilename = file.getOriginalFilename();
        System.out.println("原始文件名是："+originalFilename);
        Random random = new Random();

        try {
            if(file.getInputStream() instanceof FileInputStream){
                System.out.println("可以用强转FileInputStream");

                FileInputStream is = (FileInputStream)file.getInputStream();
                FileType fileType = getType(is);
                if(fileType == null){
                    return "暂不支持该类型";
                }
                System.out.println("文件类型是："+fileType);
                //生成新文件名，并拼接了 后缀
                String name = random.nextInt(10000) + System.currentTimeMillis() +"."+fileType;
                InputStream inputStream = file.getInputStream();
                this.uploadFile2OSS(inputStream, name);
                return name;
            }else {
                return "图片上传失败";
            }

        } catch (IOException e) {
            return "图片上传失败";
        }
    }

    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @return
     */
    public String getImgUrl(String fileUrl) {
        System.out.println(fileUrl);
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            return this.getUrl(this.filedir + split[split.length - 1]);
        }
        return null;
    }

    /**
     * 上传到OSS服务器 如果同名文件会覆盖服务器上的
     *
     * @param instream 文件流
     * @param fileName 文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public String uploadFile2OSS(InputStream instream, String fileName) {
        String ret = "";
        try {
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            // 上传文件
            PutObjectResult putResult = ossClient.putObject(bucketName, filedir + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param filenameExtension 文件后缀
     * @return String
     */
    public static String getcontentType(String filenameExtension) {
        String bmp = "bmp";
        if (bmp.equalsIgnoreCase(filenameExtension)) {
            return "image/bmp";
        }
        String gif = "gif";
        if (gif.equalsIgnoreCase(filenameExtension)) {
            return "image/gif";
        }
        String jpeg = "jpeg";
        String jpg = "jpg";
        if (jpeg.equalsIgnoreCase(filenameExtension) || jpg.equalsIgnoreCase(filenameExtension)
                || "png".equalsIgnoreCase(filenameExtension)) {
            return "image/jpeg";
        }
        String html = "html";
        if (html.equalsIgnoreCase(filenameExtension)) {
            return "text/html";
        }
        String txt = "txt";
        if (txt.equalsIgnoreCase(filenameExtension)) {
            return "text/plain";
        }
        String vsd = "vsd";
        if (vsd.equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.visio";
        }
        String pptx = "pptx";
        String ppt = "ppt";
        if (pptx.equalsIgnoreCase(filenameExtension) || ppt.equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        String docx = "docx";
        String doc = "doc";
        if (docx.equalsIgnoreCase(filenameExtension) || doc.equalsIgnoreCase(filenameExtension)) {
            return "application/msword";
        }
        String xml = "xml";
        if (xml.equalsIgnoreCase(filenameExtension)) {
            return "text/xml";
        }
        String apk = "apk";
        if (apk.equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.android.package-archive";
        }
        return "image/jpeg";
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10

        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 100);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return url.toString().replace(endpoint,network);
        }
        return null;
    }

    public String getCurrentYearMoth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 将文件头转换成16进制字符串
     *
     * @param src（原生byte）
     * @return 16进制字符串
     */
    private static String bytesToHexString(byte[] src){

        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 获取文件类型
     *
     * @param is
     * @return 文件类型
     */
    public static FileType getType(FileInputStream is) throws IOException {
        byte[] b = new byte[28];
        is.read(b, 0, b.length);
        String fileHead = bytesToHexString(b).toUpperCase();
        System.out.println("文件头："+fileHead);
        FileType[] fileTypes = FileType.values();
        for (FileType type : fileTypes) {
            if (fileHead.startsWith(type.getValue())) {
                return  type;
            }
        }

        return null;
    }
}
