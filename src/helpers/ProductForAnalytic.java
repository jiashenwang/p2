package helpers;

public class ProductForAnalytic {
	
	private String id;
	
	
	private String name;
	
	private int total;

	/**
	 * @param id
	 * @param cid
	 * @param name
	 * @param sKU
	 * @param price
	 */
	public ProductForAnalytic(String id, String name, int total) {
		this.id = id;
		this.name = name;
		this.total = total;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the price
	 */
	public int getTotal() {
		return total;
	}
}