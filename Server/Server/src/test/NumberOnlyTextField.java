package test;
import javax.swing.*;
import java.text.NumberFormat;

public class NumberOnlyTextField {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Number Only Field");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        NumberFormat format = NumberFormat.getIntegerInstance();
        JFormattedTextField numberField = new JFormattedTextField(format);
        numberField.setColumns(10);

        frame.add(numberField);
        frame.setVisible(true);
    }
}
