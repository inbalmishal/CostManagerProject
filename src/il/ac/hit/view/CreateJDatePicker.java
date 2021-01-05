package il.ac.hit.view;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

/**
 * This class create the component of putting a date.
 * @author Inbal mishal and Tal levi
 */
public class CreateJDatePicker {
    private JDatePickerImpl datePicker;

    /**
     * Create the component of putting a date.
     * @param jPanel Represent the panel which on it the date will be.
     */
    CreateJDatePicker(JPanel jPanel){
        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.day","Day");
        p.put("text.month","Month");
        p.put("text.year","Year");
        JDatePanelImpl panel = new JDatePanelImpl(model,p);
        datePicker = new JDatePickerImpl(panel, new JFormattedTextField.AbstractFormatter() {
            @Override
            public Object stringToValue(String s) {
                return null;
            }

            @Override
            public String valueToString(Object o){
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

    /**
     * Get the datePicker object.
     * @return the datePicker object
     */
    public JDatePickerImpl getDatePicker() {
        return datePicker;
    }

    /**
     * Set the datePicker object.
     * @param datePicker Represent the datePicker object.
     */
    public void setDatePicker(JDatePickerImpl datePicker) {
        this.datePicker = datePicker;
    }
}
