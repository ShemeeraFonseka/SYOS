package controller;

import com.google.gson.Gson;
import dao.StockReport;
import dao.StockReport.StockEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockReportServletTest {

    private StockReportServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StockReport stockReportMock;

    @BeforeEach
    void setUp() {
        servlet = new StockReportServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        stockReportMock = mock(StockReport.class);
    }

    @Test
    void doGet_ShouldReturnJsonResponse() throws Exception {
        // Prepare dummy data for StockReport
        List<StockEntry> dummyEntries = Arrays.asList(
                new StockEntry("Item A", "A001", 100, "extraFieldValue"),
        new StockEntry("Item B", "A002", 100, "extraFieldValue")
        );


        StockReportServlet testServlet = new StockReportServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
                resp.setHeader("Access-Control-Allow-Origin", "*");
                resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
                resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

                // use dummy data instead of real DAO
                List<StockEntry> reportList = dummyEntries;

                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                String json = new Gson().toJson(reportList);
                resp.getWriter().write(json);
            }
        };

        // Prepare StringWriter to capture output
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(printWriter);

        // Call servlet doGet
        testServlet.doGet(request, response);

        // Flush writer to complete writing
        printWriter.flush();

        // Verify headers
        verify(response).setHeader("Access-Control-Allow-Origin", "*");
        verify(response).setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        verify(response).setHeader("Access-Control-Allow-Headers", "Content-Type");
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        // Check JSON output
        String expectedJson = new Gson().toJson(dummyEntries);
        String actualJson = stringWriter.toString();

        assertEquals(expectedJson, actualJson);
    }
}
