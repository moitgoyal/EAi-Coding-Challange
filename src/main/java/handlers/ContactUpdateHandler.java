package handlers;

import java.util.Map;

import eai_coding.AbstractRequestHandler;
import eai_coding.Answer;
import eai_coding.UpdateContactCheck;
import model.Contact;
import model.Model;

public class ContactUpdateHandler extends AbstractRequestHandler<UpdateContactCheck> {

	private Model model;

    public ContactUpdateHandler(Model model) {
        super(UpdateContactCheck.class, model);
        this.model = model;
    }

    @Override
    protected Answer processImpl(UpdateContactCheck value, Map<String, String> urlParams, boolean shouldReturnHtml) {
        if (!urlParams.containsKey(":name")) {
            throw new IllegalArgumentException();
        }
        String name;
        try {
            name = urlParams.get(":name");
        } catch (IllegalArgumentException e) {
            return new Answer(404);
        }

        Map<String,Object> map = model.getContact(name);
        if (map == null) {
            return new Answer(404);
        }
        
        Contact contact = new Contact(value.getName(), value.getPhoneNumber(), value.getAddress());
        model.updateContact(name, contact);
        return new Answer(200);
    }
}
