package view;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public class CreateJDatePicker {
    public JDatePickerImpl datePicker;

    CreateJDatePicker(JFrame frame){
        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.day","Day");
        p.put("text.month","Month");
        p.put("text.year","Year");
        JDatePanelImpl panel = new JDatePanelImpl(model,p);
        datePicker = new JDatePickerImpl(panel, new JFormattedTextField.AbstractFormatter() {
            @Override
            public Object stringToValue(String s) throws ParseException {
                return null;
            }

            @Override
            public String valueToString(Object o) throws ParseException {
                if(o != null)
                {
                    Calendar cal = (Calendar) o;
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    String strDate = format.format(cal.getTime());
                    return strDate;
                }
                return " ";
            }
        });
        frame.add(datePicker);
    }

    CreateJDatePicker(JPanel jPanel){
        JDatePickerImpl datePicker;
        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.day","Day");
        p.put("text.month","Month");
        p.put("text.year","Year");
        JDatePanelImpl panel = new JDatePanelImpl(model,p);
        datePicker = new JDatePickerImpl(panel, new JFormattedTextField.AbstractFormatter() {
            @Override
            public Object stringToValue(String s) throws ParseException {
                return null;
            }

            @Override
            public String valueToString(Object o) throws ParseException {
                if(o != null)
                {
                    Calendar cal = (Calendar) o;
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    String strDate = format.format(cal.getTime());
                    return strDate;
                }
                return " ";
            }
        });
        jPanel.add(datePicker);
    }
}
