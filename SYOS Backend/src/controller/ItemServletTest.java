package controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import dao.ItemDAO;
import model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

public class ItemServletTest {

    private ItemServlet servlet;
    private ItemDAO itemDAOMock;
    private Gson gson;

    @BeforeEach
    public void setUp() {
        // Create a mock ItemDAO
        itemDAOMock = mock(ItemDAO.class);

        // Create servlet instance but replace the itemDAO with our mock via reflection
        servlet = new ItemServlet();

        // Use reflection to set the private final itemDAO field to our mock
        try {
            java.lang.reflect.Field field = ItemServlet.class.getDeclaredField("itemDAO");
            field.setAccessible(true);
            field.set(servlet, itemDAOMock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        gson = new Gson();
    }

    @Test
    public void testGetPriceFound() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Prepare response writer to capture output
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getParameter("action")).thenReturn("getPrice");
        when(request.getParameter("itemCode")).thenReturn("code123");
        when(response.getWriter()).thenReturn(writer);

        // Mock DAO behavior
        when(itemDAOMock.getItemPrice("code123")).thenReturn(25.5f);

        servlet.doGet(request, response);

        writer.flush();
        String output = stringWriter.toString();

        // Verify output JSON contains price 25.5
        assertEquals("25.5", gson.fromJson(output, Float.class).toString());

        // Verify no error status sent
        verify(response, never()).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    public void testGetPriceNotFound() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getParameter("action")).thenReturn("getPrice");
        when(request.getParameter("itemCode")).thenReturn("invalidCode");
        when(response.getWriter()).thenReturn(writer);

        when(itemDAOMock.getItemPrice("invalidCode")).thenReturn(null);

        servlet.doGet(request, response);

        writer.flush();
        String output = stringWriter.toString();

        assertTrue(output.contains("Item not found"));
        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    public void testGetAllItems() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getParameter("action")).thenReturn("getAll");
        when(response.getWriter()).thenReturn(writer);

        List<Item> itemList = Arrays.asList(
                new Item("code1", "Item One", 10f, "some/path", "category1"),
                new Item("code2", "Item Two", 10f, "some/path", "category2")

        );

        when(itemDAOMock.getAllItems()).thenReturn(itemList);

        servlet.doGet(request, response);

        writer.flush();
        String output = stringWriter.toString();

        // Deserialize output to Item[]
        Item[] items = gson.fromJson(output, Item[].class);
        assertEquals(2, items.length);
        assertEquals("code1", items[0].getItemCode());
        assertEquals("Item One", items[0].getItemName());
    }

    @Test
    public void testValidateCode() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getParameter("action")).thenReturn("validateCode");
        when(request.getParameter("itemCode")).thenReturn("code123");
        when(response.getWriter()).thenReturn(writer);

        when(itemDAOMock.isItemCodeValid("code123")).thenReturn(true);

        servlet.doGet(request, response);

        writer.flush();
        String output = stringWriter.toString();

        assertEquals("{\"valid\": true}", output);
    }

    @Test
    public void testInvalidAction() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getParameter("action")).thenReturn("unknownAction");
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        writer.flush();
        String output = stringWriter.toString();

        assertTrue(output.contains("Invalid action"));
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
