package handlers;

import java.util.Map;

import eai_coding.AbstractRequestHandler;
import eai_coding.Answer;
import eai_coding.EmptyPayload;
import model.Model;

public class ContactDeleteHandler extends AbstractRequestHandler<EmptyPayload> {

	private Model model;

    public ContactDeleteHandler(Model model) {
        super(EmptyPayload.class, model);
        this.model = model;
    }

    @Override
    protected Answer processImpl(EmptyPayload value, Map<String, String> urlParams, boolean shouldReturnHtml) {
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
        String result = model.deleteContact(name);
        return new Answer(200, result);
    }
}
