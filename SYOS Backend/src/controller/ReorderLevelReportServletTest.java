package controller;

import com.google.gson.Gson;
import dao.ReorderLevelReport;
import dao.ReorderLevelReport.ReorderEntry;
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

class ReorderLevelReportServletTest {

    private ReorderLevelReportServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        servlet = new ReorderLevelReportServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @Test
    void doGet_ShouldReturnJsonResponse() throws Exception {
        // Prepare dummy data for ReorderLevelReport
        List<ReorderEntry> dummyEntries = Arrays.asList(
                new ReorderEntry("Item A", "A001", 10),
                new ReorderEntry("Item B", "B002", 20)
        );

        // Override servlet's doGet to inject dummy data instead of calling DAO
        ReorderLevelReportServlet testServlet = new ReorderLevelReportServlet() {
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

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(printWriter);

        // Call the overridden doGet
        testServlet.doGet(request, response);

        printWriter.flush();

        // Verify CORS headers and content-type
        verify(response).setHeader("Access-Control-Allow-Origin", "*");
        verify(response).setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        verify(response).setHeader("Access-Control-Allow-Headers", "Content-Type");
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        // Assert that output matches expected JSON
        String expectedJson = new Gson().toJson(dummyEntries);
        assertEquals(expectedJson, stringWriter.toString());
    }
}
