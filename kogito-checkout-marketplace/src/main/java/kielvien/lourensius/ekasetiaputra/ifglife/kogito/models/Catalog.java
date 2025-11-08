package kielvien.lourensius.ekasetiaputra.ifglife.kogito.models;

import java.util.List;

public class Catalog {
	private List<CatalogDetail> items;
	private Double total;
	private Double payment;
	private boolean avalaible;

	public List<CatalogDetail> getItems() {
		return items;
	}

	public void setItems(List<CatalogDetail> items) {
		this.items = items;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	public boolean getAvalaible() {
		return avalaible;
	}

	public void setAvalaible(boolean avalaible) {
		this.avalaible = avalaible;
	}

	@Override
	public String toString() {
		return "Catalog [items=" + items + ", total=" + total + ", payment=" + payment + ", avalaible=" + avalaible
				+ "]";
	}
}
