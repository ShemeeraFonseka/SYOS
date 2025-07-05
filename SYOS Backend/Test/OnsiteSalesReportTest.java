

import dao.OnsiteSalesReport;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OnsiteSalesReportTest {

    @Test
    void testGetOnsiteSalesReport() {
        OnsiteSalesReport report = new OnsiteSalesReport();
        List<OnsiteSalesReport.ReportEntry> entries = report.getOnsiteSalesReport();

        assertNotNull(entries, "Report list should not be null");
        // Optionally check if the list is not empty if data is expected
        // assertFalse(entries.isEmpty(), "Report should have entries if data exists");

        for (OnsiteSalesReport.ReportEntry entry : entries) {
            assertNotNull(entry.itemName, "Item name should not be null");
            assertNotNull(entry.itemCode, "Item code should not be null");
            assertTrue(entry.totalQuantity >= 0, "Total quantity should be non-negative");
            assertTrue(entry.totalRevenue >= 0, "Total revenue should be non-negative");
        }
    }
}
