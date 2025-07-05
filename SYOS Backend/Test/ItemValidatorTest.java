

import dao.ItemDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ItemValidator;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemValidatorTest {

    private ItemDAO itemDAO;
    private ItemValidator itemValidator;

    @BeforeEach
    void setUp() {
        itemDAO = mock(ItemDAO.class);
        itemValidator = new ItemValidator(itemDAO);
    }

    @Test
    void testIsValidItemCode_ReturnsTrue() {
        String validCode = "ITEM123";

        when(itemDAO.isItemCodeValid(validCode)).thenReturn(true);

        boolean result = itemValidator.isValidItemCode(validCode);

        assertTrue(result);
        verify(itemDAO).isItemCodeValid(validCode);
    }

    @Test
    void testIsValidItemCode_ReturnsFalse() {
        String invalidCode = "INVALID999";

        when(itemDAO.isItemCodeValid(invalidCode)).thenReturn(false);

        boolean result = itemValidator.isValidItemCode(invalidCode);

        assertFalse(result);
        verify(itemDAO).isItemCodeValid(invalidCode);
    }
}
