package com.swissre.syrotynsky;

import java.io.InputStream;
import java.text.NumberFormat;
import java.util.*;

public class CoffeeShop
{
	private static final String BEVERAGE = "beverage.";
	private static final String SNACK = "snack.";
	private static final String EXTRAS = "extras.";

	private static final Set<String> BEVERAGES = new HashSet<>();
	private static final Set<String> SNACKS = new HashSet<>();
	private static final Map<String, Double> MENU_PRICES = new HashMap<>();
	private static final Map<String, Double> EXTRAS_PRICES = new HashMap<>();

	private static final Locale swissLocale = new Locale("de", "CH");
	private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(swissLocale);

	static
	{
		loadProperties();
	}

	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);

		System.out.println("Welcome to the Coffee Shop!");
		System.out.println("Please enter your order comma separated (e.g., 'large coffee with extra milk, bacon roll'):");

		List<String> shoppingList = parseOrderInput(scanner.nextLine());

		double totalCost = calculateTotalCost(shoppingList);
		System.out.println(generateReceipt(shoppingList, totalCost));
	}

	static List<String> parseOrderInput(String input)
	{
		List<String> orderItems = new ArrayList<>();
		String[] items = input.split(",");
		for (String item : items)
		{
			orderItems.add(item.trim().toLowerCase());
		}
		return orderItems;
	}

	static double calculateTotalCost(List<String> shoppingList)
	{
		int beverageCount = 0;
		int snackCount = 0;
		double extraPrice = 0;
		double totalCost = 0.0;

		for (String item : shoppingList)
		{
			String[] parts = item.split(" with ");
			String product = parts[0].toLowerCase();
			String extra = (parts.length > 1) ? parts[1].toLowerCase() : null;

			if (MENU_PRICES.containsKey(product))
			{
				totalCost += MENU_PRICES.get(product);

				// Check if the item is a beverage
				if (BEVERAGES.contains(product))
				{
					beverageCount++;

					// Check if the customer is eligible for a free beverage
					if (beverageCount % 5 == 0)
					{
						totalCost -= MENU_PRICES.get(product);
					}
				}
				// Check if the customer gets a free extra with a snack
				if (SNACKS.contains(product))
				{
					snackCount++;
				}
			}

			// Add extra cost for selected extras
			if (extra != null && EXTRAS_PRICES.containsKey(extra))
			{
				totalCost += EXTRAS_PRICES.get(extra);
				extraPrice = EXTRAS_PRICES.get(extra);
			}
		}

		//discount for one extra if beverage and snack ordered,
		if (beverageCount > 0 && snackCount > 0)
		{
			totalCost -= extraPrice;
		}

		return totalCost;
	}

	static String generateReceipt(List<String> shoppingList, double totalCost)
	{
		StringBuilder receipt = new StringBuilder("Receipt:\n");

		for (String item : shoppingList)
		{
			receipt.append(item).append("\n");
		}

		String formattedValue = currencyFormat.format(totalCost);
		formattedValue = formattedValue.replaceAll("\u00A0", " ");
		receipt.append("Total: ")
				.append(formattedValue);
		return receipt.toString();
	}

	private static void loadProperties()
	{
		try (InputStream input = CoffeeShop.class.getClassLoader().getResourceAsStream("coffee_shop.properties"))
		{
			if (input == null)
			{
				System.out.println("Sorry, unable to find " + "coffee_shop.properties");
				return;
			}

			Properties properties = new Properties();
			properties.load(input);

			for (String key : properties.stringPropertyNames())
			{
				String value = properties.getProperty(key);
				if (key.startsWith(BEVERAGE))
				{
					String beverage = key.substring(BEVERAGE.length()).replace("_", " ");
					MENU_PRICES.put(beverage, Double.parseDouble(value));
					BEVERAGES.add(beverage);
				}
				else if (key.startsWith(SNACK))
				{
					String snack = key.substring(SNACK.length()).replace("_", " ");
					MENU_PRICES.put(snack, Double.parseDouble(value));
					SNACKS.add(snack);
				}
				else if (key.startsWith(EXTRAS))
				{
					EXTRAS_PRICES.put(key.substring(EXTRAS.length()).replace("_", " "), Double.parseDouble(value));
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
