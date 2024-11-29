package codelook.jpa.order;

import codelook.jpa.user.*;
import codelook.jpa.order.*;
import codelook.jpa.book.*;
import codelook.jpa.image.*;

import org.springframework.data.jpa.repository.JpaRepository;

interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
}
