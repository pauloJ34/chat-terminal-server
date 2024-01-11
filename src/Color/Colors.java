package Color;

public enum Colors {

    RESET("\033[0m"),

    RED("\033[0;31m"), // RED
    GREEN("\033[0;32m"), // GREEN
    YELLOW("\033[0;33m"), // YELLOW
    BLUE("\033[0;34m"), // BLUE
    MAGENTA("\033[0;35m"), // MAGENTA
    CYAN("\033[0;36m"), // CYAN

     // Bold
    //  BLACK_BOLD("\033[1;30m"),   // BLACK
     RED_BOLD("\033[1;31m"),     // RED
     GREEN_BOLD("\033[1;32m"),   // GREEN
     YELLOW_BOLD("\033[1;33m"),  // YELLOW
     BLUE_BOLD("\033[1;34m"),    // BLUE
     MAGENTA_BOLD("\033[1;35m"), // MAGENTA
     CYAN_BOLD("\033[1;36m"),    // CYAN
     WHITE_BOLD("\033[1;37m");   // WHITE

    private final String code;

    Colors(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
