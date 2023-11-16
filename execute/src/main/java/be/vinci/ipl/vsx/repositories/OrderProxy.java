package be.vinci.ipl.vsx.repositories;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name = "order")
public interface OrderProxy {
  @PutMapping("/order/{guid}")
  ResponseEntity<Void> updateOne(@PathVariable String guid,@RequestBody Integer filled);
}
