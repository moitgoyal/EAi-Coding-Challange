package model;

import java.util.List;
import java.util.Map;

public interface Model {
    String create(Contact contact);
   
    Map<String,Object> getContact(String name);
    
    List<Map<String,Object>> getContactsOnQuery();

    String updateContact(String name , Contact contact);

    String deleteContact(String name);
}
