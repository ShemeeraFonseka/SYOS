package controller;

import controller.BatchServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BatchServletTest {

    private BatchServlet batchServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;

    private StringWriter responseWriter;

    @BeforeEach
    public void setUp() throws Exception {
        batchServlet = new BatchServlet();
        batchServlet.init(); // Simulate servlet init

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        responseWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(printWriter);
    }

    @Test
    public void testValidBatchInsertion() throws Exception {
        // Mock request parameters
        when(request.getParameter("itemCode")).thenReturn("fru001");
        when(request.getParameter("dateOfPurchase")).thenReturn("2024-10-10");
        when(request.getParameter("quantityReceived")).thenReturn("100");
        when(request.getParameter("expiryDate")).thenReturn("2025-10-10");
        when(request.getParameter("stockQuantity")).thenReturn("100");

        // Call doPost
        batchServlet.doPost(request, response);

        // Verify response
        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertTrue(responseWriter.toString().contains("Batch inserted successfully"));
    }

    @Test
    public void testInvalidDateFormat() throws Exception {
        // Invalid date
        when(request.getParameter("itemCode")).thenReturn("ITEM002");
        when(request.getParameter("dateOfPurchase")).thenReturn("2024/10/10");
        when(request.getParameter("quantityReceived")).thenReturn("50");
        when(request.getParameter("expiryDate")).thenReturn("2025-10-10");
        when(request.getParameter("stockQuantity")).thenReturn("50");

        // Call doPost
        batchServlet.doPost(request, response);

        // Verify response
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        assertTrue(responseWriter.toString().contains("Invalid input"));
    }
}
