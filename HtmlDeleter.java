import java.io.File;

class HtmlDeleter {
    private HtmlDeleter() {}

    public static void deleteHtmlFiles(File dir) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                deleteHtmlFiles(file);
            }
            else {
                if (Main.getExtension(file).equals("html")) {
                    if (file.delete()) {
                        System.out.printf("Fájl törölve: %s\n", file.getPath());
                    }
                    else {
                        System.out.printf("Fájl törlése meghíusult: %s\n", file.getPath());
                    }
                }
            }
        }
    }
}
