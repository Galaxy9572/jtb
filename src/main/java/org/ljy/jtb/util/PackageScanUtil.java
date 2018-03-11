package org.ljy.jtb.util;

import org.ljy.jtb.annotations.Table;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author ljy
 */
public class PackageScanUtil {

    private static final String FILE = "file";

    private static final String JAR = "jar";

    private static final String SUFFIX_CLASS = ".class";

    private static final String SLASH = "/";

    private static final String DOT = ".";

    private static final String ENCODING = "UTF-8";

    private static ClassLoader classLoader;

    static {
        classLoader = Thread.currentThread().getContextClassLoader();
    }

    public static Set<Class<?>> scanClasses(String pack, Class<Table> annotation) {
        LogUtil.debug("Start Scanning Classes...");
        Set<Class<?>> classes = new HashSet<>();
        String packageDirName = pack.replace(DOT, SLASH);
        Enumeration<URL> dirs;
        try {
            dirs = classLoader.getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if (FILE.equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), ENCODING);
                    getClassesInPackage(pack, filePath, classes,annotation);
                } else if (JAR.equals(protocol)) {
                    JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        if (DOT.equals(name.charAt(0) + "")) {
                            name = name.substring(1);
                        }
                        if (name.startsWith(packageDirName)) {
                            int index = name.lastIndexOf(SLASH);
                            if (index != -1) {
                                pack = name.substring(0, index).replace(SLASH, DOT);
                            }
                            if (name.endsWith(SUFFIX_CLASS) && !entry.isDirectory()) {
                                String className = name.substring(pack.length() + 1, name.length() - 6);
                                Class<?> aClass = Class.forName(pack + DOT + className);
                                addCandidateClasses(classes, aClass, annotation != null);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.error("", e);
        }
        LogUtil.debug("Classes Scan Finished, Found " + classes.size() + " class(es).");
        return classes;
    }

    private static void addCandidateClasses(Set<Class<?>> classes, Class<?> aClass, boolean b) {
        if (b) {
            Annotation[] annotations = aClass.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(Table.class)) {
                    classes.add(aClass);
                }
            }
        } else {
            classes.add(aClass);
        }
    }

    private static void getClassesInPackage(String packageName, String packagePath,
                                            Set<Class<?>> classes, Class<?> annotation) {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            LogUtil.warn("There Is No File Under Package: " + packageName);
            return;
        }
        File[] dirFiles = dir.listFiles(file -> (file.isDirectory()) || (file.getName().endsWith(SUFFIX_CLASS)));
        if (dirFiles != null) {
            for (File file : dirFiles) {
                if (file.isDirectory()) {
                    getClassesInPackage(packageName + DOT + file.getName(), file.getAbsolutePath(),
                            classes,annotation);
                } else {
                    String className = file.getName().substring(0, file.getName().length() - 6);
                    try {
                        Class<?> aClass = classLoader.loadClass(packageName + DOT + className);
                        addCandidateClasses(classes, aClass, annotation != null);
                    } catch (ClassNotFoundException e) {
                        LogUtil.error("Can Not Find Class File", e);
                    }
                }
            }
        }
    }
}
