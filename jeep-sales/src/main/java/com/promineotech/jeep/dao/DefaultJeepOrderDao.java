package com.promineotech.jeep.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import com.promineotech.jeep.entity.Color;
import com.promineotech.jeep.entity.Customer;
import com.promineotech.jeep.entity.Engine;
import com.promineotech.jeep.entity.FuelType;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
import com.promineotech.jeep.entity.Order;
import com.promineotech.jeep.entity.Tire;
import lombok.extern.slf4j.Slf4j;

@Component
public class DefaultJeepOrderDao implements JeepOrderDao {
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;
  
  @Override
  public Order saveOrder(Customer customer, Jeep jeep, Color color, Engine engine, Tire tire,
      BigDecimal price) {
    // TODO Auto-generated method stub
    return null;
  }
  /**
   * 
   * @param option
   * @param orderPK
   * @return
   */
  @Override
  public Optional<Customer> fetchCustomer(String customerId) {
    String sql = ""
        + "SELECT * "
        + "FROM customers "
        + "WHERE customer_id = :customer_id";
    
    Map<String, Object> params = new HashMap<>();
    params.put("customer_id", customerId);
    
    return Optional.ofNullable(
        jdbcTemplate.query(sql, params, new CustomerResultSetExtractor()));
  }
  
  @Override
  public Optional<Jeep> fetchModel(JeepModel model, String trim, int doors) {
    // @formatter:off
    String sql = ""
        + "SELECT * "
        + "FROM models "
        + "WHERE model_id = :model_id"
        + "AND trim_level = :trim_level"
        + "AND num_doors = :num_doors";
    // @formatter:on 
    
    Map<String, Object> params = new HashMap<>();
    params.put("model_id", model.toString());
    params.put("trim_level", trim);
    params.put("num_doors", doors);
    
     return Optional.ofNullable(jdbcTemplate.query(sql, params, new ModelResultSetExtractor()));
  }
  
  @Override
  public Optional<Color> fetchColor(String colorId) {
    // @formatter:off
    String sql = ""
        + "SELECT *"
        + "FROM colors"
        + "WHERE colorid = :color_id";
    // @formatter:on
    
    Map<String, Object> params = new HashMap<>();
    params.put("color_id", colorId);
    
    return Optional.ofNullable(jdbcTemplate.query(sql, params, new ColorResultSetExtractor()));
  }
  
  @Override
  public Optional<Engine> fetchEngine(String engineId) {
    // @formatter:off
    String sql = ""
        + "SELECT *"
        + "FROM engines "
        + "WHERE engine_id = :engine_id";
    // @formatter:on
    
    Map<String, Object> params = new HashMap<>();
    params.put("engine_id", engineId);
    
    return Optional.ofNullable(jdbcTemplate.query(sql, params, new EngineResultSetExtractor()));
  }
  
  @Override
  public Optional<Tire> fetchTire(String tireId) {
    // @formatter:off
    String sql = ""
        + "SELECT *"
        + "FROM tires "
        + "WHERE tire_id = :tire_id";
    // @formatter:on
    
    Map<String, Object> params = new HashMap<>();
    params.put("tire_id", tireId);
    
    return Optional.ofNullable(jdbcTemplate.query(sql, params, new TireResultSetExtractor()));
  }
  
  /**
   * 
   * @author Promineo
   *
   */
  class TireResultSetExtractor implements ResultSetExtractor<Tire> {
    @Override
    public Tire extractData(ResultSet rs) throws SQLException {
      rs.next();

      // @formatter:off
      return Tire.builder()
          .manufacturer(rs.getString("manufacturer"))
          .price(rs.getBigDecimal("price"))
          .tireId(rs.getString("tire_id"))
          .tirePK(rs.getLong("tire_pk"))
          .tireSize(rs.getString("tire_size"))
          .warrantyMiles(rs.getInt("warranty_miles"))
          .build();
      // @formatter:on
    }
  }
  
  /**
   * 
   * @author Promineo
   *
   */
  class EngineResultSetExtractor implements ResultSetExtractor<Engine> {
    @Override
    public Engine extractData(ResultSet rs) throws SQLException {
      rs.next();

      // @formatter:off
      return Engine.builder()
          .description(rs.getString("description"))
          .engineId(rs.getString("engine_id"))
          .enginePK(rs.getLong("engine_pk"))
          .fuelType(FuelType.valueOf(rs.getString("fuel_type")))
          .hasStartStop(rs.getBoolean("has_start_stop"))
          .mpgCity(rs.getFloat("mpg_city"))
          .mpgHwy(rs.getFloat("mpg_hwy"))
          .name(rs.getString("name"))
          .price(rs.getBigDecimal("price"))
          .sizeInLiters(rs.getFloat("size_in_liters"))
          .build();
      // @formatter:on
    }
  }
  
  /**
   * 
   * @author Promineo
   *
   */
  class ColorResultSetExtractor implements ResultSetExtractor<Color> {
    @Override
    public Color extractData(ResultSet rs) throws SQLException {
      rs.next();

      // @formatter:off
      return Color.builder()
          .color(rs.getString("color"))
          .colorId(rs.getString("color_id"))
          .colorPK(rs.getLong("color_pk"))
          .isExterior(rs.getBoolean("is_exterior"))
          .price(rs.getBigDecimal("price"))
          .build();
      // @formatter:on
    }
  }

  /**
   * 
   * @author Promineo
   *
   */
  class ModelResultSetExtractor implements ResultSetExtractor<Jeep> {
    @Override
    public Jeep extractData(ResultSet rs) throws SQLException {
      rs.next();

      // @formatter:off
      return Jeep.builder()
          .basePrice(rs.getBigDecimal("base_price"))
          .modelId(JeepModel.valueOf(rs.getString("model_id")))
          .modelPK(rs.getLong("model_pk"))
          .numDoors(rs.getInt("num_doors"))
          .trimLevel(rs.getString("trim_level"))
          .wheelSize(rs.getInt("wheel_size"))
          .build();
      // @formatter:on
    }
  }
  
  
  class CustomerResultSetExtractor implements ResultSetExtractor<Customer> {
    @Override
    public Customer extractData(ResultSet rs) 
        throws SQLException, DataAccessException {
      rs.next();
      
      // @formatter:off
      return Customer.builder()
          .customerId(rs.getString("customer_id"))
          .customerPK(rs.getLong("customer_pk"))
          .firstName(rs.getString("first_name"))
          .lastName(rs.getString("last_name"))
          .phone(rs.getString("phone"))
          .build(); 
      // @formatter:on
    }
    
  }


 


 }
