package Main.controller;

import Main.exceptions.ApiExceptionResponse;
import Main.model.UserRole;
import Main.service.UserRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@Tag(name = "UserRole", description = "User Role API")
@Validated
public class UserRoleController {
    private final UserRoleService userRoleService;

    @Operation(
            summary = "Find all roles",
            description = "Retrieve all user roles"
    )
    @GetMapping
    public ResponseEntity findAllRoles() {
        return ResponseEntity.status(HttpStatus.OK).body(userRoleService.findAllRoles());
    }
    @Operation(
            summary = "Find role by name",
            description = "Retrieve role information based on the name"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role found"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    @GetMapping("/{name}")
    public ResponseEntity findRoleByName(@PathVariable String name) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(userRoleService.findRoleByName(name));
    }
    @Operation(
            summary = "Create role",
            description = "Create a new user role with the provided details"
    )
    @PostMapping
    public ResponseEntity createRole(@Valid @RequestBody UserRole userRole) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(userRoleService.createRole(userRole));
    }
    @Operation(
            summary = "Delete role",
            description = "Delete a user role based on the name"
    )
    @DeleteMapping("/{name}")
    public ResponseEntity deleteRoleByName(@PathVariable String name) throws ApiExceptionResponse {
        userRoleService.deleteRoleByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(name);
    }
}