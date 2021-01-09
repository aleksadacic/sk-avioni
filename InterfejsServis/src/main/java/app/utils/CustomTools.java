package app.utils;

public class CustomTools {
	
	public static int calculateDiscount(String rankType) {
		int discount = 0;
		if (rankType.equalsIgnoreCase("srebro"))
			discount = 10;
		else if (rankType.equalsIgnoreCase("zlato"))
			discount = 20;
		return discount;
	}

}
