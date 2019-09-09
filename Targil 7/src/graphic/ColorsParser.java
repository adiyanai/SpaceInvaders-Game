package graphic;
import java.awt.Color;
/**
 * The class convert a color definition to specified color.
 * @author Adi Yanai
 *
 */
public class ColorsParser {
    /**
     * parse color definition and return the specified color.
     * @param s - a color definition
     * @return the specified color
     */
    public java.awt.Color colorFromString(String s) {
        String[] temp = s.split("\\(");
        temp = temp[temp.length - 1].split("\\)");
        // convert rgb color
        if (s.contains("RGB")) {
            String[] rgb = temp[0].split(",");
            return new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
          // convert basic colors
        } else {
              if (temp[0].equals("black")) {
                   return Color.black;
               }
               if (temp[0].equals("blue")) {
                   return Color.blue;
               }
               if (temp[0].equals("cyan")) {
                   return Color.cyan;
               }
               if (temp[0].equals("gray")) {
                   return Color.gray;
               }
               if (temp[0].equals("lightGray")) {
                   return Color.lightGray;
               }
               if (temp[0].equals("green")) {
                   return Color.green;
               }
               if (temp[0].equals("orange")) {
                   return Color.orange;
               }
               if (temp[0].equals("pink")) {
                   return Color.pink;
               }
               if (temp[0].equals("red")) {
                   return Color.red;
               }
               if (temp[0].equals("white")) {
                   return Color.white;
               }
               if (temp[0].equals("yellow")) {
                   return Color.yellow;
               }
         }
         return null;
    }
}
