

import dao.OnlineReshelveReport;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OnlineReshelveReportTest {

    @Test
    void testGenerateReshelveReport() {
        OnlineReshelveReport report = new OnlineReshelveReport();

        List<OnlineReshelveReport.ReshelveEntry> reshelveEntries = report.generateReshelveReport();

        assertNotNull(reshelveEntries, "Report list should not be null");
        // You can add further checks depending on expected DB state, e.g.:
        // assertFalse(reshelveEntries.isEmpty(), "Report should not be empty if there is data for today");

        for (OnlineReshelveReport.ReshelveEntry entry : reshelveEntries) {
            assertNotNull(entry.getItemName(), "Item name should not be null");
            assertNotNull(entry.getItemCode(), "Item code should not be null");
            assertTrue(entry.getTotalQuantity() >= 0, "Total quantity should be zero or positive");
        }
    }
}
