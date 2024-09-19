import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ibas.brta.vehims.model.VehicleType;
import com.ibas.brta.vehims.payload.request.VehicleTypeDTO;
import com.ibas.brta.vehims.service.VehicleTypService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VehicleTypeController {

    @Autowired
    VehicleTypService vehicleTypeService;

    // Create a new item
    @PostMapping("/v1/admin/configurations/vehicle-type/create")
    public ResponseEntity<VehicleType> createStatusGroup(@RequestBody VehicleTypeDTO vehicleTypeDTO) {
        VehicleType createdStatusGroup = vehicleTypeService.createVehilcleType(vehicleTypeDTO);
        return ResponseEntity.ok(createdStatusGroup);
    }

    // Update an existing item
    @PutMapping("/v1/admin/configurations/vehicle-type/update/{id}")
    public ResponseEntity<VehicleType> updateStatusGroup(
            @PathVariable Long id,
            @RequestBody VehicleTypeDTO vehicleTypeDTO) {
        VehicleType updatedStatusGroup = vehicleTypeService.updateVehilcleType(id, vehicleTypeDTO);
        return ResponseEntity.ok(updatedStatusGroup);
    }

    // Delete a item
    @DeleteMapping("/v1/admin/configurations/vehicle-type/{id}")
    public ResponseEntity<Void> deleteStatusGroup(@PathVariable Long id) {
        vehicleTypeService.deleteVehilcleType(id);
        return ResponseEntity.noContent().build();
    }

    // List all items
    @GetMapping("/v1/admin/configurations/vehicle-type/list")
    public ResponseEntity<List<VehicleType>> listAllStatusGroups() {
        List<VehicleType> vehicleType = vehicleTypeService.listAllVehilcleTypes();
        return ResponseEntity.ok(vehicleType);
    }

    // Get a single item by ID
    @GetMapping("/v1/admin/configurations/vehicle-type/{id}")
    public ResponseEntity<VehicleType> getStatusGroupById(@PathVariable Long id) {
        VehicleType vehicleType = vehicleTypeService.getVehilcleTypeById(id);
        return ResponseEntity.ok(vehicleType);
    }
}
