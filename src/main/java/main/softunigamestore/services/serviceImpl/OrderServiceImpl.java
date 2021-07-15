package main.softunigamestore.services.serviceImpl;

import main.softunigamestore.repositories.OrderRepository;
import main.softunigamestore.services.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


}
