package com.promineotech.jeep.dao;

import com.promineotech.jeep.entity.Customer;
import com.promineotech.jeep.entity.JeepModel;
import com.promineotech.jeep.entity.Order;
import java.math.BigDecimal;
import java.util.Optional;
import com.promineotech.jeep.entity.Color;
import com.promineotech.jeep.entity.Engine;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.Tire;

public interface JeepOrderDao {

  Optional<Customer> fetchCustomer(String customer);

  Optional<Tire> fetchTire(String tireId);

  Optional<Jeep> fetchModel(JeepModel model, String trim, int doors);

  Optional<Engine> fetchEngine(String engine);

  Optional<Color> fetchColor(String color);

  Order saveOrder(Customer customer, Jeep jeep, Color color, Engine engine, Tire tire,
      BigDecimal price);








}
