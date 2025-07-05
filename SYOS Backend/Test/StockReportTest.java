

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import dao.StockReport;
import org.junit.jupiter.api.Test;

public class StockReportTest {

    @Test
    public void testGenerateStockReport() {
        StockReport stockReport = new StockReport();
        List<StockReport.StockEntry> entries = stockReport.generateStockReport();

        assertNotNull(entries, "Report list should not be null");

        for (StockReport.StockEntry entry : entries) {
            assertNotNull(entry.getItemCode(), "Item code should not be null");
            assertNotNull(entry.getDateOfPurchase(), "Date of purchase should not be null");
            assertTrue(entry.getQuantityReceived() >= 0, "Quantity received should be non-negative");
            assertNotNull(entry.getExpiryDate(), "Expiry date should not be null");
        }
    }
}
