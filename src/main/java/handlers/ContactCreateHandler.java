package handlers;

import java.util.Map;

import eai_coding.AbstractRequestHandler;
import eai_coding.Answer;
import eai_coding.NewContactCheck;
import model.Contact;
import model.Model;

public class ContactCreateHandler extends AbstractRequestHandler<NewContactCheck> {

	private Model model;

	public ContactCreateHandler(Model model) {
	        super(NewContactCheck.class, model);
	        this.model = model;
	    }

	@Override
	protected Answer processImpl(NewContactCheck value, Map<String, String> urlParams, boolean shouldReturnHtml) {
		Contact contact = new Contact(value.getName(), value.getPhoneNumber(), value.getAddress());
		String result = model.create(contact);
		return new Answer(201, result.toString());
	}
}
