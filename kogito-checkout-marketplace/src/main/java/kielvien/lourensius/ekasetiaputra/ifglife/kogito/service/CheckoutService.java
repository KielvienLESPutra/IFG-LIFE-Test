package kielvien.lourensius.ekasetiaputra.ifglife.kogito.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kielvien.lourensius.ekasetiaputra.ifglife.kogito.entities.Item;
import kielvien.lourensius.ekasetiaputra.ifglife.kogito.entities.Purchases;
import kielvien.lourensius.ekasetiaputra.ifglife.kogito.models.Catalog;
import kielvien.lourensius.ekasetiaputra.ifglife.kogito.models.CatalogDetail;
import kielvien.lourensius.ekasetiaputra.ifglife.kogito.repository.ItemRepository;
import kielvien.lourensius.ekasetiaputra.ifglife.kogito.repository.PurchaseRepository;

@Service
public class CheckoutService {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private PurchaseRepository purchaseRepository;

	private Logger log = LoggerFactory.getLogger(CheckoutService.class);
	
	public Catalog doCheckItem(Catalog catalog) {
		log.info("Starting check catalog with paramater : {}", catalog.toString());
		boolean available = true;

		for (CatalogDetail detail : catalog.getItems()) {
			Item item = itemRepository.findById(detail.getItemId()).orElse(null);
			if (item == null || item.getQty() < detail.getQty()) {
				available = false;
				break;
			}
		}

		catalog.setAvalaible(available);
		
		log.info("Finish check catalog with paramater : {}", catalog.toString());
		return catalog;
	}

	public Catalog doSubmitInvoice(Catalog catalog) {
		log.info("Starting submit catalog with paramater : {}", catalog.toString());
		Double total = 0d;
		for (CatalogDetail detail : catalog.getItems()) {
			Item item = itemRepository.findById(detail.getItemId()).orElse(null);
			if (item != null) {
				total = total + (item.getPrice() * detail.getQty());
			}
		}
		catalog.setTotal(total);

		log.info("Finish submit catalog with paramater : {}", catalog.toString());
		return catalog;
	}

	@Transactional
	public Catalog doSavePurchase(Catalog catalog) {
		log.info("Starting save purchase catalog with paramater : {}", catalog.toString());
		Purchases purchases = new Purchases();
		for (CatalogDetail detail : catalog.getItems()) {
			Item item = itemRepository.findById(detail.getItemId()).orElse(null);
			if (item != null) {
				int qty = item.getQty() - detail.getQty();

				item.setQty(qty);
				itemRepository.save(item);

				purchases.setItemId(item.getId());
				purchases.setQty(detail.getQty());
			}
		}

		purchases.setTotalPrice(catalog.getTotal());
		purchaseRepository.save(purchases);
		
		log.info("Finish save purchase catalog with paramater : {}", catalog.toString());
		return catalog;
	}
}
