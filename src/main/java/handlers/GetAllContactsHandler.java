package handlers;

import java.util.List;
import java.util.Map;

import eai_coding.AbstractRequestHandler;
import eai_coding.Answer;
import eai_coding.EmptyPayload;
import model.Model;

public class GetAllContactsHandler extends AbstractRequestHandler<EmptyPayload> {
	public GetAllContactsHandler(Model model) {
        super(EmptyPayload.class, model);
    }

    @Override
    protected Answer processImpl(EmptyPayload value, Map<String,String> urlParams, boolean shouldReturnHtml) {
        
        List<Map<String,Object>> map = model.getContactsOnQuery();
        if (map == null) {
            return new Answer(404);
        }
        return Answer.ok(dataToJson(map));
    }
}
