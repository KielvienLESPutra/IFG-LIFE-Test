package kielvien.lourensius.ekasetiaputra.ifglife.kogito.models;

public class CatalogDetail {
	private Integer itemId;
	private Integer qty;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	@Override
	public String toString() {
		return "CatalogDetail [itemId=" + itemId + ", qty=" + qty + "]";
	}
}
