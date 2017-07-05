package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bson.Document;
import play.data.validation.Constraints;
import play.libs.Json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by satrioaditya on 01/07/17.
 */
public class Customer {

    private String id;

    @Constraints.Required()
    private String name;

    @Constraints.Required()
    private String address;

    public Customer() {
    }

    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Customer(String id, String name, String address) {
        if (id != null) {
            this.id = id;
        }
        this.name = name;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonIgnore
    public Document getDocument() {
        ObjectNode node = Json.newObject();
        node.put("name", this.name);
        node.put("address", this.address);
        return Document.parse(node.toString());
    }

    @JsonIgnore
    public Customer setDocument(Document document) throws IOException {
        JsonNode rawJson = Json.mapper().readValue(document.toJson(), JsonNode.class);
        return new Customer(rawJson.findValue("_id").findValue("$oid").asText(""), rawJson.findValue("name").asText(""), rawJson.findValue("address").asText(""));
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public List<Customer> setAllDocument(ArrayList<Document> documents) {
        List<Customer> customers = new ArrayList<>();
        documents.forEach(document -> {
            try {
                customers.add(new Customer().setDocument(document));
            } catch (IOException e) {
                throw new RuntimeException(" FAILED WHEN PARSE DOCUMENT TO CUSTOMER " + e.getMessage(), e.getCause());
            }
        });

        return customers;
    }
}
