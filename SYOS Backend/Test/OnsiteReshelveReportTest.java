

import dao.OnsiteReshelveReport;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OnsiteReshelveReportTest {

    @Test
    void testGenerateReshelveReport() {
        OnsiteReshelveReport report = new OnsiteReshelveReport();

        List<OnsiteReshelveReport.ReshelveEntry> entries = report.generateReshelveReport();

        assertNotNull(entries, "Report list should not be null");


        for (OnsiteReshelveReport.ReshelveEntry entry : entries) {
            assertNotNull(entry.getItemName(), "Item name should not be null");
            assertNotNull(entry.getItemCode(), "Item code should not be null");
            assertTrue(entry.getTotalQuantity() >= 0, "Total quantity should be zero or more");
        }
    }
}
