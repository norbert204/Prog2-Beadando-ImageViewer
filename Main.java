import java.io.File;

class Main {

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
                HtmlBuilder.createHtmlFiles(root);
            }
        }
    }

    public static void printMan() {
        System.out.println("\nHASZNÁLAT");
        System.out.println("\tjava -jar ImageViewer.jar <paraméterek>");

        System.out.println("\nPARAMÉTEREK");
        System.out.println("\t--help/-h\t\tKiírja ezt a segítséget");
        System.out.println("\t<könyvtár>\t\tLétrehozza a HTML fájlokat a megadott könyvtárban");
        System.out.println("\t<könyvtár> [-d/--d]\tTörli a már meglévő HTML fájlokat a megadott könyvtárban");
    }
}
