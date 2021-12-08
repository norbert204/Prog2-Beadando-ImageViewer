import java.io.File;
import java.util.ArrayList;
import java.util.List;

class Main {

    public static List<String> supportedExtensions = List.of("jpg", "png", "jpeg", "gif", "bmp");

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Hiba! Adjon meg paraméterként egy könyvtárat!");
            System.exit(1);
        }

        if (args[0].equals("--help") || args[0].equals("-h")) {
            printMan();
        }
        else {
            File root = new File(args[0]);
            if (!root.isDirectory()) {
                System.err.println("Hiba! Könyvtárat adjon meg!");
                System.exit(2);
            }

            if (args.length == 2 && (args[1].equals("-d") || args[1].equals("--delete"))) {
                HtmlDeleter.deleteHtmlFiles(root);
            }
            else { 
                handleDirectory(root, 0);
            }
        }
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
            else { 
                if (supportedExtensions.contains(getExtension(f))) {
                    files.add(f.getName());
                }
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

    public static String getExtension(File file) {
        String[] splitFilename = file.getName().split("\\.");
        String extension = splitFilename[splitFilename.length - 1];

        return extension;
    }

    public static void printMan() {
        System.out.println("PARAMETERS");
        System.out.println("\t--help/-help\t\tPrinting out this page");
        System.out.println("\t<directory>\t\tCreating the HTML files in the specified directory");
        System.out.println("\t<directory> [-d/--d]\tDeleting the already existing HTML files in the specified directory");
    }
}
