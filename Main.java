import java.io.File;
import java.util.ArrayList;
import java.util.List;

class Main {

    public static List<String> supportedExtensions = List.of("jpg", "png", "jpeg", "gif", "bmp");

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Hiba! Adjon meg paraméterként egy könyvtárat!");
            System.exit(1);
        }

        File root = new File(args[0]);
        if (!root.isDirectory()) {
            System.err.println("Hiba! Könyvtárat adjon meg!");
            System.exit(1);
        }

        handleDirectory(root, 0);
    }

    //  TODO: refactor this
    public static void handleDirectory(File dir, int level) {
        List<String> dirs = new ArrayList<>();
        List<String> files = new ArrayList<>();

        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                dirs.add(f.getName());
                
                handleDirectory(f, level + 1);
            }
            //  TODO: ugh this looks sooooo ugly
            else if (supportedExtensions.contains(f.getName().split("\\.")[f.getName().split("\\.").length - 1])) {
                files.add(f.getName());
            }   
        }

        HtmlBuilder.buildIndexPage(dir.getPath(), dirs, files, level);

        for (int i = 0; i < files.size(); i++) {
            String prev = (i == 0) ? "" : getHtmlFileFromImage(files.get(i - 1));
            String next = (i == files.size() - 1) ? "" : getHtmlFileFromImage(files.get(i + 1)); 

            HtmlBuilder.buildImagePage(getHtmlFileFromImage(dir.getPath() + File.separator + files.get(i)), files.get(i), prev, next, level);
        }
    }

    public static String getHtmlFileFromImage(String image) {
        StringBuilder sb = new StringBuilder(image);
        sb.replace(image.lastIndexOf('.') + 1, image.length(), "html");

        return sb.toString();
    }
}
