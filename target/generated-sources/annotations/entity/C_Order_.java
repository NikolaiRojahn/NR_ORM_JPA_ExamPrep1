package entity;

import entity.Customer;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-02-24T12:35:23")
@StaticMetamodel(C_Order.class)
public class C_Order_ { 

    public static volatile SingularAttribute<C_Order, Integer> orderid;
    public static volatile SingularAttribute<C_Order, Customer> customer;

}