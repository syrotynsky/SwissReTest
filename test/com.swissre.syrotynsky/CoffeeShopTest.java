package com.swissre.syrotynsky;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class CoffeeShopTest {

	@BeforeEach
	void setUp()
	{
		CoffeeShop.shoppingList.clear();
	}

	@Test
	public void testParseOrderInputWithEachItem()
	{
		String userInput = "large coffee with extra milk, bacon roll, small coffee with special roast coffee, orange juice";
		List<String> expectedOrder = List.of("large coffee", "extra milk", "bacon roll", "small coffee", "special roast coffee", "orange juice");
		CoffeeShop.parseOrderInput(userInput);
		assertEquals(expectedOrder, CoffeeShop.shoppingList);
	}

	@Test
	public void testParseOrderInputWithEachItemExtraSpaces()
	{
		String userInput = "large coffee  ,  bacon roll , small coffee with special roast coffee,orange juice";
		List<String> expectedOrder = List.of("large coffee", "bacon roll", "small coffee", "special roast coffee", "orange juice");
		CoffeeShop.parseOrderInput(userInput);
		assertEquals(expectedOrder, CoffeeShop.shoppingList);
	}

	@Test
	public void testIsValidItem() {
		// Test case 1: Valid menu item
		assertTrue(CoffeeShop.isValidItem("large coffee"));

		// Test case 3: Invalid item
		assertFalse(CoffeeShop.isValidItem("invalid item"));
	}

	@Test
	public void testIsValidItemWithExtra() {
		// Test case 1: Valid menu item
		assertTrue(CoffeeShop.isValidItem("large coffee with extra milk"));

 		assertFalse(CoffeeShop.isValidItem("large coffee wit extra milk"));

 		assertFalse(CoffeeShop.isValidItem("large coffee with extra mil"));
	}


	@Test
	public void testCalculateTotalCostWithEachItem()
	{
		CoffeeShop.parseOrderInput("large coffee, medium coffee, small coffee, bacon roll, orange juice");
		assertEquals(17.63, CoffeeShop.calculateTotalCost(), 0.01);
	}

	@Test
	public void testCalculateTotalCostWithExtra()
	{
		CoffeeShop.parseOrderInput("large coffee, medium coffee, small coffee with special roast coffee");
		assertEquals(10.1, CoffeeShop.calculateTotalCost(), 0.01);
	}

	@Test
	public void testCalculateTotalCostSnackBeverageAndFreeExtra()
	{
		CoffeeShop.parseOrderInput(("bacon roll, medium coffee with foamed milk"));
		assertEquals(7.58, CoffeeShop.calculateTotalCost(), 0.01);
	}

	@Test
	public void testCalculateTotalCostWithExtra2Snack()
	{
		CoffeeShop.parseOrderInput("large coffee with extra milk, bacon roll, medium coffee with foamed milk, bacon roll");
		assertEquals(15.98, CoffeeShop.calculateTotalCost(), 0.01);
	}

	@Test
	public void testCalculateTotalCostWithFreeBeverage()
	{
		CoffeeShop.parseOrderInput("large coffee, small coffee, medium coffee, large coffee, small coffee, large coffee");
		assertEquals(16.25, CoffeeShop.calculateTotalCost(), 0.01);
	}

	@Test
	public void testCalculateTotalCostWith2FreeBeverage()
	{
		List<String> shoppingList1 = List.of("large coffee", "small coffee", "medium coffee", "large coffee", "small coffee", "large coffee");
		List<String> shoppingList2 = List.of("large coffee", "small coffee", "medium coffee", "large coffee", "small coffee", "large coffee");
		CoffeeShop.shoppingList.addAll(shoppingList1);
		CoffeeShop.shoppingList.addAll(shoppingList2);

		assertEquals(31.5, CoffeeShop.calculateTotalCost(), 0.01);
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