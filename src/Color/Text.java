package Color;

public class Text {
    public static String color(String nickname, String messege){

        String text = Colors.RED_BOLD.toString()+nickname+": "+Colors.RESET+messege;
        return text;
    }

}
