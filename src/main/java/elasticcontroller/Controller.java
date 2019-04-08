package elasticcontroller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.elasticsearch.search.SearchHit;

import model.Contact;
import model.Model;

public class Controller implements Model {
	TransportClient client = null;

	@SuppressWarnings("resource")
	public Controller() {

		try {
			client = new PreBuiltTransportClient(Settings.EMPTY)
					.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public String create(Contact contact) {
		IndexResponse response = null;
		try {
			response = client.prepareIndex("address_book", "contact", contact.getName())
					.setSource(jsonBuilder().startObject().field("name", contact.getName())
							.field("phoneNumber", contact.getPhoneNumber()).field("address", contact.getAddress())
							.endObject())
					.get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("response id:" + response.getId());
		return response.getResult().toString();
	}

	public Map<String, Object> getContact(final String name) {
		GetResponse getResponse = client.prepareGet("address_book", "contact", name).get();
		System.out.println(getResponse.getSource());
		return getResponse.getSource();
	}

	@Override
	public String updateContact(final String name, Contact contact) {
		UpdateRequest updateRequest = new UpdateRequest();
		try {
			updateRequest.index("address_book").type("contact").id(name).doc(jsonBuilder().startObject()
					.field("phoneNumber", contact.getPhoneNumber()).field("address", contact.getAddress()).endObject());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			UpdateResponse updateResponse = client.update(updateRequest).get();
			System.out.println(updateResponse.status());
			return updateResponse.status().toString();
		} catch (InterruptedException | ExecutionException e) {
			System.out.println(e);
		}
		return "Not updated";
	}

	@Override
	public String deleteContact(final String name) {
		DeleteResponse deleteResponse = client.prepareDelete("address_book", "contact", name).get();
		System.out.println(deleteResponse.getResult().toString());
		return deleteResponse.getResult().toString();
	}


	public List<Map<String,Object>> getContactsOnQuery() {
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		
		SearchResponse response = client.prepareSearch("address_book").setTypes("contact")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).get();
		
		
		SearchHit[] results = response.getHits().getHits();
	    for (SearchHit hit : results) {
	        Map<String, Object> responseFields = hit.getSourceAsMap();
	        map.add(responseFields);
	    }
		return map;

	}
}
