package com.atguigu.springcloud.util;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author db
 * @date 2020/11/2 - 15:21
 */
public class Decompression {

    public static void main(String[] args) throws Exception {
        unGz("F:\\xxx\\ZX-CBEAM-41.163.129.33-2.0-20200927030000.xml.gz","F:\\xxx");
    }

    //解压.zip文件
    public static void unZip(String sourceFile, String outputDir) throws IOException {
        ZipFile zipFile = null;
        File file = new File(sourceFile);
        try {
            Charset CP866 = Charset.forName("CP866");  //specifying alternative (non UTF-8) charset
            zipFile = new ZipFile(file, CP866);
            createDirectory(outputDir, null);//创建输出目录

            Enumeration<?> enums = zipFile.entries();
            while (enums.hasMoreElements()) {

                ZipEntry entry = (ZipEntry) enums.nextElement();
                System.out.println("解压." + entry.getName());

                if (entry.isDirectory()) {//是目录
                    createDirectory(outputDir, entry.getName());//创建空目录
                } else {//是文件
                    File tmpFile = new File(outputDir + "/" + entry.getName());
                    createDirectory(tmpFile.getParent() + "/", null);//创建输出目录

                    InputStream in = null;
                    OutputStream out = null;
                    try {
                        in = zipFile.getInputStream(entry);
                        out = new FileOutputStream(tmpFile);
                        int length = 0;

                        byte[] b = new byte[2048];
                        while ((length = in.read(b)) != -1) {
                            out.write(b, 0, length);
                        }

                    } catch (IOException ex) {
                        throw ex;
                    } finally {
                        if (in != null)
                            in.close();
                        if (out != null)
                            out.close();
                    }
                }
            }

        } catch (IOException e) {
            throw new IOException("解压缩文件出现异常", e);
        } finally {
            try {
                if (zipFile != null) {
                    zipFile.close();
                }
            } catch (IOException ex) {
                throw new IOException("关闭zipFile出现异常", ex);
            }
        }
    }

    /**
     * 构建目录
     *
     * @param outputDir
     * @param subDir
     */
    public static void createDirectory(String outputDir, String subDir) {
        File file = new File(outputDir);
        if (!(subDir == null || subDir.trim().equals(""))) {//子目录不为空
            file = new File(outputDir + "/" + subDir);
        }
        if (!file.exists()) {
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            file.mkdirs();
        }
    }


    //解压.rar文件
    public static void unRar(String sourceFile, String outputDir) throws Exception {
        Archive archive = null;
        FileOutputStream fos = null;
        File file = new File(sourceFile);
        try {
            archive = new Archive(file);
            FileHeader fh = archive.nextFileHeader();
            int count = 0;
            File destFileName = null;
            while (fh != null) {
                System.out.println((++count) + ") " + fh.getFileNameString());
                String compressFileName = fh.getFileNameString().trim();
                destFileName = new File(outputDir + "/" + compressFileName);
                if (fh.isDirectory()) {
                    if (!destFileName.exists()) {
                        destFileName.mkdirs();
                    }
                    fh = archive.nextFileHeader();
                    continue;
                }
                if (!destFileName.getParentFile().exists()) {
                    destFileName.getParentFile().mkdirs();
                }
                fos = new FileOutputStream(destFileName);
                archive.extractFile(fh, fos);
                fos.close();
                fos = null;
                fh = archive.nextFileHeader();
            }

            archive.close();
            archive = null;
        } catch (Exception e) {
            throw e;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                    fos = null;
                } catch (Exception e) {
                    //ignore
                }
            }
            if (archive != null) {
                try {
                    archive.close();
                    archive = null;
                } catch (Exception e) {
                    //ignore
                }
            }
        }
    }


    //解压.gz文件
    public static void unGz(String sourceFile, String outputDir) {
        String ouputfile = "";
        try {
            //建立gzip压缩文件输入流
            FileInputStream fin = new FileInputStream(sourceFile);
            //建立gzip解压工作流
            GZIPInputStream gzin = new GZIPInputStream(fin);
            //建立解压文件输出流
            File file = new File(sourceFile);
            String fileName = file.getName();
            outputDir = outputDir + "/" + fileName.substring(0, fileName.lastIndexOf('.'));
            FileOutputStream fout = new FileOutputStream(outputDir);

            int num;
            byte[] buf = new byte[1024];

            while ((num = gzin.read(buf, 0, buf.length)) != -1) {
                fout.write(buf, 0, num);
            }

            gzin.close();
            fout.close();
            fin.close();
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
        return;
    }


    //解压.tar.gz文件
    public static void unTarGz(String sourceFile, String outputDir) throws IOException {
        TarInputStream tarIn = null;
        File file = new File(sourceFile);
        try {
            tarIn = new TarInputStream(new GZIPInputStream(
                    new BufferedInputStream(new FileInputStream(file))),
                    1024 * 2);

            createDirectory(outputDir, null);//创建输出目录

            TarEntry entry = null;
            while ((entry = tarIn.getNextEntry()) != null) {

                if (entry.isDirectory()) {//是目录
                    entry.getName();
                    createDirectory(outputDir, entry.getName());//创建空目录
                } else {//是文件
                    File tmpFile = new File(outputDir + "/" + entry.getName());
                    createDirectory(tmpFile.getParent() + "/", null);//创建输出目录
                    OutputStream out = null;
                    try {
                        out = new FileOutputStream(tmpFile);
                        int length = 0;

                        byte[] b = new byte[2048];

                        while ((length = tarIn.read(b)) != -1) {
                            out.write(b, 0, length);
                        }

                    } catch (IOException ex) {
                        throw ex;
                    } finally {

                        if (out != null)
                            out.close();
                    }
                }
            }
        } catch (IOException ex) {
            throw new IOException("解压归档文件出现异常", ex);
        } finally {
            try {
                if (tarIn != null) {
                    tarIn.close();
                }
            } catch (IOException ex) {
                throw new IOException("关闭tarFile出现异常", ex);
            }
        }
    }



}
