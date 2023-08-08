package hellojpa.jpql;

import javax.persistence.Embeddable;

@Embeddable
public class JpqlAddress {
    private String city;
    private String street;
    private String zipcode;

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }
}
