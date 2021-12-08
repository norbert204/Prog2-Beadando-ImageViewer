import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class HtmlBuilder {
    private HtmlBuilder() {}

    private static PrintWriter writer;

    public static void createHtmlFiles(File dir) {
        createHtmlFiles(dir, 0);
    }

    private static void createHtmlFiles(File dir, int level) {

    }

    public static void buildIndexPage(String path, List<String> dirs, List<String> images, int level) {
        buildBase(path + File.separator + "index.html", level);

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

        addEnd();
    }

    public static void buildImagePage(String path, String image, String prev, String next, int level) {
        buildBase(path, level);

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

        addEnd();
    }

    private static void buildBase(String path, int level) {
        try {
            writer = new PrintWriter(path, "utf-8");
        }
        catch (FileNotFoundException e) {

        }
        catch (UnsupportedEncodingException e) {

        }

        writer.println("" +
                "<!DOCTYPE html>\n" + 
                "<html>\n" +
                "\t<head>\n" +
                "\t\t<title>ImageViewer</title>\n" +
                "\t</head>\n" +
                "\t<body>\n" +
                "\t\t<a href=\"" + "../".repeat(level) + "index.html\"><h1>Start page</h1></a>");
    }

    private static void addEnd() {
        writer.println("" +
                "\t</body>\n" + 
                "</html>");
        writer.close();
    }
}
