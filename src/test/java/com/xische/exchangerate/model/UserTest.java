package com.xische.exchangerate.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUser() {
        User user = new User("employee", 5);

        assertEquals("employee", user.getUserType());
        assertEquals(5, user.getCustomerTenureYears());
    }

    @Test
    void testUserSetters() {
        User user = new User();

        user.setUserType("affiliate");
        user.setCustomerTenureYears(3);

        assertEquals("affiliate", user.getUserType());
        assertEquals(3, user.getCustomerTenureYears());
    }

    @Test
    void testUserEquality() {
        User user1 = new User("employee", 5);
        User user2 = new User("employee", 5);

        assertEquals(user1, user2); // Testing equals
        assertEquals(user1.hashCode(), user2.hashCode()); // Testing hashCode
        assertTrue(user1.canEqual(user2)); // Testing canEqual
    }

    @Test
    void testNotEquals() {
        User user1 = new User("employee", 5);
        User user2 = new User("affiliate", 3);

        assertNotEquals(user1, user2); // Testing inequality
    }
}