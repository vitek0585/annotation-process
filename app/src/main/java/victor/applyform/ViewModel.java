package victor.applyform;

import java.util.ArrayList;
import java.util.List;

class ViewModel {

    @Identity(id = R.id.name)
    @ValueType(type = ValueType.ItemType.text)
    public String Name;

    @Identity(id = R.id.childCount)
    @ValueType(type = ValueType.ItemType.dropdown)
    @ArrayValue(resId = R.array.child)
    public int ChildCount;

    @Identity(id = R.id.date)
    @ValueType(type = ValueType.ItemType.date)
    public Long Date;

    @IdentityGroup(id = R.id.layout_address)
    public Address address;

    @IdentityOptionalGroup(id = R.id.addresses)
    public List<Address> addresses = new ArrayList<>();

    static class Address {
        @Identity(id = R.id.street)
        @ValueType(type = ValueType.ItemType.text)
        public String street;

        public Address(){

        }
    }
}
