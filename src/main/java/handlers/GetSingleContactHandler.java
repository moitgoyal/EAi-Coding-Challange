package handlers;

import java.util.Map;
import eai_coding.AbstractRequestHandler;
import eai_coding.Answer;
import eai_coding.EmptyPayload;
import model.Model;

public class GetSingleContactHandler extends AbstractRequestHandler<EmptyPayload> {
	public GetSingleContactHandler(Model model) {
        super(EmptyPayload.class, model);
    }

    @Override
    protected Answer processImpl(EmptyPayload value, Map<String,String> urlParams, boolean shouldReturnHtml) {
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
        return Answer.ok(dataToJson(map));
    }
}
