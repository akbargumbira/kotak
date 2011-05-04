package com.kotak.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class KFileSystem {

    /**
     * Move folder or file
     * @param target Folder or file will be moved
     * @param sourcePath Old location of target
     * @param destPath New location of target
     * @return True if move success elsewhere false
     */
    public static boolean move(String target, String sourcePath, String destPath) throws IOException, SecurityException, Exception {
        File source = new File(sourcePath + "/" + target);
        File dest = new File(destPath + "/" + target);

        if (!source.exists()) {
            throw new Exception("Source is not exist");
        }

        return move(source, dest);
    }

    /**
     * Move folder or file
     * @param source Folder or file will be moved
     * @param dest Must be a folder. Source will be moved to here.
     * @return true if move success elsewhere false
     */
    public static boolean move(File source, File dest) throws IOException {
        // Copy
        copyDirectory(source, dest);

        // Delete
        return delete(source);
    }

    /**
     * Copy directory
     * @param sourceDir From
     * @param destDir To
     * @throws IOException
     */
    public static void copyDirectory(File sourceDir, File destDir) throws IOException {
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        File[] children = sourceDir.listFiles();
        for (File sourceChild : children) {
            String name = sourceChild.getName();
            File destChild = new File(destDir, name);
            if (sourceChild.isDirectory()) {
                copyDirectory(sourceChild, destChild);
            } else {
                copyFile(sourceChild, destChild);
            }
        }
    }

    /**
     * Copy File
     * @param source From
     * @param dest  To
     * @throws IOException
     */
    public static void copyFile(File source, File dest) throws IOException {
        if (!dest.exists()) {
            dest.createNewFile();
        }
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(source);
            out = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } finally {
            in.close();
            out.close();
        }
    }

    /**
     * Delete file or folder
     * @param target File or folder
     * @return
     * @throws SecurityException
     */
    public static boolean delete(File target) throws SecurityException {
        if (target.isDirectory()) {
            File[] childFiles = target.listFiles();
            for (File child : childFiles) {
                delete(child);
            }
        }
        
        return target.delete();
    }
    
    public static void save(String path, byte[] content) throws FileNotFoundException, IOException {
        File file = new File(path);
        
        if (file.exists()) {
            FileOutputStream fos = new FileOutputStream(file, false);
            fos.write(content);
            fos.close();
        }
    }

    public static void main(String[] args) {
        try {
            move("tes", "D:/Rezan/Informatika/Kuliah/Semester 6/2010-2011/Pemograman Internet/Tugas/", "C:/Users/user/Desktop/");
        } catch (IOException ex) {
            Logger.getLogger(KFileSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(KFileSystem.class.getName()).log(Level.ALL, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(KFileSystem.class.getName()).log(Level.ALL, null, ex);
        }
    }
}