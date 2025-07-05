

import dao.BillReport;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BillReportTest {

    @Test
    void testGenerateBillReport() {
        BillReport billReport = new BillReport();
        List<BillReport.BillEntry> report = billReport.generateBillReport();

        // Check that the list is not null
        assertNotNull(report, "Report list should not be null");

        // Optionally check that the list is not empty
        assertFalse(report.isEmpty(), "Report list should not be empty if the DB has data");

        // Check properties of the first entry if present
        BillReport.BillEntry firstEntry = report.get(0);
        assertNotNull(firstEntry);

        assertTrue(firstEntry.getBillID() > 0, "BillID should be positive");
        assertNotNull(firstEntry.getBillDate(), "BillDate should not be null");
        assertNotNull(firstEntry.getTransactionType(), "TransactionType should not be null");
        assertTrue(firstEntry.getTotalAmount() >= 0, "TotalAmount should be non-negative");
        assertTrue(firstEntry.getDiscount() >= 0, "Discount should be non-negative");
        assertTrue(firstEntry.getCashTendered() >= 0, "CashTendered should be non-negative");
        assertTrue(firstEntry.getCashChange() >= 0, "CashChange should be non-negative");
        assertNotNull(firstEntry.getCustomerID(), "CustomerID should not be null");
    }
}
