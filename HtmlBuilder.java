import java.io.PrintWriter;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class HtmlBuilder {

    private HtmlBuilder() {
        //  Nem példányosítható
    }

    public final static List<String> supportedExtensions = List.of("jpg", "png", "jpeg", "gif", "bmp");

    private static PrintWriter writer;

    public static void createHtmlFiles(File dir) {
        createHtmlFiles(dir, 0);
    }
    private static void createHtmlFiles(File dir, int level) {

        List<String> dirs = new ArrayList<>();
        List<String> files = new ArrayList<>();

        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                dirs.add(f.getName());
                
                createHtmlFiles(f, level + 1);
            }
            else { 
                if (supportedExtensions.contains(FileUtils.getExtension(f))) {
                    files.add(f.getName());
                }
            }   
        }

        buildIndexPage(dir.getPath(), dirs, files, level);

        for (int i = 0; i < files.size(); i++) {
            String prev = (i == 0) ? "" : Main.getHtmlFileFromImage(files.get(i - 1));
            String next = (i == files.size() - 1) ? "" : Main.getHtmlFileFromImage(files.get(i + 1)); 

            buildImagePage(Main.getHtmlFileFromImage(dir.getPath() + File.separator + files.get(i)), files.get(i), prev, next, level);
        }
    }

    private static boolean createFile(String path, int level) {
        try {
            writer = new PrintWriter(path, "utf-8");

            writer.println("" +
                    "<!DOCTYPE html>\n" + 
                    "<html>\n" +
                    "\t<head>\n" +
                    "\t\t<title>ImageViewer</title>\n" +
                    "\t</head>\n" +
                    "\t<body>\n" +
                    "\t\t<a href=\"" + "../".repeat(level) + "index.html\"><h1>Start page</h1></a>");

            return true;
        }
        catch (Exception e) {
            System.err.printf("Fájl létrehozása meghíusult: %s\n\t%s\n", path, e.getMessage());
            writer.close();
            return false;
        }
    }

    public static void buildIndexPage(String path, List<String> dirs, List<String> images, int level) {
        if (createFile(path + File.separator + "index.html", level)) {
            writer.println("" + 
                    "\t\t<h2>Directories</h2>\n" +
                    "\t\t<ul>");

            if (level != 0) {
                writer.println(String.format("\t\t\t<li><a href=\"%s\">%s</a></li>", "../index.html", "<<"));
            }
            for (String s : dirs) {
                writer.println(String.format("\t\t\t<li><a href=\"%s\">%s</a></li>", s + "/index.html", s));
            }
            writer.println("\t\t</ul>");

            if (images.size() > 0) {
                writer.println("" + 
                        "\t\t<h2>Images</h2>\n" + 
                        "\t\t<ul>");
                for (String s : images) {
                    writer.println(String.format("\t\t\t<li><a href=\"%s\">%s</a></li>", Main.getHtmlFileFromImage(s), s));
                }
                writer.println("\t\t</ul>");
            }

            closeFile();
        }
    }

    public static void buildImagePage(String path, String image, String prev, String next, int level) {
        if (createFile(path, level)) {
            writer.println("\t\t<a href=\"../index.html\">^^</a><br />");
            
            StringBuilder sb = new StringBuilder("\t\t");
            if (!prev.isEmpty()) {
                sb.append(String.format("<a href=\"%s\"><<</a>", prev));
            }
            if (!next.isEmpty()) {
                sb.append(String.format("<a href=\"%s\">>></a>", next));
            }
            sb.append("<br />");
            writer.println(sb.toString());

            sb = new StringBuilder();
            if (!next.isEmpty()) {
                sb.append(String.format("\t\t<a href=\"%s\">", next));
            }
            sb.append("\n\t\t<img src=\"" + image + "\" width=\"500\" />");
            if (!next.isEmpty()) {
                sb.append("</a>");
            }
            writer.println(sb.toString());

            closeFile();
        }
    }

    private static void closeFile() {
        writer.println("" +
                "\t</body>\n" + 
                "</html>");
        writer.close();
    }
}
