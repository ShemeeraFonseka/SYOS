

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import dao.SalesReport;
import org.junit.jupiter.api.Test;

public class SalesReportTest {

    @Test
    public void testGetSalesReport() {
        SalesReport salesReport = new SalesReport();
        List<SalesReport.ReportEntry> reportEntries = salesReport.getSalesReport();

        // Basic checks
        assertNotNull(reportEntries, "Report list should not be null");

        // Since it depends on today's data, it can be empty
        // but if you want, you can check if entries make sense when there is data

        for (SalesReport.ReportEntry entry : reportEntries) {
            assertNotNull(entry.itemName, "Item name should not be null");
            assertNotNull(entry.itemCode, "Item code should not be null");
            assertTrue(entry.totalQuantity >= 0, "Total quantity should be >= 0");
            assertTrue(entry.totalRevenue >= 0, "Total revenue should be >= 0");
        }
    }
}
