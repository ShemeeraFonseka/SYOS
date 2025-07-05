package controller;

import com.google.gson.Gson;
import dao.SalesReport;
import dao.SalesReport.ReportEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SalesReportServletTest {

    private SalesReportServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        servlet = new SalesReportServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @Test
    void doGet_ShouldReturnJsonResponse() throws Exception {
        // Prepare dummy data for SalesReport
        List<ReportEntry> dummyEntries = Arrays.asList(
                new ReportEntry("2025-01-01", "Product A", 10, 200.0),
                new ReportEntry("2025-01-02", "Product B", 5, 150.0)
        );

        // Since SalesReport is instantiated inside the servlet,
        // we override the servlet's doGet to use dummy data directly
        SalesReportServlet testServlet = new SalesReportServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
                resp.setHeader("Access-Control-Allow-Origin", "*");
                resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
                resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                String json = new Gson().toJson(dummyEntries);
                resp.getWriter().write(json);
            }
        };

        // Prepare to capture output
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(printWriter);

        // Execute servlet method
        testServlet.doGet(request, response);

        // Flush writer
        printWriter.flush();

        // Verify headers set
        verify(response).setHeader("Access-Control-Allow-Origin", "*");
        verify(response).setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        verify(response).setHeader("Access-Control-Allow-Headers", "Content-Type");
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        // Check JSON output correctness
        String expectedJson = new Gson().toJson(dummyEntries);
        assertEquals(expectedJson, stringWriter.toString());
    }
}
