package hellojpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AddressEntity {
    @Id @GeneratedValue
    private Long id;
    private TestAddress address;

    public AddressEntity(TestAddress testAddress) {
        this.address = testAddress;
    }

    public AddressEntity(String cityHistory, String streetHistory, String zipCode) {
        this.address = new TestAddress(cityHistory, streetHistory, zipCode);

    }
}
