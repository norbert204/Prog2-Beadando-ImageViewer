import java.io.File;

class FileUtils {
    private FileUtils() {
        //  Nem példányosítható
    }

    public static String getExtension(File file) {
        if (file.isDirectory()) {
            return "";
        }

        String[] splitFilename = file.getName().split("\\.");
        String extension = splitFilename[splitFilename.length - 1];

        return extension;
    }
}
