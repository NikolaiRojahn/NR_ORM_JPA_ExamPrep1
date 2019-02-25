/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbfacades;

import entity.C_Order;
import entity.Customer;
import entity.ItemType;
import entity.OrderLine;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nr
 */
public class FacadeTest {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu", null);

    Facade facade = new Facade(emf);

    @Test
    public void testCreateEmployee() {
        facade.createCustomer("Nikolai", "Nikolai@test.dk");
        Customer c = facade.findCustomerByEmail("Nikolai@test.dk");
        assertEquals("Nikolai", c.getName());
    }

    @Test
    public void testFindCustomerByName() {
        List<Customer> cs = facade.findCustomerByName("Rune Klan");
        assertEquals("Rune Klan", cs.get(0).getName());
    }

    @Test
    public void testFindCustomerByEmail() {
        Customer c = facade.findCustomerByEmail("test@test.dk");
        assertEquals("Nikolai Rojahn", c.getName());
    }

    @Test
    public void testFindCustomerByPrimaryKey() {
        Customer c1 = facade.findCustomerByPrimaryKey(1);
        Assert.assertEquals(1, (int) c1.getId());
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> allCustomers = facade.getAllCustomers();
        Assert.assertEquals(2, (int) allCustomers.size());
        for (Customer customer : allCustomers) {
            System.out.println(customer.getName());
        }
    }

    @Test
    public void testCreateOrder() {

        Long countedOrdersBefore = facade.countAllOrders();
        facade.createOrder();
        Long countedOrdersAfter = facade.countAllOrders();

        if (countedOrdersAfter > countedOrdersBefore) {
            assert true;
        } else {
            assert false;
        }
    }

    @Test
    public void testAddOrderToCustomer() {
        Customer c1 = facade.findCustomerByPrimaryKey(1);
        
        int countCustomerCurrentOrders = c1.getOrders().size();
        C_Order o = new C_Order(c1);
        facade.addOrderToCustomer(o, c1);
        int newCount = c1.getOrders().size();

        if (newCount > countCustomerCurrentOrders) {
            assert true;
        } else {
            assert false;
        }
    }

    @Test
    public void testGetOrdersForCustomer() {
        Customer c1 = facade.findCustomerByPrimaryKey(1);
        
        List<C_Order> orders = facade.ordersForCustomer(c1);
        System.out.println(orders.size());
    }

    @Test
    public void testCreateOrderLine() {
        Customer c1 = facade.findCustomerByPrimaryKey(1);

        C_Order o = facade.ordersForCustomer(c1).get(0);

        OrderLine oLine = new OrderLine(4);
        C_Order updatedO = facade.createOrderLine(o, oLine);
        

        if (updatedO.getOrderlines().get(0).getQuantity() == oLine.getQuantity()) {
            assert true;
        } else {
            assert false;
        }
    }
    
    @Test
    public void testCreateItemType() {
        EntityManager em = emf.createEntityManager();
        Customer c1 = facade.findCustomerByPrimaryKey(1);
        C_Order o = c1.getOrders().get(0);
        OrderLine oLine = o.getOrderlines().get(0);
        
       ItemType item = facade.createItemType(oLine, new ItemType("Snickers", "Chocolate Snack", 5));
       
        assertEquals(item.getDescription(), oLine.getItemtype().getDescription());
    }

        @BeforeClass
        public static void setUpClass() {
        EntityManager em = emf.createEntityManager();
            Customer c1 = new Customer("Nikolai Rojahn", "test@test.dk");
            Customer c2 = new Customer("Rune Klan", "rk@gmail.com");
            c1.addOrder(new C_Order());
            try {
                em.getTransaction().begin();
                em.merge(c1);
                em.merge(c2);
                em.getTransaction().commit();

            } finally {
                em.close();
            }
        }

        @AfterClass
        public static void tearDownClass() {
    }

    @Before
        public void setUp() {

    }

    @After
        public void tearDown() {
    }

}
