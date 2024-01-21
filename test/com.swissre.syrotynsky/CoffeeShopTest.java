package com.swissre.syrotynsky;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class CoffeeShopTest {

	@Test
	public void testParseOrderInputWithEachItem()
	{
		String userInput = "large coffee with extra milk, bacon roll, small coffee with special roast, orange juice";
		List<String> expectedOrder = List.of("large coffee with extra milk", "bacon roll", "small coffee with special roast", "orange juice");
		assertEquals(expectedOrder, CoffeeShop.parseOrderInput(userInput));
	}

	@Test
	public void testParseOrderInputWithEachItemExtraSpaces()
	{
		String userInput = "large coffee  ,  bacon roll , small coffee with special roast,orange juice";
		List<String> expectedOrder = List.of("large coffee", "bacon roll", "small coffee with special roast", "orange juice");
		assertEquals(expectedOrder, CoffeeShop.parseOrderInput(userInput));
	}

	@Test
	public void testCalculateTotalCostWithEachItem()
	{
		List<String> shoppingList1 = List.of("large coffee", "medium coffee", "small coffee", "bacon roll", "orange juice");
		assertEquals(17.63, CoffeeShop.calculateTotalCost(shoppingList1), 0.01);
	}

	@Test
	public void testCalculateTotalCostWithExtra()
	{
		List<String> shoppingList1 = List.of("large coffee", "medium coffee", "small coffee with special roast coffee");
		assertEquals(10.1, CoffeeShop.calculateTotalCost(shoppingList1), 0.01);
	}

	@Test
	public void testCalculateTotalCostSnackBeverageAndFreeExtra()
	{
		List<String> shoppingList2 = List.of("bacon roll", "medium coffee with foamed milk");
		assertEquals(7.58, CoffeeShop.calculateTotalCost(shoppingList2), 0.01);
	}

	@Test
	public void testCalculateTotalCostWithExtra2Snack()
	{
		List<String> shoppingList3 = List.of("large coffee with extra milk", "bacon roll", "medium coffee with foamed milk", "bacon roll");
		assertEquals(15.98, CoffeeShop.calculateTotalCost(shoppingList3), 0.01);
	}

	@Test
	public void testCalculateTotalCostWithFreeBeverage()
	{
		List<String> shoppingList = List.of("large coffee", "small coffee", "medium coffee", "large coffee", "small coffee", "large coffee");
		assertEquals(16.25, CoffeeShop.calculateTotalCost(shoppingList), 0.01);
	}

	@Test
	public void testCalculateTotalCostWith2FreeBeverage()
	{
		List<String> shoppingList1 = List.of("large coffee", "small coffee", "medium coffee", "large coffee", "small coffee", "large coffee");
		List<String> shoppingList2 = List.of("large coffee", "small coffee", "medium coffee", "large coffee", "small coffee", "large coffee");
		List<String> shoppingList = new ArrayList<>(shoppingList1);
		shoppingList.addAll(shoppingList2);

		assertEquals(31.5, CoffeeShop.calculateTotalCost(shoppingList), 0.01);
	}

	@Test
	public void testGenerateReceipt() {
		// Test case 1: Basic scenario with one of each item
		List<String> shoppingList = List.of("large coffee", "small coffee with special roast", "bacon roll", "orange juice");
		String expectedReceipt = "Receipt:\n" +
				"large coffee\n" +
				"small coffee with special roast\n" +
				"bacon roll\n" +
				"orange juice\n" +
				"Total: CHF 14.58";
		assertEquals(expectedReceipt, CoffeeShop.generateReceipt(shoppingList, 14.58));
	}

	@Test
	public void testGenerateEmptyReceipt()
	{
		List<String> shoppingList = List.of();
		String expectedReceipt = "Receipt:\nTotal: CHF 0.00";
		assertEquals(expectedReceipt, CoffeeShop.generateReceipt(shoppingList, 0.0));

	}
}