import model.CostManagerException;
import model.DerbyDBModel;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class GetCostOrIncomeTest {
    DerbyDBModel derbyDBModel;
    private final static Date MOCK_EARLY_DATE = new java.sql.Date(10,4,12);
    private final static Date MOCK_LATE_DATE = new java.sql.Date(100,4,12);

    @BeforeEach
    public void setUp()
    {
        try {
            derbyDBModel = new DerbyDBModel();
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void failGetAllCostsBetweenDatesTest() {
        try {
            derbyDBModel.getAllCostsBetweenDates(MOCK_LATE_DATE,MOCK_EARLY_DATE);
            fail("The addition succeed when it should failed");
        } catch (CostManagerException e) {
            Assertions.assertEquals("The date 'from' come after the date 'to'", e.getMessage());
        }
    }

    @Test
    public void succeedGetAllCostsBetweenDatesTest() {
        try {
            derbyDBModel.getAllCostsBetweenDates(MOCK_EARLY_DATE,MOCK_LATE_DATE);
        } catch (CostManagerException e) {
            fail("The exception was thrown even though it was not supposed to be thrown");
        }
    }


    @Test
    public void failGetAllIncomesBetweenDatesTest() {
        try {
            derbyDBModel.getAllCostsBetweenDates(MOCK_LATE_DATE,MOCK_EARLY_DATE);
            fail("The addition succeed when it should failed");
        } catch (CostManagerException e) {
            Assertions.assertEquals("The date 'from' come after the date 'to'", e.getMessage());
        }
    }

    @Test
    public void succeedGetAllIncomesBetweenDatesTest() {
        try {
            derbyDBModel.getAllCostsBetweenDates(MOCK_EARLY_DATE,MOCK_LATE_DATE);
        } catch (CostManagerException e) {
            fail("The exception was thrown even though it was not supposed to be thrown");
        }
    }


    @Test
    public void failGetAllCostsAndIncomesBetweenDates() {
        try {
            derbyDBModel.getAllCostsBetweenDates(MOCK_LATE_DATE,MOCK_EARLY_DATE);
            fail("The addition succeed when it should failed");
        } catch (CostManagerException e) {
            Assertions.assertEquals("The date 'from' come after the date 'to'", e.getMessage());
        }
    }

    @Test
    public void succeedGetAllCostsAndIncomesBetweenDates() {
        try {
            derbyDBModel.getAllCostsBetweenDates(MOCK_EARLY_DATE,MOCK_LATE_DATE);
        } catch (CostManagerException e) {
            fail("The exception was thrown even though it was not supposed to be thrown");
        }
    }
}
