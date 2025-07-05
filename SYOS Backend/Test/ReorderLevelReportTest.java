

import dao.ReorderLevelReport;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReorderLevelReportTest {

    @Test
    void testGenerateReorderLevelReport() {
        ReorderLevelReport report = new ReorderLevelReport();

        List<ReorderLevelReport.ReorderEntry> entries = report.generateReorderLevelReport();

        // Basic assertions - adjust based on your test DB data
        assertNotNull(entries, "Report list should not be null");

        // If you expect data, check at least one entry is present
        if (!entries.isEmpty()) {
            ReorderLevelReport.ReorderEntry entry = entries.get(0);
            assertNotNull(entry.getItemName(), "Item name should not be null");
            assertNotNull(entry.getItemCode(), "Item code should not be null");
            assertTrue(entry.getTotalStockQuantity() < 50, "Total stock quantity should be less than 50");
        }
    }
}

