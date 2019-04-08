package testcases;

import eai_coding.*;
import elasticcontroller.Controller;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import handlers.ContactCreateHandler;
import handlers.ContactDeleteHandler;
import handlers.GetSingleContactHandler;
import model.Model;

public class AddressBookTest {

	@Test
	public void anInvalidNewContactReturnsBadRequest() {
		NewContactCheck newContact = new NewContactCheck();
		newContact.setName("");
		newContact.setAddress("Syracuse");
		newContact.setPhoneNumber("232323");

		assertFalse(newContact.isValid());

		Model model = EasyMock.createMock(Model.class);
		replay(model);

		ContactCreateHandler handler = new ContactCreateHandler(model);
		assertEquals(new Answer(400), handler.process(newContact, Collections.emptyMap(), false));
		assertEquals(new Answer(400), handler.process(newContact, Collections.emptyMap(), true));

		verify(model);
	}

	@Test
	public void aPostIsValid() {
		NewContactCheck newContact = new NewContactCheck();
		newContact.setName("Mohit535");
		newContact.setAddress("Syracuse");
		newContact.setPhoneNumber("232323");
		assertTrue(newContact.isValid());

	}

	@Test
	public void testCreate() {

		NewContactCheck newContact = new NewContactCheck();
		newContact.setName("dummy1");
		newContact.setAddress("Syracuse");
		newContact.setPhoneNumber("232323");

		assertTrue(newContact.isValid());

		Model model = new Controller();
		ContactCreateHandler create = new ContactCreateHandler(model);
		GetSingleContactHandler single = new GetSingleContactHandler(model);
		create.process(newContact, Collections.emptyMap(), false);

		Map<String, String> urlParams = new HashMap<String, String>();

		urlParams.put(":name", newContact.getName());

		Answer ans = single.process(new EmptyPayload(), urlParams, false);

		String json = ans.getBody();
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(json);

		assertNotNull(jo);
		assertEquals(newContact.getName(), jo.get("name").getAsString());
		assertEquals(newContact.getPhoneNumber(), jo.get("phoneNumber").getAsString());
		assertEquals(newContact.getAddress(), jo.get("address").getAsString());

	}

	@Test
	public void testDelete() {

		NewContactCheck newContact = new NewContactCheck();
		newContact.setName("Asa1");
		newContact.setAddress("Syracuse");
		newContact.setPhoneNumber("232323");

		Model model = new Controller();

		ContactCreateHandler create = new ContactCreateHandler(model);
		ContactDeleteHandler delete = new ContactDeleteHandler(model);
		GetSingleContactHandler single = new GetSingleContactHandler(model);
		Map<String, String> urlParams = new HashMap<String, String>();
		create.process(newContact, Collections.emptyMap(), false);
		urlParams.put(":name", newContact.getName());
		delete.process(new EmptyPayload(), urlParams, false);

		assertEquals(new Answer(404), single.process(new EmptyPayload(), urlParams, false));
	}

}
