package iteration2;

public class ConsoleColours {

    public static final String ANSI_RESET;
    public static final String ANSI_WHITE_BRIGHT;
    public static final String ANSI_PURPLE_BACKGROUND;
    public static final String ANSI_RED_BACKGROUND;
    public static final String ANSI_GREEN_BACKGROUND;
    public static final String ANSI_YELLOW_BACKGROUND;
    public static final String ANSI_BLUE_BACKGROUND;
    public static final String ANSI_WHITE_BRIGHT_BACKGROUND;
    public static final String ANSI_BLACK;


    /*
    // Reset
    public static final String RESET;  // Text Reset

    // Regular Colors
    public static final String BLACK;   // BLACK
    public static final String RED;     // RED
    public static final String GREEN;   // GREEN
    public static final String YELLOW;  // YELLOW
    public static final String BLUE;    // BLUE
    public static final String PURPLE;  // PURPLE
    public static final String CYAN;    // CYAN
    public static final String WHITE;   // WHITE

    // Bold
    public static final String BLACK_BOLD;  // BLACK
    public static final String RED_BOLD;    // RED
    public static final String GREEN_BOLD;  // GREEN
    public static final String YELLOW_BOLD; // YELLOW
    public static final String BLUE_BOLD;   // BLUE
    public static final String PURPLE_BOLD; // PURPLE
    public static final String CYAN_BOLD;   // CYAN
    public static final String WHITE_BOLD;  // WHITE

    // Underline
    public static final String BLACK_UNDERLINED;  // BLACK
    public static final String RED_UNDERLINED;    // RED
    public static final String GREEN_UNDERLINED;  // GREEN
    public static final String YELLOW_UNDERLINED; // YELLOW
    public static final String BLUE_UNDERLINED;   // BLUE
    public static final String PURPLE_UNDERLINED; // PURPLE
    public static final String CYAN_UNDERLINED;   // CYAN
    public static final String WHITE_UNDERLINED;  // WHITE

    // Background
    public static final String BLACK_BACKGROUND;  // BLACK
    public static final String RED_BACKGROUND;    // RED
    public static final String GREEN_BACKGROUND;  // GREEN
    public static final String YELLOW_BACKGROUND; // YELLOW
    public static final String BLUE_BACKGROUND;   // BLUE
    public static final String PURPLE_BACKGROUND; // PURPLE
    public static final String CYAN_BACKGROUND;   // CYAN
    public static final String WHITE_BACKGROUND;  // WHITE

    // High Intensity
    public static final String BLACK_BRIGHT;  // BLACK
    public static final String RED_BRIGHT;    // RED
    public static final String GREEN_BRIGHT;  // GREEN
    public static final String YELLOW_BRIGHT; // YELLOW
    public static final String BLUE_BRIGHT;   // BLUE
    public static final String PURPLE_BRIGHT; // PURPLE
    public static final String CYAN_BRIGHT;   // CYAN
    public static final String WHITE_BRIGHT;  // WHITE

    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT; // BLACK
    public static final String RED_BOLD_BRIGHT;   // RED
    public static final String GREEN_BOLD_BRIGHT; // GREEN
    public static final String YELLOW_BOLD_BRIGHT;// YELLOW
    public static final String BLUE_BOLD_BRIGHT;  // BLUE
    public static final String PURPLE_BOLD_BRIGHT;// PURPLE
    public static final String CYAN_BOLD_BRIGHT;  // CYAN
    public static final String WHITE_BOLD_BRIGHT; // WHITE

    // High Intensity backgrounds
    public static final String BLACK_BACKGROUND_BRIGHT;// BLACK
    public static final String RED_BACKGROUND_BRIGHT;// RED
    public static final String GREEN_BACKGROUND_BRIGHT;// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT;// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT;// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT;  // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT;   // WHITE


    //ANCI
    public static final String ANSI_BLACK;
    public static final String ANSI_RED;
    public static final String ANSI_GREEN;
    public static final String ANSI_YELLOW;
    public static final String ANSI_BLUE;
    public static final String ANSI_PURPLE;
    public static final String ANSI_CYAN;
    public static final String ANSI_WHITE;
    public static final String ANSI_BLACK_BACKGROUND;

    public static final String ANSI_CYAN_BACKGROUND;
    public static final String ANSI_WHITE_BACKGROUND;
    public static final String ANSI_WHITE_BRIGHT_BACKGROUND;
     */

    static {
        ANSI_RESET = "\u001B[0m";
        ANSI_WHITE_BRIGHT = "\u001B[97m";
        ANSI_BLACK = "\u001B[30m";
        ANSI_RED_BACKGROUND = "\u001B[41m";
        ANSI_GREEN_BACKGROUND = "\u001B[42m";
        ANSI_BLUE_BACKGROUND = "\u001B[44m";
        ANSI_YELLOW_BACKGROUND = "\u001B[43m";
        ANSI_PURPLE_BACKGROUND = "\u001B[45m";
        ANSI_WHITE_BRIGHT_BACKGROUND = "\u001B[107m";
        //ANSI_BLUE_BRIGHT_BACKGROUND = "\u001B[104m";

        /*
        RESET = "\033[0m";

        PURPLE = "\033[0;35m";
        BLUE = "\033[0;34m";
        YELLOW = "\033[0;33m";
        GREEN = "\033[0;32m";
        RED = "\033[0;31m";
        BLACK = "\033[0;30m";
        CYAN = "\033[0;36m";
        WHITE = "\033[0;37m";

        BLACK_BOLD = "\033[1;30m";
        RED_BOLD = "\033[1;31m";
        GREEN_BOLD = "\033[1;32m";
        YELLOW_BOLD = "\033[1;33m";
        BLUE_BOLD = "\033[1;34m";
        PURPLE_BOLD = "\033[1;35m";
        CYAN_BOLD = "\033[1;36m";
        WHITE_BOLD = "\033[1;37m";

        BLACK_UNDERLINED = "\033[4;30m";
        RED_UNDERLINED = "\033[4;31m";
        GREEN_UNDERLINED = "\033[4;32m";
        YELLOW_UNDERLINED = "\033[4;33m";
        BLUE_UNDERLINED = "\033[4;34m";
        PURPLE_UNDERLINED = "\033[4;35m";
        CYAN_UNDERLINED = "\033[4;36m";
        WHITE_UNDERLINED = "\033[4;37m";

        BLACK_BACKGROUND = "\033[40m";
        RED_BACKGROUND = "\033[41m";
        GREEN_BACKGROUND = "\033[42m";
        YELLOW_BACKGROUND = "\033[43m";
        BLUE_BACKGROUND = "\033[44m";
        PURPLE_BACKGROUND = "\033[45m";
        CYAN_BACKGROUND = "\033[46m";
        WHITE_BACKGROUND = "\033[47m";

        BLACK_BRIGHT = "\033[0;90m";
        RED_BRIGHT = "\033[0;91m";
        GREEN_BRIGHT = "\033[0;92m";
        YELLOW_BRIGHT = "\033[0;93m";
        BLUE_BRIGHT = "\033[0;94m";
        PURPLE_BRIGHT = "\033[0;95m";
        CYAN_BRIGHT = "\033[0;96m";
        WHITE_BRIGHT = "\033[0;97m";

        BLACK_BOLD_BRIGHT = "\033[1;90m";
        RED_BOLD_BRIGHT = "\033[1;91m";
        GREEN_BOLD_BRIGHT = "\033[1;92m";
        YELLOW_BOLD_BRIGHT = "\033[1;93m";
        BLUE_BOLD_BRIGHT = "\033[1;94m";
        PURPLE_BOLD_BRIGHT = "\033[1;95m";
        CYAN_BOLD_BRIGHT = "\033[1;96m";
        WHITE_BOLD_BRIGHT = "\033[1;97m";

        BLACK_BACKGROUND_BRIGHT = "\033[0;100m";
        RED_BACKGROUND_BRIGHT = "\033[0;101m";
        GREEN_BACKGROUND_BRIGHT = "\033[0;102m";
        YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";
        BLUE_BACKGROUND_BRIGHT = "\033[0;104m";
        PURPLE_BACKGROUND_BRIGHT = "\033[0;105m";
        CYAN_BACKGROUND_BRIGHT = "\033[0;106m";
        WHITE_BACKGROUND_BRIGHT = "\033[0;107m";

        ANSI_BLACK = "\u001B[30m";
        ANSI_RED = "\u001B[31m";
        ANSI_GREEN = "\u001B[32m";
        ANSI_YELLOW = "\u001B[33m";
        ANSI_BLUE = "\u001B[34m";
        ANSI_PURPLE = "\u001B[35m";
        ANSI_CYAN = "\u001B[36m";
        ANSI_WHITE = "\u001B[37m";

        ANSI_BLACK_BACKGROUND = "\u001B[40m";
        ANSI_CYAN_BACKGROUND = "\u001B[46m";
        ANSI_WHITE_BACKGROUND = "\u001B[47m";
        ANSI_WHITE_BRIGHT_BACKGROUND = "\u001B[107m";
        */
    }

    public static void resetColour() {
        System.out.print(ANSI_RESET);
    }
    public static void paintBlueMenu() {
        System.out.print(ANSI_BLUE_BACKGROUND + ANSI_WHITE_BRIGHT);
    }
    public static void paintRedMenu() {
        System.out.print(ANSI_RED_BACKGROUND + ANSI_WHITE_BRIGHT);
    }
    public static void paintYellowMenu() {
        System.out.print(ANSI_YELLOW_BACKGROUND + ANSI_WHITE_BRIGHT);
    }
    public static void paintGreenMenu() {
        System.out.print(ANSI_GREEN_BACKGROUND + ANSI_WHITE_BRIGHT);
    }
    public static void paintPurpleMenu() {
        System.out.print(ANSI_PURPLE_BACKGROUND + ANSI_WHITE_BRIGHT);
    }
    public static void paintWhiteMenu() { System.out.print(ANSI_BLACK + ANSI_WHITE_BRIGHT_BACKGROUND); }
}
    