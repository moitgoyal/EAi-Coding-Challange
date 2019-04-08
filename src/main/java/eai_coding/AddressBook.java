package eai_coding;

import static spark.Spark.*;
import elasticcontroller.Controller;
import handlers.ContactCreateHandler;
import handlers.ContactDeleteHandler;
import handlers.ContactUpdateHandler;
import handlers.GetAllContactsHandler;
import handlers.GetSingleContactHandler;
import model.Model;

public class AddressBook {
	public static Controller controller = new Controller();

	public static void main(String[] args) {
		
		staticFiles.location("/public");
		
		Model model = new Controller();
		
		get("/contact/:name",new GetSingleContactHandler(model));
		
		get("/contact",new GetAllContactsHandler(model));
		
		post("/contact", new ContactCreateHandler(model));
		
		put("/contact/:name", new ContactUpdateHandler(model));
		
		delete("/contact/:name", new ContactDeleteHandler(model));
		
	}

}
